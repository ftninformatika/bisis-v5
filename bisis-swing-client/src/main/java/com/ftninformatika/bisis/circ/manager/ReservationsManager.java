package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import org.apache.log4j.Logger;

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
        if (!reservationsToDelete.contains(reservation)) {
            log.info("deleteReservation - Brisanje rezervacije: " + reservation.get_id() + ", na lokaciji: " + reservation.getCoderId());
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
}
