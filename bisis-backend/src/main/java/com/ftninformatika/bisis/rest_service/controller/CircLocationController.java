package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.rest_service.repository.mongo.CircLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Petar on 10/18/2017.
 */
@RestController
@RequestMapping("/circ_location")
public class CircLocationController {

    @Autowired CircLocationRepository circLocationRepository;

    @RequestMapping("/lastUserId")
    public Integer getLastUserId(@RequestParam("location") String location, @RequestHeader("Library") String library){
        List<CircLocation> circLocation = circLocationRepository.findByLocationCodeAndLibrary(location, library);

        if (circLocation == null || circLocation.size() > 1 || circLocation.size() == 0)
            return null;

        circLocation.get(0).setLastUserId(circLocation.get(0).getLastUserId() + 1);
        circLocationRepository.save(circLocation.get(0));
        return circLocation.get(0).getLastUserId();
    }
}
