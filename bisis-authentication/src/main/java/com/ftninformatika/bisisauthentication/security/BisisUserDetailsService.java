package com.ftninformatika.bisisauthentication.security;

import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import com.ftninformatika.bisisauthentication.models.BisisUserDetailsImpl;
import com.ftninformatika.bisisauthentication.repositories.LibrarianRepository;
import com.ftninformatika.bisisauthentication.repositories.LibraryMemberRepository;
import com.ftninformatika.bisisauthentication.repositories.MemberRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BisisUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("librarianAuthenticationRepository")
    LibrarianRepository librarianRepository;

    @Autowired
    @Qualifier("libraryMemberAuthenticationRepository")
    LibraryMemberRepository libraryMemberRepository;

    @Autowired
    @Qualifier("memberAuthenticationRepository")
    MemberRepository memberRepository;

    @Autowired
    LibraryPrefixProvider prefixProvider;

    String libraryFilter;
    boolean extractUserData = false;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<LibrarianDB> librarianDB = librarianRepository.findByUsername(username);
        BisisUserDetailsImpl bisisUserDetails = null;
        if (librarianDB.isPresent()) {
            if (libraryFilter == null || librarianDB.get().getBiblioteka().equals(libraryFilter)) {
                bisisUserDetails = new BisisUserDetailsImpl(librarianDB.get());
            }
        } else {
            Optional<LibraryMember> libraryMember = libraryMemberRepository.findByUsername(username);
            if (libraryMember.isPresent()) {
                if (libraryFilter == null || libraryMember.get().getLibraryPrefix().equals(libraryFilter)) {
                    LibraryMember lm = libraryMember.get();
                    bisisUserDetails = new BisisUserDetailsImpl(lm);
                    if (extractUserData) {
                        prefixProvider.setPrefix(lm.getLibraryPrefix());
                        Optional<Member> member = memberRepository.findById(lm.getIndex());
                        if (member.isPresent()) {
                            Boolean activeMember = false;
                            Member m = member.get();
                            for (Signing s : m.getSignings()) {
                                if (s.getUntilDate().after(new Date())) {
                                    activeMember = true;
                                }
                            }
                            if (activeMember) {
                                bisisUserDetails.setName(m.getFirstName());
                                bisisUserDetails.setSurname(m.getLastName());
                                if (m.getAge() != null) {
                                    if (m.getAge().equalsIgnoreCase("A")) {
                                        bisisUserDetails.setAge("adult");
                                    } else {
                                        bisisUserDetails.setAge("child");
                                    }
                                }
                            } else {
                                throw new UsernameNotFoundException("Not active member: " + username);
                            }
                        }
                    }
                }
            }
        }
        if (bisisUserDetails != null) {
            return bisisUserDetails;
        } else {
            throw new UsernameNotFoundException("Not found: " + username);
        }
    }

    public UserDetails loadUserByRefreshToken(String token) throws UsernameNotFoundException {
        Optional<LibraryMember> libraryMember = libraryMemberRepository.findByRefreshToken(token);
        if (libraryMember.isPresent()) {
            LibraryMember lm = libraryMember.get();
            BisisUserDetailsImpl bisisUserDetails = new BisisUserDetailsImpl(lm);
            return bisisUserDetails;
        } else {
            throw new UsernameNotFoundException("Not found: " + token);
        }
    }

    public void saveRefreshToken(String username, String refreshToken, String oldRefreshToken) {
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(username).get();
        if (oldRefreshToken != null) {
            libraryMember.getRefreshToken().remove(oldRefreshToken);
        }
        if (libraryMember.getRefreshToken() == null) {
            libraryMember.setRefreshToken(new ArrayList<String>());
        }
        libraryMember.getRefreshToken().add(refreshToken);
        libraryMemberRepository.save(libraryMember);
    }

    public void removeRefreshToken(String username, String refreshToken) {
        LibraryMember libraryMember = libraryMemberRepository.findByUsername(username).get();
        if (libraryMember.getRefreshToken() != null) {
            libraryMember.getRefreshToken().remove(refreshToken);
        }
        libraryMemberRepository.save(libraryMember);
    }


    public void setLibraryFilter(String library) {
        libraryFilter = library;
    }

    public void setExtractUserData(boolean extractUserData) {
        this.extractUserData = extractUserData;
    }
}
