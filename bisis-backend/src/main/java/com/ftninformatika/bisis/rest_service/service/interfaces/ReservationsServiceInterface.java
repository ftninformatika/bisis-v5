package com.ftninformatika.bisis.rest_service.service.interfaces;


import com.ftninformatika.bisis.circ.dto.ConfirmReservationDTO;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;

import java.util.List;

public interface ReservationsServiceInterface {
    Object reserveBook(String authToken, String library, String recordId, String coderId);

    List<ReservationDTO> getReservationsByUser(String library, String authToken);

    Boolean deleteReservation(String authToken, String reservationId);

    List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library);

    boolean confirmReservation(ConfirmReservationDTO confirmReservationDTO);
}
