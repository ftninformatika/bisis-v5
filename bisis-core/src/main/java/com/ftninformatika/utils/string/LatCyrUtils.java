package com.ftninformatika.utils.string;

import java.text.Normalizer;

/**
 * Latin-Cyrillic script conversion - for Serbian language only.
 * 
 * @author mbranko@uns.ns.ac.yu
 */
public class LatCyrUtils {
  
  /**
   * Converts a string with cyrillic text to latin.
   * 
   * @param s String to be converted
   * @return The converted string
   */
  public static String toLatin(String s) {
    String t = s;
    for (int i = 0; i < cyr.length; i++)
      t = t.replace(cyr[i], lat[i]);
    t = StringUtils.replace(t, "\u0409", "LJ");
    t = StringUtils.replace(t, "\u040A", "NJ");
    t = StringUtils.replace(t, "\u040F", "D\u017D");
    t = StringUtils.replace(t, "\u0459", "lj");
    t = StringUtils.replace(t, "\u045A", "nj");
    t = StringUtils.replace(t, "\u045F", "d\u017E");
    return t;
  }
  
  /**
   * Converts a string with latin text to cyrillic.
   * 
   * @param s String to be converted
   * @return The converted string
   */
  public static String toCyrillic(String s) {
    String t = s;
    t = StringUtils.replace(t, "LJ", "\u0409");
    t = StringUtils.replace(t, "Lj", "\u0409");
    t = StringUtils.replace(t, "NJ", "\u040A");
    t = StringUtils.replace(t, "Nj", "\u040A");
    t = StringUtils.replace(t, "D\u017D", "\u040F");
    t = StringUtils.replace(t, "D\u017E", "\u040F");
    t = StringUtils.replace(t, "lj", "\u0459");
    t = StringUtils.replace(t, "nj", "\u045A");
    t = StringUtils.replace(t, "d\u017e", "\u045F");
    for (int i = 0; i < cyr.length; i++)
      t = t.replace(lat[i], cyr[i]);
    return t;
  }

  /**
   * Converts a string with cyrillic text to latin without accents.
   * @param s String to be converted
   * @return The converted string
   */
  public static String toLatinUnaccented(String s) {
    String latin = toLatin(s);
    String normalized = Normalizer.normalize(latin, Normalizer.Form.NFD);
    String cleaned = normalized.replaceAll("\\p{M}", "");
    return cleaned;
  }


  public static String toLatinUnaccentedWithoutStopSigns(String s){
    String retVal = toLatinUnaccented(s);
    retVal = StringUtils.clearDelimiters(retVal, "<>=:;.-/\\\"`~()&#^!_+-[]");
    return retVal;
  }

//  public static void main(String[] args) {
//    System.out.println(toLatinUnaccentedWithoutStopSigns("Би*/тек[са]ч._ж=Aasd/978sad"));
//  }
  
  private static char[] cyr = { '\u0410', '\u0411', '\u0412', '\u0413', 
      '\u0414', '\u0402', '\u0415', '\u0416', '\u0417', '\u0418', '\u0408',
      '\u041A', '\u041B', '\u041C', '\u041D', '\u041E', '\u041F', '\u0420',
      '\u0421', '\u0422', '\u040B', '\u0423', '\u0424', '\u0425', '\u0426',
      '\u0427', '\u0428', '\u0430', '\u0431', '\u0432', '\u0433', '\u0434',
      '\u0452', '\u0435', '\u0436', '\u0437', '\u0438', '\u0458', '\u043A',
      '\u043B', '\u043C', '\u043D', '\u043E', '\u043F', '\u0440', '\u0441',
      '\u0442', '\u045B', '\u0443', '\u0444', '\u0445', '\u0446', '\u0447',
      '\u0448'};
  
  private static char[] lat = { 'A', 'B', 'V', 'G', 'D', '\u0110', 'E', 
      '\u017D', 'Z', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T',
      '\u0106', 'U', 'F', 'H', 'C', '\u010C', '\u0160', 'a', 'b', 'v', 'g', 'd',
      '\u0111', 'e', '\u017e', 'z', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r',
      's', 't', '\u0107', 'u', 'f', 'h', 'c', '\u010d', '\u0161'};
}

