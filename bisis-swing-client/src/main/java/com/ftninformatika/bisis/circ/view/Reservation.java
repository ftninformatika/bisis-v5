package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;


import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

/**
 * @author marijakovacevic
 */
public class Reservation {

    private PanelBuilder pb = null;
    private JLabel lUser = null;
    private JLabel lUntilDate = null;
    private JLabel lNote = null;
    private JTextField tfCtlgNo = null;
    private JScrollPane jScrollPane = null;
    private JTable tblLending = null;
    private ReservationTableModel reservationTableModel = null;
    private JButton btnReserve = null;
    private JButton btnSearch = null;
    private JLabel lBlockCard = null;
    private JLabel lDuplicate = null;
    private User parent = null;
    private boolean pinRequired;


    public Reservation(User parent) {
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

        pb.addSeparator(Messages.getString("circulation.reserve"), cc.xyw(2, 8, 10)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.acquisitionnumber"), cc.xyw(2, 9, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getTfCtlgNo(), cc.xy(7, 9));
        pb.add(getBtnReserve(), cc.xy(9, 9, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnSearch(), cc.xy(11, 9, "fill, fill")); //$NON-NLS-1$

        pb.addSeparator(Messages.getString("circulation.reservations"), cc.xyw(2, 16, 14)); //$NON-NLS-1$
        pb.add(getJScrollPane(), cc.xyw(2, 18, 14));
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
                        getBtnReserve().doClick();
                    } else if (e.getKeyCode() == KeyEvent.VK_F12) {
                        parent.save();
                    } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        parent.cancel();
                    }

                }

                public void keyReleased(KeyEvent e) {

                    if (e.getKeyCode() == KeyEvent.VK_ADD) {
                        tfCtlgNo.setText(tfCtlgNo.getText().substring(0, tfCtlgNo.getText().length() - 1));
                    }
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

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTblReservation());
        }
        return jScrollPane;
    }

    private JTable getTblReservation() {
        if (tblLending == null) {
            tblLending = new JTable();
            tblLending.setModel(getTableModel());
            tblLending.putClientProperty("Quaqua.Table.style", "striped");
            tblLending.setAutoCreateRowSorter(true);
            tblLending.setDefaultRenderer(Date.class, new MembershipTableModel.CellRenderer());
            tblLending.setDefaultEditor(Date.class, new MembershipTableModel.CellEditor());
        }
        return tblLending;
    }

    public ReservationTableModel getTableModel() {
        if (reservationTableModel == null) {
            reservationTableModel = new ReservationTableModel();
            reservationTableModel.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    handleKeyTyped();
                }
            });
        }
        return reservationTableModel;
    }

    private JButton getBtnReserve() {
        if (btnReserve == null) {
            btnReserve = new JButton();
            btnReserve.setToolTipText(Messages.getString("circulation.checkout")); //$NON-NLS-1$
            btnReserve.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnReserve.setFocusable(false);
            btnReserve.setPreferredSize(new java.awt.Dimension(28, 28));
            btnReserve.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (!getTfCtlgNo().getText().equals("")) { //$NON-NLS-1$
                        String ctlgno = Validator.convertCtlgNo2DB(getTfCtlgNo().getText().trim());
                        if (!ctlgno.equals("")) { //$NON-NLS-1$
                            reserveBook(ctlgno);
                        } else {
                            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.acquisitnowarning"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                    }
                }
            });
        }
        return btnReserve;
    }

    private void reserveBook(String ctlgno) {
        if (getLBlockCard().getText().equals("") || Cirkulacija.getApp().getEnvironment().getBlockedCard()) { //$NON-NLS-1$
            if (getTableModel().getRowCount() < parent.getMmbrship().getUserCateg().getTitlesNo()) {
                ReservationOnProfile reservation = Cirkulacija.getApp().getRecordsManager().reserveBook(ctlgno);
                handleKeyTyped();
                if (reservation != null) {
//                    boolean exists = getTableModel().addRow(ctlgno, record, defaultLocation, parent.getMmbrship().getUserCateg(), parent.getMmbrship().getUserID());
//                    pinRequired = true;
//                    if (exists) {
//                        JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.alreadyInReservationslist"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
//                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
//                    }
//                    getTfCtlgNo().setText(""); //$NON-NLS-1$
                } else {
//                    if (Cirkulacija.getApp().getEnvironment().getNonCtlgNo() && !Cirkulacija.getApp().getRecordsManager().existBook()) {
//                        boolean exists = getTableModel().addRow(ctlgno, record, defaultLocation, parent.getMmbrship().getUserCateg(), parent.getMmbrship().getUserID());
//                        pinRequired = true;
//                        if (exists) {
//                            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.alreadyinlist"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
//                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
//                        }
//                        getTfCtlgNo().setText(""); //$NON-NLS-1$
//                    } else if (Cirkulacija.getApp().getEnvironment().getAutoReturn() && Cirkulacija.getApp().getRecordsManager().chargedBook()) {
//                        String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
//                        int op = JOptionPane.showOptionDialog(getPanel(), Messages.getString("circulation.chargingwarning"), Messages.getString("circulation.warning"), JOptionPane.OK_CANCEL_OPTION,  //$NON-NLS-1$ //$NON-NLS-2$
//                                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[1]); //$NON-NLS-1$
//                        if (op == JOptionPane.YES_OPTION) {
//                            if (Cirkulacija.getApp().getRecordsManager().isInInventory()) {
//                                JOptionPane.showMessageDialog(null, Messages.getString("circulation.discharginginventory") + " " + ctlgno,
//                                        Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE);
//                            }
//                            boolean success = Cirkulacija.getApp().getUserManager().dischargeUser(ctlgno);
//                            if (success) {
//                                btnLend.doClick();
//                            } else {
//                                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.dischargingnotallowed"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
//                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
//                            }
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(getPanel(), Cirkulacija.getApp().getRecordsManager().getErrorMessage(), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
//                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
//                    }
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

    public void loadUser(String userID, String firstName, String lastName, Date untilDate, String note, String dupno, String blocked, java.util.List<ReservationOnProfile> reservations, boolean warnings) {
        getLUser().setText(userID + ", " + firstName + " " + lastName); //$NON-NLS-1$ //$NON-NLS-2$
        if (untilDate != null)
            getLUntilDate().setText(DateFormat.getDateInstance().format(untilDate));
        getLNote().setText(note);
        getLDuplicate().setText(dupno);
        getLBlockCard().setText(blocked);

        // get only reservations that are in the queue
        List<ReservationOnProfile> activeReservations = new ArrayList<>();
        for (ReservationOnProfile r : reservations) {
            if (!r.getReservationStatus().equals(ReservationStatus.PICKED_UP)) {
                String locationName = Cirkulacija.getApp().getUserManager().getLibraryBranchName(r.getCoderId());
                r.setCoderId(locationName);
                activeReservations.add(r);
            }
        }

        // add reservations to the table
        getTableModel().setData(activeReservations);

        pinRequired = false;
    }


    public void setTableModel(List<ReservationOnProfile> reservations) {
        getTableModel().setData(reservations);
    }

    public void clear() {
        getLUser().setText(""); //$NON-NLS-1$
        getLUntilDate().setText(""); //$NON-NLS-1$
        getLNote().setText(""); //$NON-NLS-1$
        getLDuplicate().setText(""); //$NON-NLS-1$
        getLBlockCard().setText(""); //$NON-NLS-1$
        getTfCtlgNo().setText(""); //$NON-NLS-1$
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
}
