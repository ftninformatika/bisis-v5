package com.ftninformatika.bisis.inventory.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.EnumLocationLevel;
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
        Integer locationLevel = EnumLocationLevel.LOCATION.getLevel();
        if (config.getLocationLevel() != null) {
            locationLevel = config.getLocationLevel();
        }

        List results = null;
        if (EnumLocationLevel.LOCATION.getLevel() == locationLevel) {
            results = locationRepository.getCoders(library);
        } else if (EnumLocationLevel.SUB_LOCATION.getLevel() == locationLevel) {
            results = sublocationRepository.getCoders(library);
        }

        return results;
    }


    public EnumLocationLevel getEnumInvLocation(String library) {
        if (library == null) {
            return null;
        }
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(library);
        if (config.getLocationLevel() == null || EnumLocationLevel.LOCATION.getLevel() == config.getLocationLevel()) {
            return EnumLocationLevel.LOCATION;
        } else {
            return EnumLocationLevel.SUB_LOCATION;
        }
    }
}
