/**
 * 
 */
package com.ftninformatika.bisis.editor.recordtree;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.MessageFormat;
import java.util.List;

import com.ftninformatika.utils.Messages;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.format.UValidatorException;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * @author Bojana
 *
 */
public class RecordTree extends JTree {	
	
	private RecordTreeModel model;
	private RecordTreeCellRenderer renderer;
	private RecordTreeCellEditor editor;
	
	private JPopupMenu popupMenu;
  
  private static Log log = LogFactory.getLog(RecordTree.class.getName()); 

	
	public RecordTree(){
		
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
		
		createRecordTree();		
	}	

  public void recordInitialize(Record rec){
  	CurrRecord.recordInitialize(rec);
  	model.fireTreeStructureChanged();
  	setSelectionRow(0);		
	}
  
  public RecordTreeCellEditor getCellEditor(){
    return editor;
  }
	
	public void setEditorWidth(int width){
		editor.setEditorWidth(width);		
	}
  
  public void getParent(Subfield sf){
    
  }
	
	public void setSelectedField(){
		try{	
			TreePath path = getSelectionPath();
			while(!(path.getLastPathComponent()instanceof Field)){
				path = path.getParentPath();				
			}
			CurrRecord.selectedField = (Field) path.getLastPathComponent();
		}catch(NullPointerException e){            
			CurrRecord.selectedField = null;
		}		
	}
  
  public void selectField(String name){
    Field f = CurrRecord.record.getField(name);
    Object[] objPath = {CurrRecord.record,f};
    setSelectionPath(new TreePath(objPath));
   }
	
	public Field getSelectedField(){
		try{	
			TreePath path = getSelectionPath();
			while(!(path.getLastPathComponent()instanceof Field)){
				path = path.getParentPath();				
			}
			return (Field) path.getLastPathComponent();
		}catch(NullPointerException e){
      log.fatal(e);     
			return null;
		}		
	}
	public void createRecordTree(){		
		model = new RecordTreeModel();
		this.setModel(model);		
		renderer = new RecordTreeCellRenderer();			
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);		  
		this.setCellRenderer(renderer);
		renderer.setIcon(null);
		renderer.setOpenIcon(null);
		renderer.setClosedIcon(null);		
		editor = new RecordTreeCellEditor(this,renderer);		
		this.setCellEditor(editor);		    
		this.setEditable(true);
		this.setRootVisible(false);		
		this.putClientProperty("JTree.lineStyle", "None");
		((BasicTreeUI)this.getUI()).setCollapsedIcon(null);
		((BasicTreeUI)this.getUI()).setExpandedIcon(null);		
		this.setScrollsOnExpand(true);
		this.setExpandsSelectedPaths(true);	
	}
	
	public boolean saveRecord() throws UValidatorException {
		boolean ok = CurrRecord.saveCurrentRecord();  
		if(ok)
			model.fireTreeStructureChanged();
		return ok;		
	}
  
  public void refreshView(){
    model.fireTreeStructureChanged();
  }
  
  private void onEnter(){		
		TreePath t = this.getSelectionPath();		
		if(this.isCollapsed(t)){
			this.expandPath(t);			
		}else this.collapsePath(t);
	}
  
  public  boolean isSelected(){
    return this.getSelected()!=null;
  }
	
	
	public Object getSelected(){	
    if(getSelectionPath()!=null)
      return getSelectionPath().getLastPathComponent();
    else return null;		
	}	
	
	public RecordTreeModel getModel(){
		return model;
	}
	
	public RecordTreeCellRenderer getRenderer(){
		return renderer;
	}
	
	public void insertNewField(UField uf, boolean withoutSubfields) throws UValidatorException{
		Object [] objPath = CurrRecord.addField(uf, withoutSubfields);
		TreePath path = new TreePath(objPath);		
		model.fireTreeStructureChanged();
		setSelectionPath(path);
		expandPath(path);
		scrollPathToVisible(path);
	}
	
	
	
	public void insertNewEmptyElement(Object newEl) throws UValidatorException{		
		if(newEl instanceof USubfield || newEl instanceof UIndicator){
		TreePath path = null;		
		Subfield sf;		
		if(newEl instanceof USubfield){
			USubfield usf = (USubfield)newEl;
			UField owner = usf.getOwner();	
			path = getSelectionPath();
			if (path==null) return;			
			while(!(path.getLastPathComponent()instanceof Field)){
				path = path.getParentPath();				
			}	
			
			sf = (Subfield)getModel().insertNodeInto(owner,usf);
      model.fireTreeStructureChanged();  
  
			if(sf !=null){				
				path = path.pathByAddingChild(sf);				
				setSelectionPath(path);
				expandPath(path);
				scrollPathToVisible(path);
				if(!CurrFormat.block4.contains(owner.getName())){
          editAtPath(path);		
				}			
			}	
		}
			}
		}		
	
  
   public void expandAll(){
      for(int i=0;i<getRowCount();i++){
        expandRow(i);      
      }
    }
    
    public void collapseAll(){
      for(int i=0;i<getRowCount();i++){
        collapseRow(i);      
     }    
  } 
  
  private void handleKeys(KeyEvent e){    
    if(e.getKeyCode()>47 && e.getKeyCode()<58){
      handleSelectFirstField(e.getKeyCode()-48);
    }else{
  		switch (e.getKeyCode()){
  		case (KeyEvent.VK_ENTER):
  			if(isSelected() && getModel().isLeaf(getSelectionPath().getLastPathComponent())){
          editAtPath(getSelectionPath());  				
  			}else{
  				TreePath t = this.getSelectionPath();		
  				if(this.isCollapsed(t)){
  					this.expandPath(t);			
  				}else this.collapsePath(t);
  			}
  			break;
  		case (KeyEvent.VK_UP):
  			if(e.getModifiers() == InputEvent.ALT_MASK)
  				handleMoveElement(true);
  			break;
  		case (KeyEvent.VK_DOWN):
  			if(e.getModifiers() == InputEvent.ALT_MASK)
  				handleMoveElement(false);
  			break;
  		case(KeyEvent.VK_DELETE):
  			handleDeleteElement();
  			break;      
      case(KeyEvent.VK_E):
        if(e.getModifiers() == InputEvent.SHIFT_MASK)
          expandAll();
        break;
      case(KeyEvent.VK_C):
        if(e.getModifiers() == InputEvent.SHIFT_MASK)
          collapseAll();
      	break;
      case (KeyEvent.VK_NUMPAD5):        
        	handleCopyElement();
        break;
      case(KeyEvent.VK_NUMPAD4):
      	handleAddEmptyElement();        
      }
    }   
  }

  private void handleMoveElement(boolean up){  	
		if(getSelected() instanceof Subfield){
			TreePath path = getSelectionPath();
			if(up)
				RecordUtils.replaceSubfieldWithPrevious
				(getSelectedField(),(Subfield)getSelected());
			else 
				RecordUtils.replaceSubfieldWithNext
				(getSelectedField(),(Subfield)getSelected());					
			while(!(path.getLastPathComponent()instanceof Field)){
				path = path.getParentPath();				
			}
			path = path.pathByAddingChild((Subfield)getSelected());
			model.fireTreeStructureChanged();
			Obrada.editorFrame.recordUpdated();
			setSelectionPath(path);			
		}else if(getSelected() instanceof Field){			
			Field f = getSelectedField();
			if(up)
				RecordUtils.replaceFieldWithPrevious(f);
			else{
				RecordUtils.replaceFieldWithNext(f);				
			}		
			Object[] objPath = {CurrRecord.record,f}; 
			TreePath path = new TreePath(objPath);
			model.fireTreeStructureChanged();
			Obrada.editorFrame.recordUpdated();
			setSelectionPath(path);
		}
	}
	
	private void handleDeleteElement(){		
		Object sel = this.getSelected();
		Field parent = null;
		TreePath path = null;
		String mess=""; 
		if(this.getSelectionPath().getPath().length<6){
			if(sel instanceof Subfield){
				path = getSelectionPath();
				while(!(path.getLastPathComponent()instanceof Field)){
					path = path.getParentPath();				
				}
				parent = (Field)path.getLastPathComponent();
				mess = RecordUtils.canBeDeleted(parent.getName()+((Subfield)sel).getName());
			}
			else if(sel instanceof Field){
				mess = RecordUtils.canBeDeleted(((Field)sel).getName());
			}
		}
		
		if(!mess.equals("")){
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),mess, Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
		}else{
			if(sel instanceof Field || sel instanceof Subfield){
				String message="";
				Field f = null;
				Subfield sf = null;			
				if(sel instanceof Field){
					f = (Field)sel;
					message = MessageFormat.format(Messages.getString("ARE_U_SURE_U_WANT_TO_DELETE_FIELD.0"), f.toString());
				}else if(sel instanceof Subfield){
					sf = (Subfield)sel;
					message = MessageFormat.format(Messages.getString("ARE_U_SURE_U_WANT_TO_DELETE_SUBFIELD.0"), sf.toString());
				}
				Object[] options = {Messages.getString("EDITOR_DELETE"), Messages.getString("EDITOR_BUTTONCANCEL") };
				int ret = JOptionPane.showOptionDialog(null, message , Messages.getString("DELETING"),
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[0]);			
				if(ret==0){
					if(f!=null){						
						path = new TreePath(RecordUtils.deleteField(getSelectionPath().getPath()));						
					}
					if(sf!=null && parent!=null){						
						path = new TreePath(RecordUtils.deleteSubfield(getSelectionPath().getPath()));
					}
					model.fireTreeStructureChanged();
					if (path!=null){
						setSelectionPath(path);											
					}
				}
			}
		}
	}
	
  private void handleSelectFirstField(int i) {
    if(RecordUtils.returnFirstFieldName(String.valueOf(i))!=null)
     selectField(RecordUtils.returnFirstFieldName(String.valueOf(i)));
  }

  private void editAtPath(TreePath path){  	
    editor.setCanEdit(true);
    startEditingAtPath(path);
    editor.getComponentForFocus().requestFocus();
    editor.setCanEdit(false);    
  }
  
  private void handleShowPopup(int x,int y){
		setSelectionPath(getPathForLocation(x,y));		
		createPopupMenu(); 		
		popupMenu.show(this,x,y);		
		
	}
  
  private void createPopupMenu(){
	  popupMenu = new JPopupMenu();
		popupMenu.setInvoker(this);	
		if(!(getSelected() instanceof IndicatorNode)){
			if(getSelected() instanceof Field){
				JMenuItem miCollapseOrExpand = new JMenuItem();
				if(isCollapsed(getSelectionPath())) miCollapseOrExpand.setText(Messages.getString("OPEN"));
				else miCollapseOrExpand.setText(Messages.getString("CLOSE_HTML"));
				miCollapseOrExpand.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						onEnter();				
					}		
				});
				popupMenu.add(miCollapseOrExpand);			
			}			
			popupMenu.add(new DeleteElementAction());
			popupMenu.add(new CopyElementAction());
			popupMenu.add(new AddEmptyElementAction());
		}
  }
  
  private void handleCopyElement(){  	
  	try{
	  	if(getSelected() instanceof Field){
	  		Field copy = RecordUtils.copyField((Field)getSelected());
	  		int index = CurrRecord.getRecord().getFields().indexOf(getSelectedField())+1;
	  		model.addField(copy, index);  		
	  		Object[] obj = {model.getRoot(),copy};
	  		TreePath path = new TreePath(obj);
	  		setSelectionPath(path);
	  	}else if (getSelected() instanceof Subfield){
	  		Subfield copy = RecordUtils.copySubfield((Subfield)getSelected());
	  		Field owner = getSelectedField();
	  		int index = owner.getSubfields().indexOf((Subfield)getSelected())+1;
	  		model.addSubfield(owner, copy, index);
	  		Object[] obj = {model.getRoot(), owner, copy};
	  		TreePath path = new TreePath(obj);
	  		expandPath(path);
	  		setSelectionPath(path);  		
	  	}
	  	}catch(UValidatorException e){
	  		JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
  	}
  }
  
  private void handleAddEmptyElement(){  	
  	try{
	  	if(getSelected() instanceof Field){
	  		Field newField = RecordUtils.shallowCopyFiled((Field)getSelected());
	  		int index = CurrRecord.getRecord().getFields().indexOf(getSelectedField())+1;
	  		// dodajemo i prazna potpolja
	  		List<Subfield> subfields = ((Field)getSelectedField()).getSubfields();
	  		for(Subfield sf:subfields){
	  			Subfield sfNew = sf.copy();
	  			sfNew.setContent("");
	  			newField.add(sfNew);
	  		}
 	  		model.addField(newField, index);  		
	  		Object[] obj = {model.getRoot(),newField};
	  		TreePath path = new TreePath(obj);
	  		setSelectionPath(path);
	  	}else if (getSelected() instanceof Subfield){
	  		Subfield newSubfield = new Subfield(((Subfield)getSelected()).getName());
	  		Field owner = getSelectedField();
	  		int index = owner.getSubfields().indexOf((Subfield)getSelected())+1;
	  		model.addSubfield(owner, newSubfield, index);
	  		Object[] obj = {model.getRoot(), owner, newSubfield};
	  		TreePath path = new TreePath(obj);
	  		expandPath(path);
	  		setSelectionPath(path);  		
	  	}
  	}catch(UValidatorException e){
  		JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(), Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);
  	}
  }
  
  public class DeleteElementAction extends AbstractAction {

    public DeleteElementAction() {
      putValue(SHORT_DESCRIPTION, Messages.getString("DELETION"));
      putValue(NAME, Messages.getString("EDITOR_DELETE"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));     
    }    
    public void actionPerformed(ActionEvent ev) {
    	handleDeleteElement();   
    }
  }
  
  public class CopyElementAction extends AbstractAction{
  	public CopyElementAction(){
  		putValue(SHORT_DESCRIPTION, Messages.getString("COPYING"));
      putValue(NAME, Messages.getString("COPY"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD5, 0));
  	}
  	
  	public void actionPerformed(ActionEvent ev) {
    	handleCopyElement();   
    }  	
  }
  
  public class AddEmptyElementAction extends AbstractAction{
  	public AddEmptyElementAction(){
  		putValue(SHORT_DESCRIPTION, Messages.getString("ADDING_RECORD_ELEMENT"));
      putValue(NAME, Messages.getString("EDITOR_ADD_BUTTON"));
      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD4, 0));
  	}
  	
  	public void actionPerformed(ActionEvent ev) {
    	handleAddEmptyElement();   
    }  	
  }
	
}
