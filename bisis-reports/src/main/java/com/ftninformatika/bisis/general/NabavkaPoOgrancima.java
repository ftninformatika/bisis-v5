package com.ftninformatika.bisis.general;

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
       * b – kupovina sopstvenim sredstvima

      o – kupovina sredstvima opštine

      s – kupovina na sajmu

      e – poklon izdavača

      g – poklon građana

      m – poklon ministarstva

      a – poklon autora

      d,i – izdanje biblioteke

      r – razmena
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
		            	case 'b':
		            		item.add(1, 0, 0, 0, 0, 0, 0, 0, 0, 0);	           
		            		break;
		            	case 'o':  
		            		item.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
		            		break;
		               	case 's': 
		            		item.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
		            		break;
		            	case 'e': 
		            		item.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
		            		break;
		            	case 'g': 
		            		item.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
		            		break;
		            	case 'm':
		            		item.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
		            		break;
		            	case 'a': 
		            		item.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
		            		break;
		            	case 'd': 
		            		item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
		            		break;
		            	case 'i': 
		            		item.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
		            		break;
		            	case 'r': 
		            		item.add(0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
		            		break;
		            	default: 
		            		item.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
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
	     /*
       * b – kupovina sopstvenim sredstvima

      o – kupovina sredstvima opštine

      s – kupovina na sajmu

      i – poklon izdavača

      g – poklon građana

      m – poklon ministarstva

      a – poklon autora

      d – izdanje biblioteke

      r – razmena
       */

	  public class Item  implements Comparable{
		    public String sigla;
		    public int sopstvena;
		    public int opstina;
		    public int sajam;
		    public int izdavac;
		    public int gradjanin;
		    public int pmzk;
		    public int autor;
		    public int bibizd;
		    public int razmena;
		    public int ostalo;


		    

		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.sopstvena = 0;
				this.opstina = 0;
				this.sajam = 0;
				this.izdavac = 0;
				this.gradjanin = 0;
				this.pmzk = 0;
				this.autor = 0;
				this.bibizd = 0;
				this.razmena = 0;
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
		        buf.append(	"</ogranak>\n	<sopstvena>");
		        buf.append(sopstvena);
		        buf.append("</sopstvena>\n    <opstina>");
		        buf.append(opstina);
		        buf.append("</opstina>\n    <sajam>");
		        buf.append(sajam);
		        buf.append("</sajam>\n    <izdavac>");
		        buf.append(izdavac);
		        buf.append("</izdavac>\n    <gradjanin>");
		        buf.append(gradjanin);
		        buf.append("</gradjanin>\n    <autor>");
		        buf.append(autor);
		        buf.append("</autor>\n    <pmzk>");
		        buf.append(pmzk);
		        buf.append("</pmzk>\n    <bibizd>");
		        buf.append(bibizd);
		        buf.append("</bibizd>\n    <razmena>" );
		        buf.append(razmena);
		        buf.append("</razmena>\n    <ostalo>" );
		        buf.append(ostalo);
		        buf.append("</ostalo>\n    " );
		        buf.append("   		     </item>");
		        
		        return buf.toString();
		    }
		    
		    
		    
		    
		    public void add(int sopstvena, int opstina, int sajam, int izdavac, int gradjanin, int pmzk,int autor,int bibizd, int razmena, int ostalo) {
		    	this.sopstvena += sopstvena;
				this.opstina += opstina;
				this.sajam += sajam;
				this.izdavac += izdavac;
				this.gradjanin += gradjanin;
				this.autor += autor;
				this.pmzk += pmzk;
				this.bibizd += bibizd;
				this.razmena += razmena;
				this.ostalo += ostalo;
		    	
		    	
		    }
		    public void addItem(Item i) {
		      	this.sopstvena += i.sopstvena;
				this.opstina += i.opstina;
				this.sajam += i.sajam;
				this.izdavac += i.izdavac;
				this.gradjanin += i.gradjanin;
				this.autor += i.autor;
				this.pmzk += i.pmzk;
				this.bibizd += i.bibizd;
				this.razmena += i.razmena;
				this.ostalo += i.ostalo;
		   
			 }
		     
		  }
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	  private static Log log = LogFactory.getLog(NabavkaPoOgrancima.class);
	  NumberFormat nf;
}
