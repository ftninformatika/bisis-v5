package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.records.DocFile;
import com.ftninformatika.bisis.rest_service.repository.mongo.DocFileRepository;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Petar on 1/24/2018.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired GridFsOperations gridFsOperations;
    @Autowired DocFileRepository docFileRepository;

    @RequestMapping( value = "/upload", method = RequestMethod.POST,
                    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean uploadDocumentFile(@RequestParam("file")MultipartFile file, @RequestParam("fileInfo")DocFile info){
        info.setContentType(file.getContentType());
        info.setUploadDate(new Date());
        try {
            GridFSFile gi = gridFsOperations.store(file.getInputStream(),info);
            info.setFileId(gi.getId().toString());
            docFileRepository.save(info);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
