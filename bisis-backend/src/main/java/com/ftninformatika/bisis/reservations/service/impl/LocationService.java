package com.ftninformatika.bisis.reservations.service.impl;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.LocationRepository;
import com.ftninformatika.bisis.core.repositories.SubLocationRepository;
import com.ftninformatika.bisis.library_configuration.EnumLocationLevel;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.service.interfaces.LocationServiceInterface;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author marijakovacevic
 */

@Service
public class LocationService implements LocationServiceInterface {

    @Autowired
    SubLocationRepository sublocationRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    public boolean isSameLocation(String coderId, String library, Primerak p) {
        boolean isSubLocation = isLibrarySubLocation(library);
        boolean sameLocation = false;

        if (isSubLocation) {
            if (p.getSigPodlokacija().equals(coderId)) {  // ako se desi da pukne prilikom Save, kad se kreira rezervacija iz Bisisa, to je zato sto neki primerak nema definisanu podlokaciju
                sameLocation = true;
            }
        } else {
            String coder_id = getCoderIdForPrimerakOnLocation(p);
            if (coder_id.equals(coderId)) {
                sameLocation = true;
            }
        }
        return sameLocation;
    }


    public String getLibraryBranchName(String library, String coderId) {
        boolean isSubLocation = isLibrarySubLocation(library);
        String locationDescription = "";

        if (isSubLocation) {
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
        if (!locationDescription.equals("")) {
            locationDescription = LatCyrUtils.toCyrillic(locationDescription);
        }
        return locationDescription;
    }

    public String getLocationCodeByPrimerak(Record record, String ctlgNo, String library) {
        boolean isSubLocation = isLibrarySubLocation(library);
        String coder_id = "";

        for (Primerak p : record.getPrimerci()) {
            if (p.getInvBroj().equals(ctlgNo)) {
                if (isSubLocation) {
                    coder_id = p.getSigPodlokacija();
                } else {
                    coder_id = getCoderIdForPrimerakOnLocation(p);
                }
            }
        }
        return coder_id;
    }

    private String getCoderIdForPrimerakOnLocation(Primerak p) {
        if (p.getOdeljenje() == null || p.getOdeljenje().equals("")) {
            return p.getInvBroj().substring(0, 2);
        } else {
            return p.getOdeljenje();
        }
    }

    private boolean isLibrarySubLocation(String library) {
        boolean isSubLocation = false;
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        if (config.getLocationLevel() != null && config.getLocationLevel() == EnumLocationLevel.SUB_LOCATION.getLevel()) {
            isSubLocation = true;
        }
        return isSubLocation;
    }
}
