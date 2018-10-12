package com.ftninformatika.bisis.bgb;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;

import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OpstinskePremaUdk extends Report {

	@Override
	public void init() {
	    nf = NumberFormat.getInstance(Locale.GERMANY);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		itemMap.clear();
      pattern = Pattern.compile(getReportSettings().getInvnumpattern());
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
    if (rec == null)
      return;
    String RN = rec.getSubfieldContent("001e");
    String udk = rec.getSubfieldContent("675a");
    for (Primerak p : rec.getPrimerci()) {
      Matcher matcher = pattern.matcher(p.getInvBroj());
      if (!matcher.matches())
        continue;
        Date datInv = p.getDatumInventarisanja();
        String nacinNabavke = p.getNacinNabavke();

      // ako je nacin nabavke = zateceno stanje, primerak ne ulazi u statistiku bez obzira kad je inventarisan
      if (nacinNabavke != null && nacinNabavke.trim().length() != 0) {
        if ("t".equalsIgnoreCase(nacinNabavke))
          continue;
      }

      boolean error = false;
      // inv. broj
      String invBr = p.getInvBroj();
      if (invBr == null ||invBr.trim().length() == 0) {
        log.error("RN: " + RN + " nedostaje inventarni broj\n");
        error = true;
      } 

      // razvrstava primerke po podlokacijama
      String ogranakID=p.getSigPodlokacija();
      if(ogranakID==null){
        ogranakID="\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438"; // nerazvrstani
      }
      if (udk == null) { //ako nema 675a onda probaj iz signature
        udk = p.getSigUDK();
        if (udk == null) {
          log.error("RN: " + RN + " nedostaje UDK za IN=" + invBr + "\n");
          error = true;
        }
      }
      
      if (error)
        continue;
      
      char digit = getUdkClass(udk);
      if (digit == 's' && "99".equals(invBr.substring(2, 4)))
        digit = 's';
      else if (digit == 's')
        digit = 'd';
      String key = settings.getReportName() + getFilenameSuffix(datInv);
      Ogranak o=getItem(getList(key),ogranakID); 
      if (o == null ){
    	o = new Ogranak();
        o.ogr = ogranakID;
       	o.add(RN, digit);
       	getList(key).add(o); 	
       }else{
    	   o.add(RN, digit);
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
  
  public class Ogranak implements Comparable {
    public String ogr;
    public int n0, p0;
    public int n1, p1;
    public int n2, p2;
    public int n3, p3;
    public int n5, p5;
    public int n6, p6;
    public int n7, p7;
    public int n8d, p8d;
    public int n8s, p8s;
    public int n9, p9;
    public int nd, pd;
    public int nu, pu;
    public int ns, ps;
    public HashSet set0 = new HashSet();
    public HashSet set1 = new HashSet();
    public HashSet set2 = new HashSet();
    public HashSet set3 = new HashSet();
    public HashSet set5 = new HashSet();
    public HashSet set6 = new HashSet();
    public HashSet set7 = new HashSet();
    public HashSet set8d = new HashSet();
    public HashSet set8s = new HashSet();
    public HashSet set9 = new HashSet();
    public HashSet setd = new HashSet();
    public HashSet setu = new HashSet();
    public HashSet sets = new HashSet();
    
    public int compareTo(Object o) {
      if (o instanceof Ogranak) {
        Ogranak og1 = (Ogranak)o;
        return ogr.compareTo(og1.ogr);
      }
      return 0;
    }
    public void add(String rn, char digit) {
      switch (digit) {
        case '0':
          p0++;
          if (rn != null)
            if (!set0.contains(rn)) {
              n0++;
              set0.add(rn);
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
        case '7':
          p7++;
          if (rn != null)
            if (!set7.contains(rn)) {
              n7++;
              set7.add(rn);
            }
          break;
        case '8':
          p8s++;
          if (rn != null)
            if (!set8s.contains(rn)) {
              n8s++;
              set8s.add(rn);
            }
          break;
        case 'h':
          p8d++;
          if (rn != null)
            if (!set8d.contains(rn)) {
              n8d++;
              set8d.add(rn);
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
        case 'd':
          pd++;
          if (rn != null)
            if (!setd.contains(rn)) {
              nd++;
              setd.add(rn);
            }
          break;
        case 's':
          ps++;
          if (rn != null)
            if (!sets.contains(rn)) {
              ns++;
              sets.add(rn);
            }
          break;
      }
      if (digit != 's') {
        pu++;
        if (rn != null) {
          if (!setu.contains(rn)) {
            nu++;
            setu.add(rn);
          }
          if (!ukupnoNaslova.contains(rn))
            ukupnoNaslova.add(rn);
        }
      } else {
        if (!ukupnoSlikovnica.contains(rn))
          ukupnoSlikovnica.add(rn);
      }
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
    
    public int getN8d() {
      return n8d;
    }
    
    public void setN8d(int n8d) {
      this.n8d = n8d;
    }
    
    public int getP8d() {
      return p8d;
    }
    public void setP8d(int p8d) {
      this.p8d = p8d;
    }
    public int getN8s() {
      return n8s;
    }
    public void setN8s(int n8s) {
      this.n8s = n8s;
    }
    public int getP8s() {
      return p8s;
    }
    public void setP8s(int p8s) {
      this.p8s = p8s;
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
    public int getNd() {
      return nd;
    }
    public void setNd(int nd) {
      this.nd = nd;
    }
    public int getPd() {
      return pd;
    }
    public void setPd(int pd) {
      this.pd = pd;
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
    public int getNs() {
      return ns;
    }
    public void setNs(int ns) {
      this.ns = ns;
    }
    public int getPs() {
      return ps;
    }
    public void setPs(int ps) {
      this.ps = ps;
    }
    
    public String toString() {
    	String odeljenje;
//    	if (!ogr.equalsIgnoreCase("\u043d\u0435\u0440\u0430\u0437\u0432\u0440\u0441\u0442\u0430\u043d\u0438")){
//    	   odeljenje=LatCyrUtils.toCyrillic("nepoznato");//HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, ogr);
//    	  if(getCoders().getSublocCoders().get(ogr) != null)
//    	    odeljenje = LatCyrUtils.toCyrillic(getCoders().getSublocCoders().get(ogr).getDescription());
//          int zarez=odeljenje.indexOf(",");
//    	  if(zarez!=-1){
//    		odeljenje=odeljenje.substring(0, zarez);
//    	  }
//    	}else{
    		odeljenje=ogr;
    //	}
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
        buf.append("</p3>\n		<n5>");
        buf.append(getN6());
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
        buf.append("</p7>\n		<n8d>");
        buf.append(getN8d());
        buf.append("</n8d>\n		<p8d>");
        buf.append(getP8d());
        buf.append("</p8d>\n		<n8s>");
        buf.append(getN8s());
        buf.append("</n8s>\n		<p8s>");
        buf.append(getP8s());
        buf.append("</p8s>\n		<n9>");
        buf.append(getN9());
        buf.append("</n9>\n		<p9>");
        buf.append(getP9());
        buf.append("</p9>\n		<nu>");
        buf.append(getNu());
        buf.append("</nu>\n		<pu>");
        buf.append(getPu());
        buf.append("</pu>\n		<nd>");
        buf.append(getNd());
        buf.append("</nd>\n		<pd>");
        buf.append(getPd());
        buf.append("</pd>\n		<ns>");
        buf.append(getNs());
        buf.append("</ns>\n		<ps>");
        buf.append(getPs());
        buf.append("</ps>\n		</item>");
        
        return buf.toString();
    }
    
  }

  
  private Ogranak makeSum(List items) {
    Ogranak sum = new Ogranak();
    sum.ogr = "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
    for (int i = 0; i < items.size(); i++) {
      Ogranak o = (Ogranak)items.get(i);
      sum.n0 += o.n0; sum.p0 += o.p0;
      sum.n1 += o.n1; sum.p1 += o.p1;
      sum.n2 += o.n2; sum.p2 += o.p2;
      sum.n3 += o.n3; sum.p3 += o.p3;
      sum.n5 += o.n5; sum.p5 += o.p5;
      sum.n6 += o.n6; sum.p6 += o.p6;
      sum.n7 += o.n7; sum.p7 += o.p7;
      sum.n8s += o.n8s; sum.p8s += o.p8s;
      sum.n8d += o.n8d; sum.p8d += o.p8d;
      sum.n9 += o.n9; sum.p9 += o.p9;
      sum.nd += o.nd; sum.pd += o.pd;
      sum.ns += o.ns; sum.ps += o.ps;
      sum.nu += o.nu; sum.pu += o.pu;
    }
    sum.nu = ukupnoNaslova.size();
    sum.ns = ukupnoSlikovnica.size();
    return sum;
  }
  
  /**
   * Vraca sledece vrednosti:
   * s - slikovnice
   * d - decja
   * h - 8-domaca
   * 8 - 8-strana
   * 0,1,2,3,5,6,7,9 - 0,1,2,3,5,6,7,9
   * @param s UDK broj
   * @return
   */
  private char getUdkClass(String s) {
    if (s.startsWith("087.5"))
      return 's';
    Matcher m1 = pDecje1.matcher(s);
    Matcher m2 = pDecje2.matcher(s);
    Matcher m3 = pDecje3.matcher(s);
    Matcher m4 = pDecje4.matcher(s);
    if (m1.matches() || m2.matches() || m3.matches() || m4.matches())
      return 'd';
    if (s.startsWith("821.163.41"))
      return 'h';
    return getFirstDigit(s);
  }
  
  private char getFirstDigit(String s) {
    if (s == null)
      return ' ';
    if (s.length() == 0)
      return ' ';
    int pos = 0;
    if (s.charAt(0) == '(') {
      pos = s.indexOf(')') + 1;
      if (pos == 0 || pos == s.length())
        pos = 1;
    }
    try {
      while (pos < s.length() && (!Character.isDigit(s.charAt(pos))))
        pos++;
      if (pos == s.length())
        return ' ';
      return s.charAt(pos);
    } catch (Exception e) {
      e.printStackTrace();
      return ' ';
    }
  }
  NumberFormat nf;
  private Set ukupnoNaslova = new HashSet();
  private Set ukupnoSlikovnica = new HashSet();
  private Map<String, List<Ogranak>> itemMap = new HashMap<>();
  private static Logger log = Logger.getLogger(OpstinskePremaUdk.class);
  private Pattern pDecje1 = Pattern.compile(".*\\(.*\\.053\\.2\\).*");
  private Pattern pDecje2 = Pattern.compile(".*\\(.*\\.053\\.6\\).*");
  private Pattern pDecje3 = Pattern.compile(".*-93");
  private Pattern pDecje4 = Pattern.compile(".*-93-.*");
  private Pattern pattern;



}
