package com.ftninformatika.bisis.bjpk;

import com.ftninformatika.bisis.records.Primerak;
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

public class InventarnaKnjigaOgranci extends Report {
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
	        String pov = povez;
	        try {
	            pov = getCoders().getBinCoders().get(povez).getDescription();
            } catch (Exception e ) {}
	      StringBuffer buf = new StringBuffer();
	      buf.append("\n  <item>\n    <rbr>");
	      buf.append(invbr);
	      buf.append("</rbr>\n    <datum>");
	      buf.append(datum==null ? "" :sdf.format(datum));
	      buf.append("</datum>\n    <opis>");
	      buf.append(opis==null? "" : StringUtils.adjustForHTML(opis));
	      buf.append("</opis>\n    <povez>");
	      buf.append(nvl(pov));
	      buf.append("</povez>\n    <dim>");
	      buf.append(dim==null ? "":StringUtils.adjustForHTML(dim));
	      buf.append("</dim>\n    <nabavka>");
	      buf.append(nabavka==null ? "":StringUtils.adjustForHTML(nabavka));
	      buf.append("</nabavka>\n    <cena>");
	      buf.append(cena==null ? "":StringUtils.adjustForHTML(cena));
	      buf.append("</cena>\n    <signatura>");
	      buf.append(sig==null ? "":StringUtils.adjustForHTML(sig));
	      buf.append("</signatura>\n    <napomena>");
	      buf.append(napomena==null ? "":StringUtils.adjustForHTML(napomena));
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
  @Override
  public void handleRecord(Record rec) {
    if (rec == null)
      return;
     if (rec.getPubType() != 1)
      return;

    String naslov = rec.getSubfieldContent("200a");
    if (naslov == null)
      naslov = "";
    String autor = getAutor(rec); 
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
    String RN = rec.getSubfieldContent("001e");
    if (RN == null)
      RN = "";
    
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
    if (opis.length() > 0)
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
    opis.append(god);
    opis.append(".");
       
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
    String sig = " ";

    for (Primerak p : rec.getPrimerci()) {

      if(p.getInvBroj()==null)
    	  continue;
      
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.ogr=i.invbr.substring(0,2);
      if (i.ogr.equals("00")||
    	  i.ogr.equals("01")|| 
    	  i.ogr.equals("02")|| 
    	  i.ogr.equals("03")||
    	  i.ogr.equals("14")){
    	  continue;
      }
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.povez = nvl(p.getPovez());
      i.dim = dim;
      String dobavljac=nvl(p.getDobavljac());
      String vrnab = nvl(p.getNacinNabavke());
      String nabavka = null;
      try {
        nabavka = getCoders().getAcqCoders().get(vrnab).getDescription();
      } catch (Exception e) {}
      if (dobavljac!="" && dobavljac!=" ")
            nabavka += ", " + dobavljac;
      String brRac=nvl(p.getBrojRacuna());
      if (brRac!="" && brRac!=" ")
              nabavka += ", " + brRac;
            
      i.nabavka = nabavka;
      i.cena = p.getCena() == null ? " " : "" + p.getCena();
      i.sig = sig;
      String status=p.getStatus();
      if((status!=null)&&(status.equalsIgnoreCase("9"))){
    	  i.napomena="rashodovano "+ nvl(p.getNapomene());
      }else{
    	  i.napomena=nvl(p.getNapomene());
      }
      String part=getSettings().getPart();
      String type=getSettings().getType();
      String key;
      if(part==null){
    	if (type.equals("online")){
    		key = getSettings().getReportName();
    	}else{
         key = getSettings().getReportName() +"("+i.ogr+")"+ getFilenameSuffix(p.getDatumInventarisanja());
    	}
      }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000
      	   //parametar part odredjuje koliko je primeraka u jednom fajlu
        String invBroj=p.getInvBroj().substring(2);
        int partBr=Integer.parseInt(part);
        int ceo=Integer.parseInt(invBroj)/partBr;
        int odBr=ceo*partBr;
        int doBr=ceo*partBr + partBr;
        key = getSettings().getReportName() +"("+i.ogr+")"+"-"+ ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
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
  private Pattern pattern;
  private static Log log = LogFactory.getLog(InventarnaKnjigaOgranci.class);
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
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
