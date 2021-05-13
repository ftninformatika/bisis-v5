package com.ftninformatika.bisis.prefixes;

import com.ftninformatika.bisis.records.*;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class PrefixConverter {

//  public static Map<String, String> toMap(Record rec, String stRashod) {
//    HashMap<String, String> retVal = new HashMap<>();
//    List<PrefixValue> prefixes = toPrefixes(rec, stRashod);
//    for (PrefixValue pv: prefixes) {
//      retVal.put(pv.prefName, pv.value);
//    }
//    return retVal;
//  }

  public static Map<String, List<String>> toMap(Record rec, String stRashod) {
    return toMap(rec, stRashod, null);
  }

  public static Map<String, List<String>> toMap(Record rec, String stRashod, String lib) {
    HashMap<String, List<String>> retVal = new HashMap<>();
    List<PrefixValue> prefixes = toPrefixes(rec, stRashod);
    if (lib != null) {
      retVal.put("libName", Arrays.asList(lib));
    }
    for (PrefixValue pv: prefixes) {
      if (pv == null || pv.value == null) {
        System.out.println("Record no value" + rec.getRN());
        continue;
      }
      String valueUnaccented = LatCyrUtils.toLatinUnaccented(pv.value.toLowerCase());
      if (retVal.containsKey(pv.prefName)){
        List list = retVal.get(pv.prefName);
        if (!list.contains(valueUnaccented)) {
         retVal.get(pv.prefName).add(valueUnaccented);
        }
      } else {
        List<String> list = new ArrayList<>();
        list.add(valueUnaccented);
        retVal.put(pv.prefName, list);
      }
//      Posebna vrsta indeksiranih polja, namenjena za autocomplete
//      ne koristimo iste indekse jer treba da budu tretirane kao
//      keywords prilikom pretrage
      if (autocompletePrefixMap.keySet().contains(pv.prefName)) {
        String acPref = autocompletePrefixMap.get(pv.prefName);
        String acPrefRaw = acPref + AUTOCOMPLETE_RAW_SUFFIX;
        if (acPref.equals("authors")) {
          valueUnaccented = StringUtils.removeDigitsFromString(valueUnaccented);
          pv.value = WordUtils.capitalizeFully(StringUtils.removeDigitsFromString(pv.value).replace("-", " "));
        }
        if (retVal.containsKey(acPref) && retVal.containsKey(acPrefRaw)){
          List listNormalized = retVal.get(acPref);
          List listRaw = retVal.get(acPrefRaw);
          if (!listNormalized.contains(valueUnaccented)) {
            retVal.get(acPref).add(valueUnaccented);
          }
          if (!listRaw.contains(pv.value)) {
            retVal.get(acPrefRaw).add(pv.value);
          }
        } else {
          List<String> listNormalized = new ArrayList<>();
          listNormalized.add(valueUnaccented);
          List<String> listRaw = new ArrayList<>();
          listRaw.add(pv.value);
          retVal.put(acPref, listNormalized);
          retVal.put(acPrefRaw, listRaw);
        }
      }
//      Posebni indeksi za sortiranje, moraju da sadrze po jednu vrenost
      if (sortPrefixMap.keySet().contains(pv.prefName)) {
        String sortPref = sortPrefixMap.get(pv.prefName);
        List<String> singleValList = new ArrayList<>();
        List<String> valList = retVal.get(pv.prefName);
        if (valList != null && valList.size() > 0) {
          switch (pv.prefName) {
            case "AU": singleValList.add(StringUtils.removeDigitsFromString(valList.get(0))); break;
            case "PY": singleValList.add(StringUtils.removeNonDigitsFromString(valList.get(0))); break;
            case "PU": singleValList.add(valList.get(0)); break;
            case "TI": singleValList.add(valList.get(0)); break;
          }
        }
        if (singleValList.get(0) != null)
          retVal.put(sortPref, singleValList);
      }
    }
    if (retVal.get("IN") != null && retVal.get("IN").size() > 1) {
      retVal.get("IN").sort(Comparator.naturalOrder());
    }
    return retVal;
  }

  public static List<PrefixValue> toPrefixes(Record rec, String stRashod) {
    List<PrefixValue> retVal = new ArrayList<>();
    int brRashod = 0;
    boolean activ = true;
    for (int i = 0; i < rec.getFieldCount(); i++) {
      Field field = rec.getField(i);
      fieldToPrefixes(retVal, field);
    }
    List<Primerak> primerci = rec.getPrimerci();
    List<Godina> godine = rec.getGodine();
    if (primerci != null && primerci.size() > 0) {
      for (int i = 0; i < primerci.size(); i++) {
        Primerak p = primerci.get(i);
        indeksirajPrimerak(retVal, p);
        if (stRashod != null && p.getStatus() != null && p.getStatus().equalsIgnoreCase(stRashod)) {
          brRashod++;
        }
      }
      if (brRashod == primerci.size()) {
        activ = false;
      }
    }
    if (godine != null && godine.size() > 0) {
      for (int i = 0; i < godine.size(); i++) {
        Godina g = godine.get(i);
        indeksirajGodinu(retVal, g);
        List<Sveska> sveske = g.getSveske();
        if (sveske != null && sveske.size() > 0) {
          for (int j = 0; j < sveske.size(); j++) {
            Sveska s = sveske.get(j);
            indeksirajSvesku(retVal, s);
            if (stRashod != null && s.getStatus() != null && s.getStatus().equalsIgnoreCase(stRashod)) {
              brRashod++;
            }
          }
          if (brRashod == sveske.size()) {
            activ = false;
          }
        }
      }
    }
    if (activ) {
      retVal.add(new PrefixValue("XX", "aktivan"));
    } else {
      retVal.add(new PrefixValue("XX", "neaktivan"));
    }

    return retVal;
  }

  private static void indeksirajGodinu(List dest, Godina g) {
    if (g.getInventator() != null)
      dest.add(new PrefixValue("BI", g.getInventator()));
    if (g.getCena() != null)
        dest.add(new PrefixValue("PR", String.valueOf(g.getCena())));
    if (g.getInvBroj() != null)
          dest.add(new PrefixValue("IN", g.getInvBroj()));
      if (g.getNacinNabavke() != null)
          dest.add(new PrefixValue("AM", g.getNacinNabavke()));
      if (g.getDatumInventarisanja() != null)
          dest.add(new PrefixValue("DA", dateFormat.format(g.getDatumInventarisanja())));
      if (g.getFinansijer() != null)
          dest.add(new PrefixValue("FI", g.getFinansijer()));
      if (g.getDostupnost() != null)
          dest.add(new PrefixValue("LI", g.getDostupnost()));
      if (g.getDobavljac() != null)
          dest.add(new PrefixValue("SR",g.getDobavljac()));
      if (g.getNapomene() != null)
          dest.add(new PrefixValue("IR", g.getNapomene()));
      if (g.getOdeljenje() != null)
          dest.add(new PrefixValue("OD",g.getOdeljenje()));
      ///TODO proveriti kako da se indeksira signatura!!! Trenutno je onako kako se vidi u editoru
      List<PrefixValue> signaturePrefixes = getSignaturePrefixes(g);//indeksiranje signatura
      if (signaturePrefixes.size() > 0)
        dest.addAll(signaturePrefixes);

      String sig=Signature.format(g);
      if(!sig.equals(""))
          dest.add(new PrefixValue("SG",sig));
  }

  private static void indeksirajPrimerak(List dest, Primerak p) {

    if (p.getInventator() != null)
      dest.add(new PrefixValue("BI", p.getInventator()));
    if (p.getDatumRacuna() != null)
      dest.add(new PrefixValue("DB", dateFormat.format(p.getDatumRacuna())));
    if (p.getDatumStatusa() != null)
      dest.add(new PrefixValue("DS", dateFormat.format(p.getDatumStatusa())));
    if (p.getCena() != null)
      dest.add(new PrefixValue("PR", String.valueOf(p.getCena())));
      if (p.getStatus() != null)
          dest.add(new PrefixValue("ST", p.getStatus()));
      if (p.getInvBroj() != null) {
        dest.add(new PrefixValue("IN", p.getInvBroj()));
//        if (p.getStatus() != null)
//          dest.add(new PrefixValue("IN_status", p.getStatus() + p.getInvBroj()));

      }
      if (p.getNacinNabavke() != null)
          dest.add(new PrefixValue("AM", p.getNacinNabavke()));
      if (p.getDatumInventarisanja() != null)
          dest.add(new PrefixValue("DA", dateFormat.format(p.getDatumInventarisanja())));
      if (p.getFinansijer() != null)
          dest.add(new PrefixValue("FI", p.getFinansijer()));
      if (p.getDostupnost() != null)
          dest.add(new PrefixValue("LI", p.getDostupnost()));
      if (p.getDobavljac() != null)
          dest.add(new PrefixValue("SR", p.getDobavljac()));
      if (p.getNapomene() != null)
          dest.add(new PrefixValue("IR", p.getNapomene()));
      if (p.getOdeljenje() != null)
          dest.add(new PrefixValue("OD",p.getOdeljenje()));

      if (p.getStatus() != null && p.getOdeljenje() != null)
        dest.add(new PrefixValue("OD_showable",p.getStatus() + p.getOdeljenje()));

      ///TODO proveriti kako da se indeksira signatura!!! Trenutno je onako kako se vidi u editoru
      List<PrefixValue> signaturePrefixes = getSignaturePrefixes(p);
      if (signaturePrefixes.size() > 0)
        dest.addAll(signaturePrefixes);

         String sig=Signature.format(p);
      if(!sig.equals(""))
          dest.add(new PrefixValue("SG",sig));

  }

  private static void indeksirajSvesku(List dest, Sveska s) {
    dest.add(new PrefixValue("IN", s.getInvBroj()));
    if (s.getInventator() != null)
      dest.add(new PrefixValue("BI", s.getInventator()));
    if (s.getStatus() != null) {
      dest.add(new PrefixValue("ST", s.getStatus()));
//      dest.add(new PrefixValue("IN_status", s.getStatus() + s.getInvBroj()));
      if (s.getDatumStatusa() != null)
            dest.add(new PrefixValue("DS", dateFormat.format(s.getDatumStatusa())));
    }
  }

  public static List getPrefixValues(Record rec, String prefName) {
    List retVal = toPrefixes(rec, null);
    Iterator it = retVal.iterator();
    while (it.hasNext()) {
      PrefixValue pv = (PrefixValue) it.next();
      if (!pv.prefName.equals(prefName))
        it.remove();
    }
    return retVal;
  }

  private static void fieldToPrefixes(List<PrefixValue> dest, Field field) {
    if (field.getName().startsWith("70") || field.getName().startsWith("90")) {
      String[] autor = prefixHandler.createAuthor(field);
      if (autor.length != 0) {
        if (!autor[0].equals(""))
          dest.add(new PrefixValue("AU", autor[0]));
        if (!autor[1].equals(""))
          dest.add(new PrefixValue("AU", autor[1]));
      }
    }
    if (field.getName().startsWith("60")) {
      String[] pnautor = prefixHandler.createAuthor(field);
      if (pnautor.length != 0) {
        if (!pnautor[0].equals(""))
          dest.add(new PrefixValue("PN", pnautor[0]));
        if (!pnautor[1].equals(""))
          dest.add(new PrefixValue("PN", pnautor[1]));
      }
    }
    if (field.getName().startsWith("675")){
      if (field.getSubfield('a') != null && field.getSubfield('a').getContent().length()>0 ) {
        String udkGroup = field.getSubfield('a').getContent().substring(0, 1);
        dest.add(new PrefixValue("UG", udkGroup));
      }
    }
    for (int i = 0; i < field.getSubfieldCount(); i++) {
      Subfield subfield = field.getSubfield(i);
      if (subfield.getSecField() != null) {
        dest.add(new PrefixValue(field.getName() + subfield.getName(), subfield.getSecField().getName()));
        fieldToPrefixes(dest, subfield.getSecField());
      } else if (subfield.getSubsubfieldCount() > 0) {
        String content = prefixHandler
            .concatenateSubsubfields(subfield);
        if (!content.equals("")) {
          List prefList = prefixMap.getPrefixes(field.getName()
              + subfield.getName());
          if (prefList != null) {
            Iterator it = prefList.iterator();
            while (it.hasNext()) {
              String prefName = (String) it.next();
              dest.add(new PrefixValue(prefName, content));
            }
          }
        }
      } else {
        List<String> prefList = prefixMap.getPrefixes(field.getName()
            + subfield.getName());
        if (prefList != null) {
          Iterator it = prefList.iterator();
          while (it.hasNext()) {
            String prefName = (String) it.next();
            if (prefName.equals("AU"))
              continue;
            if (subfield.getContent() != null && !subfield.getContent().equals(""))
              dest.add(new PrefixValue(prefName, subfield
                  .getContent()));
          }
        }
        dest.add(new PrefixValue(field.getName() + subfield.getName(),
            subfield.getContent()));
        // da bi u listu prefiksa dodali i potpolja npr 200a, posto je
        // sada samo TI u prefiksima
      }
    }
  }

    private static List<PrefixValue> getSignaturePrefixes(Primerak p) {
        return getSignaturePrefixes(p.getStatus(), p.getSigFormat(), p.getSigPodlokacija(), p.getSigIntOznaka(),
                p.getSigDublet(), p.getSigNumerusCurens(), p.getSigUDK(), null);
    }

    private static List<PrefixValue> getSignaturePrefixes(Godina g) {
        return getSignaturePrefixes(g.getSigFormat(), g.getSigPodlokacija(), g.getSigIntOznaka(),
                g.getSigDublet(), g.getSigNumerusCurens(), g.getSigUDK(), g.getSigNumeracija());
    }

  private static List<PrefixValue> getSignaturePrefixes(String sigFormat, String sigPodlokacija,
                                                        String sigIntOznaka, String sigDublet, String sigNumerusCurens, String sigUDK, String sigNumeracija) {
    return getSignaturePrefixes(null, sigFormat, sigPodlokacija, sigIntOznaka, sigDublet, sigNumerusCurens, sigUDK, sigNumeracija);
  }
    private static List<PrefixValue> getSignaturePrefixes(String status, String sigFormat, String sigPodlokacija,
                                                          String sigIntOznaka, String sigDublet, String sigNumerusCurens, String sigUDK, String sigNumeracija) {
        List<PrefixValue> retVal = new ArrayList<>();
        if (sigFormat != null && sigFormat.trim().length() > 0)
            retVal.add(new PrefixValue("SF", sigFormat.trim()));
        if (sigPodlokacija != null && sigPodlokacija.trim().length() > 0) {
          retVal.add(new PrefixValue("SL", sigPodlokacija.trim()));
          if (status != null)
            retVal.add(new PrefixValue("SL_showable", status + sigPodlokacija.trim()));
        }
        if (sigIntOznaka != null && sigIntOznaka.trim().length() > 0)
            retVal.add(new PrefixValue("SW", sigIntOznaka.trim()));
        if (sigDublet != null && sigDublet.trim().length() > 0)
            retVal.add(new PrefixValue("SX", sigDublet.trim()));
        if (sigNumerusCurens != null && sigNumerusCurens.trim().length() > 0)
            retVal.add(new PrefixValue("SY", sigNumerusCurens.trim()));
        if (sigUDK != null && sigUDK.trim().length() > 0)
            retVal.add(new PrefixValue("SZ", sigUDK.trim()));
      if (sigNumeracija != null && sigNumeracija.trim().length() > 0)
            retVal.add(new PrefixValue("SQ", sigNumeracija.trim()));
        return retVal;
    }


  static PrefixConfig prefixConfig;
  static PrefixHandler prefixHandler;
  static PrefixMap prefixMap;
  static String AUTOCOMPLETE_RAW_SUFFIX = "_raw";
  static Map<String, String> autocompletePrefixMap = new HashMap<>();
  static Map<String, String> sortPrefixMap = new HashMap<>();
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  static {
    try {
      prefixConfig = PrefixConfigFactory.getPrefixConfig();
      prefixHandler = prefixConfig.getPrefixHandler();
      prefixMap = prefixConfig.getPrefixMap();
      autocompletePrefixMap.put("AU", "authors");
      autocompletePrefixMap.put("SB", "subjects");
      autocompletePrefixMap.put("PU", "publishers");
      autocompletePrefixMap.put("TI", "titles");
      autocompletePrefixMap.put("KW", "keywords");

      sortPrefixMap.put("AU", "AU_sort");
      sortPrefixMap.put("TI", "TI_sort");
      sortPrefixMap.put("PY", "PY_sort");
      sortPrefixMap.put("PU", "PU_sort");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
