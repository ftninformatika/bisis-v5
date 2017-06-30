package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.rest_service.bisis4_model.Records;
import com.ftninformatika.bisis.rest_service.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 6/28/2017.
 */
@RestController
@RequestMapping("/expand_prefix_controller")
public class ExpandPrefixController {

    @Autowired
    RecordsRepository recordsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getExpandPrefix(@Param("prefix") String prefix, @Param("text") String text) {

        ArrayList<String> res = new ArrayList<String>();
        res.add("izbor1 sa backenda");
        res.add("izbor2 sa backenda");
        res.add("izbor3 sa backenda");
        res.add("izbor4 sa backenda");

        return new ResponseEntity<List<String>>(res, HttpStatus.OK);
    }

}