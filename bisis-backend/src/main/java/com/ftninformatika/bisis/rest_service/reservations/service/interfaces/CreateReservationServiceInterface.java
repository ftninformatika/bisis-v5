package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.Reservation;

/**
 * @author marijakovacevic
 */
public interface CreateReservationServiceInterface {
    Object reserveBook(String authToken, String library, String recordId, String coderId);

    Reservation createNewReservation(Member member, Record record, String coderId);

}
