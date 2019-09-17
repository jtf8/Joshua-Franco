import java.util.Collections;
import java.util.*;
import java.util.List;
public class switchValues
{
public static void main(String[]args)
{
List<String> myStrings = new ArrayList<String>();
String[] stringArray= new String[3];

myStrings.add("hi");
myStrings.add("bye");
myStrings.add("hi again");
myStrings.add("and I'm still here");
myStrings.add("see you tomorrow");


//Todo you guys make it sort alphabetically
Collections.sort(myStrings);
System.out.println("List after the use of" + " Collection.sort() :\n" + myStrings); 
System.out.println("------------------------------------------------");
//todo reverse sort
Collections.reverse(myStrings);
System.out.println("List after the use of" + " Collection.reverse() :\n" + myStrings); 
System.out.println("-----------------------------------------------");
//todo shuffle
Collections.shuffle(myStrings);
System.out.println("List after the use of" + " Collection.shuffle() :\n" + myStrings); 
System.out.println("-----------------------------------------------");
//todo create list of int, each index has a value of its index
List<Integer> myInts = new ArrayList<Integer>();
for (int i=0; i<10; i++){
myInts.add(i);
}
int total = 0;
for(int i=0; i<myInts.size(); i++){
   total += myInts.get(i);
   //total=total + myInts.get(i);
   }
   System.out.println("my total:" + total);

//TODO using the integer list, print the values and if its
//odd or even?

for(int i=0; i<myInts.size(); i++)
{

if(myInts.get(i)%2 == 0)
{
   int value = myInts.get(i);
  System.out.println(value+ " even");

}//if

else {
 int value2 = myInts.get(i);
 System.out.println(value2 + " 0dd");

}//else




//sum and show result

/*
stringArray[1]="bye";
stringArray[2]="hi again";

String temp = stringArray[2];
stringArray[2] = stringArray[0];
stringArray[0]= temp;

myStrings.add(1);
int myInt=1;
String testString=myInt+"";
System.out.println(testString);
if(testString.equals(myInt)){
   System.out.println("testString == myInt");
}
else{
System.out.println("testString != myInt");
}
myStrings.set(2, "Its me");
int size = myStrings.size();
for (int i=0; i< myStrings.size(); i++) {
   System.out.println("Index[" + i +"]=>" + myStrings.get(i));
   //System.out.println(myStrings.get(i));
   //System.out.println((Object)"string");
   //System.out.println("Array Index[" + i +"]=>" + stringArray[i]);
   }//for
   */
   
   }//main
   }//class switch
   }