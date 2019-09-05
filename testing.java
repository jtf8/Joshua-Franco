//import java.util.date;
public class testing{//is static
   public int test;
   private int test2;//just within the class
   protected int test3;// package level
   
   public String myString;
   public char myChar;
   //public date myDate;
   
   public static void main(String[] args) {
      testing Test = new testing();
      Test.test=1;
      System.out.println(Test.test);
   
   }//main

}//test

