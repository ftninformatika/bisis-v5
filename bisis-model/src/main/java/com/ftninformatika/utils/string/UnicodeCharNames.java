package com.ftninformatika.utils.string;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class UnicodeCharNames {

  public static String get(char c) {
    return get(new Character(c));
  }
  
  public static String get(Character c) {
    return (String)names.get(c);
  }
  
  public static String get(String c) {
    if (c == null || c.length() == 0)
      return null;
    return get(c.charAt(0));
  }
  
  public static void init() {
    try {
      InputStream is = UnicodeCharNames.class.getResource(
          "/com/gint/util/string/UnicodeCharNames.txt").openStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF8"));
      String line = "";
      while ((line = in.readLine()) != null) {
        String[] pieces = line.split(":");
        if (pieces.length != 2)
          continue;
        Character c = new Character((char)Integer.parseInt(pieces[0], 16));
        names.put(c, pieces[1].trim());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private static Map names = new HashMap();
}
