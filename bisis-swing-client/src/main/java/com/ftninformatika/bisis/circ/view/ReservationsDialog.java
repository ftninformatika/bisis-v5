package com.ftninformatika.bisis.circ.view;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.actions.PrintReservationAction;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * @author marijakovacevic
 */
public class ReservationsDialog extends JDialog {
    private JPanel jContentPane = null;
    private JScrollPane jScrollPane = null;
    private JButton okButton = null;

    private List<ReservationDTO> reservationDTOList;

    public ReservationsDialog(List<ReservationDTO> reservationDTOList) {
        super(BisisApp.getMainFrame(), Messages.getString("REPORT_CHOSE_REPORT"), true);
        this.reservationDTOList = reservationDTOList;
        this.initialize();
    }

    private void initialize() {
        this.setResizable(false);
        this.setSize(600, 400);
        this.setTitle(Messages.getString("circulations.reservations"));

        this.jScrollPane = getJScrollPane();

        JPanel pCenter = new JPanel();
        add(pCenter, BorderLayout.CENTER);
        pCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        pCenter.add(this.jScrollPane, BorderLayout.CENTER);

        JPanel pSouth = new JPanel();
        add(pSouth, BorderLayout.SOUTH);
        pSouth.setLayout(new FlowLayout());
        okButton = getOkButton();
        pSouth.add(okButton);

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
            jContentPane.setLayout(new GridBagLayout());

            GridBagConstraints c = getGridBagConstraints();

            for (ReservationDTO r : this.reservationDTOList) {
                JLabel bookTitle = new JLabel(r.getTitle());
                bookTitle.setPreferredSize(new Dimension(300, 15));
                jContentPane.add(bookTitle, moveGridBagLeft(c));

                JButton btnPrint = new JButton(Messages.getString("PRINT"));
                btnPrint.setIcon(new ImageIcon(getClass().getResource("/icons/print_16.png")));
                btnPrint.addActionListener(new PrintReservationAction());
                jContentPane.add(btnPrint, moveGridBagCenter(c));
            }
        }
        return jContentPane;
    }

    private GridBagConstraints moveGridBagCenter(GridBagConstraints c) {
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        return c;
    }

    private GridBagConstraints moveGridBagLeft(GridBagConstraints c) {
        c.gridx = 0;
        c.anchor = GridBagConstraints.WEST;
        return c;
    }

    private GridBagConstraints getGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(20, 20, 20, 0);
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.weightx = 1.0f;
        c.weighty = 1.0f;
        return c;
    }


    private void handleOk() {
        dispose();
    }

    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setPreferredSize(new Dimension(600, 330));
            jScrollPane.getViewport().setView(getJContentPane());
            jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
        return jScrollPane;
    }


}
