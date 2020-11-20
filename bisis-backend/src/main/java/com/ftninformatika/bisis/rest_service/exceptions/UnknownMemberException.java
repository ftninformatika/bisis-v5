package com.ftninformatika.bisis.rest_service.exceptions;

public class UnknownMemberException extends RuntimeException {
    public UnknownMemberException() {
        super("Корисник не постоји у систему.");
    }

    public UnknownMemberException(String message) {
        super(message);
    }
}
