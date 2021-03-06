package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircNewUserAction extends AbstractAction {

  public CircNewUserAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.newsolouser"));
    putValue(NAME, Messages.getString("actions.individual"));
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/circ-images/add_user_bold16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/circ-images/add_user_bold24.png")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    //putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_I));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().getUserPanel().loadDefault();
      Cirkulacija.getApp().getMainFrame().showPanel("userPanel");
    }
  }
}
