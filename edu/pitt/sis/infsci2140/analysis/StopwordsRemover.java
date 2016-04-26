package edu.pitt.sis.infsci2140.analysis;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class StopwordsRemover {

	String[] tokens; //Store the text token in this string array
        int total; //store the total amount of tkoen
	// YOU MUST IMPLEMENT THIS METHOD
	public StopwordsRemover( FileInputStream instream ) {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
          try {

             int content; //Temp store the read bytes
             String text; //stroe the corpora file into this string
             StringBuilder builder = new StringBuilder(); //Temp store the read bytes into stringbuilder
             while ((content = instream.read()) != -1) {
                builder.append((char)content);
             }
             text = builder.toString(); //stroe the corpora file into this string
             tokens = text.split("\n"); //split the whole corpora by terms into tokens
             total = tokens.length; //get the  total amount of tkoen
             } catch (IOException e) {
                e.printStackTrace();
             } finally {
                try {
                   if (instream != null) instream.close();
                } catch (IOException ex) {
                   ex.printStackTrace();
                }
             }

	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
           String term = String.valueOf(word); //convert word variable into string
           int i; 
           for (i = 0; i < total; i++) {
              if(term.equals(tokens[i])) return true; //check if the word exist in the stopword array
           }
           return false; //return false if the word is not listed in the stopword array
	}
	
}
