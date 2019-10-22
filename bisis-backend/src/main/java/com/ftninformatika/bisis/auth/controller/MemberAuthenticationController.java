package com.ftninformatika.bisis.auth.controller;

import com.ftninformatika.bisis.auth.dto.LoginDTO;
import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.auth.security.service.TokenService;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.opac2.members.OpacMemberWrapper;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibrarianRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import com.ftninformatika.bisis.rest_service.service.implementations.LibraryMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Petar on 10/13/2017.
 */
@RestController
@RequestMapping("/memauth")
public class MemberAuthenticationController {

    @Value("security.token.secret.key")
    private String secretKey;

    private final TokenService tokenService;
    @Autowired LibraryMemberService libraryMemberService;

    @Autowired
    public MemberAuthenticationController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired LibraryMemberRepository libraryMemberRepository;
    @Autowired LibrarianRepository librarianRepository;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO loginDTO) {
        String token = tokenService.getMemberToken(loginDTO.getUsername(), loginDTO.getPassword());
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(loginDTO.getUsername());
        if (token == null || libraryMember == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (!BCrypt.checkpw(loginDTO.getPassword(), libraryMember.getPassword()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        OpacMemberWrapper retVal = libraryMemberService.getOpacWrapperMember(libraryMember);
        if (retVal == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

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
            LibrarianDTO librarian = librarianRepository.findById(libraryMember.getLibrarianIndex()).get();
            librarian.setOpacAdmin(true);
            librarianRepository.save(librarian);
        }
        libraryMember.setActivationToken(null);
        libraryMember.setProfileActivated(true);
        libraryMemberRepository.save(libraryMember);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
