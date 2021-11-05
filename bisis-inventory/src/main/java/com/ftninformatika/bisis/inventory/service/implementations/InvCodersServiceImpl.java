package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.inventory.EnumInvLocation;
import com.ftninformatika.bisis.inventory.service.interfaces.InvCodersService;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.core.repositories.LocationRepository;
import com.ftninformatika.bisis.core.repositories.SubLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvCodersServiceImpl implements InvCodersService {

    private LibraryConfigurationRepository libraryConfigurationRepository;
    private LocationRepository locationRepository;
    private SubLocationRepository sublocationRepository;


    @Autowired
    public InvCodersServiceImpl(LibraryConfigurationRepository libraryConfigurationRepository, LocationRepository locationRepository,
                                SubLocationRepository sublocationRepository) {
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
        Integer locationLevel = EnumInvLocation.LOCATION.getLevel();
        if (config.getLocationLevel() != null) {
            locationLevel = config.getLocationLevel();
        }

        List results = null;
        if (EnumInvLocation.LOCATION.getLevel() == locationLevel) {
            results = locationRepository.getCoders(library);
        } else if (EnumInvLocation.SUB_LOCATION.getLevel() == locationLevel) {
            results = sublocationRepository.getCoders(library);
        }

        return results;
    }


    public EnumInvLocation getEnumInvLocation(String library) {
        if (library == null) {
            return null;
        }
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        if (config.getLocationLevel() == null || EnumInvLocation.LOCATION.getLevel() == config.getLocationLevel()) {
            return EnumInvLocation.LOCATION;
        } else {
            return EnumInvLocation.SUB_LOCATION;
        }
    }
}
