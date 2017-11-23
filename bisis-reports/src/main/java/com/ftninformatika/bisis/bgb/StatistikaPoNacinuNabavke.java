package com.ftninformatika.bisis.bgb;

import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StatistikaPoNacinuNabavke extends Report {
  
	@Override
	public void init() {
		 nf = NumberFormat.getInstance(Locale.GERMANY);
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			itemMap.clear();
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
			
			for (Item i : list) {
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
		itemMap.clear();
		log.info("Report finished.");
		
	}

	 public void handleRecord(Record rec) {
			Map<String, List<String>> oznakeMap = new HashMap<String, List<String>>();
			Map<String, Boolean> recMap = new HashMap<String, Boolean>();
		    if (rec == null)
		      return;
		    String nacinNabavke;
		    Date dateInv;
		    for (Primerak p : rec.getPrimerci()) {
		        dateInv=p.getDatumInventarisanja();
		        nacinNabavke=p.getNacinNabavke();
		        if(nacinNabavke==null)
		        	nacinNabavke="nerazvrstano";
		        String key = settings.getReportName() + getFilenameSuffix(dateInv);
		        Item i = getItem(getList(key),nacinNabavke);
		        if (i == null ){
		        	i = new Item(nacinNabavke);
		        	 List <String>l=oznakeMap.get(key);
		        	 if(l==null){
		        		 l=new ArrayList<String>();
		        		 l.add(nacinNabavke);
		        		 oznakeMap.put(key, l);
		        		 i.add(1, 1);
		        	 }else{
		        		if (!l.contains(nacinNabavke)){
		        		    l.add(nacinNabavke);
		        		    oznakeMap.put(key, l);
		        		    i.add(1, 1);
		        	    }else{
		        	    	 i.add(0, 1);
		        	    }
		              }
		              getList(key).add(i);
		        }else{
		              List <String>l=oznakeMap.get(key);
		         	 if(l==null){
		         		 l=new ArrayList<String>();
		         		 l.add(nacinNabavke);
		         		 oznakeMap.put(key, l);
		         		 i.add(1, 1);
		         	 }else{
		         		if (!l.contains(nacinNabavke)){
		         		    l.add(nacinNabavke);
		         		    oznakeMap.put(key, l);
		         		    i.add(1, 1);
		         	    }else{
		         	    	 i.add(0, 1);
		         	    }
		              }
		        }
		        Item ukupno = getItem(getList(key),"UKUPNO");     
		        if (ukupno == null ){
		            ukupno = new Item("UKUPNO");
		        	Boolean recAdded=recMap.get(key);
		        	if(recAdded==null){
		        	  recMap.put(key, new Boolean(true));
		        	  ukupno.add(1, 1);
		        	}else{
		        		ukupno.add(0, 1);
		        	}
		        	getList(key).add(ukupno); 
		        }else{
		        	Boolean recAdded=recMap.get(key);
		        	if(recAdded==null){
		        	  recMap.put(key, new Boolean(true));
		        	  ukupno.add(1, 1);
		        	}else{
		        		ukupno.add(0, 1);
		        	}
		            
		        }
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

	  private Item getItem(List<Item> il,String nacinNab) {
			for (Item i : il){		
				if (i.nacinNab.compareToIgnoreCase(nacinNab)==0){	
					return i;
				}
			}
		    return null;        
	  }

  public class Item implements Comparable {
    public String nacinNab;
    public int brNas;
    public int brPr;
    public boolean isTotal;

    public Item(String nacinNab) {
      this.nacinNab = nacinNab;
      this.brNas = 0;
      this.brPr = 0;
      this.isTotal = false;
    }

    public int compareTo(Object o) {
      if (o instanceof Item) {
        Item b = (Item) o;
        return nacinNab.compareTo(b.nacinNab);
      }
      return 0;
    }

    public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("\n  <item  total=\"");
      buf.append(isTotal);
      buf.append("\">\n    <nacinNab>");
      buf.append(getName(nacinNab));
      buf.append("</nacinNab>\n    <brNas>");
      buf.append(brNas);
      buf.append("</brNas>\n   <brPr>");
      buf.append(brPr);
      buf.append("</brPr>\n    </item>");
      return buf.toString();
    }

    public void add(int brNas, int brPr) {
      this.brNas += brNas;
      this.brPr += brPr;
    }

    public void addItem(Item i) {
      brNas += i.brNas;
      brPr += i.brPr;
    }
  }

  public String getName(String code) {
    String name;
    if (code == null || code.equals(""))
      return "nerazvrstano";
    else if (code.equals("a"))
      return "a - razmena";
    else if (code.equals("i"))
      return "i - izdanja BGB";
    else if (code.equals("k"))
      return "k - kupovina";
    else if (code.equals("l"))
      return "l - poklon izdava\u010da";
    else if (code.equals("m"))
      return "m - marketing";
    else if (code.equals("n"))
      return "n - OP NBS";
    else if (code.equals("o"))
      return "o - otkup SG";
    else if (code.equals("p"))
      return "p - poklon";
    else if (code.equals("r"))
      return "r - otkup RS";
    else if (code.equals("t"))
      return "t - zate\u010deno";
    else if (code.equals("z"))
      return "z - zamena";
    else if (code.equals("UKUPNO"))
      return "UKUPNO";
    else
      return code ;
  }

  private Date startDate;
  private Date endDate;
  private String dateRange;
  int totalRecords = 0;
  int noInvDate = 0;
  int noInvNum = 0;
  int recordSum = 0;
  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
  SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
  private static Log log = LogFactory.getLog(StatistikaPoNacinuNabavke.class);
  NumberFormat nf;

}
