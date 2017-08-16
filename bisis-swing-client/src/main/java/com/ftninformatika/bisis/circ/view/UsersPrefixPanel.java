package com.ftninformatika.bisis.circ.view;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class UsersPrefixPanel extends JDialog {

  private static final long serialVersionUID = 1L;
  private JPanel jContentPane = null;
  private JScrollPane jScrollPane = null;
  private JList<String> prefixList = null;
  private UsersPrefixModel listModel = null;
  private JPanel bottomPanel = null;
  private JButton btnOK = null;
  private JButton btnCancel = null;
  private boolean selected = false;

  public UsersPrefixPanel(Frame owner) {
    super(owner, true);
    initialize();
  }

  private void initialize() {
    this.setSize(300, 200);
    this.setContentPane(getJContentPane());
    Dimension screen = getToolkit().getScreenSize();
    this.setLocation((screen.width - getSize().width) / 2,
	        (screen.height - getSize().height) / 2);
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
      jScrollPane.setViewportView(getPrefixList());
    }
    return jScrollPane;
  }
  
  private UsersPrefixModel getListModel(){
    if (listModel == null) {
      listModel = new UsersPrefixModel();
    }
    return listModel;
  }

  private JList<String> getPrefixList() {
    if (prefixList == null) {
      prefixList = new JList<String>();
      prefixList.setModel(getListModel());
      prefixList.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            btnOK.doClick();
            return;
          }
          if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnCancel.doClick();
            return;
          }
          char c = e.getKeyChar();
          int pos = getListModel().getSelection(c);
          if (pos > -1) {
            prefixList.setSelectedIndex(pos);
            JScrollBar sb = getJScrollPane().getVerticalScrollBar();
            sb.setValue((sb.getMaximum() - sb.getMinimum())
                / prefixList.getModel().getSize() * pos);
          }
        }
      });
      prefixList.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent e) {
          if (e.getClickCount() > 1)
            btnOK.doClick();
        }
      });
    }
    return prefixList;
  }

  private JPanel getBottomPanel() {
    if (bottomPanel == null) {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setHgap(50);
      flowLayout.setVgap(5);
      bottomPanel = new JPanel();
      bottomPanel.setLayout(flowLayout);
      bottomPanel.add(getBtnOK(), null);
      bottomPanel.add(getBtnCancel(), null);
    }
    return bottomPanel;
  }

  private JButton getBtnOK() {
    if (btnOK == null) {
      btnOK = new JButton();
      btnOK.setText(Messages.getString("circulation.ok")); //$NON-NLS-1$
      btnOK.setIcon(new ImageIcon(getClass().getResource("/circ-images/Check16.png"))); //$NON-NLS-1$
      btnOK.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	if (prefixList.getSelectedIndex() != -1){
        		selected = true;
        	} else {
        		selected = false;
        	}
          setVisible(false);
        }
      });
    }
    return btnOK;
  }

  private JButton getBtnCancel() {
    if (btnCancel == null) {
      btnCancel = new JButton();
      btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
      btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
      btnCancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          selected = false;
          setVisible(false);
        }
      });
    }
    return btnCancel;
  }
  
  public Object getSelectedItem(){
    return getListModel().getUsersPrefix(prefixList.getSelectedIndex());
  }
  
  public boolean isSelected(){
    return selected;
  }
  
  public void initUsersPrefix(){
    getListModel().initUsersPrefix();
  }
  
  public void initDatePrefix(){
    getListModel().initDatePrefix();
  }
}
