package com.ftninformatika.bisis.core.repositories;

import com.ftninformatika.bisis.coders.Coder;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CoderRepository <T extends Coder> extends MongoRepository<T,String> {
    public List<T> findByLibraryIsNullOrLibrary(String library);

}
