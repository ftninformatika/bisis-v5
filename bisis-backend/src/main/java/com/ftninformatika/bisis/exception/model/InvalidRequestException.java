package com.ftninformatika.bisis.exception.model;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Грешка при покушају резервисања књиге.");
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
