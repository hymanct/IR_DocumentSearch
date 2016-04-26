package edu.pitt.sis.infsci2140.index;
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

/**
 * A class for reading your index.
 */
public class MyIndexReader {
	
	protected File dir;
        String path = null;	
 
	public MyIndexReader( File dir ) throws IOException {
	   this.dir = dir;
	}
	
	public MyIndexReader( String path_dir ) throws IOException {
	   this( new File(path_dir) );
           this.path = path_dir; //stroe the path for later use
	}
	
	/**
	 * Get the (non-negative) integer docid for the requested docno.
	 * If -1 returned, it indicates the requested docno does not exist in the index.
	 * 
	 * @param docno
	 * @return
	 */
	public int getDocid( String docno ) {
		// you should implement this method.              
           BufferedReader in_mapping = null; 
           try { //open the mapping file
              in_mapping = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/mapping.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_mapping.readLine()) != null) {
                 tmp = content.split(": ");
                 if(tmp[1].compareTo(docno)==0) return Integer.valueOf(tmp[0]); //if the mapping match the docno, then return the docid
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                 if (in_mapping != null) in_mapping.close();
              }catch (IOException ex) {
                 ex.printStackTrace();
              }
           }
           return -1; 
	}
	
	/**
	 * Retrive the docno for the integer docid.
	 * 
	 * @param docid
	 * @return
	 */
	public String getDocno( int docid ) {
		// you should implement this method.
           BufferedReader in_mapping = null;
           try {//open the mapping file
              in_mapping = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/mapping.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_mapping.readLine()) != null) {
                 tmp = content.split(": ");
                 if(Integer.valueOf(tmp[0]) == docid) return tmp[1];//if the mapping match the docid, then return the docno
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_mapping != null) in_mapping.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return null;
	}
	
	/**
	 * Get the posting list for the requested token.
	 * 
	 * The posting list records the documents' docids the token appears and corresponding frequencies of the term, such as:
	 *  
	 *  [docid]		[freq]
	 *  1			3
	 *  5			7
	 *  9			1
	 *  13			9
	 * 
	 * ...
	 * 
	 * In the returned 2-dimension array, the first dimension is for each document, and the second dimension records the docid and frequency.
	 * 
	 * For example:
	 * array[0][0] records the docid of the first document the token appears.
	 * array[0][1] records the frequency of the token in the documents with docid = array[0][0]
	 * ...
	 * 
	 * NOTE that the returned posting list array should be ranked by docid from the smallest to the largest. 
	 * 
	 * @param token
	 * @return
	 */
	public int[][] getPostingList( String token ) throws IOException {
		// you should implement this method.
           int[][] r = null; //the variable for return
           BufferedReader in_posting = null;
           try {//open the posting file
              in_posting = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/posting.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_posting.readLine()) != null) {
                 tmp = content.split(": ");
                 if(tmp[0].compareTo(token)==0) { //if the term match the token, then start to create the return 2d array
                    String[] posting = tmp[1].split(","); //split the documentid:frequency into array
                    r = new int[posting.length][2];
                    for( int i = 0; i < posting.length ; i++){ //create the return 2d array
                       String[] tmp2 = posting[i].split(":");
                       r[i][0] = Integer.valueOf(tmp2[0]);
                       r[i][1] = Integer.valueOf(tmp2[1]);
                    }
                 }
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_posting != null) in_posting.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return r;
	}
	
        public int getDocWordcount(int docid){
           BufferedReader in_posting = null;
           int r = 0;
           try {//open the posting file
              in_posting = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/posting.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_posting.readLine()) != null) {
                 tmp = content.split(","+docid+":");
                 if(tmp.length==2) { //if the term match the token, then start to create the return 2d array
                    String[] b = tmp[1].split(",");
                    r += Integer.parseInt(b[0]);
                 }
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_posting != null) in_posting.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return r;
        }

        public int getTotalWords(){
           BufferedReader in_dict = null;
           int r = 0;
           try {//open the dic file
              in_dict = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/dic.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_dict.readLine()) != null) {
                 tmp = content.split(": ");
                 r += Integer.valueOf(tmp[1]);
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_dict != null) in_dict.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return r;

        }

	/**
	 * Return the number of documents that contains the token.
	 * 
	 * @param token
	 * @return
	 */
	public int DocFreq( String token ) throws IOException {
		// you should implement this method.
           BufferedReader in_posting = null;
           try {//open the posting files
              in_posting = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/posting.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_posting.readLine()) != null) {
                 tmp = content.split(": ");
                 if(tmp[0].compareTo(token)==0) { //if the term match the toekn, then return the frequency number
                    String[] posting = tmp[1].split(",");
                    return posting.length;
                 }
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_posting != null) in_posting.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return 0;
	}
	
	/**
	 * Return the total number of times the token appears in the collection.
	 * 
	 * @param token
	 * @return
	 */
	public long CollectionFreq( String token ) throws IOException {
		// you should implement this method.
           BufferedReader in_dict = null;
           try {//open the dic file
              in_dict = new BufferedReader(new InputStreamReader(new FileInputStream(path+"/dic.txt")));
              String[] tmp = null;
              String content = null;
              while ((content = in_dict.readLine()) != null) {
                 tmp = content.split(": ");
                 if(tmp[0].compareTo(token)==0) return Integer.valueOf(tmp[1]); //if the term match the token, then return the frequency number
              }
           } catch (IOException e) {
              e.printStackTrace();
           } finally {
              try {
                if (in_dict != null) in_dict.close();
              }catch (IOException ex) {
                ex.printStackTrace();
              }
           }
           return 0;

	}
	
	public void close() throws IOException {
		// you should implement this method when necessary
            //nothing to do here
	}
	
}

