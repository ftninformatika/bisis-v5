package com.ftninformatika.bisis.gbzz;


import com.ftninformatika.bisis.general.InvKnjigaMonografske;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class NabavkaPoNacinu extends Report {

	 @Override
	  public void init() {
		  itemMap.clear();
		  nf = NumberFormat.getInstance(Locale.GERMANY);
	      nf.setMinimumFractionDigits(2);
	      nf.setMaximumFractionDigits(2);
	      nf.setGroupingUsed(false);
		    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
		    log.info("Report initialized.");
	  }

	  @Override
	  public void finish() {
		  log.info("Finishing report...");

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
    
    for (Primerak p : rec.getPrimerci()) {
    	Date datInv = p.getDatumRacuna();
    	if(datInv == null){
    		continue;
    	}
        String key =  settings.getReportName() + getFilenameSuffix(datInv);
       
    	String nacinNab = p.getNacinNabavke();
    	if (nacinNab == null || nacinNab.equals("")){
    		continue;
    	}
    	
    	if (p.getCena() == null || p.getCena().equals("")) {
    		continue;    		
          }
    	Float cena=p.getCena().floatValue();
    	String brRac = p.getBrojRacuna();
    	if (brRac == null || brRac.equals("")){
    		continue;
    	}
    	String code = nacinNab.trim();
    	if (brRac.toUpperCase().indexOf("PM") != -1)
            code = "x";
    	 if(getList(key).size()==0){
     	    i=new Item(code);
      	    getList(key).add(i);	
         }else{
     	    i = getItem(getList(key),code);
     	   if(i==null){
          	 i=new Item(code);
          }
         }
         i.add(1,cena);            
        
    }
     
  }
  public Item getItem(List <Item>itemsL, String code) {
		for (Item it : itemsL){
	  	  if (it.code.equals(code)){			
			  return it;
		  }
		}
		return null;
  }
  
  public List<Item> getList(String key) {
	    List<Item> list = itemMap.get(key);
	    if (list == null) {
	      list = new ArrayList<Item>();
	      itemMap.put(key, list);
	    }
	    return list;
	  }
  
  public class Item implements Comparable {
	  public String code;
	    public int quantity = 0;
	    public float value = 0f;
	    

	    public Item(String code) {
			super();
			this.code = code;
			this.quantity = 0;
			this.value = 0f;
			
			
		}

		public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return code.compareTo(b.code);
	      }
	      return 0;
	    }

		public String toString() {
	    	String nacin = "nepoznato";
	    	try {
	    		nacin =getCoders().getAcqCoders().get(code).getDescription();
			} catch (Exception e) {
	    					}
		      String c = code;
		      if (code.equals("x"))
		        c = "c";
		      else if (code.equalsIgnoreCase("z"))
		    	c = " ";  
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item code=\"");
		      buf.append(c);
		      buf.append("\" name=\"");
		      buf.append(nacin);
		      buf.append("\">\n    <quantity>");
		      buf.append(quantity);
		      buf.append("</quantity>\n    <value>");
		      buf.append(nf.format(value));
		      buf.append("</value>\n  </item>");
		      return buf.toString();
		    }
	    
	    	    
	    
	    public void add(int quantity, double value) {
	    	this.quantity += quantity;
	    	this.value += value;
	    }
	    public void addItem(Item i) {
	    	quantity += i.quantity;
	        value += i.value;
	      
	    }
	  }

  public char getFirstDigit(String s) {
	    if (s.length() == 0)
	      return ' ';
	    int pos = 0;
	    if (s.charAt(0) == '(') {
	      pos = s.indexOf(')') + 1;
	      if (pos == 0 || pos == s.length())
	        pos = 1;
	    }
	    while (!Character.isDigit(s.charAt(pos)) && pos < s.length())
	      pos++;
	  return s.charAt(pos);
	  }
 
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private static Log log = LogFactory.getLog(NabavkaPoNacinu.class);
  Item i;
  NumberFormat nf;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();

}
