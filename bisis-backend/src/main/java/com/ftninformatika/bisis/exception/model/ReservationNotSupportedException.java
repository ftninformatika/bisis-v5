package com.ftninformatika.bisis.exception.model;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class ReservationNotSupportedException extends RuntimeException {
    public ReservationNotSupportedException() {
        super("Библиотека не подржава ову функционалност");
    }
}

