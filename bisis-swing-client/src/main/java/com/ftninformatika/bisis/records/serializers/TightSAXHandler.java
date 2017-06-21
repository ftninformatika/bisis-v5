package com.ftninformatika.bisis.records.serializers;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author mbranko@uns.ns.ac.yu
 *
 */
public class TightSAXHandler extends DefaultHandler {

  public Record getRecord() {
    return parsedRecord;
  }
  
  public void startDocument() throws SAXException {
  }

  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    if (qName.equals("record")) {
      currRecord = new Record();
    } else if (qName.startsWith("ssf")) {
      currSubsubfield = new Subsubfield();
      currSubsubfield.setName(qName.charAt(3));
      currSubfield.getSubsubfields().add(currSubsubfield);
    } else if (qName.startsWith("sf")) {
      currSubfield = new Subfield();
      currSubfield.setName(qName.charAt(2));
      currField.getSubfields().add(currSubfield);
    } else if (qName.startsWith("f")) {
      currField = new Field();
      currField.setName(qName.substring(1, 3));
      currRecord.getFields().add(currField);
    } else if (qName.equals("ind1")) {
      currInd1 = true;
    } else if (qName.equals("ind2")) {
      currInd2 = true;
    } 
  }

  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    if (qName.equals("record")) {
      currRecord.sort();
      parsedRecord = currRecord;
      currRecord = null;
    } else if (qName.startsWith("ssf")) {
      currSubsubfield = null;
    } else if (qName.startsWith("sf")) {
      currSubfield = null;
    } else if (qName.startsWith("f")) {
      currField = null;
    } else if (qName.equals("ind1")) {
      currInd1 = false;
    } else if (qName.equals("ind2")) {
      currInd2 = false;
    }
  }

  public void characters(char[] buf, int offset, int len) throws SAXException {
    String cnt = new String(buf, offset, len).replace('\r', ' ').replace('\n', ' ');
    if (cnt.equals(""))
      return;
    if (currInd1) {
      currField.setInd1(buf[offset]);
      currInd1 = false;
    } else if (currInd2) {
      currField.setInd2(buf[offset]);
      currInd2 = false;
    } else if (currSubsubfield != null)
      currSubsubfield.setContent(currSubsubfield.getContent() + cnt);
    else if (currSubfield != null)
      currSubfield.setContent(currSubfield.getContent() + cnt);
  }

  int count = 0;
  Record currRecord = null;
  Field currField = null;
  Subfield currSubfield = null;
  Subsubfield currSubsubfield = null;
  boolean currInd1 = false;
  boolean currInd2 = false;
  Record parsedRecord = null;

}
