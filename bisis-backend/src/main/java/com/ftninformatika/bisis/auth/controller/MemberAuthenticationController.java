package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.Librarian2Repository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Petar on 10/13/2017.
 */
@RestController
@RequestMapping("/memauth")
public class MemberAuthenticationController {

    @Value("security.token.secret.key")
    private String secretKey;

    //private final TokenService tokenService;
    @Autowired LibraryMemberService libraryMemberService;

//    @Autowired
//    public MemberAuthenticationController(final TokenService tokenService) {
//        this.tokenService = tokenService;
//    }

    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired
    Librarian2Repository librarianRepository;
    /**
     *
     * @param acitvateToken
     * @return
     */
    @PostMapping(value = "/activate-account")
    public ResponseEntity<?> activateOpacAccount(@RequestBody String acitvateToken) {
        if (acitvateToken == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LibraryMember libraryMember = libraryMemberRepository.findByActivationToken(acitvateToken);
        if (libraryMember == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // If admin account
        if (libraryMember.getAuthorities().contains(Authority.ROLE_ADMIN.getAuthority())) {
            LibrarianDB librarian = librarianRepository.findById(libraryMember.getLibrarianIndex()).get();
            librarian.setRole(Librarian.Role.OPACADMIN);
            librarianRepository.save(librarian);
        }
        libraryMember.setActivationToken(null);
        libraryMember.setProfileActivated(true);
        libraryMemberRepository.save(libraryMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
