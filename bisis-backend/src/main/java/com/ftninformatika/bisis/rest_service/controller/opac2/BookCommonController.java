package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author badf00d21  27.9.19.
 */
@Controller
@RequestMapping("/book_common")
public class BookCommonController {

    @Autowired BookCommonService bookCommonService;


    @PostMapping
    public ResponseEntity<BookCommon> saveModifyBookCommon(@RequestBody BookCommon bookCommon) {
        BookCommon bc = bookCommonService.saveModifyBookCommon(bookCommon);
        if (bc == null)
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(bc, HttpStatus.OK);
    }

    @GetMapping("/{bookCommonUID}")
    public ResponseEntity<BookCommon> getBookCommon(@PathVariable("bookCommonUID") Integer bookCommonUID) {
        BookCommon bookCommon = bookCommonService.getBookCommonByUID(bookCommonUID);
        if (bookCommon == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(bookCommon, HttpStatus.OK);
    }
}
