package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.opac2.dto.AddToCollectionDTO;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author badf00d21  19.9.19.
 */
@Controller
@RequestMapping("/opac/book_collections")
public class BookCollectionController {

    @Autowired BookCollectionService bookCollectionService;

    @PostMapping
    public ResponseEntity<Boolean> addModifyCollection(@RequestBody BookCollection bookCollection) {
        if (!bookCollectionService.addModifyCollection(bookCollection))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookCollection>> getBookCollections() {
        return new ResponseEntity<>(bookCollectionService.getCollections(), HttpStatus.OK);
    }

    @PostMapping("/add_record")
    public ResponseEntity<Boolean> addRecordToCollection(@RequestBody AddToCollectionDTO addToCollectionDTO) {
        if(!bookCollectionService.addBookToCollection(addToCollectionDTO))
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{collectionId}")
    public ResponseEntity<Boolean> deleteCollection(@PathVariable("collectionId") String collectionId) {
        if(!bookCollectionService.deleteCollcetion(collectionId))
            return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/showable_collections")
    public ResponseEntity<List<BookCollection>> getShowableCollections() {
        List<BookCollection> bookCollections = bookCollectionService.getShowableCollections();
        Collections.reverse(bookCollections);
        return new ResponseEntity<>(bookCollections, HttpStatus.OK);
    }

    @GetMapping("/swap_indexes")
    public ResponseEntity<Boolean> swapIndexes(@RequestParam("i") Integer i, @RequestParam("i1") Integer i1) {
        if (bookCollectionService.swapCollectionIndexes(i, i1))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
