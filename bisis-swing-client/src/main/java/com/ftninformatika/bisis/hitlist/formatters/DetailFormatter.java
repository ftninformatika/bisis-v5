package com.ftninformatika.bisis.hitlist.formatters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.ftninformatika.bisis.prefixes.PrefixConfigFactory;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.prefixes.PrefixValue;
import com.ftninformatika.bisis.records.Record;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Formats records in the detailed format.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class DetailFormatter implements RecordFormatter {
  
  public DetailFormatter() {
    try {
      ResourceBundle rb = PropertyResourceBundle.getBundle(
          DetailFormatter.class.getName());
      String sPrefList = rb.getString("DISPLAYED_PREFIXES");
      StringTokenizer st = new StringTokenizer(sPrefList, ", \t");
      prefList = new ArrayList();
      while (st.hasMoreTokens()) {
        prefList.add(st.nextToken());
      }
    } catch (Exception ex) {
      log.fatal(ex);
    }
  }

  public String toASCII(Record record, String locale) {
    StringBuffer retVal = new StringBuffer();
    List prefixes = PrefixConverter.toPrefixes(record, null);
    List prefixNames = PrefixConfigFactory.getPrefixConfig().
        getPrefixNames(new Locale(locale));
    for (int i = 0; i < prefList.size(); i++) {
      String prefix = (String)prefList.get(i);
      String prefName = getPrefixDesc(prefix, prefixNames);
      retVal.append(prefName);
      Iterator it = prefixes.iterator();
      int j = 0;
      while (it.hasNext()) {
        PrefixValue pv = (PrefixValue)it.next();
        if (pv.prefName.equals(prefix)) {
          if (j > 0)
            retVal.append("      ");
          else
            retVal.append("    ");
          retVal.append(pv.value);
          retVal.append("\n");
          j++;
        }
      }
      retVal.append("\n");
    }
    return retVal.toString();
  }

  public String toHTML(Record record, String locale) {
    StringBuffer retVal = new StringBuffer();
    List prefixes = PrefixConverter.toPrefixes(record, null);
    List prefixNames = PrefixConfigFactory.getPrefixConfig().
        getPrefixNames(new Locale(locale));
    retVal.append("<table cellspacing=\"0\" cellpadding=\"1\" border=\"0\">");
    for (int i = 0; i < prefList.size(); i++) {
      String prefix = (String)prefList.get(i);
      String prefName = getPrefixDesc(prefix, prefixNames);
      retVal.append("<tr><td valign=\"top\"><i>");
      retVal.append(prefName);
      retVal.append("</i>&nbsp;</td><td valign=\"top\">");

      Iterator it = prefixes.iterator();
      int j = 0;
      while (it.hasNext()) {
        PrefixValue pv = (PrefixValue)it.next();
        if (pv.prefName.equals(prefix)) {
          if (j > 0)
            retVal.append("<br>");
          retVal.append(pv.value);
          j++;
        }
      }
      retVal.append("</td></tr>");
    }

    retVal.append("</table>");
    return retVal.toString();
  }
  
  public String getPrefixDesc(String prefName, List prefixNames) {
    Iterator it = prefixNames.iterator();
    while (it.hasNext()) {
      PrefixValue pv = (PrefixValue)it.next();
      if (pv.prefName.equals(prefName))
        return pv.value;
    }
    return null;
  }

  private List prefList;
  
  private static Log log = LogFactory.getLog(
      "com.gint.app.bisis.web.formatters.DetailFormatter");
}
