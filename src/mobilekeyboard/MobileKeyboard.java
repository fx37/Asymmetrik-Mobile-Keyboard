package mobilekeyboard;

import javax.swing.*;        
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.awt.BorderLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MobileKeyboard {
	private AutocompleteProvider history;
	
	//Create UI
    private void createAndShowUI() {
        final JFrame frame = new JFrame("VirtualKeyboard");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents(frame);

        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
    }

    //Fill the frame. Add respective action listeners
    private void initComponents(JFrame frame) {
    	JTextField input = new JTextField(50);
        JTextArea historyText = new JTextArea(50,50);
        JButton submit = new JButton("Submit");
        JPanel topPanel = new JPanel();
        JPanel results = new JPanel();
        JLabel headerLabel = new JLabel("Input");
        results.setLayout(new GridLayout(0, 5));
        history = new AutocompleteProvider();
        
        historyText.setEditable(false);

        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            //A character is added to the input. Provide autocompletion
            public void insertUpdate(DocumentEvent de) {
            	String in = input.getText().toLowerCase();
            	String[] words= in.split("[^a-z']+");

            	if (in.length()!=0 && in.charAt(in.length()-1)>='a' && in.charAt(in.length()-1)<='z'){//make sure we start on an alpha
            		PriorityQueue<Candidate> suggestions;
            		//System.out.println("Look for: "+words[words.length-1]);
            		suggestions = history.getWords(words[words.length-1]); //Get suggestions
            		if (suggestions!=null){
            			results.removeAll();//Remove old suggestions
            			while(suggestions.size()!=0){
            				//System.out.println("I SUGGEST:" +t.toString());
            				//Add suggestions to the GUI
            				results.add(new JLabel(suggestions.remove().toString(),JLabel.CENTER));
            			}
            			frame.pack();
            			results.revalidate(); 
            			results.repaint();
            		}
            	}
            	else{//No suggestions - remove old suggestions
            		results.removeAll();
            		frame.pack();
        			results.revalidate(); 
        			results.repaint();
            	}
            }

            @Override
          //A key is removed. Reobtain suggestions for new word
            public void removeUpdate(DocumentEvent de) {
            	insertUpdate(de);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            //Plain text components don't fire these events.
            	
            }
        });
        
        //When Enter is pressed
        input.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	  {
        		historyText.setText(historyText.getText()+input.getText()+"\n");
        		history.train(input.getText());
        		input.setText("");
        	  }
        });

        //Submit pressed
        submit.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	  {
        		historyText.setText(historyText.getText()+input.getText()+"\n");
        		history.train(input.getText());
        		input.setText("");
        	  }
        });
        		
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.add(headerLabel, BorderLayout.WEST);
        topPanel.add(input, BorderLayout.CENTER);
        topPanel.add(submit, BorderLayout.EAST);
        frame.getContentPane().add(results, BorderLayout.CENTER);
        frame.getContentPane().add(historyText, BorderLayout.SOUTH);
    }
    
    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MobileKeyboard().createAndShowUI();
            }
        });
    }
}
