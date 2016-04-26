package edu.pitt.sis.infsci2140.search;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.*;
import edu.pitt.sis.infsci2140.index.MyIndexReader;

public class MyRetrievalModel {
	
	protected MyIndexReader ixreader;
	
	public MyRetrievalModel() {
		// you should implement this method
	}
	
	public MyRetrievalModel setIndex( MyIndexReader ixreader ) {
		this.ixreader = ixreader;
		return this;
	}
	
	/**
	 * Search for the topic information. 
	 * The returned results should be ranked by the score (from the most relevant to the least).
	 * max_return specifies the maximum number of results to be returned.
	 * 
	 * @param topic The topic information to be searched for.
	 * @param max_return The maximum number of returned document
	 * @return
	 */
	public List<SearchResult> search( Topic topic, int max_return ) throws IOException {
	   // you should implement this method
           List<SearchResult> r = new ArrayList<SearchResult>(); //return variable of this function
           Map<String,Double> match = new HashMap<String,Double>();//map variable of match result 
           int totalWords = ixreader.getTotalWords(); //get totalWords counting from the posting file.
           double u = 100.0; // set up the u=100
           System.out.println("Start search query set : " + topic.getTopic()); //Debug message...
           String[] tokens = topic.getTopic().split(","); //get query terms by split
           for(int i=0; i < tokens.length; i++){
              int df = ixreader.DocFreq(tokens[i]); //get term appear in how many documents
     	      long ctf = ixreader.CollectionFreq(tokens[i]); //get collection term frequency
	      //System.out.println(" >> the token \""+tokens[1]+"\" appeared in "+df+" documents and "+ctf+" times in total"); //debug
              double score = 0.0; //set the default score = 0.0
	      if(df>0){
	         int[][] posting = ixreader.getPostingList(tokens[i]); //get posting file into array
	         for(int ix=0;ix<posting.length;ix++){ //loop the posting data that match the specfic term
	   	    int docid = posting[ix][0]; //get docid
	            int freq = posting[ix][1]; //get document term frequency 
		    String docno = ixreader.getDocno(docid); //get docno
                    int docCount = ixreader.getDocWordcount(docid); //get term frequency in current document
                    score = Math.log(((double)freq + u * ((double)ctf/(double)totalWords)) / ((double)docCount + u)); //the Statistical Language Model of The Dirichlet smoothing method 
                    //System.out.println(docno+","+docid+","+freq+","+score); //debug
                    String key = Integer.toString(docid) + "," + docno; //assign the key to save in the map.
                    Double m = match.get(key);
                    if ( m != null ) { //if the key is exist, means the document is matched in previrous terms, so multiple the two score, or, put the new score into map. 
                       match.put(key, m + score);
                    }else{
                       match.put(key, score);
                    }
	         }
	      }
           }
 
           Set<Entry<String, Double>> set = match.entrySet();//do map sorting
           List<Entry<String, Double>> list = new ArrayList<Entry<String, Double>>(set);
           Collections.sort( list, new Comparator<Map.Entry<String, Double>>(){
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 ){
                return (o2.getValue()).compareTo( o1.getValue() );
            }
           });
           for(Map.Entry<String, Double> entry:list){
              //System.out.println(entry.getKey()+" ==== "+entry.getValue()); //debug
              String tmp[] = entry.getKey().split(","); //split docno and docid
              r.add(new SearchResult( Integer.valueOf(tmp[0]), tmp[1], entry.getValue())); //add return result into SearchResult class
           }
	   return r;
	}
	
}
