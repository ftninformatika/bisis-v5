package com.ftninformatika.bisis.gbsa;


import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StatistikaBazeZbirni extends Report {

  @Override
  public void init() {
    //  log.info("Report initialized.");
      pattern = Pattern.compile(getReportSettings().getInvnumpattern());
  }

  
  @Override
  public void finish() {
	//  log.info("Finishing report...");
	 
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
	    }
	   
	    itemMap.clear();
	 //   log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
	  boolean ok=false;
	  String ogr="";
	  
      if (rec == null)
        return;
      Item i=null;
      String key;
      Iterator <Primerak>iter=rec.getPrimerci().iterator();
     while (iter.hasNext() && !ok){
    	  Primerak p=iter.next();
          Matcher matcher = pattern.matcher(p.getInvBroj());
          if (!matcher.matches()){
            return;
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
    	 String type="";
   	     String sdate=null;
         Subfield sf6 = f.getSubfield('6');//broj primeraka
         Subfield sfb = f.getSubfield('f'); //koje obradio
         if (sfb == null || sfb.getContent() == null)
           continue;
         int brPrimeraka = 1;
         if (sf6 != null && sf6.getContent() != null && sf6.getContent().trim().compareToIgnoreCase("")!=0) {
           try {
             brPrimeraka = Integer.parseInt(sf6.getContent().trim());
           } catch (Exception ex) {
             log.warn("Invalid value in f9926: " + sf6.getContent() + ", ID: "
                 + rec.getRecordID());
           }
         }
 		if (f.getSubfield('b')!=null){
		    type = f.getSubfield('b').getContent();
		}
		if (f.getSubfield('c')!=null){
		    sdate = f.getSubfield('c').getContent();
		}
         Date date = null;
         try {
           date = intern.parse(sdate);
         } catch (Exception ex) {
           continue;
         }
        key = settings.getReportName() + getFilenameSuffix(date);
        List<Item> list=getList(key);
        int o=Integer.parseInt(ogr.substring(0, 2));
        i=getItem(list,Integer.parseInt(ogr.substring(0, 2)));
        if (i == null ){
      	  i=new Item(o);
      	  getList(key).add(i);  	
        }
       if (ogr.equalsIgnoreCase("0405")){
    	   if(type.equalsIgnoreCase("re")){
    		   i.rekatalogizovanih= i.rekatalogizovanih+brPrimeraka;
    	   } else if(type.equalsIgnoreCase("nv")){
    		   i.novo= i.novo+brPrimeraka;
    	   }
        }else if (ogr.equalsIgnoreCase("0406")){
           if(type.equalsIgnoreCase("re")){
        	   i.rekatPl= i.rekatPl+brPrimeraka;
       	   } else if(type.equalsIgnoreCase("nv")){
       		 i.novoPl= i.novoPl+brPrimeraka;
       	   }
        } else if (ogr.equalsIgnoreCase("0409")){
            if(type.equalsIgnoreCase("re")){
         	   i.rekatRa= i.rekatRa+brPrimeraka;
        	   } else if(type.equalsIgnoreCase("nv")){
        		 i.novoRa= i.novoRa+brPrimeraka;
        	   }
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
  public String nvl(String s) {
    return s == null ? "" : s;
  }
  
  public Item getItem(List <Item>itemsL, int code) {
	for (Item it : itemsL){
  	  if (it.odeljenje==code){			
		  return it;
	  }
	}
	return null;
  }
  public class Item implements Comparable {
	    public int odeljenje;
	    public int rekatalogizovanih;
	    public int novo;
	    public int rekatPl;
	    public int novoPl;
	    public int rekatRa;
	    public int novoRa;
	    
	    public Item (int odeljenje){
	    	this.odeljenje=odeljenje;
	    	this.rekatalogizovanih=0;
	    	this.novo=0;
	    	this.rekatPl=0;
	    	this.novoPl=0;
	    	this.rekatRa=0;
	    	this.novoRa=0;
	    }
	    public int compareTo(Object o) {
	      Item i = (Item)o;
	      if (odeljenje < i.odeljenje)
	        return -1;
	      else if (odeljenje > i.odeljenje)
	        return 1;
	      else
	        return 0;
	    }
	    
	    public void add(Item i) {
	      rekatalogizovanih += i.rekatalogizovanih;
	      novo += i.novo;
	      rekatPl += i.rekatPl;
	      novoPl += i.novoPl;
	      rekatRa += i.rekatRa;
	      novoRa += i.novoRa;
	    }
	    
	    public String toString() {
	      StringBuffer retVal = new StringBuffer();
	      retVal.append("<item><odeljenje>");
	      retVal.append(getOdeljenje(odeljenje));
	      retVal.append("</odeljenje><rekatalog>");
	      retVal.append(rekatalogizovanih);
	      retVal.append("</rekatalog><novo>");
	      retVal.append(novo);
	      retVal.append("</novo><rekatPl>");
	      retVal.append(rekatPl);
	      retVal.append("</rekatPl><novoPl>");
	      retVal.append(novoPl);
	      retVal.append("</novoPl><rekatRa>");
	      retVal.append(rekatRa);
	      retVal.append("</rekatRa><novoRa>");
	      retVal.append(novoRa);
	      retVal.append("</novoRa>");
	      retVal.append("</item>");
	      return retVal.toString();
	    }
	    
	    public String getOdeljenje(int o) {
	      switch (o) {
	        case 1: 
	          return "\u043e\u0434\u0440\u0430\u0441\u043b\u0438"; // odrasli
	        case 2: 
	          return "\u0434\u0435\u0447\u0458\u0435"; // decje
	        case 3:
	          return "\u043d\u0430\u0443\u0447\u043d\u043e"; // naucno
	        case 4:
	          return "\u0437\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u043e"; // zavicajno
	        case 5:
	          return "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
	        default:
	          return "\u043d\u0435\u043f\u043e\u0437\u043d\u0430\u0442\u043e"; // nepoznato
	      }
	    }
	  }
  
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaBaze.class);
  private Pattern pattern;

}
