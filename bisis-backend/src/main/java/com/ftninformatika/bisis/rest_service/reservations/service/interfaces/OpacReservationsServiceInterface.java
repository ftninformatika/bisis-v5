package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;


import com.ftninformatika.bisis.opac2.dto.ReservationDTO;

import java.util.List;

/**
 * @author marijakovacevic
 */
public interface OpacReservationsServiceInterface {
    List<ReservationDTO> getReservationsByUser(String library, String memberNo);

    Boolean deleteReservation(String memberNo, String reservationId);

    Boolean isReservationsQueueEmpty(String ctlgNo);
}
