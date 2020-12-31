package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.opac2.dto.ReservationDTO;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;

import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */
public interface BisisReservationsServiceInterface {
    List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library);

    ReservationDTO confirmReservation(String reservation_id, String record_id, String ctlgNo);

    ReservationDTO getCurrentAssignedReservation(String userId, String ctlgNo);

    ItemAvailability finishReservationProcess(ItemAvailability ia, Member member);

    ReservationDTO getNextReservation(String userId, String ctlgNo, String library);

    List<Object> updateReservations(HashMap<String, String> books, List<ReservationOnProfile> reservations, Member member);

}
