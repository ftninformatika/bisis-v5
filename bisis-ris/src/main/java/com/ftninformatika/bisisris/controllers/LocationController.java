package com.ftninformatika.bisisris.controllers;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisisauthentication.LibraryPrefixProvider;
import com.ftninformatika.bisisris.repositories.LocationRepository;
import com.ftninformatika.bisisris.repositories.SubLocationRepository;
import org.apache.lucene.index.DocIDMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping("/location/{code}")
    public Location getLocation(@PathVariable("code") String code){
        String libPrefix = lpp.getLibPrefix();

        return lr.findCoder(libPrefix,code);
    }
    @GetMapping("/location/getAll")
    public List<Location> getAllLocation(){
        String libPrefix = lpp.getLibPrefix();
        return lr.findByLibrary(libPrefix);
    }

    @GetMapping("/sublocation/{code}")
    public Sublocation getSubLocation(@PathVariable("code") String code){
        String libPrefix = lpp.getLibPrefix();
        CircLocation circLoc = slr.findCoder(libPrefix,code);
        Sublocation sublocation = new Sublocation();
        sublocation.setCoder_id(circLoc.getLocationCode());
        sublocation.setDescription(circLoc.getDescription());
        return sublocation;
    }

}
