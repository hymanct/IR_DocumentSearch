package edu.pitt.sis.infsci2140.analysis;

/**
 * TextTokenizer can split a sequence of text into individual word tokens.
 */
public class TextTokenizer {
	

	int length; int index=0;
        String[] words;
	// YOU MUST IMPLEMENT THIS METHOD
	public TextTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is a char array for a whole document)
                String word = String.valueOf(texts); //convert the texts char[] into string
                words = word.split(" "); //split the passage into words
                length = words.length; //store the total amount of words
	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextWord() {
		// read and return the next word of the document; or return null if it is the end of the document
                if(--length >= 0){ //if there is more words, keep do the loop
                   return words[index++].toCharArray(); //return the single word
                }else{
		   return null; //return null if there is no word to return
                }
	}
	
}
