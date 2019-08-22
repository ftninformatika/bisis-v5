package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.BookCover;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCoverRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author badf00d21  19.8.19.
 */
@Service
public class BookCoverService {

    @Autowired BookCoverRepository bookCoverRepository;

    public BookCover uploadImage(String lib, MultipartFile file) throws IOException {
        BookCover bookCover = new BookCover();
        bookCover.setImageBin(new Binary((BsonBinarySubType.BINARY), file.getBytes()));
        bookCover = bookCoverRepository.insert(bookCover);
        bookCover.setLink(lib + "/book_cover/get/" + bookCover.get_id());
        return bookCoverRepository.save(bookCover);
    }
}
