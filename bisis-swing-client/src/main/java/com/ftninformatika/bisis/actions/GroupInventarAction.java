package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.editor.Obrada;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class GroupInventarAction extends AbstractAction {


	public GroupInventarAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.invdatachangeformultiplerecords"));
    putValue(NAME, Messages.getString("actions.groupinv"));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
  }
	
	public void actionPerformed(ActionEvent arg0) {
		Obrada.openGroupInvFrame();
	}

}
