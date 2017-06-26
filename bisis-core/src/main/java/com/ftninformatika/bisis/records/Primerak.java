package com.ftninformatika.bisis.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Primerak implements Serializable {

  private int primerakID;
  private String invBroj;
  private Date datumRacuna;
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private String usmeravanje;
  private Date datumInventarisanja;
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
  private Date datumStatusa;
  private String inventator;
  private int stanje;
  private String dostupnost;
  private String napomene;
  private int version;

  public Primerak() {
    version = 0;
  }
  
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

  public String getBrojRacuna() {
    return brojRacuna;
  }
  public void setBrojRacuna(String brojRacuna) {
    this.brojRacuna = brojRacuna;
  }
  public BigDecimal getCena() {
    return cena;
  }
  public void setCena(BigDecimal cena) {
    this.cena = cena;
  }
  public Date getDatumInventarisanja() {
    return datumInventarisanja;
  }
  public void setDatumInventarisanja(Date datumInventarisanja) {
    this.datumInventarisanja = datumInventarisanja;
  }
  public Date getDatumRacuna() {
    return datumRacuna;
  }
  public void setDatumRacuna(Date datumRacuna) {
    this.datumRacuna = datumRacuna;
  }
  public String getDobavljac() {
    return dobavljac;
  }
  public void setDobavljac(String dobavljac) {
    this.dobavljac = dobavljac;
  }
  public String getFinansijer() {
    return finansijer;
  }
  public void setFinansijer(String finansijer) {
    this.finansijer = finansijer;
  }
  public String getInvBroj() {
    return invBroj;
  }
  public void setInvBroj(String invBroj) {
    this.invBroj = invBroj;
  }
  public String getNacinNabavke() {
    return nacinNabavke;
  }
  public void setNacinNabavke(String nacinNabavke) {
    this.nacinNabavke = nacinNabavke;
  }
  public String getOdeljenje() {
    return odeljenje;
  }
  public void setOdeljenje(String odeljenje) {
    this.odeljenje = odeljenje;
  }
  public String getPovez() {
    return povez;
  }
  public void setPovez(String povez) {
    this.povez = povez;
  }
  public int getPrimerakID() {
    return primerakID;
  }
  public void setPrimerakID(int primerakID) {
    this.primerakID = primerakID;
  }
  public String getSigDublet() {
    return sigDublet;
  }
  public void setSigDublet(String sigDublet) {
    this.sigDublet = sigDublet;
  }
  public String getSigFormat() {
    return sigFormat;
  }
  public void setSigFormat(String sigFormat) {
    this.sigFormat = sigFormat;
  }
  public String getSigIntOznaka() {
    return sigIntOznaka;
  }
  public void setSigIntOznaka(String sigIntOznaka) {
    this.sigIntOznaka = sigIntOznaka;
  }
  public String getSigNumerusCurens() {
    return sigNumerusCurens;
  }
  public void setSigNumerusCurens(String sigNumerusCurens) {
    this.sigNumerusCurens = sigNumerusCurens;
  }
  public String getSigPodlokacija() {
    return sigPodlokacija;
  }
  public void setSigPodlokacija(String sigPodlokacija) {
    this.sigPodlokacija = sigPodlokacija;
  }
  public String getSigUDK() {
    return sigUDK;
  }
  public void setSigUDK(String sigUDK) {
    this.sigUDK = sigUDK;
  }
  public String getStatus() {
    return status;
  }  
  public void setStatus(String status) {
    this.status = status;
  }
  public Date getDatumStatusa(){
  	return datumStatusa;
  }
  public void setDatumStatusa(Date datumStatusa){
  	this.datumStatusa = datumStatusa;
  }
  
  public String getDostupnost(){
    return dostupnost;
  }
  public void setDostupnost(String dostupnost){
    this.dostupnost = dostupnost;
  }  
  
  public String getUsmeravanje() {
    return usmeravanje;
  }
  public void setUsmeravanje(String usmeravanje) {
    this.usmeravanje = usmeravanje;
  }
  public String getNapomene() {
    return napomene;
  }
  public void setNapomene(String napomene) {
    this.napomene = napomene;
  }
  public int getVersion() {
    return version;
  }
  public void setVersion(int version) {
    this.version = version;
  }
  public int getStanje() {
  	return stanje;
  }
  public void setStanje(int stanje) {
    this.stanje = stanje;
  }  
  public String getInventator(){
  	return inventator;
  }  
  public void setInventator(String inventator){
  	this.inventator = inventator;
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
