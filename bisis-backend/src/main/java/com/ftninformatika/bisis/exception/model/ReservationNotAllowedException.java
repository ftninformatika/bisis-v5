package com.ftninformatika.bisis.exception.model;



public class ReservationNotAllowedException extends RuntimeException {
    public ReservationNotAllowedException() {
        super();
    }

    public ReservationNotAllowedException(String message) {
        super(message);
    }
}
