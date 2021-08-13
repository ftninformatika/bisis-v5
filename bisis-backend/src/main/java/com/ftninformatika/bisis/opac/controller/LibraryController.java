package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Library;
import com.ftninformatika.bisis.opac.repository.LibraryRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Library> getLibraries(){
        return libraryRepository.findLibraryByPrefix(libraryPrefixProvider.getLibPrefix());
    }
    @PostMapping("/add")
    public Library addLibrary(@RequestBody Library library){
        return libraryRepository.save(library);
    }
}
