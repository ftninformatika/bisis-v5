package com.ftninformatika.bisisauthentication.models;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac2.members.LibraryMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class BisisUserDetailsImpl implements UserDetails {

    String id;
    String username;
    String password;
    String name;
    String surname;
    Collection<? extends GrantedAuthority> authorities;
    String library;
    String defaultDepartment;
    String circDepartment;
    String clientType;

    public BisisUserDetailsImpl(LibrarianDB librarianDB) {
        this.id = librarianDB.get_id();
        this.username = librarianDB.getUsername();
        this.password = librarianDB.getPassword();
        this.name = librarianDB.getIme();
        this.surname = librarianDB.getPrezime();
        this.authorities = librarianDB.getAuthorities();
        this.library = librarianDB.getBiblioteka();
        this.defaultDepartment = librarianDB.getDefaultDepartment();
        this.circDepartment = librarianDB.getCircDepartment();
        this.clientType = "librarian";
    }

    public BisisUserDetailsImpl(LibraryMember libraryMember) {
        this.id = libraryMember.get_id();
        this.username = libraryMember.getUsername();
        this.password = libraryMember.getPassword();
        this.name = "";
        this.surname = "";
        this.authorities = libraryMember.getAuthorities();
        this.library = libraryMember.getLibraryPrefix();
        this.defaultDepartment = "";
        this.circDepartment = "";
        this.clientType = "member";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return name + " " + surname;
    }

    public String getLibrary() {
        return library;
    }

    public String getDepartment() {
        return defaultDepartment;
    }

    public String getCircDepartment() {
        return circDepartment;
    }

    public String getClientType() {
        return clientType;
    }

    public String getID() {
        return id;
    }
}
