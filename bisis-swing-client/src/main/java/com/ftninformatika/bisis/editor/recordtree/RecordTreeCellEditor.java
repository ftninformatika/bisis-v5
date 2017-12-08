package com.ftninformatika.bisis.editor.recordtree;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.editorutils.IndicatorCodeChoiceDialog;
import com.ftninformatika.bisis.editor.editorutils.SubfieldCodeChoiceDialog;
import com.ftninformatika.bisis.editor.editorutils.TableCodeChoiceDialog;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.format.*;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.editor.editorutils.CodeChoiceDialog;
import com.ftninformatika.utils.CharacterLookup;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;


public class RecordTreeCellEditor extends DefaultTreeCellEditor{
	
	private Container editingContainer;
	private JLabel label;
	private JTextArea cellEditor;
	private JTextField codedCellEditor;
	private JScrollPane scrollTA;
	private JButton coder;	
	private Dimension editorDimension;	
	private RecordTree ownerTree;	
	private TreePath lastPath;
	
	//sluzi za operaciju isCellEditable, ako se dodaje novo potpolja da se odmah editira 
	private boolean canEdit = false;
	
	
	private Object currElement;
	private boolean coded;
	
	//pomocne sitnice
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	//RegistryDlg regDlg;
	
	public RecordTreeCellEditor(RecordTree tree,DefaultTreeCellRenderer renderer) {
		super(tree,renderer);		
		cellEditor = new JTextArea();
		codedCellEditor = new JTextField();
		label = new JLabel();	
		coder = new JButton(new ImageIcon(getClass().getResource(
        "/icons/coder.gif")));
		lookup = new CharacterLookup(BisisApp.getMainFrame());
		editingContainer = new Container();
		this.ownerTree = tree;
		//regDlg = new RegistryDlg(BisisApp.getMainFrame());
		codedCellEditor.addKeyListener(new CellEditorKeyListener());			
		cellEditor.addKeyListener(new CellEditorKeyListener());
		coder.addKeyListener(new CellEditorKeyListener());
		coder.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {						
				handleOpenCoder();				
			}			
		});
	}	
	
	private Component createEditor(){		
		editingContainer = new Container();
		editingContainer.setLayout(new GridBagLayout());		
		GridBagConstraints c = new GridBagConstraints();
		Border border = UIManager.getBorder("Tree.editorBorder");
		if(coded){			
			c.gridx = 0;
			c.gridy = 0;	
			c.anchor = GridBagConstraints.FIRST_LINE_START;		
			editingContainer.add(label,c);
			c.gridx = 1;			
			c.fill = GridBagConstraints.BOTH;			
			codedCellEditor.setBorder(border);
			codedCellEditor.setPreferredSize(new Dimension(50,10));
			editingContainer.add(codedCellEditor,c);			
			c.gridx = 2;			
			c.insets = new Insets(0,10,0,0);			
			coder.setPreferredSize(new Dimension(20,20));
			editingContainer.add(coder,c);			
		}else{	
			editingContainer.setPreferredSize(editorDimension);	
			c.gridx = 0;
			c.gridy = 0;	
			c.anchor = GridBagConstraints.FIRST_LINE_START;		
			editingContainer.add(label,c);
			c.gridx = 1;	
			c.weighty = 1;
			c.weightx = 1;
			c.fill = GridBagConstraints.BOTH;				
			cellEditor.setLineWrap(true);
			scrollTA = new JScrollPane(cellEditor);
			scrollTA.setSize(editorDimension);			
			editingContainer.add(scrollTA,c);			
			cellEditor.setBorder(border); 
      
		}	
		return editingContainer;	
	}
	
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row){		
		this.ownerTree = (RecordTree) tree;
		lastPath = tree.getPathForRow(row);    
		if(leaf){			
			if(value instanceof Subfield){
				label.setMinimumSize(new Dimension(18,15));
				Subfield sf = (Subfield) value;
				currElement = sf;
				label.setText("<html><b>["+String.valueOf(sf.getName())+"]</b>");								
				// da li je sifrirano?
			//	tree.getPathForRow(row);
				USubfield usf = CurrFormat.format.getSubfield(RecordUtils.returnSubfieldString(sf));
				currElement = usf;
				coded = usf.getCoder()!=null;
				if (coded)codedCellEditor.setText(sf.getContent());				
				else cellEditor.setText(sf.getContent());
				return createEditor();
			}else if(value instanceof IndicatorNode){
				IndicatorNode ind = (IndicatorNode) value;
				currElement = ind;
				label.setMinimumSize(new Dimension(30,15));
				label.setText("<html><b>ind"+String.valueOf(ind.getIndex())+": </b>");				
				coded = true;
				codedCellEditor.setText(String.valueOf(ind.getValue()));				
				return createEditor();			
			}
		}		
		return createEditor();		
	}
  
  public Component getComponentForFocus(){
    if(coded) return codedCellEditor;
    else return cellEditor;
  }

	public Object getCellEditorValue() {
		if(coded) return codedCellEditor.getText();
		else return cellEditor.getText();
	}

	public void setEditorWidth(int width){
  	editorDimension = new Dimension(width-45,50);		
  }

  public boolean isCellEditable(EventObject event) {		
		// celija moze da se editira ako se u stablu javlja kao "list", znaci
		// ako je potpolje ili indikator
		if (event != null) {
            if (event.getSource() instanceof JTree) {
            	ownerTree = (RecordTree)event.getSource();
                if (event instanceof MouseEvent) {
                	if (((MouseEvent)event).getClickCount()==2){                			
	                    TreePath path = ownerTree.getPathForLocation(
	                                         ((MouseEvent)event).getX(),
	                                         ((MouseEvent)event).getY());
	                    if(ownerTree.getModel().isLeaf(path.getLastPathComponent())){
	                    	if(path.getLastPathComponent() instanceof Subfield){
	                    		Subfield sf = (Subfield) path.getLastPathComponent();
	                    		return !RecordUtils.isRN(sf);
	                    	}
	                    	return true;
	                    		
	                    }
	                    		
                	}                    
                }                
            }
		}	
		return canEdit;	
	}
  
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	private void handleValueChanged(){		
		try {
			ownerTree.getModel().handleValueChanged(lastPath,getCellEditorValue());
			ownerTree.cancelEditing();
		} catch (UValidatorException e1) {
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e1.getMessage(), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
			//problemi sa enterom
			String value = cellEditor.getText();
			if(value.endsWith("\n"))
				cellEditor.setText(value.substring(0,value.length()-1));
		}		
	}
  
	private void handleOpenCoder(){
		if(currElement instanceof IndicatorNode || currElement instanceof USubfield){
		  CodeChoiceDialog ccd = null;
		if(currElement instanceof IndicatorNode){
			IndicatorNode in = (IndicatorNode)currElement;
			UIndicator ui;
			UField uf = PubTypes.getFormat().getField(in.getOwner().getName());
			if(in.getIndex()==1)
				ui = uf.getInd1();
			else
				ui = uf.getInd2();
			ccd = new IndicatorCodeChoiceDialog(ui,BisisApp.getMainFrame());
		}else if(currElement instanceof USubfield){
			USubfield usf = (USubfield)currElement;
			if(usf.getOwner().getName().equalsIgnoreCase("992")){ //cita sifarnik iz baze
				if (usf.getName()=='b'){
					ccd=new TableCodeChoiceDialog(usf,HoldingsDataCoders._992b_CODER,BisisApp.getMainFrame());
				}else if(usf.getName()=='f'){
					ccd=new TableCodeChoiceDialog(usf,HoldingsDataCoders.LIBRARIAN_CODER,BisisApp.getMainFrame());
				}else{ //ostala sifrirana polja 992 ne cita iz baze
					ccd = new SubfieldCodeChoiceDialog(usf,BisisApp.getMainFrame());
				}
				
			}else{
			    ccd = new SubfieldCodeChoiceDialog(usf,BisisApp.getMainFrame());
			}
		}		
		ccd.setVisible(true);		
		if(ccd.getSelectedCode()!=null)
			codedCellEditor.setText(ccd.getSelectedCode());
		//else codedCellEditor.setText("");
		ccd.setVisible(false);
		}
		codedCellEditor.grabFocus();
	}
	
	private void handleGenerateDate(){
		Date today = new Date();
		cellEditor.append(sdf.format(today));		
		cellEditor.grabFocus();
		cellEditor.setCaretPosition(cellEditor.getText().length());
	}
	
	private void handleLookUp() {    
    lookup.setVisible(true);
    if (lookup.isSelected()) {
      char c = lookup.getSelectedChar();
      int pos = cellEditor.getCaretPosition();
      String s1 = cellEditor.getText().substring(0, pos);
      String s2 = (pos == cellEditor.getText().length()) ? "" : cellEditor.getText().substring(pos);
      cellEditor.setText(s1 + c + s2);
      cellEditor.setCaretPosition(pos+1);
    }
    
  }
	
	// bgb
	
	private void handleRegistries(){	
		/*if(RegistryUtils.registriesAvailable()){
			regDlg.setVisible(true);
			if(regDlg.getValue()!=null){
				if(cellEditor.getText().equals(""))
					cellEditor.setText(regDlg.getValue());
				else
					cellEditor.setText(cellEditor.getText()+" "+regDlg.getValue());
			 }
			}else{
				JOptionPane.showMessageDialog(null, "Registri nisu instalirani!", "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
			}
				*/
	}
	
	CharacterLookup lookup;

  class CellEditorKeyListener extends KeyAdapter{
  	public void keyPressed(KeyEvent e) {
  		if(e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_F12) {					
  			handleValueChanged();
  			
  		}
  		if(e.getKeyCode()==KeyEvent.VK_F9){
  			handleOpenCoder();				
  		}
  		if(e.getKeyCode()==KeyEvent.VK_F2){
  			handleGenerateDate();
  		}
    if(e.getKeyCode()==KeyEvent.VK_F1){
       handleLookUp();
    }
    if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
       stopCellEditing();      
    } 
    if(e.getKeyCode()==KeyEvent.VK_R && e.getModifiers()==InputEvent.CTRL_MASK){
     handleRegistries();      
  }      
  	}				
  }
}
