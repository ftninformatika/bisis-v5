package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.location.dto.RecordCtlgNoDTO;
import com.ftninformatika.bisis.reservations.service.impl.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author marijakovacevic
 */
@RestController("backendLocationController")
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

    @PostMapping(value = "/by-primerak")
    public ResponseEntity<?> getLocationCodeByPrimerak(@RequestHeader("Library") String library,
                                                       @RequestBody RecordCtlgNoDTO recordCtlgNo) {
        String location = locationService.getLocationCodeByPrimerak(recordCtlgNo.getRecord(), recordCtlgNo.getCtlgNo(), library);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

}
