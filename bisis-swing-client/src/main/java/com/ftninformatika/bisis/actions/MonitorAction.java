package com.ftninformatika.bisis.actions;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


public class MonitorAction extends AbstractAction {

  public MonitorAction() {
    putValue(SHORT_DESCRIPTION, "Карактерисике система");
    putValue(NAME, "Монитор");
  }
  
  public void actionPerformed(ActionEvent ev) {
    //MonitorFrame memoryMonitor = new MonitorFrame();
    //memoryMonitor.setVisible(true);
    System.out.println("Monitor frejm");
  }
}
