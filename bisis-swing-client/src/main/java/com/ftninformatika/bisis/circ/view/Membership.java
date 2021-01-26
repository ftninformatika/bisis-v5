package com.ftninformatika.bisis.circ.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.pojo.*;
import com.ftninformatika.utils.string.StringUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import com.ftninformatika.utils.Messages;

public class    Membership {


    private PanelBuilder pb = null;
    private JComboBox cmbBranch = null;
    private JTextField tfUserID = null;
    private JLabel lUserID = null;
    private JComboBox cmbCateg = null;
    private JLabel lCateg = null;
    private JComboBox cmbMmbrType = null;
    private JLabel lMmbrType = null;
    private JComboBox cmbBranchID = null;
    private JTextField tfBranch = null;
    private JButton btnAuto = null;
    private ButtonGroup rbGrpGroup = null;
    private JRadioButton rbGroupY = null;
    private JRadioButton rbGroupN = null;
    private JComboBox cmbGroups = null;
    private JScrollPane jScrollPane = null;
    private JTable tblMmbrship = null;
    private MembershipTableModel mmbrshipTableModel = null;
    private JButton btnAdd = null;
    private JButton btnRemove = null;
    private JButton btnPrint = null;
    private ComboBoxRenderer cmbRenderer = null;
    private String defaultLocation = null;
    private CmbKeySelectionManager cmbKeySelectionManager = null;
    private User parent = null;

    public Membership(User parent) {
        this.parent = parent;
        makePanel();
    }

    private void makePanel() {
        FormLayout layout = new FormLayout(
                "2dlu:grow ,right:65dlu, 3dlu, 20dlu, 2dlu, 50dlu, 8dlu, 40dlu, 35dlu, right:80dlu, 3dlu, 95dlu, 5dlu, 18dlu, 2dlu:grow",  //$NON-NLS-1$
                "5dlu, pref, 2dlu, 15dlu, 2dlu, pref, 35dlu, pref, 2dlu, 15dlu, 30dlu, pref, 2dlu, 5dlu, 18dlu, 6dlu, 18dlu, 6dlu, 18dlu, 5dlu, 2dlu:grow"); //$NON-NLS-1$
        CellConstraints cc = new CellConstraints();
        pb = new PanelBuilder(layout);
        pb.setDefaultDialogBorder();

        pb.addSeparator(Messages.getString("circulation.usernumber"), cc.xyw(2, 2, 7)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.location"), cc.xy(2, 4)); //$NON-NLS-1$
        pb.add(getCmbBranchID(), cc.xyw(4, 4, 5, "fill, fill")); //$NON-NLS-1$
        pb.add(getUserIDLabel(), cc.xy(2, 6));
        pb.add(getTfBranch(), cc.xy(4, 6));
        pb.add(getTfUserID(), cc.xy(6, 6));
        pb.add(getBtnAuto(), cc.xy(8, 6));

        pb.addSeparator("", cc.xyw(2, 8, 7)); //$NON-NLS-1$
        pb.add(getUserCategLabel(), cc.xy(2, 10));
        pb.add(getCmbCateg(), cc.xyw(4, 10, 5, "fill, fill")); //$NON-NLS-1$

        pb.addSeparator("", cc.xyw(10, 8, 5)); //$NON-NLS-1$
        pb.add(getMmbrTypeLabel(), cc.xy(10, 10));
        pb.add(getCmbMmbrType(), cc.xyw(12, 10, 3, "fill, fill")); //$NON-NLS-1$

        pb.addSeparator(Messages.getString("circulation.corporate"), cc.xyw(10, 2, 5)); //$NON-NLS-1$
        pb.add(getRbGroupY(), cc.xy(10, 4));
        pb.add(getRbGroupN(), cc.xy(10, 6));
        pb.add(getCmbGroups(), cc.xyw(12, 4, 3, "fill, fill")); //$NON-NLS-1$

        pb.addSeparator(Messages.getString("circulation.membershipfee"), cc.xyw(2, 12, 13)); //$NON-NLS-1$
        pb.add(getJScrollPane(), cc.xywh(2, 14, 11, 7));
        pb.add(getBtnAdd(), cc.xy(14, 15, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnRemove(), cc.xy(14, 17, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnPrint(), cc.xy(14, 19, "fill, fill")); //$NON-NLS-1$

    }

    public JPanel getPanel() {
        return pb.getPanel();
    }

    private ComboBoxRenderer getCmbRenderer() {
        if (cmbRenderer == null) {
            cmbRenderer = new ComboBoxRenderer();
        }
        return cmbRenderer;
    }

    private CmbKeySelectionManager getCmbKeySelectionManager() {
        if (cmbKeySelectionManager == null) {
            cmbKeySelectionManager = new CmbKeySelectionManager();
        }
        return cmbKeySelectionManager;
    }

    private JComboBox getCmbBranch() {
        if (cmbBranch == null) {
            cmbBranch = new JComboBox();
            cmbBranch.setRenderer(getCmbRenderer());
            cmbBranch.setKeySelectionManager(getCmbKeySelectionManager());
            cmbBranch.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return cmbBranch;
    }

    private JTextField getTfUserID() {
        if (tfUserID == null) {
            tfUserID = new JTextField();
            tfUserID.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return tfUserID;
    }

    public JLabel getUserIDLabel() {
        if (lUserID == null) {
            lUserID = new JLabel(Messages.getString("circulation.usernumber")); //$NON-NLS-1$
        }
        return lUserID;
    }

    private JComboBox getCmbCateg() {
        if (cmbCateg == null) {
            cmbCateg = new JComboBox();
            cmbCateg.setRenderer(getCmbRenderer());
            cmbCateg.setKeySelectionManager(getCmbKeySelectionManager());
            cmbCateg.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return cmbCateg;
    }

    public JLabel getUserCategLabel() {
        if (lCateg == null) {
            lCateg = new JLabel(Messages.getString("circulation.usercategory")); //$NON-NLS-1$
        }
        return lCateg;
    }

    private JComboBox getCmbMmbrType() {
        if (cmbMmbrType == null) {
            cmbMmbrType = new JComboBox();
            cmbMmbrType.setRenderer(getCmbRenderer());
            cmbMmbrType.setKeySelectionManager(getCmbKeySelectionManager());
            cmbMmbrType.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return cmbMmbrType;
    }

    public JLabel getMmbrTypeLabel() {
        if (lMmbrType == null) {
            lMmbrType = new JLabel(Messages.getString("circulation.membershiptype")); //$NON-NLS-1$
        }
        return lMmbrType;
    }

    private JComboBox getCmbBranchID() {
        if (cmbBranchID == null) {
            cmbBranchID = new JComboBox();
            cmbBranchID.setRenderer(getCmbRenderer());
            cmbBranchID.setKeySelectionManager(getCmbKeySelectionManager());
            if (!Cirkulacija.getApp().getEnvironment().getUseridPrefix())
                cmbBranchID.setEnabled(false);
            cmbBranchID.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (Cirkulacija.getApp().getEnvironment().getUseridPrefix()) {
                        if (Utils.getCmbValue(cmbBranchID) != null) {
                            getTfBranch().setText(StringUtils.padZeros(Integer.parseInt(((CircLocation) cmbBranchID.getSelectedItem()).getLocationCode()), Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
                            getTfUserID().setText(""); //$NON-NLS-1$
                        }
                    }
                }
            });
            cmbBranchID.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return cmbBranchID;
    }

    private JTextField getTfBranch() {
        if (tfBranch == null) {
            tfBranch = new JTextField();
            tfBranch.setEditable(false);
        }
        return tfBranch;
    }

    private JButton getBtnAuto() {
        if (btnAuto == null) {
            btnAuto = new JButton();
            btnAuto.setText(Messages.getString("circulation.auto")); //$NON-NLS-1$
            btnAuto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (getTfUserID().getText().equals("")) { //$NON-NLS-1$
                        String id = Cirkulacija.getApp().getUserManager().getUserId(getTfBranch().getText());
                        if (id != null) {
                            getTfUserID().setText(id);
                            handleKeyTyped();
                        } else {
                            JOptionPane.showMessageDialog(null, Messages.getString("circulation.error_message_data"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }
        return btnAuto;
    }

    private ButtonGroup getRbGrpGroup() {
        if (rbGrpGroup == null) {
            rbGrpGroup = new ButtonGroup();
        }
        return rbGrpGroup;
    }

    private JRadioButton getRbGroupY() {
        if (rbGroupY == null) {
            rbGroupY = new JRadioButton();
            rbGroupY.setText(Messages.getString("circulation.yes")); //$NON-NLS-1$
            rbGroupY.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getCmbGroups().setEnabled(true);
                    handleKeyTyped();
                }
            });
            getRbGrpGroup().add(rbGroupY);
        }
        return rbGroupY;
    }

    private JRadioButton getRbGroupN() {
        if (rbGroupN == null) {
            rbGroupN = new JRadioButton();
            rbGroupN.setText(Messages.getString("circulation.no")); //$NON-NLS-1$
            rbGroupN.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    getCmbGroups().setEnabled(false);
                    handleKeyTyped();
                }
            });
            rbGroupN.setSelected(true);
            getCmbGroups().setEnabled(false);
            getRbGrpGroup().add(rbGroupN);
        }
        return rbGroupN;
    }

    private JComboBox getCmbGroups() {
        if (cmbGroups == null) {
            cmbGroups = new JComboBox();
            cmbGroups.setRenderer(getCmbRenderer());
            cmbGroups.setKeySelectionManager(getCmbKeySelectionManager());
            cmbGroups.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return cmbGroups;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTblMmbrship());
        }
        return jScrollPane;
    }

    private JTable getTblMmbrship() {
        if (tblMmbrship == null) {
            tblMmbrship = new JTable();
            tblMmbrship.setModel(getTableModel());
            tblMmbrship.putClientProperty("Quaqua.Table.style", "striped");
            TableColumn comboColumn = tblMmbrship.getColumnModel().getColumn(0);
            comboColumn.setCellEditor(new DefaultCellEditor(getCmbBranch()));
            comboColumn.setCellRenderer(new CmbTableRenderer());
//			comboColumn.setPreferredWidth(120);
            tblMmbrship.setDefaultEditor(Date.class, new MembershipTableModel.CellEditor());
            tblMmbrship.setDefaultRenderer(Date.class, new MembershipTableModel.CellRenderer());
        }
        return tblMmbrship;
    }

    private MembershipTableModel getTableModel() {
        if (mmbrshipTableModel == null) {
            mmbrshipTableModel = new MembershipTableModel();
            mmbrshipTableModel.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return mmbrshipTableModel;
    }

    public void setTableModel(List signings) {
        getTableModel().setData(signings);
    }

    public void fixTable() {
        TableColumn comboColumn = getTblMmbrship().getColumnModel().getColumn(0);
        comboColumn.setCellEditor(new DefaultCellEditor(getCmbBranch()));
        comboColumn.setCellRenderer(new CmbTableRenderer());
        comboColumn.setPreferredWidth(120);
        tblMmbrship.setDefaultEditor(Date.class, new MembershipTableModel.CellEditor());
        tblMmbrship.setDefaultRenderer(Date.class, new MembershipTableModel.CellRenderer());
    }

    public JLabel getMmbrshipDateLabel() {
        return new ColumnNameLabel(1);
    }

    public JLabel getMmbrshipUntilDateLabel() {
        return new ColumnNameLabel(2);
    }

    public JLabel getMmbrshipCostLabel() {
        return new ColumnNameLabel(3);
    }

    public JLabel getMmbrshipReceiptIdLabel() {
        return new ColumnNameLabel(4);
    }

    private JButton getBtnAdd() {
        if (btnAdd == null) {
            btnAdd = new JButton();
            btnAdd.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnAdd.setToolTipText(Messages.getString("circulation.add")); //$NON-NLS-1$
            btnAdd.setFocusable(false);
            //btnAdd.setPreferredSize(new java.awt.Dimension(28,28));
            btnAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean addRow = false;
                    if (getTableModel().getRowCount() == 0) {
                        addRow = true;
                    } else {
                        Date untilDate = (Date) getTableModel().getValueAt(getTableModel().getRowCount() - 1, 2);
                        Date today = new Date();
                        if (untilDate == null || today.after(untilDate)) {
                            addRow = true;
                        } else {
                            String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
                            int op = JOptionPane.showOptionDialog(null, Messages.getString("circulation.membershipwarning"), Messages.getString("circulation.warning"),  //$NON-NLS-1$ //$NON-NLS-2$
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[1]); //$NON-NLS-1$
                            if (op == JOptionPane.YES_OPTION) {
                                addRow = true;
                            }
                        }
                    }
                    if (addRow) {
                        getTableModel().addRow(defaultLocation);
                        computeMmbrship();
                        computePeriod();
                    }
                }
            });
        }
        return btnAdd;
    }

    private JButton getBtnRemove() {
        if (btnRemove == null) {
            btnRemove = new JButton();
            btnRemove.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png"))); //$NON-NLS-1$
            btnRemove.setToolTipText(Messages.getString("circulation.delete")); //$NON-NLS-1$
            btnRemove.setFocusable(false);
            //btnRemove.setPreferredSize(new java.awt.Dimension(28,28));
            btnRemove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (getTblMmbrship().getSelectedRow() != -1) {
                        if (getTblMmbrship().getCellEditor() != null)
                            getTblMmbrship().getCellEditor().stopCellEditing();
                        getTableModel().removeRow(getTblMmbrship().getSelectedRow());
                    }
//						if (!parent.getDirty())
//				      parent.setDirty(true);
                }
            });
        }
        return btnRemove;
    }

    private JButton getBtnPrint() {
        if (btnPrint == null) {
            btnPrint = new JButton();
            btnPrint.setIcon(new ImageIcon(getClass().getResource("/circ-images/doc_rich16.png"))); //$NON-NLS-1$
            btnPrint.setToolTipText(Messages.getString("circulation.receipt")); //$NON-NLS-1$
            btnPrint.setFocusable(false);
            btnPrint.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (getTblMmbrship().getSelectedRow() != -1) {
//          TODO print receipt
                    }
                }
            });
        }
        return btnPrint;
    }

    public String getUserID() {
        return Utils.makeUserId(getTfBranch().getText().trim(), getTfUserID().getText().trim());
    }

    public MembershipType getMmbrType() {
        return (MembershipType) Utils.getCmbValue(getCmbMmbrType());
    }

    public UserCategory getUserCateg() {
        return (UserCategory) Utils.getCmbValue(getCmbCateg());
    }

    public CorporateMember getGroup() {
        return (CorporateMember) Utils.getCmbValue(getCmbGroups());
    }

    public String getMmbrshipDate() {
        try {
            return getTblMmbrship().getModel().getValueAt(getTblMmbrship().getModel().getRowCount() - 1, 1).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMmbrshipUntilDate() {
        try {
            return getTblMmbrship().getModel().getValueAt(getTblMmbrship().getModel().getRowCount() - 1, 2).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMmbrshipCost() {
        try {
            return getTblMmbrship().getModel().getValueAt(getTblMmbrship().getModel().getRowCount() - 1, 3).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMmbrshipReceiptId() {
        try {
            return getTblMmbrship().getModel().getValueAt(getTblMmbrship().getModel().getRowCount() - 1, 4).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public void loadDefault() {
        getCmbGroups().setSelectedIndex(0);
        getRbGroupN().setSelected(true);
//        if (Cirkulacija.getApp().getEnvironment().getUseridPrefix()) {
            int loc = Cirkulacija.getApp().getEnvironment().getLocation();
            for (int i = 1; i < getCmbBranchID().getModel().getSize(); i++) {
                if (Integer.parseInt(((CircLocation) getCmbBranchID().getModel().getElementAt(i)).getLocationCode()) == loc) {
                    getCmbBranchID().setSelectedIndex(i);
                }
            }
//        }
        getTfUserID().setEditable(true);
        getTfBranch().setEditable(true);
        getCmbBranchID().setEnabled(true);
        getBtnAuto().setEnabled(true);
    }

    public void loadMmbrType(List data) {
        Utils.loadCombo(getCmbMmbrType(), data);
    }

    public void loadUserCateg(List data) {
        Utils.loadCombo(getCmbCateg(), data);
    }

    public void loadGroups(List data) {
        Utils.loadCombo(getCmbGroups(), data);
    }

    public void loadLocation(List data) {
        Utils.loadCombo(getCmbBranch(), data);
    }

    public void loadBranchID(List data) {
        Utils.loadCombo(getCmbBranchID(), data);
        int loc = Cirkulacija.getApp().getEnvironment().getLocation();
        for (int i = 1; i < getCmbBranchID().getModel().getSize(); i++) {
            if (Integer.parseInt(((CircLocation) getCmbBranchID().getModel().getElementAt(i)).getLocationCode()) == loc) {
                defaultLocation = ((CircLocation) getCmbBranchID().getModel().getElementAt(i)).getDescription();
            }
        }
    }

    public void loadUser(String userID, MembershipType mt, UserCategory uc, CorporateMember group, List<Signing> signings) {
        if (userID.length() == Cirkulacija.getApp().getEnvironment().getUseridLength() && Cirkulacija.getApp().getEnvironment().getUseridPrefix()) {
            getTfBranch().setText(userID.substring(0, Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
            int loc = Integer.parseInt(getTfBranch().getText());
            for (int i = 1; i < getCmbBranchID().getModel().getSize(); i++) {
                if (Integer.parseInt(((CircLocation) getCmbBranchID().getModel().getElementAt(i)).getLocationCode()) == loc) {
                    getCmbBranchID().setSelectedIndex(i);
                }
            }
            getTfUserID().setText(userID.substring(Cirkulacija.getApp().getEnvironment().getUseridPrefixLength()));
        } else {
            getTfUserID().setText(userID);
        }
        getTfUserID().setEditable(false);
        getTfBranch().setEditable(false);
        getCmbBranchID().setEnabled(false);
        getBtnAuto().setEnabled(false);
        Utils.setComboItem(getCmbMmbrType(), mt);
        Utils.setComboItem(getCmbCateg(), uc);
        Utils.setComboItem(getCmbGroups(), group);
        if (group != null) {
            getRbGroupY().doClick();
        } else {
            getRbGroupN().doClick();
        }
        getTableModel().setData(signings);
    }

    public void clear() {
        Utils.clear(getPanel());
        if (getTblMmbrship().getCellEditor() != null)
            getTblMmbrship().getCellEditor().stopCellEditing();
    }

    private void computeMmbrship() {
        if (getMmbrType() != null && getUserCateg() != null) {
            ((MembershipTableModel) getTblMmbrship().getModel()).computeMmbrship(getMmbrType(), getUserCateg());
        }
    }

    private void computePeriod() {
        if (getMmbrType() != null) {
            ((MembershipTableModel) getTblMmbrship().getModel()).computePeriod(getMmbrType().getPeriod());
        }
    }

    public ComboBoxModel getUserCategsModel() {
        return getCmbCateg().getModel();
    }

    public ComboBoxModel getMmbrTypesModel() {
        return getCmbMmbrType().getModel();
    }

    public ComboBoxModel getGroupsModel() {
        return getCmbGroups().getModel();
    }

    private void handleKeyTyped() {
        if (!parent.getDirty())
            parent.setDirty(true);
    }

    class ColumnNameLabel extends JLabel {
        private int col;

        public ColumnNameLabel(int col) {
            super();
            this.col = col;
            this.setText(getTableModel().getColumnName(col));
            this.addPropertyChangeListener("text", new PropertyChangeListener() { //$NON-NLS-1$
                public void propertyChange(PropertyChangeEvent evt) {
                    getTableModel().setColumnName(getCol(), getText());
                }
            });
        }

        public int getCol() {
            return col;
        }

    }

}
