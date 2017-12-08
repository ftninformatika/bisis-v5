package com.ftninformatika.bisis.actions;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Cirkulacija;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;


public class CircNewGroupAction extends AbstractAction {

  public CircNewGroupAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.newgroupuser"));
    putValue(NAME, Messages.getString("actions.corporate"));
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
