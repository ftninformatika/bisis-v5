package com.ftninformatika.bisis.general;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportType;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.log4j.Logger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvKnjigaMonografske extends Report {
	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;
	    public String opis;
	    public String povez;
	    public String dim;
	    public String nabavka;
	    public String cena;
	    public String sig;
	    public String napomena;
	    public String ogr;
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        return invbr.compareTo(b.invbr);
	      }
	      return 0;
	    }

	    public String toString() {
		      StringBuffer buf = new StringBuffer();
		      buf.append("\n  <item>\n    <rbr>");
		      buf.append(invbr);
		      buf.append("</rbr>\n    <datum>");
		      buf.append(datum == null ? "" : sdf.format(datum));
		      buf.append("</datum>\n    <opis>");
		      buf.append(opis == null ? "" : StringUtils.adjustForHTML(opis));
		      buf.append("</opis>\n    <povez>");
		      buf.append(povez);
		      buf.append("</povez>\n    <dim>");
		      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
		      buf.append("</dim>\n    <nabavka>");
		      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
		      buf.append("</nabavka>\n    <cena>");
		      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));
		      buf.append("</cena>\n    <signatura>");
		      buf.append(sig == null ? "" : StringUtils.adjustForHTML(sig));
		      buf.append("</signatura>\n    <napomena>");
		      buf.append(napomena == null ? "" : StringUtils.adjustForHTML(napomena));
		      buf.append("</napomena>\n  </item>");
		      return buf.toString();
		    }

		 }
  
	@Override
	public void init() {
		itemMap.clear();
	    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
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



	  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
		  log.info("Finishing  report...");
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
                getReportRepository().save(gr);
		      itemMap.get(key).clear();
		    }
		   
		    log.info("Report finished.");
	  }
  
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
    
    String sf700a = nvl(rec.getSubfieldContent("700a"));
    String sf700b = nvl(rec.getSubfieldContent("700b"));
    String sf200a = nvl(rec.getSubfieldContent("200a"));
    String sf200h = nvl(rec.getSubfieldContent("200h"));
    String sf210a = nvl(rec.getSubfieldContent("210a"));
    String sf210c = nvl(rec.getSubfieldContent("210c"));
    String sf210d = nvl(rec.getSubfieldContent("210d"));
    String sf001e = nvl(rec.getSubfieldContent("001e"));
    
    StringBuffer buff = new StringBuffer();
    if (sf700a.length() > 0) {
      buff.append(sf700a);
      buff.append(", ");
      buff.append(sf700b);
      buff.append('\n');
    }
    buff.append("      ");
    buff.append(sf200a);
    if (sf200h.length() > 0) {
      buff.append(" : ");
      buff.append(sf200h);
    }
    buff.append(". - ");
    buff.append(sf210a);
    buff.append(" : ");
    buff.append(sf210c);
    buff.append(", ");
    buff.append(sf210d);
    buff.append('\n');
    buff.append("RN: ");
    buff.append(sf001e);
    String opis = buff.toString();
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";

    String sig = " ";
    for (Primerak p : rec.getPrimerci()) {
        if(p.getInvBroj()==null)
      	  continue;
        Matcher matcher = pattern.matcher(p.getInvBroj());
        if (!matcher.matches())
          continue;
    	 sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
    	          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
    	          p.getSigUDK());
    	 if (sig==null ||sig.equals(""))
       	  sig=" ";
        if (p.getDatumInventarisanja() == null && settings.getType().equals(ReportType.YEAR.toString()))
            continue;

      
      Item i = new Item();
      i.invbr = p.getInvBroj();
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      String pov = p.getPovez();
      if (pov == null || pov.equals(""))
        pov = " ";
      else
        pov = LatCyrUtils.toCyrillic(pov);
      i.povez=pov;
      i.dim = dim;
      String dobavljac=p.getDobavljac();
      if (dobavljac == null)
        dobavljac = "";
      String vrnab = p.getNacinNabavke();
      if (vrnab == null || getCoders().getAcqCoders().get(vrnab) == null) {
          i.nabavka = "";
      } else {
          i.nabavka = LatCyrUtils.toCyrillic(getCoders().getAcqCoders().get(vrnab).getDescription().toLowerCase());
      }
        DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : df2.format(p.getCena()).toString();
      i.sig = sig;
      if (p.getNapomene() == null){
    	  i.napomena = " ";
      }else{
    	  i.napomena = p.getNapomene();  
      }
      
      String part=settings.getPart();
      String type=settings.getType();

      String key;
      if(part==null){
    	if (type.equals("online")){
    		key = settings.getReportName();
    	}else{
         key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
    	}
      }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000
      	   //parametar part odredjuje koliko je primeraka u jednom fajlu
        String invBroj=p.getInvBroj().substring(2);
        int partBr=Integer.parseInt(part);
        int ceo=Integer.parseInt(invBroj)/partBr;
        int odBr=ceo*partBr;
        int doBr=ceo*partBr + partBr;
        key = settings.getReportName() +"-"+ ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
      }
      getList(key).add(i);
      
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
  
  public String getAutor(Record rec) {
    if (rec.getField("700") != null) {
      String sfa = nvl(rec.getSubfieldContent("700a")).trim();
      String sfb = nvl(rec.getSubfieldContent("700b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = nvl(rec.getSubfieldContent("701a")).trim();
      String sfb = nvl(rec.getSubfieldContent("701b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = nvl(rec.getSubfieldContent("702a")).trim();
      String sfb = nvl(rec.getSubfieldContent("702b")).trim();
      if (sfa.length() > 0) {
        if (sfb.length() > 0)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb.length() > 0)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
  }

  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0))
            + word.substring(1).toLowerCase());

    }
    return retVal.toString();
  }

  public String nvl(String s) {
    return s == null ? "" : s.trim();
  }
  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Logger log = Logger.getLogger(InvKnjigaMonografske.class);
}
