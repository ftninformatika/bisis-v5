package com.ftninformatika.bisis.records;


import com.ftninformatika.bisis.records.serializers.*;

public class RecordFactory {

  /** unknown serialization type */
  public static final int TYPE_UNKNOWN     = 0;
  /** the UNIMARC (YUMARC) format string */
  public static final int TYPE_UNIMARC     = 1;
  /** the "tight" XML format */
  public static final int TYPE_TIGHT_XML   = 2;
  /** the "loose" XML format */
  public static final int TYPE_LOOSE_XML   = 3;
  /** the "full" format used for displaying records */
  public static final int TYPE_FULL_FORMAT = 4;
  /** the ISIS (Biblio, WinISIS, etc) format */
  public static final int TYPE_ISIS        = 5;
  /** the BISIS 3.5 format (996 stripped, UTF8) */
  public static final int TYPE_BISIS35     = 6;
  
  /**
   * Instantiates the record from the string in the given format.
   * 
   * @param format Serialization format 
   * @param text Serialized record
   * @return A new instantiated record
   */
  public static Record parse(int format, String text) {
    return parse(format, 1, text);
  }
  
  /**
   * Serializes the record to a string in the given format
   * @param format Serialization format
   * @param rec The record to be serialized
   * @return A serialized record
   */
  public static String serialize(int format, Record rec) {
    return serialize(format, 1, rec);
  }
  
  /**
   * Instantiates the record from the string in the given format.
   * 
   * @param format Serialization format 
   * @param text Serialized record
   * @return A new instantiated record
   */
  public static Record parse(int format, int pubType, String text) {
    switch (format) {
      case TYPE_UNIMARC:
        return fromUNIMARC(pubType, text);
      case TYPE_TIGHT_XML:
        return fromTightXML(text);
      case TYPE_LOOSE_XML:
        return fromLooseXML(text);
      case TYPE_FULL_FORMAT:
        return fromFullFormat(pubType, text);
      case TYPE_ISIS:
        return null;
      case TYPE_BISIS35:
        return fromBISIS35(pubType, text);
      default:
        return new Record();
    }
  }
  
  /**
   * Serializes the record to a string in the given format
   * @param format Serialization format
   * @param rec The record to be serialized
   * @return A serialized record
   */
  public static String serialize(int format, int pubType, Record rec) {
    switch (format) {
      case TYPE_UNIMARC:
        return toUNIMARC(pubType, rec);
      case TYPE_TIGHT_XML:
        return toTightXML(rec);
      case TYPE_LOOSE_XML:
        return toLooseXML(rec);
      case TYPE_FULL_FORMAT:
        return toFullFormat(pubType, rec);
      case TYPE_ISIS:
        return toISIS(rec);
      case TYPE_BISIS35:
        return toBISIS35(pubType, rec);
      default:
        return "";
    }
  }
  
  public static Record fromBISIS35(int pubType, String rez) {
    return UnimarcSerializer.fromUNIMARC(pubType, rez, false);
  }
  
  public static String toBISIS35(int pubType, Record rec) {
    return UnimarcSerializer.toUNIMARC(pubType, rec, false);
  }
  
  public static Record fromUNIMARC(int pubType, String unimarc) {
    return UnimarcSerializer.fromUNIMARC(pubType, unimarc, true)
      .poljaUPrimerke()
      .poljaUGodine()
      .polje000UMetapodatke();
  }
  
 /* public static String toUNIMARC(int pubType, Record rec) {
    return UnimarcSerializer.toUNIMARC(pubType, 
        rec.primerciUPolja().godineUPolja().polje000UMetapodatke(), true);
  }*/
  
  // proba
  public static String toUNIMARC(int pubType, Record rec) {
    return UnimarcSerializer.toUNIMARC(pubType, 
        rec.primerciUPolja().metapodaciUPolje000(), true);
  }
  
  public static String toUNIMARC1(int pubType, Record rec) {
   return UnimarcSerializer.toUNIMARC(pubType,rec, true);
 }
 
  
  public static Record fromFullFormat(int pubType, String text) {
    return FullFormatSerializer.fromFullFormat(text);
  }
  
  public static String toFullFormat(int pubType, Record rec) {
    return FullFormatSerializer.toFullFormat(rec, false);
  }
  
  public static String toFullFormat(int pubType, Record rec, boolean forHTML) {
    return FullFormatSerializer.toFullFormat(rec, forHTML);
  }

  public static Record fromLooseXML(String xml) {
	//Record test = LooseXMLSerializer.fromLooseXML(xml);
	//System.out.println("\nKonvertovani XML Record null: "+(test==null)+"\n"+xml);
	return LooseXMLSerializer.fromLooseXML(xml).poljaUPrimerke().poljaUGodine();
  }
  
  
  public static Record fromLooseXML1(String xml) {
  	//Record test = LooseXMLSerializer.fromLooseXML(xml);
  	//System.out.println("\nKonvertovani XML Record null: "+(test==null)+"\n"+xml);
  	return LooseXMLSerializer.fromLooseXML(xml);
    }
  
  public static String toLooseXML(Record rec) {
    return LooseXMLSerializer.toLooseXML(rec.primerciUPolja().godineUPolja());
  }

  public static Record fromTightXML(String xml) {
    return TightXMLSerializer.fromTightXML(xml).poljaUPrimerke().poljaUGodine();
  }
  
  public static String toTightXML(Record rec) {
    return TightXMLSerializer.toTightXML(rec.primerciUPolja().godineUPolja());
  }
  
  public static String toISIS(Record rec) {
    return IsisSerializer.toISISFormat(rec.primerciUPolja().godineUPolja());
  }
  
}
