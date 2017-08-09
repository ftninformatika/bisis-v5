package com.ftninformatika.bisis.editor;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.groupinv.GroupInvFrame;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.Record;

import java.beans.PropertyVetoException;


public class Obrada {	
	
	public static EditorFrame editorFrame = new EditorFrame();
	public static GroupInvFrame groupInvFrame = new GroupInvFrame();
	/*public static InvNumberHolesFrame invHolesFrame = new InvNumberHolesFrame();
	public static MergeRecordsFrame mergeRecFrame = new MergeRecordsFrame();*/
	
	public static void newRecord(Record rec){
		boolean editorClosed = true;
		if(editorFrame.isVisible()){
			 editorClosed = editorFrame.handleCloseEditor();
		}
		if(editorClosed){		 
			CurrRecord.update = false;
			if (rec!=null)rec.setRN(0);			
			CurrRecord.savedOnce = false;
			editorFrame.setUploadEnabled(false);
			editorFrame.editorInitialize(rec);
			showEditorFrame();
   			editorFrame.setRecordUpdated(false);
		}
	}
	
	public static void editRecord(Record rec){
		boolean editorClosed = true;
		editorFrame.setUploadEnabled(true);
		if(editorFrame.isVisible()){
			 editorClosed = editorFrame.handleCloseEditor();
		}
		if(editorClosed){			
			CurrRecord.update = true;
			CurrRecord.savedOnce = false;
			editorFrame.editorInitialize(rec);
			showEditorFrame(); 
			editorFrame.setRecordUpdated(false);
			editorFrame.setUploadEnabled(true);
		}
	}
	
	// otvara se forma za inventarisanje zapisa rec
	
	public static void editInventar(Record rec){		
		boolean editorClosed = true;
		if(editorFrame.isVisible()){
			 editorClosed = editorFrame.handleCloseEditor();
		}
		if(editorClosed){
			CurrRecord.update = true;
			CurrRecord.savedOnce = false;
			editorFrame.editorInitialize(rec);
			showEditorFrame(); 
			editorFrame.setRecordUpdated(false);
			editorFrame.showInventarPanel();
		}
	}
	
	// otvara novi zapis u editoru koji je analiticka obrada za zapis rec
	
	public static void editAnalitika(Record rec){
		boolean editorClosed = true;
		if(editorFrame.isVisible()){
			 editorClosed = editorFrame.handleCloseEditor();
		}
		if(editorClosed){
		 CurrRecord.update = false;
		 Record articleRec = RecordUtils.getAnalitickaObrada(rec);
			if (articleRec!=null)articleRec.setRN(0);
			CurrRecord.savedOnce = false;
			editorFrame.editorInitialize(articleRec);
			showEditorFrame();
   editorFrame.setRecordUpdated(false);
		}
	}
	
	private static void showEditorFrame() {
	    try {
	      if (!editorFrame.isVisible())
	        editorFrame.setVisible(true);
	      if (editorFrame.isIcon())
	        editorFrame.setIcon(false);
	      if (!editorFrame.isSelected())
	        editorFrame.setSelected(true);
	    } catch (Exception ex) {
	    }
	  }
	
	
  
  public static boolean isEditorClosable(){
    if(isEditorOpened()){
      return editorFrame.handleCloseEditor();
    }
    return true; 
  }
	
  public static boolean isEditorOpened(){
    return editorFrame.isVisible();
  }
  
  public static void openGroupInvFrame(){  	
  	try {
      if (!groupInvFrame.isVisible())
      	groupInvFrame.setVisible(true);
      if (groupInvFrame.isIcon())
      	groupInvFrame.setIcon(false);
      if (!groupInvFrame.isSelected())
      	groupInvFrame.setSelected(true);
    } catch (Exception ex) {
    }
  }
  
  public static void openInvHolesFrame(){
  /*	try {
      if (!invHolesFrame.isVisible())
      	invHolesFrame.setVisible(true);
      if (invHolesFrame.isIcon())
      	invHolesFrame.setIcon(false);
      if (!invHolesFrame.isSelected())
      	invHolesFrame.setSelected(true);
    } catch (Exception ex) {
    }*/
  }
  
  public static void openMergeRecordsFrame(){
  	/*try {
      if (!mergeRecFrame.isVisible())
      	mergeRecFrame.setVisible(true);
      if (mergeRecFrame.isIcon())
      	mergeRecFrame.setIcon(false);
      if (!mergeRecFrame.isSelected())
      	mergeRecFrame.setSelected(true);
    } catch (Exception ex) {
    }  	*/
  }
  
  
	static{
		BisisApp.getMainFrame().insertFrame(editorFrame);
		BisisApp.getMainFrame().insertFrame(groupInvFrame);
		//BisisApp.getMainFrame().insertFrame(invHolesFrame);
		//BisisApp.getMainFrame().insertFrame(mergeRecFrame);
    try {
      editorFrame.setMaximum(true);
      //groupInvFrame.setMaximum(true);
     // invHolesFrame.setMaximum(true);
    }catch (PropertyVetoException e){
    } 
	}

}
