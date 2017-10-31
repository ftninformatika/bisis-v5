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

    private final LibraryMember user;
    private boolean authenticated = true;

    public MemberAuthentication(LibraryMember m){
        this.user = m;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }


    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    public LibraryMember getUser() {
        return user;
    }

}
