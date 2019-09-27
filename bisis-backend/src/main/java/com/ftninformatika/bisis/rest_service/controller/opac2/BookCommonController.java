package com.ftninformatika.bisis.rest_service.controller.opac2;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author badf00d21  27.9.19.
 */
@Controller
@RequestMapping("/book_common")
public class BookCommonController {

    @Autowired BookCommonService bookCommonService;

    @PostMapping
    public ResponseEntity<Boolean> saveModifyBookCommon(@RequestBody BookCommon bookCommon) {
        if (!bookCommonService.saveModifyBookCommon(bookCommon))
            return new ResponseEntity<>(false, HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
