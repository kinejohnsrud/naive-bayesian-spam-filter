package bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Bayes {
	ArrayList<String> all = new ArrayList<String>();
	ArrayList<String> spamList = new ArrayList<String>();
	ArrayList<String> hamList = new ArrayList<String>();
	
	
	public static void main(String[] args) {
		Bayes run = new Bayes();
		
		try {
			run.testFileReader("/Users/Kine/Documents/GitHub/naive-bayesian-spam-filter/spam-filter/src/train.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void testFileReader(String input) throws IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(input));
		String line = in.readLine();
		
		if(line.substring(0,line.indexOf(' ')).equals("ham")){
			hamList.add(line.substring(line.indexOf(' ')+1));
		}
		else if(line.substring(0,line.indexOf(' ')).equals("spam")){
			spamList.add(line.substring(line.indexOf(' ')+1));
		}
		
		in.close();
	}
}
