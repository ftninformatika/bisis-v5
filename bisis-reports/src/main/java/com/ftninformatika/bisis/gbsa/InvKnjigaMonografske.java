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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class InvKnjigaMonografske extends Report {
  public class Item implements Comparable<Item> {
    public String invbr;
    public Date datum;
    public String opis;
    public String povez;
    public String dim;
    public String nabavka;
    public String cena;
    public String sig;
    public String napomena;
    public String ogr;

    
    public int compareTo(Item i) {
      return invbr.compareTo(i.invbr);
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("  <item>\n    <rbr>");
      buf.append(invbr);
      buf.append("</rbr>\n    <datum>");
      buf.append(datum == null ? "" : sdf.format(datum));
      buf.append("</datum>\n    <opis>");
      buf.append(StringUtils.adjustForHTML(opis));
      buf.append("</opis>\n    <povez>");
      buf.append(povez == null ? "" :povez);
      buf.append("</povez>\n    <dim>");
      buf.append(StringUtils.adjustForHTML(dim));
      buf.append("</dim>\n    <nabavka>");
      buf.append(nabavka == null ? "" :StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavka)));
      buf.append("</nabavka>\n    <cena>");
      buf.append(StringUtils.adjustForHTML(cena));
      buf.append("</cena>\n    <signatura>");
      buf.append(sig == null ? "" :StringUtils.adjustForHTML(sig));
      buf.append("</signatura>\n    <napomena>");
      buf.append(napomena == null ? "" :StringUtils.adjustForHTML(napomena));
      buf.append("</napomena>\n  </item>\n");
      return buf.toString();
    }
  }

  @Override
  public void init() {
    itemMap.clear();
    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
    //log.info("Report initialized.");
  }
  public StringBuffer finishOnline() {
	//  log.info("Finishing report...");
	  StringBuffer buff = new StringBuffer();
	  buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	  buff.append("<report>");
	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);     
	      for (Item i : list){
	    	  buff.append(i.toString());
	    	   
	      }
	      buff.append("</report>");
	    }
	    itemMap.clear();
	//    log.info("Report finished.");
	   return buff;
	    
  }
  @SuppressWarnings("unchecked")
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
	   
	    itemMap.clear();
	 //   log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = ReportsUtils.getAutor(rec);
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
    String brsveske = rec.getSubfieldContent("200v");
    if (brsveske == null)
      brsveske = "";
    String RN = rec.getSubfieldContent("001e");
    if (RN == null)
      RN = "";

    StringBuffer opis = new StringBuffer();
    opis.append(autor.trim());
    if (opis.length() > 0)
      if (autor.endsWith(".")) {
        opis.append(" ");
      } else {
        opis.append(". ");
      }
    opis.append(naslov.trim());
    opis.append(", ");
    opis.append(brsveske);
    if (brsveske.length() > 0)
      opis.append(", ");
    opis.append(mesto.trim());
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(izdavac.trim());
    if (izdavac.length() > 0)
      opis.append(", ");
    opis.append(god);
    opis.append(".");
    if (RN.length() > 0)
      opis.append(" RN: " + RN);

    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";

    String sig = " ";

    for (Primerak p : rec.getPrimerci()) {

      if (p.getInvBroj() == null)
        continue;
      Matcher matcher = pattern.matcher(p.getInvBroj());
      if (!matcher.matches())
        continue;
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), p
          .getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), p
          .getSigUDK());
      if (sig.equals(""))
        sig = " ";
      Item i = new Item();
      i.invbr = ReportsUtils.nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.povez = "";
      if(getCoders().getBinCoders().get(p.getPovez()) != null)
          i.povez = LatCyrUtils.toCyrillic(getCoders().getBinCoders().get(p.getPovez()).getDescription());
      i.dim = dim;
      String dobavljac = ReportsUtils.nvl(p.getDobavljac());
      String vrnab = ReportsUtils.nvl(p.getNacinNabavke());
      String nabavka = " ";
      if (vrnab.equals("c") || vrnab.equals("p")) {
        nabavka = "poklon";
        if (dobavljac != "" && dobavljac != " ")
          nabavka += ", " + dobavljac;
      } else if (vrnab.equals("a") || vrnab.equals("k")) {
        nabavka = "kupovina";
        if (dobavljac != "" && dobavljac != " ")
          nabavka += ", " + dobavljac;
        String brRac = ReportsUtils.nvl(p.getBrojRacuna());
        if (brRac != "" && brRac != " ")
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
      }
      i.nabavka = LatCyrUtils.toCyrillic(nabavka);
        DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : df2.format(p.getCena()).toString();
      i.sig = sig;
      i.napomena = ReportsUtils.nvl(p.getNapomene());
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

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(InvKnjigaMonografske.class);
  
//@Override
public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
	//  log.info("Finishing report...");
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
	   
	 //   log.info("Report finished.");
}
//@Override
public void finishOnline(StringBuffer buff) {
    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);   
	      Collections.sort(list);
	      for (Item i : list){
	    	  buff.append(i.toString());
	    	   
	      }
	    }
	    itemMap.clear();
}
	
}

