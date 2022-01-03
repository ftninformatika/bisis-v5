//package com.ftninformatika.bisis.bgv;
//
//import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
//import com.gint.app.bisis4.format.PubTypes;
//import com.gint.app.bisis4.records.Godina;
//import com.gint.app.bisis4.records.Record;
//import com.gint.app.bisis4.reports.Period;
//import com.gint.app.bisis4.reports.Report;
//import com.gint.app.bisis4.reports.ReportsUtils;
//import com.gint.app.bisis4.utils.FormatBigDecimal;
//import com.gint.app.bisis4.utils.Signature;
//import com.gint.util.string.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import java.io.PrintWriter;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class KnjigaInventaraSerijske extends Report {
//	public class Item implements Comparable {
//	    public String invbr;
//	    public Date datum;
//	    public String opis;
//	    public String god;
//	    public String brSv;
//	    public String dim;
//	    public String povez;
//	    public String nepovez;
//	    public String nabavka;
//	    public String cena;
//	    public String signatura;
//	    public String napomena;
//
//
//	    public int compareTo(Object o) {
//	      if (o instanceof Item) {
//	        Item b = (Item)o;
//	        return invbr.compareTo(b.invbr);
//	      }
//	      return 0;
//	    }
//
//	    public String toString() {
//	      StringBuffer buf = new StringBuffer();
//	      buf.append("\n  <item>\n    <rbr>");
//	      buf.append(invbr);
//	      buf.append("</rbr>\n    <datum>");
//	      buf.append(datum == null ? "" : sdf.format(datum));
//	      buf.append("</datum>\n    <opis>");
//	      buf.append(StringUtils.adjustForHTML(opis));
//	      buf.append("</opis>\n   <god>");
//	      buf.append(god == null ? "" : StringUtils.adjustForHTML(god));
//	      buf.append("</god>\n    <brSv>");
//	      buf.append(brSv == null ? "" : StringUtils.adjustForHTML(brSv));
//	      buf.append("</brSv>\n    <dim>");
//	      buf.append(dim == null ? "" : StringUtils.adjustForHTML(dim));
//	      buf.append("</dim>\n    <povez>");
//	      buf.append(povez);
//	      buf.append("</povez>\n    <nabavka>");
//	      buf.append(nabavka == null ? "" : StringUtils.adjustForHTML(nabavka));
//	      buf.append("</nabavka>\n     <cena>");
//	      buf.append(cena == null ? "" : StringUtils.adjustForHTML(cena));
//	      buf.append("</cena>\n    <signatura>");
//	      buf.append(signatura == null ? "" : StringUtils.adjustForHTML(signatura));
//	      buf.append("</signatura>\n    <napomena>");
//	      buf.append(napomena == null ? "" : StringUtils.adjustForHTML(napomena));
//	      buf.append("</napomena>\n  </item>");
//	      return buf.toString();
//	    }
//	  }
//
//  @Override
//  public void init() {
//	  itemMap.clear();
//	  pattern = Pattern.compile(getReportSettings().getParam("invnumpattern"));
//	  log.info("Report initialized.");
//  }
//
//  @Override
//  public void finishInv() {  //zbog inventerni one se snimaju u fajl po segmentima a ne sve od jednom
//	  log.info("Finishing report...");
//	    for (List<Item> list : itemMap.values())
//	      Collections.sort(list);
//
//	    for (String key : itemMap.keySet()) {
//	      List<Item> list = itemMap.get(key);
//	      PrintWriter out = getWriter(key);
//	      for (Item i : list){
//	    	   out.print(i.toString());
//
//	      }
//	      out.flush();
//	      itemMap.get(key).clear();
//	    }
//
//	    log.info("Report finished1.");
//  }
//  @Override
//  public void finish() {
//	  log.info("Finishing report...");
//	    for (List<Item> list : itemMap.values())
//	      Collections.sort(list);
//
//	    for (String key : itemMap.keySet()) {
//	      List<Item> list = itemMap.get(key);
//	      PrintWriter out = getWriter(key);
//	      for (Item i : list){
//	    	   out.print(i.toString());
//
//	      }
//
//	      out.println("</report>");
//	      out.close();
//	    }
//
//	  //  closeFiles();
//	    itemMap.clear();
//	    log.info("Report finished2.");
//  }
//
//  @Override
//  public void handleRecord(Record rec) {
//    if (rec == null)
//      return;
//
//       String naslov = rec.getSubfieldContent("200a");
//    if (naslov == null)
//      naslov = "";
//
//    String podnaslov = rec.getSubfieldContent("200e");
//    if (podnaslov == null)
//    	podnaslov = "";
//    String mesto = rec.getSubfieldContent("210a");
//    if (mesto == null)
//      mesto = "";
//    String drzava = PubTypes.getFormat().getSubfield("102a").getCoder().getValue(rec.getSubfieldContent("102a"));
//    if (drzava == null)
//    	drzava = "";
//
//
//    StringBuffer opis = new StringBuffer();
//
//
//
//    opis.append(naslov);
//
//    if (podnaslov.length() > 0)
//      opis.append(" : ");
//    opis.append(podnaslov);
//    opis.append("\n");
//    opis.append(mesto);
//
//    if (drzava.length() > 0)
//      opis.append(", ");
//    opis.append(drzava);
//
//
//
//    String dim = rec.getSubfieldContent("215d");
//    if (dim == null)
//      dim = " ";
//
//     String sig = " ";
//
//    for (Godina p : rec.getGodine()) {
//
//      if(p.getInvBroj()==null)
//    	  continue;
//
//      Matcher matcher = pattern.matcher(p.getInvBroj());
//      if (!matcher.matches())
//        continue;
//      String brSv = Integer.toString(p.getSveskeCount());
//      if (brSv == null)
//      	brSv = " ";
//      sig = Signature.format(p.getSigDublet(), p.getSigPodlokacija(),
//          p.getSigIntOznaka(), p.getSigFormat(), p.getSigNumerusCurens(),
//          p.getSigUDK());
//      if (sig.equals(""))
//    	  sig=" ";
//      Item i = new Item();
//      i.invbr = p.getInvBroj();
//      i.datum = p.getDatumInventarisanja();
//      i.opis = opis.toString();
//      String povez=HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.POVEZ_CODER, p.getPovez());
//      if(povez==null){
//    	  povez="";
//      }
//      int zagrada=povez.indexOf("(");
//      if(zagrada !=-1){
//    	  i.povez= povez.substring(0,zagrada);
//      }else{
//      i.povez=povez;
//      }
//      i.nepovez ="";
//      i.dim = dim;
//      String dobavljac=p.getDobavljac();
//      String vrnab = p.getNacinNabavke();
//      String nabavka=HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.NACINNABAVKE_CODER, p.getNacinNabavke());
//
//      brSv=String.valueOf(p.getSveske().size());
//      i.brSv=brSv;
//      i.cena = p.getCena() == null ? " " :
//        FormatBigDecimal.format(p.getCena(), 2).toPlainString();
//      i.signatura = sig;
//      i.nabavka=nabavka;
//      i.napomena = p.getNapomene();
//      String godina=ReportsUtils.nvl(p.getGodina());
//      String godiste=ReportsUtils.nvl(p.getGodiste());
//      if (!godiste.equals(""))
//      i.god =godiste;
//      godina =godina.trim();
//      if ((godina.length()>0)&& (!godina.equals(" "))&& (godiste.length()>0)&& (!godiste.equals(" "))){
//    	  i.god+=", ";
//      }
//    	  i.god+=godina;
//
//    	  String part=settings.getParam("part");
//          String type=settings.getParam("type");
//    	  String key;
//          if(part==null){
//        	if (type.equals("online")){
//        		key = settings.getParam("file");
//        	}else{
//             key = settings.getParam("file") + getFilenameSuffix(p.getDatumInventarisanja());
//        	}
//          }else{ //ukoliko zelimo iventarnu knjigu od po npr 1000
//          	   //parametar part odredjuje koliko je primeraka u jednom fajlu
//            String invBroj=p.getInvBroj().substring(2);
//            int partBr=Integer.parseInt(part);
//            int ceo=Integer.parseInt(invBroj)/partBr;
//            int odBr=ceo*partBr;
//            int doBr=ceo*partBr + partBr;
//            key = settings.getParam("file") +"-"+ReportsUtils.addZeroes(String.valueOf(odBr))+"_do_"+ReportsUtils.addZeroes(String.valueOf(doBr));
//          }
//      getList(key).add(i);
//
//    }
//  }
//  public List<Item> getList(String key) {
//	    List<Item> list = itemMap.get(key);
//	    if (list == null) {
//	      list = new ArrayList<Item>();
//	      itemMap.put(key, list);
//	    }
//	    return list;
//}
//
//
//  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
//  private Period period;
//  private Pattern pattern;
//  private List<Item> items = new ArrayList<Item>();
//  private String name;
//  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
//  private static Log log = LogFactory.getLog(KnjigaInventaraSerijske.class);
//@Override
//public void finishOnline( StringBuffer buff ) {
//	 log.info("Finishing report...");
//
//
//	    for (String key : itemMap.keySet()) {
//	      List<Item> list = itemMap.get(key);
//	      Collections.sort(list);
//	      for (Item i : list){
//	    	  buff.append(i.toString());
//
//	      }
//	    }
//	    itemMap.clear();
//	    log.info("Report finished.");
//}
//}
