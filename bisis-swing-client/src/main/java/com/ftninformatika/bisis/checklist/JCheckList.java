package com.ftninformatika.bisis.checklist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JCheckList extends JList {

  public JCheckList() {
    super();
    init();
  }

  public JCheckList(CheckableItem[] items) {
    super(items);
    init();
  }

  private void init() {
    setCellRenderer(new JCheckListRenderer());
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setBorder(new EmptyBorder(0,4,0,0));
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int index = locationToIndex(e.getPoint());
        CheckableItem item = (CheckableItem)getModel().getElementAt(index);
        item.setSelected(! item.isSelected());
        Rectangle rect = getCellBounds(index, index);
        repaint(rect);
      }
    });
  }
}
