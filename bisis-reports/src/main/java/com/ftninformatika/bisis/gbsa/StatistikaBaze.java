package com.ftninformatika.bisis.gbsa;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StatistikaBaze extends Report {

	@Override
	public void init() {
	//	log.info("Report initialized.");
		pattern = Pattern
				.compile(getReportSettings().getInvnumpattern());
	}

	@Override
	public void finish() {
	//	log.info("Finishing report...");

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
	//	log.info("Report finished.");
	}

	@Override
	public void handleRecord(Record rec) {
		boolean zapis = true;
		boolean rekat=false;
		
		int rekatbr=0;
		if (rec == null)
			return;
		
		Item i = null;
		String key;
		if (rec.getSubfieldContent("001c").compareToIgnoreCase("m")!=0)
				return;
	
		for (Field f : rec.getFields("992")) {
			String type="";
			String sdate=null;
			Subfield sf6 = f.getSubfield('6');// broj primeraka
			Subfield sfb = f.getSubfield('f'); // koje obradio
			if (sfb == null || sfb.getContent() == null)
				continue;
			
			int brPrimeraka = 1;
			if (sf6 != null && sf6.getContent() != null && sf6.getContent().trim().compareToIgnoreCase("")!=0) {
				try {
					brPrimeraka = Integer.parseInt(sf6.getContent().trim());
				} catch (Exception ex) {
					log.warn("Invalid value in f9926: " + sf6.getContent()
							+ ", ID: " + rec.getRecordID());
				}
			}
			if (f.getSubfield('b')!=null){
			    type = f.getSubfield('b').getContent();
			}
			if (f.getSubfield('c')!=null){
			    sdate = f.getSubfield('c').getContent();
			}
			Date date = null;
			try {
				date = intern.parse(sdate);
				if ("re".compareToIgnoreCase(type)==0) {
					rekat=true;
					key = settings.getReportName() + getFilenameSuffix(date);
					List<Item> list = getList(key);
					i = getItem(list, 3); // samo na naucnom rekatologiziraju
					if (i == null) {
						i = new Item(3);
						i.rekatalogizovanih = i.rekatalogizovanih+brPrimeraka;
						//i.primeraka=i.primeraka+brPrimeraka;
						getList(key).add(i);
					}else{
						i.rekatalogizovanih = i.rekatalogizovanih+brPrimeraka;
						//i.primeraka=i.primeraka+brPrimeraka;
					}
				}
			} catch (Exception ex) {
				
			}
		}
		for (Primerak p : rec.getPrimerci()) {
			Matcher matcher = pattern.matcher(p.getInvBroj());
			if (!matcher.matches())
				continue;
			String invBr=p.getInvBroj();
	    	if (invBr == null)
	    		continue;
	    	String ogrS=p.getOdeljenje();
	    	if(ogrS==null){
	    	   ogrS = "9" ;
	    	}
	    	int ogr=Integer.parseInt(ogrS);

			key = settings.getReportName() + getFilenameSuffix(p.getDatumInventarisanja());
			List<Item> list = getList(key);
			i = getItem(list, ogr);
			if (i == null) {
				i = new Item(ogr);
				getList(key).add(i);
			}

			String status = p.getStatus();
			String nabavka = p.getNacinNabavke();

			if ("9".equals(status)) {
				try {
					Date otpisDate = p.getDatumStatusa();
					if (otpisDate == null) {
						String napomene = p.getNapomene();
						if ((napomene != null)
								&& (napomene.toUpperCase().startsWith("R"))) {
							String datum = napomene.substring(1);
							otpisDate = intern.parse(datum);
						}
					}
					if (otpisDate != null) {

						String key1 = settings.getReportName()
								+ getFilenameSuffix(otpisDate);
						if (!key.equals(key1)) {
							List<Item> list1 = getList(key1);
							Item i1 = getItem(list1, ogr);
							if (i1 == null) {
								i1 = new Item(ogr);
								getList(key1).add(i1);
							}
							i1.otpisanih++;
						} else {
							i.otpisanih++;
							if (zapis) {
								i.zapisa++;
								zapis = false;
							}
						}
					}

				} catch (ParseException ex) { // otpadaju primerci jer ne moze
												// da se parsira datum

					if (zapis) {
						i.zapisa++;
						zapis = false;
					}
				}
			} else if ("a".equals(nabavka) || "k".equals(nabavka)) {
				i.kupljenih++;
				i.primeraka++;
			} else if ("c".equals(nabavka) || "p".equals(nabavka)) {
				i.poklonjenih++;
				i.primeraka++;

			} else if ("o".equals(nabavka)) {
				i.izotkupa++;
				i.primeraka++;
			} else {
				i.ostalo++;
				i.primeraka++;
			}
			if (zapis) {
				i.zapisa++;
				zapis = false;
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

	public Item getItem(List<Item> itemsL, int code) {
		for (Item it : itemsL) {
			if (it.odeljenje == code) {
				return it;
			}
		}
		return null;
	}

	public class Item implements Comparable {
		public int odeljenje;
		public int rekatalogizovanih;
		public int otpisanih;
		public int poklonjenih;
		public int kupljenih;
		public int izotkupa;
		public int primeraka;
		public int ostalo;
		public int zapisa;

		public Item(int odeljenje) {
			this.odeljenje = odeljenje;
			this.rekatalogizovanih = 0;
			this.otpisanih = 0;
			this.poklonjenih = 0;
			this.kupljenih = 0;
			this.izotkupa = 0;
			this.primeraka = 0;
			this.zapisa = 0;
			this.ostalo = 0;
		}

		public int compareTo(Object o) {
			Item i = (Item) o;
			if (odeljenje < i.odeljenje)
				return -1;
			else if (odeljenje > i.odeljenje)
				return 1;
			else
				return 0;
		}

		public void add(Item i) {
			rekatalogizovanih += i.rekatalogizovanih;
			otpisanih += i.otpisanih;
			poklonjenih += i.poklonjenih;
			kupljenih += i.kupljenih;
			izotkupa += i.izotkupa;
			primeraka += i.primeraka;
			ostalo += i.ostalo;
			zapisa += i.zapisa; // ovo nema smisla
		}

		public String toString() {
			StringBuffer retVal = new StringBuffer();
			retVal.append("<item><odeljenje>");
			retVal.append(getOdeljenje(odeljenje));
			retVal.append("</odeljenje><rekatalog>");
			retVal.append(rekatalogizovanih);
			retVal.append("</rekatalog><otpisanih>");
			retVal.append(otpisanih);
			retVal.append("</otpisanih><poklonjenih>");
			retVal.append(poklonjenih);
			retVal.append("</poklonjenih><kupljenih>");
			retVal.append(kupljenih);
			retVal.append("</kupljenih><izotkupa>");
			retVal.append(izotkupa);
			retVal.append("</izotkupa><primeraka>");
			retVal.append(primeraka);
			retVal.append("</primeraka><ostalo>");
			retVal.append(ostalo);
			retVal.append("</ostalo><zapisa>");
			retVal.append(zapisa);
			retVal.append("</zapisa></item>");
			return retVal.toString();
		}

		public String getOdeljenje(int o) {
			switch (o) {
			case 1:
				return "\u043e\u0434\u0440\u0430\u0441\u043b\u0438"; // odrasli
			case 2:
				return "\u0434\u0435\u0447\u0458\u0435"; // decje
			case 3:
				return "\u043d\u0430\u0443\u0447\u043d\u043e"; // naucno
			case 4:
				return "\u0437\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u043e"; // zavicajno
			case 5:
				return "\u0423\u041a\u0423\u041f\u041d\u041e"; // UKUPNO
			default:
				return "\u043d\u0435\u043f\u043e\u0437\u043d\u0430\u0442\u043e"; // nepoznato
			}
		}
	}

	SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
	private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	private static Log log = LogFactory.getLog(StatistikaBaze.class);
	private Pattern pattern;

	//@Override
	public void finishInv() {
		// TODO Auto-generated method stub

	}

	//@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub

	}
}
