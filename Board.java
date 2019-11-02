
public class Board{
   int size= 5;
   int playerCount =2;
   Cell[][] board;

   public Board(){
      board= new Cell[size][size];
      for(int y = 0; y<size;y++)
         for(int x = 0; x<size;x++)
            board[y][x] = new Cell(playerCount);
   }

   public void setCell(int x, int y, int player, Cell.CellState state){
      board[y][x].setCellState(player,state);
   }

   public void createBoat(int x, int y,int player)
   {
     board[y][x].setCellState(player, Cell.CellState.FILLED);
   }

   public Cell getCell(int x, int y){
      return board[y][x];
   }


   public void printBoard()
   {
      for(int p = 0; p<playerCount;p++) {
         System.out.println("Player " + (p+1) + ":");
         System.out.println("  A B C D E");
         for (int r = 0; r < size; r++) {
            System.out.print((r + 1) + "");
            for (int c = 0; c < size; c++) {
               if (board[r][c].getCellState(p) == Cell.CellState.EMPTY) {
                  System.out.print(" ~");
               } else if (board[r][c].getCellState(p) == Cell.CellState.HIT) {
                  System.out.print(" X");
               } else if (board[r][c].getCellState(p) == Cell.CellState.MISS) {
                  System.out.print(" O");
               }
               else if (board[r][c].getCellState(p) == Cell.CellState.FILLED) {
                  System.out.print(" *");
               }
            }
            System.out.println();
         }
         System.out.println();
      }

   }//printBoard


   public void resetGame()
   {
      for(int p = 0; p<playerCount;p++) {
         for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
               board[row][column].setCellState(p,Cell.CellState.EMPTY); ;
            }
         }
      }
   }


   public static int parseLocation(String loc) {
      switch (loc) {
         case "A":
            return 0;
         case "B":
            return 1;
         case "C":
            return 2;
         case "D":
            return 3;
         case "E":
            return 4;
         default:
            return -1;
      }
   }
   //Test if ship is located on coords
   public boolean testPos(int x, int y,int player){
      return (board[y][x].getCellState(player) == Cell.CellState.EMPTY);
   }

   public boolean testLoss(int x, int y,int player){
      return (board[y][x].getCellState(player) == Cell.CellState.FILLED);
   }


   public boolean testHit(int x, int y,int player) {
      if (board[y][x].getCellState(player) == Cell.CellState.HIT) {
         Board b = new Board();
         b.printBoard();
         return true;
      } else {
         return false;
      }
   }


   public static void main(String[] args){
      Board b = new Board();
      b.setCell(0,0,0, Cell.CellState.HIT);
      b.printBoard();
   }

}