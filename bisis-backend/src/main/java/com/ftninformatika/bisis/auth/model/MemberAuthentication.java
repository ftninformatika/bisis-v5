package com.ftninformatika.bisis.auth.model;

import com.ftninformatika.bisis.models.circ.LibraryMember;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Petar on 10/16/2017.
 */
@Getter
@Setter
public class MemberAuthentication implements Authentication{

    private static final long serialVersionUID = -7170337143687707450L;

    private final LibraryMember libraryMember;
    private boolean authenticated = true;

    public MemberAuthentication(LibraryMember m){
        this.libraryMember = m;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return libraryMember.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String getName() {
        return libraryMember.getUsername();
    }
}
