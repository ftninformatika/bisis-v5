package com.ftninformatika.bisis.circ.view;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.models.circ.pojo.MembershipType;
import com.ftninformatika.bisis.models.circ.pojo.UserCategory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class MmbrshipCoder extends JInternalFrame {

  private JPanel jContentPane = null;
  private JScrollPane jScrollPane = null;
  private JScrollPane spUserCategs = null;
  private JScrollPane spMmbrTypes = null;
  private JTable jTable = null;
  private JList userCategsList = null;
  private JList mmbrTypesList = null;
  private JTextField tfCost = null;
  private JButton btnAdd = null;
  private JButton btnDelete = null;
  private PanelBuilder pb = null;
  private DefaultListModel userCategsModel = null;
  private DefaultListModel mmbrTypesModel = null;
  private ComboBoxRenderer cmbRenderer = null;
  private MmbrshipCoderTableModel tableModel = null;

  public MmbrshipCoder() {
    super("\u010clanarina", true, true, true, true);
    initialize();
  }

  private void initialize() {
    this.setSize(500, 500);
    this.setContentPane(getJContentPane());
    this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE); 
  }

  private JPanel getJContentPane() {
    if (jContentPane == null) {
      jContentPane = new JPanel();
      jContentPane.setLayout(new BorderLayout());
      jContentPane.add(getPanel(), BorderLayout.CENTER);
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
  
  private JScrollPane getSpUserCateegs() {
    if (spUserCategs == null) {
      spUserCategs = new JScrollPane();
      spUserCategs.setViewportView(getUserCategsList());
    }
    return spUserCategs;
  }
  
  private JScrollPane getSpMmbrTypes() {
    if (spMmbrTypes == null) {
      spMmbrTypes = new JScrollPane();
      spMmbrTypes.setViewportView(getMmbrTypesList());
    }
    return spMmbrTypes;
  }

  private JTable getJTable() {
    if (jTable == null) {
      jTable = new JTable();
      jTable.setModel(getTableModel());
    }
    return jTable;
  }
  
  private MmbrshipCoderTableModel getTableModel(){
    if (tableModel == null){
      tableModel = new MmbrshipCoderTableModel();
    }
    return tableModel;
  }

  private JPanel getPanel() {
    if (pb == null) {
      FormLayout layout = new FormLayout(
          "2dlu:grow, left:120dlu, 15dlu, 30dlu, right:50dlu, 2dlu:grow", 
          "5dlu, pref, 15dlu, 5dlu, 15dlu, 10dlu, pref, 15dlu, 5dlu, 15dlu, 15dlu, 150dlu, 2dlu:grow");
      CellConstraints cc = new CellConstraints();
      pb = new PanelBuilder(layout);
      pb.setDefaultDialogBorder();
      
      pb.addSeparator("Vrsta \u010dlanstva", cc.xy(2,2));
      pb.add(getSpMmbrTypes(), cc.xywh(2,3,1,3,"fill, fill"));
      pb.addSeparator("Kategorija korisnika", cc.xy(2,7));
      pb.add(getSpUserCateegs(), cc.xywh(2,8,1,3,"fill, fill"));
      
      pb.addSeparator("Cena", cc.xyw(4,3,2));
      pb.add(getTfCost(), cc.xyw(4,5,2));
      pb.add(getBtnAdd(), cc.xy(5,8,"fill, fill"));
      pb.add(getBtnDelete(), cc.xy(5,10,"fill, fill"));
      
      pb.add(getJScrollPane(), cc.xyw(2,12,4));
      
    }
    return pb.getPanel();
  }

  private ComboBoxRenderer getCmbRenderer(){
    if (cmbRenderer == null){
      cmbRenderer = new ComboBoxRenderer();
    }
    return cmbRenderer;
  }
  
  private JList getUserCategsList() {
    if (userCategsList == null) {
      userCategsList = new JList();
      userCategsList.setCellRenderer(getCmbRenderer());
      userCategsList.setModel(getUserCategsModel());
    }
    return userCategsList;
  }
  
  private DefaultListModel getUserCategsModel() {
    if (userCategsModel == null){
      userCategsModel = new DefaultListModel();
      List data = BisisApp.appConfig.getCodersHelper()
              .getUserCategories().values().stream()
              .map(i -> {
                UserCategory u = new UserCategory();
                u.setDescription(i.getDescription());
                u.setMaxPeriod(i.getMaxPeriod());
                u.setPeriod(i.getPeriod());
                u.setTitlesNo(i.getTitlesNo());
                return u;
              })
              .collect(Collectors.toList());
      if (data != null){
        for (Object o : data){
          userCategsModel.addElement(o);
        }
      }
    }
    return userCategsModel;
  }

  private JList getMmbrTypesList() {
    if (mmbrTypesList == null) {
      mmbrTypesList = new JList();
      mmbrTypesList.setCellRenderer(getCmbRenderer());
      mmbrTypesList.setModel(getMmbrTypesModel());
    }
    return mmbrTypesList;
  }
  
  private DefaultListModel getMmbrTypesModel() {
    if (mmbrTypesModel == null){
      mmbrTypesModel = new DefaultListModel();
      List data = BisisApp.appConfig.getCodersHelper()
              .getMembershipTypes().values().stream()
              .map(i -> {
                MembershipType m = new MembershipType();
                m.setDescription(i.getDescription());
                m.setPeriod(i.getPeriod());
                return m;
              })
              .collect(Collectors.toList());
      if (data != null){
        for (Object o : data){
          mmbrTypesModel.addElement(o);
        }
      }
    }
    return mmbrTypesModel;
  }

  private JTextField getTfCost() {
    if (tfCost == null) {
      tfCost = new JTextField();
    }
    return tfCost;
  }

  private JButton getBtnAdd() {
    if (btnAdd == null) {
      btnAdd = new JButton();
      btnAdd.setText("Dodaj");
      btnAdd.setIcon(new ImageIcon(getClass().getResource("/circ-images/down16.png")));
      btnAdd.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          List usercategs = getUserCategsList().getSelectedValuesList();
          List mmbrtypes = getMmbrTypesList().getSelectedValuesList();
          
          if (mmbrtypes == null || mmbrtypes.size() == 0){
            JOptionPane.showMessageDialog(null, "Kategorija korsinika nije selektovana!", "Greska", JOptionPane.ERROR_MESSAGE,
                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
          } else if (usercategs == null || usercategs.size() == 0){
            JOptionPane.showMessageDialog(null, "Vrsta \u010dlanstva nije selektovana!", "Greska", JOptionPane.ERROR_MESSAGE,
                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
          } else if (getTfCost().getText().equals("")){
            JOptionPane.showMessageDialog(null, "Cena nije uneta!", "Greska", JOptionPane.ERROR_MESSAGE,
                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
          } else {
            try {
              Double cost = Double.parseDouble(getTfCost().getText());
              for (int i = 0; i < mmbrtypes.size(); i++){
                for (int j = 0; j < usercategs.size(); j++){
                  getTableModel().addRow((MembershipType)mmbrtypes.get(i), (UserCategory)usercategs.get(j), cost);
                }
              }
            } catch (NumberFormatException e1) {
              JOptionPane.showMessageDialog(null, "Cena nije validna!", "Greska", JOptionPane.ERROR_MESSAGE,
                  new ImageIcon(getClass().getResource("circ-images/x32.png")));
            }
          }
        }
      });
    }
    return btnAdd;
  }

  private JButton getBtnDelete() {
    if (btnDelete == null) {
      btnDelete = new JButton();
      btnDelete.setText("Obrisi");
      btnDelete.setIcon(new ImageIcon(getClass().getResource("/com/gint/app/bisis4/client/circ/images/Delete16.png")));
      btnDelete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          if (getJTable().getSelectedRow() != -1){
            getTableModel().removeRows(getJTable().getSelectedRows());
          }else{
            JOptionPane.showMessageDialog(null, "Nista nije selektovano!", "Greska", JOptionPane.ERROR_MESSAGE,
                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
          }
        }
      });
    }
    return btnDelete;
  }

}
