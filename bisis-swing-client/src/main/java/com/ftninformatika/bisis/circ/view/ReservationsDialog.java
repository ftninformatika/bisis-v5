package com.ftninformatika.bisis.circ.view;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.WindowUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;


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
        this.setSize(900, 400);
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
                    "[][grow][grow][grow]30[][]", "[][]30"));

            createHeader();

            for (ReservationDTO r : reservationsForPrint) {
                createRow(r);
            }
        }
        return jContentPane;
    }

    private void createRow(ReservationDTO r) {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        jContentPane.add(separator, "growx, spanx, wrap");

        JLabel bookInvBr = new JLabel(r.getCtlgNo());
        jContentPane.add(bookInvBr, "width 20:30:null, growx, growy");

        JLabel bookTitle = new JLabel(r.getTitle());
        jContentPane.add(bookTitle, "width 20:80:null, growx, growy");

        JLabel bookAuthor = new JLabel(r.getAuthors().get(0));
        jContentPane.add(bookAuthor, "width 20:60:null,	growx, growy");

        JLabel reservedFor = new JLabel(r.getUserId() + ", " + r.getMemberFirstName() + " " + r.getMemberLastName());
        jContentPane.add(reservedFor, "width 20:150:null, growx, growy");

        JButton btnPrint = getBtnPrint(String.valueOf(reservationsForPrint.indexOf(r)));
        jContentPane.add(btnPrint);

        JButton btnGetNext = getBtnNext(String.valueOf(reservationsForPrint.indexOf(r)));
        jContentPane.add(btnGetNext, "wrap");
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
    }

    private JButton getBtnPrint(String reservationIdx) {
        JButton btnPrint = new JButton(Messages.getString("PRINT"));
        btnPrint.setIcon(new ImageIcon(getClass().getResource("/icons/print_16.png")));
        btnPrint.setActionCommand(reservationIdx);
        btnPrint.setFocusable(false);
        btnPrint.setToolTipText(Messages.getString("PRINT")); //$NON-NLS-1$
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int idx = Integer.parseInt(event.getActionCommand());
                ReservationDTO reservation = reservationsForPrint.get(idx);
                try {
                    if (reservation.getReservationStatus().equals(ReservationStatus.WAITING_IN_QUEUE)) {
                        Cirkulacija.getApp().getUserManager().confirmReservationAndAssignBook(reservationsForPrint.get(idx));
                    }
                    // todo prikazati formu sa info o rezervaciji za stampanje
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
                // todo dobaviti sledeceg
            }
        });
        return btnNext;
    }


    private void handleOk() {
        dispose();
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setPreferredSize(new Dimension(900, 330));
            jScrollPane.getViewport().setView(getJContentPane());
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
        return jScrollPane;
    }
}
