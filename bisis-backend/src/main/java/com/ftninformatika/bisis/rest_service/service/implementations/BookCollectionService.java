package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.core.repositories.LibrarianRepository;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac.BookCollection;
import com.ftninformatika.bisis.opac.dto.AddToCollectionDTO;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCollectionRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author badf00d21  19.9.19.
 */
@Service
public class BookCollectionService {
    @Autowired BookCollectionRepository bookCollectionRepository;
    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired LibrarianRepository librarianRepository;
    @Autowired RecordsRepository recordsRepository;
    @Autowired LibraryConfigurationRepository libraryConfigurationRepository;
    private static Logger log = Logger.getLogger(BookCollectionService.class);

    public boolean addModifyCollection(String library, BookCollection newCollection) {
        if (newCollection == null || newCollection.getCreatorUsername() == null) return false;

        int maxCollectionsPerLib = getMaxCollectionsPerLib(library);

        if (newCollection.getIndex() < 0 && newCollection.get_id() == null) {
            if (bookCollectionRepository.count() + 1 > maxCollectionsPerLib) return false;
        } else {
            if (bookCollectionRepository.count() > maxCollectionsPerLib) return false;
        }

        Optional<LibrarianDB> creator = librarianRepository.findByEmailAndBiblioteka(newCollection.getCreatorUsername(), library);
        if (creator.isEmpty() || !creator.get().getLibrarianRoles().contains(Librarian.Role.OPACADMIN)
                || newCollection.getRecordsIds().size() > BookCollection.MAX_SIZE) return false;
        newCollection.setLastModified(new Date());
        if (newCollection.get_id() == null && bookCollectionRepository.findByTitle(newCollection.getTitle()) != null)
            return false;
        else if (newCollection.get_id() == null)
            newCollection.setIndex(generateIndex());
            log.info("(addModifyCollection) Za novu kolekciju: " + newCollection.getTitle() + " postavljen je index: "
                    + newCollection.getIndex());
        BookCollection bc = bookCollectionRepository.save(newCollection);
        return (bc != null && bc.get_id() != null);
    }

    private Integer getMaxCollectionsPerLib(String library) {
        LibraryConfiguration libConf = libraryConfigurationRepository.getByLibraryName(library);
        Integer maxCollectionsPerLib = libConf.getMaxCollectionsPerLib();
        if (maxCollectionsPerLib == null) {
            maxCollectionsPerLib = 15;
        }
        return maxCollectionsPerLib;
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
        bc.getRecordsIds().add(0, addToCollectionDTO.getRecordId());
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
            log.info("(swapCollectionIndexes) Prva kolekcija je: " + bc.getTitle() + " sa indexom: " + bc.getIndex());
            log.info("(swapCollectionIndexes) Druga kolekcija je: " + bc1.getTitle() + " sa indexom: " + bc1.getIndex());
            bc.setIndex(i1);
            bc1.setIndex(i);
            bookCollectionRepository.save(bc);
            bookCollectionRepository.save(bc1);
            log.info("(swapCollectionIndexes) Postavljen novi index za: " + bc.getTitle() + " je: " + bc.getIndex());
            log.info("(swapCollectionIndexes) Postavljen novi index za: " + bc.getTitle() + " je: " + bc1.getIndex());
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

    public List<BookCollection> getCollectionsForAndroid() {
        List<BookCollection> bookCollections = bookCollectionRepository.findAll();
        bookCollections.sort(Comparator.comparing(BookCollection::getIndex).reversed());
        return bookCollections;
    }
}
