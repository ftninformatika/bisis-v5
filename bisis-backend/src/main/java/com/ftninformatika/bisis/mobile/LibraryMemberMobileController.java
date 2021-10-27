package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.mobile.service.BookService;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
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
@RequestMapping("/mobile/library_members")
public class LibraryMemberMobileController {
    @Autowired
    LibraryMemberService libraryMemberService;

    @Autowired
    BookService bookService;

    @PostMapping("/get_shelf")
    public ResponseEntity<List<BookDTO>> getShelf(@RequestHeader("Library") String lib, @RequestBody String username) {
        List<Book> retVal = libraryMemberService.getShelf(username, lib);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : retVal) {
            bookDTOS.add(new BookDTO(book, bookService.isArticle(book)));
        }
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }
}
