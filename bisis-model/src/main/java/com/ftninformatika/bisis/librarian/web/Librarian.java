package com.ftninformatika.bisis.librarian.web;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Librarian {

    private String _id;
    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private List<String> librarianRoles = new ArrayList<>();
    private String biblioteka;
    private String defaultDepartment;
    private String circDepartment;

    public Librarian(LibrarianDB librarianDB) {
        this._id = librarianDB.get_id();
        this.username = librarianDB.getUsername();
        this.biblioteka = librarianDB.getBiblioteka();
        this.ime = librarianDB.getIme();
        this.prezime = librarianDB.getPrezime();
        this.email = librarianDB.getEmail();
        this.napomena = librarianDB.getNapomena();
        this.librarianRoles = librarianDB.getLibrarianRoles();
        this.defaultDepartment = librarianDB.getDefaultDepartment();
        this.circDepartment = librarianDB.getCircDepartment();
    }
}
