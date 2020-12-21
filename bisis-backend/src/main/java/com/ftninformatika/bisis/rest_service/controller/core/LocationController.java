package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.rest_service.reservations.service.impl.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author marijakovacevic
 */
@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping(value = "/{coderId}")
    public ResponseEntity<?> getLibraryBranchName(@RequestHeader("Library") String library,
                                                  @PathVariable("coderId") String coderId) {
        String location = locationService.getLibraryBranchName(library, coderId);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

}
