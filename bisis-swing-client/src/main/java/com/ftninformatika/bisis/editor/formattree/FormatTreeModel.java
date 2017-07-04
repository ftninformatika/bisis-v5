package com.ftninformatika.bisis.editor.formattree;

import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;

import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class FormatTreeModel implements TreeModel{
	
		
	 /** Listeners. */
    protected EventListenerList listenerList = new EventListenerList();
    
    private Vector<TreeModelListener> treeModelListeners =
        new Vector<TreeModelListener>();
   
  	
	
	
	
	public int getIndexOfChild(Object parent, Object child){    	
        return -1;
  }
	
	
	
	 public Object getChild(Object parent, int index) {
	    	if(parent.equals(CurrFormat.format)){    		
	    		return CurrFormat.format.getField(index);
	    	}
	    	if(parent instanceof UField){
	    		UField f = (UField)parent;
	    		if (f.getInd1()==null && f.getInd2()==null){
	    			// ako nema indikatore
	    			return f.getSubfields().get(index);
	    		}else{
	    			if(f.getInd1()!=null && f.getInd2()==null){
	    				// ima samo prvi indikator
	    				if(index==0) return f.getInd1();
	    				else return f.getSubfields().get(index-1);	    			  	
	    			}
	    			if(f.getInd1()==null && f.getInd2()!=null){
	    				// ima samo drugi indikator
	    				if(index==0) return f.getInd2();
	    				else return f.getSubfields().get(index-1);	
	    			}
	    			if (f.getInd1()!=null && f.getInd2()!=null){
	    				if(index==0) return f.getInd1();
	    				if(index==1) return f.getInd2();
	    				if(index>1)  return f.getSubfields().get(index-2);
	    			}
	    		}
	    	}    	
	    	return null;
	    }
	 
	 	public int getChildCount(Object parent) {
	    	if (parent.equals(CurrFormat.format)){
	    		return CurrFormat.format.getFields().size();
	    	}
	    	if(parent instanceof UField){
	    		UField uf = (UField) parent;
	    		int i = uf.getSubfieldCount();
	    		if(uf.getInd1()!=null) i++;
	    		if(uf.getInd2()!=null) i++;
	    		
	    		return i;
	    	}
	    		
	    		
	    		
	    	return 0;
	    }

	 	public boolean isLeaf(Object node) {    	
	        return (node instanceof USubfield) || (node instanceof UIndicator);
	    }
	 	
	 	public void insertNodeInto(Object parent, Object child){	    	
	    	if(parent.equals(CurrFormat.format) && child instanceof UField){    	
	    		CurrFormat.format.add((UField)child);    		 		
	    	}
	    	if(parent instanceof UField && child instanceof USubfield)
	    		((UField)parent).add((USubfield)child);	    	
	  }


		public Object getRoot() {		
			return CurrFormat.format;
		}


		public void valueForPathChanged(TreePath path, Object newValue) {			
			
		}

		
		
		public void addTreeModelListener(TreeModelListener l){
	        treeModelListeners.addElement(l);
	   }
	    
	    public void removeTreeModelListener(TreeModelListener l) {
	        treeModelListeners.removeElement(l);
	   }
	    
		
		 protected void fireTreeStructureChanged() {	       
      TreeModelEvent e = new TreeModelEvent(this, 
                                            new Object[] {CurrFormat.format});
      for (TreeModelListener tml : treeModelListeners) {
          tml.treeStructureChanged(e);
      }
		 }
	    
		 

}
