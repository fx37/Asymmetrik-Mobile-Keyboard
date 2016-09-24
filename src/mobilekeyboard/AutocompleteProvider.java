package mobilekeyboard;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class AutocompleteProvider {
	private Canidate value;
	private AutocompleteProvider[] table;
	public AutocompleteProvider(){
		table = new AutocompleteProvider[26];
	}
	public AutocompleteProvider(String word){
		value = new Canidate(word);
		table = new AutocompleteProvider[26];
	}
	private void setValue(String word){
		value = new Canidate(word);
	}
	//Top level method. Parse input string. Add each word to the history
	public void train(String input){
		String[] words = input.split("[^a-zA-Z']+");
		for (int i = 0; i<words.length; i++){
			this.addWord(words[i]);
		}
	}
	//Top level method. Used to add a word to the history
	public void addWord(String word){
		word = word.toLowerCase();
		AutocompleteProvider iterator = this;
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
			else {
				iterator = iterator.table[letter];
			}
		}
	}
	//Top level method. Find autocompleted choices
	public PriorityQueue<Canidate> getWords(String piece){
		PriorityQueue<Canidate> possibilites = 
	            new PriorityQueue<Canidate>();
		AutocompleteProvider iterator = this;
		for (int a=0; a<piece.length(); a++){
			int letter = piece.charAt(a)-97;//Get the letter number
			if (iterator.table[letter]==null){//No auto complete suggestions. Return null
				return null;
			}
			else if (a == piece.length()-1){//The last letter. Piece is done
				getValues(iterator.table[letter],possibilites);
			}
			else {
				iterator = iterator.table[letter];
			}
		}
		return possibilites;
	}
	//traverse and add the possibilities to the list
	private void getValues(AutocompleteProvider t,PriorityQueue<Canidate> p){
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
