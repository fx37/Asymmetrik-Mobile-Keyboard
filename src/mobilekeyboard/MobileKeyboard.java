package mobilekeyboard;

import javax.swing.*;        
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MobileKeyboard {
	private TextHistory history;
	public MobileKeyboard(){
		history = new TextHistory();
	}

    private void createAndShowUI() {
        final JFrame frame = new JFrame("VirtualKeyboard");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents(frame);

        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);
    }

    private void initComponents(JFrame frame) {
    	JTextField input = new JTextField(20);
        JTextArea historyText = new JTextArea(20,20);
        JButton submit = new JButton("Submit");
        JPanel topPanel = new JPanel();
        JPanel results = new JPanel();
        JLabel headerLabel = new JLabel("Input");
        results.setLayout(new GridLayout(0, 5));
        
        historyText.setEditable(false);

        input.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
            	historyText.setText(input.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
            	historyText.setText(input.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            //Plain text components don't fire these events.
            	
            }
        });

        submit.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	  {
        	   //submit pressed
        		
        	  }
        });
        
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        topPanel.add(headerLabel, BorderLayout.WEST);
        topPanel.add(input, BorderLayout.CENTER);
        topPanel.add(submit, BorderLayout.EAST);
        frame.getContentPane().add(results, BorderLayout.CENTER);
        frame.getContentPane().add(historyText, BorderLayout.SOUTH);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MobileKeyboard().createAndShowUI();
            }
        });
    }
}