package com.ftninformatika.bisis.bs;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.apache.log4j.Logger;


public class StanjeFondaPeriodika extends Report {

	@Override
	public void init() {
		nf = NumberFormat.getInstance(Locale.GERMANY);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		itemMap.clear();
		pattern = Pattern
				.compile(getReportSettings().getInvnumpattern());
		// log.info("Report initialized.");
	}

	public void finishInv() {

	}

	@Override
	public void finish() {
		// log.info("Finishing report...");
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
		// log.info("Report finished.");
	}

	public List<Item> getList(String key) {
		List<Item> list = itemMap.get(key);
		if (list == null) {
			list = new ArrayList<Item>();
			itemMap.put(key, list);
		}
		return list;
	}

	@Override
	public void handleRecord(Record rec) {
		if (rec == null)
			return;

		String key = settings.getReportName();

		Iterator<Godina> iter = rec.getGodine().iterator();
		String ogr, ogr1 = null;
		boolean ok = false;
		
		//razvrstam godine po ograncima
		while (iter.hasNext()) {
			Godina g = iter.next();
			String invBr = g.getInvBroj();
			if (invBr == null)
				continue;
			Matcher matcher = pattern.matcher(g.getInvBroj());
			if (!matcher.matches())
				continue;

			ogr1 = invBr.substring(0, 4);
			ok = true;
			try {
				key = settings.getReportName();
				Item item = getItem(getList(key), ogr1);
				if (item == null) {
					item = new Item(ogr1);
					item.godista++;
					getList(key).add(item);
				} else {
					item.godista++;

				}
			} catch (Exception e) {

			}
		}

		//razvrstam sveske po ograncima, ima smisla uci u for samo ako je rec o serijskim publikacima zato je ok=true
		if (ok) {
 
			for (Field f : rec.getFields("992")) {
				Subfield sf6 = f.getSubfield('6');// broj svesaka
				Subfield sfb = f.getSubfield('b');// oznaka
				Subfield sfe = f.getSubfield('e'); // kojoj inventarnoj knjizi
													// pripada

				int brPrimeraka = 1;
				if (sf6 != null && sf6.getContent() != null&& !sf6.getContent().isEmpty()) {
					try {
						brPrimeraka = Integer.parseInt(sf6.getContent().trim());
					} catch (Exception ex) {
						log.warn("stanje fonda serijske:Invalid value in f9926: "
								+ sf6.getContent()
								+ ", ID: "
								+ rec.getRecordID());
					}
				}
				if ((sfe != null) && (!sfe.getContent().isEmpty())) {
					Matcher matcher = pattern.matcher(sfe.getContent()+ "0000000");
					if (!matcher.matches()) {
						continue;

					} else {
						ogr = sfe.getContent();

						Item item = getItem(getList(key), ogr);
						if (item == null) {
							item = new Item(ogr);
							item.primerci = item.primerci + brPrimeraka;
							getList(key).add(item);
						} else {
							item.primerci = item.primerci + brPrimeraka;

						}
					}
				} else {
					if (ogr1 != null) {
						Item item = getItem(getList(key), ogr1);
						if (item == null) {
							item = new Item(ogr1);
							item.primerci = item.primerci + brPrimeraka;
							getList(key).add(item);
						} else {
							item.primerci = item.primerci + brPrimeraka;

						}
					} else {
                       log.warn("Stanje fonda periodike: godina koja ne odgovara regularnom izrazu "+ rec.getRecordID());
					}
				}
			}
	//razvrstam zapise po ograncima		
			String ogr2 = isSameOgranak(rec); 
			if (ogr2 != null) { 
				Matcher matcher =pattern.matcher(ogr2 + "0000000"); 
				if (matcher.matches()) { 
					Item item =getItem(getList(key), ogr2); 
					if (item == null) { 
					  item = new Item(ogr2);
					  item.zapisi++; 
					  getList(key).add(item); 
					} else { 
					   item.zapisi++;  
					} 
				} 
			}else{
				//ovde spadaju zapisi koji pripadaju i vise ogranaka i ne znam kako da ih racunam
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

	public String nvl(String s) {
		return s == null ? "" : s;
	}

	public Item getItem(List<Item> iteml, String sigla) {

		for (Item it : iteml) {

			if (it.sigla.compareToIgnoreCase(sigla) == 0) {
				return it;
			}
		}
		return null;

	}

	public class Item implements Comparable {
		String sigla;
		int primerci;
		int zapisi;
		int godista;

		public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.primerci = 0;
			this.zapisi = 0;
			this.godista = 0;
		}

		public int compareTo(Object o) {
			if (o instanceof Item) {
				Item b = (Item) o;
				return sigla.compareTo(b.sigla);
			}
			return 0;
		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append("\n  <item> \n <sigla>");
			buf.append(sigla);
			buf.append("</sigla>\n");
			buf.append("<ogranak>");
			buf.append(sigla);
			buf.append("</ogranak>\n    <primerci>");
			buf.append(primerci);
			buf.append("</primerci>\n   <zapisi>");
			buf.append(zapisi);
			buf.append("</zapisi>\n 	  <godista>");
			buf.append(godista);
			buf.append("</godista>\n 	  </item>");
			return buf.toString();
		}

	}

	SimpleDateFormat intern = new SimpleDateFormat("yyyy");

	private Pattern pattern;
	private Map<String, List<Item>> itemMap = new HashMap<>();
	private static Logger log = Logger.getLogger(StanjeFondaPeriodika.class);
	NumberFormat nf;

	//@Override
	public void finishOnline(StringBuffer buff) {
		// TODO Auto-generated method stub

	}
}
