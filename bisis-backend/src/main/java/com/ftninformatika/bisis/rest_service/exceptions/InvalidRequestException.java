package com.ftninformatika.bisis.rest_service.exceptions;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Грешка при покушају резервисања књиге.");
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
