package com.ftninformatika.bisis.bjpk;

import com.ftninformatika.bisis.format.PubTypes;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class StanjeFondaPoJezicima extends Report {


  @Override
  public void init() {
	  itemMap.clear();
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
		  if (key.contains("-")){
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
	  String code = rec.getSubfieldContent("101a");
	    if (code == null) {
	  
	      return;
	    }
	    List primerci = rec.getPrimerci(); 
	    String key = getSettings().getReportName() + getFilenameSuffix(rec.getCreationDate());
        Item item=getItem(getList(key),code);
        if (item == null ){
        	item=new Item(code);
        	item.add(primerci.size());
        	getList(key).add(item);
        	
        }else{
	      item.add(primerci.size());
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
  
  public Item getItem(List<Item> iteml, String code) {
		
		for (Item it : iteml){
			
			if (it.code.compareToIgnoreCase(code)==0){	
				return it;
			}
		}
	    return null;
	    
	    
	  }

  public class Item implements Comparable {
	    public String code;
	    public String text;
	    public int count;
	    
	    

	    public Item(String code) {
			super();
			this.code = code;
			this.text=nvl(PubTypes.getFormat().getSubfield("101a").getCoder().getValue(code));
			this.count = 0;
					
		}

		public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return code.compareTo(b.code);
	      }
	      return 0;
	    }

		public String toString() {
		      StringBuffer buff = new StringBuffer();
		      buff.append("  <item>\n    <code>");
		      buff.append(code);
		      buff.append("</code>\n    <text>");
		      buff.append( text);
		      buff.append("</text>\n    <count>");
		      buff.append(count);
		      buff.append("</count>\n  </item>\n");
		      return buff.toString();
		    }
	    
	    
	    
	    
	    public void add(int count) {
	    	this.count += count;
	    	
	    }
	    
	  }
  public class ItemProblems{
	   
	  int noLang;
	  
	  public ItemProblems() {
			super();
			
			this.noLang = 0;
			
		}
	  
	  
	  public void add(int noLang) {
			this.noLang += noLang;
	        
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noLang> Nedostaje 101a (jezik) u zapisu: ");
	        buf.append(noLang);
	        
	        buf.append("</noLang>\n      </item>");
	        return buf.toString();
	      }
		
  }

  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private List<Item> items = new ArrayList<Item>();
  ItemProblems ip;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StanjeFondaPoJezicima.class);
  NumberFormat nf;

}
