package com.ftninformatika.bisis.circ.merge;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.util.ArrayList;

public class MergeFrame extends JInternalFrame {

    private JPanel mPanel;
    private JComboBox cmbUserId;
    private JLabel lUserId;
    private JButton btnMerge;
    private JButton btnCancel;
    private JLabel lUser;
    private JTable tblUsers;
    private JPanel buttonPanel;
    private MergeUsersTableModel mergeUsersTableModel;
    private JScrollPane scrollPane;
    private java.util.List<String> usersList;

    public MergeFrame() {
        super(Messages.getString("circulation.merge"), true, true, true, true); //$NON-NLS-1$
        initialize();
    }

    private void initialize() {
        this.setSize(new Dimension(600, 600));
        this.setPreferredSize(new Dimension(600, 600));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent e) {
                handleClose();
            }
        });
        this.setContentPane(getMPanel());
//        Dimension screen = getToolkit().getScreenSize();
//        this.setLocation((screen.width - getSize().width) / 2,
//                (screen.height - getSize().height) / 2);
        this.pack();
    }

    private JPanel getMPanel() {
        if (mPanel == null) {
            FormLayout layout = new FormLayout(
                    "10dlu, 120dlu, 100dlu, 20dlu:grow, 10dlu",
                    "10dlu, pref, 5dlu, pref, 2dlu, 20dlu:grow, 5dlu, pref, 10dlu");
            CellConstraints cc = new CellConstraints();
            PanelBuilder pb = new PanelBuilder(layout);
            pb.setDefaultDialogBorder();
            pb.add(getLUserId(), cc.xy(2, 2));
            pb.add(getCmbUserId(), cc.xy(3, 2));
            pb.add(getLUser(), cc.xyw(2, 4, 2));
            pb.add(getScrollPane(), cc.xyw(2, 6, 3, "fill, fill"));
            pb.add(getButtonPanel(), cc.xyw(2, 8, 3));
            mPanel = pb.getPanel();
        }
        return mPanel;
    }

    public JLabel getLUserId() {
        if (lUserId == null) {
            lUserId = new JLabel();
            lUserId.setText(Messages.getString("circulation.choosememberid"));
        }
        return lUserId;
    }

    private JComboBox getCmbUserId() {
        if (cmbUserId == null) {
            cmbUserId = new JComboBox();
        }
        return cmbUserId;
    }

    private JButton getBtnMerge() {
        if (btnMerge == null) {
            btnMerge = new JButton();
            btnMerge.setText(Messages.getString("circulation.merge")); //$NON-NLS-1$
            btnMerge.setIcon(new ImageIcon(getClass().getResource("/circ-images/user_group16.png"))); //$NON-NLS-1$
            btnMerge.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (getTblUsers().getSelectedRow() != -1) {
                        String selectedUser = getMergeUsersTableModel().getUser(getTblUsers().getSelectedRow()).getUserId();
                        String userId = (String)getCmbUserId().getSelectedItem();
                        boolean done = Cirkulacija.getApp().getUserManager().mergeUsers(selectedUser, userId, usersList);
                        if (done){
                            JOptionPane.showMessageDialog(MergeFrame.this, Messages.getString("circulation.saved"), Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE,
                                    new ImageIcon(getClass().getResource("/circ-images/hand32.png")));
                        }else{
                            JOptionPane.showMessageDialog(MergeFrame.this, Messages.getString("circulation.saveerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                        }
                    } else {
                        JOptionPane.showMessageDialog(MergeFrame.this, Messages.getString("circulation.membernotchoosen"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                    }
                }
            });
        }
        return btnMerge;
    }

    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
            btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    handleClose();
                }
            });
        }
        return btnCancel;
    }

    private void handleClose () {
        releaseUsers(getMergeUsersTableModel().getData());
    }

    private void releaseUsers(java.util.List<MemberData> memberDataList) {
        boolean released = Cirkulacija.getApp().getUserManager().releaseUsers(memberDataList);
        if (released) {
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, Messages.getString("circulation.releaseerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                    new ImageIcon(getClass().getResource("/circ-images/x32.png")));
        }
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(30);
            buttonPanel = new JPanel(flowLayout);
            buttonPanel.add(getBtnMerge());
            buttonPanel.add(getBtnCancel());
        }
        return buttonPanel;
    }

    private JLabel getLUser() {
        if (lUser == null) {
            lUser = new JLabel();
            lUser.setText(Messages.getString("circulation.choosemember"));
        }
        return lUser;
    }

    private JTable getTblUsers() {
        if (tblUsers == null) {
            tblUsers = new JTable(getMergeUsersTableModel());
            //tblUsers.setAutoCreateRowSorter(true);
            tblUsers.putClientProperty("Quaqua.Table.style", "striped");
        }
        return tblUsers;
    }

    private MergeUsersTableModel getMergeUsersTableModel() {
        if (mergeUsersTableModel == null) {
            mergeUsersTableModel = new MergeUsersTableModel();
        }
        return mergeUsersTableModel;
    }

    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane();
            scrollPane.setViewportView(getTblUsers());
        }
        return scrollPane;
    }

    public void setData(java.util.List<MemberData> memberDataList) {
        boolean inUse = false;
        for (MemberData memberData: memberDataList) {
            if (memberData.getInUseBy() != null) {
                inUse = true;
                JOptionPane.showMessageDialog(this, Messages.getString("circulation.userinuse"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(getClass().getResource("/circ-images/x32.png")));
            }
        }
        if (!inUse) {
            usersList = new ArrayList<>();
            for (MemberData memberData: memberDataList) {
                getCmbUserId().addItem(memberData.getMember().getUserId());
                usersList.add(memberData.getMember().getUserId());
            }
            getMergeUsersTableModel().setData(memberDataList);
            updateRowHeights();
        } else {
            releaseUsers(memberDataList);
        }
    }

    private void updateRowHeights() {
        for (int row = 0; row < getTblUsers().getRowCount(); row++) {
            int rowHeight = getTblUsers().getRowHeight();

            for (int column = 0; column < getTblUsers().getColumnCount(); column++)
            {
                Component comp = getTblUsers().prepareRenderer(getTblUsers().getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }

            getTblUsers().setRowHeight(row, rowHeight);
        }
    }
}
