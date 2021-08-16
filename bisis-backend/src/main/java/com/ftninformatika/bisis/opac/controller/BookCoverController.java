package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.rest_service.service.implementations.BookCoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @author badf00d21  19.8.19.
 */
@Controller
@RequestMapping("book_cover")
public class BookCoverController {

    @Autowired BookCoverService bookCoverService;

    @PostMapping("/upload/{bookCommonUID}")
    public ResponseEntity<Boolean> uploadImage(@PathVariable("bookCommonUID") Integer bookCommonUID,
                                         @RequestPart("file") MultipartFile file) {
        try {
            if (!bookCoverService.uploadImage(bookCommonUID, file))
                return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/retrieve/{bookCommonUID}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getCover(@PathVariable("bookCommonUID") Integer bookCommonUID) {
        GridFsResource gridFSFile = bookCoverService.getCoverImage(bookCommonUID);
        if (gridFSFile == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        try {
            return ResponseEntity.ok()
                    .contentLength(gridFSFile.contentLength())
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_JPEG_VALUE))
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                    .contentType(MediaType.parseMediaType(MediaType.IMAGE_GIF_VALUE))
                    .body(new InputStreamResource(gridFSFile.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
