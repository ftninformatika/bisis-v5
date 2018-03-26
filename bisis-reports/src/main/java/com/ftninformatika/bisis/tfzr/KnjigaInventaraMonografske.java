package com.ftninformatika.bisis.tfzr;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.bisis.reports.ReportsUtils;
import com.ftninformatika.utils.string.LatCyrUtils;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.log4j.Logger;


public class KnjigaInventaraMonografske extends Report {

 	 public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
		    for (List<Item> list : itemMap.values())
		      Collections.sort(list);
		    
		    for (String key : itemMap.keySet()) {
		      List<Item> list = itemMap.get(key);
		      StringBuilder out = getWriter(key);
		      for (Item i : list){
		    	   out.append(i.toString());
		    	   
		      }
		      //out.flush();
		      itemMap.get(key).clear();
		    }

	  }
 @Override
 public void init() {
	  itemMap.clear();
	    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
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
	      //out.close();
	    }	   
	    itemMap.clear();
 }
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
   
    if (rec.getPubType()!=1)
      return;
    Date date=rec.getCreationDate();
    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = ReportsUtils.getAutor(rec);
    if (autor == null)
      autor = "";
    autor.trim();
    String izdavac = rec.getSubfieldContent("210c");
    if (izdavac == null)
      izdavac = "";
    String mesto = rec.getSubfieldContent("210a");
    if (mesto == null)
      mesto = "";
    String god = rec.getSubfieldContent("210d");
    if (god == null)
      god = "";
    String brsveske = rec.getSubfieldContent("200v");
    if (brsveske == null)
      brsveske = "";
    String godIzd=rec.getSubfieldContent("100c");
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
     if (!(opis.equals(" "))&&(opis.length() > 0))
      opis.append(". ");
    opis.append(naslov);
    opis.append(", ");
    opis.append(brsveske);
    if (brsveske.length() > 0)
      opis.append(", ");
    opis.append(mesto);
    if (mesto.length() > 0)
      opis.append(", ");
    opis.append(izdavac);
    if (izdavac.length() > 0)
      opis.append(", ");
   
    if (god.length() > 0){
    	 opis.append(god);
         opis.append(".");
    }
    else{
    	opis.append(godIzd);
        opis.append(".");
    }
      
   
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
        if (sig.equals(""))
      	  sig=" ";
        Item i = new Item();
        i.invbr = p.getInvBroj();
        i.datum = p.getDatumInventarisanja();
        i.opis = opis.toString();
        String povez="";
        if (getCoders().getBinCoders().get(p.getPovez()) != null)
            povez = getCoders().getBinCoders().get(p.getPovez()).getDescription();

        if(povez!=null){
        int zagrada=povez.indexOf("(");
        if(zagrada !=-1){
      	  i.povez= povez.substring(0,zagrada); 
        }else{
        i.povez=povez;
        }
        }else{
        	i.povez="";
        }
        i.dim = dim;
        String dobavljac=p.getDobavljac();
        String vrnab = p.getNacinNabavke();
        i.nabavka = "";
        if (getCoders().getAcqCoders().get(vrnab) != null)
            i.nabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
        DecimalFormat df2 = new DecimalFormat(".##");
        i.cena = p.getCena() == null ? " " : 
          df2.format(p.getCena()).toString();
        i.sig = sig;
        i.napomena = p.getNapomene();
        String part="1000";//settings.getParam("part"); TODO-hardcoded
        String key;
        if(part==null){
          key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
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
	        return invbr.substring(4).compareTo(b.invbr.substring(4));
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
		      buf.append("</napomena>\n");
		      buf.append ("<sortinv>");
		      buf.append(invbr.substring(4));
		      buf.append("</sortinv>\n    </item>");
		      return buf.toString();
		    }
}
 

  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private Map<String, List<Item>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(KnjigaInventaraMonografske.class);

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
