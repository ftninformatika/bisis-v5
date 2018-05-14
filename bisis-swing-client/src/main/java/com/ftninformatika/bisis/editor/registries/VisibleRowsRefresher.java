package com.ftninformatika.bisis.editor.registries;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.Timer;

public class VisibleRowsRefresher {

  public VisibleRowsRefresher(JTable table, JScrollBar scrollBar) {
    this.table = table;
    this.scrollBar = scrollBar;
    registryType = ((RegistryTableModel)table.getModel()).getRegistryType();
    tableModel = (RegistryTableModel)table.getModel();
  }
  
  public boolean isStarted() {
    return started;
  }
  
  public void start() {
    timer.start();
    started = true;
  }
  
  public void stop() {
    timer.stop();
    started = false;
  }
  
  private boolean started = false;
  private Timer timer = new Timer(100, new AbstractAction() {
    public void actionPerformed(ActionEvent ev) {
      loadVisiblePartOfTable();
      if (!scrollBar.getValueIsAdjusting())
        VisibleRowsRefresher.this.stop();
    }
  });
  
  public void loadVisiblePartOfTable() {
    Rectangle visibleRowsRect = table.getVisibleRect();
    final int x = visibleRowsRect.x + 5;
    final int y = visibleRowsRect.y + 5;
    final int from = table.rowAtPoint(new Point(x, y));
    final int to = table.rowAtPoint(new Point(x, visibleRowsRect.y + visibleRowsRect.height - 5));
    if (from <= -1 || to <= -1)
      return;
    for (int i = from; i <= to; i++) {
      final RegistryItem item = tableModel.getRow(i);
      if (item != null)
        continue;
      int index = i+1;
      try {
       // tableModel.getResultSet().absolute(index);
        RegistryItem newItem = new RegistryItem();
        newItem.setIndex(index);
        newItem.setText1((String)tableModel.getResults().get(index-1).get(0));
        if (registryType == Registries.AUTORI || registryType == Registries.UDK)
          newItem.setText2((String)tableModel.getResults().get(index-1).get(1));
        tableModel.setRow(i, newItem);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    tableModel.fireTableRowsUpdated(from, to);
  }
  
  private JTable table;
  private JScrollBar scrollBar;
  private int registryType;
  private RegistryTableModel tableModel;
}
