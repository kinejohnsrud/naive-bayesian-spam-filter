package bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Bayes {
	HashMap<String, Word> words = new HashMap<String,Word>();
	
	
	public static void main(String[] args) {
		Bayes run = new Bayes();
		try {
			run.train("/Users/Kine/Documents/GitHub/naive-bayesian-spam-filter/spam-filter/src/train.txt");
			run.filter("/Users/Kine/Documents/GitHub/naive-bayesian-spam-filter/spam-filter/src/testFile.txt");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("AN ERROR HAS OCCURED");
		}		
	}
	
	public void train(String input) throws IOException{
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
		   // System.out.println(words.get(key).getProbability());
		}
	}
	
	public void filter(String inputFile) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		String line = in.readLine();
		while (line != null){
			if (!line.equals("")){
				ArrayList<Word> sms = makeWordList(line);
				boolean isSpam = calculateBayes(sms);
				System.out.println(isSpam);	
			}
		}
		in.close();

	}

	public ArrayList<Word> makeWordList(String sms){
		ArrayList<Word> interestingList = new ArrayList<Word>();
		for (String word : sms.split(" ")){
			word = word.replaceAll("\\W", "");
			word = word.toLowerCase();
			Word w = null;
			if(words.containsKey(word)){
				w = (Word) words.get(word);
			}
			else {
				w = new Word(word);
				w.setProbOfSpam(0.40f);
			}
			interestingList.add(w);
			// Sort and shorten list based on interesting value
			
		}
		return interestingList;
	}
	public boolean calculateBayes(ArrayList<Word> sms){
		//Applying Bayes rule
		float probabilityOfPositiveProduct = 1.0f;
		float probabilityOfNegativeProduct = 1.0f;
		for (int i = 0; i < sms.size(); i++) {
			Word word = (Word) sms.get(i);
			probabilityOfPositiveProduct *= word.getProbOfSpam();
			probabilityOfNegativeProduct *= (1.0f - word.getProbOfSpam());
		}
		float probOfSpam = probabilityOfPositiveProduct / (probabilityOfPositiveProduct + probabilityOfNegativeProduct);
		if(probOfSpam > 0.9f) return true;
		else return false;
	}
	
	
}
