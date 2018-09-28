package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


public class BooksTreeModel implements Serializable, TreeModel{
  
  private List<Record> records;
  private HashMap<String, ItemAvailability> itemAvailabilityMap;
  protected EventListenerList listenerList = new EventListenerList();
  
  public BooksTreeModel() {
    super();
  } 
  
  public Object getRoot() {
    return records;
  }
  
  public Object getChild(Object parent, int index) {
    if (parent instanceof List){
      return ((List)parent).get(index);
    } else if (parent instanceof Record){
      int prNo = ((Record)parent).getPrimerci().size();
      if ( prNo > index){
        return ((Record)parent).getPrimerci().get(index);
      }else{
        return ((Record)parent).getGodine().get(index - prNo);
      }
    } else if (parent instanceof Godina){
      return ((Godina)parent).getSveske().get(index);
    } else {
      return null;
    }
  }
  
  public int getChildCount(Object parent) {
    if (parent instanceof List){
      return ((List)parent).size();
    } else if (parent instanceof Record){
      return ((Record)parent).getPrimerci().size() + ((Record)parent).getGodine().size();
    } else if (parent instanceof Godina){
      return ((Godina)parent).getSveske().size();
    } else {
      return 0;
    }
  }
  
  public boolean isLeaf(Object node) {
    if (node instanceof List){
      return false;
    } else if (node instanceof Record){
      return false;
    } else if (node instanceof Godina){
      return false;
    } else if (node instanceof Primerak){
      return true;
    } else if (node instanceof Sveska){
      return true;
    } else {
      return true;
    }
  }
  
  public void valueForPathChanged(TreePath path, Object newValue) {
    
  }
  
  public int getIndexOfChild(Object parent, Object child) {
    if (parent != null){
      if (parent instanceof List){
        return ((List)parent).indexOf(child);
      } else if (parent instanceof Record){
        int ind = ((Record)parent).getPrimerci().indexOf(child);
        if (ind == -1){
          ind = ((Record)parent).getGodine().indexOf(child);
        }
        return ind;
      } else if (parent instanceof Godina){
        return ((Godina)parent).getSveske().indexOf(child);
      } else {
        return -1;
      } 
    } else {
      return -1;
    }
  }
  
  public void addTreeModelListener(TreeModelListener l) {
    listenerList.add(TreeModelListener.class, l);
  }
  
  public void removeTreeModelListener(TreeModelListener l) {
    listenerList.remove(TreeModelListener.class, l);
  }
  
  public void setHits(ArrayList<String> recIDs, String library) {
    try {
      records = new ArrayList<Record>();
      itemAvailabilityMap = new HashMap<String, ItemAvailability>();
      List<RecordResponseWrapper> list = BisisApp.recMgr.getRecordsAllData(recIDs);
      for (RecordResponseWrapper wrapper : list){
          if (wrapper.getFullRecord() != null){
              if (library != null) {
                  List<Primerak> primerci = new ArrayList();
                  for (Primerak p : wrapper.getFullRecord().getPrimerci()) {
                      String invStart = p.getInvBroj().substring(0, 2);
                      if (invStart.equals(library)) {
                        primerci.add(p);
                      }
                  }
                  wrapper.getFullRecord().setPrimerci(primerci);
              }
              records.add(wrapper.getFullRecord());
              List<ItemAvailability> items = wrapper.getListOfItems();
              for (ItemAvailability item : items){
                  itemAvailabilityMap.put(item.getCtlgNo(), item);
              }
          }
      }
      fireTreeStructureChanged(records);
    } catch (Exception ex) {
      ex.printStackTrace();
      //TODO log
    }
  }

  public Boolean isBorrowed(String ctlgno){
    if (itemAvailabilityMap.get(ctlgno) != null) {
      return itemAvailabilityMap.get(ctlgno).getBorrowed();
    } else {
      return false;
    }
  }

  public void setBorrowed(String ctlgno, boolean borrowed){
      itemAvailabilityMap.get(ctlgno).setBorrowed(borrowed);
  }
  
  public void clear(){
    records.clear();
    fireTreeStructureChanged(records);
  }  
  
  private void fireTreeStructureChanged(Object root) {
    Object[] listeners = listenerList.getListenerList();
    TreeModelEvent e = null;
    for (int i = listeners.length-2; i>=0; i-=2) {
        if (listeners[i]==TreeModelListener.class) {
            if (e == null)
                e = new TreeModelEvent(this, new Object[] {root});
            ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
        }
    }
}
  

}
