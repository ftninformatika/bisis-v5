package com.ftninformatika.bisis.records;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@JsonIgnoreProperties( ignoreUnknown = true )
public class Godina implements Serializable{

  private int godinaID;
  private String invBroj;
  private Date datumRacuna;
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private Date datumInventarisanja;
  private String sigFormat;
  private String sigPodlokacija;
  private String sigIntOznaka;
  private String sigDublet;
  private String sigNumerusCurens;
  private String sigNumeracija;
  private String sigUDK;
  private String povez;
  private String nacinNabavke;
  private String odeljenje;
  private String napomene;
  private String godiste;
  private String godina;
  private String broj;
  private String dostupnost;
  private String inventator;
  private List<Sveska> sveske;
  
  public Godina() {
    sveske = new ArrayList<Sveska>();
  }

  public Godina(int godinaID, String invBroj, Date datumRacuna,
      String brojRacuna, String dobavljac, BigDecimal cena, String finansijer,
      Date datumInventarisanja, String sigFormat, String sigPodlokacija,
      String sigIntOznaka, String sigDublet, String sigNumerusCurens,
      String sigNumeracija, String sigUDK, String povez, String nacinNabavke,
      String odeljenje, String napomene, String godiste, String godina,
      String broj, String dostupnost, List<Sveska> sveske) {
    this();
    this.godinaID = godinaID;
    this.invBroj = invBroj;
    this.datumRacuna = datumRacuna;
    this.brojRacuna = brojRacuna;
    this.dobavljac = dobavljac;
    this.cena = cena;
    this.finansijer = finansijer;
    this.datumInventarisanja = datumInventarisanja;
    this.sigFormat = sigFormat;
    this.sigPodlokacija = sigPodlokacija;
    this.sigIntOznaka = sigIntOznaka;
    this.sigDublet = sigDublet;
    this.sigNumerusCurens = sigNumerusCurens;
    this.sigNumeracija = sigNumeracija;
    this.sigUDK = sigUDK;
    this.povez = povez;
    this.nacinNabavke = nacinNabavke;
    this.odeljenje = odeljenje;
    this.napomene = napomene;
    this.godiste = godiste;
    this.godina = godina;
    this.broj = broj;
    this.sveske.addAll(sveske);
  }

  public void add(Sveska s) {
    sveske.add(s);
  }
  
  public Sveska getSveska(int index) {
    if (index >= 0 && index < sveske.size())
      return sveske.get(index);
    else
      return null;
  }
  
  public void remove(Sveska s) {
    sveske.remove(s);
  }
  
  public int getSveskeCount() {
    return sveske.size();
  }
  
  public Iterator<Sveska> sveskeIterator() {
    return sveske.iterator();
  }
  
  public void clear() {
    sveske.clear();
  }
  
  public boolean isSigDefined() {
    return 
      sigFormat != null ||
      sigPodlokacija != null ||
      sigIntOznaka != null ||
      sigDublet != null ||
      sigNumerusCurens != null ||
      sigUDK != null ||
      sigNumeracija != null;
  }

  public String getBroj() {
    return broj;
  }

  public void setBroj(String broj) {
    this.broj = broj;
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

  public String getGodina() {
    return godina;
  }

  public void setGodina(String godina) {
    this.godina = godina;
  }

  public int getGodinaID() {
    return godinaID;
  }

  public void setGodinaID(int godinaID) {
    this.godinaID = godinaID;
  }

  public String getGodiste() {
    return godiste;
  }

  public void setGodiste(String godiste) {
    this.godiste = godiste;
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

  public String getNapomene() {
    return napomene;
  }

  public void setNapomene(String napomene) {
    this.napomene = napomene;
  }

  public String getOdeljenje(){
    if (odeljenje != null)
      return odeljenje;
    else
    if(invBroj != null && invBroj.substring(0,2) != null)
      return invBroj.substring(0,2);
    else
      return null;
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

  public String getSigNumeracija() {
    return sigNumeracija;
  }

  public void setSigNumeracija(String sigNumeracija) {
    this.sigNumeracija = sigNumeracija;
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
  public String getDostupnost() {
    return dostupnost;
  }

  public void setDostupnost(String dostupnost) {
    this.dostupnost = dostupnost;
  }
  
  public List<Sveska> getSveske() {
    return sveske;
  }

  public void setSveske(List<Sveska> sveske) {
    this.sveske = sveske;
  }
  
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }
  
  public String getInventator() {
		return inventator;
	}

	public void setInventator(String inventator) {
		this.inventator = inventator;
	}
	
  public Sveska getSveska(String invBroj){
	  for(Sveska s:sveske){
		  if(s.getInvBroj().equals(invBroj))
			  return s;
	  }
	  return null;
  }
  
  public Godina copy(){
	  Godina godRet = new Godina();
		godRet.setBroj(getBroj());
		godRet.setBrojRacuna(getBrojRacuna());
		godRet.setCena(getCena());
		godRet.setDatumInventarisanja(getDatumInventarisanja());
		godRet.setDatumRacuna(getDatumRacuna());
		godRet.setDobavljac(getDobavljac());
		godRet.setDostupnost(getDostupnost());
		godRet.setFinansijer(getFinansijer());
		godRet.setGodina(getGodina());
		godRet.setGodinaID(getGodinaID());
		godRet.setGodiste(getGodiste());
		godRet.setInvBroj(getInvBroj());
		godRet.setNacinNabavke(getNacinNabavke());
		godRet.setNapomene(getNapomene());
		godRet.setOdeljenje(getOdeljenje());
		godRet.setPovez(getPovez());
		godRet.setSigDublet(getSigDublet());
		godRet.setSigFormat(getSigFormat());
		godRet.setSigIntOznaka(getSigIntOznaka());
		godRet.setSigNumeracija(getSigNumeracija());
		godRet.setSigNumerusCurens(getSigNumerusCurens());
		godRet.setSigPodlokacija(getSigPodlokacija());
		godRet.setSigUDK(getSigUDK());  	
		godRet.setInventator(getInventator());
		for(Sveska s:getSveske())
			godRet.add(s.copy());			
		return godRet;
  }

	
	
}


