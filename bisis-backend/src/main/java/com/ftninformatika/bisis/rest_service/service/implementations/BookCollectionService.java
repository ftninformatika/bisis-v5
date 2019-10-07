package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.opac2.dto.AddToCollectionDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author badf00d21  19.9.19.
 */
@Service
public class BookCollectionService {
    private static int MAX_COLLECTIONS_PER_LIB = 15;
    @Autowired BookCollectionRepository bookCollectionRepository;
    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired RecordsRepository recordsRepository;

    public boolean addModifyCollection(BookCollection newCollection) {
        if (newCollection == null || newCollection.getCreatorUsername() == null) return false;
        if (bookCollectionRepository.count() >= MAX_COLLECTIONS_PER_LIB) return false;
        LibraryMember creator = libraryMemberRepository.findByUsername(newCollection.getCreatorUsername());
        if (creator == null || !creator.getAuthorities().contains(Authority.ROLE_ADMIN)
                || newCollection.getRecordsIds().size() > BookCollection.MAX_SIZE) return false;
        newCollection.setLastModified(new Date());
        if (newCollection.get_id() == null && bookCollectionRepository.findByTitle(newCollection.getTitle()) != null)
            return false;
        BookCollection bc = bookCollectionRepository.save(newCollection);
        return (bc != null && bc.get_id() != null);
    }

    public List<BookCollection> getCollections() {
        return bookCollectionRepository.findAll();
    }

    public boolean addBookToCollection(AddToCollectionDTO addToCollectionDTO) {
        if (addToCollectionDTO.getCollectionId() == null || addToCollectionDTO.getRecordId() == null) return false;
        Optional<Record> rO = recordsRepository.findById(addToCollectionDTO.getRecordId());
        Optional<BookCollection> bcO = bookCollectionRepository.findById(addToCollectionDTO.getCollectionId());
        if (!rO.isPresent() || !bcO.isPresent()) return false;
        BookCollection bc = bcO.get();
        if (bc.getRecordsIds().contains(addToCollectionDTO.getRecordId()))
            return false;
        if (bc.getRecordsIds() == null) bc.setRecordsIds(new ArrayList<>());
        bc.getRecordsIds().add(addToCollectionDTO.getRecordId());
        bookCollectionRepository.save(bc);
        return true;
    }

}
