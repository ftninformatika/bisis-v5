package com.ftninformatika.bisis.rest_service.repository.mongo;

import com.ftninformatika.bisis.opac.books.BookCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author badf00d21  10.10.19.
 */
public class BookCommonRepositoryImpl implements BookCommonRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;


    public Integer generateBookUID() {
        try {
            final Query query = new Query()
                    .limit(1)
                    .with(new Sort(Sort.Direction.DESC, "uid"));
            return mongoTemplate.findOne(query, BookCommon.class).getUid() + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
