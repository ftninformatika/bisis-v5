package com.ftninformatika.utils.string;

import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.records.Subsubfield;
import com.ftninformatika.utils.string.StringUtils;

import java.util.StringTokenizer;


/** Generise prikaz bibliotekarske signature za korisnika. */
public class Signature {


    /**	Generise prikaz bibliotekarske signature za korisnika.
     na osnovu primerka
     !Primerak nema s
     */
    public static String format(Primerak p){
        String retVal = "";
        if (p.getSigDublet()!=null && !p.getSigDublet().equals(""))
            retVal += p.getSigDublet() + " ";
        if (p.getSigPodlokacija()!=null && !p.getSigPodlokacija().equals(""))
            retVal += p.getSigPodlokacija() + "-";
        if (p.getSigIntOznaka()!=null && !p.getSigIntOznaka().equals(""))
            retVal += p.getSigIntOznaka() + "-";
        if (p.getSigFormat()!=null && !p.getSigFormat().equals(""))
            retVal += p.getSigFormat() + "-";
        if (p.getSigNumerusCurens()!=null && !p.getSigNumerusCurens().equals(""))
            retVal += p.getSigNumerusCurens();
        if (p.getSigUDK()!=null && !p.getSigUDK().equals("")){
            if (retVal.length() > 0)
                retVal += " ";
            retVal += p.getSigUDK();
        }
        return retVal;

    }

    /** Generise prikaz bibliotekarske signature za korisnika.
     na osnovu godista
     !Godiste ima  s - polje: sigNumeracija
     */
    public static String format(Godina g){
        String retVal = "";
        if (g.getSigDublet()!=null && !g.getSigDublet().equals(""))
            retVal += g.getSigDublet() + " ";
        if (g.getSigPodlokacija()!=null && !g.getSigPodlokacija().equals(""))
            retVal += g.getSigPodlokacija() + "-";
        if (g.getSigIntOznaka()!=null && !g.getSigIntOznaka().equals(""))
            retVal += g.getSigIntOznaka() + "-";
        if (g.getSigFormat()!=null && !g.getSigFormat().equals(""))
            retVal += g.getSigFormat() + "-";
        if (g.getSigNumerusCurens()!=null && !g.getSigNumerusCurens().equals(""))
            retVal += g.getSigNumerusCurens();
        if (g.getSigNumeracija()!=null && !g.getSigNumeracija().equals(""))
            retVal += "/"+g.getSigNumeracija();
        if (g.getSigUDK()!=null && !g.getSigUDK().equals("")){
            if (retVal.length() > 0)
                retVal += " ";
            retVal += g.getSigUDK();
        }

        return retVal;
    }

    public static String format(Subfield sig) {
        if (sig == null)
            return "";

        StringBuffer retVal = new StringBuffer();

        String d = getSubsubfieldContent(sig, 'd');
        String l = getSubsubfieldContent(sig, 'l');
        String i = getSubsubfieldContent(sig, 'i');
        String f = getSubsubfieldContent(sig, 'f');
        String n = getSubsubfieldContent(sig, 'n');
        String s = getSubsubfieldContent(sig, 's');
        String u = getSubsubfieldContent(sig, 'u');

        if (d != null)
            retVal.append(d + " ");
        if (l != null)
            retVal.append(l + "-");
        if (i != null)
            retVal.append(i + "-");
        if (f != null)
            retVal.append(f + "-");
        if (n != null)
            retVal.append(n);
        if (s != null)
            retVal.append("/"+s);
        if (u != null) {
            if (retVal.length() > 0)
                retVal.append(" ");
            retVal.append(u);
        }

        return retVal.toString();
    }

    public static String format(String d, String l, String i, String f, String n, String u) {
        StringBuffer retVal = new StringBuffer();
        if (d != null)
            retVal.append(d + " ");
        if (l != null)
            retVal.append(l + "-");
        if (i != null)
            retVal.append(i + "-");
        if (f != null)
            retVal.append(f + "-");
        if (n != null)
            retVal.append(n);
        if (u != null) {
            if (retVal.length() > 0)
                retVal.append(" ");
            retVal.append(u);
        }
        return retVal.toString();
    }

    public static String format(String d, String l, String i, String f, String n, String s, String u) {
        StringBuffer retVal = new StringBuffer();
        if (d != null)
            retVal.append(d + " ");
        if (l != null)
            retVal.append(l + "-");
        if (i != null)
            retVal.append(i + "-");
        if (f != null)
            retVal.append(f + "-");
        if (n != null)
            retVal.append(n);
        if (s != null)
            retVal.append("/"+s);
        if (u != null) {
            if (retVal.length() > 0)
                retVal.append(" ");
            retVal.append(u);
        }
        return retVal.toString();
    }

    private static String getSubsubfieldContent(Subfield sf, char name) {
        Subsubfield ssf = sf.getSubsubfield(name);
        if (ssf == null)
            return null;
        else
            return ssf.getContent();
    }


    /** Generise prikaz bibliotekarske signature za korisnika.
     *  @param sg Signatura kako je uneta ubazi, sa potpotpoljima i UNIMARC
     *            kodnim rasporedom
     *  @return Interpretirana signatura
     */
    public static String userDisplay(String sg) {
        String d, f, s, n, a, i, l, u, w, x, y, z;
        d = ""; f = ""; s = ""; n = ""; a = ""; i = ""; l = ""; u = "";
        w = ""; x = ""; y = ""; z = "";
        StringTokenizer st = new StringTokenizer(sg, "\\");
        while (st.hasMoreTokens()) {
            String sgPiece = st.nextToken();
            if (sgPiece.length() < 2)
                continue;
            char subsubfieldID = sgPiece.charAt(0);
            switch (subsubfieldID) {
                case 'd': // Dublet
                    d = sgPiece.substring(1);
                    break;
                case 'f': // Format
                    try {
                        int num = Integer.parseInt(sgPiece.substring(1));
                        f = StringUtils.arabicToRoman(num);
                    } catch (NumberFormatException ex) {
                        f = sgPiece.substring(1);
                    }
                    break;
                case 's': // Numeracija
                    s = sgPiece.substring(1);
                    break;
                case 'n': // Tekuci broj
                    n = sgPiece.substring(1);
                    break;
                case 'a': // Oznaka varijante ili ABC oznaka
                    a = sgPiece.substring(1);
                    break;
                case 'i': // Interna oznaka
                    i = sgPiece.substring(1);
                    break;
                case 'l': // Oznaka podlokacije
                    l = sgPiece.substring(1);
                    break;
                case 'u': // Slobodan pristup UDK
                    u = sgPiece.substring(1);
                    break;
                case 'w': // Razresenje numeracije (4. nivo)
                    w = sgPiece.substring(1);
                    break;
                case 'x': // Razresenje numeracije (1. nivo)
                    x = sgPiece.substring(1);
                    break;
                case 'y': // Razresenje numeracije (2. nivo)
                    y = sgPiece.substring(1);
                    break;
                case 'z': // Razresenje numeracije (3. nivo)
                    z = sgPiece.substring(1);
                    break;
            }
        }
        String retVal = "";
//    if (!d.equals(""))
//      retVal += d;
//    if (!l.equals(""))
//      retVal += (retVal.equals("") ? "" : " ") + l;
//    if (!f.equals(""))
//      retVal += (retVal.equals("") ? "" : " ") + f + "-";
//    if (!n.equals(""))
//      retVal += n;
//    if (!s.equals(""))
//      retVal += "/"+s;
//    if (!u.equals("")) {
//      if (retVal.length() > 0)
//        retVal += " ";
//      retVal += u;
//    }
        if (!d.equals(""))
            retVal += d + " ";
        if (!l.equals(""))
            retVal += l + "-";
        if (!i.equals(""))
            retVal += i + "-";
        if (!f.equals(""))
            retVal += f + "-";
        if (!n.equals(""))
            retVal += n;
        if (!s.equals(""))
            retVal += "/"+s;
        if (!u.equals("")){
            if (retVal.length() > 0)
                retVal += " ";
            retVal += u;
        }

        return retVal;
    }

//    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Usage: Signature <signatura>");
//            return;
//        }
//        System.out.println(userDisplay(args[0]));
//    }
}
