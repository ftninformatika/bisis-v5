/**
 * 
 */
package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.bisis.editor.editorutils.CodeChoiceDialog;
import com.ftninformatika.bisis.format.UItem;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author dimicb
 *
 */
public class CodedPrefPanel extends JPanel {
	
	private JTextField txtFld = new JTextField();
	private JButton button = new JButton();
	private String pref = "";

	public CodedPrefPanel() {
		button.setIcon(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
		button.setPreferredSize(new Dimension(20,20));
		//button.setPreferredSize(new Dimension(1,2));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0,0,0,5);		
		c.fill = GridBagConstraints.BOTH;
		add(txtFld, c);
		c.insets = new Insets(0,0,0,0);		
		c.gridx = 1;
		c.weightx = 0.001;	
		c.weighty = 0;	
		add(button, c);
		
		//actions		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {				
				openCoder();
			}			
		});
	}
	
	public void setPref(String pref){
		this.pref = pref;
	}	
	
	public void requestFocus() {
		txtFld.requestFocus();		
	}
	
	public String getText(){
		return txtFld.getText();
	}
	
	public JTextField getTextField(){
		return txtFld;
	}
	
	private void openCoder(){		
		List<UItem> codesList = CodedPrefUtils.getCodesForPrefix(pref);
		if(codesList!=null){
			CodeChoiceDialog ccd = new CodeChoiceDialog(BisisApp.getMainFrame(),
					Messages.getString("SEARCH_CODER"), codesList, MessageFormat.format(Messages.getString("SEARCH_CODER_FOR_PREFIX.0"), pref),"");
			ccd.setVisible(true);		
			if(ccd.getSelectedCode()!=null)
				txtFld.setText(ccd.getSelectedCode());
			//else codedCellEditor.setText("");
			ccd.setVisible(false);	
		}
	}
	
	
	
	
	

	
}
