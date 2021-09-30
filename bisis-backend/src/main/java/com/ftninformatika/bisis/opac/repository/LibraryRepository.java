package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Library;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends MongoRepository<Library,String> {
    List<Library> findLibraryByPrefix(String prefix);
    Page<Library> findLibraryByPrefix(String prefix, Pageable page);
}
