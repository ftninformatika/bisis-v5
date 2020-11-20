package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

/**
 * @author marijakovacevic
 */
public interface CreateReservationServiceInterface {
    Object reserveBook(String authToken, String library, String recordId, String coderId);
}
