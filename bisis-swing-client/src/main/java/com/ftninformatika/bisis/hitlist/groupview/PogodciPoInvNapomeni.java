package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class PogodciPoInvNapomeni {

	public static JasperPrint execute(String[] hits, String query) {
		itemMap = new HashMap<String, Item>();
		oldQuery = query;
		int indexBI = query.indexOf("BI:");
		int indexAP = query.indexOf("AP:");
		if (indexAP != -1) {
			odeljenjeQuery = oldQuery.substring(indexAP);
			if (odeljenjeQuery.indexOf(" ")!= -1){
			odeljenjeQuery = odeljenjeQuery.substring(3,odeljenjeQuery.indexOf(" ") );
		    }else{
			   odeljenjeQuery = odeljenjeQuery.substring(3);
		    }
		}
		if(!query.contains("BI:"))
			return null;
		query = query.substring(indexBI);
		int indexS = query.indexOf(" ");
		if (indexS != -1)
			query = query.substring(0, indexS);
		if (query.contains("*")) {
			datefromQuery = query.substring(3).replace("*", ".*");
		} else if (query.contains("?")) {
			datefromQuery = query.substring(3).replace("?", ".*");
		} else {
			datefromQuery = query.substring(3);
		}
		datefromQuery=".*"+datefromQuery+".*";
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
			param.put("query", oldQuery);
			JasperPrint jp = JasperFillManager
					.fillReport(
							PogodciPoInvNapomeni.class
									.getResource(
											"/jaspers/groupview/PogodciPoInvNapomeni.jasper")
									.openStream(), param, dataSource);
			return jp;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static void process(Record rec) {
		List<Primerak> primerci = rec.getPrimerci();
	
		for (Primerak p : primerci) {
			String status = p.getStatus();
			String odeljenje;
			if ((status==null)|| (status.equals(""))){
				status="A";
			}
			if(p.getOdeljenje()!=null){
				odeljenje=p.getOdeljenje();
			}else{
				odeljenje=p.getInvBroj().substring(0, 2);
			}
			if (p.getInventator() != null) {
					if (odeljenjeQuery != null) {
						if (p.getInventator().toLowerCase().matches(datefromQuery)
								&& odeljenje.equals(odeljenjeQuery)) {
							Item item = itemMap.get(status);
							if (item == null) {
								PogodciPoInvNapomeni pog = new PogodciPoInvNapomeni();
								item = pog.new Item(status);
								item.add(1);
								itemMap.put(status, item);
							}else{
								item.add(1);
							}																
							
						}
											
					}else{
						if (p.getInventator().toLowerCase().matches(datefromQuery)) {
							Item item = itemMap.get(status);
							if (item == null) {
								PogodciPoInvNapomeni pog = new PogodciPoInvNapomeni();
								item = pog.new Item(status);
								item.add(1);
								itemMap.put(status, item);
							}else{
								item.add(1);
							}
						} 
						
					}
				}else {  //gledamo sta je u napomeni jer je inventatro =null
					if (p.getNapomene() != null) {
						if (odeljenjeQuery != null) {					
							if (p.getNapomene().toLowerCase().matches(datefromQuery)
									&& odeljenje.equals(odeljenjeQuery)) {
								Item item = itemMap.get(status);
								if (item == null) {
									PogodciPoInvNapomeni pog = new PogodciPoInvNapomeni();
									item = pog.new Item(status);
									item.add(1);
									itemMap.put(status, item);
								}else{
									item.add(1);
								}
							}
							
						}else{
							if (p.getNapomene().toLowerCase().matches(datefromQuery)) {
								Item item = itemMap.get(status);
								if (item == null) {
									PogodciPoInvNapomeni pog = new PogodciPoInvNapomeni();
									item = pog.new Item(status);
									item.add(1);
									itemMap.put(status, item);
								}else{
									item.add(1);
								}
							}
							
						}
					}				
					
		}
	}
  }
	public class Item implements Comparable {
		public String status;

		public int inventar;

		public Item(String sigla) {
			super();
			this.status = sigla;
			this.inventar = 0;
		}

		public int compareTo(Object o) {
			if (o instanceof Item) {
				Item b = (Item) o;
				return status.compareTo(b.status);
			}
			return 0;
		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			buf.append("\n  <item id=\"");
			buf.append(status);
			buf.append("\">\n  <inventar>");
			buf.append(inventar);
			buf.append("</inventar>\n </item>");
			return buf.toString();
		}

		public void add(int inventar) {
			this.inventar += inventar;

		}

	}
	private static int i=0;
	private static String oldQuery;

	private static String datefromQuery;

	private static String odeljenjeQuery;

	private static Map<String, Item> itemMap;

}
