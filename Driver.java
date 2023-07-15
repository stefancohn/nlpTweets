import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map; 

public class Driver {
        public static void main(String[] args) throws Exception {
                ArrayList<Sentence> dataSplit = new ArrayList<Sentence>();
                Scanner sc = new Scanner(new File("/Users/mrducky/Documents/projects/nplProject/testdata.manual.2009.06.14.csv"));
                while (sc.hasNext()) {
                        //iterates through each line of the csv file
                        String token = sc.nextLine();
                        // turns a string of each line of the csv file int an array split by every category
                        String[] lines = token.split("\",\"");  
                        for (int i = 0; i < lines.length; i++) { //rids all the stuff we don't want in our array
                                lines[i] = lines[i].replace("\"", "");
                                lines[i] = lines[i].replace(",", "");
                                lines[i] = lines[i].replace(".", "");
                                lines[i] = lines[i].replace("'", "");
                        }
                       
                        //this code is to get rid of the extra stuff in the timestamp
                        // String[] arr = lines[2].split(" ");  
                        // for (int i = 2; i <arr.length; i = i + 7){
                           //     lines[i] = (arr[1] + " " + arr[2] + " " + arr[5]);
                        // }
                        
                        //creates a Sentence object that is then added to our arrayList
                        Sentence splitSentence = new Sentence(lines[5], lines[4], lines[2]);
                        dataSplit.add(splitSentence);
                }
                sc.close();

                //convertLine checker
                //String line = "\"4\",\"3\",\"Mon May 11 03:17:40 UTC 2009\",\"kindle2\",\"tpryan\",\"@stellargirl I " +
		//	"loooooooovvvvvveee my Kindle2. Not that, the DX is, cool, but the 2 is fantast,ic in its own right.\"";
                //Sentence convertLineTest = Sentence.convertLine(line);
                //dataSplit.add(convertLineTest);

                HashMap<String, Integer> occuranceOfWords = printTopWords(dataSplit);

                // hashmap checker 
                /* Map.Entry<String, Integer> maxEntry = null;
                for (Map.Entry<String, Integer> entry : occuranceOfWords.entrySet())
                        if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                        maxEntry = entry;
                        int maxValueLen = maxEntry.getValue().toString().length();
                        ArrayList <String> results = new ArrayList<String>();
                        for (Map.Entry set : occuranceOfWords.entrySet()){
                                String value = set.getValue().toString();
                                while(value.length() < maxValueLen)
                                value = " " + value;
                                results.add(value + " of " + set.getKey());
                        }
                        Collections.sort(results);
                        Collections.reverse(results);
                        for (int i = 0; i < results.size() && i < 100; i++)
                                System.out.println(results.get(i)); */
                
                // creates an arraylist that is properly filtered, passes it through getSentiment() to get scores
                ArrayList<Sentence> filteredDataSplit = new ArrayList<Sentence>();
                for (int i = 0; i < dataSplit.size(); i++) {
                        if (dataSplit.get(i).keep(23) == true) {
                                filteredDataSplit.add(dataSplit.get(i));
                        }
                }
                int averageCount = 0;
                for (int c = 0; c < filteredDataSplit.size(); c++) {
                        averageCount = averageCount + filteredDataSplit.get(c).getSentiment();
                }
                System.out.println(averageCount);
                System.out.println(filteredDataSplit.size());
                } 
                //I changed the temporalRange value for each hour, recorded the averageCount divided
                // by the size of the filtered Array list to get the average for each hour.

        //hashmap method 
        public static HashMap<String, Integer> printTopWords(ArrayList<Sentence> Sentences) {
                HashMap<String,Integer> extractor = new HashMap<String,Integer>();
                ArrayList<String> wordList = new ArrayList<>();
                for (int i = 0; i < Sentences.size(); i++) {
                        wordList.add(Sentences.get(i).splitSentence().toString());
                }

                String transform = wordList.toString();
                String[] wordListArray = transform.split(", ");

                for (int i = 0; i < wordListArray.length; i++) {
                        wordListArray[i] = wordListArray[i].replace("[", "");
                        wordListArray[i] = wordListArray[i].replace("]", "");
                }

                for (int i = 0; i < wordListArray.length; i++) {
                        if (extractor.containsKey(wordListArray[i])){
                                int counter = extractor.get(wordListArray[i]) + 1;
                                extractor.put(wordListArray[i], counter);
                        } else {
                                extractor.put(wordListArray[i], 1);
                        }
                }

                return extractor;
                
        }
}      