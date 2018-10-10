package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ProcessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Petar on 8/8/2017.
 */

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    @Autowired private LibrarianRepository librarianRepository;
    @Autowired
    private ProcessTypeRepository proctypeRep;

    @RequestMapping(value = "/getByUsername")
    public LibrarianDTO getByUsername(@RequestParam (value = "username") String username){
        LibrarianDTO retVal = null;

        retVal = librarianRepository.getByUsername(username);

       
        //Moraju se uzeti tipovi obrade iz sifarnika jer su oni azurirani
      String libName = retVal.getBiblioteka();
        if (retVal.getCurentProcessType()!=null){
          String curentPT = retVal.getCurentProcessType().getName();
          if (curentPT !=null){
            retVal.setCurentProcessType(proctypeRep.findByNameAndLibName(curentPT,libName));
          }
        }

        String defaultPT = retVal.getContext().getDefaultProcessType().getName();
        if (defaultPT !=null){
            retVal.getContext().setDefaultProcessType(proctypeRep.findByNameAndLibName(defaultPT,libName));
        }
        List <ProcessTypeDTO> processTypes = retVal.getContext().getProcessTypes();
        ArrayList <ProcessTypeDTO> newProcTypes = new ArrayList<ProcessTypeDTO>();
        for(ProcessTypeDTO pt:processTypes){
            newProcTypes.add(proctypeRep.findByNameAndLibName(pt.getName(),libName));
        }
        retVal.getContext().setProcessTypes(newProcTypes);


        return retVal;
    }


    @RequestMapping( path ="/getByLibrary" ,method = RequestMethod.GET )
    public List<LibrarianDTO> getLibrariansForLibrary(@RequestParam (value="library") String libName){

        return librarianRepository.getLibrariansByBiblioteka(libName);
    }

    @RequestMapping( value = "/update", method = RequestMethod.POST)
    public Boolean createUpdateLibrarian(@RequestBody LibrarianDTO lib){

        LibrarianDTO librarian = librarianRepository.getByUsername(lib.getUsername());

        // kreira novog
        if (librarian == null)
            librarian = new LibrarianDTO();

        //rola
        librarian.setAuthorities(Arrays.asList(new Authority[]{Authority.ROLE_ADMIN}));
        librarian.setUsername(lib.getUsername());
        librarian.setPassword(lib.getPassword());
        librarian.setIme(lib.getIme());
        librarian.setPrezime(lib.getPrezime());
        librarian.setEmail(lib.getEmail());
        librarian.setNapomena(lib.getNapomena());
        librarian.setObrada(lib.isObrada());
        librarian.setCirkulacija(lib.isCirkulacija());
        librarian.setAdministracija(lib.isAdministracija());
        librarian.setRedaktor(lib.isRedaktor());
        librarian.setInventator(lib.isInventator());
        librarian.setBiblioteka(lib.getBiblioteka());
        librarian.setContext(lib.getContext());
        librarian.setCurentProcessType(lib.getCurentProcessType());
        librarian.setDefaultDepartment(lib.getDefaultDepartment());
        librarian.setCircDepartment(lib.getCircDepartment());

        librarianRepository.save(librarian);

        return true;
    }

    @RequestMapping( value = "/delete", method = RequestMethod.POST)
    public Boolean deleteLibrarian(@RequestBody LibrarianDTO lib){

        LibrarianDTO librarian = librarianRepository.getByUsername(lib.getUsername());

        if (librarian == null)
            return false;

        librarianRepository.delete(librarian);
        return true;
    }

}
