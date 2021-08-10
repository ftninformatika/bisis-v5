package com.ftninformatika.bisis.reservations.service.interfaces;

import com.ftninformatika.bisis.circ.Member;

/**
 * @author marijakovacevic
 */
public interface CreateReservationServiceInterface {
    Object reserveBook(String memberNo, String library, String recordId, String coderId);

    Object checkIfReservationPossible(String library, String record_id, String coderId, Member member);
}
