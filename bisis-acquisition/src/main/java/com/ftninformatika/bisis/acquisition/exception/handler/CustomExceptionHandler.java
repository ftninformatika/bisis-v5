package com.ftninformatika.bisis.acquisition.exception.handler;

import com.ftninformatika.bisis.acquisition.exception.model.ServiceException;
import com.ftninformatika.bisis.acquisition.exception.model.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity handleServiceException(final ServiceException exception) {
        log.warn("Processing service exception: {}", exception.getMessage());

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFoundException(final UserNotFoundException exception) {
        log.error("Processing librarian not found exception: {}", exception.getMessage());

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAbstractException(final Exception exception) {
        log.error("Processing abstract exception: {}", exception.getMessage());
        exception.printStackTrace();

        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
