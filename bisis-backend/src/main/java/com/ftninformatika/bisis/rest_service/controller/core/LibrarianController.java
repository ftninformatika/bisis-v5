package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.LibrarianRoleDB;
import com.ftninformatika.bisis.librarian.web.Librarian;
import com.ftninformatika.bisis.core.repositories.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRoleRepository;
import com.ftninformatika.bisisauthentication.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petar on 8/8/2017.
 */

@RestController
@RequestMapping("/librarians")
public class LibrarianController {

    @Autowired private LibrarianRepository librarianRepository;
    @Autowired private LibrarianRoleRepository librarianRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping( path ="/getByLibrary" ,method = RequestMethod.GET )
    public List<LibrarianDB> getLibrariansForLibrary(@RequestParam (value="library") String libName){

//        return librarian2Repository.getLibrariansByBiblioteka(libName).stream().
//                map(i -> new Librarian(i)).
//                collect(Collectors.toList());
        List<LibrarianDB> librarians = librarianRepository.getLibrariansByBiblioteka(libName, new Sort(Sort.Direction.ASC,"username"));
        return librarians;
    }

//    @RequestMapping( value = "/update", method = RequestMethod.POST)
//    public Boolean createUpdateLibrarian(@RequestBody LibrarianDB librarianDB){
//        List<LibrarianRoleDB> librarianRoles = librarianRoleRepository.findAll();
//        List<Authority> authorities = new ArrayList<Authority>();
//        authorities = librarianRoles.stream().
//                filter(role ->librarianDB.hasRole(role.getName())).
//                map(role ->Authority.valueOf(role.getSpringRole())).
//                distinct().
//                collect(Collectors.toList());
//
//        librarianDB.setAuthorities(authorities);
//        librarianRepository.save(librarianDB);
//
//        return true;
//    }



    // new

    @GetMapping("/getLibrarians")
    public ResponseEntity<?> getLibrarians(@RequestHeader(name="Authorization") String token, @RequestParam (value="library") String libName){
        String library = jwtUtil.extractLibrary(token.substring(7));
        if (library.equals(libName)) {
            //Sort sort = Sort.by(Sort.Order.by("username").ignoreCase());
            List<Librarian> librarians = librarianRepository.getLibrariansByBiblioteka(libName,new Sort(Sort.Direction.ASC,"username")).stream().
                    map(Librarian::new).collect(Collectors.toList());
            return ResponseEntity.ok(librarians);
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }

    }

    @GetMapping("/getByUsername")
    public ResponseEntity<?> getByUsername(@RequestHeader(name="Authorization") String token, @RequestParam (value = "username") String username){
        String library = jwtUtil.extractLibrary(token.substring(7));
        LibrarianDB librarianDB = librarianRepository.getByUsername(username);
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
        List<LibrarianRoleDB> librarianRoles = librarianRoleRepository.findAll();
        String library = jwtUtil.extractLibrary(token.substring(7));
        if (librarianDB.getBiblioteka().equals(library)) {
            List<Authority> authorities = librarianRoles.stream().
                    filter(role ->librarianDB.hasRole(role.getName())).
                    map(role ->Authority.valueOf(role.getSpringRole())).
                    distinct().
                    collect(Collectors.toList());
            librarianDB.setAuthorities(authorities);
            LibrarianDB newlibrarianDB = librarianRepository.save(librarianDB);
            return ResponseEntity.ok(newlibrarianDB);
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?>  deleteLibrarian(@RequestHeader(name="Authorization") String token, @RequestBody LibrarianDB librarianDB){
        String library = jwtUtil.extractLibrary(token.substring(7));
        if (librarianDB.getBiblioteka().equals(library)) {
            librarianRepository.delete(librarianDB);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("No permission!");
        }
    }

    @GetMapping("/getRoles")
    public List<LibrarianRoleDB> getRoles(){
        return librarianRoleRepository.findAll();
    }
}
