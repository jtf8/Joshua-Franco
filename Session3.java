public class Session3{
   public static void main(String[] args){
      String name = null;
      // Exception in thread "main" java.lang.NullPointerException
      if("bob".equals(name)){
      System.out.println("bob equals name");
      }
      if(name.equals("bob")){
      System.out.println("Name equals bob");
      }
      if("bob" == name){
      System.out.println("Bob is name");
      }
      int count = 0;
      float floatcount = 0.0f;
      float total =0f;
      for(int i=0; i<10; i++){
      count++;
      floatcount += 0.1f;
      }
      System.out.println("count: " + count);
      System.out.println("Float count: " +floatcount);
      if(floatcount ==1){
         System.out.println("its actually 10!");
      }
      }
      }
       