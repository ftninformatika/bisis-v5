package com.ftninformatika.bisis.editor.inventar;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;



public class PrintersDlg extends JDialog {

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JList jList = null;
	private JPanel jPanel = null;
	private JButton btnOK = null;
	private boolean ok;
  
	
	public PrintersDlg(Frame owner) {
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
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
			jContentPane.add(getJPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.addKeyListener(new KeyAdapter(){
			      public void keyReleased(KeyEvent e){
			        if(e.getKeyCode()==KeyEvent.VK_ENTER){
			          getBtnOK().doClick();
			        } 
			      }
			    });
		      jList.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(java.awt.event.MouseEvent e) {
		          if (e.getClickCount() > 1)
		            getBtnOK().doClick();
		        }
		      });
		}
		return jList;
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
			btnOK.setText("OK"); //$NON-NLS-1$
			btnOK.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(java.awt.event.ActionEvent e) {
	        	ok = true;
	          setVisible(false);
	        }
	      });
		}
		return btnOK;
	}
	
	public String getPrinter(){
		if (getJList().getSelectedValue() != null){
			return getJList().getSelectedValue().toString();
		}else{
			return "";
		}
	}
	
	public void setPrinters(List data){
		getJList().setListData(data.toArray());
		getJList().setSelectedIndex(0);
		ok = false;
	}
	
	public boolean isOK(){
		return ok;
	}

  
}
