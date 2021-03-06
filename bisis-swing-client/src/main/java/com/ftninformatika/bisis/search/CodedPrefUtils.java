/**
 *
 */
package com.ftninformatika.bisis.search;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.bisis.prefixes.PrefixConfigFactory;
import com.ftninformatika.bisis.prefixes.def.DefaultPrefixMap;

import java.util.List;

/**
 * @author dimicb
 */
public class CodedPrefUtils {

  public static boolean isPrefCoded(String pref) {
    return getCodesForPrefix(pref) != null;
  }

  public static List<UItem> getCodesForPrefix(String pref) {
    List<UItem> items = null;
    if (pref.length() > 1 && isNumber(pref.substring(0, 2))) {  //prefiks je ustvari potpolje
      USubfield usf = PubTypes.getFormat().getSubfield(pref);
      if (usf != null && usf.getCoder() != null)
        return usf.getCoder().getItems();
    }
    if (pref.equals("AM") || pref.equals("ST") || pref.equals("LI")) {
      // prefiksi za lokacijske podatke
      if (pref.equals("AM"))
        return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().NACINNABAVKE_CODER);
      if (pref.equals("ST"))
        return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().STATUS_CODER);
      if (pref.equals("LI"))
        return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().DOSTUPNOST_CODER);

    }

    // pravi prefiks koji sadrzi vise potpolja
    DefaultPrefixMap prefMap =
        (DefaultPrefixMap) PrefixConfigFactory.getPrefixConfig().getPrefixMap();
    List<String> sfList = prefMap.getSubfields(pref);
    for (String sfName : sfList) {
      USubfield usf = PubTypes.getFormat().getSubfield(sfName);
      if (usf != null && usf.getCoder() != null)
        if (items == null)
          items = usf.getCoder().getItems();
        else {
          for (UItem it : usf.getCoder().getItems())
            if (!items.contains(it))
              items.add(it);
        }
    }

    return items;
  }

  private static String toJson(List<UItem> items) {
    StringBuffer sb = new StringBuffer();
    sb.append("[\n");
    for (UItem i: items)
      sb.append(i.toJsonString());
    sb.append("]\n");
    return sb.toString();
  }

  private static boolean isNumber(String str) {
    try {
      Integer.valueOf(str);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }



}
