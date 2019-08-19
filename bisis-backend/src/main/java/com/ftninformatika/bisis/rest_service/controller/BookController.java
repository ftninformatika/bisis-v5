package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author badf00d21  19.8.19.
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired OpacSearchService opacSearchService;

    @PostMapping
    public ResponseEntity<Book> getBook(@RequestBody String _id) {
        Book retVal = opacSearchService.getFullBookById(_id);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }
}
