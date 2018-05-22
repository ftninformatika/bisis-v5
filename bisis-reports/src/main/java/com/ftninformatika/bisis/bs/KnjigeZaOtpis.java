package com.ftninformatika.bisis.bs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.apache.log4j.Logger;


public class KnjigeZaOtpis extends Report {
  public class Item implements Comparable<Item> {
    public String invbr;
    public String naslov;
    public String autor;
    public String sig;

    
    public int compareTo(Item i) {
      return invbr.compareTo(i.invbr);
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("  <item>\n    <rbr>");
      buf.append(invbr);
      buf.append("</rbr>\n    <naslov>");
      buf.append(StringUtils.adjustForHTML(naslov));
      buf.append("</naslov>\n    ");
      buf.append("<autor>");
      buf.append(StringUtils.adjustForHTML(autor));
      buf.append("</autor>\n    ");
      buf.append("<signatura>");
      buf.append(StringUtils.adjustForHTML(sig));
      buf.append("</signatura>\n    ");
      buf.append("           </item>\n");
      return buf.toString();
    }
  }

  @Override
  public void init() {
    itemMap.clear();
    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
  //  log.info("Report initialized.");
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
            gr.setContent(buff.toString());
            gr.setReportType(getType().name().toLowerCase());
            getReportRepository().save(gr);
	    }
	    itemMap.clear();
	//    log.info("Report finished.");
	   return buff;
	    
  }
  @SuppressWarnings("unchecked")
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
	    log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
	  
	String sig;
    if (rec == null)
      return;
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    
    String autor = ReportsUtils.getAutor(rec);
    if (autor == null)
        autor = "";

    for (Primerak p : rec.getPrimerci()) {

        if (p.getInvBroj() == null)
            continue;
          Matcher matcher = pattern.matcher(p.getInvBroj());
          if (!matcher.matches())
            continue;
      String inventator=p.getInventator();
      String napomena =p.getNapomene();
      
      if(p.getStatus()!=null && p.getStatus().equals("9")) //ne broji rashodovane
  		 continue;
      

      
      if (inventator==null)
    	 inventator="xxx";
      
      inventator=LatCyrUtils.toLatin(inventator).trim().toLowerCase();
      
      if (inventator.startsWith("b"))
    	  continue;
      
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), p
              .getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), p
              .getSigUDK());
      if (sig.equals(""))
            sig = " ";
      
      Item i = new Item();
      i.invbr = ReportsUtils.nvl(p.getInvBroj());
      i.naslov = naslov.toString();
      i.autor=autor;
      i.sig=sig;
    
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
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(InvKnjigaMonografske.class);
  
//@Override
public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
	 // log.info("Finishing report...");
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

