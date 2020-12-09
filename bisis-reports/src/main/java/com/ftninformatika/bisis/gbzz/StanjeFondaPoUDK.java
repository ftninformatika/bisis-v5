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


public class StanjeFondaPoUDK extends Report {

  @Override
  public void init() {
	 p80_1 = Pattern.compile(".*80");
	 p80_2 = Pattern.compile(".*81");
	 p80_3 = Pattern.compile(".*82.*\\(091\\)");
	 p80_4 = Pattern.compile(".*82.*\\.0");
	 pDecje1 = Pattern.compile(".*\\(*\\.053\\.2\\).*");
	 pDecje2 = Pattern.compile(".*\\(*\\.053\\.6\\).*");
	 pDecje3 = Pattern.compile(".*-93-");
     ip = new ItemProblems();
     pattern = Pattern.compile(getReportSettings().getInvnumpattern());
     branches.clear();
     log.info("Report initialized.");
  }

  @Override
  public void finish() {
    
	  log.info("Finishing report...");
	  ArrayList branchList = new ArrayList();
	  for (String key : branches.keySet()) {
		  List<Branch> list = branches.get(key);
		  StringBuilder out = getWriter(key);
		  for (Branch i : list){
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
	   
	    branches.clear();
	    log.info("Report finished."); 
  }

  @Override
  public void handleRecord(Record rec) {

	Subfield sf675a = rec.getSubfield("675a");
	if (sf675a == null) {
	  ip.add(1, 0, 0, 0, "");	
	    
	  return;
	}
	String sig = sf675a.getContent().trim();
    char udkDigit = getFirstDigit(sig);
    if (udkDigit == ' ') {
    	ip.add(1, 0, 0, 0, "Neispravan 675a (UDK broj) u zapisu: " + rec.getRecordID() + "\n");
        
      return;
    }
    if (sig.startsWith("087.5"))
    	udkDigit = 'x'; // slikovnica
	   
	
    for (Primerak p : rec.getPrimerci()) {
    	Date datInv = p.getDatumRacuna();
    	String key =  settings.getReportName() + getFilenameSuffix(datInv);
    	String invBr = p.getInvBroj();
    	if (invBr == null || invBr.equals("")|| invBr.length()<4){
    		ip.add(0, 0, 0, 1, "");
    		continue;
    	}
    	String odeljenje=p.getOdeljenje();
    	if (odeljenje == null || odeljenje.equals("")){
    		ip.add(0, 1, 0, 0, "");
    		continue;
    	}
    	
    	String sigla = invBr.substring(0, 4);
        
        if (sigla.equals("0104"))
            sigla = "0101";
          if (sigla.equals("0105"))
            sigla = "0102";
        Branch b = getBranch(getList (key),sigla);

          switch (udkDigit) {          
            case '0':
              b.addUDKS(1, 0, 0, 0, 0, 0, 0, 0, 0, 0,0);              
              break;
            case '1':
              b.addUDKS(0, 1, 0, 0, 0, 0, 0, 0, 0, 0,0);	
              break;
            case '2':
              b.addUDKS(0, 0, 1, 0, 0, 0, 0, 0, 0, 0,0);
              break;
            case '3':
              b.addUDKS(0, 0, 0, 1, 0, 0, 0, 0, 0, 0,0);
              break;
            case '4':
                b.addUDKS(0, 0, 0, 0, 1, 0, 0, 0, 0, 0,0);
                break;
            case '5':
              b.addUDKS(0, 0, 0, 0, 0, 1, 0, 0, 0, 0,0);
              break;
            case '6':
              b.addUDKS(0, 0, 0, 0, 0, 0,1, 0, 0, 0, 0);
              break;
            case '7':
              b.addUDKS(0, 0, 0, 0, 0, 0,0, 1, 0, 0, 0);
              break;
            case '8':
              b.addUDKS(0, 0, 0, 0, 0, 0, 0,0, 1, 0, 0);
              break;
           
            case '9':
              b.addUDKS(0, 0, 0, 0, 0, 0, 0, 0, 0,1, 0);
              break;
            case 'x':
            	b.addUDKS(0, 0, 0, 0, 0, 0, 0, 0, 0,0, 1);
              break;
          }
          break;
     
         
        
    }
     
  }
  
  
  
  public String nvl(String s) {
    return s == null ? "" : s;
  }

  public List<Branch> getList(String key) {
	    List<Branch> list = branches.get(key);
	    if (list == null) {
	      list = new ArrayList<Branch>();
	      branches.put(key, list);
	    }
	    return list;
	  }
  private Branch getBranch(List <Branch> listBranch, String sigla) {

	    
	     for (Branch b:listBranch){
	      if(b.sigla.equals(sigla)){
	    	  return b;
	      }
	     }
	     Branch br=new Branch(sigla);
	     listBranch.add(br);
	    	return br;
	  }
  public class Branch implements Comparable {
	  public Branch() {
	    }
	    
	  public Branch(String sigla) {
	      this.sigla = sigla;
	    }
	    public String sigla = "";
	    public int udk0s = 0;
	    public int udk1s = 0;
	    public int udk2s = 0;
	    public int udk3s = 0;
	    public int udk4s = 0;
	    public int udk5s = 0;
	    public int udk6s = 0;
	    public int udk7s = 0;
	    public int udk8s = 0;
	    public int udk9s = 0;
	    public int udkX = 0;

	    public boolean isTotal = false;

	    
        public void addUDKS(int udk0s, int udk1s, int udk2s, int udk3s, int udk4s,int udk5s, int udk6s, 
        		int udk7s, int udk80s, int udk9s, int udkX){
        	  this.udk0s += udk0s;
        	  this.udk1s += udk1s;
        	  this.udk2s += udk2s;
        	  this.udk3s += udk3s;
        	  this.udk4s += udk4s;
        	  this.udk5s += udk5s;
        	  this.udk6s += udk6s;
        	  this.udk7s += udk7s;
        	  this.udk8s += udk80s;
        	  this.udk9s += udk9s;
        	  this.udkX += udkX;
        }
       
	    public int compareTo(Object o) {
	        if (o instanceof Branch) {
	          Branch b = (Branch)o;
	          return sigla.compareTo(b.sigla);
	        }
	        return 0;
	      }

	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        buf.append("\n  <item id=\"");
	        buf.append(sigla);
	        buf.append("\" name=\"");
	        buf.append(getName(sigla));
	        buf.append("\" isTotal=\"");
	        buf.append(isTotal);
	        buf.append("\">\n    <udk0s>");
	        buf.append(udk0s);
	        buf.append("</udk0s>\n    <udk1s>");
	        buf.append(udk1s);
	        buf.append("</udk1s>\n    <udk2s>");
	        buf.append(udk2s);
	        buf.append("</udk2s>\n    <udk3s>");
	        buf.append(udk3s);
	        buf.append("</udk3s>\n    <udk4s>");
	        buf.append(udk4s);
	        buf.append("</udk4s>\n    <udk5s>");
	        buf.append(udk5s);
	        buf.append("</udk5s>\n    <udk6s>");
	        buf.append(udk6s);
	        buf.append("</udk6s>\n    <udk7s>");
	        buf.append(udk7s);
	        buf.append("</udk7s>\n    <udk8s>");
	        buf.append(udk8s);
	        buf.append("</udk8s>\n     <udk9s>");
	        buf.append(udk9s);
	        buf.append("</udk9s>\n    <sliks>");
	        buf.append(udkX);
	        buf.append("</sliks>\n    <totals>");
	        buf.append(getTotal());
	        buf.append("</totals>\n      </item>");
	        return buf.toString();
	      }
	    	    
	    
		public void add(Branch b) {
		      udk0s += b.udk0s;
		      udk1s += b.udk1s;
		      udk2s += b.udk2s;
		      udk3s += b.udk3s;
		      udk5s += b.udk5s;
		      udk6s += b.udk6s;
		      udk7s += b.udk7s;
		      udk9s += b.udk9s;
		      
		    }
		public int getTotal() {
		      return udk0s + udk1s + udk2s + udk3s + udk4s + udk5s + udk6s + udk7s + udk8s +  udk9s + udkX;
		    }
		    
		   
		    
		   
	  }
  public class ItemProblems{
	  int noUDC;	 
	  int noBranch;
	  int noInvDat;
	  int noInvNum;
	  
	  
	  String problems;
	  public ItemProblems() {
			super();
			this.noUDC = 0;			
			this.noBranch = 0;
			this.noInvDat = 0;
			this.noInvNum = 0;
			
		}
	  
	  
	  public void add(int noUDC, int noBranch, int noInvDat, int noInvNum, String problems) {
			this.noUDC += noUDC;
	        this.noBranch += noBranch;
	        this.noInvDat += noInvDat;
	        this.noInvNum += noInvNum;
	        this.problems += problems;
	        
	      }
	    public String toString() {
	        StringBuffer buf = new StringBuffer();
	        
	        buf.append("\n  <item>\n    <noUDC>");
	        buf.append(noUDC);
	        buf.append("</noUDC>\n    <noBranch>");
	        buf.append(noBranch);
	        buf.append("</noBranch>\n   <noInvDat>");
	        buf.append(noInvDat);
	        buf.append("</noInvDat>\n   <noInvNum>");
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
  public void addTotals(List branches) {
	    Branch total = new Branch();
	    total.sigla = "xx";
	    total.isTotal = true;
	    Iterator it = branches.iterator();
	    while (it.hasNext()) {
	      Branch b = (Branch)it.next();
	      total.add(b);
	    }
	    branches.add(total);
	  }
  public void createBranches(){
	    p80_1 = Pattern.compile(".*80");
	    p80_2 = Pattern.compile(".*81");
	    p80_3 = Pattern.compile(".*82.*\\(091\\)");
	    p80_4 = Pattern.compile(".*82.*\\.0");
	    pDecje1 = Pattern.compile(".*\\(.*\\.053\\.2\\).*");
	    pDecje2 = Pattern.compile(".*\\(.*\\.053\\.6\\).*");
	    pDecje3 = Pattern.compile(".*-93");

	  
  }
  
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  ItemProblems ip;
  HashMap<String,List <Branch>> branches = new HashMap<String, List<Branch>>();
  private Pattern p80_1;
  private Pattern p80_2;
  private Pattern p80_3;
  private Pattern p80_4;
  private Pattern pDecje1;
  private Pattern pDecje2;
  private Pattern pDecje3;
  private static Log log = LogFactory.getLog(StanjeFondaPoUDK.class);
  NumberFormat nf;
  private static final int LANG_SER = 1;
  private static final int LANG_HUN = 2;
  private static final int LANG_OTHER = 3;

}
