package com.ftninformatika.bisis.bjpk;

import com.ftninformatika.bisis.gbzz.NabavkaPoUDK;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class StatistikaObradjivaca extends Report {
  @Override
  public void init() {
    itemMap.clear();
    log.info("Report initialized");
    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
  }

  @Override
  public void finish() {
    log.info("Finishing report...");

    for (String key : itemMap.keySet()) {
      List<Item> list = (List<Item>) itemMap.get(key);
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
      try {
        getReportRepository().save(gr);
      } catch (Exception e) {
        System.out.println("stoj!");
      }
    }
    itemMap.clear();
    log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {

	String obr;
    if (rec == null)
      return;
   /* if (rec.getSubfieldContent("001c").compareToIgnoreCase("m")!=0)
		return;*/
    
    for (Field f : rec.getFields("992")) {
      String type="";
      String sdate=null;
      Subfield sf6 = f.getSubfield('6');//broj primeraka
      Subfield sfb = f.getSubfield('f'); //koje obradio
      if (sfb == null || sfb.getContent() == null)
        continue;
      obr=f.getSubfield('f').getContent();
      int brPrimeraka = 1;
      
		if (f.getSubfield('b')!=null){
		    type = f.getSubfield('b').getContent();
		}
		if (f.getSubfield('c')!=null){
		    sdate = f.getSubfield('c').getContent();
		}
      if (sf6 != null && sf6.getContent() != null && sf6.getContent().trim().compareToIgnoreCase("")!=0) {
  
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
        String key = getReportSettings().getReportName() + getFilenameSuffix(date);
        Item item = getItem(key, obr);
        if ("cr".equals(type))
          item.add(brPrimeraka, 0, 0, 1,0,0);
        else if ("dp".equals(type)){
          item.add(0, brPrimeraka, 0, 0,1,0);
        }else if ("rd".equals(type))
          item.add(0, 0, brPrimeraka,0,0,1);
  
      } catch (Exception ex) {
          log.warn("problem sa datumom "+sdate +": "+rec.getRecordID());
      }
    
      }
  }

  public class Item implements Comparable<Item> {
    public String obr;
    public int cr;
    public int dp;
    public int rd;
    public int crZ;
    public int dpZ;
    public int rdZ;
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
      rd = 0;
      crZ = 0;
      dpZ = 0;
      rdZ = 0;
      this.obr = "Nepoznat";
      try {
        this.obr = getCoders().getLibrarians().get(obr).getIme() + " " + getCoders().getLibrarians().get(obr).getPrezime();
      } catch (Exception e ) {}
      this.obr=LatCyrUtils.toCyrillic(this.obr);

    }

    public String toString() {
        return "<item><obr>"+obr+"</obr><cr>"+cr+"</cr><dp>"+dp+"</dp><re>"+rd+"</re><crZ>"+crZ+"</crZ><dpZ>"+dpZ+"</dpZ><reZ>"+rdZ+"</reZ></item>\n";
      }

    public int hashCode() {
      return obr.hashCode();
    }

    public boolean equals(Object o) {
      Item i = (Item) o;
      return obr.equals(i.obr);
    }

    public void add(int cr, int dp, int rd,int crZ, int dpZ, int rdZ) {
      this.cr += cr;
      this.dp += dp;
      this.rd += rd;
      this.crZ += crZ;
      this.dpZ += dpZ;
      this.rdZ += rdZ;
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

  private Map<String, Map<String, Item>> itemMap = new HashMap<String, Map<String, Item>>();
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private static Log log = LogFactory.getLog(StatistikaObradjivaca.class);
}
