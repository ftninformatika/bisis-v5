package com.ftninformatika.bisis.rest_service.exceptions;

public class RecordNotCreatedOrUpdatedException extends Exception {
    public RecordNotCreatedOrUpdatedException(String _id) {
        super("Record: " + _id + " failed to create/update!");
    }
}
