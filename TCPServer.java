

import javax.swing.*;
import java.io.*;
import java.net.*;

public class TCPServer {

   public static void main(String[] args) throws Exception{

      ServerSocket welcomeSocket;
      int playerCount = 2;

      Socket[] players = new Socket[playerCount];
      BufferedReader[] clientIn = new BufferedReader[playerCount];
      DataOutputStream[] serverOut = new DataOutputStream[playerCount];


   	//from command line
      BufferedReader inFromUser = new BufferedReader(
         	new InputStreamReader(System.in));
   	//create the board
      welcomeSocket = new ServerSocket(6789);
      for(int i = 0; i <playerCount; i++) {
         players[i] = welcomeSocket.accept();
         System.out.println("Client Connected!");
      }

      Board gameBoard = new Board();

      for(int i = 0; i<playerCount;i++){
         clientIn[i] = new BufferedReader(
                 new InputStreamReader(players[i].getInputStream()));
         serverOut[i] = new DataOutputStream(players[i].getOutputStream());
      }

      System.out.println("Set the inputs for both players");
      for(int i = 0; i<playerCount;i++){
         System.out.println("Placing ships: " + i);
         serverOut[i].writeBytes("PLACE SHIPS\n");
         serverOut[i].flush();

         parseShipInput(clientIn[i].readLine(),gameBoard,i);
      }
      gameBoard.printBoard();
   }

   public static void parseShipInput(String coords,Board board,int playerNum){
      for(int i = 0; i<coords.length();i+=2){
         int x = Integer.parseInt(coords.charAt(i)+"");
         int y = Integer.parseInt(coords.charAt(i+1)+"");
         board.createBoat(x,y,playerNum);
      }
   }

}