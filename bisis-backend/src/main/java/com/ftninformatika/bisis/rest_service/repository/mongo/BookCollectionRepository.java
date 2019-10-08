package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac2.BookCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author badf00d21  19.9.19.
 */
@Repository
public interface BookCollectionRepository extends MongoRepository<BookCollection, String> {
    BookCollection findByTitle (String title);
    List<BookCollection> findBookCollectionsByShowCollection (boolean shown);
}
