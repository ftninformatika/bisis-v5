package com.ftninformatika.bisis.editor.groupinv;

import com.ftninformatika.utils.Messages;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.utils.CenteredDialog;
import net.miginfocom.swing.MigLayout;


public class GroupInvTextDialog extends CenteredDialog {
	
	private JTextArea textArea;
	private JButton okButton;  
	private JButton cancelButton;	
	private JPanel buttonsPanel;  
	private String name;
	private String newText;
	
	
	public GroupInvTextDialog(String name){
		super(BisisApp.getMainFrame(),true);
	
		this.name = name;
		this.setSize(280, 300);		
		setTitle(MessageFormat.format(Messages.getString("CHANGE_VALUE_FOR.0"), name));
		initialize();
	}
	
	private void initialize() {
		setLayout(new MigLayout("insets 10 10 10 10","","[]"));
		textArea = new JTextArea(10,20);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane,"grow, wrap");
		okButton = new JButton();
		okButton.setSize(new java.awt.Dimension(88,26));
		okButton.setText(Messages.getString("EDITOR_BUTTONACCEPT"));
		okButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				handleOk();
			}			
		});
		okButton.addKeyListener(new KeyAdapter(){
   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });		
		cancelButton = new JButton(Messages.getString("EDITOR_BUTTONCANCEL"));
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
		     "/icons/remove.gif")));
		buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		add(buttonsPanel,"grow");
		
		
		cancelButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {				
			handleCancel();
		}			
		});
		 cancelButton.addKeyListener(new KeyAdapter(){
		   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });	
		textArea.addKeyListener(new KeyAdapter(){
		   public void keyReleased(KeyEvent e){  
		     handleKeys(e);    
		   }     
		 });	
	}
	
	private void handleCancel() {
		dispose();		
	}

	private void handleKeys(KeyEvent e) {
		 if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
	      handleCancel(); 		
	}
	
	public String getNewText(){
		return newText;
	}
	
	private void handleOk(){
		newText = textArea.getText();
		setVisible(false);
	}

}
