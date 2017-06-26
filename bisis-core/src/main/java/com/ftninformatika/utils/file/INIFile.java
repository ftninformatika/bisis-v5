package com.ftninformatika.utils.file;

import java.io.*;
import java.net.URL;
import java.util.*;

/**  Klasa INIFile implementira operacije za rad sa INI
  *  datotekama. INI datoteke, po uzoru iz Windows-a, su
  *  tekstualne datoteke koje se sastoje iz vise sekcija
  *  (section) u kojima su definisane vrednosti zeljenih
  *  parametara navodjenjem reda <parametar> = <vrednost>.
  *  Komentari u datoteci pocinju znakom tacka-zarez (;) i
  *  protezu se do kraja tekuceg reda. Navodjenje imena
  *  sekcije se vrsi u uglastim zagradama i u posebnom redu.
  *  Sledi primer jedne INI datoteke:<BR><PRE>
  *  [Sekcija1]
  *  param1 = vrednost1
  *  param2 = vrednost2  ; komentar
  *  [Sekcija2]
  *  param3 = vrednost3
  *  param2 = vrednost2</PRE>
  *  @author Branko Milosavljevic
  *  @author mbranko@uns.ns.ac.yu
  *  @version 1.0
  */
public class INIFile {

  /** Konstruise objekat sa datim parametrima.
    * @param filename Naziv lokalnog INI fajla.
    */
  public INIFile(String filename) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(filename));
      readINI(in);
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Konstruise objekat sa datim parametrima.
    * @param url URL INI fajla.
    */
  public INIFile(URL url) {
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(url.openStream()));
      readINI(in);
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void readINI(BufferedReader in) {
    String line, key, value;
    StringTokenizer st;
    String currentCategory = "default";
    Hashtable currentMap = new Hashtable();
    categories.put(currentCategory, currentMap);
    try {
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.equals("") || line.indexOf(';') == 0)
          continue;
        if (line.charAt(0) == '[') {
          currentCategory = line.substring(1, line.length()-1);
          currentMap = new Hashtable();
          categories.put(currentCategory, currentMap);
        } else {
          st = new StringTokenizer(line, "=");
          if (st.hasMoreTokens()) {
            key = st.nextToken().trim();
            boolean hastoks = false;
            value = "";
            while (st.hasMoreTokens()) {
              value += (hastoks ? "=" : "") + st.nextToken().trim();
              hastoks = true;
            }
            currentMap.put(key, value);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**  Vraca vrednost datog parametra u obliku stringa.
    *  @param category Kategorija (sekcija) u kojoj se nalazi parametar
    *  @param key Naziv parametra
    *  @return String koji sadrzi vrednost parametra
    */
  public String getString(String category, String key) {
    Hashtable hm = (Hashtable)categories.get(category);
    if (hm == null)
      return "";
    else
      return (String)hm.get(key);
  }

  /**  Vraca vrednost datog parametra u obliku integera.
    *  @param category Kategorija (sekcija) u kojoj se nalazi parametar
    *  @param key Naziv parametra
    *  @return Integer vrednost parametra
    */
  public int getInt(String category, String key) {
    Hashtable hm = (Hashtable)categories.get(category);
    if (hm == null)
      return 0;
    else
      return (new Integer((String)hm.get(key))).intValue();
  }

  /**  Hash mapa koja sadrzi kategorije (sekcije). Hash kljuc je naziv kategorije (string),
    *  a vrednost je hash mapa koja sadrzi parove (parametar, vrednost). */
  private Hashtable categories = new Hashtable();
}

