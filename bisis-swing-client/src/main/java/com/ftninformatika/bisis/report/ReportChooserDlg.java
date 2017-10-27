package com.ftninformatika.bisis.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.utils.WindowUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;


public class ReportChooserDlg extends JDialog {
  public ReportChooserDlg() {
    super(BisisApp.getMainFrame(), "Izaberite izve\u0161taj", true);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    setLayout(new BorderLayout());
    JPanel pCenter = new JPanel();
    add(pCenter, BorderLayout.CENTER);
    pCenter.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    pCenter.add(spReportList, BorderLayout.CENTER);
    spReportList.getViewport().setView(lReportList);
    spReportList.setPreferredSize(new Dimension(400, 250));
    lReportList.setModel(model);
    JPanel pSouth = new JPanel();
    add(pSouth, BorderLayout.SOUTH);
    pSouth.setLayout(new FlowLayout());
    pSouth.add(btnOK);
    pSouth.add(btnCancel);
    getRootPane().setDefaultButton(btnOK);
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleOK();
      }
    });
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleCancel();
      }
    });
    lReportList.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
          case KeyEvent.VK_ENTER:
            btnOK.doClick();
            break;
          case KeyEvent.VK_ESCAPE:
            btnCancel.doClick();
            break;
        }
      }
    });
    lReportList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent ev) {
        if (ev.getClickCount() > 1)
          btnOK.doClick();
      }
    });
    btnOK.setFocusable(false);
    btnOK.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/ok.gif")));
    btnCancel.setFocusable(false);
    btnCancel.setIcon(new ImageIcon(getClass().getResource(
        "/com/gint/app/bisis4/client/images/remove.gif")));
    pack();
    WindowUtils.centerOnScreen(this);
  }

  public void setVisible(boolean visible) {
    if (visible) {
      if (model.getSize() > 0)
        lReportList.setSelectedIndex(0);
      lReportList.requestFocusInWindow();
    }
    super.setVisible(visible);
  }

  public void handleOK() {
    confirmed = true;
    if (lReportList.getSelectedIndex()==-1)
      return;
    selectedItem = model.getItem(lReportList.getSelectedIndex());
    setVisible(false);
  }

  public void handleCancel() {
    confirmed = false;
    selectedItem = null;
    setVisible(false);
  }

  public ListItem getSelectedItem() {
    return selectedItem;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setList(List<ListItem> list) {
    model.setList(list);
  }

  class ListModel extends AbstractListModel {
    public int getSize() {
      return items.size();
    }

    public Object getElementAt(int index) {
    	
      return items.get(index);
    }

    public ListItem getItem(int index) {
      return items.get(index);
    }

    public List<ListItem> getList() {
    	
      return items;
    }

    public void setList(List<ListItem> list) {
      items.clear();
      items.addAll(list);
      fireContentsChanged(this, 0, list.size() - 1);
    }

    private List<ListItem> items = new ArrayList<ListItem>();
  }

  private JButton btnOK = new JButton("   OK   ");
  private JButton btnCancel = new JButton("Odustani");
  private JScrollPane spReportList = new JScrollPane();
  private JList lReportList = new JList();
  private ListModel model = new ListModel();
  private ListItem selectedItem;
  private boolean confirmed;
}
