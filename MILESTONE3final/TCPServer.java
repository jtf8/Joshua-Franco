package BattleShipGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.awt.Point;

public class TCPServer{
	
	private int MAX_POINTS = 5;
	 
	int port = 3336;
	static int clientId = 0;
	//private Thread clientListenThread = null;
	private static ArrayList<Point> client1Coords = new ArrayList<Point>();
	private static ArrayList<Point> client2Coords = new ArrayList<Point>();

	private static Point coordinates = new Point(0, 0);
	List<ServerThread> clients = new ArrayList<ServerThread>();
	HashMap<String, String[]> moves = new HashMap<String,String[]>();
	public static boolean isRunning = true;
	public TCPServer() {
		isRunning = true;
	}
	
	
	public void welcome()
	  {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("                                     |__\n");
	    sb.append("                                     |\\/\n");
	    sb.append("                                     ---\n");
	    sb.append("                                     / | [\n");
	    sb.append("                              !      | |||\n");
	    sb.append("                            _/|     _/|-++'\n");
	    sb.append("                        +  +--|    |--|--|_ |-\n");
	    sb.append("                     { /|__|  |/\\__|  |--- |||__/\\\n");
	    sb.append("                    +---------------___[}-_===_.'____                 /\\\n");
	    sb.append("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _\n");
	    sb.append(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7\n");
	    sb.append(" \\_________________________________________________________________________|\n\n");
	    sb.append("===> Enter your coordinates: x,y\n");
	    sb.append("rules to Battleship");
	    sb.append("player 1 place your ships on the left hand side of your grid & attack on the right hand side of the ship\n");
	    sb.append("player 2 place your ships on the left hand side of your grid & attack on the right hand side of the ship\n");
	    sb.append("Max of 2 players, 5 ships per player to place on board\n");
	    
	    System.out.println(sb.toString());
	  }
	/***
	 * Send the same payload to all connected clients.
	 * @param payload
	 */
	public synchronized void broadcast(Payload payload, String except) {
		//iterate through all clients and attempt to send the message to each
		System.out.println("Sending message to " + clients.size() + " clients");
		//TODO ensure closed clients are removed from the list
		for(int i = 0; i < clients.size(); i++) {
			if(except != null && clients.get(i).getClientName().equals(except)) {
				continue;
			}
			clients.get(i).send(payload);
		}
	}
	public synchronized void broadcast(Payload payload) {
		broadcast(payload, null);
	}
	public void removeClient(ServerThread client) {
		Iterator<ServerThread> it = clients.iterator();
		while(it.hasNext()) {
			ServerThread s = it.next();
			if(s == client) {
				System.out.println("Matched client");
				it.remove();
			}
			
		}
	}
	void cleanupClients() {
		if(clients.size() == 0) {
			//we don't need to iterate or spam if we don't have clients
			return;
		}
		//use an iterator here so we can remove elements mid loop/iteration
		Iterator<ServerThread> it = clients.iterator();
		int start = clients.size();
		while(it.hasNext()) {
			ServerThread s = it.next();
			if(s.isClosed()) {
				//payload should have some value to tell all "other" clients which client disconnected
				//so they can clean up any local tracking/refs or show some sort of feedback
				broadcast(new Payload(PayloadType.DISCONNECT, null));
				s.stopThread();
				it.remove();
			}
		}
		int diff = start - clients.size();
		if(diff != 0) {
			System.out.println("Cleaned up " + diff + " clients");
		}
	}
	/***
	 * Send a payload to a client based on index (basically in order of connection)
	 * @param index
	 * @param payload
	 */
	public synchronized void sendToClientByIndex(int index, Payload payload) {
		//TODO validate index is in bounds
		clients.get(index).send(payload);
	}
	/***
	 * Send a payload to a client based on a value defined in ServerThread
	 * @param name
	 * @param payload
	 */
	public synchronized void sendToClientByName(String name, Payload payload) {
		for(int i = 0; i < clients.size(); i++) {
			if(clients.get(i).getClientName().equals(name)) {
				clients.get(i).send(payload);
				break;//jump out of loop
			}
		}
	}
	/***
	 * Separate thread to periodically check to clean up clients that may have been disconnected
	 */
	void runCleanupThread() {
		Thread cleanupThread = new Thread() {
			@Override
			public void run() {
				while(TCPServer.isRunning) {
					cleanupClients();
					try {
						Thread.sleep(1000*30);//30 seconds
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Cleanup thread exited");
			}
		};
		cleanupThread.start();
	}
	private void start(int port) {
		this.port = port;
		System.out.println("Waiting for client");
		runCleanupThread();
		try(ServerSocket serverSocket = new ServerSocket(port);){
			while(TCPServer.isRunning) {
				try {
					Socket client = serverSocket.accept();
					System.out.println("Client connected");
					welcome();
					ServerThread thread = new ServerThread(client, 
							"id_" + clientId,
							this);
					thread.start();//start client thread
					clients.add(thread);//add to client pool
					clientId++;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				isRunning = false;
				Thread.sleep(50);
				System.out.println("closing server socket");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void sendHit(String client, Point hit) {
		
	}


	public void HandleChoice(String clientName, String choice) {
		
		System.out.println("Handling choice " + choice + " from " + clientName);
		displayPoints();
		//boolean foundAGame= false;
		parseChoice(clientName,choice);
			
	}
	
	public void HandleGuess(String client,String choice) {
		if(client.contentEquals("id_0")) {
			//Client 0 guess
			Point hit = getHitPoint(choice);
			for(Point p : client2Coords) {
				if(hit.equals(p)) {
					client2Coords.remove(p);
					System.out.println("Client 1 Hit Client 2 at " + p);
					break;
				}
			}
		}else {
			//Client 1 guess
			Point hit = getHitPoint(choice);
			for(Point p : client1Coords) {
				if(hit.equals(p)) {
					client1Coords.remove(p);
					System.out.println("Client 2 Hit Client 1 at " + p);
					break;
				}
			}
		}

	}
	public Point getHitPoint(String msg) {
		String[] arr = msg.split(",");
    	int x = Integer.parseInt(arr[0]);
    	int y = Integer.parseInt(arr[1]);
    	
    	return new Point(x,y);
	}
	public void parseChoice(String client,String choice)
	{ 
    	String[] arr = choice.split(",");
    	int x = Integer.parseInt(arr[0]);
    	int y = Integer.parseInt(arr[1]);
    	
    	StoreBoat(client,x,y);
    	       
    } 
	
	public void StoreBoat(String client, int x, int y)
	{		
		coordinates.setLocation(x,y);
		//System.out.println(client);
		if(client.contentEquals("id_0")) {
			client1Coords.add(coordinates.getLocation());
		}else {
			client2Coords.add(coordinates.getLocation());
		}
		displayPoints();

	}
	
	
	public void displayPoints() {
		//System.out.println(client1Coords.size());
		//System.out.println(client2Coords.size());
		System.out.println("Client 1:");
		  for (int i = 0; i < client1Coords.size(); i++) {

		   System.out.println("Point number " + (i + 1)
		     + " is: " + client1Coords.get(i));
		  }
		  System.out.println("\nClient 2:");
		  for (int i = 0; i < client2Coords.size(); i++) {

			   System.out.println("Point number " + (i + 1)
			     + " is: " + client2Coords.get(i));
			  }

		}
	
	
	
	

	

	public static void main(String[] arg) {
		System.out.println("Starting Server");
		TCPServer server = new TCPServer();
		int port = 3000;//port should be coming from command line arguments
		if(arg.length > 0){
			try{
				port = Integer.parseInt(arg[0]);
			}
			catch(Exception e){
				System.out.println("Invalid port: " + arg[0]);
			}		
		}
		if(port > -1){
			System.out.println("Server listening on port " + port);
			server.start(port);
		}
		System.out.println("Server Stopped");
	}
}
