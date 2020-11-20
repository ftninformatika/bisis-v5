package com.ftninformatika.bisis.rest_service.exceptions;



public class ReservationNotAllowedException extends RuntimeException {
    public ReservationNotAllowedException() {
        super();
    }

    public ReservationNotAllowedException(String message) {
        super(message);
    }
}
