package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.util.*;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.string.StringUtils;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class PogodciPoPeriodu {



		public static JasperPrint execute(String[] hits, String query) {
			itemMap = new HashMap<String, Item>();
			try {
					for (int i = 0; i < hits.length; i++) {
					process(BisisApp.getRecordManager().getRecord(hits[i]));
					}
			} catch (IOException e) {
					e.printStackTrace();
				}
			StringBuffer buff = new StringBuffer();
			buff.append("<report>\n");
			List keys = new ArrayList();
			keys.addAll(itemMap.keySet());
			Collections.sort(keys);
			Iterator branchIDs = keys.iterator();
			while (branchIDs.hasNext()) {
				String key = (String) branchIDs.next();
				Item item = (Item) itemMap.get(key);
				buff.append(item.toString());
			}
			buff.append("</report>\n");
			try {
				JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils.getDocumentFromString(buff.toString()), "/report/item");
				Map param = new HashMap();
				param.put("query", query);
				JasperPrint jp = JasperFillManager
						.fillReport(
								PogodciPoPeriodu.class
										.getResource(
												"/jaspers/groupview/PogodciPoPeriodu.jasper")
										.openStream(), param, dataSource);
				return jp;

			} catch (Exception ex) {
				return null;
			}
		}

		private static void process(Record rec) {
			if (rec == null)
				return;
			String period = StringUtils.clearDelimiters(rec.getSubfieldContent("100c"), delims).trim();
			if (period==null || period.length()<1)
				return;
			if (isDigit(period))
			period=period.substring(0,period.length()-1);  //po dekadama
			for (Primerak p : rec.getPrimerci()) {
				String invbr = p.getInvBroj();
				if (invbr == null || invbr.equals("")) {
					continue;
				}
				String status = p.getStatus();
				if (status == null)
					status = "A";
				if (!status.equals("") && !status.equals("A")
						&& !status.equals("5")) { // samo aktivne i preusmerene
					continue;
				}
				String branchID = p.getOdeljenje();
				if ((branchID == null) || (branchID.equals(""))) {
					branchID = invbr.substring(0, 2);
				}
				Item item = itemMap.get(period);
				if (item == null) {
					PogodciPoPeriodu nab = new PogodciPoPeriodu();
					item = nab.new Item(period);
					item.add(branchID);
					itemMap.put(period, item);
				} else {
					item.add(branchID);
				}

			}

		}
     private static boolean isDigit(String broj){
	   try{
		int br=Integer.parseInt(broj);
		return true;
	   }catch(Exception e ){
		return false;
	 }
}
		public class Item implements Comparable {
			public String period;

			public int odeljenje01;

			public int odeljenje02;

			public int odeljenje03;

			public int odeljenje04;

			public int odeljenje05;

			public int odeljenje06;

			public int odeljenje07;

			public int odeljenje08;

			public int odeljenje09;

			public int odeljenje10;

			public int odeljenje11;

			public int odeljenje13;

			public int odeljenje14;

			public int odeljenje16;

			public int odeljenje17;

			public int odeljenje18;

			public int odeljenje19;

			public int odeljenje20;

			public int odeljenje21;

			public int odeljenje22;

			public int odeljenje23;

			public int odeljenje25;

			public int odeljenje26;

			public int odeljenje27;

			public int odeljenje28;

			public int odeljenje31;

			public Item(String period) {
				super();
				this.period = period;
				odeljenje01 = 0;
				odeljenje02 = 0;
				odeljenje03 = 0;
				odeljenje04 = 0;
				odeljenje05 = 0;
				odeljenje06 = 0;
				odeljenje07 = 0;
				odeljenje08 = 0;
				odeljenje09 = 0;
				odeljenje10 = 0;
				odeljenje11 = 0;
				odeljenje13 = 0;
				odeljenje14 = 0;
				odeljenje16 = 0;
				odeljenje17 = 0;
				odeljenje18 = 0;
				odeljenje19 = 0;
				odeljenje20 = 0;
				odeljenje21 = 0;
				odeljenje22 = 0;
				odeljenje23 = 0;
				odeljenje25 = 0;
				odeljenje26 = 0;
				odeljenje27 = 0;
				odeljenje28 = 0;
				odeljenje31 = 0;

			}

			public int hashCode() {
				return period.hashCode();
			}

			public int total() {
				return odeljenje01 + odeljenje02 + odeljenje03 + odeljenje04
						+ odeljenje05 + odeljenje06 + odeljenje07 + odeljenje08
						+ odeljenje09 + odeljenje10 + odeljenje11 + odeljenje13
						+ odeljenje14 + odeljenje16 + odeljenje17 + odeljenje18
						+ odeljenje19 + odeljenje20 + odeljenje21 + odeljenje22
						+ odeljenje23 + odeljenje25 + odeljenje26 + odeljenje27
						+ odeljenje28 + odeljenje31;
			}

			public int compareTo(Object o) {
				Item obj = (Item) o;
				return period.compareTo(obj.period);
			}

			public void add(String sigla) {
				switch (Integer.parseInt(sigla)) {
				case 1:
					odeljenje01++;
					break;
				case 2:
					odeljenje02++;
					break;
				case 3:
					odeljenje03++;
					break;
				case 4:
					odeljenje04++;
					break;
				case 5:
					odeljenje05++;
					break;
				case 6:
					odeljenje06++;
					break;
				case 7:
					odeljenje07++;
					break;
				case 8:
					odeljenje08++;
					break;
				case 9:
					odeljenje09++;
					break;
				case 10:
					odeljenje10++;
					break;
				case 11:
					odeljenje11++;
					break;
				case 13:
					odeljenje13++;
					break;
				case 14:
					odeljenje14++;
					break;
				case 16:
					odeljenje16++;
					break;
				case 17:
					odeljenje17++;
					break;
				case 18:
					odeljenje18++;
					break;
				case 19:
					odeljenje19++;
					break;
				case 20:
					odeljenje20++;
					break;
				case 21:
					odeljenje21++;
					break;
				case 22:
					odeljenje22++;
					break;
				case 23:
					odeljenje23++;
					break;
				case 25:
					odeljenje25++;
					break;
				case 26:
					odeljenje26++;
					break;
				case 27:
					odeljenje27++;
					break;
				case 28:
					odeljenje28++;
					break;
				case 31:
					odeljenje31++;
					break;
				}
			}

			public String toString() {
				StringBuffer buff = new StringBuffer();
				buff.append("  <item id=\"");
				buff.append(period+"*");
				buff.append("\">\n");
				buff.append("<odeljenje01>");
				buff.append(odeljenje01);
				buff.append("</odeljenje01>\n");
				buff.append("<odeljenje02>");
				buff.append(odeljenje02);
				buff.append("</odeljenje02>\n");
				buff.append("<odeljenje03>");
				buff.append(odeljenje03);
				buff.append("</odeljenje03>\n");
				buff.append("<odeljenje04>");
				buff.append(odeljenje04);
				buff.append("</odeljenje04>\n");
				buff.append("<odeljenje05>");
				buff.append(odeljenje05);
				buff.append("</odeljenje05>\n");
				buff.append("<odeljenje06>");
				buff.append(odeljenje06);
				buff.append("</odeljenje06>\n");
				buff.append("<odeljenje07>");
				buff.append(odeljenje07);
				buff.append("</odeljenje07>\n");
				buff.append("<odeljenje08>");
				buff.append(odeljenje08);
				buff.append("</odeljenje08>\n");
				buff.append("<odeljenje09>");
				buff.append(odeljenje09);
				buff.append("</odeljenje09>\n");
				buff.append("<odeljenje10>");
				buff.append(odeljenje10);
				buff.append("</odeljenje10>\n");
				buff.append("<odeljenje11>");
				buff.append(odeljenje11);
				buff.append("</odeljenje11>\n");
				buff.append("<odeljenje13>");
				buff.append(odeljenje13);
				buff.append("</odeljenje13>\n");
				buff.append("<odeljenje14>");
				buff.append(odeljenje14);
				buff.append("</odeljenje14>\n");
				buff.append("<odeljenje16>");
				buff.append(odeljenje16);
				buff.append("</odeljenje16>\n");
				buff.append("<odeljenje17>");
				buff.append(odeljenje17);
				buff.append("</odeljenje17>\n");
				buff.append("<odeljenje18>");
				buff.append(odeljenje18);
				buff.append("</odeljenje18>\n");
				buff.append("<odeljenje19>");
				buff.append(odeljenje19);
				buff.append("</odeljenje19>\n");
				buff.append("<odeljenje20>");
				buff.append(odeljenje20);
				buff.append("</odeljenje20>\n");
				buff.append("<odeljenje21>");
				buff.append(odeljenje21);
				buff.append("</odeljenje21>\n");
				buff.append("<odeljenje22>");
				buff.append(odeljenje22);
				buff.append("</odeljenje22>\n");
				buff.append("<odeljenje23>");
				buff.append(odeljenje23);
				buff.append("</odeljenje23>\n");
				buff.append("<odeljenje25>");
				buff.append(odeljenje25);
				buff.append("</odeljenje25>\n");
				buff.append("<odeljenje26>");
				buff.append(odeljenje26);
				buff.append("</odeljenje26>\n");
				buff.append("<odeljenje27>");
				buff.append(odeljenje27);
				buff.append("</odeljenje27>\n");
				buff.append("<odeljenje28>");
				buff.append(odeljenje28);
				buff.append("</odeljenje28>\n");
				buff.append("<odeljenje31>");
				buff.append(odeljenje31);
				buff.append("</odeljenje31>\n");
				buff.append("<ukupno>");
				buff.append(total());
				buff.append("</ukupno>\n");
				buff.append("  </item>\n");

				return buff.toString();
			}
		}
		private static String delims = ",;:\"()[]{}+/.!?*- " ;
		private static Map<String, Item> itemMap;
	}

