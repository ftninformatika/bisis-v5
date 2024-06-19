package com.ftninformatika.bisis.exception.model;


public class AudioBookDoesNotExistInRecordException extends RuntimeException {

    public AudioBookDoesNotExistInRecordException() {
        super("У траженом запису не постоји аудиокњига.");;
    }

    public AudioBookDoesNotExistInRecordException(String msg) {
        super(msg);;
    }

}
