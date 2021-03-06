package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.editor.Obrada;
import com.ftninformatika.utils.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;


public class InvHolesAction extends AbstractAction {

	public InvHolesAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.findinginvholes"));
    putValue(NAME, Messages.getString("actions.invholes"));
    /*putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/new_record.gif")));*/
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F1));
  }
	
	public void actionPerformed(ActionEvent arg0) {
		Obrada.openInvHolesFrame();
	}

}
