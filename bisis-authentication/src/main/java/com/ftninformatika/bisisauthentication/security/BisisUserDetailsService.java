package com.ftninformatika.bisisauthentication.security;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.bisisauthentication.repositories.LibrarianRepository;
import com.ftninformatika.bisisauthentication.repositories.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BisisUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("librarianAuthenticationRepository")
    LibrarianRepository librarianRepository;

    @Autowired
    @Qualifier("libraryMemberAuthenticationRepository")
    LibraryMemberRepository libraryMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibrarianDB> librarianDB = librarianRepository.findByUsername(username);
        BisisUserDetailsImpl bisisUserDetails = null;
        if (librarianDB.isPresent()) {
            bisisUserDetails = new BisisUserDetailsImpl(librarianDB.get());
        } else {
            Optional<LibraryMember> libraryMember = libraryMemberRepository.findByUsername(username);
            if (libraryMember.isPresent()) {
                bisisUserDetails = new BisisUserDetailsImpl(libraryMember.get());
            }
        }
        if (bisisUserDetails != null) {
            return bisisUserDetails;
        } else {
            throw new UsernameNotFoundException("Not found: " + username);
        }
    }
}
