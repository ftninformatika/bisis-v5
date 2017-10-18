package com.ftninformatika.bisis.records;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Sveska implements Serializable{

  private int sveskaID;
  private String invBroj;
  private String status;
  private Date datumStatusa;
  private String signatura;
  private BigDecimal cena;
  private String brojSveske;
  private String knjiga;
  private String inventator;
  @JsonIgnore
  @Transient
  private Godina parent;  
  private int version;

  public Sveska() {
  }
  
  /*public Sveska(int sveskaID, String invBroj, String status, String signatura,
      BigDecimal cena, String brojSveske, Godina parent, int stanje) {
    this.sveskaID = sveskaID;
    this.invBroj = invBroj;
    this.status = status;
    this.signatura = signatura;
    this.cena = cena;
    this.brojSveske = brojSveske;
    this.parent = parent;
    this.stanje = stanje;
  }*/

  public String getBrojSveske() {
    return brojSveske;
  }
  public void setBrojSveske(String brojSveske) {
    this.brojSveske = brojSveske;
  }
  public BigDecimal getCena() {
    return cena;
  }
  public void setCena(BigDecimal cena) {
    this.cena = cena;
  }
  public String getInvBroj() {
    return invBroj;
  }
  public void setInvBroj(String invBroj) {
    this.invBroj = invBroj;
  }
  public String getSignatura() {
    return signatura;
  }
  public void setSignatura(String signatura) {
    this.signatura = signatura;
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
  public Godina getParent() {
    return parent;
  }
  public void setParent(Godina parent) {
    this.parent = parent;
  }
  public int getSveskaID() {
    return sveskaID;
  }
  public void setSveskaID(int sveskaID) {
    this.sveskaID = sveskaID;
  }
  public int getVersion() {
    return version;
  }
  public void setVersion(int version) {
    this.version = version;
  }

  public String getKnjiga() {
		return knjiga;
	}

	public void setKnjiga(String knjiga) {
		this.knjiga = knjiga;
	}
	
	public String getInventator() {
		return inventator;
	}

	public void setInventator(String inventator) {
		this.inventator = inventator;
	}
  
  public Sveska copy(){
  	Sveska svRet = new Sveska();
  	svRet.setBrojSveske(getBrojSveske());
  	svRet.setCena(getCena());
  	svRet.setDatumStatusa(getDatumStatusa());
  	svRet.setInvBroj(getInvBroj());
  	svRet.setParent(getParent());
  	svRet.setSignatura(getSignatura());
  	svRet.setStatus(getStatus());
  	svRet.setSveskaID(getSveskaID());
  	svRet.setVersion(getVersion());
  	svRet.setKnjiga(getKnjiga());
  	svRet.setInventator(getInventator());
  	return svRet;  	
  }

	
	
	 public String toString() {
	    return ReflectionToStringBuilder.toString(this);
	  }

	
 }


