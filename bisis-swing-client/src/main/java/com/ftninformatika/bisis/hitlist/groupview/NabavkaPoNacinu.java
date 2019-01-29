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

public class NabavkaPoNacinu {

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
							NabavkaPoNacinu.class
									.getResource(
											"/jaspers/groupview/NabavkaPoNacinu.jasper")
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
			String nacinNab = p.getNacinNabavke();
			if (nacinNab == null || nacinNab.equals("")) {
				nacinNab = "k";
			}
			Item item = itemMap.get(branchID);
			char t = nacinNab.charAt(0);

			if (item == null) {
				NabavkaPoNacinu nab=new NabavkaPoNacinu();
				item =  nab.new Item(branchID);
				itemMap.put(branchID, item);
			}

			switch (t) {
			case 'a': // kupovina
			case 'k':
				item.add(1, 0, 0, 0, 0, 0, 0,1);
				break;
			case 'c': // donacije
				item.add(0, 1, 0, 0, 0, 0, 0,1);
				break;
			case 'p': // poklon
				item.add(0, 0, 1, 0, 0, 0, 0,1);
				break;
			case 'o': // otkup
				item.add(0, 0, 0, 1, 0, 0, 0,1);
				break;
			case 'b': // razmena
				item.add(0, 0, 0, 0, 1, 0, 0,1);
				break;
			case 'f': // samostalno izdanje
			case 's':
				item.add(0, 0, 0, 0, 0, 1, 0,1);
				break;
			case 'e': // zateceni fond
				item.add(0, 0, 0, 0, 0, 0, 1,1);
				break;

			}
		}
	}

	public class Item implements Comparable {
		public String sigla;
		public int kupovina;
		public int donacija;
		public int poklon;
		public int otkup;
	    public int razmena;
		public int samizdat;
		public int zateceno;
		public int primerak;
		public Item(String sigla) {
			super();
			this.sigla = sigla;
			this.kupovina = 0;
			this.donacija = 0;
			this.poklon = 0;
			this.otkup = 0;
			this.razmena = 0;
			this.samizdat = 0;
			this.zateceno = 0;
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
			buf.append("\">\n  <ogranak>");
			buf.append(BisisApp.appConfig.getCodersHelper().getValue(
					CodersHelper.ODELJENJE_CODER, sigla));
			buf.append("</ogranak>\n	<kupovina>");
			buf.append(kupovina);
			buf.append("</kupovina>\n    <poklon>");
			buf.append(poklon);
			buf.append("</poklon>\n    <donacija>");
			buf.append(donacija);
			buf.append("</donacija>\n    <otkup>");
			buf.append(otkup);
			buf.append("</otkup>\n    <razmena>");
			buf.append(razmena);
			buf.append("</razmena>\n    <samizdat>");
			buf.append(samizdat);
			buf.append("</samizdat>\n    <zateceno>");
			buf.append(zateceno);
			buf.append("</zateceno>\n     <primerak>");
			buf.append(primerak);
			buf.append("</primerak>\n     </item>");
			return buf.toString();
		}

		public void add(int kupovina, int donacija, int poklon, int otkup,
				int razmena, int samizdat, int zateceno,int primerak) {
			this.kupovina += kupovina;
			this.donacija += donacija;
			this.poklon += poklon;
			this.otkup += otkup;
			this.razmena += razmena;
			this.samizdat += samizdat;
			this.zateceno += zateceno;
			this.primerak += primerak;
		}

		public void addItem(Item i) {
			this.kupovina += i.kupovina;
			this.donacija += i.donacija;
			this.poklon += i.poklon;
			this.otkup += i.otkup;
			this.razmena += i.razmena;
			this.samizdat += i.samizdat;
			this.zateceno += i.zateceno;
		}

	}

	private static Map<String, Item> itemMap;
}
