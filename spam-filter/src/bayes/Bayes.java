package bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Bayes {
	ArrayList<String> all = new ArrayList<String>();
	ArrayList<String> spamList = new ArrayList<String>();
	ArrayList<String> hamList = new ArrayList<String>();
	HashMap<String, Word> words = new HashMap<String,Word>();
	
	
	public static void main(String[] args) {
		Bayes run = new Bayes();
		try {
			run.testFileReader("/Users/Kine/Documents/GitHub/naive-bayesian-spam-filter/spam-filter/src/train.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void testFileReader(String input) throws IOException{
		int totalSpamCount = 0;
		int totalHamCount = 0;
		BufferedReader in = new BufferedReader(new FileReader(input));
		String line = in.readLine();
		while (line != null){
			if (!line.equals("")){
				String type = line.split("\t")[0];
//				System.out.println("Type: " + type);
				String sms = line.split("\t")[1];
//				System.out.println("SMS: " + sms);
				
				for (String word : sms.split(" ")){
					word = word.replaceAll("\\W", "");
					word = word.toLowerCase();
					Word w = null;
					if(words.containsKey(word)){
						w = (Word) words.get(word);
					}
					else {
						w = new Word(word);
						words.put(word,w);
					}
					if(type.equals("ham")){
						w.countHam();
						totalHamCount++;
					}
					else if(type.equals("spam")){
						w.countSpam();
						totalSpamCount++;
					}		
				}
			}
			line = in.readLine();	
		}
		in.close();
		
		for (String key : words.keySet()) {
		    words.get(key).calculateProbability(totalSpamCount, totalHamCount);
		    System.out.println(words.get(key).getProbability());
		}

		
	}
}
