package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.Reservation;

/**
 * @author marijakovacevic
 */
public interface CreateReservationServiceInterface {
    Object reserveBook(String memberNo, String library, String recordId, String coderId);

    Object checkIfReservationPossible(String library, String record_id, String coderId, Member member);
}
