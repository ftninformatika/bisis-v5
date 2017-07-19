package com.ftninformatika.bisis.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Primerak implements Serializable {

  private int primerakID;
  private String invBroj;
  private Date datumRacuna; //date
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private String usmeravanje;
  private Date datumInventarisanja; //bio Date
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
  private Date datumStatusa;//date
  private String inventator;
  private int stanje;
  private String dostupnost;
  private String napomene;
  private int version = 0;

  public String getDatumStatusa(){
    if (this.datumStatusa == null)
      return null;

    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sss+0000"); // Quoted "Z" to indicate UTC, no timezone offset
    df.setTimeZone(tz);
    String nowAsISO = df.format(this.datumStatusa);
    return nowAsISO;
  }

//  public Primerak() {
//    version = 0;
//  }
//
  public Primerak(int primerakID, String invBroj, Date datumRacuna,
      String brojRacuna, String dobavljac, BigDecimal cena, String finansijer,
      String usmeravanje, Date datumInventarisanja, String sigFormat,
      String sigPodlokacija, String sigIntOznaka, String sigDublet,
      String sigNumerusCurens, String sigUDK, String povez,
      String nacinNabavke, String odeljenje, String status, Date datumStatusa,
      String dostupnost, String napomene, int stanje, String inventator) {
    this.primerakID = primerakID;
    this.invBroj = invBroj;
    this.datumRacuna = datumRacuna;
    this.brojRacuna = brojRacuna;
    this.dobavljac = dobavljac;
    this.cena = cena;
    this.finansijer = finansijer;
    this.usmeravanje = usmeravanje;
    this.datumInventarisanja = datumInventarisanja;
    this.sigFormat = sigFormat;
    this.sigPodlokacija = sigPodlokacija;
    this.sigIntOznaka = sigIntOznaka;
    this.sigDublet = sigDublet;
    this.sigNumerusCurens = sigNumerusCurens;
    this.sigUDK = sigUDK;
    this.povez = povez;
    this.nacinNabavke = nacinNabavke;
    this.odeljenje = odeljenje;
    this.status = status;
    this.datumStatusa = datumStatusa;
    this.dostupnost = dostupnost;
    this.napomene = napomene;
    this.stanje = stanje;
    this.inventator = inventator;
    this.version = 0;
  }
  
  public boolean isSigDefined() {
    return 
      sigFormat != null ||
      sigPodlokacija != null ||
      sigIntOznaka != null ||
      sigDublet != null ||
      sigNumerusCurens != null ||
      sigUDK != null;
  }

  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  public Primerak copy(){
  	return new Primerak(getPrimerakID(), getInvBroj(), getDatumRacuna(),
        getBrojRacuna(), getDobavljac(), getCena(), getFinansijer(),
        getUsmeravanje(), getDatumInventarisanja(), getSigFormat(),
        getSigPodlokacija(), getSigIntOznaka(), getSigDublet(),
        getSigNumerusCurens(),getSigUDK(), getPovez(),
        getNacinNabavke(),getOdeljenje(), getStatus(), this.datumStatusa,
        getDostupnost(), getNapomene(), getStanje(),getInventator());
  }


}
