package com.ftninformatika.bisis.librarian.db;

import com.ftninformatika.bisis.librarian.Librarian;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "librarians2")
public class LibrarianDB {
    @Id
    private String _id;
    private List<Authority> authorities;
    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private List<String> librarianRoles = new ArrayList<>();
    private LibrarianContextDB context = new LibrarianContextDB();
    private String biblioteka;
    @DBRef
    private ProcessTypeDB curentProcessType;

    //samo za bgb, ostali null
    private String defaultDepartment;
    private String circDepartment;


    public LibrarianDB(Librarian librarian) {
        this._id = librarian.get_id();
        this.username = librarian.getUsername();
        this.password = librarian.getPassword();
        this.biblioteka = librarian.getBiblioteka();
        this.ime = librarian.getIme();
        this.prezime = librarian.getPrezime();
        this.email = librarian.getEmail();
        this.napomena = librarian.getNapomena();
        this.librarianRoles = librarian.getLibrarianRoles();
        this.defaultDepartment = librarian.getDefaultDepartment();
        this.circDepartment = librarian.getCircDepartment();
        if (librarian.getCurentProcessType() != null) {
            this.curentProcessType = new ProcessTypeDB(librarian.getCurentProcessType());
        }
        if (librarian.getContext() != null) {
            this.context = new LibrarianContextDB(librarian.getContext());
        }
        this.authorities = Arrays.asList(new Authority[]{Authority.ROLE_ADMIN});
    }


    public boolean hasRole(String role) {
        boolean hasRole = false;
        for (String lr : librarianRoles) {
            if (lr.equals(role)) {
                hasRole = true;
            }
        }
        return hasRole;
    }

    public void setRole(String role) {
        if (librarianRoles == null) {
            librarianRoles = new ArrayList<>();
        }
        if (librarianRoles.indexOf(role) == -1) {
            librarianRoles.add(role);
        }
    }

}
