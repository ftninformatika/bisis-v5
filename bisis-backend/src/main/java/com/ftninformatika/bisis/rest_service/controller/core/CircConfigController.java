package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.circ.CircConfig;
import com.ftninformatika.bisis.core.repositories.CircConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Petar on 11/7/2017.
 */
@RestController
@RequestMapping("/circ_configuration")
public class CircConfigController {

    @Autowired CircConfigRepository circLocationRepository;

    @GetMapping("/get_by_library")
    public CircConfig getByLibrary(@RequestParam("libname") String libName) {
        return circLocationRepository.findByLibrary(libName);
    }

    @PostMapping("/save")
    public CircConfig save(@RequestBody CircConfig circConfig){
        return  circLocationRepository.save(circConfig);
    }
}
