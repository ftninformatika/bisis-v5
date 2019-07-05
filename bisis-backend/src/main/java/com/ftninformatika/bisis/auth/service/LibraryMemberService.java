package com.ftninformatika.bisis.auth.service;

import com.ftninformatika.bisis.circ.LibraryMember;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author badf00d21  5.7.19.
 */
@Service
public class LibraryMemberService {

    @Autowired LibraryMemberRepository libraryMemberRepository;

    public boolean emailExistAndActivated(String email) {
        if (email == null) return false;
        LibraryMember lm = libraryMemberRepository.findByUsername(email);
        if (lm != null && lm.getProfileActivated()) return false;
        return true;
    }

}
