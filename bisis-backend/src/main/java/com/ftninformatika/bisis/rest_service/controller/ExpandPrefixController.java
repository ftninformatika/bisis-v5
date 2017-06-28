package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.rest_service.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Petar on 6/28/2017.
 */
@RepositoryRestController
public class ExpandPrefixController {
    @Autowired
    RecordsRepository recordsRepository;

    @RequestMapping(value = "/expandList/{smth}", method = RequestMethod.GET)
    public List<String> getExpand(@PathVariable String stmh) {
        List<String> retVal = null;

        return retVal;

    }
}