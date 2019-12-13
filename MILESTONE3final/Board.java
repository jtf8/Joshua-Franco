package BattleShipGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;





public class Board {
	
	 private static Point coordinates = new Point(0, 0);
	 private static int boats = 0;
	 private static ArrayList<Point> coordinateListGuess = new ArrayList<Point>();

	//public static Point testPoint = new Point(0,0);
	//public static HashMap<Point, JButton> lazyGrid;
	public static boolean isRunning = true;
	public static void main(String[] args) {
		Dimension buttonSize = new Dimension(100,100);
		
		JFrame frame = new JFrame("Battleship");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(new Dimension(800,800));
		frame.setMinimumSize(new Dimension(600,800));
		//empty panel we'll use as a spacer for now
		JPanel empty = new JPanel();
		JTextArea textArea = new JTextArea();
		
		
		
		
		int rows = 5;
		int cols = 5;
		Dimension gridDimensions = new Dimension(400,400);
		//Create two sample grids to compare adding elements
		
		JPanel grid1 = new JPanel();
		
		//set gridlayout pass in rows and cols
		grid1.setLayout(new GridLayout(rows,cols));
		grid1.setSize(gridDimensions);
		
		JPanel grid2 = new JPanel();
		//set gridlayout pass in rows and cols
		grid2.setLayout(new GridLayout(rows, cols));
		grid2.setSize(gridDimensions);
		JTextField textField = new JTextField();

		

	
		//grid layout creation (full layout control)

			 
				for(int y = 0; y < rows; y++) {
					for(int x = 0; x < cols; x++) {
						
						JButton button = new JButton();
						
						button.setPreferredSize(buttonSize);
						//%1 first param, %2 second param, etc
					
							
						String buttonText = String.format("%1$s,%2$s", y, x);
						System.out.println("TEXT "+ buttonText);
						button.setText(buttonText);
						button.setLocation(x, y);
						
					
			//this is getting the text from button text and displaying lets try sending this
			//to server
		
			button.setBackground(new Color(51, 204, 255));
			
			
							
			//create an action to perform when button is clicked
			//override the default actionPerformed method to tell the code how to handle it
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(boats<5) {
					textField.setText(((JButton)e.getSource()).getText());
					((JButton)e.getSource()).setBackground(Color.white);
					Interaction.placeBoat(buttonText);
					boats++;
					//Interaction.SendBoat(x,y);
					//give focus back to grid2 for navigation sample
					grid2.grabFocus();
					}
				}
				
			});
			grid1.add(button);
					
		
		}//GRID 1
	}//grid 1 ends here
				

		
		
		//can omit if not doing navigation sample
		//lazyGrid = new HashMap<Point,JButton>();
		//keep if using Random, otherwise can omit
		//Random random = new Random();

		for(int y1 = 0; y1 < rows; y1++) {
			for(int x1 = 0; x1 < cols; x1++) {
				JButton bt = new JButton();
				bt.setPreferredSize(buttonSize);
				//%1 first param, %2 second param, etc
				String buttonText = String.format("%1$s,%2$s", y1, x1);
				bt.setText(buttonText);
				bt.setLocation(x1, y1);
				//point to button map for easy button reference in navigation sample
				//can omit these related lines if it's not relevant to you
				//	Point p = new Point(x1, y1);
					//lazyGrid.put(p, bt);
					//set background color based on this point matching our testPoint
				coordinates.setLocation(x1, y1);
				coordinateListGuess.add(coordinates.getLocation());
					
					bt.setBackground(new Color(250,0, 0));
				//end potential omit section
			
				//create an action to perform when button is clicked
				//override the default actionPerformed method to tell the code how to handle it
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//set the textfield value to the text value of the button to show clicked coordinate
						textField.setText(((JButton)e.getSource()).getText());
						//set clicked button to red
						((JButton)e.getSource()).setBackground(Color.white);
						Interaction.sendGuess(buttonText);
					
						//give focus back to grid2 for navigation sample
						grid2.grabFocus();
						
					}
					
				});
				//add the button to grid2
				grid2.add(bt);
				//increment our index to demo the order the buttons are added
			}
		}//GRID 2
		
	
		//add grid1 sample to left

		frame.add(grid1, BorderLayout.WEST);
		//add empty space to prevent the grids from visually merging initially
		frame.add(empty, BorderLayout.CENTER);
		//add grid2 sample to right
		frame.add(grid2, BorderLayout.EAST);
		//add output field to bottom
		frame.add(textField, BorderLayout.SOUTH);
		//resize based on elements applied to layout
		frame.pack();
		frame.setVisible(true);
		
		
		grid2.setFocusable(true);
		grid2.grabFocus();
		
		//Interaction will be our instance to interact with
		//socket client
		Interaction Interaction = new Interaction();
		Thread clientMessageReader = new Thread() {
			@Override
			public void run() {
				while(isRunning && Interaction.isClientConnected()) {
					String m = Interaction.getMessage();
					if(m != null) {
						System.out.println("Got message " + m);
						if(m.indexOf("[name]") > -1) {
							String[] n = m.split("]");
							frame.setTitle(frame.getTitle() + " - " + n[1]);
						}
						else {
							//System.out.println("Appending to textarea");
							textArea.append(m +"\n");
						}
					}
				
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Message reader thread finished");
			}
			
		};
		
		
		//I want this connection panel to show on the border of my grid that way I 
		JPanel connectionPanel = new JPanel();
		JTextField hostTextField = new JTextField();
		hostTextField.setText("127.0.0.1");
		JTextField portTextField = new JTextField();
		portTextField.setText("3000");
		JTextField errorTextField = new JTextField();
		errorTextField.setText("");
		JButton connect = new JButton();
		connect.setText("Connect");
		connect.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String hostStr = hostTextField.getText();
		        String portString = portTextField.getText();
		        try {
		        	int port = Integer.parseInt(portString.trim());
		        	Interaction.connect(hostStr, port, errorTextField);
		        	errorTextField.setText("Success!");
		        	empty.setVisible(false);
		        	clientMessageReader.start();
		        	System.out.println("Connected");
		        }
		        catch(Exception ex) {
		        	ex.printStackTrace();
		        	errorTextField.setText(ex.getMessage());
		        }
		    }
		});
		connectionPanel.add(hostTextField);
		connectionPanel.add(portTextField);
		connectionPanel.add(connect);
		connectionPanel.add(errorTextField);
		
		
		frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				  BattleShipGame.Interaction.client.disconnect();
				    System.exit(0);
				  }
		});
		frame.add(connectionPanel, BorderLayout.SOUTH);
		//frame.add(grid1, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
	
		
		
	}//main
	
}//class big




class Interaction {
	static ClientConnect client;
	public Interaction() {
		
	}
	public void connect(String host, int port, JTextField errorField) throws IOException{
		//thread just so we don't lock up main UI
		Thread connectionThread = new Thread() {
			@Override
			public void run() {
				client = new ClientConnect();
				try {
					client.connect(host, port);
					client.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorField.setText(e.getMessage());
					errorField.getParent().setVisible(true);
				}//this terminates when client is closed
				
				System.out.println("Connection thread finished");
			}
		};
		connectionThread.start();
	}
	public static void placeBoat(String choice) {
		client.placeBoat(choice);
	}//place boat

	
	public static void sendGuess(String guess) {
			client.sendGuess(guess);
	}//guess
	public boolean isClientConnected() {
		if(client == null) {
			return true;//just so loop doesn't die early
		}
		return client.isStillConnected();
	}
	public String getMessage() {
		if(client == null) {
			return null;
		}
		return client.messages.poll();
	}
}
