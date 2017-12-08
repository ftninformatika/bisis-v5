package com.ftninformatika.bisis.circ.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;


public class Picturebooks {

    private PanelBuilder pb = null;
    private JLabel lDate = null;
    private JLabel lLend = null;
    private JLabel lReturn = null;
    private JDateChooser tfDate = null;
    private JLabel lState = null;
    private JLabel tfState = null;
    private JTextField tfLend = null;
    private JTextField tfReturn = null;
    private JButton btnLend = null;
    private JScrollPane jScrollPane = null;
    private JTable table = null;
    private PicturebooksTableModel tableModel = null;
    private User parent = null;
    private boolean pinRequired;


    public Picturebooks(User parent) {
        this.parent = parent;
        makePanel();
    }

    private void makePanel() {
        if (pb == null) {
            FormLayout layout = new FormLayout(
                    "20dlu, right:40dlu, 5dlu, 18dlu, 32dlu, 160dlu, 20dlu:grow",
                    "10dlu, pref, 3dlu, pref, 5dlu, pref, 5dlu, pref, 2dlu, pref, 5dlu, 18dlu, 20dlu, pref, 5dlu, 80dlu:grow, 10dlu");
            CellConstraints cc = new CellConstraints();
            pb = new PanelBuilder(layout);
            pb.setDefaultDialogBorder();

            pb.addSeparator("Stanje", cc.xyw(2, 2, 4));
            pb.add(getLDate(), cc.xy(2, 4));
            pb.add(getTfDate(), cc.xyw(4, 4, 2));
            pb.add(getLState(), cc.xy(2, 6));
            pb.add(getTfState(), cc.xyw(4, 6, 2));
            pb.add(getLLend(), cc.xy(2, 8));
            pb.add(getTfLend(), cc.xyw(4, 8, 2));
            pb.add(getLReturn(), cc.xy(2, 10));
            pb.add(getTfReturn(), cc.xyw(4, 10, 2));
            pb.add(getBtnLend(), cc.xy(4, 12));

            pb.addSeparator("Istorija", cc.xyw(2, 14, 5));
            pb.add(getJScrollPane(), cc.xyw(2, 16, 5));

        }
    }

    public JPanel getPanel() {
        return pb.getPanel();
    }

    public JLabel getLDate() {
        if (lDate == null) {
            lDate = new JLabel(Messages.getString("circulation.date"));
        }
        return lDate;
    }

    public JDateChooser getTfDate() {
        if (tfDate == null) {
            tfDate = new JDateChooser();
        }
        return tfDate;
    }

    public JLabel getLState() {
        if (lState == null) {
            lState = new JLabel(Messages.getString("circulation.state"));
        }
        return lState;
    }

    public JLabel getTfState() {
        if (tfState == null) {
            tfState = new JLabel();
            tfState.setForeground(Color.blue);
        }
        return tfState;
    }

    public JLabel getLLend() {
        if (lLend == null) {
            lLend = new JLabel(Messages.getString("circulation.checkout2"));
        }
        return lLend;
    }

    public JTextField getTfLend() {
        if (tfLend == null) {
            tfLend = new JTextField();
        }
        return tfLend;
    }

    public JLabel getLReturn() {
        if (lReturn == null) {
            lReturn = new JLabel(Messages.getString("circulation.discharge2"));
        }
        return lReturn;
    }

    public JTextField getTfReturn() {
        if (tfReturn == null) {
            tfReturn = new JTextField();
        }
        return tfReturn;
    }

    private JButton getBtnLend() {
        if (btnLend == null) {
            btnLend = new JButton();
            btnLend.setToolTipText(Messages.getString("circulation.checkout"));
            btnLend.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png")));
            btnLend.setFocusable(false);
            btnLend.setPreferredSize(new java.awt.Dimension(28, 28));
            btnLend.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        int tmpl = 0;
                        int tmpr = 0;
                        if (!getTfLend().getText().equals("")) {
                            tmpl = Integer.parseInt(getTfLend().getText());
                        }
                        if (!getTfReturn().getText().equals("")) {
                            tmpr = Integer.parseInt(getTfReturn().getText());
                        }
                        int tmps = Integer.parseInt(getTfState().getText()) + tmpl - tmpr;
                        getTableModel().addRow(getTfDate().getDate(), tmpl, tmpr, tmps);
                        setDirty();
                        getTfState().setText(String.valueOf(tmps));
                        getTfLend().setText("");
                        getTfReturn().setText("");
                        pinRequired = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.error_enter"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
                                new ImageIcon(getClass().getResource("/circ-images/images/x32.png")));
                    }
                }
            });
        }
        return btnLend;
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTable());
        }
        return jScrollPane;
    }

    private JTable getTable() {
        if (table == null) {
            table = new JTable();
            table.setModel(getTableModel());
        }
        return table;
    }

    private PicturebooksTableModel getTableModel() {
        if (tableModel == null) {
            tableModel = new PicturebooksTableModel();
        }
        return tableModel;
    }

    public void setData(List data) {
        getTableModel().setData(data);
        getTfDate().setDate(new Date());
        if (getTable().getRowCount() > 0) {
            getTfState().setText(String.valueOf(getTable().getValueAt(getTable().getRowCount() - 1, 3)));
        } else {
            getTfState().setText("0");
        }
        pinRequired = false;
    }

    private void setDirty() {
        if (!parent.getDirty())
            parent.setDirty(true);
    }

    public boolean pinRequired() {
        return pinRequired;
    }

    public void setPinRequired(boolean value) {
        pinRequired = value;
    }


}
