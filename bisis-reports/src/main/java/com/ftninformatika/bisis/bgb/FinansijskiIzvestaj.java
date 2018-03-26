package com.ftninformatika.bisis.bgb;

import java.text.DecimalFormat;
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
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;

public class FinansijskiIzvestaj extends Report {
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

	  @Override
	  public void handleRecord(Record rec) {
		  if (rec == null)
		      return;
		  boolean sig=false;
		  double cena=0;
		  List <String>listaSigli=new ArrayList<String>();
		      
		    
		  for (Primerak p : rec.getPrimerci()) {
			  
		      String invbr = p.getInvBroj();		      
		      if (invbr == null || invbr.equals("")) {	        
		        continue;
		      } 
		      
		      String nacinNabavke = p.getNacinNabavke();
		      if (nacinNabavke == null || nacinNabavke.equals("")) {
		    	  nacinNabavke="x"; 	    	
		      }
		      switch (nacinNabavke.charAt(0)) {
			case 'b':
				nacinNabavke="a";
				break;
			case 'c':
				nacinNabavke="p";
				break;
			case 'e':
				nacinNabavke="t";
				break;
			case 'd':
				nacinNabavke="n";
				break;
			case 's':
				nacinNabavke="x";
				break;
			case 'f':
				nacinNabavke="x";
				break;
			default:
				break;
			}
		      
		      String sigla=null;
		      if (p.getCena()!=null){
		    	  cena=p.getCena().doubleValue();  
		      }else{
		          cena=0;
		      }
		      sigla=p.getOdeljenje();
		      if (sigla==null && p.getInvBroj().length()>2) {
		       sigla=p.getInvBroj().substring(0,2);
		      }else if(sigla==null && p.getInvBroj().length()<2){
		    	  sigla="nepoznato";
		      }
		      
		      try {
		        Date  invDate=p.getDatumInventarisanja();  
		        	String key = settings.getReportName() + getFilenameSuffix(invDate);
		            Item i=getItem(getList(key),sigla);    
		            if (i == null ){
		            	i=new Item(sigla);
		         	   	getList(key).add(i);
		            } 
		         
		            SubItem s=i.getSubItem(nacinNabavke);
		            if (s==null){
		            	s=new SubItem(nacinNabavke);
		            	i.izvestaj.add(s);
		                s.add(1, cena);
		            }else{
		            	 s.add(1, cena);
		            }
		            
		            if (!listaSigli.contains(sigla)){ //broji koliko ima razlicitih naslova na odeljenju
		            	i.brojNaslova++;
		            	listaSigli.add(sigla);
		            }
		           
		     }catch(Exception e){
		    	 e.printStackTrace();
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

public class SubItem{
	private String nacin;
	private int brojPrimerak;
	private double cena;
	
	public SubItem(String nacin){
		this.nacin=nacin;
		brojPrimerak=0;
		cena=0;
	}
	
	public void add(int p, double c){
		brojPrimerak=brojPrimerak+p;
		cena=cena+c;
	}
	
}
	  public class Item  implements Comparable{
		    public String sigla;
		    public int brojNaslova;
		    public List<SubItem>  izvestaj;
		   
	   
		    public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.brojNaslova=0;
				this.izvestaj=new ArrayList<SubItem>();
				
			}
		    
		    public SubItem getSubItem(String nacin){
		    	for (SubItem s:izvestaj){
		    		if (s.nacin.equalsIgnoreCase(nacin)){
		    			return s;
		    		}
		    	}
		    	return null;
		    	
		    }

		    public int compareTo(Object o) {
		        if (o instanceof Item) {
		          Item b = (Item)o;
		          return sigla.compareTo(b.sigla);
		        }
		        return 0;
		      }
		    
		    public String toString() {

		    	String odeljenje=LatCyrUtils.toCyrillic("nepoznato");//HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, sigla);
		    	if (getCoders().getLocCoders().get(sigla) != null)
		    		odeljenje = LatCyrUtils.toCyrillic(getCoders().getLocCoders().get(sigla).getDescription());
				DecimalFormat df=new DecimalFormat();
				df.applyPattern("#.00");
		    	int zarez;
		    	SubItem s;
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
		        buf.append(LatCyrUtils.toCyrillic(odeljenje));
		        buf.append(	"</ogranak>\n");
		        s= getSubItem("a");
		        if(s!=null){
			        buf.append("    <razmenaP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</razmenaP>\n    <razmenaC>");
			        buf.append(df.format(s.cena));
			        buf.append("      </razmenaC>\n  ");
		        }else{
		            buf.append("    <razmenaP>");
			        buf.append(0);
			        buf.append("</razmenaP>\n    <razmenaC>");
			        buf.append(0);
			        buf.append("      </razmenaC>\n  ");
		        }
		        s= getSubItem("i");
		        if(s!=null){
			        buf.append("   <izdanjeBGBP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</izdanjeBGBP>\n    <izdanjeBGBC>");
			        buf.append(df.format(s.cena));
			        buf.append("      </izdanjeBGBC>\n  ");
		        }else{
		        	buf.append("   <izdanjeBGBP>");
			        buf.append(0);
			        buf.append("</izdanjeBGBP>\n    <izdanjeBGBC>");
			        buf.append(0);
			        buf.append("      </izdanjeBGBC>\n  ");
		        }
		        s= getSubItem("k");
		        if(s!=null){
			        buf.append("     <kupovinaP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</kupovinaP>\n     <kupovinaC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </kupovinaC>\n  ");
		        }else{
		        	   buf.append("     <kupovinaP>");
				        buf.append(0);
				        buf.append("</kupovinaP>\n     <kupovinaC>");
				        buf.append(0);
				        buf.append("     </kupovinaC>\n  ");
		        }
		        s= getSubItem("l");
		        if(s!=null){
			        buf.append("<poklonIP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</poklonIP>\n     <poklonIC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </poklonIC>\n  ");
		        }else{
		            buf.append("<poklonIP>");
			        buf.append(0);
			        buf.append("</poklonIP>\n<poklonIC>");
			        buf.append(0);
			        buf.append("</poklonIC>\n  ");
		        }
		        s= getSubItem("m");
		        if(s!=null){
			        buf.append("     <markP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</markP>\n     <markC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </markC>\n  ");
		        }else{
		        	    buf.append("     <markP>");
				        buf.append(0);
				        buf.append("</markP>\n     <markC>");
				        buf.append(0);
				        buf.append("     </markC>\n  ");
		        }
		        s= getSubItem("n");
		        if(s!=null){
			        buf.append("     <opnbsP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</opnbsP>\n     <opnbsC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </opnbsC>\n  ");
		        }else{
		        	 buf.append("     <opnbsP>");
				        buf.append(0);
				        buf.append("</opnbsP>\n     <opnbsC>");
				        buf.append(0);
				        buf.append("     </opnbsC>\n  ");
		        }
		        s= getSubItem("o");
		        if(s!=null){
			        buf.append("     <opsgP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</opsgP>\n     <opsgC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </opsgC>\n  ");
		        }else{
		            buf.append("     <opsgP>");
			        buf.append(0);
			        buf.append("</opsgP>\n     <opsgC>");
			        buf.append(0);
			        buf.append("     </opsgC>\n  ");
		        }
		        s= getSubItem("p");
		        if(s!=null){
			        buf.append("     <poklonP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</poklonP>\n     <poklonC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </poklonC>\n  ");
		        }else{
		            buf.append("     <poklonP>");
			        buf.append(0);
			        buf.append("</poklonP>\n     <poklonC>");
			        buf.append(0);
			        buf.append("     </poklonC>\n  ");
		        }
		        s= getSubItem("r");
		        if(s!=null){
			        buf.append("     <oprsP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</oprsP>\n     <oprsC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </oprsC>\n  ");
		        }else{
		        	 buf.append("     <oprsP>");
				        buf.append(0);
				        buf.append("</oprsP>\n     <oprsC>");
				        buf.append(0);
				        buf.append("     </oprsC>\n  ");
		        }
		        s= getSubItem("t");
		        if(s!=null){
			        buf.append("     <zatP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</zatP>\n     <zatC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </zatC>\n  ");
		        }else{
		        	buf.append("     <zatP>");
			        buf.append(0);
			        buf.append("</zatP>\n     <zatC>");
			        buf.append(0);
			        buf.append("     </zatC>\n  ");
		        }
		        s= getSubItem("z");
		        if(s!=null){
			        buf.append("     <zamP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</zamP>\n     <zamC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </zamC>\n  ");
		        }else{
		        	   buf.append("     <zamP>");
				        buf.append(0);
				        buf.append("</zamP>\n     <zamC>");
				        buf.append(0);
				        buf.append("     </zamC>\n  ");
		        }
		        s= getSubItem("x");
		        if(s!=null){
			        buf.append("     <nerP>");
			        buf.append(s.brojPrimerak);
			        buf.append("</nerP>\n     <nerC>");
			        buf.append(df.format(s.cena));
			        buf.append("     </nerC>\n  ");
		        }else{
		            buf.append("     <nerP>");
			        buf.append(0);
			        buf.append("</nerP>\n     <nerC>");
			        buf.append(0);
			        buf.append("     </nerC>\n  ");
		        }
		        buf.append("<ukupno>");
		        buf.append(brojNaslova);
		        buf.append("</ukupno>");
		        
		        buf.append("		</item>");
		        
		        return buf.toString();
		    }
		    
	  }	    
		    
		    
		   
		    
	
	 
	  
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	  private Pattern pattern;
	  private Map<String, List<Item>> itemMap = new HashMap<>();
	  private static Logger log = Logger.getLogger(FinansijskiIzvestaj.class);
	  NumberFormat nf;

}
