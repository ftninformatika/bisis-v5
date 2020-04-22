package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author badf00d21  29.7.19.
 */
public interface BookCommonRepository extends MongoRepository<BookCommon, String>, BookCommonRepositoryCustom {
    BookCommon findByUid (Integer uid);

}
