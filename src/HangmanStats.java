import java.util.*;

/**
 * This is a skeleton program. You'll
 * need to modify it, either by adding code in main
 * or in methods called from main.
 * @author James Ziemba
 *
 */

public class HangmanStats {
	public void numberOfWords(HangmanFileLoader loader, int n){
		
		HashSet<String> set = new HashSet<String>();
		for(int k=0; k < 1000000; k += 1) {
			set.add(loader.getRandomWord(n));
		}
		System.out.println("number of "+ n + " letter words = "+ set.size());
		
	}
	
	public void statisticalQuestion(HangmanFileLoader loader){
		HashSet<String> set = new HashSet<String>();
		for(int k=0; k < 1000000; k += 1) {
			set.add(loader.getRandomWord(5));
		}
		int count = 0;
		Iterator<String> itr = set.iterator();
	      while(itr.hasNext()) {
	         String element = itr.next();
	         if(element.charAt(0) == 'a' && element.charAt(4) == 'a'){count+=1;System.out.println(element);}
		
	}
	      System.out.println(count);
	}
	public static void main(String[] args) {
		HangmanStats stats = new HangmanStats();
		HangmanFileLoader loader = new HangmanFileLoader();
		loader.readFile("lowerwords.txt");
		for(int i = 4;i<21;i++){
		stats.numberOfWords(loader,i);}
		stats.statisticalQuestion(loader);
}}
