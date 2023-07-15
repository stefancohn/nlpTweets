import java.util.ArrayList;
import java.util.Properties;
import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class Sentence {
        private String text;
        private String author;
        private String timestamp;

        public Sentence(String text, String author, String timestamp) {
                this.text = text;
                this.author = author;
                this.timestamp = timestamp;
        }

        public String getText() {
                return text;
        }

        public String getAuthor() {
                return author;
        }

        public String getTimestamp() {
                return timestamp;
        }

        public void setText(String text) {
                this.text = text; 
        }

        public void setAuthor(String author) {
                this.author = author; 
        }

        public void setTimestamp(String timestamp) {
                this.timestamp = timestamp; 
        }

        public static Sentence convertLine(String line) {
                String token = line;
                String[] lines = token.split("\",\"");
                for (int i = 0; i < lines.length; i++) {
                        lines[i] = lines[i].replace("\"", "");
                        lines[i] = lines[i].replace(",", "");
                        lines[i] = lines[i].replace(".", "");
                }
                        String[] arr = lines[2].split(" "); 
                        for (int i = 2; i <arr.length; i = i + 7){
                                lines[i] = (arr[1] + " " + arr[2] + " " + arr[5]);
                        }
                        
                        Sentence splitSentence = new Sentence(lines[5], lines[4], lines[2]);
                        return splitSentence;
        }

        public ArrayList<String> splitSentence() {
                ArrayList<String> returnValue = new ArrayList<String>();
                //create array of sentence words
                String[] wordArray = this.getText().split(" ");
                String[] stopwords = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", 
                "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", 
                "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", 
                "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", 
                "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", 
                "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", 
                "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", 
                "our", "ours ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", 
                "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", 
                "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", 
                "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't", 
                "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", 
                "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", 
                "yourselves"};
                
                //remove unnecessary words
                boolean flag = true;
                String holder = "";
                for (int i = 0; i < wordArray.length; i++) {
                        for (int c = 0; c < stopwords.length; c++){
                                holder = wordArray[i].toLowerCase();
                                if (holder.equals(stopwords[c])){
                                        flag = false;
                                        break;
                                }
                        }
                                if (flag) {
                                        returnValue.add(wordArray[i].toLowerCase());
                                }
                        flag = true;
                }
                return returnValue;
        }

        
        public int getSentiment(){
                Properties props = new Properties();
                props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
                StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
                Annotation annotation = pipeline.process(this.getText());
                CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                return RNNCoreAnnotations.getPredictedClass(tree);
            }

        public boolean keep(Integer temporalRange) {
                String time1 = this.getTimestamp();
                Integer hour = temporalRange; 
                String[] tempArray = time1.split(" ");
                String time2 = tempArray[3];
                String[] hourArray = time2.split(":");
                Integer.parseInt(hourArray[0]);
                if (hour == Integer.parseInt(hourArray[0])) {
                        return true;
                } else {
                        return false;
                }
                // this is my code to complete the expirement
                // to accomplish what is set out in the part 5, I changed temporalRange to a string, I 
                // split the string into an array to extract the month and day
                // and converted the month and day into their corresponding integer values
                // and passed them through if statements to properly if they match the original 
                // temporalRange string put in (which also gets transformed into integer values). 
        }

        public String toString() {
                return ("{author:" + author + ", sentence:\"" + text + "\", timestamp:\"" + timestamp + "\"}");
        }
}

