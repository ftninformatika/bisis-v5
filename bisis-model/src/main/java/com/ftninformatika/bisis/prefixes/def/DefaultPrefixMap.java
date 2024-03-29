package com.ftninformatika.bisis.prefixes.def;

import com.ftninformatika.bisis.prefixes.PrefixMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a default prefix map.
 *
 * @author mbranko@uns.ac.rs
 */
public class DefaultPrefixMap implements PrefixMap {

  /**
   * Returns a list of prefix names (strings) mapped to the given subfield.
   *
   * @param subfieldName the four-character name of the subfield.
   * @return The list of corresponding prefixes.
   * @see com.ftninformatika.bisis.prefixes.PrefixMap#getPrefixes(String)
   */
  public List<String> getPrefixes(String subfieldName) {
    List<String> list = prefixMap.get(subfieldName);
    if (list == null)
      return new ArrayList<>();
    else
      return list;
  }

  /**
   * Returns a list of subfield names (strings) mapped to the given prefix.
   *
   * @param prefix the two-character name of the prefix
   * @return The list of corresponding subfields (four-character names)
   */
  public List<String> getSubfields(String prefix) {
    List<String> list = subfieldMap.get(prefix);
    if (list == null)
      return new ArrayList<>();
    else
      return list;
  }

  public DefaultPrefixMap() {
    prefixMap = new HashMap<>();
    subfieldMap = new HashMap<>();
    addToMap("BN", "010a");
    addToMap("BN", "010z");
    addToMap("CD", "040a");
    addToMap("CO", "102a");
    addToMap("CC", "105b");
    addToMap("CL", "225a");
    addToMap("CL", "225e");
    addToMap("CL", "225i");
    addToMap("CL", "225h");
    addToMap("AT", "531a");
    addToMap("AT", "531b");
    addToMap("AU", "700a");
    addToMap("AU", "700b");
    addToMap("AU", "701a");
    addToMap("AU", "701b");
    addToMap("AU", "702a");
    addToMap("AU", "702b");
    addToMap("CS", "710a");
    addToMap("CS", "710b");
    addToMap("CP", "710e");
    addToMap("CP", "711e");
    addToMap("CS", "711a");
    addToMap("CS", "711b");
    addToMap("CS", "712a");
    addToMap("CS", "712b");
    addToMap("CP", "712e");
    addToMap("AU", "900a");
    addToMap("AU", "900b");
    addToMap("AU", "901a");
    addToMap("AU", "901b");
    addToMap("AU", "902a");
    addToMap("AU", "902b");
    addToMap("BI", "992b");









    addToMap("CS", "503a");
    addToMap("CS", "503b");

    addToMap("CS", "910a");
    addToMap("CS", "910b");
    addToMap("CP", "910e");
    addToMap("CS", "911a");
    addToMap("CP", "911e");
    addToMap("CS", "911b");
    addToMap("CS", "912a");
    addToMap("CS", "912b");
    addToMap("CP", "912e");

    addToMap("DC", "675a");
    addToMap("DC", "675u");
    addToMap("DR", "328f");

    addToMap("DT", "001c");
    addToMap("FC", "7008");
    addToMap("FC", "7018");
    addToMap("FC", "7028");
    addToMap("FD", "7009");
    addToMap("FD", "7019");
    addToMap("FD", "7029");
    addToMap("FD", "9009");
    addToMap("FD", "9019");
    addToMap("FD", "9029");
    addToMap("FQ", "110b");
    addToMap("GM", "200b");
    addToMap("IC", "105a");
    addToMap("KW", "200a");
    addToMap("KW", "200c");
    addToMap("KW", "200d");
    addToMap("KW", "200e");
    addToMap("KW", "200i");
    addToMap("KW", "327a");
    addToMap("KW", "330a");
    addToMap("KW", "500a");
    addToMap("KW", "501a");
    addToMap("KW", "510a");
    addToMap("KW", "511a");
    addToMap("KW", "512a");
    addToMap("KW", "513a");
    addToMap("KW", "514a");
    addToMap("KW", "515a");
    addToMap("KW", "516a");
    addToMap("KW", "517a");
    addToMap("KW", "530a");
    addToMap("KW", "531a");
    addToMap("KW", "532a");
    addToMap("KW", "540a");
    addToMap("KW", "6006");
    addToMap("KW", "600a");
    addToMap("KW", "600b");
    addToMap("KW", "600c");
    addToMap("KW", "600d");
    addToMap("KW", "600f");
    addToMap("KW", "600w");
    addToMap("KW", "600x");
    addToMap("KW", "600y");
    addToMap("KW", "600z");
    addToMap("KW", "6016");
    addToMap("KW", "601a");
    addToMap("KW", "601b");
    addToMap("KW", "601c");
    addToMap("KW", "601d");
    addToMap("KW", "601e");
    addToMap("KW", "601f");
    addToMap("KW", "601g");
    addToMap("KW", "601h");
    addToMap("KW", "601w");
    addToMap("KW", "601x");
    addToMap("KW", "601y");
    addToMap("KW", "601z");
    addToMap("KW", "6026");
    addToMap("KW", "602a");
    addToMap("KW", "602f");
    addToMap("KW", "602w");
    addToMap("KW", "602x");
    addToMap("KW", "602y");
    addToMap("KW", "602z");
    addToMap("KW", "6056");
    addToMap("KW", "605a");
    addToMap("KW", "605h");
    addToMap("KW", "605i");
    addToMap("KW", "605k");
    addToMap("KW", "605l");
    addToMap("KW", "605n");
    addToMap("KW", "605q");
    addToMap("KW", "605w");
    addToMap("KW", "605x");
    addToMap("KW", "605y");
    addToMap("KW", "605z");
    addToMap("KW", "6062");
    addToMap("KW", "6066");
    addToMap("KW", "606a");
    addToMap("KW", "606w");
    addToMap("KW", "606x");
    addToMap("KW", "606y");
    addToMap("KW", "606z");
    addToMap("KW", "6076");
    addToMap("KW", "607a");
    addToMap("KW", "607w");
    addToMap("KW", "607x");
    addToMap("KW", "607y");
    addToMap("KW", "607z");
    addToMap("KW", "6086");
    addToMap("KW", "608a");
    addToMap("KW", "608w");
    addToMap("KW", "608x");
    addToMap("KW", "608y");
    addToMap("KW", "608z");
    addToMap("KW", "6096");
    addToMap("KW", "609a");
    addToMap("KW", "609w");
    addToMap("KW", "609x");
    addToMap("KW", "609y");
    addToMap("KW", "609z");
    addToMap("KW", "610a");
    addToMap("KW", "610b");
    addToMap("KW", "627a");
    addToMap("LA", "101a");
    addToMap("LC", "105f");
    addToMap("LA", "101c");
    addToMap("PP", "210a");
    addToMap("PU", "210c");
    addToMap("PY", "100c");
    addToMap("P2", "100d");
    addToMap("RE", "102b");
    addToMap("RJ", "391a");
    addToMap("RS", "001a");
    addToMap("RT", "001b");
    addToMap("SC", "011y");
    addToMap("SK", "992a");
    addToMap("SN", "011a");
    addToMap("SN", "011z");
    addToMap("SP", "011e");
    addToMap("SP", "011c");
    addToMap("SS", "100b");
    addToMap("SU", "200e");
    addToMap("SU", "512e");
    addToMap("TI", "200a");
    addToMap("TI", "200c");
    addToMap("TI", "200d");
    addToMap("TI", "200i");
    addToMap("TI", "327a");
    addToMap("TI", "328a");
    addToMap("TI", "500a");
    addToMap("TI", "501a");
    addToMap("TI", "510a");
    addToMap("TI", "511a");
    addToMap("TI", "512a");
    addToMap("TI", "512e");
    addToMap("TI", "513a");
    addToMap("TI", "514a");
    addToMap("TI", "515a");
    addToMap("TI", "516a");
    addToMap("TI", "517a");
    addToMap("TI", "520a");
    addToMap("TI", "530a");
    addToMap("TI", "530b");
    addToMap("TI", "531a");
    addToMap("TI", "531b");
    addToMap("TI", "531c");
    addToMap("TI", "532a");
    addToMap("TI", "540a");
    addToMap("TI", "541a");
    addToMap("TP", "105i");
    addToMap("TY", "110a");
    addToMap("UG", "675b");
    addToMap("US", "675s");
    addToMap("Y1", "328d");
    addToMap("Y2", "328e");
    addToMap("SI", "998b");
    addToMap("ES", "205a");
    addToMap("ES", "205d");
    addToMap("ES", "205f");
    addToMap("ES", "205g");
    addToMap("ES", "205b");
    addToMap("GS", "300a");
    addToMap("II", "320a");
    addToMap("NM", "210g");
    addToMap("NT", "200h");
    addToMap("NT", "200v");
    addToMap("PA", "210b");
    addToMap("PM", "210e");
    addToMap("AB", "330a");
    addToMap("CB", "601a");
    addToMap("CB", "601b");
    addToMap("CB", "601c");
    addToMap("CB", "601d");
    addToMap("CB", "601e");
    addToMap("CB", "601f");
    addToMap("CB", "601g");
    addToMap("CB", "601h");
    addToMap("CB", "601w");

    addToMap("CB", "961a");
    addToMap("CB", "961b");
    addToMap("CB", "961c");
    addToMap("CB", "961d");
    addToMap("CB", "961e");
    addToMap("CB", "961f");
    addToMap("CB", "961g");
    addToMap("CB", "961h");
    addToMap("CB", "961w");
    addToMap("CH", "608a");
    addToMap("CH", "608w");
    addToMap("CH", "608x");
    addToMap("CH", "608y");
    addToMap("CH", "608z");
    addToMap("CH", "968a");
    addToMap("CH", "968w");
    addToMap("CH", "968x");
    addToMap("CH", "968y");
    addToMap("CH", "968z");
    addToMap("CN", "327a");
    addToMap("FN", "602a");
    addToMap("FN", "602f");
    addToMap("FN", "602w");
    addToMap("FN", "602x");
    addToMap("FN", "602y");
    addToMap("FN", "602z");
    addToMap("FN", "602a");
    addToMap("FN", "602f");
    addToMap("FN", "962w");
    addToMap("FN", "962x");
    addToMap("FN", "962y");
    addToMap("FN", "962z");
    addToMap("FS", "609a");
    addToMap("FS", "609w");
    addToMap("FS", "609x");
    addToMap("FS", "609y");
    addToMap("FS", "609z");
    addToMap("FS", "969a");
    addToMap("FS", "969w");
    addToMap("FS", "969x");
    addToMap("FS", "969y");
    addToMap("FS", "969z");
    addToMap("GN", "607a");
    addToMap("GN", "607w");
    addToMap("GN", "607x");
    addToMap("GN", "607y");
    addToMap("GN", "607z");
    addToMap("GN", "967a");
    addToMap("GN", "967w");
    addToMap("GN", "967x");
    addToMap("GN", "967y");
    addToMap("GN", "967z");
    addToMap("KT", "530a");
    addToMap("PN", "600a");
    addToMap("PN", "600b");
    addToMap("PN", "600c");
    addToMap("PN", "600d");
    addToMap("PN", "600e");
    addToMap("PN", "600f");
    addToMap("PN", "600w");
    addToMap("PN", "600x");
    addToMap("PN", "600y");
    addToMap("PN", "960a");
    addToMap("PN", "960b");
    addToMap("PN", "960c");
    addToMap("PN", "960d");
    addToMap("PN", "960e");
    addToMap("PN", "960f");
    addToMap("PN", "960w");
    addToMap("PN", "960x");
    addToMap("PN", "960y");
    addToMap("TN", "606a");
    addToMap("TN", "606w");
    addToMap("TN", "606x");
    addToMap("TN", "606y");
    addToMap("TN", "606z");
    addToMap("TN", "966a");
    addToMap("TN", "966w");
    addToMap("TN", "966x");
    addToMap("TN", "966y");
    addToMap("TN", "966z");
    addToMap("TS", "605a");
    addToMap("TS", "605h");
    addToMap("TS", "605i");
    addToMap("TS", "605k");
    addToMap("TS", "605l");
    addToMap("TS", "605m");
    addToMap("TS", "605n");
    addToMap("TS", "605q");
    addToMap("TS", "605w");
    addToMap("TS", "965a");
    addToMap("TS", "965h");
    addToMap("TS", "965i");
    addToMap("TS", "965k");
    addToMap("TS", "965l");
    addToMap("TS", "965m");
    addToMap("TS", "965n");
    addToMap("TS", "965q");
    addToMap("TS", "965w");
    addToMap("UT", "610a");
    addToMap("UT", "610b");
    addToMap("RN", "001e");
    addToMap("MR", "4741");
    addToMap("TA", "7004");
    addToMap("TA", "7014");
    addToMap("TA", "7024");
    addToMap("TA", "9004");
    addToMap("TA", "9014");
    addToMap("TA", "9024");
    //predmetne odrednice
    addToMap("SB", "6006");
    addToMap("SB", "600a");
    addToMap("SB", "600b");
    addToMap("SB", "600c");
    addToMap("SB", "600d");
    addToMap("SB", "600f");
    addToMap("SB", "600w");
    addToMap("SB", "600x");
    addToMap("SB", "600y");
    addToMap("SB", "600z");
    addToMap("SB", "6016");
    addToMap("SB", "601a");
    addToMap("SB", "601b");
    addToMap("SB", "601c");
    addToMap("SB", "601d");
    addToMap("SB", "601e");
    addToMap("SB", "601f");
    addToMap("SB", "601g");
    addToMap("SB", "601h");
    addToMap("SB", "601w");
    addToMap("SB", "601x");
    addToMap("SB", "601y");
    addToMap("SB", "601z");
    addToMap("SB", "6026");
    addToMap("SB", "602a");
    addToMap("SB", "602f");
    addToMap("SB", "602w");
    addToMap("SB", "602x");
    addToMap("SB", "602y");
    addToMap("SB", "602z");
    addToMap("SB", "6056");
    addToMap("SB", "605a");
    addToMap("SB", "605h");
    addToMap("SB", "605i");
    addToMap("SB", "605k");
    addToMap("SB", "605l");
    addToMap("SB", "605m");
    addToMap("SB", "605n");
    addToMap("SB", "605q");
    addToMap("SB", "605w");
    addToMap("SB", "605x");
    addToMap("SB", "605y");
    addToMap("SB", "605z");
    addToMap("SB", "6066");
    addToMap("SB", "606a");
    addToMap("SB", "606x");
    addToMap("SB", "606y");
    addToMap("SB", "606z");
    addToMap("SB", "606w");
    addToMap("SB", "6076");
    addToMap("SB", "607a");
    addToMap("SB", "607x");
    addToMap("SB", "607y");
    addToMap("SB", "607z");
    addToMap("SB", "607w");
    addToMap("SB", "6086");
    addToMap("SB", "608a");
    addToMap("SB", "608x");
    addToMap("SB", "608y");
    addToMap("SB", "608z");
    addToMap("SB", "608w");
    addToMap("SB", "6096");
    addToMap("SB", "609a");
    addToMap("SB", "609x");
    addToMap("SB", "609y");
    addToMap("SB", "609z");
    addToMap("SB", "609w");
    addToMap("SB", "610a");
    addToMap("SB", "9606");
    addToMap("SB", "960a");
    addToMap("SB", "960b");
    addToMap("SB", "960c");
    addToMap("SB", "960d");
    addToMap("SB", "960f");
    addToMap("SB", "960x");
    addToMap("SB", "960y");
    addToMap("SB", "960z");
    addToMap("SB", "960w");
    addToMap("SB", "9616");
    addToMap("SB", "961a");
    addToMap("SB", "961b");
    addToMap("SB", "961c");
    addToMap("SB", "961d");
    addToMap("SB", "961e");
    addToMap("SB", "961f");
    addToMap("SB", "961g");
    addToMap("SB", "961h");
    addToMap("SB", "961x");
    addToMap("SB", "961y");
    addToMap("SB", "961z");
    addToMap("SB", "961w");
    addToMap("SB", "9626");
    addToMap("SB", "962a");
    addToMap("SB", "962f");
    addToMap("SB", "962x");
    addToMap("SB", "962y");
    addToMap("SB", "962z");
    addToMap("SB", "962w");
    addToMap("SB", "9656");
    addToMap("SB", "965a");
    addToMap("SB", "965h");
    addToMap("SB", "965i");
    addToMap("SB", "965k");
    addToMap("SB", "965l");
    addToMap("SB", "965m");
    addToMap("SB", "965n");
    addToMap("SB", "965q");
    addToMap("SB", "965x");
    addToMap("SB", "965y");
    addToMap("SB", "965z");
    addToMap("SB", "965w");
    addToMap("SB", "9666");
    addToMap("SB", "966a");
    addToMap("SB", "966x");
    addToMap("SB", "966y");
    addToMap("SB", "966z");
    addToMap("SB", "966w");
    addToMap("SB", "9676");
    addToMap("SB", "967a");
    addToMap("SB", "967x");
    addToMap("SB", "967y");
    addToMap("SB", "967z");
    addToMap("SB", "967w");
    addToMap("SB", "9686");
    addToMap("SB", "968a");
    addToMap("SB", "968x");
    addToMap("SB", "968y");
    addToMap("SB", "968z");
    addToMap("SB", "968w");
    addToMap("SB", "9696");
    addToMap("SB", "969a");
    addToMap("SB", "969x");
    addToMap("SB", "969y");
    addToMap("SB", "969z");
    addToMap("SB", "969w");

    //predmetne pododrednice
    addToMap("SD", "600w");
    addToMap("SD", "600x");
    addToMap("SD", "600y");
    addToMap("SD", "600z");

    addToMap("SD", "601w");
    addToMap("SD", "601x");
    addToMap("SD", "601y");
    addToMap("SD", "601z");

    addToMap("SD", "602w");
    addToMap("SD", "602x");
    addToMap("SD", "602y");
    addToMap("SD", "602z");

    addToMap("SD", "605l");
    addToMap("SD", "605w");
    addToMap("SD", "605x");
    addToMap("SD", "605y");
    addToMap("SD", "605z");

    addToMap("SD", "606w");
    addToMap("SD", "606x");
    addToMap("SD", "606y");
    addToMap("SD", "606z");

    addToMap("SD", "607w");
    addToMap("SD", "607x");
    addToMap("SD", "607y");
    addToMap("SD", "607z");

    addToMap("SD", "608w");
    addToMap("SD", "608x");
    addToMap("SD", "608y");
    addToMap("SD", "608z");

    addToMap("SD", "609w");
    addToMap("SD", "609x");
    addToMap("SD", "609y");
    addToMap("SD", "609z");

    //vrsta autorstva
    addToMap("VA", "702a");
    addToMap("VA", "702b");
    addToMap("VA", "702c");
    addToMap("VA", "902a");
    addToMap("VA", "902b");
    addToMap("VA", "902c");

    //za korisnika 
    //PI se razlikuje od AU jer ne sadrzi 702 i 902
    addToMap("PI", "700a");
    addToMap("PI", "700b");
    addToMap("PI", "700c");
    addToMap("PI", "701a");
    addToMap("PI", "701b");
    addToMap("PI", "701c");
    addToMap("PI", "900a");
    addToMap("PI", "900b");
    addToMap("PI", "900c");
    addToMap("PI", "901a");
    addToMap("PI", "901b");
    addToMap("PI", "901c");

  }

  private void addToMap(String prefix, String subfield) {
    List<String> list = prefixMap.get(subfield);
    if (list == null) {
      list = new ArrayList<String>();
      prefixMap.put(subfield, list);
    }
    list.add(prefix);

    List<String> sfList = subfieldMap.get(prefix);
    if (sfList == null) {
      sfList = new ArrayList<String>();
      subfieldMap.put(prefix, sfList);
    }
    sfList.add(subfield);

  }

  private Map<String, List<String>> prefixMap;
  private Map<String, List<String>> subfieldMap;
}
