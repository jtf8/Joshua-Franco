// HWIT114
// Joshua Franco
import java.util.LinkedList; 
import java.util.Queue; 
  
public class QueueExample 
{ 
  public static void main(String[] args) 
  { 
    Queue<Integer> queue = new LinkedList<>(); 
   
   
    for (int i=0; i<10; i++) 
     queue.add(i); 
 
    // Display contents of the queue. 
    System.out.println("Elements of queue-"+queue); 
   
    int remove = queue.remove(); 
    System.out.println("removed element-" + remove + " from queue"); 
  
    System.out.println(queue); 
 
    int head = queue.peek(); 
    System.out.println("head of queue-" + head); 
    
    int poll = queue.poll();
    System.out.println("removes and returns the head of the queue " + poll);

    int size = queue.size(); 
    System.out.println("Size of queue-" + size); 
    int new1=7855642;
    queue.add(new1);
    System.out.println(new1+ " Elements of queue new queue`-"+queue); 
     
  } //main
}//class