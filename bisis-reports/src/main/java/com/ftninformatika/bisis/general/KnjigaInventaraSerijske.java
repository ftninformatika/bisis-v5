package com.ftninformatika.bisis.general;

import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Period;
import com.ftninformatika.bisis.reports.Report;
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


public class KnjigaInventaraSerijske extends Report {
	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;	    
	    public String opis;
	    public String god;
	    public String brSv;
	    public String nabavka;
	    public String racun;
	    public Date datumRacuna;
	    public int rn;
	    public String cena;
	    public String signatura;
	    public String napomena;
	    
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        if(b.invbr!=null && invbr!=null)
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
	      buf.append(opis==null ? "": StringUtils.adjustForHTML(opis));
	      buf.append("</opis>\n   <god>");
	      buf.append(god == null ? "" : StringUtils.adjustForHTML(god));
	      buf.append("</god>\n    <brSv>");
	      buf.append(brSv == null ? "" : StringUtils.adjustForHTML(brSv));
	      buf.append("</brSv>\n    ");
	      buf.append("<nabavka>");
	      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
          buf.append("</nabavka>\n     ");
          buf.append("<RN>");
          buf.append(rn);
          buf.append("</RN>\n    ");
          buf.append("<racun>");
          buf.append(racun == null ? "" : StringUtils.adjustForHTML(racun));
          buf.append("</racun>\n    ");
          buf.append("<datumRacuna>");
          buf.append(datumRacuna == null ? "" : sdf.format(datumRacuna));
          buf.append("</datumRacuna>\n    <cena>");
	      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));  
	      buf.append("</cena>\n    <signatura>");
	      buf.append(signatura == null ? "" : StringUtils.adjustForHTML(signatura));  
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
                gr.setPeriod(key.substring(key.indexOf("-")+1));
            }
            else{
                gr.setReportName(key);
                gr.setPeriod(LatCyrUtils.toCyrillic("ceo fond"));

            }
            gr.setFullReportName(key);
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
  
       String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    
    String podnaslov = rec.getSubfieldContent("200e");
    if (podnaslov == null)
    	podnaslov = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    
    String drzava = rec.getSubfieldContent("102a");
    if (drzava == null)
      drzava = "";

    StringBuffer opis = new StringBuffer();
    opis.append(naslov);
    
    if (podnaslov.length() > 0)
      opis.append(", ");
    opis.append(podnaslov);
    
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    
    if (drzava.length() > 0)
      opis.append(", ");
    opis.append(drzava);


     String sig = " ";

    for (Godina p : rec.getGodine()) {

      if(p.getInvBroj()==null)
    	  continue;
        Matcher matcher = pattern.matcher(p.getInvBroj());
        if (!matcher.matches())
            continue;
      String brSv = Integer.toString(p.getSveskeCount());
      if (brSv == null)
      	brSv = " ";
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.brSv = brSv;

      String god = "";
      if (p.getGodiste() != null){
          god = god + p.getGodiste();
      }
      if (p.getGodina() != null){
          god = god + ", " + p.getGodina();
      }
      if (p.getBroj() != null){
          god = god +", "+p.getBroj();
      }
      i.god = god;

      String vrnab = nvl(p.getNacinNabavke());
      i.nabavka = " ";
      if(getCoders().getAcqCoders().get(vrnab) != null){
            i.nabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
      }
      i.racun = nvl(p.getBrojRacuna());
      i.datumRacuna = p.getDatumRacuna();
      if (rec.getRN() != 0){
          i.rn = rec.getRN();
      }else if (rec.getRecordID() != 0){
          i.rn = rec.getRecordID();
      }else{
          i.rn = 0;
      }

      DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : df2.format(p.getCena()).toString();
      i.signatura = sig;
      i.napomena = nvl(p.getNapomene());

      String part=getReportSettings().getPart();
      String key;
      if(part == null){
          key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
      }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000 parametar part odredjuje koliko je primeraka u jednom fajlu
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
      String sfa = rec.getSubfieldContent("700a");
      String sfb = rec.getSubfieldContent("700b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("701") != null) {
      String sfa = rec.getSubfieldContent("701a");
      String sfb = rec.getSubfieldContent("701b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    } else if (rec.getField("702") != null) {
      String sfa = rec.getSubfieldContent("702a");
      String sfb = rec.getSubfieldContent("702b");
      if (sfa != null) {
        if (sfb != null)
          return toSentenceCase(sfa) + ", " + toSentenceCase(sfb);
        else
          return toSentenceCase(sfa);
      } else {
        if (sfb != null)
          return toSentenceCase(sfb);
        else
          return "";
      }
    }
    return "";
  }

  public String trimZeros(String s) {
    if (s == null)
      return null;
    String retVal = s;
    while (retVal.length() > 0 && retVal.charAt(0) == '0')
      retVal = retVal.substring(1);
    return retVal;
  }
  
  public String toSentenceCase(String s) {
    StringBuffer retVal = new StringBuffer();
    StringTokenizer st = new StringTokenizer(s, " -.", true);
    while (st.hasMoreTokens()) {
      String word = st.nextToken();
      if (word.length() > 0)
        retVal.append(Character.toUpperCase(word.charAt(0)) + 
            word.substring(1).toLowerCase());
        
    }
    return retVal.toString();
  }
  
  public String nvl(String s) {
    return s == null ? " " : s;
  }

  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Period period;
  private Pattern pattern;
  private List<Item> items = new ArrayList<>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(KnjigaInventaraSerijske.class);

}
