package com.ftninformatika.bisis.bpk;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class NabavkaPoNacinu extends Report {

	 @Override
	  public void init() {
		  itemMap.clear();
		    pattern = Pattern.compile(getReportSettings().getInvnumpattern());
		    log.info("Report initialized.");
	  }

	  @Override
	  public void finish() {
		  log.info("Finishing report...");

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
    
    for (Primerak p : rec.getPrimerci()) {
      
      
      Date datInv = p.getDatumInventarisanja();
      String key = getSettings().getReportName() + getFilenameSuffix(datInv);
      if (getList(key).size()==0){
  	    i=new Item();
   	    getList(key).add(i);	
      } else {
  	    i = getList(key).get(0);
      }

      if (datInv==null){
    	  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
    	  continue;    	  
      }

    String nacinNabavke = p.getNacinNabavke();
      if ( nacinNabavke==null||nacinNabavke.equals("") ){
    	  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 1);
    	  continue;    	  
    }

      String finansijer=p.getFinansijer();
     // String dobavljac=p.getDobavljac();
      
      float cena = 0.0f;
      try {
        cena = p.getCena().floatValue();
      } catch (Exception ex) {
    	  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
    	  continue;
      }
      
      switch (nacinNabavke.charAt(0)) {
      case 'a':
      case 'k':
        // kupovina
        if (finansijer==null||finansijer.equals("")) { //ako nema finansijera stavljam u nepoznato
        	i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);
          break;
        }else{
        finansijer = finansijer.trim(); 
        finansijer=LatCyrUtils.toLatin( finansijer);
        finansijer=finansijer.toLowerCase();
        if (finansijer.startsWith("sop")) {
        	i.add(1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        	
          
        } else if (finansijer.startsWith("bu")) {
        	i.add(0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        	
          
        } else if (finansijer.equalsIgnoreCase("ps")||
        		finansijer.equalsIgnoreCase("p") ||
        		finansijer.startsWith("pok")) {
        	i.add(0, 0, 1, 0, 0, 0, 0, 0, 0, 0,cena, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        	         
        }else{
        	i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0);//nepoznat finansijer- stavljam u nepoznato
        }
        break;
        }
      case 'b':
        // razmena
    	i.add(0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0, 0);      	
        break;
      case 'c':
      case 'o':
      case 'p': //stavljaju sifru o za poklon iako je to otkup
        // poklon
    	  
        if (finansijer==null|| finansijer.equals("")) {
        	i.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);    
          break;
        }else{
            finansijer = finansijer.trim(); 
            finansijer=LatCyrUtils.toLatin( finansijer);
            finansijer=finansijer.toLowerCase();
        if (finansijer.indexOf("min")!=-1) {
        	i.add(0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0, 0, 0, 0);    
         
        } else {
        	i.add(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0, 0, 0); 
          
        }
        break;
        }
      case 'd':
        // obavezni primerak
    	  i.add(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0, 0);         
        break;
       case 'f':
      case 's':
        // sopstvena izdanja
    	  i.add(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, cena, 0, 0, 0, 0, 0);
        break;
      default:
    	  i.add(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
      
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
  public String nvl(String s) {
    return s == null ? "" : s;
  }

  public class Item {
	  
	  int brojPrimeraka_sopstvenimSredstvima;
	  int brojPrimeraka_budzetOpstine;
	  int brojPrimeraka_donacije;
	  int brojPrimeraka_poklonMinistarstva;
	  int brojPrimeraka_poklonOstali;
	  int brojPrimeraka_razmena;
	  int brojPrimeraka_obavezniPrimerak;
	  int brojPrimeraka_sopstvenaIzdanja;
	  float vrednost_sopstvenimSredstvima;
	  float vrednost_budzetOpstine;
	  float vrednost_donacije;
	  float vrednost_poklonMinistarstva;
	  float vrednost_poklonOstali;
	  float vrednost_razmena;
	  float vrednost_obavezniPrimerak;
	  float vrednost_sopstvenaIzdanja;
	  
	  	 
	  int noNacinNabavke;
	  int noFinansijer;
	  int noDobavljac;
	  int noInvDate;
	  int greske;  
	  
	  
	public String invbr;  
    public int brZapisa;
    public int brOdrasli;
    public int brDecje;
    public int brNaucno;
    public int brZavicajno;
    public int brUkupno;
    NumberFormat nf;
    
    
    public Item() {
		super();
		this.brojPrimeraka_sopstvenimSredstvima = 0;
		this.brojPrimeraka_budzetOpstine = 0;
		this.brojPrimeraka_donacije = 0;
		this.brojPrimeraka_poklonMinistarstva = 0;
		this.brojPrimeraka_poklonOstali = 0;
		this.brojPrimeraka_razmena = 0;
		this.brojPrimeraka_obavezniPrimerak = 0;
		this.brojPrimeraka_sopstvenaIzdanja = 0;
		this.vrednost_sopstvenimSredstvima = 0;
		this.vrednost_budzetOpstine = 0;
		this.vrednost_donacije = 0;
		this.vrednost_poklonMinistarstva = 0;
		this.vrednost_poklonOstali = 0;
		this.vrednost_razmena = 0;
		this.vrednost_obavezniPrimerak = 0;
		this.vrednost_sopstvenaIzdanja = 0;

		this.noNacinNabavke = 0;
		this.noFinansijer = 0;
		this.noDobavljac = 0;
		this.noInvDate = 0;
		this.greske = 0;
		
	}
	public void add(int brP_sS, int brP_bO, int brP_d, int brP_pM, int brP_pO,int brP_r,
			int brP_oP,int brP_sI,float vr_sS,float vr_bO,float vr_d, float vr_pM, float vr_pO,
			float vr_r, float vr_oP, float vr_sI, int noNN, int noF, int noD, int noID, int noC) {
		this.brojPrimeraka_sopstvenimSredstvima += brP_sS;
		this.brojPrimeraka_budzetOpstine += brP_bO;
		this.brojPrimeraka_donacije += brP_d;
		this.brojPrimeraka_poklonMinistarstva += brP_pM;
		this.brojPrimeraka_poklonOstali += brP_pO;
		this.brojPrimeraka_razmena += brP_r;
		this.brojPrimeraka_obavezniPrimerak += brP_oP;
		this.brojPrimeraka_sopstvenaIzdanja += brP_sI;
		this.vrednost_sopstvenimSredstvima += vr_sS;
		this.vrednost_budzetOpstine += vr_bO;
		this.vrednost_donacije += vr_d;
		this.vrednost_poklonMinistarstva += vr_pM;
		this.vrednost_poklonOstali += vr_pO;
		this.vrednost_razmena += vr_r;
		this.vrednost_obavezniPrimerak += vr_oP;
		this.vrednost_sopstvenaIzdanja += vr_sI;

		this.noNacinNabavke += noNN;
		this.noFinansijer += noF;
		this.noDobavljac += noD;
		this.noInvDate += noID;
		this.greske += noC;
        
      }
	private int brojPrimeraka_ukupno() {
	    return brojPrimeraka_sopstvenimSredstvima + brojPrimeraka_budzetOpstine +
	      brojPrimeraka_donacije + brojPrimeraka_poklonMinistarstva +
	      brojPrimeraka_poklonOstali + brojPrimeraka_razmena +
	      brojPrimeraka_obavezniPrimerak + brojPrimeraka_sopstvenaIzdanja+noFinansijer+greske;
	  }
	  
	  private float vrednost_ukupno() {
	    return vrednost_sopstvenimSredstvima + vrednost_budzetOpstine +
	      vrednost_donacije + vrednost_poklonMinistarstva +
	      vrednost_poklonOstali + vrednost_razmena +
	      vrednost_obavezniPrimerak + vrednost_sopstvenaIzdanja;
	  }
    public String toString() {
        StringBuffer buf = new StringBuffer();
        
        buf.append("<item>\n");
        buf.append("  <bp_ssr>");
        buf.append(brojPrimeraka_sopstvenimSredstvima);
        buf.append("</bp_ssr>\n");
        buf.append("  <bp_buso>");
        buf.append(brojPrimeraka_budzetOpstine);
        buf.append("</bp_buso>\n");
        buf.append("  <bp_don>");
        buf.append(brojPrimeraka_donacije);
        buf.append("</bp_don>\n");
        buf.append("  <bp_minkul>");
        buf.append(brojPrimeraka_poklonMinistarstva);
        buf.append("</bp_minkul>\n");
        buf.append("  <bp_ost>");
        buf.append(brojPrimeraka_poklonOstali);
        buf.append("</bp_ost>\n");
        buf.append("  <bp_razm>");
        buf.append(brojPrimeraka_razmena);
        buf.append("</bp_razm>\n");
        buf.append("  <bp_obpr>");
        buf.append(brojPrimeraka_obavezniPrimerak);
        buf.append("</bp_obpr>\n");
        buf.append("  <bp_sopiz>");
        buf.append(brojPrimeraka_sopstvenaIzdanja);
        buf.append("</bp_sopiz>\n");
        buf.append("  <bp_ukupno>");
        buf.append(brojPrimeraka_ukupno());
        buf.append("</bp_ukupno>\n");
        buf.append("  <vr_ssr>");
        buf.append(vrednost_sopstvenimSredstvima);
        buf.append("</vr_ssr>\n");
        buf.append("  <vr_buso>");
        buf.append(vrednost_budzetOpstine);
        buf.append("</vr_buso>\n");
        buf.append("  <vr_don>");
        buf.append(vrednost_donacije);
        buf.append("</vr_don>\n");
        buf.append("  <vr_minkul>");
        buf.append(vrednost_poklonMinistarstva);
        buf.append("</vr_minkul>\n");
        buf.append("  <vr_ost>");
        buf.append(vrednost_poklonOstali);
        buf.append("</vr_ost>\n");
        buf.append("  <vr_razm>");
        buf.append(vrednost_razmena);
        buf.append("</vr_razm>\n");
        buf.append("  <vr_obpr>");
        buf.append(vrednost_obavezniPrimerak);
        buf.append("</vr_obpr>\n");
        buf.append("  <vr_sopiz>");
        buf.append(vrednost_sopstvenaIzdanja);
        buf.append("</vr_sopiz>\n");
        buf.append("  <vr_ukupno>");
        buf.append(vrednost_ukupno());
        buf.append("</vr_ukupno>\n");
        buf.append("<noInvDate>");
        buf.append(noInvDate);
        buf.append("</noInvDate>\n");
        buf.append("<noDobavljac>");
        buf.append(noDobavljac);
        buf.append("</noDobavljac>\n");
        buf.append("<greske>");
        buf.append(greske);
        buf.append("</greske>\n");
        buf.append("<noFinansijer>");
        buf.append(noFinansijer);
        buf.append("</noFinansijer>\n");
        buf.append("<noNacinNabavke>");
        buf.append(noNacinNabavke);
        buf.append("</noNacinNabavke>\n");
        buf.append("  </item>\n");
        
        
        
        return buf.toString();
      }
    

    
  }

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  private Pattern pattern;
  private static Log log = LogFactory.getLog(NabavkaPoNacinu.class);
  Item i;
  private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
}
