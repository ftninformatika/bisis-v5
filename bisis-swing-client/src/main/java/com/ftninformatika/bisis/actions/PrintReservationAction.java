package com.ftninformatika.bisis.actions;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

/**
 * @author marijakovacevic
 */
public class PrintReservationAction implements ActionListener {

    public void actionPerformed(ActionEvent event) {
        List<ReservationDTO> allReservations = Cirkulacija.getApp().getUserManager().getReservationsForPrint();
        int idx = Integer.parseInt(event.getActionCommand());
        try {
            Cirkulacija.getApp().getUserManager().confirmReservationAndAssignBook(allReservations.get(idx));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
