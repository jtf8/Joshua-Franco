public class Cell
{
   private int state;
   CellState[] cellStates;
  

   public Cell(int playerCount)
   {
      cellStates = new CellState[playerCount];
      for(int i=0; i<playerCount;i++)
      {
         setCellState(i,CellState.EMPTY);
      }
   }
   
   enum CellState
   {
      EMPTY,
      MISS,
      HIT,
      FILLED
   }
   
   public void setCellState(int player, CellState state)
   {
      cellStates[player]= state;
   }//hitCell

   public void setHit(int player)
   {
      cellStates[player]=CellState.HIT;
   }
   
   public void missCell(int player)
   {
      cellStates[player]= CellState.MISS;
   }
   
   public CellState getCellState(int player)
   {
      return cellStates[player];
   }//cellState
   
}

//