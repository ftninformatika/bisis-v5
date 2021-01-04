package com.ftninformatika.bisis.circ.view;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author marijakovacevic
 */
public class ReservationsDialog extends JDialog {
    private JPanel jContentPane = null;
    private JScrollPane jScrollPane = null;
    private JButton okButton = null;
    private List<ReservationDTO> reservationsForPrint;

    public ReservationsDialog() {
        super(BisisApp.getMainFrame(), Messages.getString("circulation.reservations"), true);
        this.reservationsForPrint = Cirkulacija.getApp().getUserManager().getReservationsForPrint();
        this.initialize();
    }

    private void initialize() {
        this.setSize(600, 270);
        setLayout(new BorderLayout());

        this.jScrollPane = getJScrollPane();

        JPanel pSouth = new JPanel();
        pSouth.setLayout(new FlowLayout());
        okButton = getOkButton();
        pSouth.add(okButton);

        add(this.jScrollPane, BorderLayout.NORTH);
        add(pSouth, BorderLayout.SOUTH);

        WindowUtils.centerOnScreen(this);
    }


    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setSize(new java.awt.Dimension(88, 26));
            okButton.setText(Messages.getString("CLOSE"));
//            okButton.setIcon(new ImageIcon(getClass().getResource(
//                    "/icons/ok.gif")));
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleOk();
                }
            });
        }
        return okButton;
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new MigLayout("insets 10 10 10 10",
                    "[][grow][grow][grow]", "[][]10"));

            createHeader();

            int i = 0;          // a counter that helps display two buttons in one field
            for (ReservationDTO r : reservationsForPrint) {
                i = createRow(r, i);
            }
        }
        return jContentPane;
    }

    private int createRow(ReservationDTO r, int i) {
        i = i + 3;
        JLabel bookInvBr = new JLabel(r.getCtlgNo());
        jContentPane.add(bookInvBr, "width 20:30:null, growx, growy");

        JLabel bookTitle = new JLabel(r.getTitle());
        jContentPane.add(bookTitle, "width 20:80:null, growx, growy");

        JLabel bookAuthor = new JLabel(r.getAuthors().get(0));
        jContentPane.add(bookAuthor, "width 20:60:null,	growx, growy");

        JLabel reservedFor = new JLabel(r.getUserId() + ", " + r.getMemberFirstName() + " " + r.getMemberLastName());
        jContentPane.add(reservedFor, "width 20:150:null, growx, growy, wrap");

        JButton btnPrint = getBtnPrint(String.valueOf(reservationsForPrint.indexOf(r)));
        jContentPane.add(btnPrint, "cell 3 " + i + ", span 4 1, align right");

        JButton btnGetNext = getBtnNext(String.valueOf(reservationsForPrint.indexOf(r)));
        jContentPane.add(btnGetNext, "cell 3 " + i + ", align right, wrap");

        addHorizontalSeparator();

        return i;
    }

    private void createHeader() {
        JLabel headerInvBr = new JLabel(Messages.getString("circulation.acquisitionnumber"));
        JLabel headerTitle = new JLabel(Messages.getString("circulation.pubtitle"));
        JLabel headerAuthor = new JLabel(Messages.getString("circulation.author"));
        JLabel headerReservedFor = new JLabel(Messages.getString("circulation.reservedFor2"));

        jContentPane.add(headerInvBr);
        jContentPane.add(headerTitle);
        jContentPane.add(headerAuthor);
        jContentPane.add(headerReservedFor, "wrap");
        addHorizontalSeparator();
    }

    private void addHorizontalSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        jContentPane.add(separator, "growx, spanx, wrap");
    }


    private JButton getBtnPrint(String reservationIdx) {
        JButton btnPrint = new JButton(Messages.getString("circulation.sendEmail"));
        btnPrint.setIcon(new ImageIcon(getClass().getResource("/icons/print_16.png")));
        btnPrint.setActionCommand(reservationIdx);
        btnPrint.setFocusable(false);
        btnPrint.setToolTipText(Messages.getString("circulation.sendEmail")); //$NON-NLS-1$
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int idx = Integer.parseInt(event.getActionCommand());
                ReservationDTO reservation = reservationsForPrint.get(idx);
                try {
                    if (reservation.getReservationStatus().equals(ReservationStatus.WAITING_IN_QUEUE)) {
                        reservation = Cirkulacija.getApp().getUserManager().confirmReservationAndAssignBook(reservationsForPrint.get(idx));
                        reservationsForPrint.set(idx, reservation);
                    }
                    PrintReservationDialog p = new PrintReservationDialog();
                    // todo ako je prazno tj. null nije poslat mejl
                    p.setJasper(getReservationForPrint(reservation));
                    p.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return btnPrint;
    }

    private JButton getBtnNext(String reservationIdx) {
        JButton btnNext = new JButton(Messages.getString("circulation.next"));
        btnNext.setIcon(new ImageIcon(getClass().getResource("/icons/next.gif")));
        btnNext.setActionCommand(reservationIdx);
        btnNext.setFocusable(false);
        btnNext.setToolTipText(Messages.getString("circulation.next")); //$NON-NLS-1$
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int idx = Integer.parseInt(event.getActionCommand());
                ReservationDTO reservation = reservationsForPrint.get(idx);
                ReservationDTO nextReservation = Cirkulacija.getApp().getUserManager().getNextReservation(reservation.getUserId(), reservation.getCtlgNo());
                if (nextReservation != null) {
                    reservationsForPrint.set(idx, nextReservation);
                } else {
                    JOptionPane.showMessageDialog(null, Messages.getString("circulation.noMoreReservations"),
                            Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE);
                    reservationsForPrint.remove(idx);
                }
                if (reservationsForPrint.size() == 0) {
                    handleOk();
                } else {
                    updateContentPane();
                }
            }
        });
        return btnNext;
    }

    private void updateContentPane() {
        this.jScrollPane.remove(jContentPane);
        this.remove(jScrollPane);
        this.jContentPane = null;
        this.jScrollPane = null;
        this.jScrollPane = getJScrollPane();
        this.add(jScrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private JasperPrint getReservationForPrint(ReservationDTO reservation) {
        try {
            Map<String, Object> params = new HashMap<String, Object>(9);
            params.put("biblioteka", Cirkulacija.getApp().getEnvironment().getReversLibraryName()); //$NON-NLS-1$
            params.put("korisnik", reservation.getUserId()); //$NON-NLS-1$
            params.put("bibliotekar", Cirkulacija.getApp().getLibrarian().getIme() + " " + Cirkulacija.getApp().getLibrarian().getPrezime()); //$NON-NLS-1$ //$NON-NLS-2$
            params.put("adresa", Cirkulacija.getApp().getEnvironment().getReversLibraryAddress()); //$NON-NLS-1$
            params.put("naslov", reservation.getTitle()); //$NON-NLS-1$
            params.put("invBroj", reservation.getCtlgNo()); //$NON-NLS-1$
            params.put("rezervacijaVaziDo", reservation.getPickUpDeadline()); //$NON-NLS-1$
            params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());

            return JasperFillManager.fillReport(Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("cirkulacija/jaspers/reservation.jasper"),  //$NON-NLS-1$
                    params, new JREmptyDataSource());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void handleOk() {
        dispose();
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setPreferredSize(new Dimension(600, 200));
            jScrollPane.getViewport().setView(getJContentPane());
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
        return jScrollPane;
    }
}
