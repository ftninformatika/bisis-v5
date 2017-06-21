package com.ftninformatika.bisis.records.serializers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import com.gint.util.string.StringUtils;
import com.gint.util.xml.XMLUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class TightXMLSerializer {

  /**
   * Serializes the record to the "tight" XML format. XML string is not indented
   * for pretty-printing.
   * 
   * @param record The record to be serialized.
   * @return The serialized record.
   */
  public static String toTightXML(Record record) {
    StringBuffer retVal = new StringBuffer();
    retVal.append("<record");
    retVal.append(">\n");
    for (int i = 0; i < record.getFieldCount(); i++) {
      Field field = record.getField(i);
      fieldToTightXML(retVal, field);
    }
    retVal.append("</record>\n");
    return retVal.toString();
  }
  
  /**
   * Serializes the given field to the "tight" XML format.
   * @param buff Buffer to append the output to.
   * @param field Field to be serialized.
   */
  private static void fieldToTightXML(StringBuffer buff, Field field) {
    buff.append("  <f");
    buff.append(field.getName());
    buff.append(">\n");
    if (field.getInd1() != ' ') {
      buff.append("    <ind1>");
      buff.append(field.getInd1());
      buff.append("</ind1>\n");
    }
    if (field.getInd2() != ' ') {
      buff.append("    <ind2>");
      buff.append(field.getInd2());
      buff.append("</ind2>\n");
    }
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      buff.append("    <sf");
      buff.append(subfield.getName());
      buff.append(">");
      if (subfield.getSecField() != null) {
        fieldToTightXML(buff, subfield.getSecField());
      } else if (subfield.getSubsubfieldCount() > 0) {
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield ssf = subfield.getSubsubfield(j);
          buff.append("<ssf");
          buff.append(ssf.getName());
          buff.append(">");
          buff.append(StringUtils.adjustForHTML(ssf.getContent()));
          buff.append("</ssf");
          buff.append(ssf.getName());
          buff.append(">");
        }
      } else {
        buff.append(StringUtils.adjustForHTML(subfield.getContent()));
      }
      buff.append("</sf");
      buff.append(subfield.getName());
      buff.append(">\n");
    }
    buff.append("  </f");
    buff.append(field.getName());
    buff.append(">\n");
  }
  
  /**
   * Reads the record from the "tight" XML representation.
   * @param xml String with the XML document. UTF-16 encoding is assumed.
   * @return The initalized record.
   */
  public static Record fromTightXML(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      TightSAXHandler handler = new TightSAXHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getRecord();
    } catch (Exception ex) {
      return null;
    }
  }
  
  static SAXParserFactory factory;
  
  static {
    factory = SAXParserFactory.newInstance();
  }
}
