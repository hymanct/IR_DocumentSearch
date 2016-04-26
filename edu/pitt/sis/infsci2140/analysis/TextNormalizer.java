package edu.pitt.sis.infsci2140.analysis;

public class TextNormalizer {

	// YOU MUST IMPLEMENT THIS METHOD
	public static char[] normalize( char[] chars ) {
		// return the normalized version of the word characters (replacing all uppercase characters into the corresponding lowercase characters)
                String word = String.valueOf(chars); //convert chars into string
                word = word.toLowerCase(); //convert string into lowercase
		return word.toCharArray(); //return the string by char[]
	}
	
}
