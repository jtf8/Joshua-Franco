import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Knufss
{
	// Generic function to randomize a list in Java using Fisher–Yates shuffle
	public static<Alist> void shuffle(List<Alist> list)
	{
		Random random = new Random();

		// start from end of the list
		for (int i = list.size() - 1; i >= 1; i--)
		{
			// get a random index j such that 0 <= j <= i
			int Alist = random.nextInt(i + 1);

			// swap element at i'th position in the list with element at
			// randomly generated index Alist
			Alist obj = list.get(i);
			list.set(i, list.get(Alist));
			list.set(Alist, obj);
		}//for
	}//static

	public static void main (String[] args)
	{
   for (int i=0; i<6; i++){
		List<String> list = new ArrayList(Arrays.asList("Eel", "Cat", "Dog", "Fish", "Lizard", "Ant"));
		shuffle(list);
      int index = i+1;

		System.out.println((i+1) +") "+list);
      }//for
	}//main
}//Knuffs class