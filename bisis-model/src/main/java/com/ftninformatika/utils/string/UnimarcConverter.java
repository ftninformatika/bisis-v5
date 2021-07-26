package com.ftninformatika.utils.string;


/** Contains UNIMARC to Unicode conversion routines.
 *
 *  @author Milan Vidakovic, minja@uns.ns.ac.yu
 *  @author Branko Milosavljevic, mbranko@uns.ns.ac.yu
 *  @version 1.2
 */
public class UnimarcConverter {

  /** The default constructor. */
  public UnimarcConverter() {
    last_lang   = LATIN;
  }

  /** Converts Unicode-encoded string to a UNIMARC-encoded string.
   *
   *  @param s Unicode string to be converted
   *  @param l_code If <i>true</i>, Unimarc-encoded string contains language
   *    tags.
   *  @return Unimarc-encoded string
   */
  public String Unicode2Unimarc(String s, boolean l_code) {
    // one character occupies max 3 bytes;
    // each character can be prefixed with a language tag.
    char[] c = new char[s.length() * 3 * 2];
    int i, j;
    last_lang = LATIN;

    int len = s.length();
    for(i = 0, j = 0; i < len; i++) {
      switch(Upper(s.charAt(i))) {
        case U_DEFAULT:
          j = setLang(UnicodeChar2UnimarcChar(s.charAt(i), LatinTable, !l_code), LATIN, c, j, l_code); // Western latin
          break;
        case U_CE:
          j = setLang(UnicodeChar2UnimarcChar(s.charAt(i), LatinYuTable, !l_code), LATIN, c, j, l_code); // Eastern latin
          break;
        case U_GREEK:
          j = setLang(UnicodeChar2UnimarcChar(s.charAt(i), GreekTable, !l_code),   GREEK, c, j, l_code); // greek
          break;
        case U_CYR:
          j = setLang(UnicodeChar2UnimarcChar(s.charAt(i), CyrTable, !l_code),     CYR, c, j, l_code);   // cyrillic
          break;
      }
    }
    return new String(c, 0, j);
  }

  /** Converts UNIMARC-encoded string to a Unicode-encoded string.
   *
   *  @param s UNIMARC string to be converted
   *  @return Unicode string
   */
  public String Unimarc2Unicode(String s) {
    char c[] = new char[s.length()];
    int i, j, k;
    last_lang = LATIN;

    for(i = 0, j = 0; i < s.length();) {
      switch(s.charAt(i)) {
        case LATIN:
        case CYR:
        case GREEK:
          last_lang = s.charAt(i);
          i++;
        break;
        default:
          k = Unimarc2UnicodeChars(s, i, c, j);
          j++;
          i = k;
          break;
      }
    }
    return new String(c, 0, j);
  }

  /** Inserts a language tag into an array of UNIMARC characters.
   *
   *  @param chrs Initial array of UNIMARC characters
   *  @param lang_code Language tag to be inserted
   *  @param array Resulting array containing language tag and the characters
   *    from the initial array
   *  @param index Starting index in the <code>array</code> array for copying
   *    characters from the <code>chrs</code> array
   *  @return Index of the position reached by the conversion
   */
  int setLang(char[] chrs, char lang_code, char[] array, int index, boolean l_code) {
    if(last_lang != lang_code) {
      last_lang = lang_code;
      if(l_code == true)
        array[index++] = lang_code;
    }
    char c;
    for(int i = 0; ((chrs[i]&0xFF00) != 0xFF00); i++) {
      c = chrs[i];
      array[index++] = c;
    }
    return index;
  }

 /** Converts a Unicode character to an array of Unimarc characters
  *
  *  @param c Unicode character to be converted
  *  @param table The conversion table to use
  *  @param skip7 Flag to enable/disable insertion of character #7 for 3-byte
  *    cyrillic characters
  *  @return Array of UNIMARC characters representing the given Unicode character
  */
  char[] UnicodeChar2UnimarcChar(char c, char table[][], boolean skip7) {
    char a[] = new char[4];
    boolean found = false;
    int howMany = 1;
    for(int i = 0; i < table.length; i++) {
      if (table[i][1] == (char)(c & 0x00FF)) {
        found = true;
        howMany = table[i][0];
        if (skip7 && howMany == 3) {
          for (int j = 1; j < howMany; j++)
            a[j-1] = table[i][2+j];
        } else {
          for (int j = 0; j < howMany; j++)
            a[j]  = table[i][2+j];
        }
        break;
      }
    }
    if(found == false)
      a[0] = (char)(c&0x00FF);
    if (skip7 && howMany == 3)
      howMany--;
    a[howMany] = (char)0xFF00;
    return a;
  }

  /** Converts UNIMARC character(s) to a Unicode string based on the language
   *  tag, or the last language tag used.
   *
   *  @param s UNIMARC character(s) to be converted
   *  @param uindex Position of the character to be converted
   *    (within the string <code>s</code>)
   *  @param c An array of Unicode characters to store the found Unicode character
   *  @param index Position in the array <code>c</code> to place the found
   *     Unicode character
   *  @return Position of the next UNIMARC character to be converted or 0 if
   *     corresponding Unicode character was not found
   */
  int Unimarc2UnicodeChars(String s, int uindex, char c[], int index) {
    int i = 0;
    switch (last_lang) {
      case LATIN: // LATIN IS DIFFERENT BECAUSE IT CONTAINS CE SCRIPTS
        u_last_lang = U_DEFAULT;
        i = findCharsInTable(s, uindex, LatinTable, c, index);
        if (i == 0) { // char not found in LatinTable, so let's look in LatinYuTable
          u_last_lang = U_CE;
          i = findCharsInTable(s, uindex, LatinYuTable, c, index);
        }
        break;
      case CYR:
        u_last_lang = U_CYR;
        i = findCharsInTable(s, uindex, CyrTable, c, index);
        break;
      case GREEK:
        u_last_lang = U_GREEK;
        i = findCharsInTable(s, uindex, GreekTable, c, index);
        break;
    }
    if (i == 0) { // if we haven't found char in table, we decide to put the very same
      c[index] = s.charAt(uindex++);  // char in array
      i = uindex;
    }
    return i;
  }

  /** Looks up a Unicode character for the given UNIMARC character(s).
   *
   *  @param s Unimarc karakter(i) za koji/e se konvertuje/u.
   * @param uindex Redni broj karaktera unutar string s na kojem se nalazi Unimarc karakter koji se konvertuje.
   * @param table Konverziona tabela.
   * @param c Niz Unicode karaktera u koji se smesta pronadjeni Unicode karakter (iz tabele).
   * @param index Redni broj unutar niza <i>c</i> gde se smesta pronadnjeni Unicode karakter.
   * @return Redni broj sledeceg Unimarc karaktera koji treba konvertovati ili 0 ako nije pronadjen Unicode karakter.
  */
 int findCharsInTable(String s, int uindex, char table[][], char c[], int index) {
    int i, j, k, u;
    boolean found;
    for (i = MaxChars; i > 0; i--) { // prvo proverimo medju 3-karakternim i 2-kar. sekvencama
      for(j = 0; j < table.length; j++) {
        if(table[j][0] == i) {  // pretrazujemo onaj deo tabele gde se nalaze i-karakterne sekvnce
          found = true;
          for(k = 0; k < i; k++) { // za sva i karaktera
            if((uindex + k) < s.length()) { // ako nije dosao do kraja tabele
              if(s.charAt(uindex + k) == table[j][2+k])
                found &= true;
              else
                found &= false;
            } else // ako je dosao do kraja stringa, a u tabeli ima jos karaktera u sekvenci,
              found = false; // onda mora biti kraca sekvenca
          }
          if(found == true) { // ako smo pronasli pravu sekvencu unimarc karaktera
            c[index] = table[j][1]; // unicode char
            u = u_last_lang;
            u = u << 8;
            c[index] |= u;  // gornji bajt je stranica Unicode (4 - cirilica, itd.)
            return uindex + i;// vrati novu poziciju za novi karakter (preskoci pronadjeni(e) karakter(e)
          }
        }
      }
    }
    // ako do ovde dodje, onda nije nasao nista
    return 0;
  }

  /** ESC sekvenca za LATINICNO pismo. */
  private static final char LATIN     = 28;
  /** ESC sekvenca za CIRILICNO pismo. */
  private static final char CYR       = 29;
  /** ESC sekvenca za STARU CIRILICU pismo. */
  private static final char CYR_OLD   = 22;
  /** ESC sekvenca za GRCKO pismo. */
  private static final char GREEK     = 1;
  /** Broj Unicode stranice za Default charset. */
  private static final int  U_DEFAULT = 0;
  /** Broj Unicode stranice za Central&Eastern European charset. */
  private static final int  U_CE      = 1;
  /** Broj Unicode stranice za Grcki charset. */
  private static final int  U_GREEK   = 3;
  /** Broj Unicode stranice za Cirilicnu charset. */
  private static final int  U_CYR     = 4;

  /**  Maximalan broj Unimarc karaktera za jedan Unicode karakter */
  private static int MaxChars = 3;

  /** Duzina stringa. */
  private int strlen = 80;
  /** Poslednja ESC sekvenca. */
  private char last_lang   = LATIN;
  /** Poslednji broj Unicode stranice. */
  private int u_last_lang  = U_DEFAULT;



  /** Tabela za konverziju Unimarc latinice u Unicode.
    Format:
    <pre>
    br. karaktera     donji unicode bajt     prvi unimarc karakter    drugi unimarc karakter   treci unimarc karakter
    <pre>
  */
  private char LatinTable[][]={
                {1, 0xa1, 128,  0,  0},  // Obrnuti !
                {1, 0xa3, 129,  0,  0},  // Znak za funtu
                {1, 0xa9, 130,  0,  0},  // (c)
                {1, 0xba, 131,  0,  0},  // o
                {1, 0xc2, 132,  0,  0},  // A^
                {1, 0xc0, 134,  0,  0},  // A`
                {1, 0xc1, 135,  0,  0},  // A'
                {1, 0xc3, 136,  0,  0},  // A~
                {1, 0xc4, 137,  0,  0},  // A..
                {1, 0xc5, 138,  0,  0},  // Ao
                {1, 0xc6, 139,  0,  0},  // AE
                {1, 0xc7, 143,  0,  0},  // C,
                {1, 0xca, 147,  0,  0},  // E^
                {1, 0xc8, 149,  0,  0},  // E`
                {1, 0xc9, 150,  0,  0},  // E'
                {1, 0xcb, 151,  0,  0},  // E..
                {1, 0xce, 155,  0,  0},  // I^
                {1, 0xcc, 157,  0,  0},  // I`
                {1, 0xcd, 158,  0,  0},  // I'
                {1, 0xcf, 159,  0,  0},  // I..
                {1, 0xd1, 165,  0,  0},  // N~
                {1, 0xd4, 166,  0,  0},  // O^
                {1, 0xd2, 167,  0,  0},  // O`
                {1, 0xd3, 168,  0,  0},  // O'
                {1, 0xd5, 169,  0,  0},  // O~
                {1, 0xd6, 170,  0,  0},  // O..
                {1, 0xd8, 173,  0,  0},  // O/
                {1, 0xdb, 180,  0,  0},  // U^
                {1, 0xd9, 181,  0,  0},  // U`
                {1, 0xda, 182,  0,  0},  // U'
                {1, 0xdc, 183,  0,  0},  // U..
                {1, 0xdd, 186,  0,  0},  // Y'
                {1, 0xe2, 191,  0,  0},  // a^
                {1, 0xe0, 193,  0,  0},  // a`
                {1, 0xe1, 194,  0,  0},  // a'
                {1, 0xe3, 195,  0,  0},  // a~
                {1, 0xe5, 196,  0,  0},  // ao
                {1, 0xe4, 197,  0,  0},  // a..
                {1, 0xe6, 198,  0,  0},  // ae
                {1, 0xe7, 202,  0,  0},  // c,
                {1, 0xea, 206,  0,  0},  // e^
                {1, 0xe8, 208,  0,  0},  // e`
                {1, 0xe9, 209,  0,  0},  // e'
                {1, 0xeb, 210,  0,  0},  // e..
                {1, 0xee, 214,  0,  0},  // i^
                {1, 0xec, 215,  0,  0},  // i`
                {1, 0xed, 216,  0,  0},  // i'
                {1, 0xef, 217,  0,  0},  // i..
                {1, 0xf1, 223,  0,  0},  // n~
                {1, 0xf4, 224,  0,  0},  // o^
                {1, 0xf2, 225,  0,  0},  // o`
                {1, 0xf3, 226,  0,  0},  // o'
                {1, 0xf5, 227,  0,  0},  // o~
                {1, 0xf6, 228,  0,  0},  // o..
                {1, 0xf8, 231,  0,  0},  // o/
                {1, 0xdf, 237,  0,  0},  // beta
                {1, 0xfb, 241,  0,  0},  // u^
                {1, 0xf9, 242,  0,  0},  // u`
                {1, 0xfa, 243,  0,  0},  // u'
                {1, 0xfc, 244,  0,  0},  // u..
                {1, 0xfd, 247,  0,  0},  // y'
                {1, 0xff, 248,  0,  0},  // y..
                {1, 0xb4, 254,  0,  0},  // apostrof ' !@#$!@#$@!#$
                };

  /** Tabela za konverziju Unimarc CE latinice u Unicode.
    Format:
    <pre>
    br. karaktera     donji unicode bajt     prvi unimarc karakter    drugi unimarc karakter   treci unimarc karakter
    <pre>
  */
  private char LatinYuTable[][]={
                {1, 0x02, 133,  0,  0},  // A`'
                {1, 0x04, 140,  0,  0},  // A,
                {1, 0x0c, 141,  0,  0},  // Cx
                {1, 0x06, 142,  0,  0},  // Cy
                {1, 0x10, 144,  0,  0},  // Dj
                {1, 0x0e, 145,  0,  0},  // D`'
                {1, 0x14, 148,  0,  0},  // E`'
                {1, 0x18, 152,  0,  0},  // E,
                {1, 0x1e, 153,  0,  0},  // G`'
                {1, 0x30, 156,  0,  0},  // I.
                {1, 0x39, 161,  0,  0},  // L'
                {1, 0x41, 162,  0,  0},  // L/
                {1, 0x47, 163,  0,  0},  // N`'
                {1, 0x43, 164,  0,  0},  // N'
                {1, 0x50, 171,  0,  0},  // O"
                {1, 0x52, 172,  0,  0},  // OE
                {1, 0x58, 174,  0,  0},  // R`'
                {1, 0x60, 175,  0,  0},  // Sx
                {1, 0x5a, 176,  0,  0},  // S'
                {1, 0x5e, 177,  0,  0},  // S,
                {1, 0x64, 178,  0,  0},  // T`'
                {1, 0x62, 179,  0,  0},  // T,
                {1, 0x70, 185,  0,  0},  // U"
                {1, 0x78, 187,  0,  0},  // Y..
                {1, 0x7d, 188,  0,  0},  // Zx
                {1, 0x79, 189,  0,  0},  // Z'
                {1, 0x7b, 190,  0,  0},  // Z.
                {1, 0x03, 192,  0,  0},  // a`'
                {1, 0x05, 199,  0,  0},  // a,
                {1, 0x0d, 200,  0,  0},  // cx
                {1, 0x07, 201,  0,  0},  // cy
                {1, 0x0f, 203,  0,  0},  // d`
                {1, 0x11, 204,  0,  0},  // dj
                {1, 0x1b, 207,  0,  0},  // e`'
                {1, 0x19, 211,  0,  0},  // e,
                {1, 0x1f, 212,  0,  0},  // g`'
                {1, 0x3a, 219,  0,  0},  // l' (with acute)
                {1, 0x42, 220,  0,  0},  // l/
                {1, 0x48, 221,  0,  0},  // n`'
                {1, 0x44, 222,  0,  0},  // n'
                {1, 0x51, 229,  0,  0},  // o"
                {1, 0x53, 230,  0,  0},  // oe
                {1, 0x59, 232,  0,  0},  // r`'
                {1, 0x61, 233,  0,  0},  // sx
                {1, 0x5d, 234,  0,  0},  // s`'
                {1, 0x5b, 235,  0,  0},  // s'
                {1, 0x5f, 236,  0,  0},  // s,
                {1, 0x65, 238,  0,  0},  // t`'
                {1, 0x63, 240,  0,  0},  // t,
                {1, 0x71, 245,  0,  0},  // u"
                {1, 0x7e, 249,  0,  0},  // zx.
                {1, 0x7a, 250,  0,  0},  // z'
                {1, 0x7c, 251,  0,  0},  // z.
                {1, 0x3e, 146,  0,  0},  // l' (with caron)
                };

  /** Tabela za konverziju Unimarc grckog pisma u Unicode.
    Format:
    <pre>
    br. karaktera     donji unicode bajt     prvi unimarc karakter    drugi unimarc karakter   treci unimarc karakter
    <pre>
  */
  private char GreekTable[][]={
                {1, 0x91,  65,  0,  0},  // A
                {1, 0x92,  66,  0,  0},  // B
                {1, 0x93,  67,  0,  0},  // C
                {1, 0x94,  68,  0,  0},  // D
                {1, 0x95,  69,  0,  0},  // E
                {1, 0xa6,  70,  0,  0},  // F
                {1, 0x98,  71,  0,  0},  // G
                {1, 0xa7,  72,  0,  0},  // H
                {1, 0x99,  73,  0,  0},  // I !
                {1, 0x9a,  75,  0,  0},  // K
                {1, 0x9b,  76,  0,  0},  // L
                {1, 0x9c,  77,  0,  0},  // M
                {1, 0x9d,  78,  0,  0},  // N
                {1, 0x9f,  79,  0,  0},  // O
                {1, 0xa0,  80,  0,  0},  // P
                {1, 0x97,  81,  0,  0},  // Q
                {1, 0xa1,  82,  0,  0},  // R
                {1, 0xa3,  83,  0,  0},  // S
                {1, 0xa4,  84,  0,  0},  // T
                {1, 0xa8,  85,  0,  0},  // U !
                {1, 0xa9,  87,  0,  0},  // W
                {1, 0x9e,  88,  0,  0},  // X
                {1, 0xa5,  89,  0,  0},  // Y
                {1, 0x96,  90,  0,  0},  // Z !
                {1, 0xb1,  97,  0,  0},  // a
                {1, 0xb2,  98,  0,  0},  // b
                {1, 0xb3,  99,  0,  0},  // c
                {1, 0xb4, 100,  0,  0},  // d
                {1, 0xb5, 101,  0,  0},  // e
                {1, 0xc6, 102,  0,  0},  // f
                {1, 0xb8, 103,  0,  0},  // g
                {1, 0xc7, 104,  0,  0},  // h
                {1, 0xb9, 105,  0,  0},  // i !
                {1, 0xba, 107,  0,  0},  // k
                {1, 0xbb, 108,  0,  0},  // l
                {1, 0xbc, 109,  0,  0},  // m
                {1, 0xbd, 110,  0,  0},  // n
                {1, 0xbf, 111,  0,  0},  // o
                {1, 0xc0, 112,  0,  0},  // p
                {1, 0xb7, 113,  0,  0},  // q
                {1, 0xc1, 114,  0,  0},  // r
                {1, 0xc3, 115,  0,  0},  // s
                {1, 0xc4, 116,  0,  0},  // t
                {1, 0xc8, 117,  0,  0},  // u !
                {1, 0xc9, 119,  0,  0},  // w
                {1, 0xbe, 120,  0,  0},  // x
                {1, 0xc5, 121,  0,  0},  // y
                {1, 0xb6, 122,  0,  0},  // z
                {1, 0x84, 253,  0,  0},  // '
                };

  /** Tabela za konverziju Unimarc cirilice u Unicode.
    Format:
    <pre>
    br. karaktera     donji unicode bajt     prvi unimarc karakter    drugi unimarc karakter   treci unimarc karakter
    <pre>
  */
  private char CyrTable[][] ={
                {1, 0x10,  65,  0,  0},  // A
                {1, 0x11,  66,  0,  0},  // B
                {1, 0x26,  67,  0,  0},  // C
                {1, 0x14,  68,  0,  0},  // D
                {1, 0x15,  69,  0,  0},  // E
                {1, 0x24,  70,  0,  0},  // F
                {1, 0x13,  71,  0,  0},  // G
                {1, 0x25,  72,  0,  0},  // H
                {1, 0x18,  73,  0,  0},  // I
                {1, 0x08,  74,  0,  0},  // J
                {1, 0x1a,  75,  0,  0},  // K  10
                {1, 0x1b,  76,  0,  0},  // L
                {1, 0x1c,  77,  0,  0},  // M
                {1, 0x1d,  78,  0,  0},  // N
                {1, 0x1e,  79,  0,  0},  // O
                {1, 0x1f,  80,  0,  0},  // P !
                {1, 0x20,  82,  0,  0},  // R
                {1, 0x21,  83,  0,  0},  // S
                {1, 0x22,  84,  0,  0},  // T
                {1, 0x23,  85,  0,  0},  // U
                {1, 0x12,  86,  0,  0},  // V ! 20
                {1, 0x2b,  89,  0,  0},  // Y
                {1, 0x17,  90,  0,  0},  // Z !
                {1, 0x30,  97,  0,  0},  // a
                {1, 0x31,  98,  0,  0},  // b
                {1, 0x46,  99,  0,  0},  // c
                {1, 0x34, 100,  0,  0},  // d
                {1, 0x35, 101,  0,  0},  // e
                {1, 0x44, 102,  0,  0},  // f
                {1, 0x33, 103,  0,  0},  // g
                {1, 0x45, 104,  0,  0},  // h 30
                {1, 0x38, 105,  0,  0},  // i
                {1, 0x58, 106,  0,  0},  // j
                {1, 0x3a, 107,  0,  0},  // k
                {1, 0x3b, 108,  0,  0},  // l
                {1, 0x3c, 109,  0,  0},  // m
                {1, 0x3d, 110,  0,  0},  // n
                {1, 0x3e, 111,  0,  0},  // o
                {1, 0x3f, 112,  0,  0},  // p !
                {1, 0x40, 114,  0,  0},  // r
                {1, 0x41, 115,  0,  0},  // s
                {1, 0x42, 116,  0,  0},  // t 40
                {1, 0x43, 117,  0,  0},  // u
                {1, 0x32, 118,  0,  0},  // v !
                {1, 0x4b, 121,  0,  0},  // y
                {1, 0x37, 122,  0,  0},  // z
                {1, 0x27, 141,  0,  0},  // Cx
                {1, 0x47, 200,  0,  0},  // cx
                {1, 0x0b, 142,  0,  0},  // Cy
                {1, 0x5b, 201,  0,  0},  // cy
                {1, 0x02, 144,  0,  0},  // Dj
                {1, 0x52, 204,  0,  0},  // dj 50
                {1, 0x28, 175,  0,  0},  // Sx
                {1, 0x48, 233,  0,  0},  // sx
                {1, 0x16, 188,  0,  0},  // Zx
                {1, 0x36, 249,  0,  0},  // zx
                {1, 0x03, 154,  0,  0},  // G'
                {1, 0x53, 213,  0,  0},  // g'
                {1, 0x01, 151,  0,  0},  // E..
                {1, 0x51, 210,  0,  0},  // e..
                {1, 0x06, 157,  0,  0},  // I
                {1, 0x56, 215,  0,  0},  // i  60
                {1, 0x19, 155,  0,  0},  // I^
                {1, 0x39, 214,  0,  0},  // i^
                {1, 0x07, 159,  0,  0},  // I..
                {1, 0x57, 217,  0,  0},  // i..
                {1, 0x0c, 160,  0,  0},  // K'
                {1, 0x5c, 218,  0,  0},  // k'
                {1, 0x0e, 246,  0,  0},  // u'
                {1, 0x5e, 248,  0,  0},  // u`'
                {1, 0x2a, 252,  0,  0},  // "
                {1, 0x4a, 253,  0,  0},  // '  70
                {1, 0x2d, 149,  0,  0},  // E`
                {1, 0x4d, 208,  0,  0},  // e`
                {1, 0x90, 153,  0,  0},  // G`'
                {1, 0x91, 212,  0,  0},  // g`' ????
                {1, 0x2c, 148,  0,  0},  // E`'
                {1, 0x4c, 207,  0,  0},  // e`'
                //{2, 0x05,  68, 90,  0},  // S
                //{2, 0x55, 100,122,  0},  // s
                {2, 0x09,  76, 74,  0},  // Lj
                {2, 0x59, 108,106,  0},  // lj 80
                {2, 0x0a,  78, 74,  0},  // Nj
                {2, 0x5a, 110,106,  0},  // nj
                {3, 0x04,   7, 74, 69},  // +JE
                {3, 0x54,   7,106,101},  // +je
                {3, 0x2f,   7, 74, 65},  // +JA
                {3, 0x4f,   7,106, 97},  // +ja
                {3, 0x2e,   7, 74, 85},  // +JU
                {3, 0x4e,   7,106,117},  // +ju
                {3, 0x0f,   7, 68,188},  // +DZx
                {3, 0x5f,   7,100,249},  // +dzx 90
                {3, 0x29,   7,175,142},  // +SxCy
                {3, 0x49,   7,233,201},  // +sxcy
                {1, 0x05, 146,  0,  0},  // makedonsko S
                {1, 0x55, 147,  0,  0},  // makedonsko s
                };

  /** Returns upper byte of a Unicode character. */
  int Upper(char c) {
    return (int)(c/256);
  }

  /** Returns lower byte of a Unicode character. */
  int Lower(char c) {
    return (int)(c%256);
  }

  /** Converts a Unicode string to an array of upper and lower bytes (each
   *  byte represented as a character).
   *
   *  @param s The Unicode string to be converted
   *  @return Resulting array
   */
  String Unicode2Chars(String s) {
    char a[] = new char[s.length()*2 + 10];
    int i, j = 0;
    for(i = 0; i < s.length(); i++) {
      a[j] = (char)Upper(s.charAt(i));
      j++;
      a[j] = (char)Lower(s.charAt(i));
      j++;
    } // for
    return new String(a, 0, j);
  }

  /** Converts an array of upper and lower bytes into a Unicode string
   *
   *  @param s String with an array of upper and lower bytes
   *    (each byte represented as a character)
   *  @return The resulting Unicode string
   */
  String Chars2Unicode(String s) {
    char a[] = new char[s.length()];
    int i, j = 0; char c;
    for(i = 0; i < s.length();) {
      c = s.charAt(i);
      i++;
      a[j] = s.charAt(i);
      i++;
      c = (char)((int)c << 8);
      a[j] |= c;
      j++;
    }
    return new String(a, 0, j);
  }

//
// KORISTI LI IKO OVE FUNKCIJE ILI DA IH BRISEMO?
//
  /** Removes the given language tag from a UNIMARC string.
   *
   *  @param s UNIMARC string to be cleaned up
   *  @param lang The language tag to be removed
   *  @return The cleaned-up string
   */
  public String stripCode(String s, String lang) {
    int i;
    while ((i = s.indexOf(lang)) != -1) {
      s = s.substring(0, i) + ((i != (s.length() -1)) ? s.substring(i+1) : "");
    }
    return s;
  }

  /** Removes all language tags from a UNIMARC string.
   *
   *  @param s UNIMARC string to be cleaned up
   *  @return The cleaned-up string
   */
  public String stripLangCodes(String s) {
    int i;
    s = stripCode(s, "\34"); // LATIN
    s = stripCode(s, "\35"); // CYR
    s = stripCode(s, "\26"); // CYR_OLD
    s = stripCode(s, "\1");  // GREEK
    return s;
  }
}
