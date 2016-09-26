package mobilekeyboard;

public class Candidate implements Comparable{
	private String word; //Text of the Candidate
	private int confidence; // confidence/number of occurances of the Candidate

	public Candidate(String w){
		word = w;
		confidence = 1; //New words start with a confidence value of one
	}
	
	/**
	 * Increment the confidence of a Candidate
	 */
	public void increment(){
		confidence++;
	}
	
	/**
	 *  Returns the word text of Candidate
	 * @return  		   Word text of Candidate
	 */
	public String getWord(){
		return word;
	}
	
	/**
	 *  Returns the confidence of Candidate
	 * @return  		   Confidence of Candidate
	 */
	public int getConfidence(){
		return confidence;
	}
	@Override
	/**
	 *  Returns the comparison of two Candidate confidence
	 *
	 * @param  otherObject Candidate to compare to 
	 * @return  		   1 If otherObject occurs more
	 *   				   0 If otherObject occurs same
	 *   				   -1 If otherObject occurs less
	 */
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
	
	/**
	 *  Returns the toString format of Candidate: The word plus the confidence
	 *  Ex: Hello (3)
	 * @return  		   toString format of Candidate
	 */
	public String toString(){
		return word + " ("+confidence+")";
	}
}
