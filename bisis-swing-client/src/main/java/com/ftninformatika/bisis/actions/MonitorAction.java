package com.ftninformatika.bisis.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import com.ftninformatika.utils.Messages;

public class MonitorAction extends AbstractAction {

  public MonitorAction() {
    putValue(SHORT_DESCRIPTION, Messages.getString("actions.sysconfiguration"));
    putValue(NAME, Messages.getString("actions.monitor"));
  }
  
  public void actionPerformed(ActionEvent ev) {
    //MonitorFrame memoryMonitor = new MonitorFrame();
    //memoryMonitor.setVisible(true);
    System.out.println("Monitor frejm");
  }
}
