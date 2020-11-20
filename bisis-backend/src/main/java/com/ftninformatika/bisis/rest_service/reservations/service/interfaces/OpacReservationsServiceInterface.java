package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;


import com.ftninformatika.bisis.opac2.dto.ReservationDTO;

import java.util.List;

/**
 * @author marijakovacevic
 */
public interface OpacReservationsServiceInterface {
    List<ReservationDTO> getReservationsByUser(String library, String authToken);

    Boolean deleteReservation(String authToken, String reservationId);
}
