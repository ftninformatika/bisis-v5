package com.ftninformatika.bisis.rest_service.exceptions;

public class ReservationsLimitExceededException extends RuntimeException {
    public ReservationsLimitExceededException() {
        super("Није могуће резервисати више од 3 књиге.");
    }

    public ReservationsLimitExceededException(String message) {
        super(message);
    }
}
