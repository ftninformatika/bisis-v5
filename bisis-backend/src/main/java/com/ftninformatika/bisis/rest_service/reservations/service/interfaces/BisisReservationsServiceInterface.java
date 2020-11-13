package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.opac2.dto.ReservationDTO;

import java.util.List;

/**
 * @author marijakovacevic
 */
public interface BisisReservationsServiceInterface {
    List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library);

    boolean confirmReservation(String reservation_id, String record_id, String ctlgNo);
}
