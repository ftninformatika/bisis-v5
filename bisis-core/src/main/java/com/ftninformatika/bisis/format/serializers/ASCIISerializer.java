package com.ftninformatika.bisis.format.serializers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ftninformatika.bisis.format.*;
import com.gint.util.string.StringUtils;

/**
 * Class ASCIISerializer comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class ASCIISerializer implements FormatSerializer {

  public static final int INITIAL_BUFFER_SIZE = 102400;
  
  public String getType() {
    return TYPE_PLAIN;
  }
  
  public String serializeFormat(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");

    retVal.append("Identifikator formata: ");
    retVal.append(fieldSet.getName());
    retVal.append(lineSep);
    retVal.append("Naziv formata: ");
    retVal.append(fieldSet.getDescription());
    retVal.append(lineSep);
    
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      retVal.append(lineSep);
      serializeFormat(retVal, field);
    }
    return retVal.toString();
  }
  
  public String serializeCoders(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");
    retVal.append("Identifikator formata: ");
    retVal.append(fieldSet.getName());
    retVal.append(lineSep);
    retVal.append("Naziv formata: ");
    retVal.append(fieldSet.getDescription());
    retVal.append(lineSep);
    
    List externalCoders = new ArrayList();
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      for (int j = 0; j < field.getSubfields().size(); j++) {
        USubfield sf = (USubfield)field.getSubfields().get(j);
        serializeCoders(retVal, sf, externalCoders);
      }
    }
    
    Iterator it = externalCoders.iterator();
    while (it.hasNext()) {
      UCoder coder = (UCoder)it.next();
      retVal.append(lineSep);
      retVal.append("Eksterni \u0161ifarnik: " + coder.getName());
      retVal.append(lineSep);
      retVal.append("-------------------------------------------------");
      retVal.append(lineSep);
      writeCoderItems(retVal, coder);
    }
    return retVal.toString();
  }
  
  private void serializeFormat(StringBuffer buff, UField field) {
    buff.append("Polje ");
    buff.append(field.getName());
    buff.append(" - ");
    buff.append(field.getDescription());
    buff.append(lineSep);
    buff.append(
        "====================================================================");
    buff.append(lineSep);
    buff.append("Obavezno:   ");
    buff.append(dane(field.isMandatory()));
    buff.append(lineSep);
    buff.append("Ponovljivo: ");
    buff.append(dane(field.isRepeatable()));
    buff.append(lineSep);
    buff.append("Sekundarno: ");
    buff.append(dane(field.isSecondary()));
    buff.append(lineSep);
    buff.append("Prvi indikator:  ");
    serializeFormat(buff, field.getInd1());
    buff.append("Drugi indikator:  ");
    serializeFormat(buff, field.getInd2());
    for (int i = 0; i < field.getSubfields().size(); i++) {
      USubfield subfield = (USubfield)field.getSubfields().get(i);
      buff.append(lineSep);
      serializeFormat(buff, subfield);
    }
  }
  
  private void serializeFormat(StringBuffer buff, UIndicator ind) {
    if (ind == null) {
      buff.append("nema");
      buff.append(lineSep);
    } else {
      buff.append(lineSep);
      for (int i = 0; i < ind.getValues().size(); i++) {
        UItem item = (UItem)ind.getValues().get(i);
        buff.append("  ");
        buff.append(item.getCode());
        buff.append(": ");
        buff.append(item.getValue());
        buff.append(lineSep);
      }
    }
  }
  
  private void serializeFormat(StringBuffer buff, USubfield sf) {
    buff.append("Potpolje ");
    buff.append(sf.getOwner().getName());
    buff.append(sf.getName());
    buff.append(" - ");
    buff.append(sf.getDescription());
    buff.append(lineSep);
    buff.append(
        "-------------------------------------------");
    buff.append(lineSep);
    buff.append("Obavezno:   ");
    buff.append(dane(sf.isMandatory()));
    buff.append(lineSep);
    buff.append("Ponovljivo: ");
    buff.append(dane(sf.isRepeatable()));
    buff.append(lineSep);
    if (sf.getCoder() != null) {
      buff.append("\u0160ifrirano! ");
      buff.append(lineSep);
    }
    for (int i = 0; i < sf.getSubsubfields().size(); i++) {
      USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
      buff.append(lineSep);
      serializeFormat(buff, ssf);
    }
  }
  
  private void serializeFormat(StringBuffer buff, USubsubfield ssf) {
    buff.append("  Potpotpolje ");
    buff.append(ssf.getOwner().getOwner().getName());
    buff.append(ssf.getOwner().getName());
    buff.append(ssf.getName());
    buff.append(" - ");
    buff.append(ssf.getDescription());
    buff.append(lineSep);
    buff.append(
        "  - - - - - - - - - - - - - - -");
    buff.append(lineSep);
    buff.append("  Obavezno:   ");
    buff.append(dane(ssf.isMandatory()));
    buff.append(lineSep);
    buff.append("  Ponovljivo: ");
    buff.append(dane(ssf.isRepeatable()));
    buff.append(lineSep);
    if (ssf.getCoder() != null) {
      buff.append("\u0160ifrirano! ");
      buff.append(lineSep);
    }
  }
  
  private void serializeCoders(StringBuffer buff, USubfield sf, List extCod) {
    if (sf.getSubsubfields().size() > 0) {
      for (int i = 0; i < sf.getSubsubfields().size(); i++) {
        USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
        serializeCoders(buff, ssf, extCod);
      }
      return;
    }
    UCoder coder = sf.getCoder();
    if (coder == null)
      return;
    buff.append(lineSep);
    buff.append("Potpolje ");
    buff.append(sf.getOwner().getName());
    buff.append(sf.getName());
    buff.append(" - ");
    buff.append(sf.getDescription());
    buff.append(lineSep);
    buff.append("-------------------------------------------");
    buff.append(lineSep);
    if (coder.isExternal()) {
      if (!extCod.contains(coder))
        extCod.add(coder);
      buff.append("Koristi eksterni \u0161ifarnik: " + coder.getName());
      buff.append(lineSep);
      return;
    }
    writeCoderItems(buff, coder);
  }
  
  private void serializeCoders(StringBuffer buff, USubsubfield ssf, 
      List extCoders) {
    UCoder coder = ssf.getCoder();
    if (coder == null)
      return;
    buff.append(lineSep);
    buff.append("Potpotpolje ");
    buff.append(ssf.getOwner().getOwner().getName());
    buff.append(ssf.getOwner().getName());
    buff.append(ssf.getName());
    buff.append(" - ");
    buff.append(ssf.getDescription());
    buff.append(lineSep);
    buff.append("-------------------------------------------");
    buff.append(lineSep);
    if (coder.isExternal()) {
      if (!extCoders.contains(coder))
        extCoders.add(coder);
      buff.append("Koristi eksterni \u0161ifarnik: " + coder.getName());
      buff.append(lineSep);
      return;
    }
    writeCoderItems(buff, coder);
  }
  
  private void writeCoderItems(StringBuffer buff, UCoder coder) {
    Iterator it = coder.getItems().iterator();
    while (it.hasNext()) {
      UItem item = (UItem)it.next();
      buff.append(StringUtils.padChars(item.getCode(), ' ', 8));
      buff.append("  ");
      buff.append(item.getValue());
      buff.append(lineSep);
    }
  }
  
  private static String dane(boolean b) {
    return b ? "da" : "ne";
  }

  private String lineSep;
}
