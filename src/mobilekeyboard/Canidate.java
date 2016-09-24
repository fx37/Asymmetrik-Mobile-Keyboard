package mobilekeyboard;

public class Canidate implements Comparable{
	private String word;
	private int confidence;
	public Canidate(String w){
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
		Canidate otherText = (Canidate)otherObject;
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
