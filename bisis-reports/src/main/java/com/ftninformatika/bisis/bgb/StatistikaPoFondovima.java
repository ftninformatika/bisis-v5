package com.ftninformatika.bisis.bgb;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class StatistikaPoFondovima extends Report {
	
	@Override
	public void init() {
		    nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
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
			
			for (Item i : list) {
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
  public void handleRecord(Record rec) {
	Map<String, List<String>> oznakeMap = new HashMap<String, List<String>>();
	Map<String, Boolean> recMap = new HashMap<String, Boolean>();
    if (rec == null)
      return;
    Date dateInv;
    for (Primerak p : rec.getPrimerci()) {
    	String internaOznaka=p.getSigIntOznaka();
    	if (internaOznaka==null){
    		internaOznaka="XXN";
    	}
    	internaOznaka=internaOznaka.trim();
        dateInv=p.getDatumInventarisanja();
        String key = settings.getReportName() + getFilenameSuffix(dateInv);
        Item i = getItem(getList(key),internaOznaka);
       if (i == null ){
        	i = new Item(internaOznaka);
        	 List <String>l=oznakeMap.get(key);
        	 if(l==null){
        		 l=new ArrayList<String>();
        		 l.add(internaOznaka);
        		 oznakeMap.put(key, l);
        		 i.add(1, 1);
        	 }else{
        		if (!l.contains(internaOznaka)){
        		    l.add(internaOznaka);
        		    oznakeMap.put(key, l);
        		    i.add(1, 1);
        	    }else{
        	    	 i.add(0, 1);
        	    }
              }
              getList(key).add(i);
        }else{
              List <String>l=oznakeMap.get(key);
         	 if(l==null){
         		 l=new ArrayList<String>();
         		 l.add(internaOznaka);
         		 oznakeMap.put(key, l);
         		 i.add(1, 1);
         	 }else{
         		if (!l.contains(internaOznaka)){
         		    l.add(internaOznaka);
         		    oznakeMap.put(key, l);
         		    i.add(1, 1);
         	    }else{
         	    	 i.add(0, 1);
         	    }
              }
        }
       Item ukupno = getItem(getList(key),"XXX");     
        if (ukupno == null ){
            ukupno = new Item("XXX");
        	Boolean recAdded=recMap.get(key);
        	if(recAdded==null){
        	  recMap.put(key, new Boolean(true));
        	  ukupno.add(1, 1);
        	}else{
        		ukupno.add(0, 1);
        	}
        	getList(key).add(ukupno); 
        }else{
        	Boolean recAdded=recMap.get(key);
        	if(recAdded==null){
        	  recMap.put(key, new Boolean(true));
        	  ukupno.add(1, 1);
        	}else{
        		ukupno.add(0, 1);
        	}
            
        }
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

  private Item getItem(List<Item> il,String internaOznaka) {
		for (Item item : il){		
			if (item.inv.compareToIgnoreCase(internaOznaka)==0){	
				return item;
			}
		}
	    return null;        
  }


  public class Item implements Comparable {
    public String inv;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String inv) {
      this.inv = inv;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return inv.compareToIgnoreCase(b.inv);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item>");
      buf.append("\">\n    <inv>");
      buf.append(getName(inv));
      buf.append("</inv>\n    <brNas>");
      buf.append(brNas);
      buf.append("</brNas>\n   <brPr>");
      buf.append(brPr);
      buf.append("</brPr>\n    </item>");
      return buf.toString();
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(Item i) {
      brNas += i.brNas;
      brPr += i.brPr;
    }
  }

  public String getName(String code) {
    if (code == null || code.equals(""))
      return "nerazvrstano";
    if (code.compareToIgnoreCase("B")==0)
      return "B - Zavi\u010dajni fond - Beograd";
    if (code.compareToIgnoreCase("BL")==0)
      return "BL - Bibliotekarstvo";
    if (code.compareToIgnoreCase("BLS")==0)
      return "BLS - Bibliotekarstvo - strana knjiga";
    if (code.compareToIgnoreCase("BPR")==0)
      return "BPR - Beogradika - priru\u010dnici";
    if (code.compareToIgnoreCase("BPS")==0)
      return "BPS - Beogradika - priru\u010dnici - strana knjiga";
    if (code.compareToIgnoreCase("BS")==0)
      return "BS - Beogradika - strana knjiga";
    if (code.compareToIgnoreCase("D")==0)
      return "D - De\u010dji fond";
    if (code.compareToIgnoreCase("DEP")==0)
      return "DEP - Depozit";
    if (code.compareToIgnoreCase("DF")==0)
      return "DF - Dijafilmovi";
    if (code.compareToIgnoreCase("DP")==0)
      return "DP - De\u010dje - priru\u010dnici";
    if (code.compareToIgnoreCase("DPS")==0)
      return "DPS - De\u010dje - priru\u010dnici - strana knjiga";
    if (code.compareToIgnoreCase("DR")==0)
      return "DR - De\u010dje - retka knjiga";
    if (code.compareToIgnoreCase("DRS")==0)
      return "DRS - De\u010dje - retka knjiga - strana";
    if (code.compareToIgnoreCase("DS")==0)
      return "DS - De\u010dje - strana knjiga";
    if (code.compareToIgnoreCase("F")==0)
      return "F - Fotografije";
    if (code.compareToIgnoreCase("FA")==0)
      return "FA - Fotografije - zavi\u010dajni fond";
    if (code.compareToIgnoreCase("FM")==0)
      return "FM - Film";
    if (code.compareToIgnoreCase("FMS")==0)
      return "FMS - Film - strana knjiga";
    if (code.compareToIgnoreCase("FS")==0)
      return "FS - Fotografija - strana knjiga";
    if (code.compareToIgnoreCase("FTI")==0)
      return "FTI - Fototipska izdanja";
    if (code.compareToIgnoreCase("G")==0)
      return "G - Gramofonske plo\u010de";
    if (code.compareToIgnoreCase("GK")==0)
      return "GK - Geografske karte";
    if (code.compareToIgnoreCase("IB")==0)
      return "IB - Izdavanje - Beograd";
    if (code.compareToIgnoreCase("IBS")==0)
      return "IBS - Izdavanje - Beograd - strana knjiga";
    if (code.compareToIgnoreCase("K")==0)
      return "K - Kasete";
    if (code.compareToIgnoreCase("L")==0)
      return "L - Legat";
    if (code.compareToIgnoreCase("M")==0)
      return "M - Muzika - knjige";
    if (code.compareToIgnoreCase("MF")==0)
      return "MF - Mikrofilmovi";
    if (code.compareToIgnoreCase("MS")==0)
      return "MS - Muzika - strana knjiga";
    if (code.compareToIgnoreCase("O")==0)
      return "O - Referalna zbirka";
    if (code.compareToIgnoreCase("OS")==0)
      return "OS - Referalna zbirka - strana knjiga";
    if (code.compareToIgnoreCase("P")==0)
      return "P - Pozori\u0161te";
    if (code.compareToIgnoreCase("PBL")==0)
      return "PBL - Periodika bibliografije";
    if (code.compareToIgnoreCase("PF")==0)
      return "PF - Pokretni fond";
    if (code.compareToIgnoreCase("PFS")==0)
      return "PFS - Pokretni fond - strana knjiga";
    if (code.compareToIgnoreCase("PK")==0)
      return "PK - Plakati";
    if (code.compareToIgnoreCase("PN")==0)
      return "PN - Priru\u010dnici - nabavno";
    if (code.compareToIgnoreCase("PNS")==0)
      return "PNS - Priru\u010dnici - nabavno - strana knjiga";
    if (code.compareToIgnoreCase("PPR")==0)
      return "PPR - Periodika - priru\u010dnici";
    if (code.compareToIgnoreCase("PPS")==0)
      return "PPS - Periodika - priru\u010dnici - strana knjiga";
    if (code.compareToIgnoreCase("PS")==0)
      return "PS - Pozori\u0161te - strana knjiga";
    if (code.compareToIgnoreCase("R")==0)
      return "R - Rukopisi";
    if (code.compareToIgnoreCase("RI")==0)
      return "RI - Retka izdanja";
    if (code.compareToIgnoreCase("RIS")==0)
      return "RIS - Retka izdanja - strana knjiga";
    if (code.compareToIgnoreCase("U")==0)
      return "U - Umetnost";
    if (code.compareToIgnoreCase("US")==0)
      return "US - Umetnost - strana knjiga";
    if (code.compareToIgnoreCase("X")==0)
      return "X - Op\u0161ti fond";
    if (code.compareToIgnoreCase("XS")==0)
      return "XS - Op\u0161ti fond - strana knjiga";
    if (code.compareToIgnoreCase("\u0160")==0)
      return "\u0160 - \u0161ah";
    if (code.compareToIgnoreCase("\u0160S")==0)
      return "\u0160S - \u0161ah - strana knjiga";
    if (code.compareToIgnoreCase("XXN")==0)
    	return "nerazvrstano";
    if (code.compareToIgnoreCase("XXX")==0){
    	 return "UKUPNO";
    }else{
      return code;
    }
  }

  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  int recordSum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaPoFondovima.class);
  NumberFormat nf;


}
