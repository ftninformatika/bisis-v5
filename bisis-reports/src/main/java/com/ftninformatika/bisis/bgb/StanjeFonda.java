package com.ftninformatika.bisis.bgb;

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
	 	 HashMap<String,Boolean> novZapis = new HashMap<String,Boolean>();
		  if (rec == null)
		      return;
		    for (Primerak p : rec.getPrimerci()) {
				if (p.getStatus()!=null) {
					if (p.getStatus().equals("6")) //ne broji rashodovane
						continue;
				}
		    	String invBr=p.getInvBroj();
		    	if (invBr == null)
		    		continue;
		    	String ogr=p.getOdeljenje();
		    	if(ogr==null){
		    	   ogr=p.getInvBroj().substring(0,2);
		    	}
		    	String sublocation = p.getSigPodlokacija();
				if(sublocation==null){
					sublocation=p.getInvBroj().substring(0,4);
				}
		    	try {
		    		Date invDate=p.getDatumInventarisanja();
		        
		    	String key = settings.getReportName()+ getFilenameSuffix(invDate);
				Item item = getItem(getList(key), sublocation);
			      if (item == null ){
			         	item=new Item(sublocation);
			         	item.primerci++;
			         	item.location = ogr;
			         	getList(key).add(item);
			      }else{
			    	  item.primerci++;
			      }
			      if (novZapis.get(sublocation) == null){
					  novZapis.put(sublocation,Boolean.TRUE);
					  item.zapisi++;
				  }
					if (novZapis.get(ogr) == null){
						novZapis.put(ogr,Boolean.TRUE);
						item.zapisiOpstinska++;
					}
					Item item1 = getItem(getList(key), "UKUPNO");
					if (item1 == null ){
						item1=new Item("UKUPNO");
						item1.location = "99";
						getList(key).add(item1);
					}
					if (novZapis.get("UKUPNO") == null){
						novZapis.put("UKUPNO",Boolean.TRUE);
						item1.zapisi++;
					}

		      }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		  }
	    }	   
	  
	  public String nvl(String s) {
	    return s == null ? "" : s;
	  }
	 
	  
	  public Item getItem(List<Item> iteml, String sublocation) {
			
			for (Item it : iteml){
				
				if (it.sublocation.compareToIgnoreCase(sublocation)==0){
					return it;
				}
			}
		    return null;
		    
		    
		  }

	  public class Item implements Comparable  {
		  String location;
		  String sublocation;
		  int primerci;
		  int zapisi;
		  int zapisiOpstinska;
		    public Item(String sublocation) {
				super();
				this.primerci = 0;
				this.zapisi = 0;
				this.zapisiOpstinska = 0;
				this.sublocation = sublocation;
			}
		    
		    public int compareTo(Object o) {
			      if (o instanceof Item) {
			        Item b = (Item)o;
			        return sublocation.compareTo(b.sublocation);
			      }
			      return 0;
			    }
			
		    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item> \n <sigla>");
		      if (location.equalsIgnoreCase("99")){
				  buf.append(location);
			  }else{
				  buf.append(location+" - "+LatCyrUtils.toCyrillic(getCoders().getLocCoders().get(location).getDescription()));
			  }
		      buf.append("</sigla>\n");
				buf.append("<sublocation>");
				buf.append(sublocation);
				buf.append("</sublocation>\n");
		      buf.append("<ogranak>");
		      String sig = LatCyrUtils.toCyrillic("nepoznato");
		      if(getCoders().getSublocCoders().get(sublocation) != null)
		      	sig = LatCyrUtils.toCyrillic(getCoders().getSublocCoders().get(sublocation).getDescription());
		      buf.append(sig);
		      buf.append("</ogranak>\n    <primerci>");
		      buf.append(primerci);
		      buf.append("</primerci>\n  ");
		      buf.append("<zapisi>");
		      buf.append(zapisi);
			  buf.append("</zapisi>\n ");
			  buf.append("<zapisiOpstinska>");
			  buf.append(zapisiOpstinska);
			  buf.append("</zapisiOpstinska>\n    </item>");
		      return buf.toString();
		    }
		    		   
		  }
	 
	 
	  SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<>();
	  private static Logger log = Logger.getLogger(StanjeFonda.class);
	  NumberFormat nf;

	}
