package com.ftninformatika.bisis.opac.controller;

import com.ftninformatika.bisis.opac.Library;
import com.ftninformatika.bisis.opac.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libraries")
public class LibraryController {
    @Autowired
    LibraryRepository libraryRepository;
    @GetMapping("/get/{libPrefix}")
    public List<Library> getLibraries(@PathVariable("libPrefix") String libPrefix){
        return libraryRepository.findLibraryByPrefix(libPrefix);
    }
    @PostMapping("/add")
    public Library addLibrary(@RequestBody Library library){
        return libraryRepository.save(library);
    }
}
