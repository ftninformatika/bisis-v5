package com.ftninformatika.bisis.records.serializers;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class UnimarcSerializer {

  /** Field separator in UNIMARC format. */
  public static final String FIELD_SEP = "\036";
  /** Subfield separator in UNIMARC format. */
  public static final String SUBFIELD_SEP = "\037";
  /** Subsubfield separator in UNIMARC format. */
  public static final String SUBSUBFIELD_SEP = "\\";

  /**
   * Creates a record from a UNIMARC-encoded string.
   * 
   * @param s String with the UNIMARC-encoded content. UNIMARC character 
   *   encoding is assumed (higher bytes are 0, lower bytes are read from file).
   * @return An instance of a <code>Record</code>.
   */
  public static Record fromUNIMARC(int pubType, String s, boolean unimarcCharset) {
  /*  UFormat format = PubTypes.getPubType(pubType);
    if (format == null) {
      format = PubTypes.getFormat();
    }

    UnimarcConverter conv = new UnimarcConverter();
    Record retVal = new Record();
    retVal.setPubType(pubType);
    StringTokenizer st1 = new StringTokenizer(s, FIELD_SEP);
    while (st1.hasMoreTokens()) {
      String fieldText = st1.nextToken();
      Field field = new Field();
      retVal.getFields().add(field);
      field.setName(fieldText.substring(0, 3));
      field.setInd1(fieldText.charAt(3));
      field.setInd2(fieldText.charAt(4));
      if (format.containsSecondaryFields(field.getName())) {
        String tmp = fieldText.substring(5);
        while (tmp != null && !tmp.isEmpty()) {
         Subfield sf = new Subfield();
          sf.setName('1');
          field.getSubfields().add(sf);
          int sepPos = tmp.indexOf(SUBFIELD_SEP + "1", 1);
          String secFieldText;
          if (sepPos == -1) {
            secFieldText = tmp.substring(2);
            tmp = null;
          } else {
            secFieldText = tmp.substring(2, sepPos);
            tmp = tmp.substring(sepPos);
          }
          Field secF = new Field();
          if (secFieldText.length() > 5) {
            secF.setName(secFieldText.substring(0, 3));
            secF.setInd1(secFieldText.charAt(3));
            secF.setInd2(secFieldText.charAt(4));
            sf.setSecField(secF);
            StringTokenizer st2 = new StringTokenizer(secFieldText.substring(5), 
                SUBFIELD_SEP);
            while (st2.hasMoreTokens()) {
              String subfieldText = st2.nextToken();
              Subfield secSF = new Subfield();
              secSF.setName(subfieldText.charAt(0));
              if (unimarcCharset)
                secSF.setContent(conv.Unimarc2Unicode(subfieldText.substring(1)));
              else
                secSF.setContent(subfieldText.substring(1));
              secF.getSubfields().add(secSF);
            }
          }
        }
      } else {
        StringTokenizer st2 = new StringTokenizer(fieldText.substring(5), 
            SUBFIELD_SEP);
        while (st2.hasMoreTokens()) {
          String subfieldText = st2.nextToken();
          Subfield sf = new Subfield();
          sf.setName(subfieldText.charAt(0));
          String sfName = field.getName() + sf.getName();
          if (format.containsSubsubfields(sfName)) {
            StringTokenizer st3 = new StringTokenizer(subfieldText.substring(1), 
                SUBSUBFIELD_SEP);
            while (st3.hasMoreTokens()) {
              String subsubfieldText = st3.nextToken();
              Subsubfield ssf = new Subsubfield();
              ssf.setName(subsubfieldText.charAt(0));
              if (unimarcCharset)
                ssf.setContent(conv.Unimarc2Unicode(
                    subsubfieldText.substring(1)));
              else
                ssf.setContent(subsubfieldText.substring(1));
              sf.getSubsubfields().add(ssf);
            }
          } else {
            if (unimarcCharset)
              sf.setContent(conv.Unimarc2Unicode(subfieldText.substring(1)));
            else
              sf.setContent(subfieldText.substring(1));
          }
          field.getSubfields().add(sf);
        }
      }
    }   */
    /*String pubtype = retVal.getPubType();
    if (pubtype.startsWith("001"))
      retVal.setPubType(1);
    if (pubtype.startsWith("004"))
      retVal.setPubType(2);*/
    //return retVal;
    return null;
  }
  
  /**
   * Serializes the record to a UNIMARC-encoded string.
   * @param record The record to be serialized.
   * @return UNIMARC-encoded string with the serialized record. UNIMARC 
   *   character encoding is assumed (higher bytes are 0, lower bytes to be 
   *   written to a file).
   */
  public static String toUNIMARC(int pubType, Record record, boolean unimarcCharset) {
    StringBuffer retVal = new StringBuffer(1024);
    for (int i = 0; i < record.getFields().size(); i++) {
      if (i > 0)
        retVal.append(FIELD_SEP);
      Field field = (Field)record.getFields().get(i);
      fieldToUNIMARC(retVal, field, unimarcCharset);
    }
    return retVal.toString();
  }
  
  /**
   * Serializes one field to a UNIMARC-encoded string.
   * @param buff Buffer to append the output to.
   * @param field field to be serialized.
   */
  private static void fieldToUNIMARC(StringBuffer buff, Field field, boolean unimarcCharset) {
    UnimarcConverter conv = new UnimarcConverter();
    buff.append(field.getName());
    buff.append(field.getInd1());
    buff.append(field.getInd2());
    for (int i = 0; i < field.getSubfields().size(); i++) {
      buff.append(SUBFIELD_SEP);
      Subfield subfield = field.getSubfield(i);
      buff.append(subfield.getName());
      if (subfield.getSecField() != null) {
        fieldToUNIMARC(buff, subfield.getSecField(), unimarcCharset);
      } else if (subfield.getSubsubfields().size() > 0) {
        for (int k = 0; k < subfield.getSubsubfields().size(); k++) {
          Subsubfield subsubfield = subfield.getSubsubfield(k);
          if (k > 0)
            buff.append(SUBSUBFIELD_SEP);
          buff.append(subsubfield.getName());
          if (unimarcCharset)
            buff.append(conv.Unicode2Unimarc(subsubfield.getContent(), true));
          else
            buff.append(subsubfield.getContent());
        }
      } else {
        if (unimarcCharset)
          buff.append(conv.Unicode2Unimarc(subfield.getContent(), true));
        else
          buff.append(subfield.getContent());
      }
    }
  }

  private static Logger log =  LoggerFactory.getLogger(UnimarcSerializer.class);
}
