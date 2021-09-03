package com.ftninformatika.bisisauthentication.models;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.opac.members.LibraryMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BisisUserDetailsImpl implements UserDetails {

    String id;
    String username;
    String password;
    String name;
    String surname;
    String birthday;
    Collection<? extends GrantedAuthority> authorities;
    String library;
    String defaultDepartment;
    String circDepartment;
    String clientType;
    List<String> roles;

    public BisisUserDetailsImpl(LibrarianDB librarianDB) {
        this.id = librarianDB.get_id();
        this.username = librarianDB.getUsername();
        this.password = librarianDB.getPassword();
        this.name = librarianDB.getIme();
        this.surname = librarianDB.getPrezime();
        this.birthday = "";
        this.authorities = librarianDB.getAuthorities();
        this.library = librarianDB.getBiblioteka();
        this.defaultDepartment = librarianDB.getDefaultDepartment();
        this.circDepartment = librarianDB.getCircDepartment();
        this.roles = librarianDB.getLibrarianRoles();
        this.clientType = "librarian";
    }

    public BisisUserDetailsImpl(LibraryMember libraryMember) {
        this.id = libraryMember.get_id();
        this.username = libraryMember.getUsername();
        this.password = libraryMember.getPassword();
        this.name = "";
        this.surname = "";
        this.birthday = "";
        this.authorities = libraryMember.getAuthorities();
        this.library = libraryMember.getLibraryPrefix();
        this.defaultDepartment = "";
        this.circDepartment = "";
        this.roles = new ArrayList<>();
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

    public String getFirstName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthday() {
        return birthday;
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

    public List<String> getRoles() {
        return this.roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
