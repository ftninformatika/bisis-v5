package com.ftninformatika.bisis.editor;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.formattree.CurrFormat;
import com.ftninformatika.bisis.editor.formattree.FormatTree;
import com.ftninformatika.bisis.editor.recordtree.RecordTree;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.format.UValidatorException;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;

import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class ZapisPanel extends JPanel {
  
  private FormatTree formatTree;
  private RecordTree recordTree; 
  public ZapisPanel(){
    setName("Запис"); //$NON-NLS-1$
    create();
    addActionListeners();
  }
  
  public FormatTree getFormatTree(){
    return formatTree;
  }
  
  public RecordTree getRecordTree(){
    return recordTree;
  }
  
  public void initializeRecordPanel(Record rec){
    formatTree.initializeFormatTree(rec);
    recordTree.recordInitialize(rec);
    recordTree.setEditorWidth(400);
  }
  
  private void create(){
    GridLayout gl = new GridLayout(1,1);
    // split pane
    
    // format tree    
    formatTree = new FormatTree();
    JPanel formatPanel = new JPanel(gl);
    JScrollPane formatScroll = new JScrollPane(formatTree);
    formatPanel.add(formatScroll);      
    
    // record tree    
    recordTree = new RecordTree();
    JPanel recordPanel = new JPanel(gl);
    JScrollPane recordScroll = new JScrollPane(recordTree); 
    recordScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    recordPanel.add(recordScroll);    
    
    // split pane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        formatPanel,recordPanel);   
    splitPane.setDividerLocation(500);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerSize(20);   
    setLayout(gl);
    add(splitPane);    
    setFocusCycleRoot(true);     
  }
  
  private void addActionListeners() {
    addComponentListener(new ComponentAdapter(){
      public void componentShown(ComponentEvent arg0) {  
        recordTree.requestFocus();
      }      
    });
    
    formatTree.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){         
          if(formatTree.getSelectionPath().getLastPathComponent() instanceof USubfield){
                handleAddNewElement();
          }
        }
      }
    }); 
    
    formatTree.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
          if(e.getModifiers() == InputEvent.SHIFT_MASK 
              && formatTree.getSelectionPath().getLastPathComponent() instanceof UField){
            handleAddNewField((UField)formatTree.getSelected());            
          }
          if(formatTree.getSelectionPath().getLastPathComponent() instanceof USubfield)
            handleAddNewElement();  
          }          
      }
    });   
    
    recordTree.addTreeSelectionListener(new TreeSelectionListener(){
      public void valueChanged(TreeSelectionEvent e) {
        if(e.getPath().getPathCount()>1)
          if(e.getPath().getPathComponent(1) instanceof Field){         
            Field f = (Field)e.getPath().getPathComponent(1);
            formatTree.setSecondaryMode(f.getName());
            recordTree.setSelectedField();
          }         
      }     
    });    
  }
  
  private void handleAddNewElement(){
    try {
      if(recordTree.getSelectedField()!=null && (recordTree.getSelectedField().getName().equals(((USubfield)formatTree.getSelected()).getOwner().getName())
      		|| CurrFormat.block4.contains(recordTree.getSelectedField().getName()))){
        recordTree.setSelectedField();
        recordTree.insertNewEmptyElement(formatTree.getSelected());    
      }
    } catch (UValidatorException e1) {
      JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e1.getMessage(),Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);      //$NON-NLS-1$
    }             
  }
  
  public void handleAddNewField(UField uf){
    try {
      recordTree.insertNewField(uf, false);      
    } catch (UValidatorException e) {
      JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);   //$NON-NLS-1$
    }   
  }
  
  public void handleAddEmptyField(UField uf){
   try {
     recordTree.insertNewField(uf, true);      
   } catch (UValidatorException e) {
     JOptionPane.showMessageDialog(BisisApp.getMainFrame(),e.getMessage(),Messages.getString("EDITOR_ERROR"),JOptionPane.ERROR_MESSAGE);   //$NON-NLS-1$
   }   
 }

}
