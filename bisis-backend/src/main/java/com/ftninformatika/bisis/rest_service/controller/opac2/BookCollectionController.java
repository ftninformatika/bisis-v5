package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
