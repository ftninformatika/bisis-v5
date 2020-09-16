package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.librarian.LibrarianManager;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.LibrarianRoleDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.rest_service.repository.mongo.Librarian2Repository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRoleRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ProcessType2Repository;
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
    @Autowired private ProcessTypeRepository proctypeRep;
    @Autowired private ProcessType2Repository proctype2Rep;
    @Autowired private Librarian2Repository librarian2Repository;
    @Autowired private LibrarianRoleRepository librarianRoleRepository;

//    @GetMapping("/getByUsername")
//    public LibrarianDTO getByUsername(@RequestParam (value = "username") String username){
//        LibrarianDTO retVal = null;
//
//        retVal = librarianRepository.getByUsername(username);
//
//      //Moraju se uzeti tipovi obrade iz sifarnika jer su oni azurirani
//      String libName = retVal.getBiblioteka();
//        if (retVal.getCurentProcessType()!=null){
//          String curentPT = retVal.getCurentProcessType().getName();
//          if (curentPT !=null){
//            retVal.setCurentProcessType(proctypeRep.findByNameAndLibName(curentPT,libName));
//          }
//        }
//
//        if (retVal.getContext().getDefaultProcessType() != null) {
//            String defaultPT = retVal.getContext().getDefaultProcessType().getName();
//            if (defaultPT != null) {
//                retVal.getContext().setDefaultProcessType(proctypeRep.findByNameAndLibName(defaultPT, libName));
//            }
//        }
//        List <ProcessTypeDTO> processTypes = retVal.getContext().getProcessTypes();
//        ArrayList <ProcessTypeDTO> newProcTypes = new ArrayList<ProcessTypeDTO>();
//        for(ProcessTypeDTO pt:processTypes){
//            ProcessTypeDTO processTypeDTO = null;
//            try{
//                processTypeDTO = proctypeRep.findByNameAndLibName(pt.getName(),libName);
//            }
//            catch (Exception e) {
//                processTypeDTO = null;
//                e.printStackTrace();
//            }
//            if (processTypeDTO != null)
//                newProcTypes.add(processTypeDTO);
//        }
//        retVal.getContext().setProcessTypes(newProcTypes);
//
//
//        return retVal;
//    }

    @GetMapping("/getByUsername")
    public LibrarianDB getByUsername(@RequestParam (value = "username") String username){
        return librarian2Repository.getByUsername(username);
    }


    // migration

    @GetMapping("/insertLibrarianRoles")
    public void insertLibrarianRoles(){
        String[] roles = {"obrada", "cirkulacija", "administracija", "redaktor", "inventator", "cirkulacijaPlus", "opacAdmin", "deziderati", "nabavka"};
        for (int i = 0; i < roles.length; i++) {
            LibrarianRoleDB librarianRoleDB = new LibrarianRoleDB();
            librarianRoleDB.setName(roles[i]);
            librarianRoleRepository.save(librarianRoleDB);
        }
    }

    @GetMapping("/migrateLibrarians")
    public void migrate(){
        List<LibrarianDTO> librarians = librarianRepository.findAll();
        librarians.forEach(librarianDTO -> {
            LibrarianDB librarianDB = LibrarianManager.initializeLibrarianDBFromDTO(librarianDTO);
            String libName = librarianDB.getBiblioteka();
            if (librarianDB.getCurentProcessType()!=null){
                String curentPT = librarianDB.getCurentProcessType().getName();
                if (curentPT !=null){
                    ProcessTypeDB processTypeDB = proctype2Rep.findByNameAndLibName(curentPT,libName);
                    if (processTypeDB == null) {
                        System.out.println("Process type not found: " + curentPT + " " + libName + ". Librarian: " + librarianDB.getUsername());
                    }
                    librarianDB.setCurentProcessType(processTypeDB);
                }
            }

            if (librarianDB.getContext().getDefaultProcessType() != null) {
                String defaultPT = librarianDB.getContext().getDefaultProcessType().getName();
                if (defaultPT != null) {
                    ProcessTypeDB processTypeDB = proctype2Rep.findByNameAndLibName(defaultPT,libName);
                    if (processTypeDB == null) {
                        System.out.println("Process type not found: " + defaultPT + " " + libName + ". Librarian: " + librarianDB.getUsername());
                    }
                    librarianDB.getContext().setDefaultProcessType(processTypeDB);
                }
            }
            List <ProcessTypeDB> processTypes = librarianDB.getContext().getProcessTypes();
            ArrayList <ProcessTypeDB> newProcTypes = new ArrayList<ProcessTypeDB>();
            for(ProcessTypeDB pt:processTypes){
                ProcessTypeDB processTypeDB = proctype2Rep.findByNameAndLibName(pt.getName(),libName);
                if (processTypeDB == null) {
                    System.out.println("Process type not found: " + pt.getName() + " " + libName + ". Librarian: " + librarianDB.getUsername());
                }
                if (processTypeDB != null)
                    newProcTypes.add(processTypeDB);
            }
            librarianDB.getContext().setProcessTypes(newProcTypes);

            librarian2Repository.save(librarianDB);
        });
    }

    // migration


    @RequestMapping( path ="/getByLibrary" ,method = RequestMethod.GET )
    public List<LibrarianDB> getLibrariansForLibrary(@RequestParam (value="library") String libName){

//        return librarian2Repository.getLibrariansByBiblioteka(libName).stream().
//                map(i -> new Librarian(i)).
//                collect(Collectors.toList());
        return librarian2Repository.getLibrariansByBiblioteka(libName);
    }

    @RequestMapping( value = "/update", method = RequestMethod.POST)
    public Boolean createUpdateLibrarian(@RequestBody LibrarianDB lib){
        lib.setAuthorities(Arrays.asList(new Authority[]{Authority.ROLE_ADMIN}));
        librarian2Repository.save(lib);

        return true;
    }

    @RequestMapping( value = "/delete", method = RequestMethod.POST)
    public Boolean deleteLibrarian(@RequestBody LibrarianDB lib){
        LibrarianDB librarian = librarian2Repository.getByUsername(lib.getUsername());
        if (librarian == null)
            return false;
        librarian2Repository.delete(librarian);
        return true;
    }

}
