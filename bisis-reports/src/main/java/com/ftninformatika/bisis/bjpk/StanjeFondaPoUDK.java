package com.ftninformatika.bisis.bjpk;

import com.ftninformatika.bisis.gbzz.NabavkaPoUDK;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
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
	 pDecje3 = Pattern.compile(".*\\-93\\-.*");
    ip = new ItemProblems();  
    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
    branches.clear();
	log.info("Report initialized.");
  }


  @Override
  public void finish() {
    
	  log.info("Finishing report...");
	  List<Branch> branchList = new ArrayList();
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
	int language = LANG_OTHER;
	String sf101a = rec.getSubfieldContent("101a");
	if ("scc".equals(sf101a))
	  language = LANG_SER;
	if ("scr".equals(sf101a))
	  language = LANG_SER;
	if ("srp".equals(sf101a))
		  language = LANG_SER;
	if ("hun".equals(sf101a))
	  language = LANG_HUN;
	Subfield sf675b = rec.getSubfield("675b");
	Subfield sf675a = rec.getSubfield("675a");
	if (sf675b == null||sf675a == null) {
	  ip.add(1, 0, 0, 0, "");	
	    
	  return;
	}
	String sig = sf675b.getContent().trim();
	String siga = sf675b.getContent().trim();
	char c = getFirstDigit(sig);
	if (c == ' ') {
	 ip.add(1, 0, 0, 0, "");	
	 return;
	}
	if (sig.startsWith("087.5"))
	   c = 'x'; // slikovnica
	if (c == '8') {
	  Matcher m1 = p80_1.matcher(sig);
	  Matcher m2 = p80_2.matcher(sig);
	  Matcher m3 = p80_3.matcher(sig);
	  Matcher m4 = p80_4.matcher(sig);
	  if (m1.matches() || m2.matches() || m3.matches() || m4.matches())
	    c = 'a';
	  else 
	    c = 'b';
	}
	boolean isDecje = false;
	Matcher m1 = pDecje1.matcher(siga);
	Matcher m2 = pDecje2.matcher(siga);
	Matcher m3 = pDecje3.matcher(siga);
	if (m1.matches() || m2.matches() || m3.matches()){
		isDecje = true;
	}
	  
	int po = rec.getFields("600").size();
	po += rec.getFields("601").size();
	po += rec.getFields("602").size();
	po += rec.getFields("603").size();
	po += rec.getFields("604").size();
	po += rec.getFields("605").size();
	po += rec.getFields("606").size();
	po += rec.getFields("607").size();
	po += rec.getFields("608").size();
	po += rec.getFields("609").size();
	po += rec.getFields("610").size();
	    
	
    for (Primerak p : rec.getPrimerci()) {
    	Date datInv = p.getDatumInventarisanja();
    	String key = getReportSettings().getReportName() + getFilenameSuffix(datInv);
    	String invBr = p.getInvBroj();
    	if (invBr == null || invBr.equals("")|| invBr.length()<4){
    		ip.add(0, 0, 0, 1, "");
    		continue;
    	}
    	String odeljenje=p.getOdeljenje();
    	if (odeljenje == null || odeljenje.equals("")){
    		odeljenje=invBr.substring(0,2);
    	}
    	
     	
        Branch b = getBranch(getList (key),odeljenje, isDecje);
        b.addElemSl(0, 0, 0, po);
        switch (language) {
        case LANG_SER:
          switch (c) {          
            case '0':
              b.addUDKS(1, 0, 0, 0, 0, 0, 0, 0, 0, 0);              
              break;
            case '1':
              b.addUDKS(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);	
              break;
            case '2':
              b.addUDKS(0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
              break;
            case '3':
              b.addUDKS(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
              break;
            case '5':
              b.addUDKS(0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
              break;
            case '6':
              b.addUDKS(0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
              break;
            case '7':
              b.addUDKS(0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
              break;
            case 'a':
              b.addUDKS(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
              break;
            case 'b':
              b.addUDKS(0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
              break;
            case '9':
              b.addUDKS(0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
              break;
            case 'x':
              b.addElemSl(1, 0, 0, 0);
              break;
          }
          break;
        case LANG_HUN:
          switch (c) {
            case '0':
              b.addUDKM(1, 0, 0, 0, 0, 0, 0, 0, 0, 0);              
              break;
            case '1':
              b.addUDKM(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);	
              break;
            case '2':
              b.addUDKM(0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
              break;
            case '3':
              b.addUDKM(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
              break;
            case '5':
              b.addUDKM(0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
              break;
            case '6':
              b.addUDKM(0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
              break;
            case '7':
              b.addUDKM(0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
              break;
            case 'a':
              b.addUDKM(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
              break;
            case 'b':
              b.addUDKM(0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
              break;
            case '9':
              b.addUDKM(0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
              break;
            case 'x':
              b.addElemSl(0, 1, 0, 0);
              break;
          }
          break;
        case LANG_OTHER:
          switch (c) {
            case '0':
              b.addUDKO(1, 0, 0, 0, 0, 0, 0, 0, 0, 0);              
              break;
            case '1':
              b.addUDKO(0, 1, 0, 0, 0, 0, 0, 0, 0, 0);	
              break;
            case '2':
              b.addUDKO(0, 0, 1, 0, 0, 0, 0, 0, 0, 0);
              break;
            case '3':
              b.addUDKO(0, 0, 0, 1, 0, 0, 0, 0, 0, 0);
              break;
            case '5':
              b.addUDKO(0, 0, 0, 0, 1, 0, 0, 0, 0, 0);
              break;
            case '6':
              b.addUDKO(0, 0, 0, 0, 0, 1, 0, 0, 0, 0);
              break;
            case '7':
              b.addUDKO(0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
              break;
            case 'a':
              b.addUDKO(0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
              break;
            case 'b':
              b.addUDKO(0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
              break;
            case '9':
              b.addUDKM(0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
              break;
            case 'x':
              b.addElemSl(0, 0, 1, 0);
              break;
          }
          break;
      }
        
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
  private Branch getBranch(List <Branch> listBranch, String sigla, boolean isDecje) {
	  
	    try {
	      int test = Integer.parseInt(sigla);
	      if (test >= 4 && test <= 12)
	        sigla = sigla + (isDecje? "d" : "o");
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    
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
	    public int udk0m = 0;
	    public int udk0o = 0;
	    public int udk1s = 0;
	    public int udk1m = 0;
	    public int udk1o = 0;
	    public int udk2s = 0;
	    public int udk2m = 0;
	    public int udk2o = 0;
	    public int udk3s = 0;
	    public int udk3m = 0;
	    public int udk3o = 0;
	    public int udk5s = 0;
	    public int udk5m = 0;
	    public int udk5o = 0;
	    public int udk6s = 0;
	    public int udk6m = 0;
	    public int udk6o = 0;
	    public int udk7s = 0;
	    public int udk7m = 0;
	    public int udk7o = 0;
	    public int udk80s = 0;
	    public int udk80m = 0;
	    public int udk80o = 0;
	    public int udk82s = 0;
	    public int udk82m = 0;
	    public int udk82o = 0;
	    public int udk9s = 0;
	    public int udk9m = 0;
	    public int udk9o = 0;
	    public int sliks = 0;
	    public int slikm = 0;
	    public int sliko = 0;
	    public int po = 0; 
	    public boolean isTotal = false;

	    
        public void addUDKS(int udk0s, int udk1s, int udk2s, int udk3s, int udk5s, int udk6s, 
        		int udk7s, int udk80s, int udk82s, int udk9s){
        	  this.udk0s += udk0s;
        	  this.udk1s += udk1s;
        	  this.udk2s += udk2s;
        	  this.udk3s += udk3s;
        	  this.udk5s += udk5s;
        	  this.udk6s += udk6s;
        	  this.udk7s += udk7s;
        	  this.udk80s += udk80s;
        	  this.udk82s += udk82s;
        	  this.udk9s += udk9s;
        }
        public void addUDKM(int udk0m, int udk1m, int udk2m, int udk3m, int udk5m, int udk6m, 
        		int udk7m, int udk80m, int udk82m, int udk9m){	  
        	  
        	  this.udk0m += udk0m;
        	  this.udk1m += udk1m;
        	  this.udk2m += udk2m;
        	  this.udk3m += udk3m;
        	  this.udk5m += udk5m;
        	  this.udk6m += udk6m;
        	  this.udk7m += udk7m;
        	  this.udk80m += udk80m;
        	  this.udk82m += udk82m;
        	  this.udk9m += udk9m;
        }
        	  public void addUDKO(int udk0o, int udk1o, int udk2o, int udk3o, int udk5o, int udk6o, 
              		int udk7o, int udk80o, int udk82o, int udk9o){		  
        	  
        	  this.udk0o += udk0o;
        	  this.udk1o += udk1o;
        	  this.udk2o += udk2o;
        	  this.udk3o += udk3o;
        	  this.udk5o += udk5o;
        	  this.udk6o += udk6o;
        	  this.udk7o += udk7o;
        	  this.udk80o += udk80o;
        	  this.udk82o += udk82o;
        	  this.udk9o += udk9o;
        	  }
        	  public void addElemSl(int sliks, int slikm, int sliko, int po){	
        	  this.sliks += sliks;
        	  this.slikm += slikm;
        	  this.sliko += sliko;
        	  this.po += po;
        	
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
	        buf.append("</udk3s>\n    <udk5s>");
	        buf.append(udk5s);
	        buf.append("</udk5s>\n    <udk6s>");
	        buf.append(udk6s);
	        buf.append("</udk6s>\n    <udk7s>");
	        buf.append(udk7s);
	        buf.append("</udk7s>\n    <udk80s>");
	        buf.append(udk80s);
	        buf.append("</udk80s>\n    <udk82s>");
	        buf.append(udk82s);
	        buf.append("</udk82s>\n    <udk9s>");
	        buf.append(udk9s);
	        buf.append("</udk9s>\n    <sliks>");
	        buf.append(sliks);
	        buf.append("</sliks>\n    <totals>");
	        buf.append(getTotalS());
	        buf.append("</totals>\n    <udk0m>");
	        buf.append(udk0m);
	        buf.append("</udk0m>\n    <udk1m>");
	        buf.append(udk1m);
	        buf.append("</udk1m>\n    <udk2m>");
	        buf.append(udk2m);
	        buf.append("</udk2m>\n    <udk3m>");
	        buf.append(udk3m);
	        buf.append("</udk3m>\n    <udk5m>");
	        buf.append(udk5m);
	        buf.append("</udk5m>\n    <udk6m>");
	        buf.append(udk6m);
	        buf.append("</udk6m>\n    <udk7m>");
	        buf.append(udk7m);
	        buf.append("</udk7m>\n    <udk80m>");
	        buf.append(udk80m);
	        buf.append("</udk80m>\n    <udk82m>");
	        buf.append(udk82m);
	        buf.append("</udk82m>\n    <udk9m>");
	        buf.append(udk9m);
	        buf.append("</udk9m>\n    <slikm>");
	        buf.append(slikm);
	        buf.append("</slikm>\n    <totalm>");
	        buf.append(getTotalM());
	        buf.append("</totalm>\n    <udk0o>");
	        buf.append(udk0o);
	        buf.append("</udk0o>\n    <udk1o>");
	        buf.append(udk1o);
	        buf.append("</udk1o>\n    <udk2o>");
	        buf.append(udk2o);
	        buf.append("</udk2o>\n    <udk3o>");
	        buf.append(udk3o);
	        buf.append("</udk3o>\n    <udk5o>");
	        buf.append(udk5o);
	        buf.append("</udk5o>\n    <udk6o>");
	        buf.append(udk6o);
	        buf.append("</udk6o>\n    <udk7o>");
	        buf.append(udk7o);
	        buf.append("</udk7o>\n    <udk80o>");
	        buf.append(udk80o);
	        buf.append("</udk80o>\n    <udk82o>");
	        buf.append(udk82o);
	        buf.append("</udk82o>\n    <udk9o>");
	        buf.append(udk9o);
	        buf.append("</udk9o>\n    <sliko>");
	        buf.append(sliko);
	        buf.append("</sliko>\n    <totalo>");
	        buf.append(getTotalO());
	        buf.append("</totalo>\n    <totalu>");
	        buf.append(getTotal());
	        buf.append("</totalu>\n    <po>");
	        buf.append(po);
	        buf.append("</po>\n  </item>");
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
		      udk80s += b.udk80s;
		      udk82s += b.udk82s;
		      udk9s += b.udk9s;
		      udk0m += b.udk0m;
		      udk1m += b.udk1m;
		      udk2m += b.udk2m;
		      udk3m += b.udk3m;
		      udk5m += b.udk5m;
		      udk6m += b.udk6m;
		      udk7m += b.udk7m;
		      udk80m += b.udk80m;
		      udk82m += b.udk82m;
		      udk9m += b.udk9m;
		      udk0o += b.udk0o;
		      udk1o += b.udk1o;
		      udk2o += b.udk2o;
		      udk3o += b.udk3o;
		      udk5o += b.udk5o;
		      udk6o += b.udk6o;
		      udk7o += b.udk7o;
		      udk80o += b.udk80o;
		      udk82o += b.udk82o;
		      udk9o += b.udk9o;
		      sliks += b.sliks;
		      slikm += b.slikm;
		      sliko += b.sliko;
		      po += b.po;
		    }
		public int getTotalS() {
		      return udk0s + udk1s + udk2s + udk3s + udk5s + udk6s + udk7s + udk80s + udk82s + udk9s + sliks;
		    }
		    
		    public int getTotalM() {
		      return udk0m + udk1m + udk2m + udk3m + udk5m + udk6m + udk7m + udk80m + udk82m + udk9m + slikm;
		    }
		    
		    public int getTotalO() {
		      return udk0o + udk1o + udk2o + udk3o + udk5o + udk6o + udk7o + udk80o + udk82o + udk9o + sliko;
		    }
		    
		    public int getTotal() {
		      return getTotalS() + getTotalM() + getTotalO();
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
	    if (sigla.equals("01"))
	      return "De\u010dje odeljenje";
	    if (sigla.equals("02"))
	      return "Odeljenje za odrasle";
	    if (sigla.equals("03"))
	      return "Stru\u010dne knjige";
	    if (sigla.equals("04d"))
	      return "Banatska Topola (D)";
	    if (sigla.equals("04o"))
	      return "Banatska Topola (O)";
	    if (sigla.equals("05d"))
	      return "Ban. Veliko Selo (D)";
	    if (sigla.equals("05o"))
	      return "Ban. Veliko Selo (O)";
	    if (sigla.equals("06d"))
	      return "Ba\u0161aid (D)";
	    if (sigla.equals("06o"))
	      return "Ba\u0161aid (O)";
	    if (sigla.equals("07d"))
	      return "I\u0111o\u0161 (D)";
	    if (sigla.equals("07o"))
	      return "I\u0111o\u0161 (O)";
	    if (sigla.equals("08d"))
	      return "Mokrin (D)";
	    if (sigla.equals("08o"))
	      return "Mokrin (O)";
	    if (sigla.equals("09d"))
	      return "Nakovo (D)";
	    if (sigla.equals("09o"))
	      return "Nakovo (O)";
	    if (sigla.equals("10d"))
	      return "Novi Kozarci (D)";
	    if (sigla.equals("10o"))
	      return "Novi Kozarci (O)";
	    if (sigla.equals("11d"))
	      return "Rusko Selo (D)";
	    if (sigla.equals("11o"))
	      return "Rusko Selo (O)";
	    if (sigla.equals("12d"))
	      return "Sajan (D)";
	    if (sigla.equals("12o"))
	      return "Sajan (O)";
	    if (sigla.equals("13"))
	      return "Zavi\u010dajno";
	    if (sigla.equals("14"))
	      return "Legat";
	    if (sigla.equals("xx"))
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
