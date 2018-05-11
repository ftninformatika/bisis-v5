package com.ftninformatika.bisis.records;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class Primerak implements Serializable {

  private int primerakID;
  private String invBroj;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'")
  private Date datumRacuna;
  private String brojRacuna;
  private String dobavljac;
  private BigDecimal cena;
  private String finansijer;
  private String usmeravanje;
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'")
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
  @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm'Z'")
  private Date datumStatusa;
  private String inventator;
  private String dostupnost;
  private String napomene;
  private int version = 0;



//  public Primerak() {
//    version = 0;
//  }
//

  public String getOdeljenje(){
    if (odeljenje != null)
      return odeljenje;
    else
      if(invBroj != null && invBroj.substring(0,2) != null)
        return invBroj.substring(0,2);
    else
        return null;
  }

  public Primerak(int primerakID, String invBroj, Date datumRacuna,
      String brojRacuna, String dobavljac, BigDecimal cena, String finansijer,
      String usmeravanje, Date datumInventarisanja, String sigFormat,
      String sigPodlokacija, String sigIntOznaka, String sigDublet,
      String sigNumerusCurens, String sigUDK, String povez,
      String nacinNabavke, String odeljenje, String status, Date datumStatusa,
      String dostupnost, String napomene, String inventator) {
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
        getDostupnost(), getNapomene(), getInventator());
  }


}
