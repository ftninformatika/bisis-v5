package com.ftninformatika.bisis.reservations.service.interfaces;


import com.ftninformatika.bisis.opac.dto.ReservationDTO;

import java.util.List;

/**
 * @author marijakovacevic
 */
public interface OpacReservationsServiceInterface {
    List<ReservationDTO> getReservationsByUser(String library, String memberNo);

    Boolean deleteReservation(String memberNo, String reservationId);

    Boolean isReservationPresentOnLocation(String library, String ctlgNo);
}
