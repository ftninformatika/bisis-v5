package com.ftninformatika.bisis.bjpk;

import com.ftninformatika.bisis.gbzz.NabavkaPoUDK;
import com.ftninformatika.bisis.records.Primerak;
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

public class StanjePoNaslovima extends Report {

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
	String title = rec.getSubfieldContent("200a");
	if (title == null) {

	    return;
	}
	title = title.toUpperCase();
    for (Primerak p : rec.getPrimerci()) {
    	String odeljenje=p.getOdeljenje();
    
    	String invBr = p.getInvBroj();
    	if (invBr == null || invBr.equals("")|| invBr.length()<4){
    		
    		continue;
    	}
    	if (odeljenje == null || odeljenje.equals("")){
        	
    		odeljenje=invBr.substring(0,2);
    	}
    	
    	 String key = getReportSettings().getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
         Item item=getItem(getList(key),odeljenje);
        
        if (item == null ){
    	   item=new Item(odeljenje);
     	   getList(key).add(item);	
        }
        if (!item.titles.contains(title))
             item.titles.add(title);
                 
        }
     
  }
  
  
  public String nvl(String s) {
    return s == null ? "" : s;
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
	    

  public class Item implements Comparable {
	  public String sigla;
	  public List<String> titles;
	  public boolean isTotal;

	    public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.titles = new ArrayList<String>();;			
			
			this.isTotal = false;
			
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
		      buf.append("\n  <item id=\"");
		      buf.append(sigla);
		      buf.append("\" name=\"");
		      buf.append(/*SifarniciReports.getOdeljenjeNaziv(sigla)*/ getName(sigla));
		      buf.append("\" isTotal=\"");
		      buf.append(isTotal);
		      buf.append("\">\n     <titleCount>");
		      buf.append(titles.size());
		      buf.append("</titleCount>\n  </item>");
		      return buf.toString();
		    }
	    
	    	    
	    
	    public void add(String title) {
	    	this.titles.add(title);
	    	
	    	
	    }
	    public void addItem(Item i) {
	    	titles.addAll(i.titles) ;
	    }
	  }
  public class ItemProblems{
	  int noTitle;	 
	  int noBranch;
	  int noInvNum;
	  
	  String problems;
	  public ItemProblems() {
			super();
			this.noTitle = 0;			
			this.noBranch = 0;
			this.noInvNum = 0;
		}
	  
	  
	  public void add(int noTitle, int noBranch, int noInvNum, String problems) {
			this.noTitle += noTitle;
	        this.noBranch += noBranch;
	        this.noInvNum += noInvNum;
	        this.problems += problems;
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noTitle>");
	        buf.append(noTitle);
	        buf.append("</noTitle>\n    <noBranch>");
	        buf.append(noBranch);
	        buf.append("</noBranch>\n   <noInvNum>");
	        buf.append(noInvNum);
	        buf.append("</noInvNum>\n   <problems>");
	        buf.append(problems);
	        buf.append("</problems>\n   </item>");
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
	    while (!Character.isDigit(s.charAt(pos)) && pos < s.length())
	      pos++;
	    if (pos == s.length())
	      return ' ';
	    return s.charAt(pos);
	  }
  public String getName(String sigla) {
	    if (sigla.equals("01"))
	      return "De\u010dje odeljenje";
	    if (sigla.equals("02"))
	      return "Odeljenje za odrasle";
	    if (sigla.equals("03"))
	      return "Stru\u010dne knjige";
	    if (sigla.equals("04"))
	      return "Banatska Topola";
	    if (sigla.equals("05"))
	      return "Banatsko Veliko Selo";
	    if (sigla.equals("06"))
	      return "Ba\u0161aid";
	    if (sigla.equals("07"))
	      return "I\u0111o\u0161";
	    if (sigla.equals("08"))
	      return "Mokrin";
	    if (sigla.equals("09"))
	      return "Nakovo";
	    if (sigla.equals("10"))
	      return "Novi Kozarci";
	    if (sigla.equals("11"))
	      return "Rusko Selo";
	    if (sigla.equals("12"))
	      return "Sajan";
	    if (sigla.equals("13"))
	      return "Zavi\u010dajno";
	    if (sigla.equals("14"))
	      return "Legat";
	    if (sigla.equals("xx"))
	      return "UKUPNO";
	    return sigla;
	  }
  public void addTotals(List<Item> itemList) {
	    
	    Item total = new Item("xx");
	    total.isTotal=true;
	    for (Item i:itemList){
	    	total.addItem(i);
	    }
	    	    
	    items.add(total);
	    
	  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private List<Item> items = new ArrayList<Item>();
  ItemProblems ip;
  private static Log log = LogFactory.getLog(StanjePoNaslovima.class);
  NumberFormat nf;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
}
