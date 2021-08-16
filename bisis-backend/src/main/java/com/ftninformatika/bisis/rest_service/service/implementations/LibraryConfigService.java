package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author badf00d21  26.3.19.
 */
@Service
public class LibraryConfigService {

    @Autowired
    LibraryConfigurationRepository lcRepo;

    public List<String> getAllLibraryPrefixes() {
        List<String> retVal = new ArrayList<>();

        try {
            retVal = lcRepo.findAll().stream()
                    .map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retVal;
    }

    public Map<String, LibraryConfiguration> getAllLibraryConfigMap() {
        Map<String, LibraryConfiguration> retVal = new HashMap<>();
        for (LibraryConfiguration lc: lcRepo.findAll()) {
            retVal.put(lc.getLibraryName(), lc);
        }
        return retVal;
    }
}
