package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac2.BookCover;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author badf00d21  19.8.19.
 */
@Repository
public interface BookCoverRepository extends MongoRepository<BookCover, String> {

}
