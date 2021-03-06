package com.ftninformatika.bisis.tfzr;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.format.PubTypes;
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
	      buf.append(StringUtils.adjustForHTML(opis));
	      buf.append("</opis>\n   <god>");
	      buf.append(god == null ? "" : StringUtils.adjustForHTML(god));
	      buf.append("</god>\n    <brSv>");
	      buf.append(brSv == null ? "" : StringUtils.adjustForHTML(brSv));
	      buf.append("</brSv>\n    <dim>");
	      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
	      buf.append("</dim>\n    <povez>");
	      buf.append(povez);
	      buf.append("</povez>\n    <nabavka>");
	      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
	      buf.append("</nabavka>\n     <cena>");
	      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));  
	      buf.append("</cena>\n    <signatura>");
	      buf.append(signatura == null ? "" : StringUtils.adjustForHTML(signatura));  
	      buf.append("</signatura>\n    <napomena>");
	      buf.append(napomena == null ? "" : StringUtils.adjustForHTML(napomena));  
	      buf.append("</napomena>\n");
	      buf.append ("<sortinv>");
	      buf.append(invbr.substring(4));
	      buf.append("</sortinv>\n    </item>");
	      return buf.toString();
	    }
	  }

  @Override
  public void init() {
	  itemMap.clear();
	  pattern = Pattern.compile(getReportSettings().getInvnumpattern());
	  log.info("Report initialized.");
  }

  //@Override
  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
	  log.info("Finishing report...");
	    for (List<Item> list : itemMap.values())
	      Collections.sort(list);
	    
	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);
	      StringBuilder out = getWriter(key);
	      for (Item i : list){
	    	   out.append(i.toString());
	    	   
	      }
	      itemMap.get(key).clear();
	    }
	    
  }
  @Override
  public void finish() {
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
	    }
	    
	  //  closeFiles();
	    itemMap.clear();

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
    String drzava = PubTypes.getFormat().getSubfield("102a").getCoder().getValue(rec.getSubfieldContent("102a"));
    if (drzava == null)
    	drzava = "";
    
    
    StringBuffer opis = new StringBuffer();
    
    
      
    opis.append(naslov);
    
    if (podnaslov.length() > 0)
      opis.append(" : ");
    opis.append(podnaslov);
    opis.append("\n");
    opis.append(mesto);
    
    if (drzava.length() > 0)
      opis.append(", ");
    opis.append(drzava);
   
   
    
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
     String sig = " ";

    for (Godina p : rec.getGodine()) {

      if(p.getInvBroj()==null)
    	  continue;
      
      if(!p.getInvBroj().substring(2, 4).startsWith("02"))
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
      i.invbr = p.getInvBroj();
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      String povez="";
      if(getCoders().getBinCoders().get(p.getPovez()) != null)
      	povez = getCoders().getBinCoders().get(p.getPovez()).getDescription();
      if(povez!=null){
	   int zagrada=povez.indexOf("(");
	   if(zagrada !=-1){
	     i.povez= povez.substring(0,zagrada); 
	   }else{
	      i.povez=povez;
	   }
      }
      i.nepovez ="";
      i.dim = dim;
      String dobavljac=p.getDobavljac();
      String vrnab = p.getNacinNabavke();
      String nabavka="";
      if(getCoders().getAcqCoders().get(vrnab) != null)
      	nabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
      
      brSv=String.valueOf(p.getSveske().size());
      i.brSv=brSv;

		DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : 
        df2.format(p.getCena()).toString();
      i.signatura = sig;
      i.nabavka=nabavka;
      i.napomena = p.getNapomene();
      String godina= ReportsUtils.nvl(p.getGodina());
      String godiste=ReportsUtils.nvl(p.getGodiste());
      if (!godiste.equals(""))
      i.god =godiste;
      godina =godina.trim();
      if ((godina.length()>0)&& (!godina.equals(" "))&& (godiste.length()>0)&& (!godiste.equals(" "))){
    	  i.god+=", ";
      }
    	  i.god+=godina;
      
    	  String part="1000";//settings.getParam("part");
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
            key = settings.getReportName() +"-"+ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
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
  private Period period;
  private Pattern pattern;
  private List<Item> items = new ArrayList<>();
  private String name;
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(KnjigaInventaraSerijske.class);
//@Override
public void finishOnline( StringBuffer buff ) {

	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);   
	      Collections.sort(list);
	      for (Item i : list){
	    	  buff.append(i.toString());    	   
	      }
	    }
	    itemMap.clear();

}
}
