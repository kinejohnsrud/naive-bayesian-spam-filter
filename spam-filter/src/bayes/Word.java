package bayes;

public class Word {
	private String word;	//the word itself
	private int spamCount;	//number of this words appearances in spam messages
	private int hamCount;	//number of this words appearances in ham messages
	private float spamRate;	//spamCount divided by total spam count
	private float hamRate;	//hamCount divided by total ham count
	private float probOfSpam;	//probability of word being spam
	
	public Word(String word){
		this.word = word;
		spamCount = 0;
		hamCount = 0;
		spamRate = 0.0f;
		hamRate = 0.0f;
		probOfSpam = 0.0f;	
	}
	
	public void countSpam(){
		spamCount++;
	}
	
	public void countHam(){
		hamCount++;
	}
	
	//calculates the probability of spam, 
	//and gives the smallest and biggest probabilities more precedence
	public void calculateProbability(int totSpam, int totHam){
		spamRate = spamCount / (float) totSpam;
		hamRate = hamCount / (float) totHam;
		
		if(spamRate + hamRate > 0){
			probOfSpam = spamRate / (spamRate + hamRate);
		}
		if(probOfSpam < 0.01f){
			probOfSpam = 0.01f;
		}
		else if(probOfSpam > 0.99f){
			probOfSpam = 0.99f;
		}
	}
	
	public float getProbability(){
		return this.probOfSpam;
	}
	
	
	//The interesting rate is how far the probability is from 0.5 i.e. 50/50 chance.
	public float interestingRate(){
		return Math.abs(0.5f - probOfSpam);
	}
	

}
