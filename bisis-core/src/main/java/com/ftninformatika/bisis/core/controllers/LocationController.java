package com.ftninformatika.bisis.core.controllers;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.core.repositories.CircLocationRepository;
import com.ftninformatika.bisis.core.repositories.LocationRepository;
import com.ftninformatika.bisis.core.repositories.SubLocationRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LocationController {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CircLocationRepository circLocationRepository;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @Autowired
    SubLocationRepository subLocationRepository;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @RequestMapping(path = "/coders/sublocation/get_by_coder_id")
    public Sublocation getSublocationByCoderId(@RequestParam("coderId") String coderId, @RequestParam("lib") String lib) {
        Sublocation sl = subLocationRepository.findCoder(lib, coderId);
        if (sl != null && sl.getDescription() != null) {
            sl.setDescription(LatCyrUtils.toCyrillic(sl.getDescription()));
        }
        return sl;
    }

    @RequestMapping(path = "/coders/sublocation/get_by_location")
    public List<Sublocation> getSublocationsByLocation(@RequestParam("loc") String loc, @RequestParam("lib") String lib) {
        List<Sublocation> retVal = subLocationRepository.getCoders(lib).stream().filter(s -> s.getCoder_id().startsWith(loc)).collect(Collectors.toList());
        System.out.println(retVal);
        return retVal;
    }

    @GetMapping("/location/{code}")
    public Location getLocation(@PathVariable("code") String code){
        String libPrefix = libraryPrefixProvider.getLibPrefix();

        return locationRepository.findCoder(libPrefix,code);
    }
    @GetMapping("/location/getAll")
    public List<Location> getAllLocation(){
        String libPrefix = libraryPrefixProvider.getLibPrefix();
        return locationRepository.findByLibrary(libPrefix);
    }

    @GetMapping("/location/getAllSublocation")
    public List<Sublocation> getAllSublocation(){
        return subLocationRepository.findByLibrary(libraryPrefixProvider.getLibPrefix());
    }

    @GetMapping("/sublocation/{code}")
    public Sublocation getSubLocation(@PathVariable("code") String code){
        String libPrefix = libraryPrefixProvider.getLibPrefix();
        CircLocation circLoc = circLocationRepository.findCoder(libPrefix,code);
        Sublocation sublocation = new Sublocation();
        sublocation.setCoder_id(circLoc.getCoder_id());
        sublocation.setDescription(circLoc.getDescription());
        return sublocation;
    }

}
