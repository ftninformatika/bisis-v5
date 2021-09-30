package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Library;
import com.ftninformatika.bisis.opac.repository.LibraryRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
public class LibraryController {
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    LibraryPrefixProvider libraryPrefixProvider;

    @GetMapping("/get")
    public List<Library> getLibraries() {
        return libraryRepository.findLibraryByPrefix(libraryPrefixProvider.getLibPrefix());
    }

    @GetMapping()
    public ResponseEntity<Page<Library>> getAllLibraries(@RequestHeader("Library") String lib,
                                                         @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
                                                         @RequestParam(value = "pageSize", required = false) final Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<Library> libraries = libraryRepository.findLibraryByPrefix(libraryPrefixProvider.getLibPrefix(), paging);
        return new ResponseEntity<>(libraries, HttpStatus.OK);
    }

    @PostMapping("/add")
    public Library addLibrary(@RequestBody Library library) {
        return libraryRepository.save(library);
    }

    @PutMapping
    public ResponseEntity<Library> edit(@RequestBody Library library) {
        if (!libraryRepository.findById(library.get_id()).isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        try {
            Library editedLibrary = libraryRepository.save(library);
            return new ResponseEntity<>(editedLibrary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{libraryId}")
    public ResponseEntity<Boolean> deleteLibrary(@PathVariable String libraryId) {
        if (!libraryRepository.findById(libraryId).isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        try {
            libraryRepository.deleteById(libraryId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
