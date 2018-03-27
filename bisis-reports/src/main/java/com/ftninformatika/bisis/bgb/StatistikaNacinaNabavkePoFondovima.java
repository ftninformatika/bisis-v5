package com.ftninformatika.bisis.bgb;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.reports.Report;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.log4j.Logger;

public class StatistikaNacinaNabavkePoFondovima extends Report {

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
		if (rec == null)
			return;
		String internaOznaka;
		Map<String, List<String>> nabavkePoFondu = new HashMap<String, List<String>>();
		Map<String, List<String>> dodatUNabavku = new HashMap<String, List<String>>();
		for (Primerak p : rec.getPrimerci()) {
			Date datInv = p.getDatumInventarisanja();
			internaOznaka = p.getSigIntOznaka();
			if (internaOznaka == null || internaOznaka.trim().length() == 0) {
				internaOznaka = "";
			}
			String nacinNabavke = p.getNacinNabavke();
			if (nacinNabavke == null || nacinNabavke.trim().length() == 0) {
				nacinNabavke = "";
			}
			String key = settings.getReportName() + getFilenameSuffix(datInv);
			Item item = getItem(getList(key), nacinNabavke);
			if (item != null) {
				SubItem subItem = item.getSubItem(internaOznaka);
				List<String> l = nabavkePoFondu.get(key);
				if (l == null) {
					l = new ArrayList<String>();
					l.add(nacinNabavke + ":" + internaOznaka);
					nabavkePoFondu.put(key, l);
					subItem.add(1, 1);
				} else {
					if (!l.contains(nacinNabavke + ":" + internaOznaka)) {
						l.add(nacinNabavke + ":" + internaOznaka);
						nabavkePoFondu.put(key, l);
						subItem.add(1, 1);
					} else {
						subItem.add(0, 1);
					}
				}

			} else {
				item = new Item(nacinNabavke);
				SubItem subItem = item.getSubItem(internaOznaka);
				List<String> l = nabavkePoFondu.get(key);
				if (l == null) {
					l = new ArrayList<String>();
					l.add(nacinNabavke + ":" + internaOznaka);
					nabavkePoFondu.put(key, l);
					subItem.add(1, 1);
				} else {
					if (!l.contains(nacinNabavke + ":" + internaOznaka)) {
						l.add(nacinNabavke + ":" + internaOznaka);
						nabavkePoFondu.put(key, l);
						subItem.add(1, 1);
					} else {
						subItem.add(0, 1);
					}
				}
				getList(key).add(item);
			}
		    SubItem subIt = item.getSubItem("XXX");
		    List<String>  recAdded=dodatUNabavku.get(key);
			if (recAdded == null) {
				recAdded = new ArrayList<String>();
				recAdded.add(nacinNabavke);
				dodatUNabavku.put(key, recAdded);
				subIt.add(1, 1);
			} else {
				if (!recAdded.contains(nacinNabavke)) {
					recAdded.add(nacinNabavke);
					dodatUNabavku.put(key, recAdded);
					subIt.add(1, 1);
				} else {
					subIt.add(0, 1);
				}
			}
	            
	        }
		}

	private Item getItem(List<Item> il, String nacinNabavke) {
		for (Item item : il) {
			if (item.nacinNab.compareToIgnoreCase(nacinNabavke) == 0) {
				return item;
			}
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

	public class Item implements Comparable {
		public String nacinNab;
		public List subitems;

		public Item(String nacinNab) {
			this.nacinNab = nacinNab;
			subitems = new ArrayList();
		}

		public List getSubItems() {
			return this.subitems;
		}

		public SubItem getSubItem(String internaOznaka) {
			Iterator it = subitems.iterator();
			while (it.hasNext()) {
				SubItem si = (SubItem) it.next();
				if (internaOznaka.equals(si.inv))
					return si;
			}
			SubItem si = new SubItem(internaOznaka);
			subitems.add(si);
			return si;
		}

		public int compareTo(Object o) {
			Item obj = (Item) o;
			return nacinNab.compareTo(obj.nacinNab);
		}

		public String toString() {
			StringBuffer buff = new StringBuffer();
			buff.append("  <item>\n <nacinNab>");
			buff.append(getNameNabavka(nacinNab));
			buff.append("</nacinNab>\n");
			Collections.sort(subitems);
			for (int i = 0; i < subitems.size(); i++) {
				String ib = ((SubItem) subitems.get(i)).inv;
				int brN = ((SubItem) subitems.get(i)).brNas;
				int brP = ((SubItem) subitems.get(i)).brPr;
				buff.append("    <subItem> <inv>");
				buff.append(getNameFond(ib));
				buff.append("</inv>\n  <brNas>");
				buff.append(brN);
				buff.append("</brNas>\n <brPr>");
				buff.append(brP);
				buff.append("</brPr>\n </subItem>");
			}
			buff.append("  </item>\n");
			return buff.toString();
		}
	}

	public class SubItem implements Comparable {
		public String inv;
		public int brNas;
		public int brPr;
		public boolean isTotal;

		public SubItem(String inv) {
			this.inv = inv;
			this.brNas = 0;
			this.brPr = 0;
		}

		public int compareTo(Object o) {
			SubItem si = (SubItem) o;
			return inv.compareTo(si.inv);
		}

		public void add(int brNas, int brPr) {
			this.brNas += brNas;
			this.brPr += brPr;
		}

		public void addItem(SubItem si) {
			brNas += si.brNas;
			brPr += si.brPr;
		}
	}

	public String getNameNabavka(String code) {
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
		else
			return code + " - nepoznato";
	}

	public String getNameFond(String code) {
		if (code == null || code.equals(""))
			return "nerazvrstano";
		if (code.equals("B"))
			return "B - Zavi\u010dajni fond - Beograd";
		if (code.equals("BL"))
			return "BL - Bibliotekarstvo";
		if (code.equals("BLS"))
			return "BLS - Bibliotekarstvo - strana knjiga";
		if (code.equals("BPR"))
			return "BPR - Beogradika - priru\u010dnici";
		if (code.equals("BPS"))
			return "BPS - Beogradika - priru\u010dnici - strana knjiga";
		if (code.equals("BS"))
			return "BS - Beogradika - strana knjiga";
		if (code.equals("D"))
			return "D - De\u010dji fond";
		if (code.equals("DEP"))
			return "DEP - Depozit";
		if (code.equals("DF"))
			return "DF - Dijafilmovi";
		if (code.equals("DP"))
			return "DP - De\u010dje - priru\u010dnici";
		if (code.equals("DPS"))
			return "DPS - De\u010dje - priru\u010dnici - strana knjiga";
		if (code.equals("DR"))
			return "DR - De\u010dje - retka knjiga";
		if (code.equals("DRS"))
			return "DRS - De\u010dje - retka knjiga - strana";
		if (code.equals("DS"))
			return "DS - De\u010dje - strana knjiga";
		if (code.equals("F"))
			return "F - Fotografije";
		if (code.equals("FA"))
			return "FA - Fotografije - zavi\u010dajni fond";
		if (code.equals("FM"))
			return "FM - Film";
		if (code.equals("FMS"))
			return "FMS - Film - strana knjiga";
		if (code.equals("FS"))
			return "FS - Fotografija - strana knjiga";
		if (code.equals("FTI"))
			return "FTI - Fototipska izdanja";
		if (code.equals("G"))
			return "G - Gramofonske plo\u010de";
		if (code.equals("GK"))
			return "GK - Geografske karte";
		if (code.equals("IB"))
			return "IB - Izdavanje - Beograd";
		if (code.equals("IBS"))
			return "IBS - Izdavanje - Beograd - strana knjiga";
		if (code.equals("K"))
			return "K - Kasete";
		if (code.equals("L"))
			return "L - Legat";
		if (code.equals("M"))
			return "M - Muzika - knjige";
		if (code.equals("MF"))
			return "MF - Mikrofilmovi";
		if (code.equals("MS"))
			return "MS - Muzika - strana knjiga";
		if (code.equals("O"))
			return "O - Referalna zbirka";
		if (code.equals("OS"))
			return "OS - Referalna zbirka - strana knjiga";
		if (code.equals("P"))
			return "P - Pozori\u0161te";
		if (code.equals("PBL"))
			return "PBL - Periodika bibliografije";
		if (code.equals("PF"))
			return "PF - Pokretni fond";
		if (code.equals("PFS"))
			return "PFS - Pokretni fond - strana knjiga";
		if (code.equals("PK"))
			return "PK - Plakati";
		if (code.equals("PN"))
			return "PN - Priru\u010dnici - nabavno";
		if (code.equals("PNS"))
			return "PNS - Priru\u010dnici - nabavno - strana knjiga";
		if (code.equals("PPR"))
			return "PPR - Periodika - priru\u010dnici";
		if (code.equals("PPS"))
			return "PPS - Periodika - priru\u010dnici - strana knjiga";
		if (code.equals("PS"))
			return "PS - Pozori\u0161te - strana knjiga";
		if (code.equals("R"))
			return "R - Rukopisi";
		if (code.equals("RI"))
			return "RI - Retka izdanja";
		if (code.equals("RIS"))
			return "RIS - Retka izdanja - strana knjiga";
		if (code.equals("U"))
			return "U - Umetnost";
		if (code.equals("US"))
			return "US - Umetnost - strana knjiga";
		if (code.equals("X"))
			return "X - Op\u0161ti fond";
		if (code.equals("XS"))
			return "XS - Op\u0161ti fond - strana knjiga";
		if (code.equals("\u0160"))
			return "\u0160 - \u0161ah";
		if (code.equals("\u0160S"))
			return "\u0160S - \u0161ah - strana knjiga";
		if (code.equals("XXX"))
			return "UKUPNO";
		else
			return code + " - nepoznato";
	}


	int totalRecords = 0;
	int noInvDate = 0;
	int noInvNum = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	SimpleDateFormat intern = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat intern2 = new SimpleDateFormat("ddMMyy");
	NumberFormat nf;
	private Map<String, List<Item>> itemMap = new HashMap<>();
	private static Logger log = Logger.getLogger(StatistikaNacinaNabavkePoFondovima.class);



}
