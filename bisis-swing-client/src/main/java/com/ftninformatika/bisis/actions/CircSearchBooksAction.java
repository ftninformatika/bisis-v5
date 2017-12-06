package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class CircSearchBooksAction extends AbstractAction {

  public CircSearchBooksAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.searchpublications"));
    putValue(NAME, Messages.getString("actions.publications"));
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/circ-images/find_book16.png")));
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/circ-images/find_book24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("searchBooksPanel");
    }
  }
}
