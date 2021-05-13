package com.ftninformatika.bisis.format.serializers;

import com.ftninformatika.bisis.format.*;

import java.util.HashMap;
import java.util.Iterator;


/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class XMLSerializer implements FormatSerializer {

  public String getType() {
    return TYPE_XML;
  }

  public String serializeFormat(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer();
    LF = System.getProperty("line.separator");
    externalCoders.clear();
    retVal.append("<?xml version=\"1.0\"?>");
    retVal.append(LF);
    retVal.append("<format xmlns=\"http://bisis.uns.ac.rs/schemas/unimarc.xsd\"");
    retVal.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    retVal.append(" xsi:noNamespaceSchemaLocation=\"unimarc.xsd\"");
    retVal.append(" formatID=\"0\"");
    retVal.append(" name=\"");
    retVal.append(fieldSet.getName());
    retVal.append("\" description=\"");
    retVal.append(fieldSet.getDescription());
    retVal.append("\">");
    retVal.append(LF);
    
    Iterator fields = fieldSet.getFields().iterator();
    while (fields.hasNext()) {
      UField field = (UField)fields.next();
      fieldToXML(retVal, field);
    }
    
    Iterator extCoders = externalCoders.values().iterator();
    while (extCoders.hasNext()) {
      UCoder coder = (UCoder)extCoders.next();
      coderToXML(retVal, coder, "  ");
    }
    
    retVal.append("</format>");
    return retVal.toString();
  }
  
  public String serializeCoders(UFormat fieldSet) {
    return "";
  }
  
  private void fieldToXML(StringBuffer retVal, UField field) {
    retVal.append("  <field name=\"");
    retVal.append(field.getName());
    retVal.append("\" description=\"");
    retVal.append(field.getDescription());
    retVal.append("\" mandatory=\"");
    retVal.append(field.isMandatory());
    retVal.append("\" repeatable=\"");
    retVal.append(field.isRepeatable());
    retVal.append("\" secondary=\"");
    retVal.append(field.isSecondary());
    retVal.append("\">");
    retVal.append(LF);
    
    indicatorToXML(retVal, field.getInd1(), 1);
    indicatorToXML(retVal, field.getInd2(), 2);
    
    Iterator subfields = field.getSubfields().iterator();
    while (subfields.hasNext()) {
      USubfield subfield = (USubfield)subfields.next();
      subfieldToXML(retVal, subfield);
    }
    retVal.append("  </field>");
    retVal.append(LF);
  }
  
  private void indicatorToXML(StringBuffer retVal, UIndicator ind, int index) {
    if (ind == null)
      return;
    retVal.append("    <indicator index=\"");
    retVal.append(index);
    retVal.append("\" description=\"");
    retVal.append(ind.getDescription());
    if (ind.getDefaultValue() != null) {
      retVal.append("\" defaultValue=\"");
      retVal.append(ind.getDefaultValue());
    }
    retVal.append("\">");
    retVal.append(LF);
    Iterator items = ind.getValues().iterator();
    while (items.hasNext()) {
      UItem item = (UItem)items.next();
      retVal.append("      <item code=\"");
      retVal.append(item.getCode());
      retVal.append("\" value=\"");
      retVal.append(item.getValue());
      retVal.append("\"/>");
      retVal.append(LF);
    }
    retVal.append("    </indicator>");
    retVal.append(LF);
  }
  
  private void subfieldToXML(StringBuffer retVal, USubfield subfield) {
    retVal.append("    <subfield name=\"");
    retVal.append(subfield.getName());
    retVal.append("\" description=\"");
    retVal.append(subfield.getDescription());
    retVal.append("\" mandatory=\"");
    retVal.append(subfield.isMandatory());
    retVal.append("\" repeatable=\"");
    retVal.append(subfield.isRepeatable());
    retVal.append("\" length=\"");
    retVal.append(subfield.getLength());
    retVal.append("\"");
    if (subfield.getDefaultValue() != null) {
      retVal.append(" defaultValue=\"");
      retVal.append(subfield.getDefaultValue());
      retVal.append("\"");
    }
    if (subfield.getCoder() != null) {
      retVal.append(">");
      retVal.append(LF);
      UCoder coder = subfield.getCoder();
      if (coder.isExternal()) {
        externalCoders.put(new Integer(coder.getType()), coder);
        retVal.append("      <coder external=\"true\" type=\"");
        retVal.append(coder.getType());
        retVal.append("\"/>");
        retVal.append(LF);
      } else {
        coderToXML(retVal, coder, "      ");
      }
      retVal.append("    </subfield>");
    } else if (subfield.getSubsubfields().size() > 0) {
      retVal.append(">");
      retVal.append(LF);
      Iterator subsubfields = subfield.getSubsubfields().iterator();
      while (subsubfields.hasNext()) {
        USubsubfield subsubfield = (USubsubfield)subsubfields.next();
        subsubfieldToXML(retVal, subsubfield);
      }
      retVal.append("    </subfield>");
    } else {
      retVal.append("/>");
    }
    retVal.append(LF);
  }
  
  private void subsubfieldToXML(StringBuffer retVal, USubsubfield subsubfield) {
    retVal.append("      <subsubfield name=\"");
    retVal.append(subsubfield.getName());
    retVal.append("\" description=\"");
    retVal.append(subsubfield.getDescription());
    retVal.append("\" mandatory=\"");
    retVal.append(subsubfield.isMandatory());
    retVal.append("\" repeatable=\"");
    retVal.append(subsubfield.isRepeatable());
    retVal.append("\" length=\"");
    retVal.append(subsubfield.getLength());
    retVal.append("\"");
    if (subsubfield.getDefaultValue() != null) {
      retVal.append(" defaultValue=\"");
      retVal.append(subsubfield.getDefaultValue());
      retVal.append("\"");
    }
    if (subsubfield.getCoder() != null) {
      retVal.append(">");
      retVal.append(LF);
      UCoder coder = subsubfield.getCoder();
      if (coder.isExternal()) {
        externalCoders.put(new Integer(coder.getType()), coder);
        retVal.append("      <coder external=\"true\" type=\"");
        retVal.append(coder.getType());
        retVal.append("\"/>");
        retVal.append(LF);
      } else {
        coderToXML(retVal, coder, "        ");
      }
      retVal.append("      </subsubfield>");
    } else {
      retVal.append("/>");
    }
    retVal.append(LF);
  }
  
  private void coderToXML(StringBuffer retVal, UCoder coder, String indent) {
    retVal.append(indent);
    retVal.append("<coder external=\"");
    retVal.append(coder.isExternal());
    retVal.append("\"");
    if (coder.getType() != 0) {
      retVal.append(" type=\"");
      retVal.append(coder.getType());
      retVal.append("\"");
    }
    if (coder.getName() != null) {
      retVal.append(" name=\"");
      retVal.append(coder.getName());
      retVal.append("\"");
    }
    retVal.append(">");
    retVal.append(LF);
    
    Iterator items = coder.getItems().iterator();
    while (items.hasNext()) {
      UItem item = (UItem)items.next();
      retVal.append(indent);
      retVal.append("  <item code=\"");
      retVal.append(item.getCode());
      retVal.append("\" value=\"");
      retVal.append(item.getValue());
      retVal.append("\"/>");
      retVal.append(LF);
    }
    retVal.append(indent);
    retVal.append("</coder>");
    retVal.append(LF);
  }
  
  private String LF;
  private HashMap externalCoders = new HashMap();

}
