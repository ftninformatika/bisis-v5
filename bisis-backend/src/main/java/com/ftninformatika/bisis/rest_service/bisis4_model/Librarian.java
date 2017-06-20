package com.ftninformatika.bisis.rest_service.bisis4_model;

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
  private int obrada;
  private int cirkulacija;
  private int administracija;
  private String context;
  private String biblioteka;

}
