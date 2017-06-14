package com.ftninformatika.bisis.rest_service.bisis4_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bibliotekari  {

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

}
