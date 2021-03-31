package com.ftninformatika.bisis.bs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;


public class StatistikaObradjivacaZbirni extends Report {
  @Override
  public void init() {
    itemMap.clear();
    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
   // log.info("Report initialized");
  }

  @Override
  public void finish() {
 //   log.info("Finishing report...");
    for (String key : itemMap.keySet()) {
      Map<String, Item> map = itemMap.get(key);
      StringBuilder out = getWriter(key);
      for (Item i : map.values())
        out.append(i.toString());
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
   // log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
	  boolean ok=false;
	  String ogr="";
    if (rec == null)
      return;
 
    Iterator <Primerak>iter=rec.getPrimerci().iterator();
    while (iter.hasNext() && !ok){
  	  Primerak p=iter.next();
        Matcher matcher = pattern.matcher(p.getInvBroj());
        if (!matcher.matches()){
          continue;
        }else{
          String pom = p.getInvBroj();
          if (pom == null || pom.length() < 4)
             continue;
          ogr = pom.substring(0, 4);
          ok=true;
        }
    }
   if(ok){
    for (Field f : rec.getFields("992")) {
    
      Subfield sf6 = f.getSubfield('6');//broj primeraka
      Subfield sfb = f.getSubfield('f'); //koje obradio
      if (sfb == null || sfb.getContent() == null)
        continue;
      int brPrimeraka = 1;
      String type = f.getSubfield('b').getContent();
      String obr = f.getSubfield('f').getContent();
      String sdate = f.getSubfield('c').getContent();
      if (sf6 != null && sf6.getContent() != null && sf6.getContent().trim().compareToIgnoreCase("")!=0 && type.compareToIgnoreCase("cr")!=0) {
        try {
          brPrimeraka = Integer.parseInt(sf6.getContent().trim());
        } catch (Exception ex) {
          log.warn("Invalid value in f9926: " + sf6.getContent() + ", ID: "
              + rec.getRecordID());
        }
      }
   
      Date date = null;
      try {
        date = intern.parse(sdate);
      } catch (Exception ex) {
    	
    	  log.warn("problem sa datumom "+sdate +": "+rec.getRecordID());
        continue;
      }
      String key = settings.getReportName() + getFilenameSuffix(date);
      Item item = getItem(key, obr);
      if ("cr".equals(type))
        item.add(1, 0, 0, 0,0);
      else if ("dp".equals(type)){
        item.add(0, 1, 0, 0,0);
      }else if ("co".equals(type)){
        item.add(0, 0, 1, 0,0);
      }else if ("re".equals(type))
        item.add(0, 0, 0, brPrimeraka,0);
      else if ("nv".equals(type))
        item.add(0, 0, 0, 0, brPrimeraka);
    }
  }
  }
  
  public class Item implements Comparable<Item> {
    public String obr;
    public int cr;
    public int dp;
    public int co;
    public int re;
    public int nv;
    public int compareTo(Item o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return obr.compareTo(b.obr);
      }
      return 0;
    }

    public Item(String obr) {
      cr = 0;
      dp = 0;
      co = 0;
      re = 0;
      nv=0;
      //this.inventator = HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.LIBRARIAN_CODER, inventator);
      if(this.obr!=null){
    	  this.obr=LatCyrUtils.toCyrillic(this.obr);
     }else{
    	 this.obr=obr;
     }
    }

    public String toString() {
      return "<item><obr>"+obr+"</obr><cr>"+cr+"</cr><dp>"+dp+"</dp><co>"+co+"</co><re>"+re+"</re><nov>"+nv+"</nov></item>\n";
    }

    public int hashCode() {
      return obr.hashCode();
    }

    public boolean equals(Object o) {
      Item i = (Item) o;
      return obr.equals(i.obr);
    }

    public void add(int cr, int dp, int co, int re,int nv) {
      this.cr += cr;
      this.dp += dp;
      this.co += co;
      this.re += re;
      this.nv+=nv;
    }

    public void setObr(String obr) {
      this.obr = obr;
    }
   
  }

  public Item getItem(String key, String obr) {
    Map<String, Item> map = getItemMap(key);
    Item i = getItem(map, obr);
    return i;
  }
  
  public Item getItem(Map<String, Item> map, String obr) {
    Item i = map.get(obr);
    if (i == null) {
      i = new Item(obr);
      map.put(obr, i);
    }
    return i;
  }
  
  public Map<String, Item> getItemMap(String key) {
    Map<String, Item> map = itemMap.get(key);
    if (map == null) {
      map = new HashMap<String, Item>();
      itemMap.put(key, map);
    }
    return map;
  }
  private Pattern pattern;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Map<String, Map<String, Item>> itemMap = new HashMap<>();
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private static Logger log = Logger.getLogger(StatistikaObradjivacaZbirni.class);

}
