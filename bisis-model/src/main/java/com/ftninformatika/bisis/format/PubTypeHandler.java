package com.ftninformatika.bisis.format;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class PubTypeHandler extends DefaultHandler {
  
  public UFormat getPubType() {
    return pubType;
  }

  public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
    if (qName.equals("pubType")) {
      int pubTypeID = Integer.valueOf(attrs.getValue("id")).intValue();
      String name = attrs.getValue("name");
      pubType = new UFormat(pubTypeID, name, name);
    } else if (qName.equals("subfield")) {
      String name = attrs.getValue("name");
      if (name == null || name.length() != 4)
        return;
      String defVal = attrs.getValue("default");
      String fName = name.substring(0, 3);
      char sfName = name.charAt(3);
      UField field = pubType.getField(fName);     
      if (field == null) {      		
        field = PubTypes.getFormat().getField(fName).shallowCopy();
        pubType.add(field);
      }
      USubfield subfield = pubType.getSubfield(name);
      if (subfield == null) {      	 
        subfield = PubTypes.getFormat().getSubfield(name).shallowCopy();
        field.add(subfield);
      }
      if (defVal != null)
        subfield.setDefaultValue(defVal);
    }
  }
  
  private UFormat pubType;
  
}
