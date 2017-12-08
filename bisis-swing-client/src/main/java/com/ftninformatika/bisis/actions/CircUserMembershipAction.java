package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircUserMembershipAction extends AbstractAction {

  public CircUserMembershipAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.dues"));
    putValue(NAME, Messages.getString("actions.memebershipfee"));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(2);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true); 
    }
  }
}
