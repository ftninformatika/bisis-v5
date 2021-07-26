package com.ftninformatika.bisis.records.serializers;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import com.ftninformatika.utils.string.StringUtils;

/**
 * 
 * @author mbranko@uns.ac.rs
 */
public class FullFormatSerializer {

  /**
   * Serializes a record in the full format for display. Can produce 
   * HTML-compliant output.
   * 
   * @param record Record to be serialized.
   * @param forHTML If <code>true</code>, produces HTML-compatible output.
   * @return The serialized record.
   */
  public static String toFullFormat(Record record, boolean forHTML) {
    StringBuffer retVal = new StringBuffer(1024);
    if(!forHTML){
    	record=record.primerciUPolja();
    	record=record.godineUPolja();
    }
    for (int i = 0; i < record.getFields().size(); i++) {
      Field field = record.getField(i);
      fieldToFullFormat(retVal, field, forHTML);
      if (forHTML)
        retVal.append("<br>");
      retVal.append('\n');
    }
    if(!forHTML){
    	record=record.poljaUPrimerke();
    	record=record.godineUPolja();
    }
    return retVal.toString();
  }
  
  /**
   * Serializes one field in the full format. Can produce HTML-compatible 
   * output.
   * 
   * @param buff Buffer to append the output to.
   * @param field Field to be serialized
   * @param forHTML If <code>true</code>, produces HTML-compatible output.
   */
  private static void fieldToFullFormat(StringBuffer buff, Field field, boolean forHTML) {
    if (forHTML)
      buff.append("<b>");
    buff.append(field.getName());
    if (forHTML)
      buff.append("</b>");
    buff.append(' ');
    buff.append(field.getInd1() == ' ' ? '#' : field.getInd1());
    buff.append(field.getInd2() == ' ' ? '#' : field.getInd2());
    buff.append(' ');
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      if (forHTML)
        buff.append("<b>");
      buff.append('[');
      buff.append(subfield.getName());
      buff.append(']');
      if (forHTML)
        buff.append("</b>");
      if (subfield.getSecField() != null) {
        if (forHTML)
          buff.append("<b>");
        buff.append("{{");
        if (forHTML)
          buff.append("</b>");
        fieldToFullFormat(buff, subfield.getSecField(), forHTML);
        if (forHTML)
          buff.append("<b>");
        buff.append("}}");
        if (forHTML)
          buff.append("</b>");
      } else if (subfield.getSubsubfieldCount() > 0) {
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield subsubfield = subfield.getSubsubfield(j);
          if (forHTML)
            buff.append("<b>");
          buff.append('(');
          buff.append(subsubfield.getName());
          buff.append(')');
          if (forHTML)
            buff.append("</b>");
          if (forHTML)
            buff.append(StringUtils.adjustForHTML(subsubfield.getContent()));
          else
            buff.append(subsubfield.getContent());
        }
      } else {
        if (forHTML)
          buff.append(StringUtils.adjustForHTML(subfield.getContent()));
        else
          buff.append(subfield.getContent());
      }
    }
  }
  
  /**
   * Parses a record from a full-format representation. NOT IMPLEMENTED YET.
   * 
   * @param s String containing the full-format representation.
   * @return An instance of the record.
   */
  public static Record fromFullFormat(String s) {
    // TODO: implement this properly
    return null;
  }
  
  
}
