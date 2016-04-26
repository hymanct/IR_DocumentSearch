package edu.pitt.sis.infsci2140.index;
import edu.pitt.sis.infsci2140.analysis.TextNormalizer;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.*;
import edu.pitt.sis.infsci2140.analysis.TextTokenizer;

public class MyIndexWriter {
        File dir;	
        BufferedWriter output_dic = null; //outputstream of dict
        BufferedWriter output_posting = null; // outputstream of posting
        BufferedWriter output_mapping = null; //outputstream of mapping
        Map<String,Integer> dic = new HashMap<String,Integer>(); //store of dict
        Map<String,String> posting = new HashMap<String,String>(); //store of posting
        Map<Integer,String> mapping = new HashMap<Integer,String>(); //store of mapping
        int did = 0; //global document id variable

	public MyIndexWriter( File dir ) throws IOException {
		this.dir = dir;
	}
	
	public MyIndexWriter( String path_dir ) throws IOException {
	   this.dir = new File(path_dir);
	   if( !this.dir.exists() ) {
	      this.dir.mkdir();
	   } 
           try {
	   // Initiate the output writer ...
           // create three instances for the output stream
	      output_dic = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(path_dir+"/dic.txt"), "UTF-8" ) );
              output_posting = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(path_dir+"/posting.txt"), "UTF-8" ) );
              output_mapping = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(path_dir+"/mapping.txt"), "UTF-8" ) );
           }catch(IOException e){
	      System.out.println("ERROR: cannot initiate output file.");
	      e.printStackTrace();
           }
	}
	
	/**
	 * This method build index for each document.
	 * NOTE THAT: in your implementation of the index, you should transform your string docnos into non-negative integer docids !!!
	 * In MyIndexReader, you should be able to request the integer docid for docnos.
	 * 
	 * @param docno Docno
	 * @param tokenizer A tokenizer that iteratively gives out each token in the document.
	 * @throws IOException
	 */
	public void index( String docno, TextTokenizer tokenizer) throws IOException {
		// you should implement this method to build index for each document
	   char[] word = null;
           Map<String,Integer> tmp = new HashMap<String,Integer>();
	   while( ( word=tokenizer.nextWord() ) != null ) { // iteratively loading each word from the document
	     word = TextNormalizer.normalize(word); // normalize each word
             String w = String.valueOf(word); //convert char into string
             w = w.replace("\n", "").replace("\r", ""); //remove the break charactor \n
             w = w.replace(",", " ");
             w = w.replace(":", " ");
             Integer m = dic.get(w); //get the current term frequency of given word
             Integer tmp_m = tmp.get(w); // get the local variable of term frequency for the posting file later
             if ( m != null ) { //examine if the term exist in current dict map
               // not found
                dic.put(w, m + 1);
             } else {
               // found
                dic.put(w, 1);
             }

             if ( tmp_m != null ) {//examine if the term frequency exist in current local tmp map
                tmp.put(w, tmp_m + 1);
             }else{
                tmp.put(w, 1);
             }
           }

           Set<Map.Entry<String, Integer>> set = tmp.entrySet(); //put the local frequency into global posting map
           for (Map.Entry<String, Integer> me : set) {
              String k = me.getKey();
              String m = posting.get( me.getKey());
              if ( m != null ) {
               // not found
                posting.put(k, m + did + ":" + me.getValue() + ",");
              } else {
                // found
                posting.put(k, did + ":" + me.getValue() + ",");
              }              
           }
           mapping.put(did++, docno); //put the global document id into global mapping map
	}
	
	/**
	 * Close the index writer, and you should output all the buffered content (if any).
	 * @throws IOException
	 */
	public void close() throws IOException {
		// you should implement this method if necessary


           Set<Map.Entry<String, String>> set1 = posting.entrySet(); //save the stream into posting file
           for (Map.Entry<String, String> me : set1) {
              output_posting.write(me.getKey()+ ": ");
              output_posting.write(me.getValue() + "\n");
           }
           Set<Map.Entry<Integer, String>> set2 = mapping.entrySet(); //save the stream into mapping file
           for (Map.Entry<Integer, String> me : set2) {
              output_mapping.write(me.getKey()+ ": ");
              output_mapping.write(me.getValue() + "\n");
           }
           Set<Map.Entry<String, Integer>> set3 = dic.entrySet(); //save the stream into dict file
           for (Map.Entry<String, Integer> me : set3) {
              output_dic.write(me.getKey()+ ": ");
              output_dic.write(me.getValue() + "\n");
           }

	   if( output_dic!=null ) { //close the streams
	      try{
	        output_dic.close();
                output_posting.close();
                output_mapping.close();
	      }catch(IOException e){ }
	   }
	}
	
}
