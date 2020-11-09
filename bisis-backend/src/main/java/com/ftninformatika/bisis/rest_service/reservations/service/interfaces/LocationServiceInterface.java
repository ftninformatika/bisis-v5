package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;

/**
 * @author marijakovacevic
 */
public interface LocationServiceInterface {
    boolean isSameLocation(String coderId, String library, Primerak p);

    String getLibraryBranchName(String library, String coderId);

    String getLocationCodeByPrimerak(Record record, String ctlgNo, String library);


}
