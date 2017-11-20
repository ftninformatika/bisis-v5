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
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
		//closeFiles();
		itemMap.clear();
		log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
	  if (rec == null)
	      return;
	  boolean sig=false;
	  int type = 1; // odrasli
	  char c=' ';
	  String greske=" ";
	  for (Primerak p : rec.getPrimerci()) {
		 /* String signature = Signature.format(p.getSigDublet(), p.getSigPodlokacija(), 
		          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
		          p.getSigUDK());*/
		  String signature = p.getSigUDK();
		  if (signature==null || signature.equals("")){
			  signature=rec.getSubfieldContent("675a");
		  }
		  	  if (signature!=null && !signature.equals("")){
			  sig=true;
			  c = ReportsUtils.getFirstDigit(signature);
			    if (c == ' ') {
			    	type = 4; //nema signature
			    	log.info("Nema UDK broja. Record ID : "+rec.getRecordID());
			    	if (!greske.contains(String.valueOf(rec.getRecordID()))){
			        	greske=greske+ " "+String.valueOf(rec.getRecordID());
			        }
			    }
			    if (signature.indexOf("-93") != -1 || signature.indexOf(".053.2") != -1)
			      type = 2; // deca
			    if (signature.startsWith("087.5"))
			      type = 3; // slikovnica
			   /* if (signature.contains("E23")){
				      int e=signature.indexOf("E23");
				      e=e+4;
				      c=ReportsUtils.getFirstDigit(signature.substring(e));
			    }*/	   		  
		  }
			  		  
	  }
	  if (sig==false){
		  type = 4; //nema signature
		  log.info("Nema UDK broja. Record ID : "+rec.getRecordID());
		  if (!greske.contains(String.valueOf(rec.getRecordID()))){
	        	greske=greske+ " "+String.valueOf(rec.getRecordID());
	      }
	  }
	    
	    
	  for (Primerak p : rec.getPrimerci()) {
		  String invbr=p.getInvBroj();
		/*  if(invbr.substring(0, 2).startsWith("31")&&(!invbr.substring(4, 6).startsWith("00"))){
	    		continue;
	    	}
	    	else if(!invbr.substring(2, 4).startsWith("00")){
	    		continue;
	    	}*/
       /*  String sigla = p.getOdeljenje();
	      
	      if (sigla == null || sigla.equals(" ")) {*/
	    	  String sigla;
	    //  }
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
	      
	      if (type == 3) {
	        //b.slik++;
	        item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,0,greske);
	        continue;
	      }
	      switch (type) {
	        case 1:
	          switch (c) {
	            case '0':
	              //b.adult0++;
	              item.add(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	  	       
	              break;
	            case '1':
	              //b.adult1++;
	              item.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '2':
	              //b.adult2++;
	              item.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '3':
	              //b.adult3++;
	              item.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '4': //ukoliko je udk 4 to je greska
	              //b.adult4++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1,greske);
	              break;
	            case '5':
	              //b.adult5++;
	              item.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '6':
	              //b.adult6++;
	              item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '7':
	              //b.adult7++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '8':
	              //b.adult8++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '9':
	              //b.adult9++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	          }
	          break;
	        case 2:
	          switch (c) {
	            case '0':
	              //b.child0++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '1':
	              //b.child1++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '2':
	              //b.child2++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '3':
	              //b.child3++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '4':
	              //b.child4++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1,greske);
	              break;
	            case '5':
	              //b.child5++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,0,greske);
	              break;
	            case '6':
	              //b.child6++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0,0,greske);
	              break;
	            case '7':
	              //b.child7++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0,0,greske);
	              break;
	            case '8':
	              //b.child8++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0,0,greske);
	              break;
	            case '9':
	              //b.child9++;
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0,0,greske);
	              break;
	          }
	          break;
	        case 4:
	        	 item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,1,greske);
	              break;
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
	    Item t = new Item(LatCyrUtils.toCyrillic("UKUPNO"));
	    	    
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
	    public int child0;
	    public int child1;
	    public int child2;
	    public int child3;
	    public int child4;
	    public int child5;
	    public int child6;
	    public int child7;
	    public int child8;
	    public int child9;
	    public int slik;
	    public int xxx;
	    public String greske;

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
			this.child0 = 0;
			this.child1 = 0;
			this.child2 = 0;
			this.child3 = 0;
			this.child4 = 0;
			this.child5 = 0;
			this.child6 = 0;
			this.child7 = 0;
			this.child8 = 0;
			this.child9 = 0;
			this.slik = 0;
			this.xxx=0;	
			greske=" ";
			
			
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
	    	this.child0 += i.child0;
	    	this.child1 += i.child1;
	    	this.child2 += i.child2;
	    	this.child3 += i.child3;
	    	this.child4 += i.child4;
	    	this.child5 += i.child5;
	    	this.child6 += i.child6;
	    	this.child7 += i.child7;
	    	this.child8 += i.child8;
	    	this.child9 += i.child9;
	    	this.slik += i.slik;
	    	this.xxx+=i.xxx;
	    	this.greske=i.greske;
	    	
	        
	      
	    }
	    public String toString() {
	    	StringBuffer buf = new StringBuffer();
	    	if(sigla.startsWith("0") && sigla.length() == 2)
	    		sigla = sigla.substring(1);
	    	String sig = "nepoznatno";
	    	if(getCoders().getLocCoders().get(sigla) != null)
	    		sig = getCoders().getLocCoders().get(sigla).getDescription();
	        buf.append("\n  <item id=\"");
	        buf.append(sigla+" - "+ LatCyrUtils.toCyrillic(sig));
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
	        buf.append("</adult9>\n    <totalAdult>");
	        buf.append(totalAdult());
	        buf.append("</totalAdult>\n    <child0>");
	        buf.append(child0);
	        buf.append("</child0>\n    <child1>");
	        buf.append(child1);
	        buf.append("</child1>\n    <child2>");
	        buf.append(child2);
	        buf.append("</child2>\n    <child3>");
	        buf.append(child3);
	        buf.append("</child3>\n    <child4>");
	        buf.append(child4);
	        buf.append("</child4>\n    <child5>");
	        buf.append(child5);
	        buf.append("</child5>\n    <child6>");
	        buf.append(child6);
	        buf.append("</child6>\n    <child7>");
	        buf.append(child7);
	        buf.append("</child7>\n    <child8>");
	        buf.append(child8);
	        buf.append("</child8>\n    <child9>");
	        buf.append(child9);
	        buf.append("</child9>\n    <slik>");
	        buf.append(slik);
	        buf.append("</slik>\n    <totalChild>");
	        buf.append(totalChild());
	        buf.append("</totalChild>\n    <total>");
	        buf.append(total());
	        buf.append("</total>\n  <xxx>");
	        buf.append(xxx);
	        buf.append("</xxx>\n	<greske>");
	      //  buf.append(greske);
	        buf.append(" ");
	        buf.append("</greske>\n 	</item>");
	        return buf.toString();
	    }
	    
	    
	    
	    
	    public void add(int adult0, int adult1, int adult2, int adult3, int adult4, int adult5, int adult6,
	    		int adult7, int adult8, int adult9, int child0, int child1, int child2, int child3,
	    		int child4, int child5, int child6, int child7, int child8, int child9, int slik,int xxx, String greske) {
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
	    	this.child0 += child0;
	    	this.child1 += child1;
	    	this.child2 += child2;
	    	this.child3 += child3;
	    	this.child4 += child4;
	    	this.child5 += child5;
	    	this.child6 += child6;
	    	this.child7 += child7;
	    	this.child8 += child8;
	    	this.child9 += child9;
	    	this.slik += slik;
	    	this.xxx+=xxx;
	    	this.greske=this.greske+greske;
	    	
	    	
	    }
	      public int total() {
	        return totalAdult() + totalChild();
	      }
	      
	      public int totalAdult() {
	        return adult0+adult1+adult2+adult3+adult4+adult5+adult6+adult7+adult8+adult9;
	      }
	      
	      public int totalChild() {
	        return child0+child1+child2+child3+child4+child5+child6+child7+child8+child9+slik;
	      }
	  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(NabavkaPoUDK.class);
  NumberFormat nf;


}
