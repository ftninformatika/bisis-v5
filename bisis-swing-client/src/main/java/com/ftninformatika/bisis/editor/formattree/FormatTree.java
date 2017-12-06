package com.ftninformatika.bisis.editor.formattree;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.editorutils.AddUFieldDialog;
import com.ftninformatika.bisis.editor.editorutils.AddUSubfieldDialog;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.records.Record;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

public class FormatTree extends JTree {
	
	private FormatTreeModel model;
	private FormatTreeCellRenderer renderer;	
	private AddUFieldDialog addDlg;
	private AddUSubfieldDialog addsfDialog;
	
	private JPopupMenu popupMenu;
	
	
	public FormatTree(){		
		createTree();	
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
        handleKeys(e);
			}		
		});	
    
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){				
					handleShowPopup(e.getX(),e.getY());				
				}
			}
		});    
  }	

  public void initializeFormatTree(Record rec){
    CurrFormat.formatInitialize(rec);    
    model.fireTreeStructureChanged();
    setSelectionRow(0);
  }

  public Object getSelected(){		
  	return getSelectionPath().getLastPathComponent();		
  }

  public void addElementAction(){		
  	addDlg = new AddUFieldDialog(BisisApp.getMainFrame());
  	addDlg.setVisible(true);
  	if(addDlg.getChosenUField()!=null){			
  		addNewUField(addDlg.getChosenUField());
  	}
  }

  public void setSecondaryMode(String fieldName){
  	if(CurrFormat.block4.contains(fieldName)){
  		renderer.setSecondaryMode(true);
  	}else{
  		renderer.setSecondaryMode(false);
  	}  	
  	repaint();
  }
  
  public void expandAll(){
    for(int i=0;i<getRowCount();i++){
      expandRow(i);      
    }
  }
  
  public void collapseAll(){
    for(int i=1;i<getRowCount();i++){
      collapseRow(i);      
    }    
  }
  
  public void refreshView(){
  	model.fireTreeStructureChanged();
  }

  private void createTree(){
		model = new FormatTreeModel();
		setModel(model);		
		//setRootVisible(false);
		renderer = new FormatTreeCellRenderer();
		putClientProperty("JTree.lineStyle", "None");
		((BasicTreeUI)this.getUI()).setCollapsedIcon(null);
		((BasicTreeUI)this.getUI()).setExpandedIcon(null);
		setCellRenderer(renderer);		
	}
  
  private void onEnter(){		
		TreePath t = this.getSelectionPath();		
		if(this.isCollapsed(t)){
			this.expandPath(t);			
		}else this.collapsePath(t);
	}
	
	private UField getSelectedField(){
    if(getSelectionPath()!=null){
      Object o = getSelectionPath().getLastPathComponent();
      if(o instanceof UField) return (UField)o;
      else if(o instanceof USubfield)  return ((USubfield)o).getOwner();
      else if(o instanceof UIndicator) return ((UIndicator)o).getOwner();
    }
    return null;      
  }
  
  private void selectField(String name){
    UField uf = CurrFormat.format.getField(name);
    Object[] obj = {CurrFormat.format,uf};
    setSelectionPath(new TreePath(obj));  
  } 
	
	private void addNewUField(UField uf){		
		model.insertNodeInto(model.getRoot(),uf);
		CurrFormat.sortFields();	
		model.fireTreeStructureChanged();
		Object[] objPath = {model.getRoot(),uf,uf.getSubfields().get(0)};
		TreePath path = new TreePath(objPath);		
		this.expandPath(path);		
		this.scrollPathToVisible(path);		
		this.setSelectionPath(path);		
	    RecordUtils.addUFieldWithSubfields(uf);
	    Obrada.editorFrame.getRecordTree().getModel().fireTreeStructureChanged();
	}
	
	private void createUFieldPopupMenu(){
		popupMenu = new JPopupMenu();
		popupMenu.setInvoker(this);			
		JMenuItem miCollapseOrExpand = new JMenuItem();
		if(isCollapsed(getSelectionPath())) miCollapseOrExpand.setText(Messages.getString("OPEN"));
		else miCollapseOrExpand.setText(Messages.getString("CLOSE"));
		miCollapseOrExpand.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				onEnter();				
			}			
		});
		popupMenu.add(miCollapseOrExpand);   
	    popupMenu.add(new ExpandAllAction());    
	    popupMenu.add(new CollapseAllAction());
	    popupMenu.add(new AddFieldAction());	
	    AddSubfieldAction addSubfieldAction = new AddSubfieldAction();
	    popupMenu.add(addSubfieldAction);		
	    popupMenu.add(new AddEmptyFieldAction());
		
		if(CurrFormat.returnMissingUSubfields((UField)getSelected()).size()>0)
			addSubfieldAction.setEnabled(true);
		else addSubfieldAction.setEnabled(false);	
	}
	
	private void handleAddUField() {
		Obrada.editorFrame.getZapisPanel()
			.handleAddNewField((UField)this.getSelected());
    
  }
	
	private void handleAddEmptyField(){
		Obrada.editorFrame.getZapisPanel()
		.handleAddEmptyField((UField)this.getSelected());
	}

	private void handleShowPopup(int x,int y){
		setSelectionPath(getPathForLocation(x,y));
		if(getSelected() instanceof UField){
			createUFieldPopupMenu(); 		
			popupMenu.show(this,x,y);		
		}
	}
	
	private void handleAddUSubfield(){
		UField owner = CurrFormat.format.getField(getSelectedField().getName());
		addsfDialog = new AddUSubfieldDialog(BisisApp.getMainFrame(),owner);
		addsfDialog.setVisible(true);
		if(addsfDialog.hasUSubfieldSelected() && addsfDialog.getChosenSubfields().size()>0){
			addUSubfields(addsfDialog.getChosenSubfields(),owner);
		}
	}

	private void addUSubfields(List<USubfield> sfList, UField uf){
		for(int i=0;i<sfList.size();i++){
		 model.insertNodeInto(uf,sfList.get(i));
		}
		CurrFormat.sortSubfields(uf);
		model.fireTreeStructureChanged();
		Object[] objPath = {model.getRoot(),uf,sfList.get(0)};
		TreePath path = new TreePath(objPath);		
		this.expandPath(path);		
		this.scrollPathToVisible(path);		
		this.setSelectionPath(path);
  RecordUtils.addUSubfields(sfList);
  Obrada.editorFrame.getRecordTree().setSelectedField();
  Obrada.editorFrame.getRecordTree().getModel().fireTreeStructureChanged();		
	} 
  
   
  private void handleKeys(KeyEvent e){  
    if (e.getKeyCode()== KeyEvent.VK_ENTER)
      if(e.getModifiers()!=InputEvent.SHIFT_MASK)
        onEnter();    
    if(e.getKeyCode()>47 && e.getKeyCode()<58)
      handleSelectFirstUField(e.getKeyCode()-48);   
    if(e.getKeyCode()==KeyEvent.VK_NUMPAD4)
      if(CurrFormat.returnMissingUSubfields(CurrFormat.format.getField(getSelectedField().getName())).size()>0)
        handleAddUSubfield();
    // TODO izbaciti kad se smisli bolje resenje
    /*ovaj deo ne treba da postoji jer su definisane 
    akcije sa precicama sa tastature, ali one reaguju samo
    kada je otvoren padajuci meni, to treba malo pogledati*/
    if(e.getKeyCode()== KeyEvent.VK_E)
      if(e.getModifiers() == InputEvent.SHIFT_MASK)        
        expandAll();      
    if(e.getKeyCode()==KeyEvent.VK_C)
      if(e.getModifiers() == InputEvent.SHIFT_MASK)
        collapseAll();   
    if(e.getKeyCode()==KeyEvent.VK_DELETE){
    	deleteSelected();    	
    }
    if(e.getKeyCode()==KeyEvent.VK_NUMPAD7)
      Obrada.editorFrame.getAddUField().doClick(); 
  }

  private void handleSelectFirstUField(int i) {   
    if(FormatUtils.returnFirstFieldName(String.valueOf(i))!=null)
      selectField(FormatUtils.returnFirstFieldName(String.valueOf(i)));
  }
  
  private void deleteSelected(){
	  //TODO implementirati
  }
  
  public class ExpandAllAction extends AbstractAction {

    public ExpandAllAction() {
      putValue(SHORT_DESCRIPTION, Messages.getString("OPENING_TREE_ELEMENTS"));
      putValue(NAME, Messages.getString("OPEN_ALL"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_MASK));
     
    }    
    public void actionPerformed(ActionEvent ev) {
      expandAll();
    }

  }
  
  public class CollapseAllAction extends AbstractAction {

    public CollapseAllAction() {
      putValue(SHORT_DESCRIPTION, Messages.getString("CLOSE_TREE_ELEMENTS"));
      putValue(NAME, Messages.getString("CLOSE_ALL"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK));     
    }    
    public void actionPerformed(ActionEvent ev) {
      collapseAll();
    }
  }
  
  public class AddFieldAction extends AbstractAction {

    public AddFieldAction() {
      putValue(SHORT_DESCRIPTION, Messages.getString("ADDING_SELECTED_FIELD_TO_RECORD"));
      putValue(NAME, Messages.getString("ADD_FIELD_TO_RECORD"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_MASK));     
    }    
    public void actionPerformed(ActionEvent ev) {
      handleAddUField();       
    }
  }
  
  public class AddEmptyFieldAction extends AbstractAction {

   public AddEmptyFieldAction() {
     putValue(SHORT_DESCRIPTION, Messages.getString("ADDING_FIELD_TO_RECORD_WITHOUT_SUBFIELD"));
     putValue(NAME, Messages.getString("ADD_EMPTY_FIELD_TO_RECORD"));
     putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.SHIFT_MASK));     
   }    
   public void actionPerformed(ActionEvent ev) {
     handleAddEmptyField();      
   }
 }
  
  public class AddSubfieldAction extends AbstractAction {

	    public AddSubfieldAction() {
	      putValue(SHORT_DESCRIPTION, Messages.getString("ADDING_SUBFIELD"));
	      putValue(NAME, Messages.getString("ADD_SUBFIELDS"));
	      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4,0));     
	    }    
	    public void actionPerformed(ActionEvent ev) {
	    	handleAddUSubfield();   
	    }
  }
	
	
	


}
