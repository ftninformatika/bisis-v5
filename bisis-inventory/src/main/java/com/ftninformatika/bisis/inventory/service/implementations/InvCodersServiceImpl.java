package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.coders.Coder;
import com.ftninformatika.bisis.inventory.EnumInvLocation;
import com.ftninformatika.bisis.inventory.service.interfaces.InvCodersService;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.LocationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.SublocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvCodersServiceImpl implements InvCodersService {

    private LibraryConfigurationRepository libraryConfigurationRepository;
    private LocationRepository locationRepository;
    private SublocationRepository sublocationRepository;


    @Autowired
    public InvCodersServiceImpl(LibraryConfigurationRepository libraryConfigurationRepository, LocationRepository locationRepository,
                                SublocationRepository sublocationRepository) {
        this.libraryConfigurationRepository = libraryConfigurationRepository;
        this.locationRepository = locationRepository;
        this.sublocationRepository = sublocationRepository;
    }

    @Override
    public List<?> getInvLocationsByLib(String library) {
        if (library == null) {
            return null;
        }
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        String invLocation = EnumInvLocation.LOCATION.toString();
        if (config.getInvLocation() != null) {
            invLocation = config.getInvLocation();
        }

        List results = null;
        if (EnumInvLocation.LOCATION.toString().equals(invLocation)) {
            results = locationRepository.getCoders(library);
        } else if (EnumInvLocation.SUB_LOCATION.toString().equals(invLocation)) {
            results = sublocationRepository.getCoders(library);
        }

        return results;
    }


    public EnumInvLocation getEnumInvLocation(String library) {
        if (library == null) {
            return null;
        }
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        if (config.getInvLocation() == null || EnumInvLocation.LOCATION.toString().equals(config.getInvLocation())) {
            return EnumInvLocation.LOCATION;
        } else {
            return EnumInvLocation.SUB_LOCATION;
        }
    }
}
