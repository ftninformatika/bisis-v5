package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Petar on 8/8/2017.
 */

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    @Autowired
    private LibrarianRepository librarianRepository;


    @RequestMapping(value = "/getByUsername")
    public LibrarianDTO getByUsername(@RequestParam (value = "username") String username){
        LibrarianDTO retVal = null;

        retVal = librarianRepository.getByUsername(username);

        return retVal;
    }

    /*@RequestMapping( method = RequestMethod.GET )
    public List<Librarian> getLibrarians(){

        return librarianRepository.findAll();
    }*/

    @RequestMapping( path ="/getByLibrary" ,method = RequestMethod.GET )
    public List<LibrarianDTO> getLibrariansForLibrary(@RequestParam (value="library") String libName){

        return librarianRepository.getLibrariansByBiblioteka(libName);
    }

}
