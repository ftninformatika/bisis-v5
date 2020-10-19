package com.ftninformatika.bisis.bssa;

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


public class NabavkaPoUDK extends Report {

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
		  List<NabavkaPoUDK.Item> list = itemMap.get(key);
		  StringBuilder out = getWriter(key);
		  for (NabavkaPoUDK.Item i : list){
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
	  if (rec == null)
	      return;
	  boolean sig=false;
	  String signature="";
	  char c=' ';
	  String greske=" ";
	  for (Primerak p : rec.getPrimerci()) {
		 /* String signature = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), 
		          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
		          p.getSigUDK());*/
		  signature = p.getSigUDK();
		  if (signature==null || signature.equals("")){
			  signature=rec.getSubfieldContent("675a");
		  }
		  if (signature!=null && !signature.equals("")){
			 sig=true;
		  }
			  		  
	  }
	  if (sig==false){
		  log.info("Nema UDK broja. Record ID : "+rec.getRecordID());
		  
	  }else{  
		  for (Primerak p : rec.getPrimerci()) {
			  String invbr=p.getInvBroj();
			  String sigla;
	    
			  if (p.getStatus()!=null) {
	        	if(p.getStatus().equals("9")) //ne broji rashodovane
	        		continue; 
			  } 
			  Date  invDate;
			  String status=p.getStatus();
			  if (status==null) 
	        	status="A";
			  if(status.compareToIgnoreCase("5")==0){
	        	 invDate = p.getDatumStatusa(); 
	        	 sigla=p.getOdeljenje();
			  }else{
	        	 invDate=p.getDatumInventarisanja();
	        	 sigla=invbr.substring(0,2);
	          } 	
	        	
			  String key = settings.getReportName() + getFilenameSuffix(invDate);
			  Item item=getItem(getList(key),sigla);
			  if (item == null ){
	         	item=new Item(sigla);
	         	getList(key).add(item);
	         	
			  }
	      	
	          if (signature.startsWith("0")) {
	             item.add(1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	          }else if (signature.startsWith("1")) {
	              item.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
	          }else if (signature.startsWith("2")) {
	              item.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
	          }else if (signature.startsWith("3")) {
	              item.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
	          }else if (signature.startsWith("5")) {
	              item.add(0, 0, 0, 0, 1, 0, 0, 0, 0,0);
	          }else if (signature.startsWith("6")) {
	              item.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
	          }else if (signature.startsWith("7")) {
	              item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
	          }else if (signature.startsWith("886")) {
	              item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
	          }else if (signature.startsWith("8")) {
		              item.add(0, 0, 0, 0, 0, 0, 0, 0,1, 0);
	          }else if (signature.startsWith("9")) {
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0,1);
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
  public Item getItem(List<Item> iteml, String sigla) {
		
		for (Item it : iteml){
			
			if (it.sigla.compareToIgnoreCase(sigla)==0){	
				return it;
			}
		}
	    return null;
	    
	    
	  }
  public String nvl(String s) {
    return s == null ? "" : s;
  }

  private void addTotal(List<Item> itemList) {
	    Item t = new Item("UKUPNO");
	    	    
	    for (Item i : itemList) {
	    	t.addItem(i);
	    }
	    itemList.add(t);
	  }
  public class Item  implements Comparable{
	    public String sigla;
	    public int adult0;
	    public int adult1;
	    public int adult2;
	    public int adult3;
	    public int adult4;
	    public int adult5;
	    public int adult6;
	    public int adult7;
	    public int adult8;
	    public int adult9;

	    

	    public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.adult0 = 0;
			this.adult1 = 0;
			this.adult2 = 0;
			this.adult3 = 0;
			this.adult4 = 0;
			this.adult5 = 0;
			this.adult6 = 0;
			this.adult7 = 0;
			this.adult8 = 0;
			this.adult9 = 0;
			
			
		}
	    

	    public int compareTo(Object o) {
	        if (o instanceof Item) {
	          Item b = (Item)o;
	          return sigla.compareTo(b.sigla);
	        }
	        return 0;
	      }
	    public void addItem(Item i) {
	    	this.adult0 += i.adult0;
	    	this.adult1 += i.adult1;
	    	this.adult2 += i.adult2;
	    	this.adult3 += i.adult3;
	    	this.adult4 += i.adult4;
	    	this.adult5 += i.adult5;
	    	this.adult6 += i.adult6;
	    	this.adult7 += i.adult7;
	    	this.adult8 += i.adult8;
	    	this.adult9 += i.adult9;

	    	
	        
	      
	    }
	    public String toString() {
	    	StringBuffer buf = new StringBuffer();
			String odeljenje=sigla;
			try {
				sigla = getCoders().getLocCoders().get(sigla).getDescription();
			} catch (Exception e) {}
	        buf.append("\n  <item id=\"");
			buf.append(sigla+"\" name=\"" + odeljenje);
			buf.append("\">\n    <adult0>");
	        buf.append(adult0);
	        buf.append("</adult0>\n    <adult1>");
	        buf.append(adult1);
	        buf.append("</adult1>\n    <adult2>");
	        buf.append(adult2);
	        buf.append("</adult2>\n    <adult3>");
	        buf.append(adult3);
	        buf.append("</adult3>\n    <adult4>");
	        buf.append(adult4);
	        buf.append("</adult4>\n    <adult5>");
	        buf.append(adult5);
	        buf.append("</adult5>\n    <adult6>");
	        buf.append(adult6);
	        buf.append("</adult6>\n    <adult7>");
	        buf.append(adult7);
	        buf.append("</adult7>\n    <adult8>");
	        buf.append(adult8);
	        buf.append("</adult8>\n    <adult9>");
	        buf.append(adult9);
	        buf.append("</adult9>\n ");

	        buf.append("<total>");
	        buf.append(total());
	        buf.append("</total>\n      </item>");
	        return buf.toString();
	    }
	    
	    
	    
	    
	    public void add(int adult0, int adult1, int adult2, int adult3, int adult4, int adult5, int adult6,
	    		int adult7, int adult8, int adult9) {
	    	this.adult0 += adult0;
	    	this.adult1 += adult1;
	    	this.adult2 += adult2;
	    	this.adult3 += adult3;
	    	this.adult4 += adult4;
	    	this.adult5 += adult5;
	    	this.adult6 += adult6;
	    	this.adult7 += adult7;
	    	this.adult8 += adult8;
	    	this.adult9 += adult9;
	        	
	    	
	    }
	   	      
	      public int total() {
	        return adult0+adult1+adult2+adult3+adult4+adult5+adult6+adult7+adult8+adult9;
	      }
	      
	  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(NabavkaPoUDK.class);
  NumberFormat nf;

}
