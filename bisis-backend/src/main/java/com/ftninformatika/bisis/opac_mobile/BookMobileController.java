package com.ftninformatika.bisis.opac_mobile;

import com.ftninformatika.bisis.mobile.BookDTO;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
