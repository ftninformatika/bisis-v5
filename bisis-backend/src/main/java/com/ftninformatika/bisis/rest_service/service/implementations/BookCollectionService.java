package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.opac2.BookCollection;
import com.ftninformatika.bisis.opac2.dto.AddToCollectionDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        else if (newCollection.get_id() == null)
            newCollection.setIndex(generateIndex());
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
        if (bc.getRecordsIds().contains(addToCollectionDTO.getRecordId()) || bc.getRecordsIds().size() >= 30)
            return false;
        if (bc.getRecordsIds() == null) bc.setRecordsIds(new ArrayList<>());
        bc.getRecordsIds().add(addToCollectionDTO.getRecordId());
        bookCollectionRepository.save(bc);
        return true;
    }

    public boolean deleteCollcetion(String collectionId) {
        if (collectionId == null) return false;
        if (!bookCollectionRepository.findById(collectionId).isPresent())
            return false;
        try {
            bookCollectionRepository.deleteById(collectionId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<BookCollection> getShowableCollections() {
        List<BookCollection> bookCollections = new ArrayList<>();
        bookCollections = bookCollectionRepository.findBookCollectionsByShowCollection(true);
        bookCollections.sort(Comparator.comparing(BookCollection::getIndex).reversed());
        return bookCollections;
    }

    public boolean swapCollectionIndexes(Integer i, Integer i1) {
        try {
            List<BookCollection> bookCollections = bookCollectionRepository.findAll();
            List<Integer> indexes = bookCollections.stream().map(BookCollection::getIndex).collect(Collectors.toList());
            if (i == null || i1 == null || !indexes.contains(i) || !indexes.contains(i1))
                return false;
            BookCollection bc = bookCollections.stream().filter(b -> b.getIndex() == i).findFirst().get();
            BookCollection bc1 = bookCollections.stream().filter(b -> b.getIndex() == i1).findFirst().get();
            bc.setIndex(i1);
            bc1.setIndex(i);
            bookCollectionRepository.save(bc);
            bookCollectionRepository.save(bc1);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int generateIndex() {
        try {
            List<BookCollection> bookCollections = bookCollectionRepository.findAll();
            bookCollections.sort(Comparator.comparing(BookCollection::getIndex).reversed());
            return bookCollections.get(0).getIndex() + 1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
