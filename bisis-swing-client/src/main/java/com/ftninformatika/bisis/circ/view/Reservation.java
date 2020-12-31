package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.validator.Validator;
import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.books.Item;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.Messages;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

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
    private JButton btnReturn = null;
    private JLabel lBlockCard = null;
    private JLabel lDuplicate = null;
    private User parent = null;
    private boolean pinRequired;
    private Record currentRecord = null;
    private String currentInvNum = "";
    private JLabel lInvNumber = null;
    private JLabel lTitle = null;
    private JLabel lAuthor = null;
    HashMap<String, String> locations = new HashMap<>();
    private JComboBox cmbGroups = null;
    private CmbKeySelectionManager cmbKeySelectionManager = null;
    private ComboBoxRenderer cmbRenderer = null;
    private JButton btnAddToTable = null;


    public Reservation(User parent) {
        this.parent = parent;
        this.clear();
        this.makePanel();
    }

    private void makePanel() {
        FormLayout layout = new FormLayout(
                "2dlu:grow, 20dlu, 18dlu, 20dlu, 18dlu, 3dlu, 120dlu, 15dlu, 18dlu, 15dlu, 18dlu, 50dlu, 70dlu, 5dlu, 70dlu, 2dlu:grow", //$NON-NLS-1$
                "5dlu, pref, 2dlu, pref, 5dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 3dlu, pref, 5dlu, pref, 2dlu, 110dlu, 2dlu, 18dlu, 2dlu:grow "); //$NON-NLS-1$

        CellConstraints cc = new CellConstraints();
        pb = new PanelBuilder(layout);
        pb.setDefaultDialogBorder();

        pb.addSeparator(Messages.getString("circulation.reserve"), cc.xyw(2, 2, 10)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.acquisitionnumber"), cc.xyw(2, 4, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getTfCtlgNo(), cc.xy(7, 4));
        pb.add(getBtnReserve(), cc.xy(9, 4, "fill, fill")); //$NON-NLS-1$
        pb.add(getBtnSearch(), cc.xy(11, 4, "fill, fill")); //$NON-NLS-1$

        pb.addSeparator(Messages.getString("circulation.chooseLocation"), cc.xyw(2, 6, 10)); //$NON-NLS-1$
        pb.addLabel(Messages.getString("circulation.acquisitionnumber"), cc.xyw(2, 8, 4, "right, center")); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getLinvNumber(), cc.xyw(7, 8, 4)); //$NON-NLS-1$ //$NON-NLS-2$
        pb.addLabel(Messages.getString("circulation.title2"), cc.xyw(2, 10, 4, "right, center")); //$NON-NLS-1$
        pb.add(getLtitle(), cc.xyw(7, 10, 4)); //$NON-NLS-1$ //$NON-NLS-2$
        pb.addLabel(Messages.getString("circulation.author2"), cc.xyw(2, 12, 4, "right, center")); //$NON-NLS-1$
        pb.add(getLauthor(), cc.xyw(7, 12, 4)); //$NON-NLS-1$ //$NON-NLS-2$
        pb.add(getCmbGroups(), cc.xy(7, 14)); //$NON-NLS-1$
        pb.add(getBtnAddToTable(), cc.xy(9, 14, "fill, fill")); //$NON-NLS-1$ //$NON-NLS-2$

        pb.addSeparator(Messages.getString("circulation.reservations"), cc.xyw(2, 16, 14)); //$NON-NLS-1$
        pb.add(getJScrollPane(), cc.xyw(2, 18, 14));
        pb.add(getBtnReturn(), cc.xy(2, 20, "fill, fill")); //$NON-NLS-1$

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
                            reserveBook(null, ctlgno);
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

    public boolean isReservationLimitExceeded() {
        int numberOfReservedBook = getTableModel().getRowCount()
                + Cirkulacija.getApp().getRecordsManager().getListOfBooksToBeReserved().size();
        return numberOfReservedBook >= 3;
    }

    public void reserveBook(Record record, String ctlgNo) {
        clear();
        // check if card is blocked
        if (getLBlockCard().getText().equals("") || Cirkulacija.getApp().getEnvironment().getBlockedCard()) { //$NON-NLS-1$

            if (!isReservationLimitExceeded()) {
                // 1. flow - rezervise se preko buttona
                if (record == null) {
                    record = Cirkulacija.getApp().getRecordsManager().getRecord(ctlgNo);
                }

                if (record != null) {
                    // check if book is already reserved
                    if (getTableModel().isBookInTable(record)) {
                        JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.alreadyInReservationslist"),
                                Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                    } else {

                        currentRecord = record;
                        currentInvNum = ctlgNo;
                        fillLabels();
                        pinRequired = true;

                        getTfCtlgNo().setText(""); //$NON-NLS-1$
                        handleKeyTyped();
                    }
                } else {
                    JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.acquisitnowarning"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                            new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                }
            } else {
                JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.reservationLimitExceeded"),
                        Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
            }
        } else {
            JOptionPane.showMessageDialog(getPanel(), Messages.getString("circulation.blockedcardwarning"),
                    Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
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

    private void fillLabels() {
        RecordBean bean = new RecordBean(currentRecord);
        lInvNumber.setText(currentInvNum);
        lTitle.setText(bean.getNaslov());
        lAuthor.setText(bean.getAutor());
        loadLocations();
    }

    public void loadLocations() {
        locations.clear();
        getCmbGroups().removeAllItems();
        Book book = null;
        try {
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), currentRecord.get_id());
            book = BisisApp.bisisService.getBookLocations(requestBody).execute().body();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (book != null) {
            for (Item i : book.getItems()) {
                locations.put(i.getLocation(), i.getLocCode());
            }
            List<String> locationsDescriptions = new ArrayList<>(locations.keySet());
            Collections.sort(locationsDescriptions);
            Utils.loadCombo(getCmbGroups(), locationsDescriptions);
            btnAddToTable.setEnabled(false);
        }
    }


    private JButton getBtnAddToTable() {
        if (btnAddToTable == null) {
            btnAddToTable = new JButton();
            btnAddToTable.setToolTipText(Messages.getString("circulation.addToTable")); //$NON-NLS-1$
            btnAddToTable.setIcon(new ImageIcon(getClass().getResource("/circ-images/plus16.png"))); //$NON-NLS-1$
            btnAddToTable.setFocusable(false);
            btnAddToTable.setEnabled(false);
            btnAddToTable.setPreferredSize(new java.awt.Dimension(28, 28));
            btnAddToTable.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (currentRecord != null) {
                        String locDescription = Objects.requireNonNull(getCmbGroups().getSelectedItem()).toString();
                        getTableModel().addRow(currentInvNum, currentRecord, locations.get(locDescription));
                        // add the book to the temporary list
                        Cirkulacija.getApp().getRecordsManager().reserveBook(currentRecord);
                        clear();
                        btnAddToTable.setEnabled(false);
                    }
                }
            });
        }
        return btnAddToTable;
    }

    private JComboBox getCmbGroups() {
        if (cmbGroups == null) {
            cmbGroups = new JComboBox();
            cmbGroups.setRenderer(getCmbRenderer());
            cmbGroups.setKeySelectionManager(getCmbKeySelectionManager());
            cmbGroups.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (currentRecord != null) {
                        btnAddToTable.setEnabled(true);
                    }
                    handleKeyTyped();
                }
            });
        }
        return cmbGroups;
    }

    private CmbKeySelectionManager getCmbKeySelectionManager() {
        if (cmbKeySelectionManager == null) {
            cmbKeySelectionManager = new CmbKeySelectionManager();
        }
        return cmbKeySelectionManager;
    }

    private ComboBoxRenderer getCmbRenderer() {
        if (cmbRenderer == null) {
            cmbRenderer = new ComboBoxRenderer();
        }
        return cmbRenderer;
    }

    private JButton getBtnReturn() {
        if (btnReturn == null) {
            btnReturn = new JButton();
            btnReturn.setToolTipText(Messages.getString("circulation.cancelReservation")); //$NON-NLS-1$
            btnReturn.setIcon(new ImageIcon(getClass().getResource("/circ-images/minus16.png"))); //$NON-NLS-1$
            btnReturn.setFocusable(false);
            btnReturn.setPreferredSize(new java.awt.Dimension(28, 28));
            btnReturn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int[] rows = getTblReservation().getSelectedRows();
                    System.out.println("");
                    if (rows.length != 0) {
                        for (int i = 0; i < rows.length; i++) {
                            // add the book to the temporary list
                            Cirkulacija.getApp().getRecordsManager().cancelReservation(getTableModel().getReservation(rows[i]));
                            Cirkulacija.getApp().getUserManager().deleteReservation(getTableModel().getReservation(rows[i]));
                        }
                        getTableModel().removeRows(rows);
                        handleKeyTyped();
                        pinRequired = true;
                    }
                }
            });
        }
        return btnReturn;
    }

    public JLabel getLinvNumber() {
        if (lInvNumber == null) {
            lInvNumber = new JLabel();
            lInvNumber.setForeground(Color.blue);
            lInvNumber.setText(""); //$NON-NLS-1$
        }
        return lInvNumber;
    }

    public JLabel getLtitle() {
        if (lTitle == null) {
            lTitle = new JLabel();
            lTitle.setForeground(Color.blue);
            lTitle.setText(""); //$NON-NLS-1$
        }
        return lTitle;
    }

    public JLabel getLauthor() {
        if (lAuthor == null) {
            lAuthor = new JLabel();
            lAuthor.setForeground(Color.blue);
            lAuthor.setText(""); //$NON-NLS-1$
        }
        return lAuthor;
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

    public void loadUser(String dupno, String blocked, java.util.List<ReservationOnProfile> reservations, boolean warnings) {
        getLDuplicate().setText(dupno);
        getLBlockCard().setText(blocked);

        // get only reservations that are in the queue (status != PICKED_UP)
        List<ReservationOnProfile> activeReservations = new ArrayList<>();
        for (ReservationOnProfile r : reservations) {
            if (!r.getReservationStatus().equals(ReservationStatus.PICKED_UP)) {
                activeReservations.add(r);
            }
        }

//        this.reservations = new ArrayList<>();
//        this.reservations = activeReservations;

        // add reservations to the table
        getTableModel().setData(activeReservations);

        pinRequired = false;
    }


    public void setTableModel(List<ReservationOnProfile> reservations) {
        getTableModel().setData(reservations);
    }

    public void clear() {
        getLinvNumber().setText(""); //$NON-NLS-1$
        getLtitle().setText(""); //$NON-NLS-1$
        getLauthor().setText(""); //$NON-NLS-1$
        locations.clear();
        getCmbGroups().removeAllItems();
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
