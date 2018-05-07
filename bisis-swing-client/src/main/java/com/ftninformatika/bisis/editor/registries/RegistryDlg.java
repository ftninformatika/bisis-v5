package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.LibraryList;
import com.ftninformatika.utils.WindowUtils;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import com.gint.app.bisis.editor.Environment;

public class RegistryDlg extends JDialog {

  /**
   * @param parent
   */
  public RegistryDlg(Frame parent) {
    super(parent, "Registri", true);
    this.parent = parent;

    tPredOdr.putClientProperty("Quaqua.Table.style", "striped");
    tPredPododr.putClientProperty("Quaqua.Table.style", "striped");
    tAutOdr.putClientProperty("Quaqua.Table.style", "striped");
    tKolOdr.putClientProperty("Quaqua.Table.style", "striped");
    tZbirke.putClientProperty("Quaqua.Table.style", "striped");
    tUDK.putClientProperty("Quaqua.Table.style", "striped");

    editDlg = new EditItemDlg(parent);
    setSize(700, 400);
    getContentPane().setLayout(new BorderLayout());

    btnLat.setFocusable(false);
    btnCyr.setFocusable(false);
    btnAdd.setToolTipText("Dodaj stavku");
    btnAdd.setFocusable(false);
    btnModify.setToolTipText("Izmeni stavku");
    btnModify.setFocusable(false);
    btnRemove.setToolTipText("Obri\u0161i stavku");
    btnRemove.setFocusable(false);
    btnSearch.setToolTipText("Prona\u0111i stavku");
    btnSearch.setFocusable(false);

    if (!LibraryList.isRegistryEnabled(BisisApp.appConfig.getLibrary())){
      btnAdd.setEnabled(false);
      btnAdd.setToolTipText("Nije dozvoljeno!");
      btnModify.setEnabled(false);
      btnModify.setToolTipText("Nije dozvoljeno!");
      btnRemove.setToolTipText("Nije dozvoljeno!");
      btnRemove.setEnabled(false);
    }

    Box north = Box.createHorizontalBox();
    north.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    //north.add(btnCyr);
    //north.add(btnLat);
    grpSortOrder.add(btnCyr);
    grpSortOrder.add(btnLat);
    btnLat.setSelected(true);
    north.add(Box.createHorizontalGlue());
    north.add(btnAdd);
    north.add(btnModify);
    north.add(btnRemove);
    north.add(btnSearch);
    btnAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleAdd();
      }
    });
    btnModify.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleModify();
      }
    });
    btnSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleSearch();
      }
    });
    btnRemove.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        handleRemove();
      }
    });
    
    getContentPane().add(north, BorderLayout.NORTH);
    
    tabbedPane.setFocusable(false);
    tabbedPane.addTab(" ", pChoose);
    tabbedPane.addTab(Registries.getShortName(Registries.ODREDNICE), pPredOdr);
    tabbedPane.addTab(Registries.getShortName(Registries.PODODREDNICE), pPredPododr);
    tabbedPane.addTab(Registries.getShortName(Registries.AUTORI), pAutOdr);
    tabbedPane.addTab(Registries.getShortName(Registries.KOLEKTIVNI), pKolOdr);
    tabbedPane.addTab(Registries.getShortName(Registries.ZBIRKE), pZbirke);
    tabbedPane.addTab(Registries.getShortName(Registries.UDK), pUDK);
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    tabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ev) {
        if (" ".equals(tabbedPane.getTitleAt(0))) {
          tabbedPane.removeTabAt(0);
          tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() - 1);
          return;
        }
        switch(getCurrentType()) {
          case Registries.ODREDNICE:
            if (tmPredOdr.getRowCount() == 0)
              tmPredOdr.init(RegistryDlg.this);
            break;
          case Registries.PODODREDNICE:
            if (tmPredPododr.getRowCount() == 0)
              tmPredPododr.init(RegistryDlg.this);
            break;
          case Registries.AUTORI:
            if (tmAutOdr.getRowCount() == 0)
              tmAutOdr.init(RegistryDlg.this);
            break;
          case Registries.KOLEKTIVNI:
            if (tmKolOdr.getRowCount() == 0)
              tmKolOdr.init(RegistryDlg.this);
            break;
          case Registries.ZBIRKE:
            if (tmZbirke.getRowCount() == 0)
              tmZbirke.init(RegistryDlg.this);
            break;
          case Registries.UDK:
            if (tmUDK.getRowCount() == 0)
              tmUDK.init(RegistryDlg.this);
            break;
          default:
            break;
        }
      }
    });
    
    JLabel lChoose = new JLabel("Izaberite registar");
    Font font = lChoose.getFont();
    Font font2 = font.deriveFont(14.0f);
    lChoose.setFont(font2);
    pChoose.add(lChoose);
    
    pPredOdr.setLayout(new BorderLayout());
    spPredOdr.getViewport().setView(tPredOdr);
    spPredOdr.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spPredOdr.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pPredOdr.add(spPredOdr, BorderLayout.CENTER);
    rfPredOdr = new VisibleRowsRefresher(tPredOdr, 
        spPredOdr.getVerticalScrollBar());
    
    pPredPododr.setLayout(new BorderLayout());
    spPredPododr.getViewport().setView(tPredPododr);
    spPredPododr.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spPredPododr.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pPredPododr.add(spPredPododr, BorderLayout.CENTER);
    rfPredPododr = new VisibleRowsRefresher(
        tPredPododr, spPredPododr.getVerticalScrollBar());

    pAutOdr.setLayout(new BorderLayout());
    spAutOdr.getViewport().setView(tAutOdr);
    spAutOdr.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spAutOdr.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pAutOdr.add(spAutOdr, BorderLayout.CENTER);
    rfAutOdr = new VisibleRowsRefresher(tAutOdr, 
        spAutOdr.getVerticalScrollBar());

    pKolOdr.setLayout(new BorderLayout());
    spKolOdr.getViewport().setView(tKolOdr);
    spKolOdr.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spKolOdr.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pKolOdr.add(spKolOdr, BorderLayout.CENTER);
    rfKolOdr = new VisibleRowsRefresher(tKolOdr, 
        spKolOdr.getVerticalScrollBar());

    pZbirke.setLayout(new BorderLayout());
    spZbirke.getViewport().setView(tZbirke);
    spZbirke.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spZbirke.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pZbirke.add(spZbirke, BorderLayout.CENTER);
    rfZbirke = new VisibleRowsRefresher(tZbirke, 
        spZbirke.getVerticalScrollBar());

    pUDK.setLayout(new BorderLayout());
    spUDK.getViewport().setView(tUDK);
    spUDK.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    spUDK.setVerticalScrollBarPolicy(
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    pUDK.add(spUDK, BorderLayout.CENTER);
    rfUDK = new VisibleRowsRefresher(tUDK, 
        spUDK.getVerticalScrollBar());

    tPredOdr.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tPredOdr.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.ODREDNICE));
    tPredOdr.setDoubleBuffered(true);
    tPredOdr.setSurrendersFocusOnKeystroke(false);
    tPredOdr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tPredPododr.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tPredPododr.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.PODODREDNICE));
    tPredPododr.setDoubleBuffered(true);
    tPredPododr.setSurrendersFocusOnKeystroke(false);
    tPredPododr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tAutOdr.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tAutOdr.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.AUTORI));
    tAutOdr.getColumnModel().getColumn(1).setHeaderValue(
        Registries.getLabel2(Registries.AUTORI));
    tAutOdr.getTableHeader().setResizingAllowed(false);
    tAutOdr.getTableHeader().setReorderingAllowed(false);
    tAutOdr.setDoubleBuffered(true);
    tAutOdr.setSurrendersFocusOnKeystroke(false);
    tAutOdr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tUDK.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tUDK.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.UDK));
    tUDK.getColumnModel().getColumn(1).setHeaderValue(
        Registries.getLabel2(Registries.UDK));
    tUDK.getColumnModel().getColumn(0).setMinWidth(150);
    tUDK.getColumnModel().getColumn(0).setMaxWidth(150);
    tUDK.getTableHeader().setResizingAllowed(false);
    tUDK.getTableHeader().setReorderingAllowed(false);
    tUDK.setDoubleBuffered(true);
    tUDK.setSurrendersFocusOnKeystroke(false);
    tZbirke.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tZbirke.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.ZBIRKE));
    tZbirke.setDoubleBuffered(true);
    tZbirke.setSurrendersFocusOnKeystroke(false);
    tZbirke.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tKolOdr.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    tKolOdr.getColumnModel().getColumn(0).setHeaderValue(
        Registries.getLabel1(Registries.KOLEKTIVNI));
    tKolOdr.setDoubleBuffered(true);
    tKolOdr.setSurrendersFocusOnKeystroke(false);
    tKolOdr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    progressBar.setStringPainted(true);
    progressBar.setVisible(false);
    btnOK.setFocusable(false);
    btnCancel.setFocusable(false);
    Box box = Box.createHorizontalBox();
    box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    box.add(progressBar);
    box.add(Box.createHorizontalGlue());
    box.add(btnCancel);
    box.add(btnOK);
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
    btnCyr.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        sort();
      }
    });
    btnLat.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        sort();
      }
    });
    getContentPane().add(box, BorderLayout.SOUTH);
    WindowUtils.centerOnScreen(this);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowActivated(WindowEvent ev) {
      }
      public void windowDeactivated(WindowEvent ev) {
      }
    });
    setGlassPane(new MyGlassPane());
  }
  
  public int getCurrentType() {
    switch (tabbedPane.getSelectedIndex()) {
      case 0:
        return Registries.ODREDNICE;
      case 1:
        return Registries.PODODREDNICE;
      case 2:
        return Registries.AUTORI;
      case 3:
        return Registries.KOLEKTIVNI;
      case 4:
        return Registries.ZBIRKE;
      case 5:
        return Registries.UDK;
      default:
        return Registries.NEPOZNAT;
    }
  }
  
  public void sort() {
    Comparator comparator = null;
//    if (btnLat.isSelected())
//      comparator = RegistryUtils.getLatComparator();
//    else
//      comparator = RegistryUtils.getCyrComparator();
    final Comparator c = RegistryUtils.getLatComparator();
    //getGlassPane().setVisible(true);
    final com.ftninformatika.utils.SwingWorker worker = new com.ftninformatika.utils.SwingWorker() {

      public Object construct() {
        return new SortTask(c);
      }
    };
    worker.start();
  }
  
  public void handleAdd() {
    editDlg.makeNew(getCurrentType());
    if (editDlg.getValue1() != null) {
      RegistryItem item = new RegistryItem();
      item.setText1(editDlg.getValue1());
      item.setText2(editDlg.getValue2());
      switch(getCurrentType()) {
        case Registries.ODREDNICE:
          tmPredOdr.addRow(item);
          break;
        case Registries.PODODREDNICE:
          tmPredPododr.addRow(item);
          break;
        case Registries.AUTORI:
          tmAutOdr.addRow(item);
          break;
        case Registries.KOLEKTIVNI:
          tmKolOdr.addRow(item);
          break;
        case Registries.ZBIRKE:
          tmZbirke.addRow(item);
          break;
        case Registries.UDK:
          tmUDK.addRow(item);
          break;
        default:
          break;
      }
      sort();
    }
  }
  
  public void handleModify() {
    int index = -1;
    RegistryItem item = null;
    switch(getCurrentType()) {
      case Registries.ODREDNICE:
        index = tPredOdr.getSelectedRow();
        if (index == -1)
          return;
        item = tmPredOdr.getRow(index);
        break;
      case Registries.PODODREDNICE:
        index = tPredPododr.getSelectedRow();
        if (index == -1)
          return;
        item = tmPredPododr.getRow(index);
        break;
      case Registries.AUTORI:
        index = tAutOdr.getSelectedRow();
        if (index == -1)
          return;
        item = tmAutOdr.getRow(index);
        break;
      case Registries.KOLEKTIVNI:
        index = tKolOdr.getSelectedRow();
        if (index == -1)
          return;
        item = tmKolOdr.getRow(index);
        break;
      case Registries.ZBIRKE:
        index = tZbirke.getSelectedRow();
        if (index == -1)
          return;
        item = tmZbirke.getRow(index);
        break;
      case Registries.UDK:
        index = tUDK.getSelectedRow();
        if (index == -1)
          return;
        item = tmUDK.getRow(index);
        break;
      default:
        break;
    }
    editDlg.edit(getCurrentType(), item.getText1(), item.getText2());
    if (editDlg.getValue1() != null) {
      RegistryItem item2 = new RegistryItem();
      item2.setText1(editDlg.getValue1());
      item2.setText2(editDlg.getValue2());
      switch(getCurrentType()) {
        case Registries.ODREDNICE:
          tmPredOdr.updateRow(index, item2);
          break;
        case Registries.PODODREDNICE:
          tmPredPododr.updateRow(index, item2);
          break;
        case Registries.AUTORI:
          tmAutOdr.updateRow(index, item2);
          break;
        case Registries.KOLEKTIVNI:
          tmKolOdr.updateRow(index, item2);
          break;
        case Registries.ZBIRKE:
          tmZbirke.updateRow(index, item2);
          break;
        case Registries.UDK:
          tmUDK.updateRow(index, item2);
          break;
        default:
          break;
      }
      sort();
    }
  }
  
  public void handleRemove() {
    int answer = JOptionPane.showConfirmDialog(parent, 
        "Da li ste sigurni da \u017eelite da obri\u0161ete stavku?", "Potvrda", 
        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    if (answer != JOptionPane.YES_OPTION)
      return;
    int index = -1;
    RegistryItem item = null;
    switch(getCurrentType()) {
      case Registries.ODREDNICE:
        index = tPredOdr.getSelectedRow();
        if (index == -1)
          return;
        tmPredOdr.deleteRow(index);
        break;
      case Registries.PODODREDNICE:
        index = tPredPododr.getSelectedRow();
        if (index == -1)
          return;
        tmPredPododr.deleteRow(index);
        break;
      case Registries.AUTORI:
        index = tAutOdr.getSelectedRow();
        if (index == -1)
          return;
        tmAutOdr.deleteRow(index);
        break;
      case Registries.KOLEKTIVNI:
        index = tKolOdr.getSelectedRow();
        if (index == -1)
          return;
        tmKolOdr.deleteRow(index);
        break;
      case Registries.ZBIRKE:
        index = tZbirke.getSelectedRow();
        if (index == -1)
          return;
        tmZbirke.deleteRow(index);
        break;
      case Registries.UDK:
        index = tUDK.getSelectedRow();
        if (index == -1)
          return;
        tmUDK.deleteRow(index);
        break;
      default:
        break;
    }
  }
  
  public void handleSearch() {
    editDlg.search(getCurrentType());
    if (editDlg.getValue1() == null && editDlg.getValue2() == null)
      return;
    RegistryItem sample = new RegistryItem();
    sample.setText1(editDlg.getValue1());
    sample.setText2(editDlg.getValue2());
    int index = -1;
    switch (getCurrentType()) {
      case Registries.ODREDNICE:
        index = tmPredOdr.searchRow(sample);
        if (index != -1) {
          tPredOdr.setRowSelectionInterval(index, index);
          setScroller(spPredOdr.getVerticalScrollBar(), tmPredOdr, index);
        }
        break;
      case Registries.PODODREDNICE:
        index = tmPredPododr.searchRow(sample);
        if (index != -1) {
          tPredPododr.setRowSelectionInterval(index, index);
          setScroller(spPredPododr.getVerticalScrollBar(), tmPredPododr, index);
        }
        break;
      case Registries.AUTORI:
        index = tmAutOdr.searchRow(sample);
        if (index != -1) {
          tAutOdr.setRowSelectionInterval(index, index);
          setScroller(spAutOdr.getVerticalScrollBar(), tmAutOdr, index);
        }
        break;
      case Registries.KOLEKTIVNI:
        index = tmKolOdr.searchRow(sample);
        if (index != -1) {
          tKolOdr.setRowSelectionInterval(index, index);
          setScroller(spKolOdr.getVerticalScrollBar(), tmKolOdr, index);
        }
        break;
      case Registries.ZBIRKE:
        index = tmZbirke.searchRow(sample);
        if (index != -1) {
          tZbirke.setRowSelectionInterval(index, index);
          setScroller(spZbirke.getVerticalScrollBar(), tmZbirke, index);
        }
        break;
      case Registries.UDK:
        index = tmUDK.searchRow(sample);
        if (index != -1) {
          tUDK.setRowSelectionInterval(index, index);
          setScroller(spUDK.getVerticalScrollBar(), tmUDK, index);
        }
        break;
      default:
        break;
    }
  }
  
  public void handleOK() {
    setVisible(false);
    RegistryItem selection = getSelection();
    if (selection != null)
      value = selection.getText1().trim();
  }
  
  public void handleCancel() {
    setVisible(false);
    value = null;
  }
  
  public String getValue() {
    return value;
  }
  
  public RegistryItem getSelection() {
    RegistryItem retVal = null;
    int index = -1;
    switch (getCurrentType()) {
      case Registries.ODREDNICE:
        index = tPredOdr.getSelectedRow();
        if (index != -1)
          retVal = tmPredOdr.getRow(index);
        break;
      case Registries.PODODREDNICE:
        index = tPredPododr.getSelectedRow();
        if (index != -1)
          retVal = tmPredPododr.getRow(index);
        break;
      case Registries.AUTORI:
        index = tAutOdr.getSelectedRow();
        if (index != -1)
          retVal = tmAutOdr.getRow(index);
        break;
      case Registries.KOLEKTIVNI:
        index = tKolOdr.getSelectedRow();
        if (index != -1)
          retVal = tmKolOdr.getRow(index);
        break;
      case Registries.ZBIRKE:
        index = tZbirke.getSelectedRow();
        if (index != -1)
          retVal = tmZbirke.getRow(index);
        break;
      case Registries.UDK:
        index = tUDK.getSelectedRow();
        if (index != -1)
          retVal = tmUDK.getRow(index);
        break;
      default:
        break;
    }
    return retVal;
  }
  
  public void setVisible(boolean visible) {
    if (visible) {
      try {
        tmPredOdr.clear();
        tmPredPododr.clear();
        tmAutOdr.clear();
        tmKolOdr.clear();
        tmZbirke.clear();
        tmUDK.clear();
        if (!" ".equals(tabbedPane.getTitleAt(0))) {
          ChangeListener[] listeners = tabbedPane.getChangeListeners();
          for (int i = 0; i < listeners.length; i++)
            tabbedPane.removeChangeListener(listeners[i]);
          tabbedPane.insertTab(" ", null, pChoose, "Izaberite registar", 0);
          tabbedPane.setSelectedIndex(0);
          for (int i = 0; i < listeners.length; i++)
            tabbedPane.addChangeListener(listeners[i]);
          
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      value = null;
    }
    super.setVisible(visible);
  }
  
  private void setScroller(JScrollBar scrollBar, RegistryTableModel model, int row) {
    float min = scrollBar.getMinimum();
    float max = scrollBar.getMaximum();
    float ext = scrollBar.getModel().getExtent();
    float val = (max - min) * ((float)row / (float)model.getRowCount()) /* - ext / 2 */;
    if (val < min)
      val = min;
    else if (val > max - ext)
      val = max - ext;
    scrollBar.setValue((int)val);
  }
  
  JPanel pPredOdr = new JPanel();
  RegistryTableModel tmPredOdr = new RegistryTableModel(Registries.ODREDNICE);
  JTable tPredOdr = new JTable(tmPredOdr);
  JScrollPane spPredOdr = new JScrollPane();
  VisibleRowsRefresher rfPredOdr;
  
  JPanel pPredPododr = new JPanel();
  RegistryTableModel tmPredPododr = new RegistryTableModel(Registries.PODODREDNICE);
  JTable tPredPododr = new JTable(tmPredPododr);
  JScrollPane spPredPododr = new JScrollPane();
  VisibleRowsRefresher rfPredPododr;

  JPanel pAutOdr = new JPanel();
  RegistryTableModel tmAutOdr = new RegistryTableModel(Registries.AUTORI);
  JTable tAutOdr = new JTable(tmAutOdr);
  JScrollPane spAutOdr = new JScrollPane();
  VisibleRowsRefresher rfAutOdr;

  JPanel pKolOdr = new JPanel();
  RegistryTableModel tmKolOdr = new RegistryTableModel(Registries.KOLEKTIVNI);
  JTable tKolOdr = new JTable(tmKolOdr);
  JScrollPane spKolOdr = new JScrollPane();
  VisibleRowsRefresher rfKolOdr;

  JPanel pZbirke = new JPanel();
  RegistryTableModel tmZbirke = new RegistryTableModel(Registries.ZBIRKE);
  JTable tZbirke = new JTable(tmZbirke);
  JScrollPane spZbirke = new JScrollPane();
  VisibleRowsRefresher rfZbirke;

  JPanel pUDK = new JPanel();
  RegistryTableModel tmUDK = new RegistryTableModel(Registries.UDK);
  JTable tUDK = new JTable(tmUDK);
  JScrollPane spUDK = new JScrollPane();
  VisibleRowsRefresher rfUDK;
  
  JPanel pChoose = new JPanel();
  
  JTabbedPane tabbedPane = new JTabbedPane(
      JTabbedPane.NORTH, JTabbedPane.SCROLL_TAB_LAYOUT);
  
  JToggleButton btnCyr = new JToggleButton("\u0430\u0437\u0431\u0443\u0447\u043d\u043e");
  JToggleButton btnLat = new JToggleButton("abecedno");
  
  JButton btnAdd = new JButton(new ImageIcon(RegistryDlg.class.getResource(
    "/icons/add.png")));
  JButton btnModify = new JButton(new ImageIcon(RegistryDlg.class.getResource(
    "/icons/edit.png")));
  JButton btnRemove = new JButton(new ImageIcon(RegistryDlg.class.getResource(
    "/icons/delete.png")));
  JButton btnSearch = new JButton(new ImageIcon(RegistryDlg.class.getResource(
    "/icons/search.png")));

  JButton btnCancel = new JButton("Zatvori");
  JButton btnOK = new JButton("Preuzmi");
  JProgressBar progressBar = new JProgressBar();
  ButtonGroup grpSortOrder = new ButtonGroup();
  
  EditItemDlg editDlg;
  
  private String value;
  
  public class MyGlassPane extends JComponent {
    public MyGlassPane() {
      addMouseListener(new MouseAdapter(){});
    }
  }
  
  public class SortTask {
    public SortTask(Comparator comparator) {
      RegistryDlg.this.progressBar.setVisible(true);
      RegistryDlg.this.progressBar.setString("sortiram");
      RegistryDlg.this.tmPredOdr.sort(comparator);
      RegistryDlg.this.tmPredPododr.sort(comparator);
      RegistryDlg.this.tmAutOdr.sort(comparator);
      RegistryDlg.this.tmKolOdr.sort(comparator);
      RegistryDlg.this.tmZbirke.sort(comparator);
      RegistryDlg.this.tmUDK.sort(comparator);
      RegistryDlg.this.progressBar.setVisible(false);
      //RegistryDlg.this.getGlassPane().setVisible(false);
    }
  }
  
  public static void main(String[] args) throws Exception {
    //Class.forName("com.sap.dbtech.jdbc.DriverSapDB");
  /*  Environment.setURL("jdbc:sapdb://192.168.100.10/BISIS?timeout=0");
    Environment.setUsername("bisisbg");
    Environment.setPassword("bisisbg"); */
    RegistryDlg dlg = new RegistryDlg(null);
    dlg.setVisible(true);
  }
  
  private Frame parent;
}
