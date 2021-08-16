package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.BookCollection;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/mobile/book_collections")
public class BookCollectionMobileController {
    @Autowired
    BookCollectionService bookCollectionService;

    @GetMapping("/collections")
    public ResponseEntity<List<BookCollectionDTO>> getAllCollectionsForAndroid() {
        List<BookCollection> bookCollections = bookCollectionService.getCollectionsForAndroid();
        Collections.reverse(bookCollections);

        List<BookCollectionDTO> bookCollectionDTOS = new ArrayList<>();
        for (BookCollection bc : bookCollections) {
            bookCollectionDTOS.add(new BookCollectionDTO(bc));
        }

        return new ResponseEntity<>(bookCollectionDTOS, HttpStatus.OK);
    }
}
