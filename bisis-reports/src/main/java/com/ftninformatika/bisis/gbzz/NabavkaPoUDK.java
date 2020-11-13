package com.ftninformatika.bisis.gbzz;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	  if (rec == null)
	      return;
	   Subfield udc=rec.getSubfield("675a");
	   String sig = udc.getContent().trim();
	   char udkDigit = getFirstDigit(sig);
	  if (udkDigit == ' ') {
	    	log.info("Nema udk broja, udk=' ': "+ rec.toString());
	    	udkDigit='8';
	    }
	    if (sig.startsWith("087.5"))
	    	udkDigit = 'x'; // slikovnica
	    
	    
	  for (Primerak p : rec.getPrimerci()) {
		  Date invDate = p.getDatumRacuna();
	      String jezik=p.getInvBroj().substring(2, 4);
	      String odelj=p.getInvBroj().substring(0, 2);
	      if (jezik.equals("04"))
	    		 jezik = "01";
	      if (jezik.equals("05"))
	        jezik = "02";
	      String  sigla=odelj+jezik;
	     
	      String key =  settings.getReportName() + getFilenameSuffix(invDate);
	     
	      Item item=getItem(getList(key),sigla);
	      if (item == null ){
	         	item=new Item(sigla);
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
	              item.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 );
	              break;
	            case '5':
	              item.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
	              break;
	            case '6':
	              item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
	              break;
	            case '7':
	              item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
	              break;
	            case '8':
	              item.add(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
	              break;
	            case '9':
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
	    public int adult4;
	    public int adult5;
	    public int adult6;
	    public int adult7;
	    public int adult8;
	    public int adult9;   
	    public int slik;
	    

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
			this.slik = 0;
				
			
			
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
	    	this.slik += i.slik;
	    	
	        
	      
	    }
	    public String toString() {
	    	StringBuffer buf = new StringBuffer();
	        buf.append("\n  <item id=\"");
	        buf.append(sigla+"\" name=\"" + getName(sigla));
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
	        buf.append("</adult9>\n       <slik>");
	        buf.append(slik);
	        buf.append("</slik>\n      <total>");
	        buf.append(total());
	        buf.append("</total>\n  </item>");
	        return buf.toString();
	    }
	    
	    
	    
	    
	    public void add(int adult0, int adult1, int adult2, int adult3, int adult4, int adult5, int adult6,
	    		int adult7, int adult8, int adult9,int slik) {
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
	    	this.slik += slik;
	    	
	    	
	    }

	      
	      public int total() {
	        return adult0+adult1+adult2+adult3+adult4+adult5+adult6+adult7+adult8+adult9+slik;
	      }
	      
	  }
  public class ItemProblems{
	  int noSignature;
	  int noPrice;
	  int noInvDate;
	  int noBranch;
	  int noInvNum;
	  String problems;
	  public ItemProblems() {
			super();
			this.noSignature = 0;
			this.noPrice = 0;
			this.noInvDate = 0;
			this.noBranch = 0;
			this.noInvNum = 0;
			this.problems = "";
		}
	  
	  
	  public void add(int noSignature, int noPrice, int noInvDate, int noBranch, int noInvNum, String problems) {
			this.noSignature += noSignature;
	        this.noPrice += noPrice;
	        this.noInvDate += noInvDate;
	        this.noBranch += noBranch;	        
	        this.noInvNum += noInvNum;
	        this.problems += problems;
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noSignature>");
	        buf.append(noSignature);
	        buf.append("</noSignature>\n    <noPrice>");
	        buf.append(noPrice);
	        buf.append("</noPrice>\n    <noBranch>");
	        buf.append(noBranch);
	        buf.append("</noBranch>\n    <noInvDate>");
	        buf.append(noInvDate);
	        buf.append("</noInvDate>\n   <noInvNum>");	        
	        buf.append(noInvNum);
	        buf.append("</noInvNum>\n     </item>");
	        return buf.toString();
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

	private String getName(String sigla) {
//  		try {
//  			String sigla1 = coders.getLocCoders().get(sigla.substring(0,2)).getDescription();
//  			String inv = coders.getAccRegCoders().get(sigla.substring(2)).getDescription();
//  			return sigla1 + " - " + inv;
//		} catch (Exception e ) {
  			return sigla;
//		}
	} //todo prosiriti jasper prvu kolonu da stane u formi teksta
  
  public String getNameOld(String sigla) {
	    if (sigla.equals("0101"))
	      return "De\u010dje - srpski";
	    if (sigla.equals("0102"))
	      return "De\u010dje - ma\u0111arski";
	    if (sigla.equals("0104"))
	      return "De\u010dje - slik. srpski";
	    if (sigla.equals("0105"))
	      return "De\u010dje - slik. ma\u0111arski";
	    if (sigla.equals("0103"))
	      return "De\u010dje - strani";
	    if (sigla.equals("0197"))
	      return "Vrti\u0107i";
	    if (sigla.equals("0198"))
	      return "Bolnica";
	    if (sigla.equals("0201"))
	      return "Nau\u010dno - srpski";
	    if (sigla.equals("0206"))
	      return "Nau\u010dno - srpski VP\u0160";
	    if (sigla.equals("0299"))
	      return "Nau\u010dno - srpski PTF";
	    if (sigla.equals("0202"))
	      return "Nau\u010dno - ma\u0111arski";
	    if (sigla.equals("0203"))
	      return "Nau\u010dno - strani";
	    if (sigla.equals("0301"))
	      return "Zavi\u010dajno - srpski";
	    if (sigla.equals("0302"))
	      return "Zavi\u010dajno - ma\u0111arski";
	    if (sigla.equals("0303"))
	      return "Zavi\u010dajno - strani";
	    if (sigla.equals("0401"))
	      return "Pozajmno - srpski";
	    if (sigla.equals("0402"))
	      return "Pozajmno - ma\u0111arski";
	    if (sigla.equals("0403"))
	      return "Pozajmno - strani";
	    if (sigla.equals("01xx"))
	        return "De\u010dje / ukupno";
	      if (sigla.equals("02xx"))
	        return "Nau\u010dno / ukupno";
	      if (sigla.equals("03xx"))
	        return "Zavi\u010dajno / ukupno";
	      if (sigla.equals("04xx"))
	        return "Pozajmno / ukupno";
	      if (sigla.equals("xxxx"))
	        return "UKUPNO";
	    return sigla;
	  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(NabavkaPoUDK.class);
  NumberFormat nf;

}
