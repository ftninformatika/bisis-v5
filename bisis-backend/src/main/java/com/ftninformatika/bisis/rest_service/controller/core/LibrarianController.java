package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.librarian.LibrarianManager;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.LibrarianRoleDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.web.Librarian;
import com.ftninformatika.bisis.rest_service.repository.mongo.Librarian2Repository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRoleRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ProcessType2Repository;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.ProcessTypeRepository;
import com.ftninformatika.bisisauthentication.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;

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


    // migration

    @GetMapping("/insertLibrarianRoles")
    public void insertLibrarianRoles(){
        String[] roles = {"obrada", "cirkulacija", "administracija", "redaktor", "inventator", "cirkulacijaPlus", "opacAdmin", "deziderati", "nabavka", "RIS", "RISAdmin"};
        String[] springRoles = {"ROLE_ADMIN", "ROLE_ADMIN", "ROLE_ADMIN", "ROLE_ADMIN", "ROLE_ADMIN", "ROLE_ADMIN", "ROLE_ADMIN", "ROLE_DEZIDERATI", "ROLE_NABAVKA", "ROLE_RIS_USER", "ROLE_RIS_ADMIN"};
        for (int i = 0; i < roles.length; i++) {
            LibrarianRoleDB librarianRoleDB = new LibrarianRoleDB();
            librarianRoleDB.setName(roles[i]);
            librarianRoleDB.setSpringRole(springRoles[i]);
            librarianRoleRepository.save(librarianRoleDB);
        }
    }

    @GetMapping("/migrateLibrarians")
    public void migrate(){
        List<LibrarianDTO> librarians = librarianRepository.findAll();
        List<LibrarianRoleDB> librarianRoles = librarianRoleRepository.findAll();
        librarians.forEach(librarianDTO -> {
            LibrarianDB librarianDB = LibrarianManager.initializeLibrarianDBFromDTO(librarianDTO);
            librarianDB.setPassword(passwordEncoder.encode(librarianDTO.getPassword()));
            List<String> authorities = new ArrayList<>();
            librarianRoles.forEach(role -> {
                if (librarianDB.hasRole(role.getName())) {
                    if(authorities.indexOf(role.getSpringRole()) != -1) {
                        authorities.add(role.getSpringRole());
                    }
                }
            });

            //TODO konvertovai niz authorities u Authority objekte
            List<Authority> authorityList = authorities.stream().map(a ->Authority.valueOf(a)).collect(Collectors.toList());
            librarianDB.setAuthorities(authorityList);
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
        lib.setAuthorities(Arrays.asList(Authority.ROLE_ADMIN));
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


    // new

    @GetMapping("/getLibrarians")
    public ResponseEntity<?> getLibrarians(@RequestHeader(name="Authorization") String token, @RequestParam (value="library") String libName){
        String library = jwtUtil.extractLibrary(token.substring(7));
        if (library.equals(libName)) {
            List<Librarian> librarians = librarian2Repository.getLibrariansByBiblioteka(libName).stream().
                    map(Librarian::new).collect(Collectors.toList());
            return ResponseEntity.ok(librarians);
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }

    }

    @GetMapping("/getByUsername")
    public ResponseEntity<?> getByUsername(@RequestHeader(name="Authorization") String token, @RequestParam (value = "username") String username){
        String library = jwtUtil.extractLibrary(token.substring(7));
        LibrarianDB librarianDB = librarian2Repository.getByUsername(username);
        if (librarianDB.getBiblioteka().equals(library)) {
            return ResponseEntity.ok(librarianDB);
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }

    }

    @PostMapping("/hashPassword")
    public ResponseEntity<?> hashPassword(@RequestHeader(name="Authorization") String token, @RequestBody Librarian librarian){
        if (librarian != null) {
            String password = passwordEncoder.encode(librarian.getPassword());
            librarian.setPassword(password);
            return ResponseEntity.ok(librarian);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Empty request");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveLibrarian(@RequestHeader(name="Authorization") String token, @RequestBody LibrarianDB librarianDB){
        String library = jwtUtil.extractLibrary(token.substring(7));
        if (librarianDB.getBiblioteka().equals(library)) {
            if (librarianDB.get_id() == null) {
                String password = passwordEncoder.encode(librarianDB.getPassword());
                librarianDB.setPassword(password);
            }
            //TODO mapirati role na authorities
            librarianDB.setAuthorities(Arrays.asList(Authority.ROLE_ADMIN));
            librarianDB = librarian2Repository.save(librarianDB);
            return ResponseEntity.ok(librarianDB);
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }
    }
}
