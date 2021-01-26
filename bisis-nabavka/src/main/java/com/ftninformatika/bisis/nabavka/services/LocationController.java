package com.ftninformatika.bisis.nabavka.services;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.nabavka.repositories.*;
import com.ftninformatika.bisis.nabavka.repositories.SubLocationRepository;
import com.ftninformatika.bisisauthentication.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    LocationRepository lr;

    @Autowired
    SubLocationRepository slr;

    @Autowired
    LibraryPrefixProvider lpp;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @GetMapping("/getAll")
    public List<Location> getAllLocation(){
        return lr.findByLibrary(lpp.getLibPrefix());
    }

    @GetMapping("/getAllSublocation")
    public List<Sublocation> getAllSublocation(){
        return slr.findByLibrary(lpp.getLibPrefix());
    }

}
