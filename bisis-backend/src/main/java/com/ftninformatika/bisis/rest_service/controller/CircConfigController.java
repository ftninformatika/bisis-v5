package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.models.circ.CircConfig;
import com.ftninformatika.bisis.models.circ.CircLocation;
import com.ftninformatika.bisis.rest_service.repository.mongo.CircConfigRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.CircLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Petar on 11/7/2017.
 */
@RestController
@RequestMapping("/circ_configuration")
public class CircConfigController {

    @Autowired
    CircConfigRepository circLocationRepository;

    @RequestMapping( value = "/save", method = RequestMethod.POST )
    public CircConfig save(CircConfig circConfig){
        return  circLocationRepository.save(circConfig);
    }
}
