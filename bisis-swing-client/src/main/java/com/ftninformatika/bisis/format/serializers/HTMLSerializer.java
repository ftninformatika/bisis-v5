package com.ftninformatika.bisis.format.serializers;

import com.ftninformatika.bisis.format.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Class HTMLSerializer comment.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class HTMLSerializer implements FormatSerializer {

  public static final int INITIAL_BUFFER_SIZE = 204800;

  public String getType() {
    return TYPE_HTML;
  }
  
  public String serializeFormat(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");
    tocItems = new ArrayList();
    
    retVal.append("<html><head><title>Specifikacija formata</title>");
    retVal.append(lineSep);
    retVal.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
    retVal.append(lineSep);
    retVal.append("</head><body bgcolor=\"white\" style=\"font-size: 10pt;\">");
    retVal.append(lineSep);
    
    retVal.append("<h1>Identifikator formata: ");
    retVal.append(fieldSet.getName());
    retVal.append("<br/>");
    retVal.append("Naziv formata: ");
    retVal.append(fieldSet.getDescription());
    retVal.append("</h1>");
    retVal.append(lineSep);

    StringBuffer buff = new StringBuffer(INITIAL_BUFFER_SIZE);
    
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      retVal.append(lineSep);
      serializeFormat(buff, field);
    }
    
    retVal.append("<h2>Sadr\u017eaj</h2><p>");
    for (int i = 0; i < tocItems.size(); i++) {
      String line = (String)tocItems.get(i);
      retVal.append(line);
    }
    retVal.append("</p>");
    
    retVal.append(buff);
    
    retVal.append(lineSep);
    retVal.append("</body></html>");
    retVal.append(lineSep);
    return retVal.toString();
  }
  
  public String serializeCoders(UFormat fieldSet) {
    StringBuffer retVal = new StringBuffer(INITIAL_BUFFER_SIZE);
    lineSep = System.getProperty("line.separator");
    tocItems = new ArrayList();
    
    retVal.append("<html><head><title>Sadržaj šifarnika</title>");
    retVal.append(lineSep);
    retVal.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
    retVal.append(lineSep);
    retVal.append("</head><body bgcolor=\"white\" style=\"font-size: 10pt;\">");
    retVal.append(lineSep);
    
    retVal.append("<h1>Identifikator formata: ");
    retVal.append(fieldSet.getName());
    retVal.append("<br/>");
    retVal.append("Naziv formata: ");
    retVal.append(fieldSet.getDescription());
    retVal.append("</h1>");
    retVal.append(lineSep);

    StringBuffer buff = new StringBuffer(INITIAL_BUFFER_SIZE);
    List externalCoders = new ArrayList();
    for (int i = 0; i < fieldSet.getFields().size(); i++) {
      UField field = (UField)fieldSet.getFields().get(i);
      for (int j = 0; j < field.getSubfields().size(); j++) {
        USubfield sf = (USubfield)field.getSubfields().get(j);
        serializeCoders(buff, sf, externalCoders);
      }
    }
    
    Iterator it = externalCoders.iterator();
    while (it.hasNext()) {
      UCoder coder = (UCoder)it.next();
      retVal.append(lineSep);
      String title = "Eksterni \u0161ifarnik: " + coder.getName();
      String id = "ext" + coder.getType();
      tocItems.add("<a href=\"#" + id + "\">" + title + "</a><br/>");
      
      buff.append("<h3><a name=\""+id+"\">"+title+"</a></h3>");
      buff.append(lineSep);
      writeCoderItems(buff, coder);
    }
    
    
    retVal.append("<h2>Sadr\u017eaj</h2><p>");
    for (int i = 0; i < tocItems.size(); i++) {
      String line = (String)tocItems.get(i);
      retVal.append(line);
    }
    retVal.append("</p>");
    
    retVal.append(buff);
    
    retVal.append(lineSep);
    retVal.append("</body></html>");
    retVal.append(lineSep);
    return retVal.toString();
  }
  
  private void serializeFormat(StringBuffer buff, UField field) {
    String id = field.getName();
    tocItems.add("<a href=\"#f"+id+"\">"+id+" - "+field.getDescription() + "</a><br/>");
    
    buff.append("<a name=\"f");
    buff.append(id);
    buff.append("\">");
    buff.append("<h2>Polje ");
    buff.append(field.getName());
    buff.append(" - ");
    buff.append(field.getDescription());
    buff.append("</h2></a>");
    buff.append(lineSep);
    buff.append("<p>Obavezno:   ");
    buff.append(dane(field.isMandatory()));
    buff.append("<br/>Ponovljivo: ");
    buff.append(dane(field.isRepeatable()));
    buff.append("<br/>Sekundarno: ");
    buff.append(dane(field.isSecondary()));
    buff.append("</p>");
    buff.append(lineSep);
    buff.append("<p>Prvi indikator:  ");
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
      buff.append("nema<br/>");
      buff.append(lineSep);
    } else {
      buff.append("<br/>");
      buff.append(lineSep);
      buff.append("<table cellspacing=\"1\" cellpadding=\"2\" border=\"0\" bgcolor=\"lightgrey\" style=\"left-indent: 40px;\">");
      for (int i = 0; i < ind.getValues().size(); i++) {
        UItem item = (UItem)ind.getValues().get(i);
        buff.append("<tr><td bgcolor=\"white\">");
        buff.append(item.getCode());
        buff.append("</td><td bgcolor=\"white\">");
        buff.append(item.getValue());
        buff.append("</td></tr>");
      }
      buff.append("</table><br/>");
      buff.append(lineSep);
    }
  }

  private void serializeFormat(StringBuffer buff, USubfield sf) {
    String id = sf.getOwner().getName() + sf.getName();
    tocItems.add("&nbsp;&nbsp;&nbsp;<a href=\"#sf"+id+"\">"+id+" - "+sf.getDescription()+"</a><br/>");

    buff.append("<a name=\"sf");
    buff.append(id);
    buff.append("\">");
    buff.append("<h3>Potpolje ");
    buff.append(sf.getOwner().getName());
    buff.append(sf.getName());
    buff.append(" - ");
    buff.append(sf.getDescription());
    buff.append("</h3></a>");
    buff.append(lineSep);
    buff.append("<p>Obavezno:   ");
    buff.append(dane(sf.isMandatory()));
    buff.append("<br/>Ponovljivo: ");
    buff.append(dane(sf.isRepeatable()));
    if (sf.getCoder() != null) {
      buff.append("<br/>\u0160ifrirano! ");
    }
    buff.append("</p>");
    for (int i = 0; i < sf.getSubsubfields().size(); i++) {
      USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
      buff.append(lineSep);
      serializeFormat(buff, ssf);
    }
  }

  private void serializeFormat(StringBuffer buff, USubsubfield ssf) {
    String id = ssf.getOwner().getOwner().getName() + 
      ssf.getOwner().getName() + ssf.getName();
    tocItems.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#ssf"+id+
        "\">"+id+" - "+ssf.getDescription()+"</a><br/>");

    buff.append("<a name=\"ssf");
    buff.append(id);
    buff.append("\">");
    buff.append("<h4>Potpotpolje ");
    buff.append(id);
    buff.append(" - ");
    buff.append(ssf.getDescription());
    buff.append("</h4></a>");
    buff.append(lineSep);
    buff.append("<p>Obavezno:   ");
    buff.append(dane(ssf.isMandatory()));
    buff.append("<br/>Ponovljivo: ");
    buff.append(dane(ssf.isRepeatable()));
    if (ssf.getCoder() != null) {
      buff.append("<br/>\u0160ifrirano! ");
      buff.append(lineSep);
    }
    buff.append("</p>");
    buff.append(lineSep);
  }

  private static String dane(boolean b) {
    return b ? "da" : "ne";
  }
  
  private void serializeCoders(StringBuffer buff, USubfield sf, List extCoders){
    if (sf.getSubsubfields().size() > 0) {
      for (int i = 0; i < sf.getSubsubfields().size(); i++) {
        USubsubfield ssf = (USubsubfield)sf.getSubsubfields().get(i);
        serializeCoders(buff, ssf, extCoders);
      }
      return;
    }
    UCoder coder = sf.getCoder();
    if (coder == null)
      return;
    String id = sf.getOwner().getName() + sf.getName();
    tocItems.add("&nbsp;&nbsp;&nbsp;<a href=\"#sf"+id+"\">"+id+" - "+sf.getDescription()+"</a><br/>");
    buff.append("<a name=\"sf");
    buff.append(id);
    buff.append("\">");
    buff.append("<h3>Potpolje ");
    buff.append(sf.getOwner().getName());
    buff.append(sf.getName());
    buff.append(" - ");
    buff.append(sf.getDescription());
    buff.append("</h3></a>");
    buff.append(lineSep);
    if (coder.isExternal()) {
      if (!extCoders.contains(coder))
        extCoders.add(coder);
      buff.append("<p>Koristi eksterni \u0161ifarnik: ");
      buff.append(coder.getName());
      buff.append("</p>");
      buff.append(lineSep);
      return;
    }
    writeCoderItems(buff, coder);
  }
  
  private void serializeCoders(StringBuffer buff, USubsubfield ssf, List extCoders) {
    UCoder coder = ssf.getCoder();
    if (coder == null)
      return;

    String id = ssf.getOwner().getOwner().getName() + 
    ssf.getOwner().getName() + ssf.getName();
    tocItems.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#ssf"+id+
      "\">"+id+" - "+ssf.getDescription()+"</a><br/>");
	  buff.append("<a name=\"ssf");
	  buff.append(id);
	  buff.append("\">");
	  buff.append("<h3>Potpotpolje ");
	  buff.append(id);
	  buff.append(" - ");
	  buff.append(ssf.getDescription());
	  buff.append("</h3></a>");
	  buff.append(lineSep);
    if (coder.isExternal()) {
      if (!extCoders.contains(coder))
        extCoders.add(coder);
      buff.append("<p>Koristi eksterni \u0161ifarnik: ");
      buff.append(coder.getName());
      buff.append("</p>");
      buff.append(lineSep);
      return;
    }
    writeCoderItems(buff, coder);
  }
  
  private void writeCoderItems(StringBuffer buff, UCoder coder) {
    buff.append("<table cellspacing=\"1\" cellpadding=\"4\" border=\"0\" bgcolor=\"#000000\">");
    buff.append(lineSep);
    Iterator it = coder.getItems().iterator();
    while (it.hasNext()) {
      UItem item = (UItem)it.next();
      buff.append("<tr bgcolor=\"#FFFFFF\">");
      buff.append("<td align=\"center\">");
      buff.append(item.getCode());
      buff.append("</td><td>");
      buff.append(item.getValue());
      buff.append("</td></tr>");
      buff.append(lineSep);
    }
    buff.append("</table>");
    buff.append(lineSep);
  }

  private String lineSep;
  private List tocItems;
}
