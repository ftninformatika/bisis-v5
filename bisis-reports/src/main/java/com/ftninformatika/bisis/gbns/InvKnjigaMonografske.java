package com.ftninformatika.bisis.gbns;


import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Period;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.log4j.Logger;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InvKnjigaMonografske extends Report {


	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;
	    public String opis;
	    public String povez;
	    public String dim;
	    public String nabavka;
	    public String cena;
	    public String sig;
	    public String napomena;
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return invbr.substring(4).compareTo(b.invbr.substring(4));
	      }
	      return 0;
	    }

	    public String toString() {
	      StringBuffer buf = new StringBuffer();
	      buf.append("\n  <item>\n    <rbr>");
	      buf.append(invbr);
	      buf.append("</rbr>\n    <datum>");
	      buf.append(datum == null ? "" : sdf.format(datum));
	      buf.append("</datum>\n    <opis>");
	      buf.append(opis==null ? "": StringUtils.adjustForHTML(opis));
	      buf.append("</opis>\n    <povez>");
	      buf.append(povez);
	      buf.append("</povez>\n    <dim>");
	      buf.append(dim);
	      buf.append("</dim>\n    <nabavka>");
	      buf.append(nabavka==null ? "" :nabavka);
	      buf.append("</nabavka>\n    <cena>");
	      buf.append(cena==null ? "" : cena);
	      buf.append("</cena>\n    <signatura>");
	      buf.append(sig==null ? "" : sig);
	      buf.append("</signatura>\n    <napomena>");
	      buf.append(napomena==null ? "" :napomena);
	      buf.append("</napomena>\n");
	      buf.append ("<sortinv>");
	      buf.append(invbr.substring(4));
	      buf.append("</sortinv>\n    </item>");
	      return buf.toString();
	    }
	  }

  @Override
  public void init() {
	    itemMap.clear();
	    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
	    log.info("Report initialized.");
  }

  @Override
  public void finish() {
	  log.info("Finishing report...");
	    for (List<Item> list : itemMap.values())
	      Collections.sort(list);
	    
	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);

	      StringBuilder out = getWriter(key);
	      for (Item i : list){
	    	   out.append(i.toString());
	      }
	       out.append("</report>");

           GeneratedReport gr=new GeneratedReport();
            if (key.indexOf("-") >= 0){
                gr.setReportName(key.substring(0,key.indexOf("-")));
                gr.setPeriod(key.substring(key.indexOf("-")+1));
            }
            else{
                gr.setReportName(key);
                gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

            }
           gr.setFullReportName(key);
           gr.setContent(out.toString());
           gr.setReportType(getType().name().toLowerCase());
           try {
               getReportRepository().save(gr);
           }
           catch (Exception e) {
               System.out.println(e.getMessage());
               log.error("Error saving report: " + key);
               log.error("With message: " + e.getMessage());
           }

	    }
	   
	    itemMap.clear();
	    log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec ) {
    if (rec == null)
      return;
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = getAutor(rec); 
    if (autor == null)
      autor = "";
    autor.trim();
    String izdavac = rec.getSubfieldContent("210c");
    if (izdavac == null)
      izdavac = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";
    String brsveske = rec.getSubfieldContent("200h");
    if (brsveske == null)
      brsveske = "";
    String RN = rec.getSubfieldContent("001e");
    if (RN == null)
      RN = "";
    
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
    opis.append("\n");
    opis.append(naslov);
    if (naslov.length() > 0)
      opis.append(", ");
    opis.append(brsveske);
    if (brsveske.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(izdavac);
    if (izdavac.length() > 0)
      opis.append(", ");
    opis.append(god);
    if (god.length() > 0)
      opis.append(".");
    if (RN.length() > 0)
      opis.append("   RN: " + RN);
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
    String sig = " ";

    for (Primerak p : rec.getPrimerci()) {
    	
      if(p.getInvBroj()==null)
    	  continue;
      Matcher matcher = pattern.matcher(p.getInvBroj());
      if (!matcher.matches())
            continue;

      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
        i.povez="";
      if (getCoders().getBinCoders().get(p.getPovez())!=null)
       i.povez = getCoders().getBinCoders().get(p.getPovez()).getDescription();

        i.dim = dim;
      String dobavljac=nvl(p.getDobavljac());
      String vrnab = nvl(p.getNacinNabavke());
      String nabavka=" ";
      if (getCoders().getAcqCoders().get(vrnab) != null)
          nabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
      /*if (vrnab.equals("c") || vrnab.equals("p")) {
          nabavka = "poklon";
          if (dobavljac!="" && dobavljac!=" ")
            nabavka += ", " + dobavljac;
        } else if (vrnab.equals("a") || vrnab.equals("k")) {
          nabavka = "kupovina";
          if (dobavljac!="" && dobavljac!=" ")
            nabavka += ", " + dobavljac;
          String brRac=nvl(p.getBrojRacuna());
          if (brRac!="" && brRac!=" ")
              nabavka += ", " + brRac;
            
        } else if (vrnab.equals("b")) {
          nabavka = "razmena";
        } else if (vrnab.equals("d")) {
          nabavka = "obavezni primerak";
        } else if (vrnab.equals("e")) {
          nabavka = "zate\u010deni fond";
        } else if (vrnab.equals("f") || vrnab.equals("s")) {
          nabavka = "sopstvena izdanja";
        } else if (vrnab.equals("o")) {
          nabavka = "otkup";
        }*/
      i.nabavka = nabavka;
      i.cena = p.getCena() == null ? " " : p.getCena().setScale(0, RoundingMode.HALF_UP).toString();
      i.sig = sig;
      i.napomena = nvl(p.getNapomene());
      String key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
      getList(key).add(i);
      
      }
  }

  public List<Item> getList(String key) {
	    List<Item> list = itemMap.get(key);
	    if (list == null) {
	      list = new ArrayList<Item>();
	      itemMap.put(key, list);
	    }
	    return list;
  }
  
  public String getAutor(Record rec) {
    if (rec.getField("700") != null) {
      String sfa = rec.getSubfieldContent("700a");
      String sfb = rec.getSubfieldContent("700b");
      if ((sfa != null)&&(!sfa.equals(""))) {
        if ((sfb != null)&&(!sfb.equals("")))
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if ((sfb != null)&&(!sfb.equals("")))
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = rec.getSubfieldContent("701a");
      String sfb = rec.getSubfieldContent("701b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = rec.getSubfieldContent("702a");
      String sfb = rec.getSubfieldContent("702b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
  }

  public String trimZeros(String s) {
    if (s == null)
      return null;
    String retVal = s;
    while (retVal.length() > 0 && retVal.charAt(0) == '0')
      retVal = retVal.substring(1);
    return retVal;
  }
  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0)) + 
            word.substring(1).toLowerCase());
        
    }
    return retVal.toString();
  }
  
  public String nvl(String s) {
    return s == null ? " " : s;
  }

  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Period period;
  private Pattern pattern;
  private List<Item> items = new ArrayList<>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(InvKnjigaMonografske.class);


}
