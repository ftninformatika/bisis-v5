package com.ftninformatika.bisis.bgv;

import com.ftninformatika.bisis.bs.InvKnjigaMonografske;
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

public class NabavkaPoOgrancima extends Report {
	 @Override
	  public void init() {
		
		    nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
			log.info("Report initialized.");
	  }
	  public void finishInv() {
		  
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
			  getReportRepository().save(gr);
		  }

		  itemMap.clear();
	  }


	@Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;
		  boolean sig=false;
		  String greske=" ";
		      
		    
		  for (Primerak p : rec.getPrimerci()) {
			  
		      String invbr = p.getInvBroj();		      
		      if (invbr == null || invbr.equals("")) {	        
		        if (!greske.contains(String.valueOf(rec.getRecordID()))){
		        	greske=greske+ " "+rec.getRecordID();
		         }
		        continue;
		      } 		
		      String nacinNab = p.getNacinNabavke();
		      if (nacinNab == null || nacinNab.equals("")) {
		    	  nacinNab="e"; //ovo sam se dogovorila sa Mirom Miljkovic
	    	  if (!greske.contains(String.valueOf(rec.getRecordID()))){
	    		  greske=greske+ " "+rec.getRecordID();
	    		 
		       }
		//    	  log.info("Nema šifre za nabavku. Inventarni broj : "+p.getInvBroj());
		    	
		      }
		      String sigla=null;
		      if (p.getInvBroj().length()>2) {
		       sigla=p.getInvBroj().substring(0,2);
		      }else{
		    	   sigla=p.getOdeljenje();
		       }
		        if (p.getStatus()!=null) {
		        	if(p.getStatus().equals("9")) //ne broji rashodovane
		        		continue; 
		        }  
		        Date  invDate;
		        String status=p.getStatus();
		        if (status==null) 
		        	status="A";
		       /*  if(status.compareToIgnoreCase("5")==0){
		        	Date  presiglirnoDate = p.getDatumStatusa(); 
		        	String ogr=p.getOdeljenje();
		        	String key = settings.getParam("file")+ getFilenameSuffix(presiglirnoDate);
		        	Item item = getItem(getList(key), ogr);
		        	if (item == null ){
			         	item=new Item(ogr);
			         	item.add(0, 0, 0, 0, 0, 0, 0,1,0," ");	  
			         	getList(key).add(item);	
		        	}else{
		        		item.add(0, 0, 0, 0, 0, 0, 0,1,0," ");
		        	}
		    	}else{*/
                    invDate=p.getDatumInventarisanja();
			  		String key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
		            Item item=getItem(getList(key),sigla);    
		            char t = nacinNab.charAt(0);     
		            if (item == null ){
		            	item=new Item(sigla);
		         	   	getList(key).add(item);
		            } 
		            switch (t) {
		            	case 'a': // kupovina
		            	case 'k':
		            		item.add(1, 0, 0, 0, 0, 0, 0,0,0," ");	           
		            		break;
		            	case 'd':   //obavezni primerak
		            		item.add(0, 1, 0, 0, 0, 0, 0,0,0," ");
		            		break;
		            	case 'c': 
		            	case 'p': //poklon  
		            		item.add(0, 0, 1, 0, 0, 0, 0,0,0," ");
		            		break;
		            	case 'o': // otkup
		            		item.add(0, 0, 0, 1, 0, 0, 0,0,0," ");
		            		break;
		            	case 'b': // razmena
		            		item.add(0, 0, 0, 0, 1, 0, 0,0,0," ");
		            		break;
		            	case 'f': // samostalno izdanje
		            	case 's':
		            		item.add(0, 0, 0, 0, 0, 1,0,0,0," ");
		            		break;
		            	case 'e': //zateceni fond
		            		item.add(0, 0, 0, 0, 0, 0,1,0,0," ");
		            		break;
		            	default: //nema informacije o nabavci ili je oznaka r,ili u
		            		item.add(0, 0, 0, 0, 0, 0,0,0,1,greske);
		            		break;
		            }
		    //	}
		   }
	  }
	        
	 
	  public Item getItem(List<Item> iteml, String sigla) {
			
			for (Item it : iteml){
				
				if (it.sigla.compareToIgnoreCase(sigla)==0){	
					return it;
				}
			}
		    return null;
		    
		    
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


	  public class Item  implements Comparable{
		    public String sigla;
		    public int kupovina;
		    public int donacija;
		    public int poklon;
		    public int otkup;
		    public int razmena;
		    public int samizdat;
		    public int zateceno;
		    public int presiglirano;
		    public int nemainform;
		    public String greske;

		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.kupovina = 0;
				this.donacija = 0;
				this.poklon = 0;
				this.otkup = 0;
				this.razmena = 0;
				this.samizdat = 0;
				this.zateceno = 0;
				this.presiglirano = 0;
				this.nemainform=0;
				this.greske="";
				
			}
		    

		    public int compareTo(Object o) {
		        if (o instanceof Item) {
		          Item b = (Item)o;
		          return sigla.compareTo(b.sigla);
		        }
		        return 0;
		      }
		    public String toString() {
				if (getCoders().getLocCoders().get(sigla) != null) {
					sigla = sigla + " - " + getCoders().getLocCoders().get(sigla).getDescription();
				}
		    	String odeljenje=sigla;
		    	int zarez;
		    	if(odeljenje!=null){
		    	 zarez=odeljenje.indexOf(",");
		    	}else{
		    		zarez=-1;
		    		odeljenje=sigla;
		    	}
		    	if(zarez!=-1){
		    		odeljenje=odeljenje.substring(0, zarez);
		    	}
		    	StringBuffer buf = new StringBuffer();
		        buf.append("\n  <item id=\"");
		        buf.append(sigla);
		        buf.append("\">\n  <ogranak>");
		        buf.append(odeljenje);
		        buf.append(	"</ogranak>\n	<kupovina>");
		        buf.append(kupovina);
		        buf.append("</kupovina>\n    <poklon>");
		        buf.append(poklon);
		        buf.append("</poklon>\n    <donacija>");
		        buf.append(donacija);
		        buf.append("</donacija>\n    <otkup>");
		        buf.append(otkup);
		        buf.append("</otkup>\n    <razmena>");
		        buf.append(razmena);
		        buf.append("</razmena>\n    <samizdat>");
		        buf.append(samizdat);
		        buf.append("</samizdat>\n    <zateceno>");
		        buf.append(zateceno);
		        buf.append("</zateceno>\n    <presiglirano>");
		        buf.append(presiglirano);
		        buf.append("</presiglirano>\n		<nemainform>");
		        buf.append(nemainform);
		        buf.append("</nemainform>\n		<greske>");
		        buf.append(greske);
		        buf.append("</greske>\n		</item>");
		        
		        return buf.toString();
		    }
		    
		    
		    
		    
		    public void add(int kupovina, int donacija, int poklon, int otkup, int razmena, int samizdat, int zateceno,int presiglirano,int nemainform,String greske) {
		    	this.kupovina += kupovina;
		    	this.donacija += donacija;
		    	this.poklon += poklon;
		    	this.otkup += otkup;
		    	this.razmena += razmena;
		    	this.samizdat += samizdat;
		    	this.zateceno += zateceno;
		    	this.presiglirano += presiglirano;  
		    	this.nemainform+=nemainform;
		    	this.greske+=greske;
		    	
		    }
		    public void addItem(Item i) {
		      	this.kupovina += i.kupovina;
		    	this.donacija += i.donacija;
		    	this.poklon += i.poklon;
		    	this.otkup += i.otkup;
		    	this.razmena += i.razmena;
		    	this.samizdat += i.samizdat;
		    	this.zateceno += i.zateceno;
		    	this.presiglirano += i.presiglirano;
		    	this.nemainform+=i.nemainform;
		    	this.greske+=i.greske;
			 }
		     
		  }
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(NabavkaPoOgrancima.class);
	  NumberFormat nf;
}
