package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.opac2.opac2.Filters;
import com.ftninformatika.bisis.opac2.opac2.ResultPageSearchRequest;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author badf00d21  25.7.19.
 */
@Controller
@RequestMapping("/opac/search")
public class OpacSearchController {

    @Autowired OpacSearchService opacSearchService;


    @PostMapping
    public ResponseEntity<?> search(@RequestBody ResultPageSearchRequest resultPageSearchRequest
            , @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {

        PageImpl<List<Book>> retVal = opacSearchService.searchBooks(resultPageSearchRequest, pageNumber, pageSize);

        if (retVal.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "getFilters")
    public ResponseEntity<?> getFilters(@RequestHeader("Library") String lib, @RequestBody ResultPageSearchRequest filterRequest) {
        Filters retVal = opacSearchService.getFilters(filterRequest, lib);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping(value = "filterSearch")
    public ResponseEntity<?> filterSearch(@RequestBody ResultPageSearchRequest filterRequest) {
        return null;
    }
}

