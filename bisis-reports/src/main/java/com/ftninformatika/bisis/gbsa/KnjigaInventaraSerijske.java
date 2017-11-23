package com.ftninformatika.bisis.gbsa;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Period;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class KnjigaInventaraSerijske extends Report {
	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;	    
	    public String opis;
	    public String god;
	    public String brSv;
	    public String dim;
	    public String povez;	   
	    public String nepovez;
	    public String nabavkaO;
	    public String nabavkaK;
	    public String nabavkaR;
	    public String nabavkaP;
	    public String cena;
	    public String signatura;
	    public String napomena;
	    
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        if(b.god!=null && god!=null)
	          return god.compareTo(b.god);
	        
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
	      buf.append("</opis>\n   <god>");
	      buf.append(god == null ? "" : StringUtils.adjustForHTML(god));
	      buf.append("</god>\n    <brSv>");
	      buf.append(brSv == null ? "" : StringUtils.adjustForHTML(brSv));
	      buf.append("</brSv>\n    <dim>");
	      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
	      buf.append("</dim>\n    <povez>");
	      buf.append(LatCyrUtils.toCyrillic(povez));
	      buf.append("</povez>\n    <nabavkaO>");
	      buf.append(nabavkaO == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavkaO)));
	      buf.append("</nabavkaO>\n    <nabavkaK>");
	      buf.append(nabavkaK == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavkaK)));
	      buf.append("</nabavkaK>\n    <nabavkaR>");
	      buf.append(nabavkaR == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavkaR)));
	      buf.append("</nabavkaR>\n    <nabavkaP>");
	      buf.append(nabavkaP == null ? "" : StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavkaP)));  
	      buf.append("</nabavkaP>\n    <cena>");
	      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));  
	      buf.append("</cena>\n    <signatura>");
	      buf.append(signatura == null ? "" : StringUtils.adjustForHTML(signatura));  
	      buf.append("</signatura>\n    <napomena>");
	      buf.append(napomena == null ? "" : LatCyrUtils.toCyrillic(napomena));  
	      buf.append("</napomena>\n  </item>");
	      return buf.toString();
	    }
	  }

  @Override
  public void init() {
	  itemMap.clear();
	  pattern = Pattern.compile(getReportSettings().getInvnumpattern());
	//  log.info("Report initialized.");
  }

  //@Override
  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
	  //log.info("Finishing report...");
	    for (List<Item> list : itemMap.values())
	      Collections.sort(list);
	    
	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);
	      StringBuilder out = getWriter(key);
	      for (Item i : list){
	    	   out.append(i.toString());
	    	   
	      }out.append("</report>");
            GeneratedReport gr=new GeneratedReport();
            if (key.indexOf("-") >= 0){
                gr.setReportName(key.substring(0,key.indexOf("-")));
                gr.setFullReportName(key);
                gr.setPeriod(key.substring(key.indexOf("-")+1));
            }
            else{
                gr.setReportName(key);
                gr.setFullReportName(key);
                gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

            }
            gr.setContent(out.toString());
            gr.setReportType(getType().name().toLowerCase());
            getReportRepository().save(gr);
	      itemMap.get(key).clear();
	    }
	    
	   // log.info("Report finished1.");
  }
  @Override
  public void finish() {
	//  log.info("Finishing report...");
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
                gr.setFullReportName(key);
                gr.setPeriod(key.substring(key.indexOf("-")+1));
            }
            else{
                gr.setReportName(key);
                gr.setFullReportName(key);
                gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

            }
            gr.setContent(out.toString());
            gr.setReportType(getType().name().toLowerCase());
            getReportRepository().save(gr);
	    }
	    
	  //  closeFiles();
	    itemMap.clear();
	  //  log.info("Report finished2.");
  }

  @Override
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
   
    if(rec.getPubType()!=2)
      return;
    
       String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    
    String podnaslov = rec.getSubfieldContent("200e");
    if (podnaslov == null)
    	podnaslov = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    
    String drzava = rec.getSubfieldContent("102a");
    if (drzava == null)
      drzava = "";
    
    
    StringBuffer opis = new StringBuffer();
    
    
      
    opis.append(naslov);
    
    if (podnaslov.length() > 0)
      opis.append(", ");
    opis.append(podnaslov);
    
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    
    if (drzava.length() > 0)
      opis.append(", ");
    opis.append(drzava);
      
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
     String sig = " ";

    for (Godina p : rec.getGodine()) {
   
      if(p.getInvBroj()==null)
    	  continue;
      Matcher matcher = pattern.matcher(p.getInvBroj());
      if (!matcher.matches())
        continue;
      String brSv = Integer.toString(p.getSveskeCount());
      if (brSv == null)
      	brSv = " ";
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.povez = "";
      i.nepovez = "";
      if (getCoders().getBinCoders().get(p.getPovez()) != null) {
          i.povez = LatCyrUtils.toCyrillic(getCoders().getBinCoders().get(p.getPovez()).getDescription());
          i.nepovez = LatCyrUtils.toCyrillic(getCoders().getBinCoders().get(p.getPovez()).getDescription());
      }
      i.dim = dim;
      i.god=p.getGodina()+", "+p.getGodiste()+", "+p.getBroj();
      String dobavljac=nvl(p.getDobavljac());
      String vrnab = nvl(p.getNacinNabavke());
      //******************    NABAVKA NIJE ZAVRSENA   *************************
      String nabavkaO=" ";
      String nabavkaK=" ";
      String nabavkaR=" ";
      String nabavkaP=" ";
      if (vrnab.equals("c") || vrnab.equals("p")) {
    	  nabavkaP = "poklon";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaP += ", " + dobavljac;
        } else if (vrnab.equals("a") || vrnab.equals("k")) {
          nabavkaK = "kupovina";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaK += ", " + dobavljac;
          String brRac=nvl(p.getBrojRacuna());
          if (brRac!="" && brRac!=" ")
              nabavkaK += ", " + brRac;
            
        } else if (vrnab.equals("b")) {
          nabavkaR = "razmena";
        } else if (vrnab.equals("d")) {
          nabavkaO = "obavezni primerak";
        } else if (vrnab.equals("e")) {
          //nabavka = "zate\u010deni fond";
        } else if (vrnab.equals("f") || vrnab.equals("s")) {
          //nabavka = "sopstvena izdanja";
        } else if (vrnab.equals("o")) {
          //nabavka = "otkup";
        }
        DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : 
        df2.format(p.getCena()).toString();
      i.signatura = sig;
      i.nabavkaO=nabavkaO;
      i.nabavkaR=nabavkaR;
      i.nabavkaK=nabavkaK;
      i.nabavkaP=nabavkaP;
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
  private List<Item> items = new ArrayList<Item>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(KnjigaInventaraSerijske.class);
//@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}
}
