package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class PogodciPoDatumuInventara {

	public static JasperPrint execute(String[] hits, String query) {
		itemMap = new HashMap<String, Item>();
		if(query.startsWith("DA:")){
			if(query.contains("*")){
				datefromQuery=query.substring(3).replace("*", "");
			}else if(query.contains("?")){
				datefromQuery=query.substring(3).replace("?", "");
			}else{
			datefromQuery=query.substring(3);
			}
			
		}

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
			param.put("datum", datefromQuery);
			if(query.startsWith("DA:")){
				param.put("query", datefromQuery);
			}
			JasperPrint jp = JasperFillManager
					.fillReport(
							PogodciPoDatumuInventara.class
									.getResource(
											"/jaspers/groupview/BranchesPoDatumu.jasper")
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
			String invbr = p.getInvBroj();
			String status = p.getStatus();
			if (status == null)
				status = "A";
			if (!status.equals("") && !status.equals("A")
					&& !status.equals("5")) { // samo aktivne i preusmerene
				continue;
			}
			String branchID=p.getOdeljenje();
		      if((branchID==null)||(branchID.equals(""))){
		    	  branchID=invbr.substring(0,2);
		      }
			Item item = itemMap.get(branchID);
			if (item == null) {
				PogodciPoDatumuInventara pog=new PogodciPoDatumuInventara();
				item =  pog.new Item(branchID);
				itemMap.put(branchID, item);
			}
			item.add(1, 0);
			if(p.getDatumInventarisanja()!=null){		
              String datInv=sdf.format(p.getDatumInventarisanja());
            if(datInv.startsWith(datefromQuery)){
            	item.add(0, 1);
            }
			}
			
		}
	}

	public class Item implements Comparable {
		public String sigla;

		public int inventar;

		public int primerak;

		public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.inventar = 0;
			this.primerak = 0;
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
			buf.append("\">\n  <primerak>");
			buf.append(primerak);
			buf.append("</primerak>\n	<inventar>");
			buf.append(inventar);
			buf.append("</inventar>\n    </item>");
			return buf.toString();
		}

		public void add(int primerak, int inventar) {
			this.primerak += primerak;
			this.inventar += inventar;

		}

	}
	private static String datefromQuery;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Map<String, Item> itemMap;

}
