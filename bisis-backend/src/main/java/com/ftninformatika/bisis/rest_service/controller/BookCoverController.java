package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.opac2.BookCover;
import com.ftninformatika.bisis.rest_service.service.implementations.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author badf00d21  19.8.19.
 */
@Controller
@RequestMapping("bookCover")
public class BookCoverController {

    @Autowired BookCoverService bookCoverService;

    @PostMapping("upload")
    public ResponseEntity<?> uploadImage(@RequestHeader("Library") String lib, @RequestBody MultipartFile file) {
        try {
            BookCover bc = bookCoverService.uploadImage(lib, file);
            return new ResponseEntity<>(bc.getLink(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
