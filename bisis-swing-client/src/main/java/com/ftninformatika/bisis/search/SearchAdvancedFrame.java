package com.ftninformatika.bisis.search;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import com.ftninformatika.bisis.editor.Messages;
import net.miginfocom.swing.MigLayout;

public class SearchAdvancedFrame extends JInternalFrame{
	
	public  SearchAdvancedFrame() {
		super(Messages.getString("SEARCH_ADVANCED_SEARCH"),true, true, false, true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		panel.setLayout(layout);
		btnOK.setText(Messages.getString("SEARCH"));
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bOK_actionPerformed(e);
			}
		});
		btnCancel.setText(Messages.getString("SEARCH_CANCEL"));
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bCancel_actionPerformed(e);
			}
	    });
		
	    taQuery.setLineWrap(true);
	    taQuery.addKeyListener(new java.awt.event.KeyAdapter() {
	    	public void keyReleased(KeyEvent e) {
	    		taQuery_keyReleased(e);
	    	}
	    });
		getContentPane().add(panel);
		
		panel.add(scrollPane,"wrap, grow, span 3");
		panel.add(btnOK,"gapleft 80");
		panel.add(btnCancel);	
		setSize(400, 200);
		getRootPane().setDefaultButton(btnOK);
		
	}

    void bOK_actionPerformed(ActionEvent e) {
	    //query = LatCyrUtils.toLatin(taQuery.getText().trim());
        SearchStatusDlg statusDlg = new SearchStatusDlg();
    	
  	 	SearchTask task = new SearchTask(query,statusDlg);
  	 	statusDlg.addActionListener(task);
  	 	task.execute();
  	 	statusDlg.setVisible(true);
  	 	btnOK.setEnabled(true);
    }
    
    void bCancel_actionPerformed(ActionEvent e) {
    	query = "";
    	taQuery.setText("");
    	setVisible(false);
	}
    void taQuery_keyReleased(KeyEvent e) {
    	if (e.getKeyCode() == KeyEvent.VK_ENTER)
    		btnOK.doClick();
    	else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    		btnCancel.doClick();
    }
  JPanel panel = new JPanel();
  
  JLabel lSelect = new JLabel();
  MigLayout layout = new MigLayout("","[] 20 [] []","");
  JTextArea taQuery = new JTextArea(7,400);
  JScrollPane scrollPane=new JScrollPane(taQuery);
  JButton btnOK = new JButton();
  JButton btnCancel = new JButton();
  int []hits;
  //Result queryResult;
  String query = "";

}
