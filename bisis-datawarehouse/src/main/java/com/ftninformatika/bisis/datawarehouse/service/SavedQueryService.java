package com.ftninformatika.bisis.datawarehouse.service;

import com.ftninformatika.bisis.datawarehouse.entity.SavedQuery;
import com.ftninformatika.bisis.datawarehouse.repository.SavedQueryRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedQueryService {

    @Autowired
    @Qualifier("savedQueryJPARepository")
    SavedQueryRepository savedQueryRepository;

    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    public SavedQuery saveQuery(SavedQuery savedQuery) {
        savedQuery.setDate(LocalDateTime.now());
        savedQuery.setLibrary(libraryPrefixProvider.getLibPrefix());
        return savedQueryRepository.save(savedQuery);
    }

    public List<SavedQuery> getSavedQueries() {
        Optional<List<SavedQuery>> optionalSavedQueries = savedQueryRepository.findByLibrary(libraryPrefixProvider.getLibPrefix());
        return optionalSavedQueries.orElseGet(ArrayList::new);
    }

    public boolean deleteSavedQuery(Integer id) {
        Optional<SavedQuery> optionalSavedQuery = savedQueryRepository.findById(id);
        if (optionalSavedQuery.isPresent()) {
            SavedQuery savedQuery = optionalSavedQuery.get();
            savedQueryRepository.delete(savedQuery);
            return true;
        } else {
            return false;
        }
    }
}
