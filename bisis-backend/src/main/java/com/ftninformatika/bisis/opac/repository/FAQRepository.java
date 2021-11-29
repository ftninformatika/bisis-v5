package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.FAQ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FAQRepository extends MongoRepository<FAQ,String> {
    Page<FAQ> findAll(Pageable page);
}
