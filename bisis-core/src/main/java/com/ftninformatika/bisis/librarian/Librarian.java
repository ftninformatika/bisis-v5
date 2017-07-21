package com.ftninformatika.bisis.librarian;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class Librarian {

  @Id private String _id;
  private String username;
  private String password;
  private String ime;
  private String prezime;
  private String email;
  private String napomena;
  private boolean obrada;
  private boolean cirkulacija;
  private boolean administracija;
  private LibrarianContext context = new LibrarianContext();
  private String biblioteka;
  private ProcessType curentProcessType;

}
