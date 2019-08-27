package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacAutocompleteService;
import io.swagger.annotations.*;
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
@Api(value = "Autocomplete")
public class OpacAutocompleteController {

    @Autowired OpacAutocompleteService opacAutocompleteService;

    @ApiOperation(value = "View a list of autocomplete predefined values for `query` input and `Library` request header value." +
            "`Library` value is 'gbns' for Public Library of Novi Sad."
            , response = PrefixValue[].class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a list of values", examples = @Example(
                    value = {
                            @ExampleProperty(
                                    mediaType = "application/json",
                                    value = "[{'prefName': 'keyword', 'value': 'andric'}]"
                            )
                    }
            )),
            @ApiResponse(code = 400, message = "Your request is invalid"),
            @ApiResponse(code = 204, message = "Result set empty")
    })
    @PostMapping
    public ResponseEntity<List<PrefixValue>> searchAutocomplete(
        @ApiParam(example = "gbns", required = true) @RequestHeader(value = "Library") String lib,
        @ApiParam(example = "andric", required = true) @RequestBody String query) {
        if (lib == null || lib.trim().equals(""))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<PrefixValue> retVal = opacAutocompleteService.getAutocompleteResults(query);
        if (retVal.size() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(retVal, HttpStatus.OK);

    }
}
