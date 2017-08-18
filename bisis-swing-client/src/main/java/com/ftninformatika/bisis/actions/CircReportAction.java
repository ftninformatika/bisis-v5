package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircReportAction extends AbstractAction {

  public CircReportAction() {
    putValue(SHORT_DESCRIPTION, "Izve\u0161taji");
    putValue(NAME, "Izve\u0161taji");
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/new-circ-images/Report-32.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("reportPanel");
    }
  }
}
