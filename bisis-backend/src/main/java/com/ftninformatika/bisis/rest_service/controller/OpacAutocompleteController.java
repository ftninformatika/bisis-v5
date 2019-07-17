package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.prefixes.PrefixValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author badf00d21  17.7.19.
 */
@Controller
@RequestMapping("/opac/autocomplete")
public class OpacAutocompleteController {

//    TODO- implement real version
    @PostMapping
    public ResponseEntity<List<PrefixValue>> searchAutocomplete(@RequestBody String query) {
        List<PrefixValue> retVal = new ArrayList<>();
        retVal.add(new PrefixValue("AU", "Ivo Andrić"));
        retVal.add(new PrefixValue("AU", "Лав Николајевич Толстој"));
        retVal.add(new PrefixValue("AU", "Sanja  Marinković"));
        retVal.add(new PrefixValue("PU", "Vulkan"));
        retVal.add(new PrefixValue("KW", "tri praseta"));
        retVal.add(new PrefixValue("TI", "Titanik"));
        retVal.add(new PrefixValue("Ti", "Britannica"));
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
