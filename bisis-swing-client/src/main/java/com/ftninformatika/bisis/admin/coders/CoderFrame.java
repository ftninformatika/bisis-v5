package com.ftninformatika.bisis.admin.coders;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;


import com.ftninformatika.bisis.BisisApp;
import net.miginfocom.swing.MigLayout;

public class CoderFrame extends JInternalFrame {
  
  public CoderFrame(Table table) {
    super(table.getCaption(), true, true, true, true);
    setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    this.table = table;
    tblModel = new CoderTableModel(table);
    tblData.setModel(tblModel);
    tblData.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tblData.setRowSelectionAllowed(true);
    tblData.setColumnSelectionAllowed(false);
    tblData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    spData.setViewportView(tblData);
    spData.setPreferredSize(new Dimension(400, 300));
    btnAdd.setIcon(new ImageIcon(getClass().getResource(
        "/icons/add.gif")));
    btnDelete.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));
    btnUpdate.setIcon(new ImageIcon(getClass().getResource(
        "/icons/commit.gif")));
    btnRefresh.setIcon(new ImageIcon(getClass().getResource(
        "/icons/refresh.gif")));
    btnAdd.setToolTipText(Messages.getString("newline"));
    btnUpdate.setToolTipText(Messages.getString("updaterow"));
    btnDelete.setToolTipText(Messages.getString("deleterow"));
    btnRefresh.setToolTipText(Messages.getString("refreshtable"));
    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[]rel[]rel[]rel[grow]rel[]",
        "[]rel[grow]para[]");
    /*
     * layout bez delete dugmeta
     * MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[]rel[]rel[grow]rel[]",
        "[]rel[grow]para[]");*/
    setLayout(layout);
    add(btnAdd, "");
    add(btnUpdate, "");
    add(btnDelete, "");
    add(new JLabel(" "));
    add(btnRefresh, "wrap");
    add(spData, "span 5, wrap, grow");
    add(btnClose, "span 5, wrap, tag ok");
    pack();
    
    btnAdd.setFocusable(false);
    btnUpdate.setFocusable(false);
    btnDelete.setFocusable(false);
    btnRefresh.setFocusable(false);
    btnClose.setFocusable(false);
    btnRefresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	try {
      		tblModel.refresh();
      	} catch (Exception ex){
      		JOptionPane.showMessageDialog(
  					BisisApp.getMainFrame(),
  					ex.getMessage(),
                    Messages.getString("error"),JOptionPane.ERROR_MESSAGE);
      	}
        if (tblModel.getRowCount() > 0)
          tblData.setRowSelectionInterval(0, 0);
        tblModel.fireTableDataChanged();
      }
    });
    
    btnDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int row = tblData.getSelectedRow();
        if (row == -1)
          return;
        try {
        	tblModel.delete(row);
		} catch (Exception e1) {					
			JOptionPane.showMessageDialog(
					BisisApp.getMainFrame(),
					e1.getMessage(),
                    Messages.getString("error"),JOptionPane.ERROR_MESSAGE);
		}
        if (tblModel.getRowCount() > 0)
          tblData.setRowSelectionInterval(0, 0);
      }
    });
    btnUpdate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int row = tblData.getSelectedRow();
        if (row == -1)
          return;
        try{
	        tblModel.update(row);
	        tblData.setRowSelectionInterval(row, row);
        } catch (Exception ex){
      		JOptionPane.showMessageDialog(
  					BisisApp.getMainFrame(),
  					ex.getMessage(),
                    Messages.getString("error"),JOptionPane.ERROR_MESSAGE);
      	}
      }
    });
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handleAdd();
      }
    });
    
    btnClose.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {       
        setVisible(false);
      }      
    });
  }
  
  public void setVisible(boolean visible) {
    if (visible) {
    	try {
    		tblModel.refresh();
    	} catch (Exception e){
    		JOptionPane.showMessageDialog(
					BisisApp.getMainFrame(),
					e.getMessage(),
                    Messages.getString("error"),JOptionPane.ERROR_MESSAGE);
    	}
      if (tblModel.getRowCount() > 0)
        tblData.setRowSelectionInterval(0, 0);
      tblData.requestFocus();
    }
    super.setVisible(visible);
  }
  
  private void handleAdd() {
    ArrayList<String> row = new ArrayList<String>();
    for (Column c : table.getColumns()) {
      String svalue = JOptionPane.showInternalInputDialog(
          this, MessageFormat.format(Messages.getString("inputfield"), c.getCaption()),
              Messages.getString("newline"), JOptionPane.QUESTION_MESSAGE);
      		if (svalue == null || svalue.equals(""))
      			return;
      		row.add(svalue);
    } 
    try {
		tblModel.insert(row);
	} catch (Exception e) {
		JOptionPane.showMessageDialog(
				BisisApp.getMainFrame(),
				e.getMessage(),
                Messages.getString("error"),JOptionPane.ERROR_MESSAGE);
	} 
    tblData.setRowSelectionInterval(
        tblModel.getRowCount()-1, tblModel.getRowCount()-1);
  }
  
  

  private JButton btnAdd = new JButton();
  private JButton btnDelete = new JButton();
  private JButton btnUpdate = new JButton();
  private JButton btnRefresh = new JButton();
  private JButton btnClose = new JButton(Messages.getString("exit"));
  private JScrollPane spData = new JScrollPane();
  private JTable tblData = new JTable();
  private CoderTableModel tblModel;
  
  private Table table;
}
