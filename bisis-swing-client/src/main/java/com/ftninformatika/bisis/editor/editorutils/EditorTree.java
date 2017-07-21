/**
 * 
 */
package com.ftninformatika.bisis.editor.editorutils;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * @author dimicb
 *
 */
public class EditorTree extends JTree {
  
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
  
  protected void handleCollapseOrExpandAll(KeyEvent e){
    switch (e.getKeyCode()){
      case(KeyEvent.VK_E):
        if(e.getModifiers() == InputEvent.SHIFT_MASK)
          expandAll();
        break;
      case(KeyEvent.VK_C):
        if(e.getModifiers() == InputEvent.SHIFT_MASK)
          collapseAll();
        break;   
         
     }
  }
  
  

}
