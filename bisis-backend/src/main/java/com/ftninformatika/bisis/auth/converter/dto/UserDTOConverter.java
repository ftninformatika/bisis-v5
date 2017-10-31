package com.ftninformatika.bisis.auth.converter.dto;

import com.ftninformatika.bisis.auth.dto.UserDTO;
import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.auth.model.LibrarianUser;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;


public class UserDTOConverter implements Converter<UserDTO, LibrarianUser> {

    @Override
    public LibrarianUser convert(final UserDTO dto) {
        final LibrarianUser librarianUser = new LibrarianUser();

        librarianUser.setUsername(dto.getUsername());
        librarianUser.setPassword(dto.getPassword());
        librarianUser.setAccountNonExpired(false);
        librarianUser.setCredentialsNonExpired(false);
        librarianUser.setEnabled(true);

        List<Authority> authorities = new ArrayList<>();
        authorities.add(Authority.ROLE_USER);
        librarianUser.setAuthorities(authorities);
        return librarianUser;
    }
}
