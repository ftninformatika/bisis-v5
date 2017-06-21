package com.ftninformatika.bisis.prefixes;

import java.io.Serializable;

/** 
 * PrefixPair klasa se koristi da grupise dve vrednosti <code>(prefName, 
 * value)</code> u par. Metoda parseRecord klase RecordParser vraca listu 
 * objekata klase PrefixPair. Ova metoda je namenjena za konverziju UNIMARC 
 * zapisa u prefikse za pretragu.
 * 
 * @see RecordParser
 * @author mbranko@uns.ns.ac.yu
 */ 
public class PrefixValue implements Serializable, Comparable {
  
  /** Konstruise PrefixPair objekat.
    * @param prefName naziv prefiksa
    * @param value sadrzaj prefiksa
    */
  public PrefixValue(String prefName, String value) {
    this.prefName = prefName;
    this.value = value;
  }
  
  /** Konstruise PrefixPair objekat sa praznim vrednostima.
    */
  public PrefixValue() {
    prefName = "";
    value = "";
  }
  
  public int compareTo(Object o) {
    if (!(o instanceof PrefixValue))
      return 0;
    PrefixValue pv = (PrefixValue)o;
    return prefName.compareTo(pv.prefName);
  }
  
  /** Naziv prefiksa */
  public String prefName;
  
  /** Sadrzaj prefiksa */
  public String value;
}
