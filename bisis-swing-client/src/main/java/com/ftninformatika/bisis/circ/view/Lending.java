package com.ftninformatika.bisis.circ.view;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.circ.pojo.CircLocation;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Lending {

    private PanelBuilder pb = null;
    private JLabel lUser = null;
    private JLabel lUntilDate = null;
    private JLabel lNote = null;
    private JTextField tfCtlgNo = null;
    private JScrollPane jScrollPane = null;
    private JTable tblLending = null;
    private LendingTableModel lendingTableModel = null;
    private JComboBox cmbLocation = null;
    private JButton btnReturn = null;
    private JButton btnResume = null;
    private JButton btnRevers = null;
    private JButton btnHistory = null;
    private JButton btnLend = null;
    private JButton btnSearch = null;
    private JButton btnWarnings = null;
    private JLabel lWarnings = null;
    private JLabel lBlockCard = null;
    private JLabel lDuplicate = null;
    private ComboBoxRenderer cmbRenderer = null;
    private String defaultLocation = null;
    private User parent = null;
    private boolean pinRequired;


    public Lending(User parent) {
        this.parent = parent;
        makePanel();
    }

    private void makePanel() {
        FormLayout layout = new FormLayout(
                "2dlu:grow, 20dlu, 18dlu, 20dlu, 18dlu, 3dlu, 120dlu, 15dlu, 18dlu, 15dlu, 18dlu, 50dlu, 70dlu, 5dlu, 70dlu, 2dlu:grow", //$NON-NLS-1$
                "5dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 20dlu, pref, 2dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu, 110dlu, 2dlu, 18dlu, 2dlu:grow "); //$NON-NLS-1$
        CellConstraints cc = new CellConstraints();
        pb = new PanelBuilder(layout);
        pb.setDefaultDialogBorder();

        pb.addSeparator(Messages.getString("circulation.info"), cc.xyw(2, 2, 14)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.user"), cc.xyw(2, 4, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getLUser(), cc.xyw(7, 4, 4));
        pb.addLabel(Messages.getString("circulation.mmbrexpirdate"), cc.xyw(2, 6, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getLUntilDate(), cc.xyw(7, 6, 4));
        pb.addLabel(Messages.getString("circulation.note"), cc.xyw(2, 8, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getLNote(), cc.xyw(7, 8, 4));

        pb.add(getLBlockCard(), cc.xyw(11, 4, 5));
        pb.add(getLDuplicate(), cc.xyw(11, 6, 5));

        pb.addSeparator(Messages.getString("circulation.charging"), cc.xyw(2, 16, 14)); //$NON-NLS-1$
        pb.add(getJScrollPane(), cc.xyw(2, 18, 14));
        pb.add(getBtnReturn(), cc.xy(3, 20, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnResume(), cc.xy(5, 20, "fill, fill")); //$NON-NLS-1$

        pb.add(getBtnRevers(), cc.xy(13, 20));
        pb.add(getBtnHistory(), cc.xy(15, 20));

        pb.addSeparator(Messages.getString("circulation.checkout"), cc.xyw(2, 10, 10)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.acquisitionnumber"), cc.xyw(2, 12, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getTfCtlgNo(), cc.xy(7, 12));
        pb.add(getBtnLend(), cc.xy(9, 12, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnSearch(), cc.xy(11, 12, "fill, fill")); //$NON-NLS-1$
        pb.add(getLWarnings(), cc.xyw(13, 10, 3, "right, center")); //$NON-NLS-1$
        pb.add(getBtnWarnings(), cc.xy(15, 12, "fill, fill")); //$NON-NLS-1$

    }

    public JPanel getPanel() {
        return pb.getPanel();
    }

    public JTextField getTfCtlgNo() {
        if (tfCtlgNo == null) {
            tfCtlgNo = new JTextField();
            tfCtlgNo.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ADD || e.getKeyCode() == KeyEvent.VK_ENTER) {
                        getBtnLend().doClick();
                        //tfCtlgNo.setText(tfCtlgNo.getText().substring(0,tfCtlgNo.getText().length()-2));
                    } else if (e.getKeyCode() == KeyEvent.VK_F12) {
                        parent.save();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        parent.cancel();
                    }

                }

                public void keyReleased(KeyEvent e) {
//          if(e.getKeyCode()==KeyEvent.VK_ENTER){
//            if (!getTfCtlgNo().getText().equals("")){
//              getBtnLend().doClick();
//            }else{
//              parent.save();
//            }
//          }
                    if (e.getKeyCode() == KeyEvent.VK_ADD) {
                        tfCtlgNo.setText(tfCtlgNo.getText().substring(0, tfCtlgNo.getText().length() - 1));
                    }
//          else if (e.getKeyCode()==KeyEvent.VK_ENTER){
//            parent.save();
//          }
                }
            });
        }
        return tfCtlgNo;
    }

    private JLabel getLUser() {
        if (lUser == null) {
            lUser = new JLabel();
            lUser.setForeground(Color.blue);
            lUser.setText(""); //$NON-NLS-1$
        }
        return lUser;
    }

    public String getUser() {
        return getLUser().getText();
    }

    private JLabel getLUntilDate() {
        if (lUntilDate == null) {
            lUntilDate = new JLabel();
            lUntilDate.setForeground(Color.blue);
            lUntilDate.setText(""); //$NON-NLS-1$
        }
        return lUntilDate;
    }

    private JLabel getLNote() {
        if (lNote == null) {
            lNote = new JLabel();
            lNote.setForeground(Color.red);
            lNote.setText(""); //$NON-NLS-1$
        }
        return lNote;
    }

    private JLabel getLWarnings() {
        if (lWarnings == null) {
            lWarnings = new JLabel();
            lWarnings.setForeground(Color.red);
            lWarnings.setText(Messages.getString("circulation.remindersweresent")); //$NON-NLS-1$
        }
        return lWarnings;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTblLending());
        }
        return jScrollPane;
    }

    private JTable getTblLending() {
        if (tblLending == null) {
            tblLending = new JTable();
            tblLending.setModel(getTableModel());
            tblLending.putClientProperty("Quaqua.Table.style", "striped");
//            tblLending.setPreferredSize(new Dimension(400, 400));
            //tblLending.getColumnModel().getColumn(7).setPreferredWidth(40);
//			TableColumn comboColumnLoc = tblLending.getColumnModel().getColumn(6);
//			comboColumnLoc.setCellEditor(new DefaultCellEditor(getCmbLocation()));
//			tblLending.setDefaultRenderer(Object.class, new CmbTableRenderer());
            tblLending.setAutoCreateRowSorter(true);
            //comboColumnLoc.setPreferredWidth(120);
            tblLending.setDefaultRenderer(Date.class, new MembershipTableModel.CellRenderer());
            tblLending.setDefaultEditor(Date.class, new MembershipTableModel.CellEditor());
        }
        return tblLending;
    }

    public LendingTableModel getTableModel() {
        if (lendingTableModel == null) {
            lendingTableModel = new LendingTableModel();
            lendingTableModel.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return lendingTableModel;
    }


    public LendingTableModel getReversTableModel() {
        if (Cirkulacija.getApp().getEnvironment().getReversSelected()) {
            LendingTableModel model = new LendingTableModel();
            int[] rows = getTblLending().getSelectedRows();
            if (rows.length != 0) {
                for (int j = 0; j < rows.length; j++) {
                    model.setRow(getTableModel().getRow(getTblLending().convertRowIndexToModel(rows[j])));
                }
            } else {
                model = getTableModel();
            }
            return model;
        } else {
            return getTableModel();
        }
    }

    private ComboBoxRenderer getCmbRenderer() {
        if (cmbRenderer == null) {
            cmbRenderer = new ComboBoxRenderer();
        }
        return cmbRenderer;
    }

    private JComboBox getCmbLocation() {
        if (cmbLocation == null) {
            cmbLocation = new JComboBox();
            cmbLocation.setRenderer(getCmbRenderer());
        }
        return cmbLocation;
    }

    private JButton getBtnReturn() {
        if (btnReturn == null) {
            btnReturn = new JButton();
            btnReturn.setToolTipText(Messages.getString("circulation.discharge")); //$NON-NLS-1$
            btnReturn.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png"))); //$NON-NLS-1$
            btnReturn.setFocusable(false);
            btnReturn.setPreferredSize(new java.awt.Dimension(28, 28));
            btnReturn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int[] rows = getTblLending().getSelectedRows();
                    if (rows.length != 0) {
                        int[] modelrows = new int[rows.length];
                        for (int j = 0; j < rows.length; j++) {
                            modelrows[j] = getTblLending().convertRowIndexToModel(rows[j]);
                        }
                        for (int i = 0; i < modelrows.length; i++) {
                            if (!getTableModel().getItem(modelrows[i]).getLocation().equals(defaultLocation)) {
                                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.dischargingnotallowed")+ " " +
                                                Messages.getString("circulation.lendlocation")+" "+ getTableModel().getItem(modelrows[i]).getLocation(), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                                return;
                            }
                        }
                        for (int i = 0; i < modelrows.length; i++) {
                            Cirkulacija.getApp().getRecordsManager().returnBook((String) getTableModel().getValueAt(modelrows[i], 0));
                        }
                        getTableModel().removeRows(modelrows);
                        handleKeyTyped();
                        pinRequired = true;
                    }
                }
            });
        }
        return btnReturn;
    }

    private JButton getBtnResume() {
        if (btnResume == null) {
            btnResume = new JButton();
            btnResume.setToolTipText(Messages.getString("circulation.renew")); //$NON-NLS-1$
            btnResume.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnResume.setFocusable(false);
            btnResume.setPreferredSize(new java.awt.Dimension(28, 28));
            btnResume.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int[] rows = getTblLending().getSelectedRows();
                    if (rows.length != 0) {
                        int[] modelrows = new int[rows.length];
                        for (int j = 0; j < rows.length; j++) {
                            modelrows[j] = getTblLending().convertRowIndexToModel(rows[j]);
                        }
                        for (int i = 0; i < modelrows.length; i++) {
                            if (!getTableModel().getItem(modelrows[i]).getLocation().equals(defaultLocation)) {
                                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.dischargingnotallowed")+ " " +
                                                Messages.getString("circulation.lendlocation")+" "+ getTableModel().getItem(modelrows[i]).getLocation(),
                                        Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                                return;
                            }
                        }
                        getTableModel().updateRows(modelrows, parent.getMmbrship().getUserCateg());
                        handleKeyTyped();
                        //pinRequired = true;
                    }
                }
            });
        }
        return btnResume;
    }

    private JButton getBtnRevers() {
        if (btnRevers == null) {
            btnRevers = new JButton();
            btnRevers.setText(Messages.getString("circulation.lendingform")); //$NON-NLS-1$
            btnRevers.setToolTipText(Messages.getString("circulation.lendingform")); //$NON-NLS-1$
            btnRevers.setFocusable(false);
            btnRevers.setIcon(new ImageIcon(getClass().getResource("/circ-images/doc_rich16.png"))); //$NON-NLS-1$
            btnRevers.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    parent.printRevers();
                }
            });
        }
        return btnRevers;
    }

    private JButton getBtnHistory() {
        if (btnHistory == null) {
            btnHistory = new JButton();
            btnHistory.setText(Messages.getString("circulation.history")); //$NON-NLS-1$
            btnHistory.setToolTipText(Messages.getString("circulation.history")); //$NON-NLS-1$
            btnHistory.setFocusable(false);
            btnHistory.setIcon(new ImageIcon(getClass().getResource("/circ-images/doc16.png"))); //$NON-NLS-1$
            btnHistory.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    //TODO
                    Cirkulacija.getApp().getMainFrame().getReport().selectMemberHistory(parent.getMmbrship().getUserID());
                    Cirkulacija.getApp().getMainFrame().showPanel("reportPanel"); //$NON-NLS-1$
                }
            });
        }
        return btnHistory;
    }

    private JButton getBtnWarnings() {
        if (btnWarnings == null) {
            btnWarnings = new JButton();
            btnWarnings.setText(Messages.getString("circulation.reminders")); //$NON-NLS-1$
            btnWarnings.setToolTipText(Messages.getString("circulation.reminders")); //$NON-NLS-1$
            btnWarnings.setFocusable(false);
            btnWarnings.setIcon(new ImageIcon(getClass().getResource("/circ-images/doc_rich16.png"))); //$NON-NLS-1$
            btnWarnings.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    parent.showWarnings();
                }
            });
        }
        return btnWarnings;
    }

    private JButton getBtnLend() {
        if (btnLend == null) {
            btnLend = new JButton();
            //btnLend.setText("Zadu\u017ei");
            btnLend.setToolTipText(Messages.getString("circulation.checkout")); //$NON-NLS-1$
            btnLend.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnLend.setFocusable(false);
            btnLend.setPreferredSize(new java.awt.Dimension(28, 28));
            btnLend.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (!getTfCtlgNo().getText().equals("")) { //$NON-NLS-1$
                        String ctlgno = Validator.convertCtlgNo2DB(getTfCtlgNo().getText().trim());
                        if (!ctlgno.equals("")) { //$NON-NLS-1$
                            lendAction(ctlgno);
                        } else {
                            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.acquisitnowarning"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                    }
                }
            });
        }
        return btnLend;
    }

    public void lendBook(String ctlgno) {
        lendAction(ctlgno);
//    Record record = Cirkulacija.getApp().getRecordsManager().lendBook(ctlgno);
//    if (record != null){
//      getTableModel().addRow(ctlgno, record, defaultLocation);
//    }else{
//      JOptionPane.showMessageDialog(getPanel(), Cirkulacija.getApp().getRecordsManager().getErrorMessage(), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
//          new ImageIcon(getClass().getResource("/com/gint/app/bisis4/client/circ/images/x32.png"))); //$NON-NLS-1$
//    }
    }

    private void lendAction(String ctlgno) {
        if (getLBlockCard().getText().equals("") || Cirkulacija.getApp().getEnvironment().getBlockedCard()) { //$NON-NLS-1$
            if (getTableModel().getRowCount() < parent.getMmbrship().getUserCateg().getTitlesNo()) {
                Record record = Cirkulacija.getApp().getRecordsManager().lendBook(ctlgno);
                handleKeyTyped();
                if (record != null) {
                    boolean exists = getTableModel().addRow(ctlgno, record, defaultLocation, parent.getMmbrship().getUserCateg(), parent.getMmbrship().getUserID());
                    pinRequired = true;
                    if (exists) {
                        JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.alreadyinlist"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                    }
                    getTfCtlgNo().setText(""); //$NON-NLS-1$
                } else {
                    if (Cirkulacija.getApp().getEnvironment().getNonCtlgNo() && !Cirkulacija.getApp().getRecordsManager().existBook()) {
                        boolean exists = getTableModel().addRow(ctlgno, record, defaultLocation, parent.getMmbrship().getUserCateg(), parent.getMmbrship().getUserID());
                        pinRequired = true;
                        if (exists) {
                            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.alreadyinlist"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                        getTfCtlgNo().setText(""); //$NON-NLS-1$
                    } else if (Cirkulacija.getApp().getEnvironment().getAutoReturn() && Cirkulacija.getApp().getRecordsManager().chargedBook()) {
                        String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
                        int op = JOptionPane.showOptionDialog(getPanel(), Messages.getString("circulation.chargingwarning"), Messages.getString("circulation.warning"), JOptionPane.OK_CANCEL_OPTION,  //$NON-NLS-1$ //$NON-NLS-2$
                                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[1]); //$NON-NLS-1$
                        if (op == JOptionPane.YES_OPTION) {
                            boolean success = Cirkulacija.getApp().getUserManager().dischargeUser(ctlgno);
                            if (success) {
                                btnLend.doClick();
                            } else {
                                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.dischargingnotallowed"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(getPanel(), Cirkulacija.getApp().getRecordsManager().getErrorMessage(), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                    }
                }
            } else {
                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.titleno"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
            }
        } else {
            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.blockedcardwarning"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
        }
    }

    private JButton getBtnSearch() {
        if (btnSearch == null) {
            btnSearch = new JButton();
            //btnSearch.setText("Tra\u017ei");
            btnSearch.setToolTipText(Messages.getString("circulation.search")); //$NON-NLS-1$
            btnSearch.setIcon(new ImageIcon(getClass().getResource("/circ-images/find16.png"))); //$NON-NLS-1$
            btnSearch.setFocusable(false);
            btnSearch.setPreferredSize(new java.awt.Dimension(28, 28));
            btnSearch.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Cirkulacija.getApp().getMainFrame().showPanel("searchBooksPanel"); //$NON-NLS-1$
                }
            });
        }
        return btnSearch;
    }

    private JLabel getLBlockCard() {
        if (lBlockCard == null) {
            lBlockCard = new JLabel();
            lBlockCard.setText(""); //$NON-NLS-1$
            lBlockCard.setForeground(Color.red);
        }
        return lBlockCard;
    }

    private JLabel getLDuplicate() {
        if (lDuplicate == null) {
            lDuplicate = new JLabel();
            lDuplicate.setText(""); //$NON-NLS-1$
            lDuplicate.setForeground(Color.red);
        }
        return lDuplicate;
    }

    public void loadDefault() {
        getLWarnings().setVisible(false);
        getBtnWarnings().setVisible(false);
    }

    public void loadLocation(List data) {
        Utils.loadCombo(getCmbLocation(), data);
        int loc = Cirkulacija.getApp().getEnvironment().getLocation();
        for (int i = 1; i < getCmbLocation().getModel().getSize(); i++) {
            if (Integer.parseInt(((CircLocation) getCmbLocation().getModel().getElementAt(i)).getLocationCode()) == loc) {
                defaultLocation = ((CircLocation) getCmbLocation().getModel().getElementAt(i)).getDescription();
            }
        }
        getTableModel().setDefaultLocation(defaultLocation);
    }

    public void loadUser(String userID, String firstName, String lastName, Date untilDate, String note, String dupno, String blocked, List lendings, boolean warnings) {
        getLUser().setText(userID + ", " + firstName + " " + lastName); //$NON-NLS-1$ //$NON-NLS-2$
        if (untilDate != null)
            getLUntilDate().setText(DateFormat.getDateInstance().format(untilDate));
        getLNote().setText(note);
        getLDuplicate().setText(dupno);
        getLBlockCard().setText(blocked);
        getTableModel().setData(lendings);
        if (warnings) {
            getLWarnings().setVisible(true);
            getBtnWarnings().setVisible(true);
        } else {
            getLWarnings().setVisible(false);
            getBtnWarnings().setVisible(false);
        }
        pinRequired = false;
    }

    public void refreshInfo(String userID, String firstName, String lastName, Date untilDate, String note, String dupno, String blocked) {
        getLUser().setText(userID + ", " + firstName + " " + lastName); //$NON-NLS-1$ //$NON-NLS-2$
        if (untilDate != null)
            getLUntilDate().setText(DateFormat.getDateInstance().format(untilDate));
        getLNote().setText(note);
        getLDuplicate().setText(dupno);
        getLBlockCard().setText(blocked);
    }

    public void setTableModel(List<com.ftninformatika.bisis.circ.Lending> lendings) {
        getTableModel().setData(lendings);
    }

    public void clear() {
        getLUser().setText(""); //$NON-NLS-1$
        getLUntilDate().setText(""); //$NON-NLS-1$
        getLNote().setText(""); //$NON-NLS-1$
        getLDuplicate().setText(""); //$NON-NLS-1$
        getLBlockCard().setText(""); //$NON-NLS-1$
        getTfCtlgNo().setText(""); //$NON-NLS-1$
        getLWarnings().setVisible(false);
        getBtnWarnings().setVisible(false);
    }

    private void handleKeyTyped() {
        if (!parent.getDirty())
            parent.setDirty(true);
    }

    public boolean pinRequired() {
        return pinRequired;
    }

    public void setPinRequired(boolean value) {
        pinRequired = value;
    }

    public boolean existLending() {
        return getTableModel().getRowCount() != 0;
    }

}
