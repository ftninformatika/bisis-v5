package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.SubLocationRepository;
import com.ftninformatika.bisis.library_configuration.LibConfigDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dboberic on 6.9.2018..
 */
@RestController
@RequestMapping("/library_configuration")
public class LibraryConfigurationController {
    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;
    @Autowired
    SubLocationRepository subLocationRepository;

    @RequestMapping(path = "findAllByLibraryNameNotLike")
    public List<LibraryConfiguration> getConfigs(String libName){

        return libraryConfigurationRepository.findAllByLibraryNameNotLike(libName);
    }

    @GetMapping(path = "forLib")
    public ResponseEntity<LibraryConfiguration> getLibraryConfiguration(@RequestParam("libName") String libName){

        LibraryConfiguration lc = libraryConfigurationRepository.getByLibraryName(libName);
        if (lc != null)
            return ResponseEntity.ok(lc);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("allConfigsBrief")
    public ResponseEntity<List<LibConfigDTO>> getAllConfigsBrief() {
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findAll();
        if (libraryConfigurations == null || libraryConfigurations.size() == 0)
            return ResponseEntity.noContent().build();
        List<LibConfigDTO> retVal = libraryConfigurations.stream()
                .map(lc -> new LibConfigDTO(lc.getLibraryName(), lc.getLibraryFullName(), lc.getShortName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(retVal);
    }

    @GetMapping("mobileSupported")
    public ResponseEntity<List<LibConfigDTO>> getConfigsMobileSupported() {
        List<LibraryConfiguration> libraryConfigurations = libraryConfigurationRepository.findLibraryConfigurationsByMobileAppIsTrue();
        if (libraryConfigurations == null || libraryConfigurations.size() == 0)
            return ResponseEntity.noContent().build();
        List<LibConfigDTO> retVal = new ArrayList<LibConfigDTO>();
         for (LibraryConfiguration lc: libraryConfigurations){
             List<Sublocation> sublocations = subLocationRepository.getCoders(lc.getLibraryName());
             retVal.add(new LibConfigDTO(lc.getLibraryName(),lc.getLibraryFullName(),lc.getShortName(),sublocations));
         }
        return ResponseEntity.ok(retVal);
    }
}
