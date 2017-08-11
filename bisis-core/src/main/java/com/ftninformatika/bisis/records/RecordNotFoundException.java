package com.ftninformatika.bisis.records;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
  public RecordNotFoundException(String recordId) {
    super("Nije pronadjen zapis sa ID: " + recordId);
  }
}
