package com.ftninformatika.bisis.gbzz;

import com.ftninformatika.bisis.general.InvKnjigaMonografske;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class GrupniInventar extends Report {

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
    if (udc == null) {          
        return;
      }
    String sig = udc.getContent().trim();
    char udkDigit = getFirstDigit(sig);
    if (udkDigit == ' ') 	
      return;
    
    for (Primerak p : rec.getPrimerci()) {
    	Date datInv = p.getDatumRacuna();
    	if(datInv == null){
    		continue;
    	}
    	
    	String invBr = p.getInvBroj();
    	if (invBr == null || invBr.equals("")|| invBr.length()<4){ 		
    		continue;
    	}
    	
    	if (p.getCena() == null || p.getCena().equals("")) {   		
    		continue;    		
          }
    	Float cena=p.getCena().floatValue();
    	if(cena==null)
    		cena=0f;
    	String code = invBr.substring(0, 4);
        invBr = invBr.substring(4);
        String key = settings.getReportName() + getFilenameSuffix(datInv);
        if(getList(key).size()==0){
     	    i=new Item(code);
      	    getList(key).add(i);	
         }else{
     	    i = getItem(getList(key),code);
     	   if(i==null){
          	 i=new Item(code);
          }
         }
       
        i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, cena);
        if (i.ibmin.compareTo(invBr) > 0)
            i.ibmin = invBr;
          if (i.ibmax.compareTo(invBr) < 0)
            i.ibmax = invBr;
          String jez = code.substring(2);
          if (jez.equals("01") || jez.equals("04")) {
        	i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);  
                      
          } if (jez.equals("02") || jez.equals("05"))
            i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
        	  
          if (jez.equals("03"))
            i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
          if (sig.indexOf("-93") != -1 || sig.indexOf(".053.2") != -1)
            i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
          if (sig.startsWith("087.5"))
        	  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
          else {
            if (udkDigit == '8') {
              int pos = sig.indexOf('8');
              if (pos < sig.length() - 1) {
                String test = sig.substring(pos, pos+2);
                if (test.equals("81") || test.equals("80")) {
                  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0);	
                  
                }
              }
            } else {
              switch (udkDigit) {
                case '0':
                  i.add(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);	                  
                  break;
                case '1':
                	i.add(0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '2':
                	i.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '3':
                	i.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '4':
                	i.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '5':
                	i.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '6':
                	i.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '7':
                	i.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '8':
                	i.add(0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);
                  break;
                case '9':
                  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
                  break;
              }
            }
          }
        
    }
    
      
      
      
      
      
    
  }


  public Item getItem(List <Item>itemsL, String code) {
		for (Item it : itemsL){
	  	  if (it.code.equals(code)){			
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
  public class Item implements Comparable {
	    public String code;
	    public String ibmin;
	    public String ibmax;
	    public int udk0;
	    public int udk1;
	    public int udk2;
	    public int udk3;
	    public int udk4;
	    public int udk5;
	    public int udk6;
	    public int udk7;
	    public int udk8;
	    public int udk801;
	    public int udk9;
	    public int slik;
	    public int dec;
	    public int srpski;
	    public int madjarski;
	    public int strani;
	    public double value;
	    public boolean isTotal;
	    

	    public Item(String code) {
			super();
			this.code = code;
			this.ibmin = "99999999999";
			this.ibmax = "00000000000";
			this.udk0 = 0;
			this.udk1 = 0;
			this.udk2 = 0;
			this.udk3 = 0;
			this.udk4 = 0;
			this.udk5 = 0;
			this.udk6 = 0;
			this.udk7 = 0;
			this.udk8 = 0;
			this.udk801 = 0;
			this.udk9 = 0;
			this.slik = 0;
			this.dec = 0;
			this.srpski = 0;
			this.madjarski = 0;
			this.strani = 0;
			this.value = 0;
			this.isTotal = false;
			
		}

		public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return code.compareTo(b.code);
	      }
	      return 0;
	    }

	    public String toString() {
	      StringBuffer buf = new StringBuffer();
	      buf.append("\n  <item code=\"");
	      buf.append(code);
	      buf.append("\" name=\"");
	      buf.append(getName(code));
	      buf.append("\" isTotal=\"");
	      buf.append(isTotal ? "true" : "false");
	      buf.append("\">\n    <ibmin>");
	      buf.append(ibmin);
	      buf.append("</ibmin>\n    <ibmax>");
	      buf.append(ibmax);
	      buf.append("</ibmax>\n    <udk0>");
	      buf.append(udk0);
	      buf.append("</udk0>\n    <udk1>");
	      buf.append(udk1);
	      buf.append("</udk1>\n    <udk2>");
	      buf.append(udk2);
	      buf.append("</udk2>\n    <udk3>");
	      buf.append(udk3);
	      buf.append("</udk3>\n    <udk4>");
	      buf.append(udk4);
	      buf.append("</udk4>\n    <udk5>");
	      buf.append(udk5);
	      buf.append("</udk5>\n    <udk6>");
	      buf.append(udk6);
	      buf.append("</udk6>\n    <udk7>");
	      buf.append(udk7);
	      buf.append("</udk7>\n    <udk8>");
	      buf.append(udk8);
	      buf.append("</udk8>\n    <udk801>");
	      buf.append(udk801);
	      buf.append("</udk801>\n    <udk9>");
	      buf.append(udk9);
	      buf.append("</udk9>\n    <slik>");
	      buf.append(slik);
	      buf.append("</slik>\n    <ukup>");
	      buf.append(getTotal());
	      buf.append("</ukup>\n    <dec>");
	      buf.append(dec);
	      buf.append("</dec>\n    <srpski>");
	      buf.append(srpski);
	      buf.append("</srpski>\n    <madjarski>");
	      buf.append(madjarski);
	      buf.append("</madjarski>\n    <strani>");
	      buf.append(strani);
	      buf.append("</strani>\n    <vrednost>");
	      buf.append(value);
	      buf.append("</vrednost>\n  <problems>");
	      buf.append(" ");
	      buf.append("</problems>\n  </item>");
	      return buf.toString();
	    }
	    
	    
	    public int getTotal() {
	      return udk0 + udk1 + udk2 + udk3 + udk4 + udk5 + udk6 + udk7 + udk8 + udk801 + udk9;
	    }
	    
	    
	    public void add(int udk0, int udk1, int udk2, int udk3, int udk4, int udk5, int udk6,
	    		int udk7, int udk8, int udk801,int udk9, int slik,int dec, int srpski, 
	    		int madjarski, int strani, double value) {
	    	this.udk0 += udk0;
	    	this.udk1 += udk1;
	    	this.udk2 += udk2;
	    	this.udk3 += udk3;
	    	this.udk4 += udk4;
	    	this.udk5 += udk5;
	    	this.udk6 += udk6;
	    	this.udk7 += udk7;
	    	this.udk8 += udk8;
	    	this.udk801 += udk801;
	    	this.udk9 += udk9;
	    	this.slik += slik;
	    	this.dec += dec;
	    	this.srpski += srpski;
	    	this.madjarski += madjarski;
	    	this.strani += strani;
	    	this.value += value;
	    }
	    public void addItem(Item i) {
	        udk0 += i.udk0;
	        udk1 += i.udk1;
	        udk2 += i.udk2;
	        udk3 += i.udk3;
	        udk4 += i.udk4;
	        udk5 += i.udk5;
	        udk6 += i.udk6;
	        udk7 += i.udk7;
	        udk8 += i.udk8;
	        udk801 += i.udk801;
	        udk9 += i.udk9;
	        slik += i.slik;
	        dec += i.dec;
	        srpski += i.srpski;
	        madjarski += i.madjarski;
	        strani += i.strani;
	        value += i.value;
	      
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
	    while (!Character.isDigit(s.charAt(pos)) && pos < s.length())
	      pos++;
	    if (pos == s.length())
	      return ' ';
	    return s.charAt(pos);
	  }
  public String getName(String sigla) {
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
	    if (sigla.equals("01__"))
	      return "De\u010dje / ukupno";
	    if (sigla.equals("02__"))
	      return "Nau\u010dno / ukupno";
	    if (sigla.equals("03__"))
	      return "Zavi\u010dajno / ukupno";
	    if (sigla.equals("04__"))
	      return "Pozajmno / ukupno";
	    if (sigla.equals("____"))
	      return "UKUPNO";
	    return sigla;
	  }
  
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private static Log log = LogFactory.getLog(NabavkaPoNacinu.class);
  Item i;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  
  public void finishOnline( StringBuffer buff) {

  }

}
