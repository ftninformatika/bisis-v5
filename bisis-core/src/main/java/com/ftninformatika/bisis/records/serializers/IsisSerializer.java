package com.ftninformatika.bisis.records.serializers;

import java.util.Iterator;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.gint.util.string.Signature;
import com.gint.util.string.StringUtils;

/**
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class IsisSerializer {

  public static String toISISFormat(Record record) {
    StringBuffer buff = new StringBuffer(1024);
    buff.append("! ID ");
    buff.append(StringUtils.padChars(Integer.toString(record.getRecordID()),
        '0', 6));
    buff.append('\n');
    Iterator it = record.getFields().iterator();
    while (it.hasNext()) {
      Field f = (Field)it.next();
     // if (PubTypes.getFormat().containsSecondaryFields(f.getName()))
       // continue;
      buff.append("!v");
      buff.append(f.getName());
      buff.append('!');
      Iterator it2 = f.getSubfields().iterator(); 
      while (it2.hasNext()) {
        Subfield sf = (Subfield)it2.next();
        buff.append('^');
        buff.append(sf.getName());
        if ((f.getName().equals("996") || f.getName().equals("997")) && sf.getName() == 'd')
          buff.append(Signature.userDisplay(sf.getContent()));
        else
          buff.append(sf.getContent());
      }
      buff.append('\n');
    }
    return buff.toString();
  }
  
}
