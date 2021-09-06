package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCommonService;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/mobile/book")
public class BookMobileController {
    @Autowired
    BookCommonService bookCommonService;

    @Autowired
    OpacSearchService opacSearchService;

    @PostMapping("/collection")
    public ResponseEntity<List<BookDTO>> getBooksByCollectionId(@RequestBody String collectionId) {
        List<Book> books = bookCommonService.getBooksByCollectionId(collectionId);

        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            bookDTOS.add(new BookDTO(book));
        }

        if (books.isEmpty())
            return new ResponseEntity<>(bookDTOS, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }


    @PostMapping("/availability")
    public ResponseEntity<List<BookAvailabilityDTO>> getBooksAvailability(@RequestHeader("Library") String lib,
                                                                          @RequestBody String recordId) {
        Book book = opacSearchService.getFullBookById(recordId, lib);
        if (book == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<BookAvailabilityDTO> itemAvailabilities = opacSearchService.getBooksAvailabilityByLocation(book);
        return new ResponseEntity<>(itemAvailabilities, HttpStatus.OK);
    }
}