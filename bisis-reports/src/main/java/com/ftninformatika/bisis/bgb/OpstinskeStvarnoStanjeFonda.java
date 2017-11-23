package com.ftninformatika.bisis.bgb;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class OpstinskeStvarnoStanjeFonda extends Report {
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

		for (List<Ogranak> list : itemMap.values())
			Collections.sort(list);

		for (String key : itemMap.keySet()) {
			List<Ogranak> list = itemMap.get(key);
			StringBuilder out = getWriter(key);
			
			for (Ogranak o : list) {
				out.append(o.toString());
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
	  boolean first=true;
    if (rec == null)
      return;
    String RN = rec.getSubfieldContent("001e");
    for (Primerak p : rec.getPrimerci()) {
        Date datInv = p.getDatumInventarisanja();

      boolean error = false;
      // inv. broj
      String invBr = p.getInvBroj();
      if (invBr == null ||invBr.trim().length() == 0) {
        log.error("RN: " + RN + " nedostaje inventarni broj\n");
        error = true;
      } 
      // ogranak
      String ogranakID=p.getOdeljenje();
	  if (ogranakID == null || ogranakID.equals("")) {
			  ogranakID = "\u043d\u0435\u0440."; // ner.
	  }

      // status
      String status=p.getStatus();
      if (status == null || status.trim().length() == 0) {
        log.error("RN: " + RN + " nedostaje status za IN=" + invBr + "\n");
        error = true;
      }   
      if (error)
        continue;
      
      String key = settings.getReportName() + getFilenameSuffix(datInv);
      Ogranak o=getItem(getList(key),ogranakID); 
      if (o == null ){
    	o = new Ogranak();
        o.ogr = ogranakID;
       	o.add(RN, status.charAt(0));
       	getList(key).add(o); 	
       }else{
    	   if(first){
    		  clearSets(o);
			  first=false;
    	   }
    	   o.add(RN, status.charAt(0));
       }
    }
  }
  
  public Ogranak getItem(List<Ogranak> ol, String ogranakID) {
		
		for (Ogranak o : ol){
			
			if (o.ogr.compareToIgnoreCase(ogranakID)==0){	
				return o;
			}
		}
	    return null;
	}
  
  public List<Ogranak> getList(String key) {
	    List<Ogranak> list = itemMap.get(key);
	    if (list == null) {
	      list = new ArrayList<Ogranak>();
	      itemMap.put(key, list);
	    }
	    return list;
  }
  
  private void clearSets(Ogranak o){
	  o.set0.clear();
	  o.set1.clear();
	  o.set2.clear();
	  o.set3.clear();
	  o.set4.clear();
	  o.set5.clear();
	  o.set6.clear();
	  o.set7.clear();
	  o.set8.clear();
	  o.set9.clear();
	  o.setA.clear();
	  o.setN.clear();
	  o.setu.clear();
	  o.setu6.clear();
  }
  
  public class Ogranak implements Comparable {
    public String ogr;
    public int n0, p0;
    public int n1, p1;
    public int n2, p2;
    public int n3, p3;
    public int n4, p4;
    public int n5, p5;
    public int n6, p6;
    public int n7, p7;
    public int n8, p8;
    public int n9, p9;
    public int na, pa;
    public int nn, pn;
    public int nu, pu;
    public int nu6, pu6;
    public HashSet set0 = new HashSet();
    public HashSet set1 = new HashSet();
    public HashSet set2 = new HashSet();
    public HashSet set3 = new HashSet();
    public HashSet set4 = new HashSet();
    public HashSet set5 = new HashSet();
    public HashSet set6 = new HashSet();
    public HashSet set7 = new HashSet();
    public HashSet set8 = new HashSet();
    public HashSet set9 = new HashSet();
    public HashSet setA = new HashSet();
    public HashSet setN = new HashSet();
    public HashSet setu = new HashSet();
    public HashSet setu6 = new HashSet();
    
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, char status) {
      switch (status) {
        case '0':
          p0++;
          if (rn != null){
            if (!set0.contains(rn)) {
              n0++;
              set0.add(rn);
            }
          }
          break;
        case '1':
          p1++;
          if (rn != null)
            if (!set1.contains(rn)) {
              n1++;
              set1.add(rn);
            }
          break;
        case '2':
          p2++;
          if (rn != null)
            if (!set2.contains(rn)) {
              n2++;
              set2.add(rn);
            }
          break;
        case '3':
          p3++;
          if (rn != null)
            if (!set3.contains(rn)) {
              n3++;
              set3.add(rn);
            }
          break;
        case '4':
          p4++;
          if (rn != null)
            if (!set4.contains(rn)) {
              n4++;
              set4.add(rn);
            }
          break;
        case '5':
          p5++;
          if (rn != null)
            if (!set5.contains(rn)) {
              n5++;
              set5.add(rn);
            }
          break;
        case '6':
          p6++;
          if (rn != null)
            if (!set6.contains(rn)) {
              n6++;
              set6.add(rn);
            }
          break;
        case 'o':
          p7++;
          if (rn != null)
            if (!set7.contains(rn)) {
              n7++;
              set7.add(rn);
            }
          break;
        case '8':
          p8++;
          if (rn != null)
            if (!set8.contains(rn)) {
              n8++;
              set8.add(rn);
            }
          break;
        case '9':
          p9++;
          if (rn != null)
            if (!set9.contains(rn)) {
              n9++;
              set9.add(rn);
            }
          break;
        case 'A':
          pa++;
          if (rn != null)
            if (!setA.contains(rn)) {
              na++;
              setA.add(rn);
            }
          break;
        case 'N':
          pn++;
          if (rn != null)
            if (!setN.contains(rn)) {
              nn++;
              setN.add(rn);
            }
          break;
      }
      pu++;
      if (rn != null) {
        if (!setu.contains(rn)) {
          nu++;
          setu.add(rn);
        }
        //if (!ukupnoNaslova.contains(rn))
        //  ukupnoNaslova.add(rn);
      }
      pu6 = pu - p6;
      if (status == '1' || status == '2') {
        if (!setu6.contains(rn)) {
          setu6.add(rn);
          nu6++;
        }
     /*   if (!ukupnoNerashodovanihNaslova.contains(rn))
          ukupnoNerashodovanihNaslova.add(rn);*/
      }
    }
    public String toString() {
    	String odeljenje;
    	if (!ogr.equalsIgnoreCase("\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438")){	 
    	 odeljenje=LatCyrUtils.toCyrillic("nepoznato");//HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, ogr);
    	 if(getCoders().getLocCoders().get(ogr) != null)
    	   odeljenje = LatCyrUtils.toCyrillic(getCoders().getLocCoders().get(ogr).getDescription());
          if(odeljenje!=null){
    	   int zarez=odeljenje.indexOf(",");
    	   if(zarez!=-1){
    		  odeljenje=odeljenje.substring(0, zarez);
    	   }
    	 }
    	}else{
    		odeljenje=ogr;
    	}
    	StringBuffer buf = new StringBuffer();
        buf.append("\n  <item id=\"");
        buf.append(ogr);
        buf.append("\">\n  <ogranak>");
        buf.append(odeljenje);
        buf.append(	"</ogranak>\n	<n0>");
        buf.append(getN0());
        buf.append("</n0>\n    <p0>");
        buf.append(getP0());
        buf.append("</p0>\n    <n1>");
        buf.append(getN1());
        buf.append("</n1>\n    <p1>");
        buf.append(getP1());
        buf.append("</p1>\n    <n2>");
        buf.append(getN2());
        buf.append("</n2>\n    <p2>");
        buf.append(getP2());
        buf.append("</p2>\n    <n3>");
        buf.append(getN3());
        buf.append("</n3>\n    <p3>");
        buf.append(getP3());
        buf.append("</p3>\n		<n4>");
        buf.append(getN4());
        buf.append("</n4>\n		<p4>");
        buf.append(getP4());
        buf.append("</p4>\n		<n5>");
        buf.append(getN5());
        buf.append("</n5>\n		<p5>");
        buf.append(getP5());
        buf.append("</p5>\n		<n6>");
        buf.append(getN6());
        buf.append("</n6>\n		<p6>");
        buf.append(getP6());
        buf.append("</p6>\n		<n7>");
        buf.append(getN7());
        buf.append("</n7>\n		<p7>");
        buf.append(getP7());
        buf.append("</p7>\n		<n8>");
        buf.append(getN8());
        buf.append("</n8>\n		<p8>");
        buf.append(getP8());
        buf.append("</p8>\n		<n9>");
        buf.append(getN9());
        buf.append("</n9>\n		<p9>");
        buf.append(getP9());
        buf.append("</p9>\n		<na>");
        buf.append(getNa());
        buf.append("</na>\n		<pa>");
        buf.append(getPa());
        buf.append("</pa>\n		<nn>");
        buf.append(getNn());
        buf.append("</nn>\n		<pn>");
        buf.append(getPn());
        buf.append("</pn>\n		<nu>");
        buf.append(getNu());
        buf.append("</nu>\n		<pu>");
        buf.append(getPu());
        buf.append("</pu>\n		<nu6>");
        buf.append(getNu6());
        buf.append("</nu6>\n		<pu6>");
        buf.append(getPu6());
        buf.append("</pu6>\n		</item>");
        
        return buf.toString();
    }
    public String getOgr() {
      return ogr;
    }
    public void setOgr(String ogr) {
      this.ogr = ogr;
    }
    public int getN0() {
      return n0;
    }
    public void setN0(int n0) {
      this.n0 = n0;
    }
    public int getP0() {
      return p0;
    }
    public void setP0(int p0) {
      this.p0 = p0;
    }
    public int getN1() {
      return n1;
    }
    public void setN1(int n1) {
      this.n1 = n1;
    }
    public int getP1() {
      return p1;
    }
    public void setP1(int p1) {
      this.p1 = p1;
    }
    public int getN2() {
      return n2;
    }
    public void setN2(int n2) {
      this.n2 = n2;
    }
    public int getP2() {
      return p2;
    }
    public void setP2(int p2) {
      this.p2 = p2;
    }
    public int getN3() {
      return n3;
    }
    public void setN3(int n3) {
      this.n3 = n3;
    }
    public int getP3() {
      return p3;
    }
    public void setP3(int p3) {
      this.p3 = p3;
    }
    public int getN4() {
      return n4;
    }
    public void setN4(int n4) {
      this.n4 = n4;
    }
    public int getP4() {
      return p4;
    }
    public void setP4(int p4) {
      this.p4 = p4;
    }
    public int getN5() {
      return n5;
    }
    public void setN5(int n5) {
      this.n5 = n5;
    }
    public int getP5() {
      return p5;
    }
    public void setP5(int p5) {
      this.p5 = p5;
    }
    public int getN6() {
      return n6;
    }
    public void setN6(int n6) {
      this.n6 = n6;
    }
    public int getP6() {
      return p6;
    }
    public void setP6(int p6) {
      this.p6 = p6;
    }
    public int getN7() {
      return n7;
    }
    public void setN7(int n7) {
      this.n7 = n7;
    }
    public int getP7() {
      return p7;
    }
    public void setP7(int p7) {
      this.p7 = p7;
    }
    public int getN8() {
      return n8;
    }
    public void setN8(int n8) {
      this.n8 = n8;
    }
    public int getP8() {
      return p8;
    }
    public void setP8(int p8) {
      this.p8 = p8;
    }
    public int getN9() {
      return n9;
    }
    public void setN9(int n9) {
      this.n9 = n9;
    }
    public int getP9() {
      return p9;
    }
    public void setP9(int p9) {
      this.p9 = p9;
    }
    public int getNa() {
      return na;
    }
    public void setNa(int na) {
      this.na = na;
    }
    public int getPa() {
      return pa;
    }
    public void setPa(int pa) {
      this.pa = pa;
    }
    public int getNn() {
      return nn;
    }
    public void setNn(int nn) {
      this.nn = nn;
    }
    public int getPn() {
      return pn;
    }
    public void setPn(int pn) {
      this.pn = pn;
    }
    public int getNu() {
      return nu;
    }
    public void setNu(int nu) {
      this.nu = nu;
    }
    public int getPu() {
      return pu;
    }
    public void setPu(int pu) {
      this.pu = pu;
    }
    public int getNu6() {
      return nu6;
    }
    public void setNu6(int nu6) {
      this.nu6 = nu6;
    }
    public int getPu6() {
      return pu6;
    }
    public void setPu6(int pu6) {
      this.pu6 = pu6;
    }
  }
 
  
  private Map<String, List<Ogranak>> itemMap = new HashMap<String, List<Ogranak>>();
  private static Log log = LogFactory.getLog(OpstinskeStvarnoStanjeFonda.class);
  NumberFormat nf;
//  private Set ukupnoNaslova = new HashSet();
//  private Set ukupnoNerashodovanihNaslova = new HashSet();




}
