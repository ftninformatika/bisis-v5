package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dboberic on 6.9.2018..
 */
@RestController
@RequestMapping("/library_configuration")
public class LibraryConfigurationController {
    @Autowired LibraryConfigurationRepository librep;

    @RequestMapping(path = "findAllByLibraryNameNotLike")
    public List<LibraryConfiguration> getConfigs(String libName){

        return librep.findAllByLibraryNameNotLike(libName);
    }

    @GetMapping(path = "forLib")
    public ResponseEntity<LibraryConfiguration> getLibraryConfiguration(@RequestParam("libName") String libName){

        LibraryConfiguration lc = librep.getByLibraryName(libName);
        if (lc != null)
            return ResponseEntity.ok(lc);
        return ResponseEntity.notFound().build();
    }
}
