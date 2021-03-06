package com.ftninformatika.bisis.gbns;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public class StanjeFonda extends Report {
  
 
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
                    gr.setPeriod(key.substring(key.indexOf("-")+1));
                }
                else{
                    gr.setReportName(key);
                    gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

                }
				gr.setFullReportName(key);
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
		        if (p.getStatus()!=null) {
		        	if(p.getStatus().equals("9")) //ne broji rashodovane
		        		continue; 
		        }
					 
		    	try {
		    		Date  invDate;
		        if (p.getStatus()!=null) {
		        	if(p.getStatus().equals("5"))  {//za presiglirane gleda datum statusa a ne datum inventarisanja
		        		invDate = p.getDatumStatusa();
		             }else{   //za sve ostale slucajeve uzimam datum inventarisanja
		            	 invDate=p.getDatumInventarisanja();
		           }
		        }else{//ukoliko nema statusa opet uzimam datum inventarisanja 
		        	invDate=p.getDatumInventarisanja();
		        }
		    	String key = settings.getReportName()+ getFilenameSuffix(invDate);
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
		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.primerci = 0;
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
				String sig = "nepoznatno";
				if(getCoders().getLocCoders().get(sigla) != null)
					sig = getCoders().getLocCoders().get(sigla).getDescription();
		      buf.append(LatCyrUtils.toCyrillic(sig));
		      buf.append("</ogranak>\n    <primerci>");
		      buf.append(primerci);
		      buf.append("</primerci>\n  </item>");
		      return buf.toString();
		    }
		    		   
		  }
	 
	 
	  SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<>();
	  private static Logger log = Logger.getLogger(StanjeFonda.class);
	  NumberFormat nf;

	}
