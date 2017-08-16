package com.ftninformatika.bisis.librarian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "user")
//@JsonIgnoreProperties( ignoreUnknown = true )
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

  public Librarian(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public boolean isAdministration() { //lakse zbog starog koda!
      return this.administracija;
  }

  public boolean isCataloguing() {
    return this.obrada;
  }

  public boolean isCirculation() {
    return this.cirkulacija;
  }

  public void setAdministration(boolean administration) {
    this.administracija = administration;
  }

  public void setCataloguing(boolean cataloguing) {
    this.obrada = cataloguing;
  }

  public void setCirculation(boolean circulation) {
    this.cirkulacija = circulation;
  }
}
