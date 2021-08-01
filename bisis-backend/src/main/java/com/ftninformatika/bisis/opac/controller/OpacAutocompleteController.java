package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacAutocompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author badf00d21  17.7.19.
 */
@Controller
@RequestMapping("/opac/autocomplete")
public class OpacAutocompleteController {

    @Autowired OpacAutocompleteService opacAutocompleteService;

    @PostMapping
    public ResponseEntity<List<PrefixValue>> searchAutocomplete(
       @RequestHeader(value = "Library") String lib,
       @RequestBody String query) {
        if (lib == null || lib.trim().equals(""))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<PrefixValue> retVal = opacAutocompleteService.getAutocompleteResults(query);
        if (retVal.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }
}
