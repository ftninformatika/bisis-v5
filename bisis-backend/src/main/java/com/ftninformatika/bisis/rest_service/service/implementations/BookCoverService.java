package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCoverRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author badf00d21  19.8.19.
 */
@Service
public class BookCoverService {

    @Autowired BookCoverRepository bookCoverRepository;
    @Autowired GridFsTemplate gridFsTemplate;
    @Autowired BookCommonRepository bookCommonRepository;

    public boolean uploadImage(Integer bookCommonID, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty() || bookCommonID == null)
            return false;
        BasicDBObject metaData = new BasicDBObject();
        metaData.put("bookCommonUID", bookCommonID);
        metaData.put("link", "/book_cover/retrieve/" + bookCommonID);
        BookCommon bc = bookCommonRepository.findByUid(bookCommonID);
        if (bc == null) return false;
        bc.setImageUrl("/book_cover/retrieve/" + bookCommonID);
        bookCommonRepository.save(bc);
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "image", metaData );
        return true;
    }

    public GridFsResource getCoverImage(Integer bookCommonID) {
        if (bookCommonID == null) return null;
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("metadata.bookCommonUID").is(bookCommonID)));
        if (gridFSFile == null) return null;
        return gridFsTemplate.getResource(gridFSFile);
    }

}
