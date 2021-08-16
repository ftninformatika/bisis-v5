package com.ftninformatika.bisis.bpk;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;

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
		nf.setGroupingUsed(false);
		itemMap.clear();
		pattern = Pattern
				.compile(getReportSettings().getInvnumpattern());
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
	//	log.info("Report finished.");
  }

  @Override
  public void handleRecord(Record rec) {
	  boolean sigOk=false;
	  if (rec == null)
	      return;   	    
	  for (Primerak p : rec.getPrimerci()) {
	      String invbr = p.getInvBroj();		      
	      if (invbr == null || invbr.equals("")) {	                
	        continue;
	      }
	      String nacinNab = p.getNacinNabavke();
	      if (nacinNab == null || nacinNab.equals("")) {
	    	  nacinNab="x";
	      }
	      if(nacinNab.equals("x")||(nacinNab.equals("e"))){
	    	continue;
	      }
		  char udkDigit='x';
		   String sig = p.getSigUDK();
		   if (sig==null || sig.trim().equals("")){
				  sig=rec.getSubfieldContent("675a");
				  
		   }
		  	  if (sig!=null && !sig.trim().equals("")){
				  sigOk=true;
				  udkDigit = ReportsUtils.getFirstDigit(sig);
				    if (udkDigit == ' ') {
				    	udkDigit = 'x'; //nema signature
				    	//log.info("Nema UDK broja. Record ID : "+rec.getRecordID());
				    }			
				    if (sig.startsWith("087.5"))
				    	udkDigit = 's'; // slikovnica
			   		  
			  }			  		  
		  if (sigOk==false){
			  udkDigit = 'x'; //nema signature
			//  log.info("Nema UDK broja. Record ID : "+rec.getRecordID());
		  }

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
		  String odelj=p.getOdeljenje();
		  if(odelj==null || odelj.equals(""))
	        odelj=p.getInvBroj().substring(0, 2);	     
	      String key = settings.getReportName() + getFilenameSuffix(invDate);
	     
	      Item item=getItem(getList(key),odelj);
	      if (item == null ){
	         	item=new Item(odelj);
	         	getList(key).add(item);
	         	
	         }

	          switch (udkDigit) {
	            case '0':
	              item.add(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	  	       
	              break;
	            case '1':
	              item.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	              break;
	            case '2':
	              item.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
	              break;
	            case '3':
	              item.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
	              break;
	            case '4':
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 );
	              break;
	            case '5':
	              item.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
	              break;
	            case '6':
	              item.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
	              break;
	            case '7':
	              item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
	              break;
	            case '8':
	              item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
	              break;
	            case '9':
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
	              break;
	            case 's':
		              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
		              break;
	            case 'x':
		              item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
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
	    public int adult5;
	    public int adult6;
	    public int adult7;
	    public int adult8;
	    public int adult9;   
	    public int slik;
	    public int greska;
	    

	    public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.adult0 = 0;
			this.adult1 = 0;
			this.adult2 = 0;
			this.adult3 = 0;
			this.adult5 = 0;
			this.adult6 = 0;
			this.adult7 = 0;
			this.adult8 = 0;
			this.adult9 = 0;
			this.slik = 0;
			this.greska = 0;
						
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
	    	this.adult5 += i.adult5;
	    	this.adult6 += i.adult6;
	    	this.adult7 += i.adult7;
	    	this.adult8 += i.adult8;
	    	this.adult9 += i.adult9;
	    	this.slik += i.slik;
	    	this.greska += i.greska;	      
	    }
	    
	    public String toString() {
	    	StringBuffer buf = new StringBuffer();
	        buf.append("\n  <item id=\"");
	        String si = "nepoznato";
	        if (getCoders().getLocCoders().get(sigla) != null)
	        	si = LatCyrUtils.toCyrillic(getCoders().getLocCoders().get(sigla).getDescription());
	        buf.append(sigla+"\" name=\"" + si);
	        buf.append("\">\n    <adult0>");
	        buf.append(adult0);
	        buf.append("</adult0>\n    <adult1>");
	        buf.append(adult1);
	        buf.append("</adult1>\n    <adult2>");
	        buf.append(adult2);
	        buf.append("</adult2>\n    <adult3>");
	        buf.append(adult3);
	        buf.append("</adult3>\n    <adult5>");
	        buf.append(adult5);
	        buf.append("</adult5>\n    <adult6>");
	        buf.append(adult6);
	        buf.append("</adult6>\n    <adult7>");
	        buf.append(adult7);
	        buf.append("</adult7>\n    <adult8>");
	        buf.append(adult8);
	        buf.append("</adult8>\n    <adult9>");
	        buf.append(adult9);
	        buf.append("</adult9>\n       <slik>");
	        buf.append(slik);
	        buf.append("</slik>\n      <greska>");
	        buf.append(greska);
	        buf.append("</greska>\n       <total>");
	        buf.append(total());
	        buf.append("</total>\n  </item>");
	        return buf.toString();
	    }
	    
	     public void add(int adult0, int adult1, int adult2, int adult3,  int adult5, int adult6,
	    		int adult7, int adult8, int adult9,int slik,int greska) {
	    	this.adult0 += adult0;
	    	this.adult1 += adult1;
	    	this.adult2 += adult2;
	    	this.adult3 += adult3;
	    	this.adult5 += adult5;
	    	this.adult6 += adult6;
	    	this.adult7 += adult7;
	    	this.adult8 += adult8;
	    	this.adult9 += adult9;
	    	this.slik += slik;
	    	this.greska += greska;
	    	
	    } 
	      public int total() {
	        return adult0+adult1+adult2+adult3+adult5+adult6+adult7+adult8+adult9+slik;
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
	    while (pos < s.length()&&!Character.isDigit(s.charAt(pos)))
	      pos++;
	    if (pos == s.length())
	      return ' ';
	    return s.charAt(pos);
	  }
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(NabavkaPoUDK.class);
  NumberFormat nf;
//@Override
public void finishOnline(StringBuffer buff) {
	// TODO Auto-generated method stub
	
}
  

}
