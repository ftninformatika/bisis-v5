package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;import java.util.ArrayList;
import java.util.List;

public class AddUSubfieldDialog extends CenteredDialog {
	
	// clasa jos nije u funkciji 
	private String title = Messages.getString("EDITOR_ADD_SUBFIELD_FORMAT");
	private UField ownerField;
	private JList sfList = new JList();	
	private UElementListCellRenderer renderer = new UElementListCellRenderer();	
	private DefaultListModel sfListmodel = new DefaultListModel();
	private JScrollPane sfListScrollPane;
	
	private JPanel buttonsPanel = null; 
	private JButton okButton = null;  
	private JButton cancelButton = null;  
	private JLabel fLabel = null;
	
	private List<USubfield> chosenSubfields;

	public AddUSubfieldDialog() {
		super();		
	}

	public AddUSubfieldDialog(Frame owner, UField uf){
		super(owner,true);
		ownerField = uf;		
		initialize();		
	}
	
	private void initialize(){
		setSize(473, 300);		
		setTitle(title);
    getRootPane().setDefaultButton(okButton);	
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		fLabel = new JLabel(MessageFormat.format(Messages.getString("EDITOR_ADD_SUBFIEDL"), ownerField.getName(), ownerField.getDescription()));
		c.gridx = 0;
		c.gridy = 0;			
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(10,10,5,10);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;			
		this.getContentPane().add(fLabel, c);
		
		sfList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sfList.setModel(sfListmodel);
		sfList.setCellRenderer(renderer);
		createSfList();
		sfListScrollPane = new JScrollPane(sfList);
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.6;
		c.weighty = 0.5;
		this.getContentPane().add(sfListScrollPane,c);
		
		buttonsPanel = new JPanel();
		okButton = new JButton(Messages.getString("EDITOR_BUTTONACCEPT"));
		okButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
		cancelButton = new JButton(Messages.getString("EDITOR_BUTTONCANCEL"));
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
		buttonsPanel.add(okButton,null);
		buttonsPanel.add(cancelButton,null);		
		c.gridy = 2;			
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5,10,10,10);			
		c.weightx = 0;
		c.weighty = 0;				
		this.getContentPane().add(buttonsPanel, c);
		
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
    
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				okAction();
			}
		});
    
    okButton.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });
    sfList.addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });
		
	}
	
	protected void handleKeys(KeyEvent e) {
    switch(e.getKeyCode()){
      case(KeyEvent.VK_ESCAPE):
        cancelAction();
        break;
      case(KeyEvent.VK_ENTER):
        okButton.doClick();        
    }
    
  }

  private void createSfList(){		
		sfList.removeAll();
		sfListmodel.removeAllElements();
		List<USubfield> l = CurrFormat.returnMissingUSubfields(ownerField);
		for(int i=0;i<l.size();i++){
			sfListmodel.addElement(l.get(i));
		}		
	}	
	
	public void cancelAction(){
		dispose();		
	}
	
	public void okAction(){
		prepareUSubfields();
		setVisible(false);
	}
	
	private void prepareUSubfields(){
		chosenSubfields = new ArrayList<USubfield>();
		Object[] sel = sfList.getSelectedValues();		
		for(int i=0;i<sel.length;i++){
			chosenSubfields.add((USubfield)sel[i]);
		}		
	}
	
	public boolean hasUSubfieldSelected(){
		return chosenSubfields!=null;
	}
	
	public List<USubfield> getChosenSubfields(){	
		return chosenSubfields;	
	}

}
