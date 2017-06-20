package com.ftninformatika.bisis.search;

import java.awt.Frame;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;


public class PrefixListDlg extends JDialog {
  
  public PrefixListDlg(Frame owner) {
    super(owner, "Izbor prefiksa", true);
    lbPrefixList.setModel(plm);
    spPrefixList.setViewportView(lbPrefixList);
    spPrefixList.setFocusable(false);
    lbPrefixList.addKeyListener(new java.awt.event.KeyAdapter() {
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
        int pos = plm.getSelection(c);
        if (pos > -1) {
          lbPrefixList.setSelectedIndex(pos);
          JScrollBar sb = spPrefixList.getVerticalScrollBar();
          sb.setValue((sb.getMaximum() - sb.getMinimum())
              / lbPrefixList.getModel().getSize() * pos);
        }
      }
    });
    lbPrefixList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getClickCount() > 1)
          btnOK.doClick();
      }
    });
    
    btnOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        selected = true;
        setVisible(false);
      }
    });
    btnOK.setFocusable(false);
    getRootPane().setDefaultButton(btnOK);
    btnOK.setIcon(new ImageIcon(getClass().getResource(
        "/icons/ok.gif")));
    
    btnCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        selected = false;
        setVisible(false);
      }
    });
    btnCancel.setFocusable(false);
    btnCancel.setIcon(new ImageIcon(getClass().getResource(
        "/icons/remove.gif")));

    MigLayout layout = new MigLayout(
        "insets dialog, wrap",
        "[400lp]",
        "[300lp][]");
    setLayout(layout);
    add(spPrefixList, "grow, wrap");
    add(btnCancel, "gaptop para, split 2, tag cancel");
    add(btnOK, "gaptop para, wrap, tag ok");
    pack();
    
    //WindowUtils.centerOnScreen(this);
  }

  public boolean isSelected() {
    return selected;
  }

  public String getSelectedPrefix() {
    return plm.getPrefixName(lbPrefixList.getSelectedIndex());
  }

  public void moveTo(String s) {
    int pos = plm.getSelection(s);
    if (pos == -1)
      pos = 0;
    lbPrefixList.setSelectedIndex(pos);
    JScrollBar sb = spPrefixList.getVerticalScrollBar();
    sb.setValue((sb.getMaximum() - sb.getMinimum())
        / lbPrefixList.getModel().getSize() * pos);
  }

  private JList lbPrefixList = new JList();
  private JScrollPane spPrefixList = new JScrollPane();
  private JButton btnOK = new JButton("Izaberi");
  private JButton btnCancel = new JButton("Odustani");

  private boolean selected = false;
  private PrefixListModel plm = new PrefixListModel();
}
