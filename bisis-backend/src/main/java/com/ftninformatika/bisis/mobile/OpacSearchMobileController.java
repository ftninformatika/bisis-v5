package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.books.Book;
import com.ftninformatika.bisis.opac.search.ResultPageSearchRequest;
import com.ftninformatika.bisis.rest_service.service.implementations.OpacSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/mobile/opac_search")
public class OpacSearchMobileController {
    @Autowired
    OpacSearchService opacSearchService;

    @PostMapping
    public ResponseEntity<?> search(
            @RequestHeader("Library") String lib,
            @RequestBody ResultPageSearchRequest resultPageSearchRequest,
            @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) final Integer pageSize) {

        PageImpl<List<Book>> retVal = opacSearchService.searchBooks(resultPageSearchRequest, lib, pageNumber, pageSize);

        if (retVal == null || retVal.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        List<BookDTO> bookDTOS = new ArrayList<>();
        for (int i = 0; i < retVal.getContent().size(); i++) {
            bookDTOS.add(new BookDTO((Book) retVal.getContent().get(i)));
        }
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }
}
