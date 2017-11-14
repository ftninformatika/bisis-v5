package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.circ.CircConfig;
import com.ftninformatika.bisis.rest_service.repository.mongo.CircConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Petar on 11/7/2017.
 */
@RestController
@RequestMapping("/circ_configuration")
public class CircConfigController {

    @Autowired
    CircConfigRepository circLocationRepository;

    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public CircConfig save(@RequestBody CircConfig circConfig){
        return  circLocationRepository.save(circConfig);
    }
}
