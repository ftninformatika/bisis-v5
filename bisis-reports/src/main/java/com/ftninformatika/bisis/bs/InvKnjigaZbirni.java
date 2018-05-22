package com.ftninformatika.bisis.bs;

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
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.log4j.Logger;

public class InvKnjigaZbirni extends Report {
  public class Item implements Comparable<Item> {
    public String invbr;
    public Date datum;
    public String opis;
    public String nabavka;
    public String brDoc;
    public String vrsta;
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
      buf.append("</opis>\n    <vrsta>");
      buf.append(vrsta == null ? "" :vrsta);
      buf.append("</vrsta>\n    <brDoc>");
      buf.append(brDoc == null ? "" :StringUtils.adjustForHTML(brDoc));
      buf.append("</brDoc>\n    <nabavka>");
      buf.append(nabavka == null ? "" :StringUtils.adjustForHTML(LatCyrUtils.toCyrillic(nabavka)));
      buf.append("</nabavka>\n    ");
      buf.append(" <signatura>");
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
    log.info("Report initialized.");
  }
  public StringBuffer finishOnline() {
	  log.info("Finishing report...");
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
	    log.info("Report finished.");
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
    opis.append(autor);
    if (opis.length() > 0)
      if (autor.endsWith(".")) {
        opis.append(" ");
      } else {
        opis.append(". ");
      }
    opis.append(naslov);
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
    opis.append(".");
    if (RN.length() > 0)
      opis.append(" RN: " + RN);

    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    String vrsta= rec.getSubfieldContent("215a");
    if (vrsta == null)
    	vrsta = "";

   
    String sig = " ";
    Subfield s9926;
    int brojPrimeraka=0;
    List <Subfield> subfields9926=rec.getSubfields("9926");
    for(int s=0;s<subfields9926.size();s++){
    	try{
    		s9926=(Subfield)subfields9926.get(s);
        	int tempPrimeraka=Integer.parseInt(s9926.getContent());
        	brojPrimeraka=brojPrimeraka+tempPrimeraka;
    	}catch(Exception e){
    		//log.warn("Record id: "+rec.getRecordID()+" Vrednost u polju 9926 nije broj");
    	}
    	
    }

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
      vrsta=vrsta.replaceAll("<", "");
      vrsta=vrsta.replaceAll(">", ""); 
      i.vrsta = vrsta;
      i.brDoc = String.valueOf(brojPrimeraka);
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
	   
	  //  log.info("Report finished.");
}
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
