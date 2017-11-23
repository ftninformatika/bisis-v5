package com.ftninformatika.bisis.gbsa;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class StatistikaBazeSerijske extends Report {

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

			}out.append("</report>");
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
		boolean ok = false;
		String ogr = "9999";

		if (rec == null)
			return;
		Item i = null;
		String key;

		ogr = isSameOgranak(rec);
		if (!rec.getGodine().isEmpty()){
			ok=true;
		}
		if(ok){
		for (Field f : rec.getFields("992")) {
			Subfield sf6 = f.getSubfield('6');// broj primeraka
			Subfield sfe = f.getSubfield('e'); // kojoj inventarnoj knjizi
												// pripada
			String type = "";
			String sdate = null;
			int brPrimeraka = 1;
			if (sf6 != null && sf6.getContent() != null && !sf6.getContent().isEmpty()) {
				try {
					brPrimeraka = Integer.parseInt(sf6.getContent().trim());
				} catch (Exception ex) {
					log.warn("Statistika baze serijske:Invalid value in 9926: "
							+ sf6.getContent()
							+ ", ID: "
							+ rec.getRecordID());
				}
			}
			if (f.getSubfield('b') != null) {
				type = f.getSubfield('b').getContent();
			}
			if (f.getSubfield('c') != null) {
				sdate = f.getSubfield('c').getContent();
			}
			Date date = null;
			try {
				date = intern.parse(sdate);
			} catch (Exception ex) {
				log.warn("Statistika baze serijske:Invalid value in f992c: "
						+ sdate + ", ID: " + rec.getRecordID());
				continue;
			}
			key = settings.getReportName() + getFilenameSuffix(date);
			List<Item> list = getList(key);
			
			if (ogr == null) {
				if ((sfe != null) && (!sfe.getContent().isEmpty())) {
					Matcher matcher = pattern.matcher(sfe.getContent()+ "0000000");
					if (!matcher.matches()) {
						continue;

					} else {
						String o=sfe.getContent().substring(0, 2);
						i = getItem(list, o);
						if (i == null) {
							i = new Item(o);
						    if (type.equalsIgnoreCase("re")) {
							  i.rekatalogizovanih = i.rekatalogizovanih + brPrimeraka;
						    } else if (type.equalsIgnoreCase("nv")) {
							  i.novo = i.novo + brPrimeraka;
						    }else{
						    	log.warn("statistika baze serijske: tip obrade je "+type + " ID "+rec.getRecordID());
						    }
						    getList(key).add(i);
						}else{
							if (type.equalsIgnoreCase("re")) {
								  i.rekatalogizovanih = i.rekatalogizovanih + brPrimeraka;
							} else if (type.equalsIgnoreCase("nv")) {
								  i.novo = i.novo + brPrimeraka;
							}
						}
					
					}
				}
			}else{
				String o=ogr.substring(0, 2);
				i = getItem(list, o);
				if (i == null) {
					i = new Item(o);
				    if (type.equalsIgnoreCase("re")) {
					  i.rekatalogizovanih = i.rekatalogizovanih + brPrimeraka;
				    } else if (type.equalsIgnoreCase("nv")) {
					  i.novo = i.novo + brPrimeraka;
				    }
				    getList(key).add(i);
				}else{
					if (type.equalsIgnoreCase("re")) {
						  i.rekatalogizovanih = i.rekatalogizovanih + brPrimeraka;
					} else if (type.equalsIgnoreCase("nv")) {
						  i.novo = i.novo + brPrimeraka;
					}
				}
			}
		
		}
	  }
	}
	

	public String isSameOgranak(Record rec) {
		Iterator<Godina> iter = rec.getGodine().iterator();
		if (iter.hasNext()) {
			Godina g = iter.next();
			String ogr = g.getInvBroj().substring(0, 4);
			Matcher matcher = pattern.matcher(g.getInvBroj());
			if (!matcher.matches())
				return null;
			while (iter.hasNext()) {
				Godina g1 = iter.next();
				String ogr1 = g1.getInvBroj().substring(0, 4);
				if (!ogr1.equalsIgnoreCase(ogr)) {
					return null;
				}
			}
			return ogr;
		}
		return null;
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

	public Item getItem(List<Item> itemsL, String code) {
		for (Item it : itemsL) {
			if (it.odeljenje.equalsIgnoreCase(code)) {
				return it;
			}
		}
		return null;
	}

	public class Item implements Comparable {
		public String odeljenje;
		public int rekatalogizovanih;
		public int novo;

		public Item(String odeljenje) {
			this.odeljenje = odeljenje;
			this.rekatalogizovanih = 0;
			this.novo = 0;

		}

		public void add(Item i) {
			rekatalogizovanih += i.rekatalogizovanih;
			novo += i.novo;

		}

		public String toString() {
			StringBuffer retVal = new StringBuffer();
			retVal.append("<item><odeljenje>");
			retVal.append(getOdeljenje(odeljenje));
			retVal.append("</odeljenje><rekatalog>");
			retVal.append(rekatalogizovanih);
			retVal.append("</rekatalog><novo>");
			retVal.append(novo);
			retVal.append("</novo>");
			retVal.append("</item>");
			return retVal.toString();
		}

		public String getOdeljenje(String o) {
			if (o.equalsIgnoreCase("01"))
				return "\u043e\u0434\u0440\u0430\u0441\u043b\u0438"; // odrasli
			else if (o.equalsIgnoreCase("02"))
				return "\u0434\u0435\u0447\u0458\u0435"; // decje
			else if (o.equalsIgnoreCase("03"))
				return "\u043d\u0430\u0443\u0447\u043d\u043e"; // naucno
			else if (o.equalsIgnoreCase("04"))
				return "\u0437\u0430\u0432\u0438\u0447\u0430\u0458\u043d\u043e"; // zavicajno
			else if (o.equalsIgnoreCase("05"))
				return "\u043e\u043f\u0448\u0442\u0435"; // opste
			else {
				return "\u043d\u0435\u043f\u043e\u0437\u043d\u0430\u0442\u043e"; // nepoznato
			}
		}

		@Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}



	SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
	private Map<String, List<Item>> itemMap = new HashMap<String, List<Item>>();
	private static Log log = LogFactory.getLog(StatistikaBazeSerijske.class);
	private Pattern pattern;

}
