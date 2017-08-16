package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;


public class CircNewGroupAction extends AbstractAction {

  public CircNewGroupAction() {
    putValue(SHORT_DESCRIPTION, "Novi kolektivni korisnik");
    putValue(NAME, "Kolektivni");
    putValue(SMALL_ICON, new ImageIcon(getClass().getResource(
        "/circ-images/add_user_group_boldbold16.png")));
  }
  
  public void actionPerformed(ActionEvent ev) {
    if (Cirkulacija.getApp().getMainFrame().getBlank()){
    	Cirkulacija.getApp().getMainFrame().getGroupPanel().loadDefault();
    	Cirkulacija.getApp().getMainFrame().showPanel("groupPanel");
    }
  }
}
