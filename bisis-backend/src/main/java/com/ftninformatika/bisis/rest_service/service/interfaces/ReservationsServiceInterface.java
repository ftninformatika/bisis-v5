package com.ftninformatika.bisis.rest_service.service.interfaces;


public interface ReservationsServiceInterface {
    Object reserveBook(String authToken, String library, String recordId, String coderId);
}
