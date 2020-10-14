package com.ftninformatika.bisis.gbzz;

import com.ftninformatika.bisis.general.InvKnjigaMonografske;
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


public class NabavkaPoRacunima extends Report {

	 @Override
	  public void init() {
		  itemMap.clear();
		  nf = NumberFormat.getInstance(Locale.ENGLISH);
	      nf.setMinimumFractionDigits(2);
	      nf.setMaximumFractionDigits(2);
	      nf.setGroupingUsed(false);
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
	   Float cena;
	   Item item;
    if (rec == null)
      return; 
      
    for (Primerak p : rec.getPrimerci()) {
    	Date datInv = p.getDatumRacuna();
    	String key =  settings.getReportName() + getFilenameSuffix(datInv);
    	String invBr = p.getInvBroj();
    	if (invBr == null || invBr.equals("")){  	
    		invBr="XXX";
    	}
    	
    	if (p.getCena()==null){
    		cena=Float.valueOf("0");
    	}else{
    		cena=p.getCena().floatValue();
    	}
   
    	String racun = p.getBrojRacuna();
    	String dobavljac=p.getDobavljac();
    	
    	if(dobavljac==null){
    		dobavljac="";
    	}
    	dobavljac=dobavljac.replaceAll("\"", "");
    	if (racun == null || racun.equals("")){
    		racun="nepoznato";
    	}
    	if(getList(key).size()==0){
      	    item=new Item(racun,dobavljac);
      	    item.subitems.add(new SubItem(invBr.trim(), cena));
       	    getList(key).add(item);	
         }else{
      	    item = getItem(getList(key),racun);
      	    if (item==null){
      	    	item=new Item(racun,dobavljac);
      	    	item.subitems.add(new SubItem(invBr.trim(), cena));
           	    getList(key).add(item);	
      	    }else{
      	    	item.subitems.add(new SubItem(invBr.trim(), cena));
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
  public String nvl(String s) {
    return s == null ? "" : s;
  }

  public Item getItem(List <Item>itemsL,String racun) {
		
		for (Item it : itemsL){
    		if (it.racun.equals(racun)){			
			   return it;
			}
		}
	    
	    
	    return null;
	  }
  public class Item implements Comparable {
	    public String racun;
	    public String dobavljac;
	    public List subitems = new ArrayList();
	    

	    public Item(String racun, String dobavljac) {
			super();
			this.racun = racun;
			this.dobavljac=dobavljac;
		}

		public int hashCode() {
	        return racun.hashCode();
	      }

	    public int compareTo(Object o) {
	        Item obj = (Item)o;
	        return racun.compareTo(obj.racun);
	      }

	    public String toString() {
	        Collections.sort(subitems);
	        StringBuffer buff = new StringBuffer();
	        buff.append("  <item id=\"");
	        buff.append(racun);
	        buff.append("\"  dob=\"");
	        buff.append(dobavljac);
	        buff.append("\" totalBooks=\"");
	        buff.append(subitems.size());
	        buff.append("\">\n");
	        double sum = 0d;
	        for (int i = 0; i < subitems.size(); i++) {
		          String ib = ((SubItem)subitems.get(i)).invbr;
		          double c = ((SubItem)subitems.get(i)).cena;
		          sum += c;
		          buff.append("    <subItem invbr=\"");
		          buff.append(ib);
		          buff.append("\" cena=\"");
		          buff.append(nf.format(c));
		          buff.append("\" isTotal=\"false\"/>\n");
		     }           
	         buff.append("  </item>\n");
		        return buff.toString();
	      }
	  
	    
	    
	  }
  public class SubItem implements Comparable {
	    public SubItem() {
	    }
	    public SubItem(String invbr, double cena) {
	      this.invbr = invbr;
	      this.cena = cena;
	    }
	    
	    public int compareTo(Object o) {
	      SubItem si = (SubItem)o;
	      return invbr.compareTo(si.invbr);
	    }
	    
	    public String invbr;
	    public double cena;
	  }

  public class ItemProblems{
	  int noRacun;
	  int noPrice;
	  int noInvDate;	  
	  int noInvNum;
	  String problems;
	  public ItemProblems() {
			super();
			this.noRacun = 0;
			this.noPrice = 0;
			this.noInvDate = 0;			
			this.noInvNum = 0;
			this.problems = "";
		}
	  
	  
	  public void add(int noRacun, int noPrice, int noInvDate, int noInvNum, String problems) {
			this.noRacun += noRacun;
	        this.noPrice += noPrice;
	        this.noInvDate += noInvDate;	        	        
	        this.noInvNum += noInvNum;
	        this.problems += problems;
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noRacun>");
	        buf.append(noRacun);
	        buf.append("</noRacun>\n    <noPrice>");
	        buf.append(noPrice);
	        buf.append("</noPrice>\n    <noInvDate>");
	        buf.append(noInvDate);
	        buf.append("</noInvDate>\n  <noInvNum>");	        
	        buf.append(noInvNum);
	        buf.append("</noInvNum>\n     </item>");
	        return buf.toString();
	      }
		
  }
  
	 

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private static Log log = LogFactory.getLog(NabavkaPoRacunima.class);
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  NumberFormat nf;
}
