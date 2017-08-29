package com.ftninformatika.bisis.circ.view;


import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;



public class ZipPlaceDlg extends JDialog {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel = null;
	private JButton btnOK = null;
	private ZipPlaceTableModel model = null;
  
	
	public ZipPlaceDlg(Frame owner) {
		super(owner, true);
		initialize();
	}

	private void initialize() {
		this.setSize(150, 200);
		this.setTitle(""); //$NON-NLS-1$
		this.setContentPane(getJContentPane());
    this.pack();
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJPanel(), java.awt.BorderLayout.SOUTH);
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
			jTable = new JTable(getTableModel());
      jTable.setAutoCreateRowSorter(true);
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setDefaultEditor(Object.class,null);
      jTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "none"); //$NON-NLS-1$
			jTable.addKeyListener(new KeyAdapter(){
			      public void keyReleased(KeyEvent e){
			        if(e.getKeyCode()==KeyEvent.VK_ENTER){
			          getBtnOK().doClick();
			        } else {
                int i = selectionForKey(e.getKeyChar());
                if (i > -1) {
                  jTable.getSelectionModel().setSelectionInterval(i, i);
                  JScrollBar sb = getJScrollPane().getVerticalScrollBar();
                  sb.setValue((sb.getMaximum() - sb.getMinimum())
                      / jTable.getRowCount() * i);
                }
              }
			      }
			    });
      jTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
          if (e.getClickCount() > 1)
            getBtnOK().doClick();
        }
      });
		}
		return jTable;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.add(getBtnOK(), null);
		}
		return jPanel;
	}

	private JButton getBtnOK() {
		if (btnOK == null) {
			btnOK = new JButton();
			btnOK.setText(Messages.getString("circulation.ok")); //$NON-NLS-1$
      btnOK.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          setVisible(false);
        }
      });
		}
		return btnOK;
	}
  
  private ZipPlaceTableModel getTableModel(){
    if (model == null){
      model = new ZipPlaceTableModel();
    }
    return model;
  }
	
	public String getZip(){
		if (getJTable().getSelectedRow() != -1){
			return (String)getJTable().getValueAt(getJTable().getSelectedRow(), 0);
		}else{
			return ""; //$NON-NLS-1$
		}
	}
	
	public String getPlace(){
		if (getJTable().getSelectedRow() != -1){
			return (String)getJTable().getValueAt(getJTable().getSelectedRow(), 1);
		}else{
			return ""; //$NON-NLS-1$
		}	
	}
  
  public int selectionForKey(char aKey) {
    int n = getJTable().getRowCount();
    for (int i = 0; i < n; i++) {
      if (Character.toUpperCase(
            ((String)getJTable().getValueAt(i, 1)).charAt(0)) == 
          Character.toUpperCase(aKey))
        return i;
    }
    return -1;
  }

  
}
