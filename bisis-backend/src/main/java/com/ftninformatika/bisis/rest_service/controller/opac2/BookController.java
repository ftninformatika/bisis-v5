package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCommonService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author badf00d21  19.8.19.
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired OpacSearchService opacSearchService;
    @Autowired BookCommonService bookCommonService;

    @PostMapping
    public ResponseEntity<Book> getBook(@RequestHeader("Library") String lib, @RequestBody String recordId) {
        Book retVal = opacSearchService.getFullBookById(recordId, lib);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    @PostMapping("/multiple")
    public ResponseEntity<List<Book>> getBooks(@RequestBody List<String> recordsIds) {
        List<Book> books = bookCommonService.getBooksByRecordIds(recordsIds);
        if (books.isEmpty())
            return new ResponseEntity<>(books, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
