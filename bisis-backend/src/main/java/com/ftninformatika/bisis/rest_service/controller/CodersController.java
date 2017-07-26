package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.coders.ItemStatus;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.rest_service.repository.mongo.ItemStatusRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dboberic on 26/07/2017.
 */
@RestController
@RequestMapping("/coders")
public class CodersController {
    @Autowired
    LocationRepository locrep;

    @Autowired
    ItemStatusRepository statrep;


    @RequestMapping(path = "item_status")
    public List<ItemStatus> getStatuses(String libName){
        return statrep.getStatuses(libName);
    }
    @RequestMapping(path = "locations")
    public List<Location> getLocations(String libName){
        return locrep.getLocations(libName);
    }
}
