package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

public class CountFields {



		public static JasperPrint execute(String[] hits, String query,String fields) {
			itemMap = new HashMap<String, Item>();
		    fieldList=fields.split(",");
			for (int i = 0; i < hits.length; i++) {
				try {
					process(BisisApp.getRecordManager().getRecord(hits[i]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			StringBuffer buff = new StringBuffer();
			buff.append("<report>\n");
			List keys = new ArrayList();
			keys.addAll(itemMap.keySet());
			Collections.sort(keys);
			Iterator fieldIDs = keys.iterator();
			while (fieldIDs.hasNext()) {
				String key = (String) fieldIDs.next();
				Item item = (Item) itemMap.get(key);
				buff.append(item.toString());
			}
			buff.append("\n</report>\n");
			try {
				JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
						.getDocumentFromString(buff.toString()), "/report/item");
				Map param = new HashMap();
				param.put("query", query);
				JasperPrint jp = JasperFillManager
						.fillReport(
								CountFields.class
										.getResource(
												"/jaspers/groupview/CountFields.jasper")
										.openStream(), param, dataSource);
				return jp;

			} catch (Exception ex) {
				return null;
			}
		}

	  
		private static void process(Record rec) {
			for (int j=0;j<fieldList.length;j++) {
				Item item = itemMap.get(fieldList[j]);
				if (item == null) {
					
					item =  cfl.new Item(fieldList[j]);
					itemMap.put(fieldList[j], item);
				}
				List<Field> fields= rec.getFields(fieldList[j]);
				for(int k=0;k<fields.size();k++){
					fields.get(k).pack();
					if (fields.get(k).getSubfieldCount()>0){
						item.add(1);
					}
				}
				

			}
		}

		public class Item implements Comparable {
			public String name;
			public int count;
			public Item(String name) {
				super();
				this.name = name;
				this.count = 0;
			}

			public int compareTo(Object o) {
				if (o instanceof Item) {
					Item b = (Item) o;
					return name.compareTo(b.name);
				}
				return 0;
			}

			public String toString() {
				StringBuffer buf = new StringBuffer();
				buf.append("\n  <item id=\"");
				buf.append(name);
				buf.append("\">\n  <count>");
				buf.append(count);
				buf.append("</count>\n</item>");
				return buf.toString();
			}

			public void add(int count) {
				this.count=this.count+count;

			}

		}

		private static Map<String, Item> itemMap;
		private static String fieldList[];
		private static CountFields cfl=new CountFields();
	}

