package com.ftninformatika.bisis.search;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import com.ftninformatika.bisis.BisisApp;
import com.gint.util.gui.WindowUtils;

import net.miginfocom.swing.MigLayout;

public class SearchStatusDlg extends JDialog {

	  public SearchStatusDlg() {
		  super(BisisApp.getMainFrame(), "Info", true);
	        setSize(250,150);
		    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		    progressBar.setMinimum(0);
		    progressBar.setIndeterminate(true);
		    progressBar.setMinimumSize(new Dimension(200,5));
		    cancelBtn.setIcon(new ImageIcon(getClass().getResource(
		        "/icons/remove.gif")));
		   
		    MigLayout mig = new MigLayout(
		        "",
		        "[]",
		        "[]para[]para[]");
		    setLayout(mig);
		    add(new JLabel("<html> <b>Pretraga je u toku...</html>"), "center, wrap");
		    add(progressBar, "span 2, center, growx, wrap");
		    add(cancelBtn, "span 2, split 2, tag cancel");
			 pack();
		    WindowUtils.centerOnScreen(this);
		 
	  }
	  public void addActionListener(final SearchTask task){
			
		  cancelBtn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					 setVisible(false);
					task.setIsCancelled(true);
				}});
	  
	  }

	  private JProgressBar progressBar = new JProgressBar();
	  private JButton cancelBtn=new JButton("Odustani");

	}
