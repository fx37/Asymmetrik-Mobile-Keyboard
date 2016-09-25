MobilrKeyboard
By, Winston Tong

This is my implementation of the Mobile Device Keyboard challenged by Asymmetrik.

I used Java Swing to implement the UI.

Interface Specification

Candidate
    String getWord() : returns the autocomplete candidate
    Integer getConfidence() : returns the confidence* for the candidate

AutocompleteProvider
    PriorityQueue<Candidate> getWords(String fragment) : returns list of candidates ordered by confidence*
    void train(String passage) : trains the algorithm with the provided passage

Note:
One modification I've made is returning a PriorityQueue of candidates as opposed to an ordered list during getWords(). In order to provide a list of ordered canidates, you need to remove the canidates off the queue one by one. (The efficiency of the PriorityQueue vs sorting the list is comparable).
However, in the context of obtaining autocompletion of a mobile device, we would want to provide only the most X confident canidates. A PriorityQueue would be able to provide these number of canidates effciently. 

I implemented a tree like structure to contain the text history in order to focus on speed.
As a result, searching for the partial word would take O(N) time where n is the length of the partial word.
Returning all of the canidates for a partial word composes of simiply searching all the children with values of the partial word.

The program can be run through the jar file.
java -jar MobileKeyboard.jar

To compile and run the code yourself:
1. Download the code off git
2. Compile the code: javac *.java
3. Run MobileKeyboard: cd ..; java mobilekeyboard.MobileKeyboard

Future Improvements:
Storing using an ordered map as opposed to the 26 character array would improve efficiency and space usage.
