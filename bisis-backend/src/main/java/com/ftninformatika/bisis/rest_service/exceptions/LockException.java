package com.ftninformatika.bisis.rest_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Petar on 8/11/2017.
 */
@ResponseStatus(HttpStatus.LOCKED)
public class LockException extends RuntimeException {
    public LockException(String recId){ super("Zakljucan je zapis sa ID-om: " + recId);}
}
