package com.ftninformatika.bisis.rest_service.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> invalidRequest(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Invalid request");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ReservationNotAllowedException.class)
    public final ResponseEntity<Object> reservationNotAllowed(ReservationNotAllowedException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Сви примерци књиге су доступни у библиотеци. " +
                "Резервација нија могућа.");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ReservationsLimitExceededException.class)
    protected ResponseEntity<Object> reservationLimitExceeded(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("You have reached the reservation limit (3 books already reserved)");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UnknownMemberException.class)
    protected ResponseEntity<Object> unknownUserHandle(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        bodyOfResponse = Optional.ofNullable(bodyOfResponse).orElse("Unknown user!");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}
