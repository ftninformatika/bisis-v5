package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Warnings extends JPanel {

  private static final long serialVersionUID = 1L;
  private JScrollPane jScrollPane = null;
  private JTable jTable = null;
  private WarningsTableModel tableModel = null;
  private User parent = null;

  
  public Warnings(User parent) {
    super();
    this.parent = parent;
    initialize();
  }

  private void initialize() {
    this.setSize(300, 200);
    this.setLayout(new BorderLayout());
    this.add(getJScrollPane(), BorderLayout.CENTER);
  }

  private JScrollPane getJScrollPane() {
    if (jScrollPane == null) {
      jScrollPane = new JScrollPane();
      jScrollPane.setViewportView(getJTable());
    }
    return jScrollPane;
  }

  private JTable getJTable() {
    if (jTable == null) {
      jTable = new JTable();
      jTable.setModel(getTableModel());
    }
    return jTable;
  }
  
  private WarningsTableModel getTableModel() {
    if (tableModel == null) {
      tableModel = new WarningsTableModel();
//      tableModel.addTableModelListener(new TableModelListener() {
//        public void tableChanged(TableModelEvent e){
//          handleKeyTyped();
//        }
//      });
    }
    return tableModel;
  }
  
  public void setData(List data){
    getTableModel().setData(data);
  }
  
  private void handleKeyTyped(){
    if (!parent.getDirty())
      parent.setDirty(true);
  }

}
