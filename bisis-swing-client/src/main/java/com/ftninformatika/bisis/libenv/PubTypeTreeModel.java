/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.USubfield;

import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author dimicb
 * 
 * models stabla koje sadrzi polja i potpolja
 * odgovarajuceg tipa publikacije
 * slican je kao models stabla u
 * editoru, ali ne sadrzi indikatore
 */
public class PubTypeTreeModel implements TreeModel {
	
	private int pubTypeId;
	private UFormat pubType;
	
	 /** Listeners. */
  protected EventListenerList listenerList = new EventListenerList();
  
  private Vector<TreeModelListener> treeModelListeners =
      new Vector<TreeModelListener>();

	public PubTypeTreeModel(int pubTypeId) {
		this.pubTypeId = pubTypeId;
		pubType = PubTypes.getPubType(pubTypeId);
	}
	
	public Object getChild(Object parent, int index) {
		if(parent instanceof UFormat)
			return ((UFormat)parent).getField(index);
		else if(parent instanceof UField)
			return ((UField)parent).getSubfields().get(index);		
		return null;
	}
	
	public int getChildCount(Object parent) {
		if(parent instanceof UFormat)
			return ((UFormat)parent).getFields().size();
		else if(parent instanceof UField)
			return ((UField)parent).getSubfieldCount();
		return 0;
	}
	
	public int getIndexOfChild(Object parent, Object child) {		
		return 0;
	}

	public Object getRoot() {		
		return pubType;
	}

	public boolean isLeaf(Object node) {		
		return (node instanceof USubfield);
	}	

	public void valueForPathChanged(TreePath path, Object newValue) {
		

	}
	
	public void addTreeModelListener(TreeModelListener l){
    treeModelListeners.addElement(l);
	}

	public void removeTreeModelListener(TreeModelListener l) {
    treeModelListeners.removeElement(l);
}


	public void fireTreeStructureChanged() {	       
      TreeModelEvent e = new TreeModelEvent(this, 
                                            new Object[] {pubType});
      for (TreeModelListener tml : treeModelListeners) {
          tml.treeStructureChanged(e);
      }
  }

	public int getPubTypeId() {
		return pubTypeId;
	}

	public void setPubTypeId(int pubTypeId) {
		this.pubTypeId = pubTypeId;
		pubType = PubTypes.getPubType(pubTypeId);
		fireTreeStructureChanged();
	}

}
