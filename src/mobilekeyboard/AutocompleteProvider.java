package mobilekeyboard;

import java.util.LinkedList;
import java.util.PriorityQueue;

//Tree-like structure which creates nodes leading up to a completed value which was trained on
public class AutocompleteProvider {
	private Candidate value;
	private AutocompleteProvider[] table;
	
	
	public AutocompleteProvider(){
		table = new AutocompleteProvider[26]; //Create empty table
	}
	
	public AutocompleteProvider(String word){
		value = new Candidate(word); //Set value = the Candidate
		table = new AutocompleteProvider[26]; //Create empty table
	}
	
	/**
	 * Set the text value of the node
	 * @param  word    The text value
	 */
	private void setValue(String word){
		value = new Candidate(word);
	}
	
	/**
	 * Parse the training text into alpha words. Add each word to the data structure
	 * @param  passage    The training text
	 */
	public void train(String passage){
		String[] words = passage.split("[^a-zA-Z']+");
		for (int i = 0; i<words.length; i++){
			//System.out.println(words[i]);
			this.addWord(words[i]);
		}
	}
	/**
	 * Add a word to the data structure
	 * @param  word    The word to add to the data structure
	 */
	public void addWord(String word){
		word = word.toLowerCase();
		AutocompleteProvider iterator = this;
		//Traverse the tables. Adding new tables as needed. Store/increment the word as a Candidate and the lowest level
		for (int a=0; a<word.length(); a++){
			int letter = word.charAt(a)-97;//Get the letter number
			if (a == word.length()-1){//The last letter. Word is done
				if (iterator.table[letter]==null){//The next table is not created. 
					iterator.table[letter] = new AutocompleteProvider(word);//Create the table and add the word.
				}
				else if(iterator.table[letter].value == null){//the table exists but word not set
					iterator.table[letter].setValue(word);
				}
				else{
					iterator.table[letter].value.increment();//Increment the word count
				}
			}
			else if (iterator.table[letter]==null){//The next table is not created
				iterator.table[letter] = new AutocompleteProvider();
				iterator = iterator.table[letter];
			}
			else {//Traverse to the next table
				iterator = iterator.table[letter];
			}
		}
	}
	/**
	 * Find the autocompletion candidates for the fragment
	 * @param  fragment    The text to perform autocompletion on
	 * @return  		   Candidates of the fragment in a PriorityQueue
	 */
	public PriorityQueue<Candidate> getWords(String fragment){
		PriorityQueue<Candidate> possibilites = 
	            new PriorityQueue<Candidate>();
		AutocompleteProvider iterator = this;
		for (int a=0; a<fragment.length(); a++){
			int letter = fragment.charAt(a)-97;//Get the letter number
			if (iterator.table[letter]==null){//No auto complete suggestions. Return null
				return null;
			}
			else if (a == fragment.length()-1){//The last letter. Piece is done
				//Fill queue with Candidates
				getValues(iterator.table[letter],possibilites);
			}
			else {//Traverse to next table
				iterator = iterator.table[letter];
			}
		}
		return possibilites;
	}
	
	/**
	 * Recursive method with traverses the AutocompleteProvider and adds Candidates to the PriorityQueue
	 * @param  AutocompleteProvider t      The node of the AutocompleteProvider to test
	 * 		   PriorityQueue<Candidate> p  Where Candidates are stored
	 */
	private void getValues(AutocompleteProvider t,PriorityQueue<Candidate> p){
		if (t!=null){
			for (int a = 0; a<t.table.length; a++){
				if (t.table[a] != null){//there is a value/deeper value
					if(t.table[a].value!=null){
						p.add(t.table[a].value); 
						//System.out.println(t.table[a].value.toString());
					}
					getValues(t.table[a],p);
				}
			}
		}
	}
}
