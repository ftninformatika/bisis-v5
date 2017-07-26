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
