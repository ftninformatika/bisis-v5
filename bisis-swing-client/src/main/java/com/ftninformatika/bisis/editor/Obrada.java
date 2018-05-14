package com.ftninformatika.bisis.editor;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.groupinv.GroupInvFrame;
import com.ftninformatika.bisis.editor.merge.MergeRecordsFrame;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.fx.JFXInternalFrame.JFXInternalFrame;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyVetoException;


public class Obrada {

    public static EditorFrame editorFrame = new EditorFrame();
    public static GroupInvFrame groupInvFrame = new GroupInvFrame();
	/*public static InvNumberHolesFrame invHolesFrame = new InvNumberHolesFrame();*/
//	public static MergeRecordsFrame mergeRecFrame = new MergeRecordsFrame();

    public static JFXInternalFrame mergeRecFrame = new JFXInternalFrame("Merge",
            "/fx/merge/mergeFrame.fxml",
            "/fx/merge/css/mergeFrame.css",null);

    public static void newRecord(Record rec) {
        boolean editorClosed = true;
        if (editorFrame.isVisible()) {
            editorClosed = editorFrame.handleCloseEditor();
        }
        if (editorClosed) {
            CurrRecord.update = false;
            if (rec != null) rec.setRN(0);
            CurrRecord.savedOnce = false;
            editorFrame.setUploadEnabled(false);
            editorFrame.editorInitialize(rec);
            showEditorFrame();
            editorFrame.setRecordUpdated(false);
        }
    }

    public static void editRecord(Record rec) {
        boolean editorClosed = true;
        editorFrame.setUploadEnabled(true);
        if (editorFrame.isVisible()) {
            editorClosed = editorFrame.handleCloseEditor();
        }
        if (editorClosed) {
            CurrRecord.update = true;
            CurrRecord.savedOnce = false;
            editorFrame.editorInitialize(rec);
            showEditorFrame();
            editorFrame.setRecordUpdated(false);
            editorFrame.setUploadEnabled(true);
        }
    }

    // otvara se forma za inventarisanje zapisa rec
    public static void editInventar(Record rec) {
        boolean editorClosed = true;
        if (editorFrame.isVisible()) {
            editorClosed = editorFrame.handleCloseEditor();
        }
        if (editorClosed) {
            CurrRecord.update = true;
            CurrRecord.savedOnce = false;
            editorFrame.editorInitialize(rec);
            showEditorFrame();
            editorFrame.setRecordUpdated(false);
            editorFrame.showInventarPanel();
        }
    }

    // otvara novi zapis u editoru koji je analiticka obrada za zapis rec
    public static void editAnalitika(Record rec) {
        boolean editorClosed = true;
        if (editorFrame.isVisible()) {
            editorClosed = editorFrame.handleCloseEditor();
        }
        if (editorClosed) {
            CurrRecord.update = false;
            Record articleRec = RecordUtils.getAnalitickaObrada(rec);
            if (articleRec != null) articleRec.setRN(0);
            CurrRecord.savedOnce = false;
            editorFrame.editorInitialize(articleRec);
            showEditorFrame();
            editorFrame.setRecordUpdated(false);
        }
    }

    private static void showEditorFrame() {
        BisisApp.getMainFrame().showFrame(editorFrame);
    }


    public static boolean isEditorClosable() {
        if (isEditorOpened()) {
            return editorFrame.handleCloseEditor();
        }
        return true;
    }

    public static boolean isEditorOpened() {
        return editorFrame.isVisible();
    }

    public static void openGroupInvFrame() {
        BisisApp.getMainFrame().showFrame(groupInvFrame);
    }

    public static void openInvHolesFrame() {
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

    public static void openMergeRecordsFrame() {
  	try {
      if (!mergeRecFrame.isVisible())
      	mergeRecFrame.setVisible(true);
      if (mergeRecFrame.isIcon())
      	mergeRecFrame.setIcon(false);
      if (!mergeRecFrame.isSelected())
      	mergeRecFrame.setSelected(true);
    } catch (Exception ex) {
    }
    }


    static {
        BisisApp.getMainFrame().insertFrame(editorFrame);
        BisisApp.getMainFrame().insertFrame(groupInvFrame);
        //BisisApp.getMainFrame().insertFrame(invHolesFrame);
        BisisApp.getMainFrame().insertFrame(mergeRecFrame);
        try {
            editorFrame.setMaximum(true);
            groupInvFrame.setMaximum(true);
            mergeRecFrame.setSize(600, 500);
            mergeRecFrame.setResizable(false);
            mergeRecFrame.setMaximizable(false);
            // invHolesFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
        }
    }

}
