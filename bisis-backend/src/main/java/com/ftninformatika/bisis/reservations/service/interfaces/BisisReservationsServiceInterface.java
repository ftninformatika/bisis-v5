package com.ftninformatika.bisis.reservations.service.interfaces;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.dto.ReservationInQueueDTO;
import com.ftninformatika.bisis.opac.dto.ReservationDTO;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;

import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */
public interface BisisReservationsServiceInterface {
    List<ReservationDTO> getReservationsForReturnedBooks(List<String> returnedBooks, String library);

    ReservationDTO confirmReservation(String reservation_id, String record_id, String ctlgNo, String library);

    ReservationDTO getCurrentAssignedReservation(String userId, String ctlgNo);

    ItemAvailability finishReservationProcess(ItemAvailability ia, Member member);

    ReservationDTO getNextReservation(String userId, String ctlgNo, String library);

    HashMap<String, String> updateReservations(String library, HashMap<String, String> books, List<ReservationOnProfile> reservations, Member member);

    List<ReservationInQueueDTO> getReservationsByRecord(String library, String record_id);
}
