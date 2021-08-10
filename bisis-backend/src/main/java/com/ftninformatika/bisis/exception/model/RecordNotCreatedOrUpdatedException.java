package com.ftninformatika.bisis.exception.model;

public class RecordNotCreatedOrUpdatedException extends Exception {
    public RecordNotCreatedOrUpdatedException(String _id) {
        super("Record: " + _id + " failed to create/update!");
    }
}
