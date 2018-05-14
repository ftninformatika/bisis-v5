package com.ftninformatika.bisis.librarian.dto;

import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.librarian.LibrarianContext;
import com.ftninformatika.bisis.librarian.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Petar on 8/10/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "librarians")
public class LibrarianDTO {

    @Id private String _id;
    private List<Authority> authorities;
    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private boolean obrada;
    private boolean cirkulacija;
    private boolean administracija;
    private LibrarianContextDTO context = new LibrarianContextDTO();
    private String biblioteka;
    private ProcessTypeDTO curentProcessType;

    //samo za bgb, ostali null
    private String defaultDepartment;
}
