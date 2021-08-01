package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.config.YAMLConfig;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCoverRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
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
    @Autowired
    YAMLConfig yamlConfig;

    public boolean uploadImage(Integer bookCommonID, MultipartFile file) throws IOException {

        if (file == null || file.isEmpty() || bookCommonID == null)
            return false;
        BasicDBObject metaData = new BasicDBObject();
        metaData.put("bookCommonUID", bookCommonID);

        metaData.put("link", yamlConfig.getServerOrigin() + "book_cover/retrieve/" + bookCommonID);
        BookCommon bc = bookCommonRepository.findByUid(bookCommonID);
        if (bc == null) return false;
        bc.setImageUrl(yamlConfig.getServerOrigin() + "book_cover/retrieve/" + bookCommonID);
        bookCommonRepository.save(bc);
        GridFsResource storedCover = getCoverImage(bc.getUid());
        if (storedCover != null)
            deleteCoverImage(bookCommonID);
        gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "image", metaData );
        return true;
    }

    public GridFsResource getCoverImage(Integer bookCommonUID) {
        if (bookCommonUID == null) return null;
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("metadata.bookCommonUID").is(bookCommonUID)));
        if (gridFSFile == null) return null;
        return gridFsTemplate.getResource(gridFSFile);
    }

    public void deleteCoverImage(Integer bookCommonUID) {
        if (bookCommonUID == null) return;
        gridFsTemplate.delete(new Query(Criteria.where("metadata.bookCommonUID").is(bookCommonUID)));
    }

}
