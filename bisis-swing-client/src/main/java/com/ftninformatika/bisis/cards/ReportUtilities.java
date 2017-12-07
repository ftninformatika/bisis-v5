package com.ftninformatika.bisis.cards;

import java.util.*;

/** Klasa sa pomocnim funkcijama za rad sa stringovima u izvestavanju.
 *  @author Tatjana Zubic, tanja@uns.ns.ac.yu
 *  @version 1.0
 */

public class ReportUtilities {


  /** Prvih words reci u stringu in prebacuje u velika slova
   *  @param in String koji se konvertuje
   *  @param words broj reci od pocetka stringa koje treba prebaciti u velika slova
   *  @return izmenjeni string
   */

  static String toUpper(String in, int words) {
    int d = 0;

    if (!in.equals("")) {
      if (words == -1) {
        if ((d =  in.indexOf(","))!= -1)
          return in.substring(0,d).toUpperCase()+in.substring(d,in.length()) ;
        else
          if ((d =  in.indexOf(" "))!= -1)
             return in.substring(0,d).toUpperCase()+in.substring(d,in.length());
          else
             return in.toUpperCase();
      }
      else {
         int d1 = 0;
         for (int i=0; i<words; i++) {
            d = in.indexOf(" ",d1);
            if (d == -1)
              return in;
            d1 = d + 1;
         }
         return in.substring(0,d).toUpperCase()+in.substring(d,in.length());
      }
    }
    return in;
  }

  /** Prvih words reci u stringu in prebacuje u HTML bold font
   *  @param in String koji se konvertuje
   *  @param words broj reci od pocetka stringa koje treba prebaciti u bold
   *  @return izmenjeni string
   */

  static String toBold(String in, int words) {
    String out = new String("");

    if (words == -1)  {
      out = "<B>" + in + "</B>";
    }
    else {
      out = "<B>";
      int i = 1;
      StringTokenizer st = new StringTokenizer(in, " ");
      while (st.hasMoreTokens() ) {
        String sgPiece = st.nextToken();
        out += sgPiece + " ";
        if (i == words)
          out += "</B>";
        i++;
      }
    }
    return out;
  }

  /** Skracujemo ulazni string na prvih words reci i na kraju dodajemo "..."
   *  @param in String koji se konvertuje
   *  @param words broj reci od pocetka stringa koje treba da ostanu
   *  @return skraceni string
   */

  static String cutWords(String in, int words) {
    int d = 0;

    if (!in.equals("")) {
     int d1 = 0;
     for (int i=0; i<words; i++) {
       d = in.indexOf(" ",d1);
       if (d == -1)
        return in;
       d1 = d + 1;
     }
     in = in.substring(0,d)+ "...";
    }
    return in;
  }

  /** Proverava da li ulazni string sadrzi samo cifre
   *  @param in String koji proveravamo
   *  @return boolean
   */
  static boolean areDigits(String in) {
    int i = 0;

    while  (i < in.length()) {
      if (Character.isDigit(in.charAt(i)))
        i++;
      else
        return false;
    }
    return true;
  }

  /** Konvertuje datum iz oblika YYYYMMDD u DD.MM.YYYY.
   * @param in String YYYYMMDD ili YYYY
   * @return konvertovan string ili "GRESKA"
   */

  static String convertDate(String in) {
    String out = new String("");

    if (in.length() ==  8 && areDigits(in)) {
      String y = in.substring(0,4);
      String m = in.substring(4,6);
      String d = in.substring(6);
      out = d + (d.equals("")? "" : ".") + m + (m.equals("")? "" : ".") + y + (y.equals("")? "" : ".");
      return out;
    }
    else
      return Messages.getString("CARDS_ERROR_IN_CARD");
  }



}
