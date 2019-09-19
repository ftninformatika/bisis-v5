package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author badf00d21  19.9.19.
 */
@Service
public class BookCollectionService {
    @Autowired BookCollectionRepository bookCollectionRepository;
    @Autowired LibraryMemberRepository libraryMemberRepository;

    public boolean addModifyCollection(BookCollection newCollection) {
        if (newCollection == null || newCollection.getCreatorUsername() == null) return false;
        LibraryMember cretor = libraryMemberRepository.findByUsername(newCollection.getCreatorUsername());
        if (cretor == null || !cretor.getAuthorities().contains(Authority.ROLE_ADMIN)
                || newCollection.getBookIds().size() > BookCollection.MAX_SIZE) return false;
        BookCollection bc = bookCollectionRepository.save(newCollection);
        return (bc != null && bc.get_id() != null);
    }

//    public List<BookCollection> getCollections() {
//        List<BookCollection> bookCollections =
//    }

}
