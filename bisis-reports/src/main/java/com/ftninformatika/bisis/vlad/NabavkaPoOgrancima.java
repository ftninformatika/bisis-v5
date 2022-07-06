package com.ftninformatika.bisis.vlad;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	  
	     /*
      d– obavezni primerak
      e – zateceni fond
      f - sopstveni fond
	  k - kupovina
      m – poklon ministarstva
      p - poklon
      z - zamena
       */
	  @Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;    
		    
		  for (Primerak p : rec.getPrimerci()) {
			  
		      String invbr = p.getInvBroj();		      
		      if (invbr == null || invbr.equals("")) {	        
		         continue;
		      } 		
		      String nacinNab = p.getNacinNabavke();
              if (nacinNab==null){
            	  nacinNab="x";
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
		        Date  invDate=p.getDatumInventarisanja();  
		        	String key = settings.getReportName() +  getFilenameSuffix(invDate);
		            Item item=getItem(getList(key),sigla);    
		            char t = nacinNab.charAt(0);     
		            if (item == null ){
		            	item=new Item(sigla);
		         	   	getList(key).add(item);
		            } 
		       
		            switch (t) {
		            	case 'd':
		            		item.add(1, 0, 0, 0, 0, 0, 0, 0);
		            		break;
		            	case 'e':
		            		item.add(0, 1, 0, 0, 0, 0, 0, 0);
		            		break;
		               	case 'f':
		            		item.add(0, 0, 1, 0, 0, 0, 0, 0);
		            		break;
		            	case 'k':
		            		item.add(0, 0, 0, 1, 0, 0, 0, 0);
		            		break;
		            	case 'm':
		            		item.add(0, 0, 0, 0, 1, 0, 0, 0);
		            		break;
		            	case 'p':
		            		item.add(0, 0, 0, 0, 0, 1, 0, 0);
		            		break;
		            	case 'z':
		            		item.add(0, 0, 0, 0, 0, 0, 1,  0);
		            		break;
		            	default: 
		            		item.add(0, 0, 0, 0, 0, 0, 0, 1);
		            		break;
		            }
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
		    public int obavezan;
		    public int zateceno;
		    public int sopstveniFond;
		    public int kupovina;
		    public int ministarstvo;
		    public int poklon;
		    public int zamena;
		    public int ostalo;

		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.obavezan = 0;
				this.zateceno = 0;
				this.sopstveniFond = 0;
				this.kupovina = 0;
				this.ministarstvo = 0;
				this.poklon = 0;
				this.zamena = 0;
				this.ostalo = 0;
				
			}
		    

		    public int compareTo(Object o) {
		        if (o instanceof Item) {
		          Item b = (Item)o;
		          return sigla.compareTo(b.sigla);
		        }
		        return 0;
		      }
		    public String toString() {
		    	String odeljenje=sigla;
		    	try {
		    		sigla = getCoders().getLocCoders().get(sigla).getDescription();
				} catch (Exception e) {}
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
		        buf.append(	"</ogranak>\n	<obavezan>");
		        buf.append(obavezan);
		        buf.append("</obavezan>\n    <zateceno>");
		        buf.append(zateceno);
		        buf.append("</zateceno>\n    <sopstveniFond>");
		        buf.append(sopstveniFond);
		        buf.append("</sopstveniFond>\n    <kupovina>");
		        buf.append(kupovina);
		        buf.append("</kupovina>\n    <ministarstvo>");
		        buf.append(ministarstvo);
		        buf.append("</ministarstvo>\n    <poklon>");
		        buf.append(poklon);
		        buf.append("</poklon>\n    <zamena>");
		        buf.append(zamena);
		        buf.append("</zamena>\n    <ostalo>");
		        buf.append(ostalo);
		        buf.append("</ostalo>\n    " );
		        buf.append("   		     </item>");
		        
		        return buf.toString();
		    }
		    
		    
		    
		    
		    public void add(int obavezan, int zateceno, int sopstveniFond, int kupovina, int ministarstvo, int poklon,int zamena,int ostalo) {
		    	this.obavezan += obavezan;
				this.zateceno += zateceno;
				this.sopstveniFond += sopstveniFond;
				this.kupovina += kupovina;
				this.ministarstvo += ministarstvo;
				this.poklon += poklon;
				this.zamena += zamena;
				this.ostalo += ostalo;
		    	
		    	
		    }
		    public void addItem(Item i) {
				this.obavezan += i.obavezan;
				this.zateceno += i.zateceno;
				this.sopstveniFond += i.sopstveniFond;
				this.kupovina += i.kupovina;
				this.ministarstvo += i.ministarstvo;
				this.poklon += i.poklon;
				this.zamena += i.zamena;
				this.ostalo += i.ostalo;
			}
		     
		  }
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(NabavkaPoOgrancima.class);
	  NumberFormat nf;
}
