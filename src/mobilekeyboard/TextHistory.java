package mobilekeyboard;

import java.util.LinkedList;

public class TextHistory {
	private TextCount value;
	private TextHistory[] table;
	public TextHistory(){
		table = new TextHistory[26];
	}
	public TextHistory(String word){
		value = new TextCount(word);
		table = new TextHistory[26];
	}
	//Top level method. Used to add a word to the history
	public void addWord(String word){
		word = word.toLowerCase();
		TextHistory iterator = this;
		for (int a=0; a<word.length(); a++){
			int letter = word.charAt(a)-97;//Get the letter number
			if (a == word.length()-1){//The last letter. Word is done
				if (iterator.table[letter]==null){//The next table is not created. 
					iterator.table[letter] = new TextHistory(word);//Create the table and add the word.
				}
				else{
					iterator.table[letter].value.increment();//Increment the word count
				}
			}
			else if (iterator.table[letter]==null){//The next table is not created
				iterator.table[letter] = new TextHistory();
				iterator = iterator.table[letter];
			}
			else {
				iterator = iterator.table[letter];
			}
		}
	}
	//Top level method. Find autocompleted choices
	public LinkedList<TextCount> autocomplete(String piece){
		LinkedList<TextCount> possibilites = new LinkedList<TextCount>();
		piece = piece.toLowerCase();
		TextHistory iterator = this;
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
	public void getValues(TextHistory t,LinkedList<TextCount> p){
		for (int a = 0; a<t.table.length; a++){
			if (t.table[a] != null){//there is a value
				p.add(t.table[a].value); 
				//Do an insertion sort *Change to insertion add
				for (int i = 1; i <p.size(); i++){
					    TextCount x = p.get(i);
					    int j = i - 1;
					    while( j >= 0 && p.get(j).compareTo(x) > 0){
					        p.set(j+1, p.get(j));
					        j = j - 1;
					    }
					    p.set(j+1,x);
				}
				getValues(t.table[a],p);
			}
		}
	}
}
