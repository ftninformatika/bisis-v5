package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac.books.BookCommon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author badf00d21  29.7.19.
 */
public interface BookCommonRepository extends MongoRepository<BookCommon, String>, BookCommonRepositoryCustom {
    BookCommon findByUid (Integer uid);
    Optional<List<BookCommon>> findByIsbn(String isbn);

}
