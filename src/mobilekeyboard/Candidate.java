package mobilekeyboard;

public class Candidate implements Comparable{
	private String word;
	private int confidence;
	public Candidate(String w){
		word = w;
		confidence = 1;
	}
	public void increment(){
		confidence++;
	}
	public String getWord(){
		return word;
	}
	public int getConfidence(){
		return confidence;
	}
	@Override
	public int compareTo(Object otherObject) {
		Candidate otherText = (Candidate)otherObject;
	   if (this.confidence< otherText.getConfidence()){
		   return 1;
	   }
	   else if (this.confidence>otherText.getConfidence()){
		   return -1;
	   }
	   return 0;
	}
	public String toString(){
		return word + " ("+confidence+")";
	}
}
