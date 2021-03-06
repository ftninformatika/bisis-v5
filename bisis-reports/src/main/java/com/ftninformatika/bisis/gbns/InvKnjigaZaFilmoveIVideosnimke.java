package com.ftninformatika.bisis.gbns;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Period;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.Signature;
import com.ftninformatika.utils.string.StringUtils;
import org.apache.log4j.Logger;


public class InvKnjigaZaFilmoveIVideosnimke extends Report {
	public class Item implements Comparable {
		public String invbr;
	    public Date datum;
	    public String opis;
	    public String vrDok;
	    public String brDok;
	    public String propratnaGr;
	    public String boja;
	    public String zvuk;
	    public String sirina;
	    public String dim;
	    public String nabavkaO;
	    public String nabavkaK;
	    public String nabavkaR;
	    public String nabavkaP;
	    public String cena;
	    public String sig;
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
		      buf.append(sdf.format(datum));
		      buf.append("</datum>\n    <opis>");
		      buf.append(StringUtils.adjustForHTML(opis));
		      buf.append("</opis>\n    <vrDok>");
		      buf.append(vrDok);
		      buf.append("</vrDok>\n    <brDok>");
		      buf.append(brDok);
		      buf.append("</brDok>\n    <propratnaGr>");
		      buf.append(StringUtils.adjustForHTML(propratnaGr));
		      buf.append("</propratnaGr>\n    <boja>");
		      buf.append(StringUtils.adjustForHTML(boja));
		      buf.append("</boja>\n    <zvuk>");
		      buf.append(StringUtils.adjustForHTML(zvuk));
		      buf.append("</zvuk>\n    <sirina>");
		      buf.append(StringUtils.adjustForHTML(sirina));
		      buf.append("</sirina>\n    <dim>");
		      buf.append(StringUtils.adjustForHTML(dim));
		      buf.append("</dim>\n     <nabavkaO>");
		      buf.append(StringUtils.adjustForHTML(nabavkaO));
		      buf.append("</nabavkaO>\n   <nabavkaK>");
		      buf.append(StringUtils.adjustForHTML(nabavkaK));
		      buf.append("</nabavkaK>\n   <nabavkaR>");
		      buf.append(StringUtils.adjustForHTML(nabavkaR));
		      buf.append("</nabavkaR>\n   <nabavkaP>");
		      buf.append(StringUtils.adjustForHTML(nabavkaP));
		      buf.append("</nabavkaP>\n   <cena>");
		      buf.append(StringUtils.adjustForHTML(cena));
		      buf.append("</cena>\n    <signatura>");
		      buf.append(StringUtils.adjustForHTML(sig));
		      buf.append("</signatura>\n    <napomena>");
		      buf.append(StringUtils.adjustForHTML(napomena));
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
  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
	  log.info("Finishing report...");
	    for (List<Item> list : itemMap.values())
	      Collections.sort(list);
	    
	    for (String key : itemMap.keySet()) {
	      List<Item> list = itemMap.get(key);
	      StringBuilder out = getWriter(key);
	      for (Item i : list){
	    	  System.out.println("ovde");
	    	   out.append(i.toString());
	    	  // out.flush();
	      }
	 
	    }
	    itemMap.clear();
	    log.info("Report finished.");
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
            gr.setReportName(key.substring(0,key.indexOf("-")));
            gr.setFullReportName(key);
            gr.setPeriod(key.substring(key.indexOf("-")+1));
            gr.setContent(out.toString());
            gr.setReportType(getType().name().toLowerCase());
            getReportRepository().save(gr);
	 
	    }
	    //closeFiles();
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
    
    
    StringBuffer opis = new StringBuffer();
    opis.append(autor);
    if (opis.length() > 0)
      opis.append(". ");
    opis.append(naslov);
    opis.append(", ");
    
    opis.append(mesto);
    if (mesto.length() > 0)
      opis.append(". - ");
    opis.append(izdavac);
    if (izdavac.length() > 0)
      opis.append(". - ");
    opis.append(god);
    opis.append(".");
   
    int vrPublikacije = rec.getPubType() ;
    String vrDok = rec.getSubfieldContent("115a");
    String brDok = rec.getSubfieldContent("215a");    
    if (brDok == null)
    	brDok = " ";
    String propratnaGr = rec.getSubfieldContent("215e");
    String boja = rec.getSubfieldContent("115c");
    String zvuk = rec.getSubfieldContent("115d");
    String sirina = rec.getSubfieldContent("115f");
    String dim = rec.getSubfieldContent("215d");
    if (dim == null)
      dim = " ";
    
    String sig = " ";

    for (Primerak p : rec.getPrimerci()) {
    	
      if(p.getInvBroj()==null)
    	  continue;
      if (p.getInvBroj().substring(0, 2).compareToIgnoreCase("31")==0){
		  if(p.getInvBroj().substring(5, 7).compareToIgnoreCase("06")!=0)
			  continue;
  }else if (p.getInvBroj().substring(2, 4).compareToIgnoreCase("06")!=0){
	  continue;
  }
      String vrPov = p.getPovez();
      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(), 
          p.getSigUDK());
      if (sig.equals(""))
    	  sig=" ";
      Item i = new Item();
      i.invbr =  nvl(p.getInvBroj());
      i.datum = p.getDatumInventarisanja();
      i.opis = opis.toString();
      i.vrDok = vrPov;
      i.brDok = brDok;
      i.propratnaGr = propratnaGr;
      i.boja = boja;
      i.zvuk = zvuk;
      i.sirina = sirina;
      i.dim = dim;
      String dobavljac=nvl(p.getDobavljac());
      String vrnab = nvl(p.getNacinNabavke());
      //    ******************    NABAVKA NIJE ZAVRSENA   *************************
      String nabavkaO=" ";
      String nabavkaK=" ";
      String nabavkaR=" ";
      String nabavkaP=" ";
      if (vrnab.equals("c") || vrnab.equals("p")) {
    	  nabavkaP = "poklon";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaP += ", " + dobavljac;
        } else if (vrnab.equals("a") || vrnab.equals("k")) {
          nabavkaK = "kupovina";
          if (dobavljac!="" && dobavljac!=" ")
            nabavkaK += ", " + dobavljac;
          String brRac=nvl(p.getBrojRacuna());
          if (brRac!="" && brRac!=" ")
              nabavkaK += ", " + brRac;
            
        } else if (vrnab.equals("b")) {
          nabavkaR = "razmena";
        } else if (vrnab.equals("d")) {
          nabavkaO = "obavezni primerak";
        } else if (vrnab.equals("e")) {
          //nabavka = "zate\u010deni fond";
        } else if (vrnab.equals("f") || vrnab.equals("s")) {
          //nabavka = "sopstvena izdanja";
        } else if (vrnab.equals("o")) {
          //nabavka = "otkup";
        }
        DecimalFormat df2 = new DecimalFormat(".##");
      i.cena = p.getCena() == null ? " " : 
        df2.format(p.getCena()).toString();
      i.sig = sig;
      i.napomena = nvl(p.getNapomene());
      String key = settings.getReportName() + super.getFilenameSuffix(p.getDatumInventarisanja());
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
	    } else if (rec.getField("710") != null) {
	      String sfa = rec.getSubfieldContent("710a");
	      
	      if (sfa != null) {
	        
	          return toSentenceCase(sfa) ;
	        
	      } 
	        else
	          return "";
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
  private Map<String, List<Item>> itemMap = new HashMap<>();

  private String name;
  private static Logger log = Logger.getLogger(InvKnjigaZaFilmoveIVideosnimke.class);

}
