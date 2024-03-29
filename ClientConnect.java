import java.io.*;
import java.net.*;

public class ClientConnect {
	
	public static void main(String argv[]) throws Exception {

		String ip = "127.0.0.1";
		Socket clientSocket;

		//from command line
		BufferedReader inFromUser = new BufferedReader(
				new InputStreamReader(System.in));
		clientSocket = new Socket(ip, 6789);
		//from server
		BufferedReader inFromServer = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
		//out to server the hit or miss message
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());

		System.out.println("Connected To Server!");

		String request = inFromServer.readLine();

		Board gameBoard = new Board();
		switch (request){
			case "PLACE SHIPS":
				String outString = "";
				System.out.println("Below you can input where you want to" +
						" place your battleships.\n Please enter them in integers" +
						" starting with the row followed by columns\n (for example" +
						" start with the Row as A for Row 1,\n  " +
						" Column as 2 for column 2)\n. Please input the data\n " +
						"left to right and top to bottom\n" +
						"Type q when done.");
				for(int p =0;p<1;p++) {
					//Ships per player
					for (int c = 0; c < 5; c++) {
						System.out.println("player:" + (p + 1));
						System.out.println("Please enter Row location:");
						String line1 = inFromUser.readLine();
						System.out.println("Please enter Column location:");
						String line2 = inFromUser.readLine();

						int x = Board.parseLocation(line1);
						int y = Integer.parseInt(line2) - 1;
						//we call the testPos method to verify that we can place
						//the battleship at the inputed locationn
						if (gameBoard.testPos(x, y, p)) {
							gameBoard.createBoat(x, y, p);
							System.out.println("Creating boat at " + line1 + " and " + line2);
							outString += ""+ x+""+y;
						} else
							System.out.println("Sorry, can't place the battleship using these locations.");
					}
				}
				outToServer.writeBytes(outString + '\n');
				outToServer.flush();
				break;
			default:
				break;
		}

		System.out.println("Set the ships done!");

	}
	
}