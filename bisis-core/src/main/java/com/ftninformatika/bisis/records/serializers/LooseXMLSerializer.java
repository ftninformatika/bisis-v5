package com.ftninformatika.bisis.records.serializers;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import com.ftninformatika.utils.string.StringUtils;
import com.ftninformatika.utils.xml.XMLUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class LooseXMLSerializer {

  /**
   * Serializes the record to the "loose" XML format. XML string is not
   * indented for pretty-printing.
   * 
   * @param rec The record to be serialized.
   * @return The XML document representing the record.
   */
  public static String toLooseXML(Record rec) {
    StringBuffer retVal = new StringBuffer(2048);
    retVal.append("<record recordID=\"");
    retVal.append(rec.getRecordID());
    retVal.append("\" pubType=\"");
    retVal.append(rec.getPubType());
    retVal.append("\">\n");
    for (int i = 0; i < rec.getFieldCount(); i++) {
      Field field = rec.getField(i);
      fieldToLooseXML(retVal, field, false);
    }
    retVal.append("</record>\n");
    return retVal.toString();
  }
  
  /**
   * Serializes one field to the "loose" XML format.
   * 
   * @param buff Buffer to append the output to.
   * @param field The field to be serialized.
   */
  private static void fieldToLooseXML(StringBuffer buff, Field field, boolean 
      isSecondary) {
    if (isSecondary)
      buff.append("    ");
    buff.append("  <field name=\"");
    buff.append(field.getName());
    buff.append("\" ind1=\"");
    buff.append(field.getInd1());
    buff.append("\" ind2=\"");
    buff.append(field.getInd2());
    buff.append("\">\n");
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      if (isSecondary)
        buff.append("    ");
      buff.append("    <subfield name=\"");
      buff.append(subfield.getName());
      buff.append("\">");
      if (subfield.getSecField() != null) {
        buff.append("\n");
        fieldToLooseXML(buff, subfield.getSecField(), true);
        buff.append("    </subfield>\n");
      } else if (subfield.getSubsubfieldCount() > 0) {
        buff.append("\n");
        for (int j = 0; j < subfield.getSubsubfieldCount(); j++) {
          Subsubfield subsubfield = subfield.getSubsubfield(j);
          if (isSecondary)
            buff.append("    ");
          buff.append("      <subsubfield name=\"");
          buff.append(subsubfield.getName());
          buff.append("\">");
          buff.append(StringUtils.adjustForHTML(subsubfield.getContent()));
          buff.append("</subsubfield>\n");
        }
        if (isSecondary)
          buff.append("    ");
        buff.append("    </subfield>\n");
      } else {
        buff.append(StringUtils.adjustForHTML(subfield.getContent()));
        buff.append("</subfield>\n");
      }
    }
    if (isSecondary)
      buff.append("    ");
    buff.append("  </field>\n");
  }
  
  /**
   * Reads the record from the "loose" XML representation.
   * @param xml String with the XML document. UTF-16 encoding is assumed.
   * @return The initalized record.
   */
  public static Record fromLooseXML(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      LooseSAXHandler handler = new LooseSAXHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getRecord();
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  /**
   * Reads the record from the "loose" XML representation.
   * @param xml String with the XML document. UTF-16 encoding is assumed.
   * @return The initalized record.
   */
  public static List fromLooseXMLMany(String xml) {
    try {
      SAXParser parser = factory.newSAXParser();
      LooseSAXHandler handler = new LooseSAXHandler();
      parser.parse(XMLUtils.getInputSourceFromString(xml), handler);
      return handler.getRecords();
    } catch (Exception ex) {
      log.fatal(ex);
      return null;
    }
  }
  
  
  public static List fromLooseXMLMany(InputSource xml) {
   try {
     SAXParser parser = factory.newSAXParser();
     LooseSAXHandler handler = new LooseSAXHandler();
     parser.parse(xml, handler);
     return handler.getRecords();
   } catch (Exception ex) {
     log.fatal(ex);
     return null;
   }
 }
  
  static SAXParserFactory factory;
  static Logger log = Logger.getLogger(LooseXMLSerializer.class);
  
  static {
    factory = SAXParserFactory.newInstance();
  }
}
