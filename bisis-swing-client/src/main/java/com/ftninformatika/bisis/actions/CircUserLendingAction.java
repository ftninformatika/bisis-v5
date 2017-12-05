package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircUserLendingAction extends AbstractAction {

  public CircUserLendingAction() {
    putValue(SHORT_DESCRIPTION, "Задужења корисника");
    putValue(NAME, "Задужења");
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
  }
  
  public void actionPerformed(ActionEvent ev) {
   /* if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().setRequestedPanel(3);
      Cirkulacija.getApp().getMainFrame().getUserIDPanel().setVisible(true);
    }*/
  }
}
