package com.ftninformatika.utils.string;

import java.io.*;
import java.util.StringTokenizer;

/** Utility klasa koja sadrzi konverzije raznih karakter setova u Unicode.
    @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
    @version 1.0
  */
public class Charset {

  /** Numericka oznaka za CP1250 karakter set. */
  public static final int CP1250 = 0;
  /** Numericka oznaka za CP1251 karakter set. */
  public static final int CP1251 = 1;
  /** Numericka oznaka za CP1252 karakter set. */
  public static final int CP1252 = 2;

  /** Podrazumevani karakter set koji se koristi u HTML stranicama za postavljanje upita. */
  public static final String defaultCharset = "CP1250";

  /** Konvertuje string koga je poslao browser u skladu sa tipom browsera (Netscape
      i Explorer salju razlicito kodirane stringove).
      @param text Tekst koji se konvertuje
      @param charset Karakter set u kome je dat <code>text</code>
      @param userAgent Sadrzaj "user-agent" header polja prilikom poziva servleta. Sluzi za identifikaciju browsera
      @return Konvertovani string
    */
  public static String adaptToBrowser(String text, String charset, String userAgent) {
    String retVal = text;
    if (userAgent == null)
      userAgent = "";
    if (userAgent.indexOf("MSIE") > 0) {
      // explorer
      retVal = URItoUnicode(convertToUnicode(retVal, defaultCharset));
      if (charset.equals("YUSCII"))
        retVal = YUSCII2Unicode(retVal);
    } else {
      // netscape
      retVal = Charset.convertToUnicode(text, charset);
    }
    return retVal;
  }

  /** Konvertuje URI enkodiran string u Unicode. URI enkodiran string koristi <code>&#xxxx;</code>
      notaciju za zapisivanje Unicode karaktera, gde je <code>xxxx</code> dekadni Unicode kod
      datog karaktera. Na primer, <code>&#1072;</code> predstavlja oznaku za cirilicno slovo a
      (Unicode kod 1072 odnosno 0x0430).
      @param text Tekst koji se konvertuje
      @return Konvertovani tekst
    */
  public static String URItoUnicode(String text) {
    String retVal = "";
    int finishedPos = 0;
    int startPos = 0;
    int semicolonPos = 0;
    while ((startPos = text.indexOf("&#", startPos)) != -1) {
      if (startPos != finishedPos)
        retVal += text.substring(finishedPos, startPos);
      startPos += 2;
      semicolonPos = text.indexOf(';', startPos);
      String sValue = text.substring(startPos, semicolonPos);
      retVal += String2char(sValue);
      startPos = semicolonPos + 1;
      finishedPos = startPos;
    }
    if (finishedPos < text.length())
      retVal += text.substring(finishedPos);
    return retVal;
  }

  /** Konvertuje string u formatu <code>xxxx</code> (gde je <code>xxxx</code>
      dekadni kod Unicode karaktera) u odgovarajuci karakter. Na primer,
      string <code>1072</code> bi se konvertovao u cirilicno slovo a.
      @param s String koji se konvertuje
      @return Karakter dobijen konverzijom
    */
  private static char String2char(String s) {
    int n = s.length();
    int k = 1;
    char retVal = 0;
    for (int i = n - 1; i > -1; i--) {
      int increment = k*((int)s.charAt(i)-48);
      retVal += increment;
      k *= 10;
    }
    return retVal;
  }

  /** Konvertuje tekst dat u nekom od podrzanih kodnih rasporeda u Unicode.
      @param text Tekst koji se konvertuje
      @param codePage Numericka oznaka kodnog rasporeda (CP1250, CP1251, CP1252).
      @return Konvertovani string
    */
  public static String convertToUnicode(String text, int codePage) {
    switch (codePage) {
      case CP1250:
        return convertToUnicode(text, "Cp1250");
      case CP1251:
        return convertToUnicode(text, "Cp1251");
      case CP1252:
        return convertToUnicode(text, "Cp1252");
      default:
        return "";
    }
  }

  /** Konvertuje tekst dat u nekom od podrzanih kodnih rasporeda u Unicode.
      @param text Tekst koji se konvertuje
      @param codePage Tekstualna oznaka kodnog rasporeda ("Cp1250", "Cp1251", "Cp1252", itd).
      @return Konvertovani string
    */
  public static String convertToUnicode(String text, String codePage) {
    byte[] bytes = StringUtils.getLowerBytes(text);
    String retVal;
    try {
      retVal = new String(bytes, codePage);
    } catch (UnsupportedEncodingException ex) {
      retVal = "";
    }
    return retVal;
  }

  /** Konvertuje tekst dat u Unicode-u u neku posebnu kodnu stranu.
      @param text Tekst koji se konvertuje
      @param codePage Tekstualna oznaka kodnog rasporeda ("Cp1250", "Cp1251", "Cp1252", itd).
      @return Konvertovani string
    */
  public static String convertFromUnicode(String text, String codePage) {
    try {
      byte[] bytes = text.getBytes(codePage);
      return StringUtils.getStringLower(bytes);
    } catch (UnsupportedEncodingException ex) {
      return "";
    }
  }

  /** Konverzija stringa sa YUSCII tekstom u Unicode
      @param s String koji se konvertuje
      @return Konvertovani string
    */
  public static String YUSCII2Unicode(String s) {
    if (s == null)
      return null;
    String retVal = s;
    retVal = retVal.replace('{', '\u0161');
    retVal = retVal.replace('}', '\u0107');
    retVal = retVal.replace('|', '\u0111');
    retVal = retVal.replace('`', '\u017e');
    retVal = retVal.replace('~', '\u010d');
    retVal = retVal.replace('[', '\u0160');
    retVal = retVal.replace(']', '\u0106');
    retVal = retVal.replace('\\', '\u0110');
    retVal = retVal.replace('@', '\u017d');
    retVal = retVal.replace('^', '\u010c');
    return retVal;
  }

  /**  Vraca konvertovani string ako dati string sadrzi YU slova. MENJA
    *  STRING TAKO DA ON NEMA YU SLOVA -- UMESTO NJIH STAVLJA cczsdj
    *  @param s String koji se konvertuje
    *  @return true ako je string imao YU karaktere u sebi
    */
  public static String containsUnimarcYUChars(String s) {
    boolean found = false;
    for (int i = 0; i < src.length; i++)
      if (s.indexOf(src[i]) != -1) {
        found = true;
        s = s.replace(src[i], dest[i]);
      }
    String newStr = "";
    StringTokenizer st = new StringTokenizer(s, "\u0090", true);
    while (st.hasMoreTokens()) {
      String t = st.nextToken();
      if (t.equals("\u0090")) {
        found = true;
        newStr += "DJ";
      } else
        newStr += t;
    }
    if (found)
      return newStr;
    else
      return "";
  }

  // ovi karakteri se menjaju pomocu String.replace(char, char), a slovo DJ mora posebno
  private static char[] src  = {'\u008D', '\u008E', '\u00BC', '\u00AF'};
  private static char[] dest = {'C', 'C', 'Z', 'S'};

/*
LISTA DOPUSTENIH KODNIH STRANA
------------------------------
<oznaka>         <opis>

8859_1           ISO 8859-1
8859_2           ISO 8859-2
8859_3           ISO 8859-3
8859_4           ISO 8859-4
8859_5           ISO 8859-5
8859_6           ISO 8859-6
8859_7           ISO 8859-7
8859_8           ISO 8859-8
8859_9           ISO 8859-9
Big5             Big5, Traditional Chinese
CNS11643         CNS 11643, Traditional Chinese
Cp037            USA, Canada (Bilingual, French), Netherlands, Portugal, Brazil, Australia
Cp1006           IBM AIX Pakistan (Urdu)
Cp1025           IBM Multilingual Cyrillic: Bulgaria, Bosnia, Herzegovinia, Macedonia(FYR)
Cp1026           IBM Latin-5, Turkey
Cp1046           IBM Open Edition US EBCDIC
Cp1097           IBM Iran(Farsi)/Persian
Cp1098           IBM Iran(Farsi)/Persian (PC)
Cp1112           IBM Latvia, Lithuania
Cp1122           IBM Estonia
Cp1123           IBM Ukraine
Cp1124           IBM AIX Ukraine
Cp1125           IBM Ukraine (PC)
Cp1250           Windows Eastern European
Cp1251           Windows Cyrillic
Cp1252           Windows Latin-1
Cp1253           Windows Greek
Cp1254           Windows Turkish
Cp1255           Windows Hebrew
Cp1256           Windows Arabic
Cp1257           Windows Baltic
Cp1258           Windows Vietnamese
Cp1381           IBM OS/2, DOS People's Republic of China (PRC)
Cp1383           IBM AIX People's Republic of China (PRC)
Cp273            IBM Austria, Germany
Cp277            IBM Denmark, Norway
Cp278            IBM Finland, Sweden
Cp280            IBM Italy
Cp284            IBM Catalan/Spain, Spanish Latin America
Cp285            IBM United Kingdom, Ireland
Cp297            IBM France
Cp33722          IBM-eucJP - Japanese (superset of 5050)
Cp420            IBM Arabic
Cp424            IBM Hebrew
Cp437            MS-DOS United States, Australia, New Zealand, South Africa
Cp500            EBCDIC 500V1
Cp737            PC Greek
Cp775            PC Baltic
Cp838            IBM Thailand extended SBCS
Cp850            MS-DOS Latin-1
Cp852            MS-DOS Latin-2
Cp855            IBM Cyrillic
Cp857            IBM Turkish
Cp860            MS-DOS Portuguese
Cp861            MS-DOS Icelandic
Cp862            PC Hebrew
Cp863            MS-DOS Canadian French
Cp864            PC Arabic
Cp865            MS-DOS Nordic
Cp866            MS-DOS Russian
Cp868            MS-DOS Pakistan
Cp869            IBM Modern Greek
Cp870            IBM Multilingual Latin-2
Cp871            IBM Iceland
Cp874            IBM Thai
Cp875            IBM Greek
Cp918            IBM Pakistan(Urdu)
Cp921            IBM Latvia, Lithuania (AIX, DOS)
Cp922            IBM Estonia (AIX, DOS)
Cp930            Japanese Katakana-Kanji mixed with 4370 UDC, superset of 5026
Cp933            Korean Mixed with 1880 UDC, superset of 5029
Cp935            Simplified Chinese Host mixed with 1880 UDC, superset of 5031
Cp937            Traditional Chinese Host miexed with 6204 UDC, superset of 5033
Cp939            Japanese Latin Kanji mixed with 4370 UDC, superset of 5035
Cp942            Japanese (OS/2) superset of 932
Cp948            OS/2 Chinese (Taiwan) superset of 938
Cp949            PC Korean
Cp950            PC Chinese (Hong Kong, Taiwan)
Cp964            AIX Chinese (Taiwan)
Cp970            AIX Korean
EUCJIS           JIS, EUC Encoding, Japanese
GB2312           GB2312, EUC encoding, Simplified Chinese
GBK              GBK, Simplified Chinese
ISO2022CN        ISO 2022 CN, Chinese
ISO2022CN_CNS    CNS 11643 in ISO-2022-CN form, T. Chinese
ISO2022CN_GB     GB 2312 in ISO-2022-CN form, S. Chinese
ISO2022KR        ISO 2022 KR, Korean
JIS              JIS, Japanese
JIS0208          JIS 0208, Japanese
KOI8_R           KOI8-R, Russian
KSC5601          KS C 5601, Korean
MS874            Windows Thai
MacArabic        Macintosh Arabic
MacCentralEurope Macintosh Latin-2
MacCroatian      Macintosh Croatian
MacCyrillic      Macintosh Cyrillic
MacDingbat       Macintosh Dingbat
MacGreek         Macintosh Greek
MacHebrew        Macintosh Hebrew
MacIceland       Macintosh Iceland
MacRoman         Macintosh Roman
MacRomania       Macintosh Romania
MacSymbol        Macintosh Symbol
MacThai          Macintosh Thai
MacTurkish       Macintosh Turkish
MacUkraine       Macintosh Ukraine
SJIS             Shift-JIS, Japanese
UTF8             UTF-8
*/
}