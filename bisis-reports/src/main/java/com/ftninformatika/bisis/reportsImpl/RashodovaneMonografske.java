package com.ftninformatika.bisis.reportsImpl;

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
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RashodovaneMonografske extends Report {
  
 
	 @Override
	  public void init() {
			nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			pattern = Pattern
					.compile(getReportSettings().getInvnumpattern());
			log.info("Report initialized.");
	  }
	  public void finishInv() {
		  
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
				gr.setReportName(key.substring(0,key.indexOf("-")));
				gr.setFullReportName(key);
				gr.setPeriod(key.substring(key.indexOf("-")+1));
				gr.setContent(out.toString());
				gr.setReportType(getType().name().toLowerCase());
				getReportRepository().save(gr);
			}
			//closeFiles();

			itemMap.clear();
			log.info("Report finished.");
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
			    
		    for (Primerak p : rec.getPrimerci()) {
		    	String invBr=p.getInvBroj();
		    	if (invBr == null)
		    		continue;
		    	String ogr=p.getOdeljenje();
		    	if(ogr==null)
		    	ogr = invBr.substring(0, 2);    	
		    	try {
		      	  String napomene =p.getNapomene();
		      	  if(p.getStatus().equals("9")){
		      		Date  otpisDate = p.getDatumStatusa(); 
		      	    if(otpisDate==null){
		              if ((napomene != null)&&(napomene.toUpperCase().startsWith("R"))) {
		               String datum = napomene.substring(1,5);
		               otpisDate = intern.parse(datum);
		            
		            }
		      	  }
		    	String key = settings.getReportName()+ getFilenameSuffix(otpisDate);
				Item item = getItem(getList(key), ogr);
			      if (item == null ){
			         	item=new Item(ogr);
			         	item.rashodovano++;
			         	getList(key).add(item);	
			      }else{
			    	  item.rashodovano++;
			      }
		      }
		    }catch(Exception e){
		    	
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
		  int rashodovano;

		 
		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.rashodovano = 0;
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
				if(sigla.startsWith("0") && sigla.length() == 2)
					sigla = sigla.substring(1);
				String sig = "nepoznatno";
				if(getCoders().getLocCoders().get(sigla) != null)
					sig = getCoders().getLocCoders().get(sigla).getDescription();
		      buf.append(LatCyrUtils.toCyrillic(sig));
		      buf.append("</ogranak>\n    <rashodovano>");
		      buf.append(rashodovano);
		      buf.append("</rashodovano>\n  </item>");
		      return buf.toString();
		    }
		    		   
		  }
	 
	 
	  SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(RashodovaneMonografske.class);
	  NumberFormat nf;

	}
