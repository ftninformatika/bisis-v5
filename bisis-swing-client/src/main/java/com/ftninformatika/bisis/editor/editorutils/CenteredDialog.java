package com.ftninformatika.bisis.editor.editorutils;

import java.awt.Frame;
import java.awt.Dimension;
import javax.swing.*;



public class CenteredDialog extends JDialog {

	
	/**
	 * This is the default constructor
	 */
	public CenteredDialog() {
		super();		
		
	}
	
	public CenteredDialog(Frame owner, boolean modal) {
		super(owner,modal);		
		
	}
  
  public CenteredDialog(Frame owner) {
    super(owner,true);   
    
  }
  
	public void setVisible(boolean visible){
		if(visible){
			Dimension screen = getToolkit().getScreenSize();
		    setLocation((screen.width - getSize().width) / 2,
		      (screen.height - getSize().height) / 2);
		}
	  super.setVisible(visible);
	}
	
	
}
