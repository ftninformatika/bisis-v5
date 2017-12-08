package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircSearchUsersAction extends AbstractAction {

  public CircSearchUsersAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.searchuser"));
    putValue(NAME, Messages.getString("actions.users"));
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/circ-images/find_user16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/circ-images/find_user24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("searchUsersPanel");
    }
  }
}
