package com.ftninformatika.utils.string;

/** Utility class for string manipulation.
 *
 *  @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 *  @version 1.1
 */
public class StringUtils {

  public static String removeNonDigitsFromString(String s) {
    if (s == null)
      return null;
    return s.replaceAll("[^\\d]", "");
  }

   /**
   * Removes all digits from string
   * @param s String to change
   * @return
   */
  public static String removeDigitsFromString(String s) {
    if (s == null)
      return null;
    return s.replaceAll("\\d", "").trim();
  }

  /** Returns an array of bytes extracted from a Unicode string
   *  ordered as <higher-byte>,<lower-byte>,...
   *
   *  @param s String to be converted
   *  @return The resulting byte array
   */
  public static byte[] getBytes(String s) {
    int len = s.length();
    byte[] retVal = new byte [len*2];
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      retVal[i*2] = (byte)(c/256);
      retVal[i*2+1] = (byte)(c%256);
    }
    return retVal;
  }

  /** Returns an array of bytes extracted from a Unicode string
   *  ordered as <lower-byte>,<higher-byte>,...
   *
   *  @param s String to be converted
   *  @return The resulting byte array
   */
  public static byte[] getBytesReverse(String s) {
    int len = s.length();
    byte[] retVal = new byte [len*2];
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      retVal[i*2] = (byte)(c%256);
      retVal[i*2+1] = (byte)(c/256);
    }
    return retVal;
  }

  /** Returns an array of characters' lower bytes
   *  extracted from a Unicode string
   *
   *  @param s String to be converted
   *  @return The resulting byte array
   */
  public static byte[] getLowerBytes(String s) {
    int len = s.length();
    byte[] retVal = new byte [len];
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      retVal[i] = (byte)(c%256);
    }
    return retVal;
  }

  /** Returns an array of characters' higher bytes
   *  extracted from a Unicode string
   *
   *  @param s String to be converted
   *  @return The resulting byte array
   */
  public static byte[] getHigherBytes(String s) {
    int len = s.length();
    byte[] retVal = new byte [len];
    for (int i = 0; i < len; i++) {
      char c = s.charAt(i);
      retVal[i] = (byte)(c/256);
    }
    return retVal;
  }

  /**  Converts a byte array to a string and DOES NOT PERFORM
   *   ANY CODE PAGE CONVERSIONS. Each character's higher byte
   *   is considered to be zero.
   *
   *   @param s The byte array to be converted
   *   @return The resulting string
   */
  public static String getStringLower(byte[] s) {
    if (s == null)
      return null;
    StringBuffer retVal = new StringBuffer();
    for (int i = 0; i < s.length; i++) {
      int temp = (int)s[i];
      if (temp < 0)
        temp += 256;
      retVal.append((char)temp);
    }
    return retVal.toString();
  }

  /**  Converts a byte array to a string and DOES NOT PERFORM
   *   ANY CODE PAGE CONVERSIONS. Each character occupies two bytes
   *   ordered as <higher-byte>,<lower-byte>,<higher-byte>,<lower-byte>...
   *   The byte array length must be an even value, otherwise the
   *   result is an empty string.
   *
   *   @param s The byte array to be converted
   *   @return The resulting string
   */
  public static String getString(byte[] s) {
    if (s.length % 2 != 0)
      return "";
    StringBuffer retVal = new StringBuffer();
    for (int i = 0; i < s.length; i+=2) {
      int temp1 = (int)s[i];
      if (temp1 < 0)
        temp1 += 256;
      int temp2 = (int)s[i+1];
      if (temp2 < 0)
        temp2 += 256;
      int temp3 = (temp1 << 8) + temp2;
      retVal.append((char)temp3);
    }
    return retVal.toString();
  }

  /** Returns a string's hex dump formatted as
   *  <higher-byte>:<lower_byte>|...
   *
   *  @param s String to be dumped
   *  @return Hex dump
   */
  public static String getHexDump(String s) {
    StringBuffer retVal = new StringBuffer();
    byte[] bytes = getBytes(s);
    int temp1, temp2;
    for (int i = 0; i < bytes.length; i+=2) {
      temp1 = (int)bytes[i];
      if (temp1 < 0)
        temp1 += 256;
      retVal.append(Integer.toHexString(temp1));
      retVal.append(":");
      temp2 = (int)bytes[i+1];
      if (temp2 < 0)
        temp2 += 256;
      retVal.append(Integer.toHexString(temp2));
      retVal.append("|");
    }
    return retVal.toString();
  }

  /** Returns a string's decimal dump formatted as
   *  <higher-byte>:<lower_byte>|...
   *
   *  @param s String to be dumped
   *  @return Decimal dump
   */
  public static String getDecDump(String s) {
    StringBuffer retVal = new StringBuffer();
    byte[] bytes = getBytes(s);
    int temp1, temp2;
    for (int i = 0; i < bytes.length; i+=2) {
      temp1 = (int)bytes[i];
      if (temp1 < 0)
        temp1 += 256;
      retVal.append(Integer.toString(temp1));
      retVal.append(":");
      temp2 = (int)bytes[i+1];
      if (temp2 < 0)
        temp2 += 256;
      retVal.append(Integer.toString(temp2));
      retVal.append("|");
    }
    return retVal.toString();
  }

  /** Converts an arabic number to a roman number. Returns an empty string
   *  for invalid numbers.
   *
   *  @param a Arabic number
   *  @return Roman equivalent
   */
  public static String arabicToRoman(String a) {
    int n;
    try { n = Integer.parseInt(a); } catch (NumberFormatException ex) { return ""; }
    return arabicToRoman(n);
  }

  /** Converts an integer to its roman representation in a string.
   *  Based on some weird Knuth's algorithm.
   *
   *  @param a Arabic number
   *  @return Roman equivalent
   */
  public static String arabicToRoman(int n) {
    int j = 0, k;
    int u, v = 1000;
    //String r = "m2d5c2l5x2v5i";
    String r = "M2D5C2l5X2V5I";
    String rn = "";

    while (true) {
      while (n >= v) {
        rn += r.substring(j, j + 1);
        n -= v;
      }
      if (n <= 0)
        break;
      k = j + 2;
      u = v / Integer.parseInt(r.substring(k-1, k));
      if (r.substring(k-1, k).equals("2")) {
        k += 2;
        u /= Integer.parseInt(r.substring(k-1, k));
      }
      if (n + u >= v) {
        rn += r.substring(k, k+1);
        n += u;
      } else {
        j += 2;
        v /= Integer.parseInt(r.substring(j-1, j));
      }
    }
    return rn;
  }

  /** Converts a roman number to arabic. Returns an empty string for
   *  invalid arguments.
   *
   *  @param a Roman number
   *  @return Arabic equivalent
   */
  public static int romanToArabic(String r) {
    int retVal = 0;
    int i = 0;
    while (i < r.length()) {
      int current = romanValue(r.charAt(i));
      int next = (i == r.length()-1 ? -1: romanValue(r.charAt(i+1)));
      if (next != -1 && next > current) {
        if (romanDistance(r.charAt(i+1), r.charAt(i)) > 2) {
          return -1;
        }
        retVal += next - current;
        i += 2;
      } else {
        retVal += current;
        i += 1;
      }
    }
    return retVal;
  }

  /** Returns a value of the roman digit. */
  private static int romanValue(char x) {
    x = Character.toUpperCase(x);
    int retVal = -1;
    switch (x) {
      case 'M':
        retVal = 1000;
        break;
      case 'D':
        retVal = 500;
        break;
      case 'C':
        retVal = 100;
        break;
      case 'L':
        retVal = 50;
        break;
      case 'X':
        retVal = 10;
        break;
      case 'V':
        retVal = 5;
        break;
      case 'I':
        retVal = 1;
        break;
    }
    return retVal;
  }

  /** Returns a distance of two roman digits. (Roman numbers must not
   *  contain a digit A positioned to the left of digit B, where
   *  A is more than "two orders of magnitude" less than B.
   */
  private static int romanDistance(char x, char y) {
    String cifre = "IVXLCDM";
    int index1 = cifre.indexOf(x);
    int index2 = cifre.indexOf(y);
    return index1 - index2;
  }

  /** Replaces one apostrophe character (') with two ('') in the
   *  given string. Used mostly when composing SQL statements.
   *
   *  @param s String to be converted
   *  @return Resulting string
   */
  public static String adjustQuotes(String s) {
    if (s == null)
      return null;
    int startIndex = 0;
    int pos;
    StringBuffer retVal = new StringBuffer();
    while (true) {
      pos = s.indexOf('\'', startIndex);
      if (pos > -1) {
        retVal.append(s.substring(startIndex, pos));
        retVal.append("\'\'");
        startIndex = pos+1;
      } else {
        retVal.append(s.substring(startIndex));
        break;
      }
    }
    return retVal.toString();
  }

  /** Replaces occurrences of '&', '<', and '>' with '&amp;', '&lt;', and '&gt',
   *  respectively. Usable when converting text for HTML.
   */
  public static String adjustForHTML(String s) {
    if (s == null)
      return "";
    return replace(replace(replace(replace(s, "&", "&amp;"), "<", "&lt;"), ">", "&gt;"), "'", "&apos;");
  }

  /** Converts a string into an array of corresponding character hex values.
   *  Each hex value consists of four characters, with leading zeros if
   *  needed.
   *
   *  @param toConvert String to be converted
   *  @return The converted string
   */
  public static String hexEncode(String toConvert) {
    StringBuffer retVal = new StringBuffer();
    int length = toConvert.length();
    int ichar;
    String hexPiece;
    int n;
    for (int i = 0; i < length; i++) {
      ichar = (int)toConvert.charAt(i);
      hexPiece = Integer.toHexString(ichar);
      n = 4 - hexPiece.length();
      for (int j = 0; j < n; j++)
        retVal.append("0");
      retVal.append(hexPiece);
    }
    return retVal.toString();
  }

  /** Converts an array of character hex values into a string. Each hex
   *  value consists of four characters, with leading zeros if needed.
   *
   *  @param hexCode String to be converted
   *  @return The converted string; <code>null</code> if encoded string
   *    is invalid
   */
  public static String hexDecode(String hexCode) {
    try {
      StringBuffer retVal = new StringBuffer();
      int start = 0;
      String hex = null;
      int hexLen = hexCode.length();
      while (start < hexLen) {
        hex = hexCode.substring(start, start + 4);
        retVal.append((char)Integer.parseInt(hex, 16));
        start += 4;
      }
      return retVal.toString();
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  /** Convert all chars to HTML UTF-8 decimal format
   *
   * @param text
   * @return
   */
  public static String convertToHtmlUtf8(String text) {
    if (text == null || "".equals(text)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    for (char c: text.toCharArray()) {
      sb.append("&#");
      sb.append((int) c);
      sb.append(";");
    }
    return sb.toString();
  }

  /** Replaces one substring with another in a given string.
   *
   *  @param s Container string
   *  @param src Substring to be replaced
   *  @param dest Replacement string
   *  @return Converted string
   */
  public static String replace(String s, String src, String dest) {
    StringBuffer retVal = new StringBuffer();
    int finishedPos = 0;
    int startPos = 0;
    int srcLen = src.length();
    while ((startPos = s.indexOf(src, startPos)) != -1) {
      if (startPos != finishedPos)
        retVal.append(s.substring(finishedPos, startPos));
      startPos += srcLen;
      retVal.append(dest);
      finishedPos = startPos;
    }
    if (finishedPos < s.length())
      retVal.append(s.substring(finishedPos));
    return retVal.toString();
  }

  /** Replaces all given delimiters with a blank character.
   *
   *  @param s Container string
   *  @param delims String containing all delimiters
   *  @return Converted string
   */
  public static String clearDelimiters(String s, String delims) {
    String retVal = s;
    int n = delims.length();
    for (int i = 0; i < n; i++)
      retVal = retVal.replace(delims.charAt(i), ' ');
    return retVal;
  }

  /** Formats a double value by trimming chars and/or padding blanks on both
   *  sides based on formatting parameters. Does not round the result while
   *  stripping decimals, but does truncation instead.
   *
   *  @param value The value to be formatted.
   *  @param totalDigits The total number of characters to use, including
   *    decimal point and minus-sign.
   *  @param decimals The number of decimals
   *  @return The converted string
   */
  public static String pad(double value, int totalDigits, int decimals) {
    String s = Double.toString(value);
    int n = s.length();
    if (totalDigits <= n)
      return s;
    StringBuffer retVal = new StringBuffer();
    int dotpos = s.indexOf('.');
    int dec = n - dotpos - 1;
    if (dec >= decimals) {
      for (int i = 0; i < totalDigits - (decimals + 1 + dotpos); i++)
        retVal.append(" ");
      retVal.append(s.substring(0, dotpos + decimals + 1));
    } else {
      for (int i = 0; i < totalDigits - (n + decimals - dec); i++)
        retVal.append(" ");
      retVal.append(s);
      for (int i = 0; i < decimals - dec; i++)
        retVal.append(" ");
    }
    return retVal.toString();
  }

  /** Formats an int value by padding blanks on the left side.
   *  Returns a <code>null</code> if the <code>value</code> is longer than
   *  <code>totalDigits</code>.
   */
  public static String pad(int value, int totalDigits) {
    String s = Integer.toString(value);
    if (s.length() > totalDigits)
      return null;
    int len = s.length();
    char[] retVal = new char[totalDigits];
    for (int i = 0; i < totalDigits - len; i++)
      retVal[i] = ' ';
    for (int i = totalDigits - len; i < totalDigits; i++)
      retVal[i] = s.charAt(i-(totalDigits-len));
    return new String(retVal);
  }

  /** Formats an int value by padding zeros on the left side.
   *  Returns a <code>null</code> if the <code>value</code> is longer than
   *  <code>totalDigits</code>.
   */
  public static String padZeros(int value, int totalDigits) {
    String s = Integer.toString(value);
    if (s.length() > totalDigits)
      return null;
    int len = s.length();
    char[] retVal = new char[totalDigits];
    for (int i = 0; i < totalDigits - len; i++)
      retVal[i] = '0';
    for (int i = totalDigits - len; i < totalDigits; i++)
      retVal[i] = s.charAt(i-(totalDigits-len));
    return new String(retVal);
  }
  
  /**
   * Pads given character to the left. If the given string is longer than the
   * total length, it is returned unmodified.
   * 
   * @param value The value to be left-padded.
   * @param padChar The padding character.
   * @param totalLength The total length of the resulting string.
   * @return The padded string.
   */
  public static String padChars(String value, char padChar, int totalLength) {
    int len = value.length();
    if (len >= totalLength)
      return value;
    StringBuffer buff = new StringBuffer(totalLength);
    for (int i = 0; i < totalLength - len; i++)
      buff.append(padChar);
    buff.append(value);
    return buff.toString();
  }

  /** Converts a <code>String</code> to an <code>int</code> without
   *  having to catch the exception every time.
   *
   *  @param s String to be converted
   *  @return The resulting integer; -1 if an exception occured.
   */
  public static int String2int(String s) {
    int retVal = -1;
    try { retVal = Integer.parseInt(s); } catch (Exception ex) { }
    return retVal;
  }

  /** Converts Java escape characters in a string to their
   *  corresponding character value in the resulting string.
   *
   *  @param s String to be converted
   *  @return The converted string
   */
  public static String convertJavaEscapes(String s) {
    String retVal = s;
    java.util.Iterator i = javaEscapes.keySet().iterator();
    while (i.hasNext()) {
      String key = (String)i.next();
      String value = (String)javaEscapes.get(key);
      retVal = replace(retVal, key, value);
    }
    retVal = replace(retVal, "\\\\", "\\");
    return retVal;
  }
  
  /**
   * Returns the number of occurrences of a char in a string.
   * @param s String to be analyzed
   * @param c Char to be counted
   * @return The number of occurrences of <code>c</code> in <code>s</code>
   */
  public static int countChars(String s, char c) {
    int retVal = 0;
    for (int i = 0; i < s.length(); i++)
      retVal += s.charAt(i) == c ? 1 : 0;
    return retVal;
  }

  private static java.util.HashMap javaEscapes = new java.util.HashMap();
  static {
    javaEscapes.put("\\n", "\n");
    javaEscapes.put("\\r", "\r");
    javaEscapes.put("\\t", "\t");
  }
  
//  public static void main(String[] args) {
////    System.out.println(replace("\"<Filip Visnjic\">", "<", ""));
////    System.out.println(replace("\"<Filip Visnjic\">", ">", ""));
//      System.out.println(LatCyrUtils.toCyrillic("Bibliotekari"));
//  }
}
