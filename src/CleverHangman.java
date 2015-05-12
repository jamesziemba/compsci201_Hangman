import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import com.sun.javafx.collections.MappingChange.Map;

/**
 * You write code here to play a game of Hangman.
 * Some sample code provided at the start. You'll probably remove almost 
 * all of it (readString might stick around).
 * 
 * @author James Ziemba
 */

public class CleverHangman {

	// Used for reading data from the console.
	Scanner myInput;

	public CleverHangman() {
		// Set up our read-from-console.
		myInput = new Scanner(
				new BufferedReader(new InputStreamReader(System.in)));
	}


	/**
	 * Get a line from the user and return the line as a String.
	 * 
	 * @param prompt is printed as an instruction to the user
	 * @return entire line entered by the user
	 */
	public String readString(String prompt) {
		System.out.printf("%s ", prompt);
		String entered = myInput.nextLine();
		return entered;
	}
	//add guessed letter to word if it is in it
	public String[] processLetter(String secret, char letter, String[] blanks){
		for(int j = 0;j<secret.length();j++){
			if(secret.charAt(j) == letter){
				blanks[j] = Character.toString(letter);

			}
		}
		return blanks;
	}
	// show the word: the letters if it was guessed and a "_" if it was not
	public String showBlanks(String[] blanks){
		String retval = "";
		for(int i = 0;i < blanks.length;i++){
			String str = blanks[i] + " ";
			retval += str;
		}
		return retval;
	}
	public ArrayList<String> makeDict(String n,String[] blank,ArrayList<String> data){
		HashMap<String,ArrayList<String>> hmap = new HashMap<String,ArrayList<String>>();
		for(String s: data){
			String[] blank1 = new String[s.length()];
			for(int i =0;i<s.length();i++){
				blank1[i]="_";
			}
			String[] pattern = processLetter(s,n.charAt(0),blank1);
			String shown = showBlanks(pattern);
			if(!hmap.containsKey(shown)){
				hmap.put(shown, new ArrayList<String>());
			}
			hmap.get(shown).add(s);
		}
		int maximum = 0;
		ArrayList<String> maxlist = new ArrayList<String>();
		for(String key : hmap.keySet()){
			if(hmap.get(key).size()>maximum){
				maximum = hmap.get(key).size();
				maxlist = hmap.get(key);
			}

		}
		data = maxlist;
		return data;
	}

	/**
	 * Play one game of Hangman. This should prompt
	 * user for parameters and then play a complete game.
	 * You'll likely want to call other functions from this one. 
	 * The existing code may provide some helpful examples.
	 */
	public void play() {

		HangmanFileLoader data = new HangmanFileLoader();
		data.readFile("lowerwords.txt");

		String input = readString("How many misses until you lose?");
		int guessCount = Integer.parseInt(input);
		String a = readString("How many letters long should the secret word be?");
		int guessLength = Integer.parseInt(a);

		String secretWord = data.getRandomWord(guessLength);

		String[] blanks = new String[guessLength];
		for(int i = 0;i<guessLength;i++){
			blanks[i] = "_";
		}
		String blanks1 = showBlanks(blanks);
		System.out.println(blanks1);
		boolean found = false;
		String guessed = "";
		String underscore = "_";
		int misses = guessCount;
		int correctCount =secretWord.length();
		ArrayList<String> words = new ArrayList<String>();
		words = HangmanFileLoader.myWordMap.get(guessLength);
		while(!found){
			String guess = readString("What's your guess:");
			while(guessed.indexOf(guess)!=-1){
				guess = readString("You already guessed that letter. Please guess again:  ");
			}
			words = makeDict(guess,blanks,words);
			Collections.shuffle(words);
			secretWord = words.get(0); 
			//			System.out.println(secretWord);
			//			System.out.println("# of words to pick from: "+words.size());
			String[] guessedstr = processLetter(secretWord,guess.charAt(0),blanks);
			String blanks2 = showBlanks(guessedstr);
			System.out.println(blanks2);
			int counter1 = 0;
			for(int i = 0;i<secretWord.length();i++){
				if(secretWord.charAt(i) == guess.charAt(0)){
					counter1+=1;
				}
			}
			if(counter1== 0){
				guessed += guess;misses-=1;
			}
			else{
				correctCount-=1;guessed += guess;
			}
			System.out.println("Previous guesses: "+guessed);
			System.out.println("Number of misses until you lose: "+misses);
			int counter2 = 0;
			for(int j = 0;j<blanks2.length();j++){
				if(underscore.charAt(0) == blanks2.charAt(j)){
					counter2+=1;
				}
			}
			if(misses==0){
				found = true;
			}
			if(correctCount==0){
				found = true;
			}
			if(counter2 == 0){
				found=true;
			}

		}
		if(misses==0){
			System.out.println("I'm sorry! You have lost. The secret word was "+secretWord);
		}
		else{
			System.out.println("Congratulations! You've won!");
		}

	}
}

