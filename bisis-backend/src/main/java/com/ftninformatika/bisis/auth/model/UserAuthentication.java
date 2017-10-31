package com.ftninformatika.bisis.auth.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserAuthentication implements Authentication {

    private static final long serialVersionUID = -7170337143687707450L;

    private final LibrarianUser user;
    private boolean authenticated = true;

    public UserAuthentication(final LibrarianUser librarianUser) {
        this.user = librarianUser;
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

    public LibrarianUser getUser() {
        return user;
    }
}
