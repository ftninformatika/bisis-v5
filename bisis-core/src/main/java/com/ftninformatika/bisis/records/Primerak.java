package com.ftninformatika.bisis.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
@Getter
@Setter
public class Primerak implements Serializable {

  private int primerakID;
  private String invBroj;
  private String datumRacuna; //date
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private String usmeravanje;
  private String datumInventarisanja; //bio Date
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
  private String datumStatusa;//date
  private String inventator;
  private int stanje;
  private String dostupnost;
  private String napomene;
  private int version;

  public Primerak() {
    version = 0;
  }
  
  public Primerak(int primerakID, String invBroj, String datumRacuna,
      String brojRacuna, String dobavljac, BigDecimal cena, String finansijer,
      String usmeravanje, String datumInventarisanja, String sigFormat,
      String sigPodlokacija, String sigIntOznaka, String sigDublet,
      String sigNumerusCurens, String sigUDK, String povez,
      String nacinNabavke, String odeljenje, String status, String datumStatusa,
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
    version = 0;
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
        getNacinNabavke(),getOdeljenje(), getStatus(), getDatumStatusa(),
        getDostupnost(), getNapomene(), getStanje(),getInventator());
  }
}
