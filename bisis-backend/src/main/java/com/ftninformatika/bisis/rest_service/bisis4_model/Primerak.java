package com.ftninformatika.bisis.rest_service.bisis4_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Primerak implements java.io.Serializable {

  // Fields    

  //@Id private String _id;
  private int primerakID;
  private String invBroj;
  private String datumRacuna; //Date
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private String usmeravanje;
  private String datumInventarisanja; //Date
  private String sigFormat;
  private String sigPodlokacija;
  private String sigIntOznaka;
  private String sigDublet;
  private String sigNumerusCurens;
  private String sigUDK;
  private String povez;
  private String nacinNabavke;
  private String odeljenje;
  private String status;
  private String datumStatusa; //Date
  private String inventator;
  private int stanje;
  private String dostupnost;
  private String napomene;
  private int version;

  /** minimal constructor */
  public Primerak(int primerakIDd, String invBroj) {
    this.primerakID = primerakID;
    this.invBroj = invBroj;
  }


}
