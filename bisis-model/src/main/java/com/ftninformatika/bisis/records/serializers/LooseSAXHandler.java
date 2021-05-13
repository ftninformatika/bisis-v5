package com.ftninformatika.bisis.records.serializers;

import java.util.ArrayList;
import java.util.List;

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
public class LooseSAXHandler extends DefaultHandler {

  /** mode of operation: collect all records in a document */
  public static final int OPERATION_COLLECT = 1;
  /** mode of operation: notify observer for each parsed record */
  public static final int OPERATION_NOTIFY  = 2;
  
  public LooseSAXHandler() {
    operation = OPERATION_COLLECT;
  }
  
  public LooseSAXHandler(RecordListener listener) {
    if (listener == null)
      throw new NullPointerException("listener == null");
    this.listener = listener;
    operation = OPERATION_NOTIFY;
  }
  
  public List getRecords() {
    return parsedRecords;
  }
  
  public Record getRecord() {
    if (parsedRecords.size() == 0)
      return null;
    return (Record)parsedRecords.get(0);
  }
  
  public void startDocument() throws SAXException {
    parsedRecords.clear();
  }

  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    try {
    if (qName.equals("record")) {
      currRecord = new Record();
      int recordID = 0;
      int pubType = 1;
      try {
    	recordID = Integer.parseInt(attrs.getValue("recordID"));
        pubType = Integer.parseInt(attrs.getValue("pubType"));
      } catch (Exception ex) {
      }
      currRecord.setRecordID(recordID);
      currRecord.setPubType(pubType);
    } else if (qName.equals("field")) {
      String name = attrs.getValue("name");
      char ind1 = attrs.getValue("ind1").charAt(0);
      char ind2 = attrs.getValue("ind2").charAt(0);
      if (currSubfield != null) {
        secField = new Field(name, ind1, ind2);
        currSubfield.setSecField(secField);
      } else {
        currField = new Field(name, ind1, ind2);
        currRecord.add(currField);
      }
    } else if (qName.equals("subfield")) {
      char name = attrs.getValue("name").charAt(0);
      if (secField != null) {
        secSubfield = new Subfield(name);
        secField.add(secSubfield);
      } else {
        currSubfield = new Subfield(name);
        currField.add(currSubfield);
      }
    } else if (qName.equals("subsubfield")) {
      char name = attrs.getValue("name").charAt(0);
      currSubsubfield = new Subsubfield(name);
      if (secSubfield != null)
        secSubfield.add(currSubsubfield);
      else
        currSubfield.add(currSubsubfield);
    }
    } catch (NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    try {
      if (qName.equals("record")) {
        currRecord.pack();
        currRecord.trim();
        currRecord.sort();
        if (operation == OPERATION_COLLECT)
          parsedRecords.add(currRecord);
        else if (operation == OPERATION_NOTIFY)
          listener.handleRecord(currRecord);
        currRecord = null;
      } else if (qName.equals("field")) {
        if (secField != null)
          secField = null;
        else
          currField = null;
      } else if (qName.equals("subfield")) {
        if (secSubfield != null)
          secSubfield = null;
        else
          currSubfield = null;
      } else if (qName.equals("subsubfield")) {
        currSubsubfield = null;
      }
    } catch (NullPointerException ex) {
      ex.printStackTrace();
    }
    
  }

  public void characters(char[] buf, int offset, int len) throws SAXException {
    try {
      String cnt = new String(buf, offset, len).trim().replace('\r', ' ').replace('\n', ' ');
      if (cnt.equals(""))
        return;
      if (currSubsubfield != null)
        currSubsubfield.setContent(currSubsubfield.getContent() + cnt);
      else if (secSubfield != null)
        secSubfield.setContent(secSubfield.getContent() + cnt);
      else if (currSubfield != null)
        currSubfield.setContent(currSubfield.getContent() + cnt);
    } catch (NullPointerException ex) {
      ex.printStackTrace();
    }
    
  }

  int count = 0;
  int operation = OPERATION_COLLECT;
  Record currRecord = null;
  Field currField = null;
  Field secField = null;
  Subfield currSubfield = null;
  Subfield secSubfield = null;
  Subsubfield currSubsubfield = null;
  List parsedRecords = new ArrayList();
  RecordListener listener;
}
