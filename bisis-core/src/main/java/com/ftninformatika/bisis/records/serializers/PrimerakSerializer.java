package com.ftninformatika.bisis.records.serializers;

import com.ftninformatika.bisis.records.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


public class PrimerakSerializer {

  public static Field getField(Primerak p) {
    Field f = new Field("996");
  /*  UField uf = PubTypes.getFormat().getField("996");
    String ind1DefVal = uf.getInd1().getDefaultValue();
    if (isEmpty(ind1DefVal))
      ind1DefVal = " ";
    String ind2DefVal = uf.getInd2().getDefaultValue();
    if (isEmpty(ind2DefVal))
      ind2DefVal = " ";
    f.setInd1(ind1DefVal.charAt(0));
    f.setInd2(ind2DefVal.charAt(0));*/
    
    if (!isEmpty(p.getDatumRacuna()) || !isEmpty(p.getBrojRacuna())) {
      Subfield sf1 = new Subfield('1');
      if (!isEmpty(p.getBrojRacuna()))
        addSubsubfield(sf1, 'm', p.getBrojRacuna());
      if (!isEmpty(p.getDatumRacuna()))
        addSubsubfield(sf1, 'q', dateFormat.format(p.getDatumRacuna()));
      f.add(sf1);
    }
    
    if (!isEmpty(p.getDobavljac()))
      addSubfield(f, '2', p.getDobavljac());
    
    if (!isEmpty(p.getCena()))
      addSubfield(f, '3', p.getCena().toPlainString());
    
    if (!isEmpty(p.getFinansijer()))
      addSubfield(f, '4', p.getFinansijer());
    
    if (!isEmpty(p.getUsmeravanje()))
      addSubfield(f, '5', p.getUsmeravanje());
    
    Subfield sfd = new Subfield('d');
    f.add(sfd);
    if (!isEmpty(p.getSigDublet()))
      addSubsubfield(sfd, 'd', p.getSigDublet());
    if (!isEmpty(p.getSigFormat()))
      addSubsubfield(sfd, 'f', p.getSigFormat());
    if (!isEmpty(p.getSigIntOznaka()))
      addSubsubfield(sfd, 'i', p.getSigIntOznaka());
    if (!isEmpty(p.getSigPodlokacija()))
      addSubsubfield(sfd, 'l', p.getSigPodlokacija());
    if (!isEmpty(p.getSigNumerusCurens()))
      addSubsubfield(sfd, 'n', p.getSigNumerusCurens());
    if (!isEmpty(p.getSigUDK()))
      addSubsubfield(sfd, 'u', p.getSigUDK());
    
    if (!isEmpty(p.getInvBroj()))
      addSubfield(f, 'f', p.getInvBroj());
    
    if (!isEmpty(p.getDatumInventarisanja()))
      addSubfield(f, 'o', dateFormat.format(p.getDatumInventarisanja()));
    
    if (!isEmpty(p.getStatus()))
      addSubfield(f, 'q', p.getStatus());
    
    if (!isEmpty(p.getNapomene()))
      addSubfield(f, 'r', p.getNapomene());

    if (!isEmpty(p.getPovez()))
      addSubfield(f, 's', p.getPovez());
    
    if (!isEmpty(p.getNacinNabavke()))
      addSubfield(f, 'v', p.getNacinNabavke());

    if (!isEmpty(p.getOdeljenje()))
      addSubfield(f, 'w', p.getOdeljenje());
    
    if(!isEmpty(p.getDostupnost()))
      addSubfield(f, 'p', p.getDostupnost());
    
    if(!isEmpty(p.getDatumStatusa()))
    	addSubfield(f, 't', dateFormat.format(p.getDatumStatusa()));
    
    //dodato zbog mobilne aplikacije	
    //addSubfield(f, '9', String.valueOf(p.getStanje()));

    return f;
  }
  
  public static Primerak getPrimerak(Field f) {  	
    if (!f.getName().equals("996"))
      return null;
    Primerak p = new Primerak();
    boolean toLog = false;
    p.setBrojRacuna(getSubsubfieldContent(f, '1', 'm'));
    try {
      String s = getSubsubfieldContent(f, '1', 'q');
      if (s != null)
        p.setDatumRacuna(dateFormat.parse(s));
    } catch (Exception ex) {
      log.warn("Neispravan datum racuna: " + 
          getSubsubfieldContent(f, '1', 'q'));
      toLog = true;
    }
    p.setDobavljac(getSubfieldContent(f, '2'));
    String cena = getSubfieldContent(f, '3');
    try {
      if (cena != null) {
        cena = cena.replace(',', '.');
        BigDecimal num = new BigDecimal(cena);
        if (num.precision() <= 12 && 0 <= num.scale() && num.scale() <= 5){
          p.setCena(num);
        }else{
          p.setCena(null);
          log.warn("Neispravna cena: " + cena);
          toLog = true;
        }
//        if(cena.contains("E") || cena.contains("e")) throw new NumberFormatException();
      }
    } catch (NumberFormatException ex) {
      p.setCena(null);
      log.warn("Neispravna cena(exception) : " + cena);
      toLog = true;
    }
    p.setFinansijer(getSubfieldContent(f, '4'));
    p.setUsmeravanje(getSubfieldContent(f, '5'));
    p.setSigDublet(getSubsubfieldContent(f, 'd', 'd'));
    p.setSigFormat(getSubsubfieldContent(f, 'd', 'f'));   
    p.setSigIntOznaka(getSubsubfieldContent(f, 'd', 'i'));
    p.setSigPodlokacija(getSubsubfieldContent(f, 'd', 'l'));
    p.setSigNumerusCurens(getSubsubfieldContent(f, 'd', 'n'));
    p.setSigUDK(getSubsubfieldContent(f, 'd', 'u'));
    p.setInvBroj(getSubfieldContent(f, 'f'));
    String s = getSubfieldContent(f, 'o');
    if (s != null)
      try {
        p.setDatumInventarisanja(dateFormat.parse(s));
      } catch (Exception ex) {
        try {
          p.setDatumInventarisanja(dateFormat2.parse(s));
        } catch (Exception ex2) {
          log.warn("Neispravan datum inventarisanja: " + 
              getSubfieldContent(f, 'o'));
          toLog = true;
        }
      }
    p.setStatus(getSubfieldContent(f, 'q'));
    p.setNapomene(getSubfieldContent(f, 'r'));
    p.setPovez(getSubfieldContent(f, 's'));
    p.setNacinNabavke(getSubfieldContent(f, 'v'));
    p.setOdeljenje(getSubfieldContent(f, 'w'));
    p.setDostupnost(getSubfieldContent(f, 'p'));
    try{
    if (getSubfieldContent(f, '9') != null){
    	//p.setStanje(Integer.parseInt(getSubfieldContent(f, '9')));
    }else{
    	//p.setStanje(0);
    }
    }catch(NumberFormatException ex){
     log.warn("Neispravno stanje: " + 
     		getSubfieldContent(f, '9'));
     toLog = true;
     //p.setStanje(0);
    }
    try {
      String ds = getSubfieldContent(f, 't');
      if (ds != null){
        p.setDatumStatusa(dateFormat.parse(ds));
      }else {
    	  p.setDatumStatusa(null);
      }
    } catch (Exception ex) {
      log.warn("Neispravan datum statusa: " + 
      		getSubfieldContent(f, 't'));
      toLog = true;
      p.setDatumStatusa(null);
    }
    p.setVersion(0);
    if (toLog){
      log.warn("Primerak: "+ p.toString());
    }
    return p;
  }
  
  public static Field getField(Godina g) {
    Field f = new Field("997");
   // UField uf = PubTypes.getFormat().getField("997");
   /* String ind1DefVal = uf.getInd1().getDefaultValue();
    if (isEmpty(ind1DefVal))
      ind1DefVal = " ";
    String ind2DefVal = uf.getInd2().getDefaultValue();
    if (isEmpty(ind2DefVal))
      ind2DefVal = " ";
    f.setInd1(ind1DefVal.charAt(0));
    f.setInd2(ind2DefVal.charAt(0));*/
    
    if (!isEmpty(g.getDatumRacuna()) || !isEmpty(g.getBrojRacuna())) {
      Subfield sf1 = new Subfield('1');
      if (!isEmpty(g.getBrojRacuna()))
        addSubsubfield(sf1, 'm', g.getBrojRacuna());
      if (!isEmpty(g.getDatumRacuna()))
        addSubsubfield(sf1, 'q', dateFormat.format(g.getDatumRacuna()));
      f.add(sf1);
    }
    
    if (!isEmpty(g.getDobavljac()))
      addSubfield(f, '2', g.getDobavljac());
    
    if (!isEmpty(g.getCena()))
      addSubfield(f, '3', g.getCena().toPlainString());
    
    if (!isEmpty(g.getFinansijer()))
      addSubfield(f, '4', g.getFinansijer());
    
    Subfield sfd = new Subfield('d');
    f.add(sfd);
    if (!isEmpty(g.getSigDublet()))
      addSubsubfield(sfd, 'd', g.getSigDublet());
    if (!isEmpty(g.getSigFormat()))
      addSubsubfield(sfd, 'f', g.getSigFormat());
    if (!isEmpty(g.getSigIntOznaka()))
      addSubsubfield(sfd, 'i', g.getSigIntOznaka());
    if (!isEmpty(g.getSigPodlokacija()))
      addSubsubfield(sfd, 'l', g.getSigPodlokacija());
    if (!isEmpty(g.getSigNumerusCurens()))
      addSubsubfield(sfd, 'n', g.getSigNumerusCurens());
    if (!isEmpty(g.getSigNumeracija()))
      addSubsubfield(sfd, 's', g.getSigNumeracija());
    if (!isEmpty(g.getSigUDK()))
      addSubsubfield(sfd, 'u', g.getSigUDK());
    
    if (!isEmpty(g.getInvBroj()))
      addSubfield(f, 'f', g.getInvBroj());
    
    if (!isEmpty(g.getGodiste()))
      addSubfield(f, 'j', g.getGodiste());
    
    if (!isEmpty(g.getGodina()))
      addSubfield(f, 'k', g.getGodina());
    
    if (!isEmpty(g.getBroj()))
      addSubfield(f, 'm', g.getBroj());
    
    if (!isEmpty(g.getDatumInventarisanja()))
      addSubfield(f, 'o', dateFormat.format(g.getDatumInventarisanja()));
    
    if (!isEmpty(g.getNapomene()))
      addSubfield(f, 'r', g.getNapomene());

    if (!isEmpty(g.getPovez()))
      addSubfield(f, 's', g.getPovez());
    
    if (!isEmpty(g.getNacinNabavke()))
      addSubfield(f, 'v', g.getNacinNabavke());

    if (!isEmpty(g.getOdeljenje()))
      addSubfield(f, 'w', g.getOdeljenje());
    
    if (!isEmpty(g.getDostupnost()))
      addSubfield(f, 'p', g.getDostupnost());

    return f;
  }
  
  public static Godina getGodina(Field f) {  	
    if (!f.getName().equals("997"))
      return null;
    Godina g = new Godina();
    boolean toLog = false;
    g.setBrojRacuna(getSubsubfieldContent(f, '1', 'm'));
    try {
      String s = getSubsubfieldContent(f, '1', 'q');
      if (s != null)
        g.setDatumRacuna(dateFormat.parse(s));
    } catch (ParseException ex) {
      log.warn("Neispravan datum racuna: " + 
          getSubsubfieldContent(f, '1', 'q'));
      toLog = true;
    }
    g.setDobavljac(getSubfieldContent(f, '2'));
    String cena = getSubfieldContent(f, '3');
    try {
      if (cena != null) {
        cena = cena.replace(',', '.');
        BigDecimal num = new BigDecimal(cena);
        if (num.precision() <= 12 && num.scale() <= 2){
          g.setCena(num);
        }else{
          g.setCena(null);
          log.warn("Neispravna cena: " + cena);
          toLog = true;
        }
      }
    } catch (NumberFormatException ex) {
      g.setCena(null);
      log.warn("Neispravna cena: " + cena);
      toLog = true;
    }
    g.setFinansijer(getSubfieldContent(f, '4'));
    g.setSigDublet(getSubsubfieldContent(f, 'd', 'd'));
    g.setSigFormat(getSubsubfieldContent(f, 'd', 'f'));
    g.setSigIntOznaka(getSubsubfieldContent(f, 'd', 'i'));
    g.setSigPodlokacija(getSubsubfieldContent(f, 'd', 'l'));
    g.setSigNumerusCurens(getSubsubfieldContent(f, 'd', 'n'));
    g.setSigNumeracija(getSubsubfieldContent(f, 'd', 's'));
    g.setSigUDK(getSubsubfieldContent(f, 'd', 'u'));
    g.setInvBroj(getSubfieldContent(f, 'f'));
    g.setGodiste(getSubfieldContent(f, 'j'));
    g.setGodina(getSubfieldContent(f, 'k'));
    g.setBroj(getSubfieldContent(f, 'm'));
    String s = getSubfieldContent(f, 'o');
    if (s != null)
      try {
        g.setDatumInventarisanja(dateFormat.parse(s));
      } catch (ParseException ex) {
        try {
          g.setDatumInventarisanja(dateFormat2.parse(s));
        } catch (ParseException ex2) {
          log.warn("Neispravan datum inventarisanja: " + 
              getSubfieldContent(f, 'o'));
          toLog = true;
        }
      }
    g.setNapomene(getSubfieldContent(f, 'r'));
    g.setPovez(getSubfieldContent(f, 's'));
    g.setNacinNabavke(getSubfieldContent(f, 'v'));
    g.setOdeljenje(getSubfieldContent(f, 'w'));
    g.setDostupnost(getSubfieldContent(f, 'p'));
    if (toLog){
      log.warn("Godina: "+ g.toString());
    }
    return g;
  }
  
  public static Record primerciUPolja(Record rec) {
    for (Primerak p : rec.getPrimerci())
      rec.add(getField(p));
    rec.getPrimerci().clear();
    return rec;
  }
  
  public static Record poljaUPrimerke(Record rec) {  	
    String lastSigFormat = null;
    String lastSigPodlokacija = null;
    String lastSigIntOznaka = null;
    String lastSigDublet = null;
    String lastSigNumerusCurens = null;
    String lastSigUDK = null;
    Iterator<Field> it = rec.getFields("996").iterator();    
    while (it.hasNext()) {
      Field f = it.next();
      Primerak p = getPrimerak(f);
//      if (p.isSigDefined()) {
//        lastSigFormat = p.getSigFormat();
//        lastSigPodlokacija = p.getSigPodlokacija();
//        lastSigIntOznaka = p.getSigIntOznaka();
//        lastSigDublet = p.getSigDublet();
//        lastSigNumerusCurens = p.getSigNumerusCurens();
//        lastSigUDK = p.getSigUDK();
//      } else {
//        p.setSigFormat(lastSigFormat);
//        p.setSigPodlokacija(lastSigPodlokacija);
//        p.setSigIntOznaka(lastSigIntOznaka);
//        p.setSigDublet(lastSigDublet);
//        p.setSigNumerusCurens(lastSigNumerusCurens);
//        p.setSigUDK(lastSigUDK);
//      }
      rec.getPrimerci().add(p);
      rec.getFields().remove(f);
    }    
    return rec;
  }
  
  public static Record godineUPolja(Record rec) {
    for (Godina g : rec.getGodine())
      rec.add(getField(g));
    rec.getGodine().clear();
    return rec;
  }
  
  public static Record poljaUGodine(Record rec) {
    String lastSigFormat = null;
    String lastSigPodlokacija = null;
    String lastSigIntOznaka = null;
    String lastSigDublet = null;
    String lastSigNumerusCurens = null;
    String lastSigUDK = null;
    String lastSigNumeracija = null;
    Iterator<Field> it = rec.getFields("997").iterator();
    while (it.hasNext()) {
      Field f = it.next();
      Godina g = getGodina(f);
//      if (g.isSigDefined()) {
//        lastSigFormat = g.getSigFormat();
//        lastSigPodlokacija = g.getSigPodlokacija();
//        lastSigIntOznaka = g.getSigIntOznaka();
//        lastSigDublet = g.getSigDublet();
//        lastSigNumerusCurens = g.getSigNumerusCurens();
//        lastSigUDK = g.getSigUDK();
//        lastSigNumeracija = g.getSigNumeracija();
//      } else {
//        g.setSigFormat(lastSigFormat);
//        g.setSigPodlokacija(lastSigPodlokacija);
//        g.setSigIntOznaka(lastSigIntOznaka);
//        g.setSigDublet(lastSigDublet);
//        g.setSigNumerusCurens(lastSigNumerusCurens);
//        g.setSigUDK(lastSigUDK);
//        g.setSigNumeracija(lastSigNumeracija);
//      }
      rec.getGodine().add(g);
      //removeInventarFrom997(f);
      rec.getFields().remove(f);
    }
    
    return rec;
  }
  
  /*
   * iz polja 997 izbacuje inventarne podatke 
   * (inv broj, status, nacin nabavke,...)
   * a ostavlja podatke koji su vezani za obradu
   * (godiste, brojevi sveska, knjige,...) 
   * 
   */
  public static void removeInventarFrom997(Field f){  	
  		f.removeSubfield('1');
  		f.removeSubfield('2');
  		f.removeSubfield('3');
  		f.removeSubfield('4');
  		f.removeSubfield('d');  		
  		f.removeSubfield('f');
  		f.removeSubfield('r');
  		f.removeSubfield('s');
  		f.removeSubfield('v');
  		f.removeSubfield('w');
  		f.removeSubfield('p');
  		f.removeSubfield('o');
  }  
    
  public static Record polje000UMetapodatke(Record rec) {
    Field f = rec.getField("000");
    if (f == null)
      return rec;
    String sfa = rec.getSubfieldContent("000a");
    if (sfa != null) {
      // TODO: treba podrzati sve tipove publikacija
    	
    	// default tip publikacije
    	rec.setPubType(1);
    /*if (sfa.startsWith("001") || sfa.startsWith("002") || sfa.startsWith("003"))
        rec.setPubType(1);*/
      if (sfa.startsWith("004") || sfa.startsWith("005"))
        rec.setPubType(2);
      if (sfa.startsWith("006"))
        rec.setPubType(3);
    }
    String sfb = rec.getSubfieldContent("000b");
    if (sfb != null && sfb.length() == 16) {
      String crDate = sfb.substring(0, 8);
      String modDate = sfb.substring(8);
      /*try {
        rec.setCreationDate(dateFormat.parse(crDate));
        if (!"00000000".equals(modDate))
          rec.setLastModifiedDate(dateFormat.parse(modDate));
      } catch (ParseException ex) {
        log.warn("Unable to parse dates: " + sfb);
        log.warn(ex);
      }
    }else{
    	rec.setCreationDate(new Date());
    	rec.setLastModifiedDate(new Date());
    }
    String sfc = rec.getSubfieldContent("000c");
    if (sfc != null) {
      if (sfc.indexOf('@') != -1)
        rec.setCreator(new Author(sfc));
      else
        rec.setCreator(new Author(sfc, "unknown"));*/
    }
    String sfd = rec.getSubfieldContent("000d");
    if (sfd != null) {
      if (sfd.indexOf('@') != -1)
        rec.setModifier(new Author(sfd));
      else
        rec.setModifier(new Author(sfd, "unknown"));
    }
    rec.getFields().remove(f);
    return rec;
  }
  
  public static Record metapodaciUPolje000(Record rec) {
    if (rec.getField("000") != null)
      return rec;
    Field f000 = new Field("000");
    Subfield sfa = new Subfield('a');
    switch (rec.getPubType()) {
      // TODO: podrzati sve tipove publikacija
      case 1:
        sfa.setContent("00101");
        break;
      case 2:
        sfa.setContent("00401");
        break;
      case 3:
        sfa.setContent("00601");
        break;
    }
    f000.add(sfa);
    if (rec.getCreationDate() != null) {
      Subfield sfb = new Subfield('b');
      f000.add(sfb);
      String s1 = dateFormat.format(rec.getCreationDate());
      String s2 = "00000000";
      if (rec.getLastModifiedDate() != null)
        s2 = dateFormat.format(rec.getLastModifiedDate());
      sfb.setContent(s1 + s2);
    }
    if (rec.getCreator() != null) {
      Subfield sfc = new Subfield('c');
      f000.add(sfc);
      sfc.setContent(rec.getCreator().getCompact());
    }
    if (rec.getModifier() != null) {
      Subfield sfd = new Subfield('d');
      f000.add(sfd);
      sfd.setContent(rec.getModifier().getCompact());
    }
    rec.getFields().add(0, f000);
    return rec;
  }
  
  private static boolean isEmpty(String s) {
    return s == null || "".equals(s);
  }
  
  private static boolean isEmpty(Date d) {
    return d == null;
  }
  
  private static boolean isEmpty(BigDecimal bd) {
    return bd == null;
  }
  
  private static void addSubsubfield(Subfield sf, char name, String content) {
    Subsubfield ssf = new Subsubfield(name);
    ssf.setContent(content);
    sf.add(ssf);
  }
  
  private static void addSubfield(Field f, char name, String content) {
    Subfield sf = new Subfield(name);
    sf.setContent(content);
    f.add(sf);
  }
  
  private static String getSubfieldContent(Field f, char name) {
    Subfield sf = f.getSubfield(name);
    if (sf == null)
      return null;
    String cnt = sf.getContent();
    if ("".equals(cnt))
      return null;
    return cnt;
  }
  
  private static String getSubsubfieldContent(Field f, char sfName, char ssfName) {  	
    Subfield sf = f.getSubfield(sfName);
    if (sf == null)
      return null;
    Subsubfield ssf = sf.getSubsubfield(ssfName);    
    if (ssf == null)
      return null;
    String cnt = ssf.getContent();
    if ("".equals(cnt))
      return null;
    return cnt;
  }
  
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("ddMMyy");
  private static Log log = LogFactory.getLog(PrimerakSerializer.class);
  
  
}
