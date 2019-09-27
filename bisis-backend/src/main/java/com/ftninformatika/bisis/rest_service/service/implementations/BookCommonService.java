package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author badf00d21  27.9.19.
 */
@Service
public class BookCommonService {

    @Autowired BookCommonRepository bookCommonRepository;

    public BookCommon saveModifyBookCommon(BookCommon bookCommon) {
        if (bookCommon == null || bookCommon.getUid() == null ||
                (bookCommon.getIsbn() == null && bookCommon.getIssn() == null)) return null;
        return bookCommonRepository.save(bookCommon);
    }

    public BookCommon getBookCommonByUID(Integer bookCommonUID) {
        return bookCommonRepository.findByUid(bookCommonUID);
    }

}
