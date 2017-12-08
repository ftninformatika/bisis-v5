package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircUserDataAction extends AbstractAction {

  public CircUserDataAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.existinguser"));
    putValue(NAME, Messages.getString("actions.data"));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/circ-images/user24.png")));
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(1);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    }
  }
}
