package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;





public class AddUFieldDialog extends CenteredDialog {

	private JButton okButton = null;  
	private JButton cancelButton = null;  
	private JPanel buttonsPanel = null; 
	private JPanel jContentPane = null; 
	
	private JList fList = new JList();
	private JList sfList = new JList();	
	
	private JScrollPane listScrollPane;
	private DefaultListModel sfListmodel = new DefaultListModel();
	private DefaultListModel fListModel = new DefaultListModel();
	
	private UElementListCellRenderer renderer = new UElementListCellRenderer();	
	
	private JLabel fLabel = new JLabel();
	private JLabel sfLabel = new JLabel();

	private String title = Messages.getString("EDITOR_ADDFIELDFRAMETITLE"); //$NON-NLS-1$
	
	private UField chosenUField = null;

	public AddUFieldDialog(Frame owner) {
		super(owner,true);		
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {		
		this.setSize(473, 500);		
		this.setTitle(title);
		this.setContentPane(getJContentPane());		
		getRootPane().setDefaultButton(okButton);		
	}
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {				
			jContentPane = new JPanel();			
			fList.setModel(fListModel);	
			fList.setCellRenderer(renderer);
			createFieldList();
			JScrollPane fListScroll = new JScrollPane(fList);	
			
			sfList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			sfList.setModel(sfListmodel);
			sfList.setCellRenderer(renderer);
			createSfList((UField)fList.getSelectedValue());
      sfList.setSelectedIndex(0);
			sfLabel = new JLabel(Messages.getString("EDITOR_LABELSUBFIELDS")); //$NON-NLS-1$
			listScrollPane = new JScrollPane(sfList);
			buttonsPanel = new JPanel();
			okButton = new JButton(Messages.getString("EDITOR_BUTTONACCEPT")); //$NON-NLS-1$
			okButton.setIcon(new ImageIcon(getClass().getResource(
	        "/icons/ok.gif")));		 //$NON-NLS-1$
			cancelButton = new JButton(Messages.getString("EDITOR_CANCEL")); //$NON-NLS-1$
			cancelButton.setIcon(new ImageIcon(getClass().getResource(
	        "/icons/remove.gif")));		 //$NON-NLS-1$
			
			// components laying out
			
			jContentPane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();			
			c.gridx = 0;
			c.gridy = 0;			
			c.anchor = GridBagConstraints.LINE_START;			
			c.insets = new Insets(20,20,10,20);	
			jContentPane.add(new JLabel(Messages.getString("EDITOR_LABELFIELDS")), c); //$NON-NLS-1$
			c.anchor = GridBagConstraints.CENTER;
			c.weighty = 1;	
			c.weightx = 1;
			c.gridy = 1;
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(10,20,10,20);	
			jContentPane.add(fListScroll,c);
			
			c.weighty = 0;	
			c.weightx = 0;	
			c.gridy = 2;			
			c.insets = new Insets(10,20,0,30);		
			jContentPane.add(fLabel, c);
			
			c.gridy = 3;
			c.insets = new Insets(10,20,0,0);
			c.anchor = GridBagConstraints.LINE_START;			
			jContentPane.add(sfLabel,c);			
			
			c.gridy = 4;			
			c.insets = new Insets(10,20,20,20);		
			c.anchor = GridBagConstraints.CENTER;
			c.weightx = 0.6;
			c.weighty = 0.5;			
			jContentPane.add(listScrollPane, c);			
			
			buttonsPanel.add(okButton,null);
			buttonsPanel.add(cancelButton,null);
			
			c.gridy = 5;			
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5,10,10,10);			
			c.weightx = 0;
			c.weighty = 0;				
			jContentPane.add(buttonsPanel, c);	
			
			// action listeners
			fList.addListSelectionListener(new javax.swing.event.ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {
						setAddUsubfieldPanel();		
					}		
				});
      fList.addKeyListener(new KeyAdapter(){
        public void keyReleased(KeyEvent e){  
          handleKeys(e);    
        }     
      });
      sfList.addKeyListener(new KeyAdapter(){
        public void keyReleased(KeyEvent e){  
          handleKeys(e);    
        }     
      });
      
			cancelButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					cancelAction();
				}
			});
      cancelButton.addKeyListener(new KeyAdapter(){
        public void keyReleased(KeyEvent e){  
          handleKeys(e);    
        }     
      });
			okButton.addKeyListener(new KeyAdapter(){
        public void keyReleased(KeyEvent e){  
          handleKeys(e);    
        }     
      });
			okButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					okAction();
				}
			});
		}
		return jContentPane;
	}
	
	protected void handleKeys(KeyEvent e) {
    if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
      cancelAction();    
  }

  private void createFieldList(){
		fList.removeAll();		
		List<UField> l = CurrFormat.returnMissingUFields();
		for(int i=0;i<l.size();i++){
			fListModel.addElement(l.get(i));			
		}
		fList.setSelectedIndex(0);
	}
	
	private void createSfList(UField f){
		fLabel.setText(Messages.getString("EDITOR_LABELFIELD")+f.getName()+"-"+f.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
		sfList.removeAll();
		sfListmodel.removeAllElements();
		List<USubfield> l = f.getSubfields();
		for(int i=0;i<l.size();i++){
			sfListmodel.addElement(l.get(i));
		}		
	}	
	
	public void setAddUsubfieldPanel(){		
		createSfList((UField)fList.getSelectedValue());
    sfList.setSelectedIndex(0);
	}
	
	public void cancelAction(){
		chosenUField = null;
		dispose();		
	}
	
	public void okAction(){
		prepareUField();
		setVisible(false);
	}
	
	public void prepareUField(){
		chosenUField = new UField();
		chosenUField = ((UField)fList.getSelectedValue()).shallowCopy();
		List selectedSf = new ArrayList();
		Object[] sel = sfList.getSelectedValues();
		for (int i=0;i<sel.length;i++){			
			selectedSf.add(((USubfield)sel[i]).shallowCopy());
		}
		chosenUField.setSubfields(selectedSf);		
	}

	/**
	 * @return Returns the chosenUField.
	 */
	public UField getChosenUField() {
		return chosenUField;
	}
	
	
	
	
	
}
