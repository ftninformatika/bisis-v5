package com.ftninformatika.bisis.bs;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;


public class StanjeFonda extends Report {
  
 
	 @Override
	  public void init() {
			nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			pattern = Pattern
					.compile(getReportSettings().getInvnumpattern());
		//	log.info("Report initialized.");
	  }
	  public void finishInv() {
		  
	  }
	  @Override
	  public void finish() {
		//	log.info("Finishing report...");
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
		//	log.info("Report finished.");
	  }
	  public List<Item> getList(String key) {
			List<Item> list = itemMap.get(key);
			if (list == null) {
				list = new ArrayList<Item>();
				itemMap.put(key, list);
			}
			return list;
		}
	  @Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;
		  if (rec.getSubfieldContent("001c")  == null)
			  return;
		  if (!rec.getSubfieldContent("001c").equalsIgnoreCase("m"))
		    	return;  
	
		   String key=null;
		    for (Primerak p : rec.getPrimerci()) {
		    	Matcher matcher = pattern.matcher(p.getInvBroj());
				if (!matcher.matches())
					continue;
		    	String invBr=p.getInvBroj();
		    	if (invBr == null)
		    		continue;
		    	
		    	String ogr=p.getOdeljenje();
		    	if(ogr==null)
		    	ogr = "nepoznato" ;
		        if (p.getStatus()!=null) {
		        	if(p.getStatus().equals("9")) //ne broji rashodovane
		        		continue; 
		        }
					 
		    	try {
		    		Date invDate=p.getDatumInventarisanja();
		        
		    	key = settings.getReportName() + getFilenameSuffix(invDate);
				Item item = getItem(getList(key), ogr);
			      if (item == null ){
			         	item=new Item(ogr);
			         	item.primerci++;
		
			         	getList(key).add(item);	
			      }else{
			    	  item.primerci++;
			    	 
			      }
		      }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		  }
		    if(rec.getPrimerci().size()>0 && key != null){
			  Item item = getItem(getList(key), "999");
		      if (item == null ){
		         	item=new Item("999");
		         	item.zapisi++;
	
		         	getList(key).add(item);	
		      }else{
		    	  item.zapisi++;
		    	 
		      }
		    }
	    }	   
	  
	  public String nvl(String s) {
	    return s == null ? "" : s;
	  }
	 
	  
	  public Item getItem(List<Item> iteml, String sigla) {
			
			for (Item it : iteml){
				
				if (it.sigla.compareToIgnoreCase(sigla)==0){	
					return it;
				}
			}
		    return null;
		    
		    
		  }

	  public class Item implements Comparable  {
		  String sigla;
		  int primerci;	    
		  int zapisi;	 
		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.primerci = 0;
				this.zapisi=0;
			}
		    
		    public int compareTo(Object o) {
			      if (o instanceof Item) {
			        Item b = (Item)o;
			        return sigla.compareTo(b.sigla);
			      }
			      return 0;
			    }
			
		    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item> \n <sigla>");
		      buf.append(sigla);
		      buf.append("</sigla>\n");
		      buf.append("<ogranak>");
		      String si = LatCyrUtils.toCyrillic("nepoznato");
		      if (getCoders().getLocCoders().get(sigla) != null)
		      	si = LatCyrUtils.toCyrillic(getCoders().getLocCoders().get(sigla).getDescription());
		      buf.append(si);
		      buf.append("</ogranak>\n    <primerci>");
		      buf.append(primerci);
		      buf.append("</primerci>\n   <zapisi>");
		      buf.append(zapisi);		
		      buf.append("</zapisi>\n 		</item>");
		      return buf.toString();
		    }
		    		   
		  }
	 
	 
	  SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<>();
	  private static Logger log = Logger.getLogger(StanjeFonda.class);
	  NumberFormat nf;

	//@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub
		
	}
	}
