package com.mt.examples;
import java.util.Queue;
import java.util.LinkedList;

public class QueueEx {
	public static void main(String[] args) {
		Queue<CustomKeyValuePair> queue = new LinkedList<CustomKeyValuePair>();
		for(int i = 0; i < 10; i++) {
			queue.add(new CustomKeyValuePair(i, "A Value"));//add function
		}//for
		
		System.out.println("Show queue: " + queue);
		
		CustomKeyValuePair first = queue.remove();
		System.out.println("Pulled first: " + first);
		System.out.println("Show altered queue: " + queue);
		
		CustomKeyValuePair peek = queue.peek();
		System.out.println("Just viewing: " + peek);
		System.out.println("Show unaltered queue: " + queue);
     
	}//main
}
class CustomKeyValuePair{
	public int key;
	public String value;
	public CustomKeyValuePair(int k, String v) {
		this.key = k;
		this.value = v;
	}//CustomKeyValuePair
	@Override
	public String toString() {
		return "{'key':'" + this.key + "', 'value':'" + this.value + "'}";
	}//toString
}//class custom