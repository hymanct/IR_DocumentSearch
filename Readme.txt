A. Scripts directory:
1. root folder: src/edu/pitt/sis/infsci2140
2. HW3Main.java : the main execution class
3. index/ TrectextCollection.java : Loading & parsing TrecText collection file
4. index/ TrecwebCollection.java : Loading & parsing TrecWeb collection file
5. analysis/ TextTokenizer.java : Parse the text token by space
6. analysis/ TextNormalizer.java: convert all words into lowercase
7. analysis/ StopwordsRemover.java : test if the word exist in the stop words list
8. index/MyIndexReader.java : Read the inverted index
9. index/MyIndexWriter.java : Create the inverted index
10. search/ Topic.java : Convert the topics source file into query terms
11. search/ MyRetrievalModel.java : The retrieval model
12. indexout/ : the index file for later use

B. Output:
1. topic.txt  (please see the folder for this generated file)

C. Instructions:
1. Please execute in src folder.
2. the execution command:

javac edu/pitt/sis/infsci2140/HW3Main.java 

java edu/pitt/sis/infsci2140/HW3Main indexoutput/ topics.txt 


D. Environment Information:
1. Operation System: Ubuntu 12.04
2. Java:
java version "1.7.0_25"
OpenJDK Runtime Environment (IcedTea 2.3.10) (7u25-2.3.10-1ubuntu0.12.04.2)
OpenJDK 64-Bit Server VM (build 23.7-b01, mixed mode)
