package com.ftninformatika.bisisauthentication.controllers;

import com.ftninformatika.bisis.librarian.db.Authority;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisis.opac2.members.OpacMemberWrapper;
import com.ftninformatika.bisisauthentication.models.AuthenticationRequest;
import com.ftninformatika.bisisauthentication.models.AuthenticationResponse;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.bisisauthentication.repositories.LibrarianRepository;
import com.ftninformatika.bisisauthentication.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisisauthentication.repositories.LibraryMemberRepository;
import com.ftninformatika.bisisauthentication.repositories.MemberRepository;
import com.ftninformatika.bisisauthentication.security.BisisUserDetailsService;
import com.ftninformatika.bisisauthentication.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BisisAuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BisisUserDetailsService userDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    @Qualifier("libraryMemberAuthenticationRepository")
    LibraryMemberRepository libraryMemberRepository;

    @Autowired
    @Qualifier("memberAuthenticationRepository")
    MemberRepository memberRepository;

    @Autowired
    @Qualifier("libraryConfigurationAuthenticationRepository")
    LibraryConfigurationRepository libraryConfigurationRepository;

    @Autowired
    @Qualifier("librarianAuthenticationRepository")
    LibrarianRepository librarianRepository;



    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return new ResponseEntity<>("Disabled user", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        final BisisUserDetailsImpl userDetails = (BisisUserDetailsImpl)userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setName(userDetails.getName());
        authenticationResponse.setUsername(userDetails.getUsername());
        authenticationResponse.setLibrary(userDetails.getLibrary());
        authenticationResponse.setDepartment(userDetails.getDepartment());
        authenticationResponse.setSublocation(userDetails.getCircDepartment());
        authenticationResponse.setAuthorities(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        authenticationResponse.setRoles(userDetails.getRoles());
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping(value = "/memauth")
    public ResponseEntity<?> opacAuthenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return new ResponseEntity<>("Disabled user", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        final BisisUserDetailsImpl userDetails = (BisisUserDetailsImpl)userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        Optional<LibraryMember> libraryMemberOptional = libraryMemberRepository.findByUsername(userDetails.getUsername());
        if (libraryMemberOptional.isPresent()) {
            LibraryMember libraryMember = libraryMemberOptional.get();
            OpacMemberWrapper retVal = getOpacWrapperMember(libraryMember);
            libraryMember.setAuthToken(token);
            libraryMember.setLastActivity(new Date());
            libraryMemberRepository.save(libraryMember);
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // piece od Petar's shit
    public OpacMemberWrapper getOpacWrapperMember(LibraryMember libraryMember) {
        List<String> allPrefixes = libraryConfigurationRepository.findAll()
                .stream().map(LibraryConfiguration::getLibraryName).collect(Collectors.toList());
        if (libraryMember == null || libraryMember.getLibraryPrefix() == null
                || !allPrefixes.contains(libraryMember.getLibraryPrefix()))
            return null;
        OpacMemberWrapper retVal = new OpacMemberWrapper();
        if (libraryMember.getAuthorities().contains(Authority.ROLE_USER)) {
            Optional<Member> member = memberRepository.findById(libraryMember.getIndex());
            if (member.isPresent()) retVal.setMember(member.get());
            else return null;
        }
        else if (libraryMember.getAuthorities().contains(Authority.ROLE_ADMIN)) {
            Optional<LibrarianDB> librarianDTO = librarianRepository.findById(libraryMember.getLibrarianIndex());
            if (librarianDTO.isPresent()) {
                Member tmpMem = new Member();
                LibrarianDB librarian = librarianDTO.get();
                tmpMem.setFirstName(librarian.getIme());
                tmpMem.setLastName(librarian.getPrezime());
                tmpMem.setAddress("");
                tmpMem.setUserId("ADMIN");
                retVal.setMember(tmpMem);
            }
            else return null;
        }
        libraryMember.setPassword(null);
        retVal.setLibraryMember(libraryMember);
        return retVal;
    }


}
