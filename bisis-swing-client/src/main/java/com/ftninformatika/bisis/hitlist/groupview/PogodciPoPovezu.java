package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.admin.coders.CodersHelper;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

	public class PogodciPoPovezu{

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
					JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
							.getDocumentFromString(buff.toString()), "/report/item");
					Map param = new HashMap();
					param.put("query", query);
					JasperPrint jp = JasperFillManager
							.fillReport(
									PogodciPoPovezu.class
											.getResource(
													"/jaspers/groupview/PogodciPoPovezu.jasper")
											.openStream(), param, dataSource);
					return jp;

				} catch (Exception ex) {
					return null;
				}
			}


			private static void process(Record rec) {
			for (Primerak p : rec.getPrimerci()) {
				String invbr = p.getInvBroj();
				if (invbr == null || invbr.equals("")) {
					continue;
				}
				String status=p.getStatus();
			      if (status==null)
			    	  status="A";
				if (!status.equals("")&& !status.equals("A") && !status.equals("5")){ // samo aktivne i preusmerene
			    	  continue;
			      }
				 String branchID=p.getOdeljenje();
			      if((branchID==null)||(branchID.equals(""))){
			    	  branchID=invbr.substring(0,2);
			      }
				String povez = p.getPovez();
				if (povez == null || povez.equals("")) {
					povez = "o";
				}
				povez=povez.toLowerCase();
				Item item = itemMap.get(branchID);
				char t = povez.charAt(0);

				if (item == null) {
					PogodciPoPovezu nab=new PogodciPoPovezu();
					item =  nab.new Item(branchID);
					itemMap.put(branchID, item);
				}

				switch (t) {

				case 'm':
					item.add(1, 1, 0, 0);
					break;
				case 't': 
					item.add(1, 0, 1,0);
					break;
				case 'o': 
					item.add(1, 0, 0, 1);
					break;
				

				}
			}
		}

		public class Item implements Comparable {
			public String sigla;
			public int primerak;
			public int meki;
			public int tvrdi;
			public int ostalo;
			public Item(String sigla) {
				super();
				this.sigla = sigla;
				this.primerak = 0;
				this.meki = 0;
				this.tvrdi = 0;
				this.ostalo = 0;
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
				buf.append("\n  <item id=\"");
				buf.append(sigla);
				buf.append("\">\n  <ogranak>");
				buf.append(BisisApp.appConfig.getCodersHelper().getValue(
						CodersHelper.ODELJENJE_CODER, sigla));
				buf.append("</ogranak>\n	<primerak>");
				buf.append(primerak);
				buf.append("</primerak>\n    <tvrdi>");
				buf.append(tvrdi);
				buf.append("</tvrdi>\n    <meki>");
				buf.append(meki);
				buf.append("</meki>\n    <ostalo>");
				buf.append(ostalo);
				buf.append("</ostalo>\n  </item>");
				return buf.toString();
			}

			public void add(int primerak, int meki, int tvrdi, int ostalo) {
				this.primerak += primerak;
				this.tvrdi += tvrdi;
				this.meki += meki;
				this.ostalo += ostalo;
			}

			public void addItem(Item i) {
				this.primerak += i.primerak;
				this.tvrdi += i.tvrdi;
				this.meki += i.meki;
				this.ostalo += i.ostalo;
			}

		}

		private static Map<String, Item> itemMap;
	}

