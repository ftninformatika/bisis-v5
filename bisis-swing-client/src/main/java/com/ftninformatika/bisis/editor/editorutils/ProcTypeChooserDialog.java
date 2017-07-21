/**
 * 
 */
package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.formattree.FormatUtils;
import com.ftninformatika.bisis.libenv.ProcessTypeListCellRenderer;
import com.ftninformatika.bisis.libenv.ProcessTypeListModel;
import com.ftninformatika.bisis.librarian.ProcessType;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


/**
 * @author dimicb
 *
 */
public class ProcTypeChooserDialog extends CenteredDialog {
	
	private JList procTypeList;
	private ProcessTypeListModel procTypeListModel;	
	private JScrollPane scrollPane;
	private JButton okButton = null;  
	private JButton cancelButton = null;	
	private String labelString;
	
	private ProcessType selectedProcessType;
	
	public ProcTypeChooserDialog(){
		this.setSize(473, 400);		
		this.setTitle("Promena tipa obrade");		
		initializeList();	
		labelString = "Tipovi obrade za bibliotekara: \n"
			+ BisisApp.appConfig.getLibrarian().getUsername();
		okButton = new JButton("Potvrdi");		
		okButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));
		
		cancelButton = new JButton("Odustani");
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/remove.gif")));	
		layoutPanels();
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleCloseDialog();				
			}			
		});
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				handleSetSelectedProcessType();				
			}			
		});	
	}
	
	public ProcessType getSelectedProcessType(){
		return selectedProcessType;
	}
	

	private void initializeList(){
		procTypeListModel = new ProcessTypeListModel(
				FormatUtils.getProcessTypeGroup(BisisApp.appConfig.getLibrarian()),
				BisisApp.appConfig.getLibrarian().getCurentProcessType()/*getContext().getDefaultProcessType()*/);//TODO-????
		procTypeList = new JList(procTypeListModel);
		ProcessTypeListCellRenderer renderer = new ProcessTypeListCellRenderer();
		procTypeList.setCellRenderer(renderer);
		scrollPane = new JScrollPane(procTypeList);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	private void handleCloseDialog(){
		dispose();
	}
	
	private void handleSetSelectedProcessType(){
		if(procTypeList.getSelectedValue()!=null){
			selectedProcessType = (ProcessType) procTypeList.getSelectedValue();
			Obrada.editorFrame.handleChangeProcessType(selectedProcessType);
		}
		setVisible(false);
	}
	
	private void layoutPanels() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 0.1;
		c.insets = new Insets(10,10,5,10);
		add(new JLabel(labelString),c);
		c.gridy = 1;
		c.weighty = 0.8;
		add(scrollPane,c);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		c.gridy = 2;
		c.weighty = 0.1;
		add(buttonsPanel,c);		
	}
	
}
