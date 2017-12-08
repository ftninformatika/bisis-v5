package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.format.UItem;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class CodeChoiceDialog extends CenteredDialog {

	private JPanel jContentPane = null;  
	private JLabel fieldLabel = null;
	private JLabel indicatorNameLabel = null;
//	private CodesList codesList = null;
	private JButton okButton = null;  
	private JButton cancelButton = null;	
	private JPanel buttonsPanel = null;  
	private JScrollPane jScrollPane = null;  
	private FilteredJList filteredJList = null;
	
	//private JTextField filterTextField = null;
	
	private String title = null;
	private List<UItem> cList = null;
	private String ownerString = null;
	private String nameString = null;
	
	private String selectedCode = null;
	/**
	 * This is the default constructor
	 */
	public CodeChoiceDialog() {
		super();
		initialize();
	}
	
	public CodeChoiceDialog(Frame owner,String title, List<UItem> cList,String ownerString,String nameString) {		
		super(owner,true);
		this.title = title;
		this.cList = cList;
		this.ownerString = ownerString;
		this.nameString = nameString;		
		initialize();
	}
	
	public String getSelectedCode(){		
		return selectedCode;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {		
		this.setSize(373, 400);		
		this.setTitle(title);	
		filteredJList = new FilteredJList();
	
	//	codesList = new CodesList(cList);
		filteredJList.getJList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		for(UItem item:cList){
			filteredJList.addItem(item.toString());
		}
		
		filteredJList.getJList().addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {		       
				if(e.getClickCount()==2){
					handleOk();
				}
			}		
		});
		filteredJList.getJList().addKeyListener(new KeyAdapter(){
      public void keyReleased(KeyEvent e){  
        handleKeys(e);    
      }     
    });		
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
		cancelButton = new JButton(Messages.getString("EDITOR_CANCEL"));
		cancelButton.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
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
		
		
		getRootPane().setDefaultButton(okButton);		
		this.setContentPane(getJContentPane());
		
		
	}

	private void handleKeys(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
      handleCancel();   
    
  }

		/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			fieldLabel = new JLabel(ownerString);			
			indicatorNameLabel = new JLabel(nameString);			
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());			
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;			
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.LINE_START;
			c.insets = new Insets(10,10,5,10);
			jContentPane.add(fieldLabel, c);		
			c.gridy = 1;			
			jContentPane.add(indicatorNameLabel, c);			
			c.gridy = 2;
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			jContentPane.add(filteredJList, c);					
			c = new GridBagConstraints();			
			c.gridy = 3;			
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5,10,10,10);
			c.fill = GridBagConstraints.BOTH;
			buttonsPanel = new JPanel();
			buttonsPanel.add(okButton);
			buttonsPanel.add(cancelButton);
			jContentPane.add(buttonsPanel, c);
			
		}
		return jContentPane;
	}
	
	private void handleOk(){
		setSelectedCode();	
		setVisible(false);	
	}
	
	private void handleCancel(){
		dispose();
	}
	
	private void setSelectedCode(){
		try{
			String selectedString = filteredJList.getJList().getSelectedValue().toString();
			selectedCode = selectedString.substring(0,selectedString.indexOf("-"));
		}catch(NullPointerException e){
			selectedCode = null;
		}		
	}
	
	

}  
