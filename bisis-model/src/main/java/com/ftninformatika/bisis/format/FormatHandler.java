package com.ftninformatika.bisis.format;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class FormatHandler extends DefaultHandler {
    
  public UFormat getFormat() {
    return format;
  }
    
  public void startDocument() throws SAXException {
  }
    
  public void endDocument() throws SAXException {
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    if (qName.equals("format")) {
      String name = attrs.getValue("name");    
      String desc = attrs.getValue("description");
      int formatID = Integer.valueOf(attrs.getValue("formatID")).intValue();
      format = new UFormat(formatID, name, desc);
      externalCoders = new ArrayList();
    } else if (qName.equals("field")) {
      String name = attrs.getValue("name");     
      String desc = attrs.getValue("description");
      boolean mandatory = Boolean.valueOf(attrs.getValue("mandatory")).booleanValue();
      boolean repeatable = Boolean.valueOf(attrs.getValue("repeatable")).booleanValue();
      boolean secondary = Boolean.valueOf(attrs.getValue("secondary")).booleanValue();
      currField = new UField(name, desc, mandatory, repeatable, secondary);
      format.add(currField);
    } else if (qName.equals("indicator")) {
      int index = Integer.valueOf(attrs.getValue("index")).intValue();
      String desc = attrs.getValue("description");
      String defVal = attrs.getValue("defaultValue");
      currInd = new UIndicator(index,desc, defVal,currField);
      if (index == 1)
        currField.setInd1(currInd); 
      else
        currField.setInd2(currInd);
    } else if (qName.equals("subfield")) {
      char name = attrs.getValue("name").charAt(0);
      String desc = attrs.getValue("description");
      boolean mandatory = Boolean.valueOf(attrs.getValue("mandatory")).booleanValue();
      boolean repeatable = Boolean.valueOf(attrs.getValue("repeatable")).booleanValue();
      int length = Integer.valueOf(attrs.getValue("length")).intValue();
      String defVal = attrs.getValue("defaultValue");
      currSubfield = new USubfield(currField, name, mandatory, repeatable);
      currSubfield.setDescription(desc);
      currSubfield.setLength(length);
      if (defVal != null)
        currSubfield.setDefaultValue(defVal);
      currField.getSubfields().add(currSubfield);
    } else if (qName.equals("subsubfield")) {
      char name = attrs.getValue("name").charAt(0);
      String desc = attrs.getValue("description");
      boolean mandatory = Boolean.valueOf(attrs.getValue("mandatory")).booleanValue();
      boolean repeatable = Boolean.valueOf(attrs.getValue("repeatable")).booleanValue();
      int length = Integer.valueOf(attrs.getValue("length")).intValue();
      String defVal = attrs.getValue("defaultValue");
      currSubsubfield = new USubsubfield(currSubfield, name, mandatory, repeatable);
      currSubsubfield.setDescription(desc);
      currSubsubfield.setLength(length);
      if (defVal != null)
        currSubsubfield.setDefaultValue(defVal);
      currSubfield.getSubsubfields().add(currSubsubfield);
    } else if (qName.equals("coder")) {
      boolean external = Boolean.valueOf(attrs.getValue("external")).booleanValue();
      if (external) {
        int type = Integer.valueOf(attrs.getValue("type")).intValue();
        if (currSubsubfield != null) {
          UCoder coder = new UCoder();
          coder.setType(type);
          coder.setExternal(true);
          currSubsubfield.setCoder(coder);
        } else if (currSubfield != null) {
          UCoder coder = new UCoder();
          coder.setType(type);
          coder.setExternal(true);
          currSubfield.setCoder(coder);
        } else {
          String name = attrs.getValue("name");
          currCoder = new UCoder(type, name, external);
          externalCoders.add(currCoder);
        }
      } else {
        currCoder = new UCoder();
        currCoder.setExternal(false);
        if (currSubsubfield != null)
          currSubsubfield.setCoder(currCoder);
        else
          currSubfield.setCoder(currCoder);
      }
    } else if (qName.equals("item")) {
      String code = attrs.getValue("code");
      String value = attrs.getValue("value");
      if (currInd != null)
        currInd.addValue(new UItem(code, value));
      else
        currCoder.addItem(new UItem(code, value));
    }
  }
    
  public void endElement(String namespaceURI, String lName, String qName) throws SAXException {
    if (qName.equals("format")) {
      connectExternalCoders();
    } else if (qName.equals("field")) {
      currField = null;
    } else if (qName.equals("indicator")) {
      currInd = null;
    } else if (qName.equals("subfield")) {
      currSubfield = null;
    } else if (qName.equals("subsubfield")) {
      currSubsubfield = null;
    } else if (qName.equals("coder")) {
      currCoder = null;
    } else if (qName.equals("item")) {
      // do nothing
    }
  }

  public void characters(char[] buf, int offset, int len) throws SAXException {
    // do nothing
  }
  
  private void connectExternalCoders() {
    Iterator fields = format.getFields().iterator();
    while (fields.hasNext()) {
      UField field = (UField)fields.next();
      Iterator subfields = field.getSubfields().iterator();
      while (subfields.hasNext()) {
        USubfield sf = (USubfield)subfields.next();
        if (sf.getCoder() != null) {
          if (sf.getCoder().isExternal()) {
            sf.setCoder(findCoder(sf.getCoder().getType()));
          }
        }
        Iterator subsubfields = sf.getSubsubfields().iterator();
        while (subsubfields.hasNext()) {
          USubsubfield ssf = (USubsubfield)subsubfields.next();
          if (ssf.getCoder() != null) {
            if (ssf.getCoder().isExternal()) {
              ssf.setCoder(findCoder(ssf.getCoder().getType()));
            }
          }
        }
      }
      
    }
  }
  
  private UCoder findCoder(int type) {
    Iterator it = externalCoders.iterator();
    while (it.hasNext()) {
      UCoder coder = (UCoder)it.next();
      if (coder.getType() == type)
        return coder;
    }
    return null;
  }

  private UFormat format = null;
  private UField currField = null;
  private UIndicator currInd = null;
  private USubfield currSubfield = null;
  private USubsubfield currSubsubfield = null;
  private UCoder currCoder = null;
  private List externalCoders = null;
}
