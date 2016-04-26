package edu.pitt.sis.infsci2140.index;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TrecwebCollection implements DocumentCollection {
        String[] tokens; //Store the text token in this string array
        int index=1; //store the current indicator of token
        int total;  //store the total amount of tokens
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrecwebCollection( FileInputStream instream ) throws IOException {
		// This constructor should take an inputstream of the collection file as the input parameter.
           try {
             System.out.println("Start doing the TrecWebCollection, Total file size to read (in bytes) : "
                                + instream.available()); // Print the corpora file size

             int content; //Temp store the read bytes
             String text; //stroe the corpora file into this string
             StringBuilder builder = new StringBuilder(); //Temp store the read bytes into stringbuilder
             while ((content = instream.read()) != -1) {
                builder.append((char)content); //read from the file into stringbuilder
             }
             text = builder.toString(); //stroe the corpora file into this string
             tokens = text.split("<DOC>"); //split the whole corpora by terms into tokens
             total = tokens.length; //get the  total amount of tkoen
             System.out.println(total);
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
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// Read the definition of this method from edu.pitt.sis.infsci2140.index.DocumentCollection interface 
		// and follow the assignment instructions to implement this method.
           if(--total >= 1){
              String tmp = tokens[index++];  //read the text from tokens array
              String[] bufferArray = tmp.split("<DOCNO>"); //extract the DOCNO from the passage
              if(bufferArray.length < 2) return null;
              bufferArray = bufferArray[1].split("</DOCNO>");
              String docno = bufferArray[0];

              String[] bufferArray1 = tmp.split("</DOCHDR>"); //extract the CONTENT from the passage
              if(bufferArray1.length < 2) return null;
              bufferArray1 = bufferArray1[1].split("</DOC>");
              String text = bufferArray1[0];

              Map<String, Object> map = new HashMap<String, Object>(); //Create the map for return
              map.put("DOCNO", docno);
              map.put("CONTENT", text.toCharArray());
              return map; //return the map variable to client
          }else{
              return null;
          }
	}
	
}
