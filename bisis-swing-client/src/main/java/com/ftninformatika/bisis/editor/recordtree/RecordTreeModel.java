package com.ftninformatika.bisis.editor.recordtree;

import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.format.UValidatorException;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Subfield;

import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;



public class RecordTreeModel  implements TreeModel{    
   
    /** Listeners. */
    protected EventListenerList listenerList = new EventListenerList();
    
    private Vector<TreeModelListener> treeModelListeners =
        new Vector<TreeModelListener>();
   
  

     public RecordTreeModel() {  	
    	
    } 

    
    public int getIndexOfChild(Object parent, Object child){    	
        if(parent == null || child == null)
            return -1;
        if(parent.equals(CurrRecord.record) && child instanceof Field){
        	return RecordUtils.getIndexOfField((Field)child);
        }
        if(parent instanceof Field){
        	Field f = (Field) parent;
        	if(child instanceof Subfield){
        		return RecordUtils.getIndexOfSubfield(f,(Subfield)child)+RecordUtils.getNumberOfIndicators(f);
        	}else if(child instanceof IndicatorNode){
        		return ((IndicatorNode)child).getIndex(); 
        	}        		
        } 
        // secundarna
        if(parent instanceof Subfield && child instanceof Field)
        	return 0;
        return -1;
     }

    // treba ubaciti za sekundarna polja, ako ce i ona biti u strukturi
    public Object getChild(Object parent, int index) {
    	if(parent.equals(CurrRecord.record)){    		
    		return CurrRecord.record.getField(index);
    	}
    	if(parent instanceof Field){
    		Field f = (Field)parent;
    		UField uf = CurrFormat.getFullFormat().getField(f.getName());
    		if (uf.getInd1()==null && uf.getInd2()==null){
    			// ako nema indikatore
    			return f.getSubfields().get(index);
    		}else{
    			if(uf.getInd1()!=null && uf.getInd2()==null){
    				// ima samo prvi indikator
    				if(index==0) return new IndicatorNode(1,f.getInd1(),f);
    				else return f.getSubfields().get(index-1);	    			  	
    			}
    			if(uf.getInd1()== null && uf.getInd2()!= null){
    				// ima samo drugi indikator
    				if(index==0) return new IndicatorNode(2,f.getInd2(),f);
    				else return f.getSubfields().get(index-1);	
    			}
    			if (uf.getInd1()!=null && uf.getInd2()!=null){
    				if(index==0) return new IndicatorNode(1,f.getInd1(),f);;
    				if(index==1) return new IndicatorNode(2,f.getInd2(),f);;
    				if(index>1)  return f.getSubfields().get(index-2);
    			}
    		}
    	}
    	if(parent instanceof Subfield){
    		Subfield sf = (Subfield) parent;
    		return sf.getSecField();
    	}    	
    	return null;
    }

   
    public int getChildCount(Object parent) {
    	if (parent.equals(CurrRecord.record)){
    		return CurrRecord.record.getFields().size();
    	}
    	if(parent instanceof Field){
    		Field f = (Field) parent;
    		UField uf = CurrFormat.getFullFormat().getField(f.getName());
    		int i = f.getSubfieldCount();
    		if(uf.getInd1()!= null) i++;
    		if(uf.getInd2()!= null) i++;    		
    		return i;
    	}
    	if(parent instanceof Subfield)
    		if(((Subfield)parent).getSecField()!=null) return 1;
    	return 0;
    }

  
    public boolean isLeaf(Object node) {
    	if(node instanceof Subfield) return((Subfield)node).getSecField()==null;
        return (node instanceof Subfield) || (node instanceof IndicatorNode);
    }
    
    public void addTreeModelListener(TreeModelListener l){
        treeModelListeners.addElement(l);
    }
    
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.removeElement(l);
    }
    
    public void handleValueChanged(TreePath path, Object value) throws UValidatorException {
    	Field f = (Field)path.getParentPath().getLastPathComponent();
    	Object selElement = path.getLastPathComponent();    	 	
    	if (selElement instanceof Subfield){    		
    		Subfield aNode = (Subfield)selElement;    		
    		CurrRecord.changeSubfieldConetent(aNode,f.getName(),value.toString());    		 		
    	}else if(selElement instanceof IndicatorNode){
    		IndicatorNode in = (IndicatorNode)selElement;  
    		UIndicator ui;
    		if(in.getIndex()==1)
    			ui = CurrFormat.format.getField(in.getOwner().getName()).getInd1();
    		else 
    			ui = CurrFormat.format.getField(in.getOwner().getName()).getInd2();
    		CurrRecord.changeIndicatroValue(ui,(String)value);
    					  		  		
    	}
    	valueForPathChanged(path,value);    		
    	
    }
    
    public void valueForPathChanged(TreePath path, Object value) {
    	Obrada.editorFrame.recordUpdated();
    	Object selElement = path.getLastPathComponent();    	
    	if (selElement instanceof Subfield){    		
    		Subfield aNode = (Subfield)selElement;    		
    		aNode.setContent(value.toString());    		
    	}else if(selElement instanceof IndicatorNode){
    		IndicatorNode in = (IndicatorNode)selElement; 
    		if(value.toString().length()==1)
    			in.setValue(value.toString().charAt(0));  
    		else in.setValue(' ');  
    	}   	
	} 
    
    public Object insertNodeInto(Object parent, Object child) throws UValidatorException {
    	Obrada.editorFrame.recordUpdated();
    	if(parent instanceof UField){
    		if(child instanceof USubfield){
    			USubfield usf = (USubfield)child;
    			if(usf.getDefaultValue()==null)
    				return CurrRecord.addSubfied((USubfield)child,"");
    			else
    				return CurrRecord.addSubfied((USubfield)child,usf.getDefaultValue());
    		}    	
    	}    	
    	return null;    	
    }

	public Object getRoot(){		
		return CurrRecord.record;
	}	
  
	public void addField(Field field, int index) throws UValidatorException{
		Obrada.editorFrame.recordUpdated();
		//	TODO provera ponovljivosti
		CurrRecord.addField(field, index);		
		fireTreeStructureChanged();
	}
	
	public void addSubfield(Field field, Subfield subfield,int index) throws UValidatorException{
		Obrada.editorFrame.recordUpdated();
		CurrRecord.addSubfiled(field, subfield, index);
		fireTreeStructureChanged();
	}
	
	 public void fireTreeStructureChanged() {	       
	        TreeModelEvent e = new TreeModelEvent(this, 
	                                              new Object[] {CurrRecord.record});
	        for (TreeModelListener tml : treeModelListeners) {
	            tml.treeStructureChanged(e);
	        }
	        //Obrada.editorFrame.recordUpdated();
	    }

	
	
    
}
    
 

   




