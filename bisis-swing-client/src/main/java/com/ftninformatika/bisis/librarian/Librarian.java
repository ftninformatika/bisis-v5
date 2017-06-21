package com.ftninformatika.bisis.librarian;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of={"username"})
public class Librarian {

    private String username;
    private String password;
    private String ime;
    private String prezime;
    private String email;
    private String napomena;
    private boolean obrada;
    private boolean cirkulacija;
    private boolean administracija;
    private String context;
    private String biblioteka;


    public boolean isCataloguing(){  //dasmanjimo refeaktorisanje koda
        return obrada;
    }
}
