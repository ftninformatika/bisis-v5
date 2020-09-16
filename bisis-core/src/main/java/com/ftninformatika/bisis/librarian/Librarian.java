package com.ftninformatika.bisis.librarian;

import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Librarian {

    @Id private String _id;
    private String username;
    private String password;
    private String biblioteka;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private List<String> librarianRoles = new ArrayList<>();
    private LibrarianContext context = new LibrarianContext();
    private ProcessType curentProcessType;
    //samo za bgb, ostali null
    private String defaultDepartment;
    private String circDepartment;

    public Librarian(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Librarian(LibrarianDB librarianDB) {
        this._id = librarianDB.get_id();
        this.username = librarianDB.getUsername();
        this.password = librarianDB.getPassword();
        this.biblioteka = librarianDB.getBiblioteka();
        this.ime = librarianDB.getIme();
        this.prezime = librarianDB.getPrezime();
        this.email = librarianDB.getEmail();
        this.napomena = librarianDB.getNapomena();
        this.librarianRoles = librarianDB.getLibrarianRoles();
        this.defaultDepartment = librarianDB.getDefaultDepartment();
        this.circDepartment = librarianDB.getCircDepartment();
        if (librarianDB.getCurentProcessType() != null) {
            this.curentProcessType = new ProcessType(librarianDB.getCurentProcessType());
        }
        if (librarianDB.getContext() != null) {
            this.context = new LibrarianContext(librarianDB.getContext());
        }
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

    public class Role {
        public static final String OBRADA = "obrada";
        public static final String CIRKULACIJA = "cirkulacija";
        public static final String ADMINISTRACIJA = "administracija";
        public static final String REDAKTOR = "redaktor";
        public static final String INVENTATOR = "inventator";
        public static final String CIRKULACIJAPLUS = "cirkulacijaPlus";
        public static final String OPACADMIN = "opacAdmin";
        public static final String DEZIDERATI = "deziderati";
        public static final String NABAVKA = "nabavka";
    }
}
