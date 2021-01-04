package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.view.RecordBean;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.location.dto.RecordCtlgNoDTO;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.constants.ReservationsConstants;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */
public class ReservationsManager {

    private static final Logger log = Logger.getLogger(ReservationsManager.class);

    private final HashMap<String, String> booksToReserve = new HashMap<>();
    private final List<ReservationOnProfile> reservationsToDelete = new ArrayList<>();


    public ReservationsManager() {
    }

    public void reserveBook(String record_id, String location) {
        if (!booksToReserve.containsKey(record_id)) {
            log.info("addBookForReservation - Rezervisanje knjige: " + record_id + ", na lokaciji: " + location);
            booksToReserve.put(record_id, location);
        }
    }

    public void deleteReservation(ReservationOnProfile reservation) {
        if (booksToReserve.containsKey(reservation.getRecord_id())) {
            log.info("deleteReservation - Brisanje privremene rezervacije, koja nije sacuvana u bazi: " + reservation.getRecord_id() +
                    ", na lokaciji: " + reservation.getCoderId());
            booksToReserve.remove(reservation.getRecord_id());
        } else if (!reservationsToDelete.contains(reservation)) {
            log.info("deleteReservation - Brisanje postojece rezervacije: " + reservation.get_id() + ", na lokaciji: " + reservation.getCoderId());
            reservationsToDelete.add(reservation);
        }
    }


    public HashMap<String, String> getBooksToReserve() {
        return booksToReserve;
    }

    public List<ReservationOnProfile> getReservationsToDelete() {
        return reservationsToDelete;
    }

    public void setBooksForReservations(MemberData memberData) {
        if (!booksToReserve.isEmpty()) {
            log.info("setBooksForReservations - postoje knjige za rezervisanje");
            memberData.setBooksToReserve(booksToReserve);
        }

        if (!reservationsToDelete.isEmpty()) {
            log.info("setBooksForReservations - postoje rezervacije za brisanje");
            memberData.setReservationsToDelete(reservationsToDelete);
        }

    }

    public void clearLists() {
        this.booksToReserve.clear();
        this.reservationsToDelete.clear();
    }

    public void displayFailedReservations(MemberData memberData) {
        if (memberData.getBooksToReserve() != null) {
            for (String record_id : memberData.getBooksToReserve().keySet()) {
                String reservationStatus = memberData.getBooksToReserve().get(record_id);
                if (reservationStatus.equals(ReservationsConstants.NORESERVATION)) {
                    String naslovKnjige = "";
                    try {
                        Record record = BisisApp.getRecordManager().getRecord(record_id);
                        if (record != null) {
                            RecordBean bean = new RecordBean(record);
                            naslovKnjige = bean.getNaslov();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        log.error("displayFailedReservations - greska prilikom dobavljanja zapisa");
                    }

                    log.info("displayFailedReservations - rezervacija nije uspesna jer ne postoji zaduzen primerak knjige: " + naslovKnjige);
                    JOptionPane.showMessageDialog(null,
                            MessageFormat.format(Messages.getString("circulation.reservationNotAllowed"), naslovKnjige),
                            Messages.getString("circulation.error"), JOptionPane.ERROR_MESSAGE, //$NON-NLS-1$ //$NON-NLS-2$
                            new ImageIcon(getClass().getResource("/circ-images/x32.png"))); //$NON-NLS-1$
                }
            }
        }
    }

    public String getLocationCodeByPrimerak(Record record, String ctlgno) {
        if (record == null){
            return "";
        }

        RecordCtlgNoDTO recordCtlgNoDTO = new RecordCtlgNoDTO(record, ctlgno);
        String location = "";
        try {
            location = BisisApp.bisisService.getLocationCodeByPrimerak(recordCtlgNoDTO).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }


    public String getLibraryBranchName(String coderId) {
        String libraryBranch = "";

        try {
            libraryBranch = BisisApp.bisisService.getLibraryBranchName(coderId).execute().body();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // if library branch is null => coderId is library branch name already
        return libraryBranch == null || libraryBranch.equals("") ? coderId : libraryBranch;
    }
}
