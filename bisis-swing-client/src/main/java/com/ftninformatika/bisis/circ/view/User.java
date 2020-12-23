package com.ftninformatika.bisis.circ.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.barcode.epl2.IPrinter;
import com.ftninformatika.bisis.barcode.epl2.Label;
import com.ftninformatika.bisis.barcode.epl2.Printer2;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.validator.Validate;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.string.LatCyrUtils;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.swing.JRViewer;
import org.apache.log4j.Logger;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class User extends JPanel {

    private JPanel pSouth = null;
    private JButton btnSave = null;
    private JButton btnCancel = null;
    private JButton btnArchive = null;
    private JTabbedPane tpMain = null;
    private JPanel pMain0 = null;
    private UserData userData = null;
    private Membership mmbrship = null;
    private Reservation pReservations = null;
    private Lending lending = null;
    private Picturebooks picturebooks = null;
    private JPanel pPrint = null;
    private Warnings pWarnings = null;
    private static Logger log = org.apache.log4j.Logger.getLogger(User.class);
    private boolean dirty = false;


    public User() {
        super();
        initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(700, 400);
        this.add(getPSouth(), BorderLayout.SOUTH);
        this.add(getTpMain(), BorderLayout.CENTER);
        Validate.fixUserLabels(getUser());
        fixTables();
    }

    private JPanel getPSouth() {
        if (pSouth == null) {
            FlowLayout flowLayout = new FlowLayout();
            flowLayout.setHgap(50);
            pSouth = new JPanel(flowLayout);
            //pSouth.add(Box.createHorizontalStrut(170), null);
            pSouth.add(getBtnSave(), null);
            pSouth.add(getBtnCancel(), null);
//			if (BisisApp.appConfig.getClientConfig().getBoolean("database-archive", "enabled")){
//				pSouth.add(Box.createHorizontalStrut(100), null);
//				pSouth.add(getBtnArchive(), null);
//			}else{
//				pSouth.add(Box.createHorizontalStrut(170), null);
//			}

        }
        return pSouth;
    }

    private User getUser() {
        return this;
    }

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    private JButton getBtnSave() {
        if (btnSave == null) {
            btnSave = new JButton();
            btnSave.setText(Messages.getString("circulation.save")); //$NON-NLS-1$
            btnSave.setFocusable(false);
            btnSave.setIcon(new ImageIcon(getClass().getResource("/circ-images/Check16.png"))); //$NON-NLS-1$
            btnSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String result = Validate.validateUser(getUser());
                    if (result != null) {
                        JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame(), result, Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
                                new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                    } else {
                        if (BisisApp.appConfig.getClientConfig().getPincodeEnabled().equals("yes") && pinRequired()) {
                            JPasswordField pwd = new JPasswordField(10);
                            JOptionPane.showConfirmDialog(Cirkulacija.getApp().getMainFrame(), pwd, Messages.getString("circulation.enter_pin"), JOptionPane.OK_CANCEL_OPTION);
                            if (!getUserData().getPinCode().equals(new String(pwd.getPassword()))) {
                                JOptionPane.showMessageDialog(null, Messages.getString("circulation.pinerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                                return;
                            }
                        }
                        String message = Cirkulacija.getApp().getUserManager().saveUser(getUser());
                        if (message.equals("ok")) { //$NON-NLS-1$
                            JOptionPane.showMessageDialog(null, Messages.getString("circulation.saved"), Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/hand32.png"))); //$NON-NLS-1$
                            if (!getTpMain().isEnabledAt(3))
                                getTpMain().setEnabledAt(3, true);
                            dirty = false;

                            getReservations();

                        } else {
                            JOptionPane.showMessageDialog(null, Messages.getString("circulation.saveerror") + " " + message, Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        }
                    }
                }
            });
        }
        return btnSave;
    }

    private void getReservations() {
        List<ReservationDTO> reservations = Cirkulacija.getApp().getUserManager().getReservationsForReturnedBooks("");

        // if there is at least one reservation for returned book(s), display dialog with info
        if (reservations != null && reservations.size() > 0) {
            ReservationsDialog dialog = new ReservationsDialog();
            dialog.setVisible(true);
        }
    }


    public void save() {
        getBtnSave().doClick();
    }

    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setText(Messages.getString("circulation.cancel")); //$NON-NLS-1$
            btnCancel.setFocusable(false);
            btnCancel.setIcon(new ImageIcon(getClass().getResource("/circ-images/Delete16.png"))); //$NON-NLS-1$
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (dirty) {
                        String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
                        int op = JOptionPane.showOptionDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.savewarning"), Messages.getString("circulation.warning"), JOptionPane.OK_CANCEL_OPTION,  //$NON-NLS-1$ //$NON-NLS-2$
                                JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[0]); //$NON-NLS-1$
                        if (op == JOptionPane.YES_OPTION) {
                            getBtnSave().doClick();
                        } else {
                            dirty = false;
                            Cirkulacija.getApp().getRecordsManager().getListOfBooksToBeReserved().clear();
                            Cirkulacija.getApp().getUserManager().getBooksToReserve().clear();

                        }
                    }
                    if (!dirty) {
                        boolean released = Cirkulacija.getApp().getUserManager().releaseUser();
                        if (released) {
                            Cirkulacija.getApp().getMainFrame().previousPanel();
                            getTpMain().remove(getPMain4());
                            getTpMain().remove(getPMain5());
                            if (!getTpMain().isEnabledAt(3))
                                getTpMain().setEnabledAt(3, true);
                            //clearPanels();
                            if (picturebooks != null) {
                                getPicturebooks().setPinRequired(false);
                            }
                            getLending().setPinRequired(false);
                        } else {
                            JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.releaseerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png")));
                        }
                    }
                }
            });
        }
        return btnCancel;
    }

    public void cancel() {
        getBtnCancel().doClick();
    }

    private JButton getBtnArchive() {
        if (btnArchive == null) {
            btnArchive = new JButton();
            btnArchive.setToolTipText(Messages.getString("circulation.archive")); //$NON-NLS-1$
            btnArchive.setFocusable(false);
            btnArchive.setIcon(new ImageIcon(getClass().getResource("/circ-images/remove_user16_button.png"))); //$NON-NLS-1$
            btnArchive.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String[] options = {Messages.getString("circulation.yes"), Messages.getString("circulation.no")};  //$NON-NLS-1$ //$NON-NLS-2$
                    int op = JOptionPane.showOptionDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.archivewarning"), Messages.getString("circulation.warning"), JOptionPane.OK_CANCEL_OPTION,  //$NON-NLS-1$ //$NON-NLS-2$
                            JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/circ-images/critical32.png")), options, options[1]); //$NON-NLS-1$
                    if (op == JOptionPane.YES_OPTION) {
                        if (getLending().existLending()) {
                            JOptionPane.showMessageDialog(Cirkulacija.getApp().getMainFrame(), Messages.getString("circulation.archiveerror"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$
                                    new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                        } else {
                            String message = Cirkulacija.getApp().getUserManager().archiveUser(getUser());
                            if (message.equals("ok")) { //$NON-NLS-1$
                                JOptionPane.showMessageDialog(null, Messages.getString("circulation.archived"), Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/hand32.png"))); //$NON-NLS-1$
                                Cirkulacija.getApp().getMainFrame().previousPanel();
                                getTpMain().remove(getPMain4());
                                //getTpMain().remove(getPMain5());
                                if (!getTpMain().isEnabledAt(3))
                                    getTpMain().setEnabledAt(3, true);
                            } else {
                                JOptionPane.showMessageDialog(null, Messages.getString("circulation.archiveerror2"), Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                                        new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                            }

                        }
                    }
                }
            });
        }
        return btnArchive;
    }

    public void archive() {
        getBtnArchive().doClick();
    }

    public UserData getUserData() {
        if (userData == null) {
            userData = new UserData(this);
        }
        return userData;
    }

    private JPanel getPMain0() {
        if (pMain0 == null) {
            pMain0 = getUserData().getPMain0();
        }
        return pMain0;
    }

    private JPanel getPMain1() {
        return getUserData().getPMain1();
    }

    public Membership getMmbrship() {
        if (mmbrship == null) {
            mmbrship = new Membership(this);
        }
        return mmbrship;
    }

    private JPanel getPMain2() {
        return getMmbrship().getPanel();
    }

    //todo
    public Reservation getReservationsPanel() {
        if (pReservations == null) {
            pReservations = new Reservation(this);
        }
        return pReservations;
    }

    private JPanel getPMain6() {
        return getReservationsPanel().getPanel();
    }

    public Lending getLending() {
        if (lending == null) {
            lending = new Lending(this);
        }
        return lending;
    }

    public Picturebooks getPicturebooks() {
        if (picturebooks == null) {
            picturebooks = new Picturebooks(this);
        }
        return picturebooks;
    }

    private JPanel getPMain3() {
        return getLending().getPanel();
    }

    private JTabbedPane getTpMain() {
        if (tpMain == null) {
            tpMain = new JTabbedPane();
            tpMain.addTab(Messages.getString("circulation.basicdata"), null, getPMain0(), null); //$NON-NLS-1$
            tpMain.addTab(Messages.getString("circulation.additionaldata"), null, getPMain1(), null); //$NON-NLS-1$
            tpMain.addTab(Messages.getString("circulation.membershipfee"), null, getPMain2(), null); //$NON-NLS-1$
            tpMain.addTab(Messages.getString("circulation.charging"), null, getPMain3(), null); //$NON-NLS-1$
            tpMain.addTab(Messages.getString("circulation.reservations"), null, getPMain6(), null); //$NON-NLS-1$
            tpMain.setSelectedIndex(0);
            tpMain.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (tpMain.getSelectedIndex() == 3) {
                        Cirkulacija.getApp().getMainFrame().setLendingsTabWindowSize();
                    } else {
                        Cirkulacija.getApp().getMainFrame().setDefaultWindowSize();
                    }
                }
            });
        }
        return tpMain;
    }

    private JPanel getPMain4() {
        if (pPrint == null) {
            pPrint = new JPanel();
            pPrint.setLayout(new BorderLayout());
        }
        return pPrint;
    }

    private Warnings getPMain5() {
        if (pWarnings == null) {
            pWarnings = new Warnings(this);
        }
        return pWarnings;
    }


//	private JPanel getMMbrCard(){
//		Map params = new HashMap(3);
//	    params.put("ime", userData.getLastName() +" "+ userData.getFirstName()); //$NON-NLS-1$ //$NON-NLS-2$
//	    params.put("broj", mmbrship.getUserID()); //$NON-NLS-1$
//	    try{
//		    JasperPrint jp = JasperFillManager.fillReport(
//		              LibrarianUser.class.getResource(
//		                "/cirkulacija/jasper/membercard.jasper").openStream(),  //$NON-NLS-1$
//		                params, new JREmptyDataSource());
//		    JRViewer jr = new JRViewer(jp);
//		    return jr;
//	    }catch (Exception e){
//	    	log.error(e);
//	    	return null;
//	    }
//	}

    private JPanel getRevers() {
        try {
            Map<String, Object> params = new HashMap<String, Object>(9);
            params.put("korisnik", lending.getUser()); //$NON-NLS-1$
            params.put("korisnik-adresa", userData.getAddressRevers()); //$NON-NLS-1$
            params.put("broj-indeksa", userData.getIndexNoRevers());
            params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
            params.put("biblioteka", Cirkulacija.getApp().getEnvironment().getReversLibraryName()); //$NON-NLS-1$
            params.put("adresa", Cirkulacija.getApp().getEnvironment().getReversLibraryAddress()); //$NON-NLS-1$
            params.put("bibliotekar", Cirkulacija.getApp().getLibrarian().getIme() + " " + Cirkulacija.getApp().getLibrarian().getPrezime()); //$NON-NLS-1$ //$NON-NLS-2$
            // budzotina zbog subotice (ako je vrednostu u ReversHeight = 1 stampace se inverntarni broj u zaglavlju reversa
            params.put("zaglavlje-broj", Cirkulacija.getApp().getEnvironment().getReversHeight());


            JasperPrint jp = JasperFillManager.fillReport(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("cirkulacija/jaspers/revers.jasper"),  //$NON-NLS-1$
                    params, new JRTableModelDataSource(lending.getReversTableModel()));
            JRViewer jr = new JRViewer(jp);
            return jr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return null;
        }
    }


    private JPanel getPinCard() {
        try {
            Map<String, Object> params = new HashMap<String, Object>(4);
            params.put("library", BisisApp.appConfig.getClientConfig().getPincodeLibrary());
            params.put("userid", mmbrship.getUserID()); //$NON-NLS-1$
            params.put("name", userData.getFirstName() + " " + userData.getLastName());//$NON-NLS-1$
            params.put("pincode", userData.getPinCode()); //$NON-NLS-1$
            params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());

            JasperPrint jp = JasperFillManager.fillReport(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("cirkulacija/jaspers/pincode.jasper"),  //$NON-NLS-1$
                    params, new JREmptyDataSource());
            JRViewer jr = new JRViewer(jp);
            return jr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            return null;
        }
    }

    public void printCard() {
        // getPMain4().removeAll();
        // getPMain4().add(getMMbrCard(), java.awt.BorderLayout.CENTER);
        // getTpMain().addTab(Messages.getString("circulation.librarycard"), null,
        // getPMain4(), null); //$NON-NLS-1$
        // getTpMain().setSelectedComponent(getPMain4());

        String port = BisisApp.appConfig.getClientConfig().getBarcodePort();
        int labelWidth = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersLabelWidth());
        int labelHeight = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersLabelHeight());
        int labelResolution = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersLabelResolution());
        String pageCode = BisisApp.appConfig.getClientConfig().getBarcodeUsersPageCode();
        int widebar = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersWidebar());
        int narrowbar = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersNarrowbar());
        int barwidth = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersBarwidth());
        int linelength = Integer.parseInt(BisisApp.appConfig.getClientConfig().getBarcodeUsersLinelength());

        // TODO:
        IPrinter printer = Printer2.getInstance();
        //Printer printer = Printer.getDefaultPrinter(port);
        Label label = new Label(labelWidth, labelHeight, labelResolution, widebar, narrowbar, barwidth, pageCode);
        String fname = userData.getFirstName();
        String lname = userData.getLastName();
        if (pageCode.equals("1251")) {
            fname = LatCyrUtils.toCyrillic(fname);
            lname = LatCyrUtils.toCyrillic(lname);
        } else {
            fname = LatCyrUtils.toLatin(fname);
            lname = LatCyrUtils.toLatin(lname);
        }
        if (lname.length() + fname.length() > linelength) {
            label.appendText(fname, 3);
            label.appendText(lname, 3);
        } else {
            label.appendText(fname + " " + lname, 3);
            label.appendBlankLine();
        }
        label.appendCode128("K" + mmbrship.getUserID());
        printer.print(label, pageCode);

    }

    public void printPin() {
        getPMain4().removeAll();
        getPMain4().add(getPinCard(), BorderLayout.CENTER);
        getTpMain().addTab(Messages.getString("circulation.pin"), null, getPMain4(), null); //$NON-NLS-1$
        getTpMain().setSelectedComponent(getPMain4());
    }

    public void printRevers() {
        getPMain4().removeAll();
        getPMain4().add(getRevers(), BorderLayout.CENTER);
        getTpMain().addTab(Messages.getString("circulation.lendingform"), null, getPMain4(), null); //$NON-NLS-1$
        getTpMain().setSelectedComponent(getPMain4());
    }

    public void showWarnings() {
        boolean tmp = dirty;
        getPMain5().setData(Cirkulacija.getApp().getUserManager().getWarnings());
        getTpMain().addTab(Messages.getString("circulation.reminders"), null, getPMain5(), null); //$NON-NLS-1$
        getTpMain().setSelectedComponent(getPMain5());
        dirty = tmp;
    }

    public void loadDefault() {
        Cirkulacija.getApp().getUserManager().initialiseUser(this);
        getUserData().loadDefault();
        getMmbrship().loadDefault();
        getLending().loadDefault();
        getTpMain().setSelectedComponent(getPMain0());
        getTpMain().setEnabledAt(3, false);
        dirty = false;
    }

    public void showData() {
        getTpMain().setSelectedComponent(getPMain0());
    }

    public void showMmbrship() {
        getTpMain().setSelectedComponent(getPMain2());
    }

    public void showLending() {
        getLending().getTfCtlgNo().requestFocusInWindow();
        getTpMain().setSelectedComponent(getPMain3());
    }

    //todo
    public void showReservations() {
        getReservationsPanel().getTfCtlgNo().requestFocusInWindow();
        getTpMain().setSelectedComponent(getPMain6());
    }

    public void showPicturebooks() {
        getPicturebooks().setData(Cirkulacija.getApp().getUserManager().getPicturebooks());
        getPMain4().removeAll();
        getPMain4().add(getPicturebooks().getPanel(), BorderLayout.CENTER);
        getTpMain().addTab(Messages.getString("circulation.picturebooks"), null, getPMain4(), null); //$NON-NLS-1$
        getTpMain().setSelectedComponent(getPMain4());
    }


    public void clearPanels() {
        getUserData().clear();
        getMmbrship().clear();
        getLending().clear();
    }

    private void fixTables() {
        getUserData().fixTable();
        getMmbrship().fixTable();
    }

    private boolean pinRequired() {
        if (picturebooks != null) {
            return getPicturebooks().pinRequired() || getLending().pinRequired();
        }
        return getLending().pinRequired();
    }


}
