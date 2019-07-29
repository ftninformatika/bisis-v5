package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import com.ftninformatika.bisis.search.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author badf00d21  25.7.19.
 */
@Controller
@RequestMapping("/opac/search")
public class OpacSearchController {

    @Autowired OpacSearchService opacSearchService;


    @PostMapping
    public ResponseEntity<?> search(@RequestBody SearchModel searchModel
            , @RequestParam(value = "pageNumber", required = false) final Integer pageNumber
            , @RequestParam(value = "pageSize", required = false) final Integer pageSize) {

        PageImpl<List<Book>> retVal = opacSearchService.searchBooks(searchModel, pageNumber, pageSize);

        if (retVal.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}

