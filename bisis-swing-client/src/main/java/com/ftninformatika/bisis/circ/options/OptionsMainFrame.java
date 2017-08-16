package com.ftninformatika.bisis.circ.options;

import com.ftninformatika.bisis.BisisApp;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class OptionsMainFrame extends JInternalFrame {

  private JPanel jContentPane = null;
  private JScrollPane jScrollPane = null;
  private JTable jTable = null;
  private JPanel bottomPanel = null;
  private JButton btnAdd = null;
  private JButton btnDelete = null;
  private JButton btnEdit = null;
  private OptionsTableModel tableModel = null;
  private OptionsFrame optionsFrame = null;

  public OptionsMainFrame() {
    super("Ra\u010dunari", true, true, true, true);
    //Manager.loadDocs();
    initialize();
  }

  private void initialize() {
    this.setSize(300, 250);
    this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    this.setContentPane(getJContentPane());
    this.pack();
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
      jContentPane.add(getBottomPanel(), BorderLayout.SOUTH);
    }
    return jContentPane;
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
  
  private OptionsTableModel getTableModel(){
    if (tableModel == null){
      tableModel = new OptionsTableModel();
      tableModel.setDoc(EnvironmentOptions.getDoc());
    }
    return tableModel;
  }

  private JPanel getBottomPanel() {
    if (bottomPanel == null) {
      bottomPanel = new JPanel();
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setHgap(30);
      bottomPanel.setLayout(flowLayout);
      bottomPanel.add(getBtnAdd(), null);
      bottomPanel.add(getBtnDelete(), null);
      bottomPanel.add(getBtnEdit(), null);
    }
    return bottomPanel;
  }

  private JButton getBtnAdd() {
    if (btnAdd == null) {
      btnAdd = new JButton();
      btnAdd.setText("Dodaj");
      btnAdd.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png")));
      btnAdd.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          getOptionsFrame().loadData(-1);
        }
      });
    }
    return btnAdd;
  }

  private JButton getBtnDelete() {
    if (btnDelete == null) {
      btnDelete = new JButton();
      btnDelete.setText("Obri\u0161i");
      btnDelete.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png")));
      btnDelete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          if (getJTable().getSelectedRow() != -1){
            getTableModel().removeRow(getJTable().getSelectedRow());
            EnvironmentOptions.save();
          }
          
        }
      });
    }
    return btnDelete;
  }

  private JButton getBtnEdit() {
    if (btnEdit == null) {
      btnEdit = new JButton();
      btnEdit.setText("Izmeni");
      btnEdit.setIcon(new ImageIcon(getClass().getResource("/circ-images/doc_rich16.png")));
      btnEdit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          if (getJTable().getSelectedRow() != -1)
            getOptionsFrame().loadData(getJTable().getSelectedRow());
        }
      });
    }
    return btnEdit;
  }
  
  private OptionsFrame getOptionsFrame(){
    if (optionsFrame == null){
      optionsFrame = new OptionsFrame(this);
      BisisApp.getMainFrame().insertFrame(optionsFrame);
    }
    return optionsFrame;
  }
  
  public void save(){
    getTableModel().fireTableDataChanged();
  }

}
