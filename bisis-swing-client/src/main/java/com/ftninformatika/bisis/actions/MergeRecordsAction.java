package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.editor.Obrada;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class MergeRecordsAction extends AbstractAction {

	public MergeRecordsAction() {
    putValue(SHORT_DESCRIPTION, "спајање записа");
    putValue(NAME, "Merge");
    /*putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/new_record.gif")));*/
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
	
	public void actionPerformed(ActionEvent arg0) {
		Obrada.openMergeRecordsFrame();
	}

}
