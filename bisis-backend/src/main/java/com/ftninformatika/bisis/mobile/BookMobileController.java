package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.mobile.service.BookService;
import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.dto.ReservationResponseDTO;
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

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Autowired
    BookService bookService;

    @PostMapping("/collection")
    public ResponseEntity<List<BookDTO>> getBooksByCollectionId(@RequestHeader("Library") String lib,
                                                                @RequestBody String collectionId) {
        List<Book> books = bookCommonService.getFullBooksByCollectionId(collectionId);

        List<BookDTO> bookDTOS = new ArrayList<>();
        for (Book book : books) {
            bookDTOS.add(new BookDTO(book, bookService.isArticle(book)));
        }

        if (books.isEmpty())
            return new ResponseEntity<>(bookDTOS, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{recordId}")
    public ResponseEntity<BookDTO> getBook(@RequestHeader("Library") String lib, @PathVariable("recordId") String recordId) {
        Book book = opacSearchService.getFullBookById(recordId, lib);
        if (book == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        BookDTO bookDTO = new BookDTO(book, bookService.isArticle(book));
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping("/availability")
    public ResponseEntity<List<BookAvailabilityDTO>> getBooksAvailability(@RequestHeader("Library") String lib,
                                                                          @RequestBody String recordId) {
        LibraryConfiguration config = this.libraryConfigurationRepository.getByLibraryName(lib);

        if (config.getReservation() != null && config.getReservation()) {
            Book book = opacSearchService.getFullBookById(recordId, lib);
            if (book == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<BookAvailabilityDTO> itemAvailabilities = opacSearchService.getBooksAvailabilityByLocation(book);
            return new ResponseEntity<>(itemAvailabilities, HttpStatus.OK);
        } else {
            return new ResponseEntity(new ReservationResponseDTO("Библиотека не подржава ову функционалност"), HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
