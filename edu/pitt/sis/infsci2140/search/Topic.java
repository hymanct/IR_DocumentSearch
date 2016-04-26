package edu.pitt.sis.infsci2140.search;

import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A class that stores topic information.
 */
public class Topic {
	
	
        int topicID = 0; //variable for topicID
        String topic = ""; //variable for query terms

        public Topic(){
        }

        public Topic(int id, String str){
            this.topicID = id;
            this.topic = str;
        }

	public String topicId() { //get topic id
		// you should implement this method
		return String.valueOf(this.topicID);
	}
        public String getTopic() { //get query
                // you should implement this method
                return this.topic;
        }
	
	/**
	 * Parse a list of TREC topics from the provided f.
	 * 
	 * @param f
	 * @return
	 */
	public static List<Topic> parse( File f ) {
	   // you should implement this method
           BufferedReader topics = null; //loading the source topics file
           BufferedWriter output_query = null; //output stream of topics
           List<Topic> list = new ArrayList<>();  //variable for return
           try { //open the mapping file
              topics = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
              output_query = new BufferedWriter( new OutputStreamWriter( new FileOutputStream("./query.txt"), "UTF-8" ) );
              String[] tmp = null;
              String content = null;
              String reg = "(?:^|\\s)\"([^']*?)\"(?:\\s|$)"; //adopt regular expression to get the specfic search term like "" 
              Pattern pattern = Pattern.compile(reg);
              int counter = 0;  String output="";
              while ((content = topics.readLine()) != null) { 
                 tmp = content.split("> "); 
                 if(tmp[0].compareTo("<title")==0) { //get title from the topics files
                    String query = "";
                    Matcher matcher = pattern.matcher(tmp[1]); //regular expression to get the specfic search term like ""
                    boolean matchFound = matcher.find();
                    if(matchFound) {
//                       System.out.println(matcher.start() + "-" + matcher.end()); //debug
                       query += tmp[1].substring(matcher.start()+2, matcher.end()-1) + ","; //if match, put the string into query
                       tmp[1] = tmp[1].substring(0, matcher.start()) + tmp[1].substring(matcher.end(), tmp[1].length()); //remove the string alreaday in query
                    }
                    String[] arr = tmp[1].split(" "); //take care the rest query terms by split by space
                    for( int i = 0; i <= arr.length - 1; i++){
                       query += arr[i] + ","; //add comma between query terms
                    }
                    System.out.println(query); //debug
                    list.add(new Topic(counter, query)); //return variable
                    output += counter+":"+query+"\n"; //store the result for later output stream to files
                    counter++;
                 }
              }   
              output_query.write(output); //output the execution result into stream file
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                 if (topics != null) topics.close();
                 output_query.close();
              }catch (IOException ex) {
                 ex.printStackTrace();
              }
           }
	   return list;
	}
	
}
