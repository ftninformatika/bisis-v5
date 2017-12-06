package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class CircReportAction extends AbstractAction {

  public CircReportAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.reports"));
    putValue(NAME, "Извештаји");
    putValue(LARGE_ICON_KEY, new ImageIcon(getClass().getResource(
        "/circ-images/files_text24.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
      Cirkulacija.getApp().getMainFrame().showPanel("reportPanel");
    }
  }
}
