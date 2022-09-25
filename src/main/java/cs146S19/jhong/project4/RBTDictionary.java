package cs146S19.jhong.project4;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * RBTDictionary has methods that will read in a web dictionary into a RBT and a method that 
 * will compare the words in the poem to those in the dictionary only printing those in both
 * @author antho
 *
 */

public class RBTDictionary {
	RedBlackTree<String> words; //words is a BTD of all the words in a dictionary
	
	/**
	 * Constructor of RBTDictionary
	 * @param rbt
	 */
	public RBTDictionary(RedBlackTree<String> rbt) {
		words = rbt;
	}
	
	/**
	 * Reads a dictionary found at weeb URL and inserts all words into the RBT
	 * @throws IOException
	 */
	public void readWordsFromURL() throws IOException {
		URL weblink = new URL("http://www.math.sjsu.edu/~foster/dictionary.txt"); //this creates a URL that our scanner will access
		Scanner in = new Scanner(weblink.openStream()); //scanner will read from the URL
		
		long startTime = System.currentTimeMillis();
		
		//this while loop will read every line of the URL and insert each word into the RBT
		while(in.hasNextLine()) {
			String temp = in.nextLine();
			words.insert(temp);
			
		}
		
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		System.out.println("The time it took to load the RBT with all the words: " + timeTaken + " ms");
		
		in.close(); //close the scanner
	}
	
	/**
	 * Spell checks all of the words if a word in the poem is not found in the dictionary 
	 * it is not printed
	 * @param file file name of poem/piece
	 * @throws FileNotFoundException
	 */
	public void readFileToSpellCheck(String file) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(file), "UTF-8");
		
		long startTime = System.currentTimeMillis();
		
		while(in.hasNextLine()) {
			
			String line = in.nextLine();
			String[] indPoemWords = line.split("[\\s\\p{Punct}]+");
			
			for(int i = 0; i < indPoemWords.length; i++) {
				if(words.lookup(indPoemWords[i].toLowerCase()) != null)
					System.out.print(words.lookup(indPoemWords[i].toLowerCase()).getKey() + " ");
			}
			System.out.println();
		}
		
		long endTime = System.currentTimeMillis();
		long timeTaken = endTime - startTime;
		
		in.close();
		System.out.println();
		
		System.out.print("The time it took to spell check the words: " +
				 timeTaken + " ms");
		
	}
	
	
}
