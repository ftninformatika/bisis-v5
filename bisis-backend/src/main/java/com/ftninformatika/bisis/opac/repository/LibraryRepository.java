package com.ftninformatika.bisis.opac.repository;

import com.ftninformatika.bisis.opac.Library;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibraryRepository extends MongoRepository<Library,String> {
    public List<Library> findLibraryByPrefix(String prefix);
}
