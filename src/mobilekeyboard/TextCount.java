package mobilekeyboard;

public class TextCount implements Comparable{
	private String word;
	private int occurance;
	public TextCount(String w){
		word = w;
		occurance = 1;
	}
	public void increment(){
		occurance++;
	}
	public String getWord(){
		return word;
	}
	public int occurance(){
		return occurance;
	}
	@Override
	public int compareTo(Object otherObject) {
		TextCount otherText = (TextCount)otherObject;
	   if (this.occurance< otherText.occurance){
		   return 1;
	   }
	   else if (this.occurance>otherText.occurance){
		   return -1;
	   }
	   return 0;
	}
	public String toString(){
		return word + " ("+occurance+")";
	}
}
