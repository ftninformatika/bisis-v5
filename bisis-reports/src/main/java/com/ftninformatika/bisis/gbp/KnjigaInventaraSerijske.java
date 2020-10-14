package com.ftninformatika.bisis.gbp;

import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class KnjigaInventaraSerijske extends Report {
	public class Item implements Comparable {
	    public String invbr;
	    public Date datum;	    
	    public String opis;
	    public String god;
	    public String brSv;
	    public String dim;
	    public String povez;	   
	    public String nepovez;
	    public String nabavka;
	    public String cena;
	    public String signatura;
	    public String napomena;
	    
	    
	    public int compareTo(Object o) {
	      if (o instanceof Item) {
	        Item b = (Item)o;
	        if(b.god!=null && god!=null)
	          return god.compareTo(b.god);
	        
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
	      buf.append("</brSv>\n    <dim>");
	      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
	      buf.append("</dim>\n    <povez>");
	      buf.append(povez==null ? "" :povez);
	      buf.append("</povez>\n    <nabavka>");
	      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
	      buf.append("</nabavka>\n    <cena>");
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
	  log.info("Report finished2.");
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
   
    String god = rec.getSubfieldContent("207a");
    if (god == null)
    	god = " ";
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
     String sig = " ";

    for (Godina p : rec.getGodine()) {

      if(p.getInvBroj()==null)
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
      i.invbr =  ReportsUtils.nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      
      String temppovez = null ;
      try {
      	temppovez = getCoders().getBinCoders().get(p.getPovez()).getDescription();
	  } catch (Exception e ) {}

      if (temppovez!=null){
    	  int zagrada=temppovez.indexOf("(");
          if(zagrada !=-1){
        	  i.povez= LatCyrUtils.toCyrillic(temppovez.substring(0,zagrada)); 
          }
      }else{
      i.povez=temppovez;
      }
      i.nepovez ="";
      i.dim = dim;
      i.god=god;
      String dobavljac=ReportsUtils.nvl(p.getDobavljac());
      String vrnab = ReportsUtils.nvl(p.getNacinNabavke());
      
      String tempnabavka = null;
      try {
      	tempnabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
	  } catch (Exception e) {}

		String nabavka="";
      if (tempnabavka!=null){
        nabavka=LatCyrUtils.toCyrillic(tempnabavka);
      }
      if (dobavljac!="" && dobavljac!=" ")
        nabavka += ", " + dobavljac;
      String brRac=ReportsUtils.nvl(p.getBrojRacuna());
      if (brRac!="" && brRac!=" ")
        nabavka += ", " + brRac;

      
      i.cena = p.getCena() == null ? " " : "" + p.getCena();

      i.signatura = sig;
      i.nabavka=nabavka;
      brSv=String.valueOf(p.getSveske().size());
      i.brSv=brSv;
      i.napomena = ReportsUtils.nvl(p.getNapomene());
      String part=getReportSettings().getPart();
//			  settings.getParam("part");
      String type=getReportSettings().getType();
//			  settings.getParam("type");
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
          key = settings.getReportName() +"-"+odBr+"do"+doBr;
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
 

  

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private List<Item> items = new ArrayList<Item>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(KnjigaInventaraSerijske.class);
}
