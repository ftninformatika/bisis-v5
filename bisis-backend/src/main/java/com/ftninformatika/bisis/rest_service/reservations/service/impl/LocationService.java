package com.ftninformatika.bisis.rest_service.reservations.service.impl;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.util.constants.LocationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author marijakovacevic
 */

@Service
public class LocationService implements LocationServiceInterface {

    @Autowired
    SublocationRepository sublocationRepository;

    @Autowired
    LocationRepository locationRepository;

    public boolean isSameLocation(String coderId, String library, Primerak p) {
        boolean isBgb = isLibraryBgb(library, coderId);
        boolean sameLocation = false;

        if (isBgb) {                       // todo: i bmb (citanje iz konfiguracije biblioteke)
            if (p.getSigPodlokacija().equals(coderId)) {
                sameLocation = true;
            }
        } else {                                // todo ako odeljenje nije popunjeno
            if (p.getOdeljenje().equals(coderId)) {
                sameLocation = true;
            }
        }
        return sameLocation;
    }

    private boolean isLibraryBgb(String library, String coderId) {
        boolean isBgb = false;
        // todo smestiti negde u config da li iima jos neka
        if (library.equals(LocationConstants.BGB) || library.equals(LocationConstants.BMB) || library.equals(LocationConstants.BVAO)) {
            isBgb = true;
            Sublocation sublocation = sublocationRepository.getByCoder_Id(coderId, library);
        } else {
            Location location = locationRepository.getByCoder_Id(coderId, library);
        }
        return isBgb;
    }

    public String getLibraryBranchName(String library, String coderId) {
        String locationDescription = "";
        if (library.equals(LocationConstants.BGB) || library.equals(LocationConstants.BMB) || library.equals(LocationConstants.BVAO)) {
            Sublocation sublocation = sublocationRepository.getByCoder_Id(coderId, library);
            if (sublocation != null) {
                locationDescription = sublocation.getDescription();
            }
        } else {
            Location location = locationRepository.getByCoder_Id(coderId, library);
            if (location != null) {
                locationDescription = location.getDescription();
            }
        }
        return locationDescription;
    }

    public String getLocationCodeByPrimerak(Record record, String ctlgNo, String library) {
        String coder_id = "";

        for (Primerak p : record.getPrimerci()) {
            if (p.getInvBroj().equals(ctlgNo)) {
                if (library.equals(LocationConstants.BGB) || library.equals(LocationConstants.BMB) || library.equals(LocationConstants.BVAO)) {
                    coder_id = p.getSigPodlokacija();
                } else {
                    if (p.getOdeljenje() == null || p.getOdeljenje().equals("")) {
                        coder_id = p.getInvBroj().substring(0, 2);
                    } else {
                        coder_id = p.getOdeljenje();
                    }
                }
            }
        }
        return coder_id;
    }
}
