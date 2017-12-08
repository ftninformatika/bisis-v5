package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.editor.Obrada;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class NewRecordAction extends AbstractAction {

  public NewRecordAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.openingnewrecord"));
    putValue(NAME, Messages.getString("actions.newrecord"));
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/icons/new_record.gif")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
  
  public void actionPerformed(ActionEvent ev) {
	  Obrada.newRecord(null);
  }

}
