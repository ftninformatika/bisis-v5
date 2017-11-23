package com.ftninformatika.bisis.gbsa;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.util.HashMap;


import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SabacFix {

	private static Connection conn;
	private static Log log = LogFactory.getLog(SabacFix.class);
	static HashMap <String,String> bibliotekari=new HashMap<String,String>();
	public static void main(String[] args) {
		if(args.length != 3){
			System.out.println("backup: <address/database>  <user> <password> ");
		  return;
		}
		String address=args[0];
		String user=args[1];
		String password=args[2];
		run(address,user,password);

	}
	
	public static void run(String databaseURL, String username, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+databaseURL,username,password);
			//HoldingsDataCodersJdbc.loadData(conn);
			initHashMap();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement stmt = null;
		ResultSet rset = null;
		int recId;
		try {
			log.info("Opening database");
            
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
		              ResultSet.CONCUR_READ_ONLY);
			rset = stmt
					.executeQuery("SELECT creator, modifier, date_created, "
							+ "date_modified, pub_type, content, record_id FROM Records");

			log.info("Initializing reports");
			//TODO - pretabati za MongoDB
			/*DBStorage storage = new DBStorage();
			log.info("Fetching records...");
			int count = 0;
			Record newRec;
			while (rset.next()) {
				count++;
				Record rec = null;
				recId = rset.getInt("record_id");
				try {
					rec = storage.get(conn, recId);
					if (rec == null)
						continue;
					
					newRec= handleRecord(rec);
					if (newRec !=null){
					     storage.update(conn, newRec);
					}
				} catch (Exception ex) {
					log.warn("Problem with loading record ID: "
							+ rec.getRecordID());
					log.warn(ex);
					continue;
				}
				if (count % 1000 == 0) {
					System.out.println("Records processed: " + count);
				}	
			}
			log.info("Closing database");
			if (rset != null)
				rset.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();


			log.info("Done with " + count + " records.");*/
		} catch (Exception ex) {
			ex.printStackTrace();
			log.fatal(ex);
			ex.printStackTrace();
		}
	}
	
	public static Record handleRecord(Record rec){
		
		   Subfield sfc=new Subfield('c');
		   Subfield sff=new Subfield('f');
		   String bibliotekar;
		   boolean update=false;
		    for (Field f : rec.getFields("992")) {
		      Subfield sfb = f.getSubfield('b'); //koje obradio
		      if (sfb == null || sfb.getContent() == null || sfb.getContent().length()!=12)
		        continue;
		      update=true;
		      String temp=sfb.getContent();
		      String type = temp.substring(0, 2).toLowerCase();
		      String obr = temp.substring(2, 4).toUpperCase();
		      String sdate = temp.substring(4,12);
              sfb.setContent(type);
		      sfc.setContent(sdate);
		      obr= LatCyrUtils.toLatin(obr).toUpperCase().trim();
		      bibliotekar=bibliotekari.get(obr);
		      if (bibliotekar!=null){
		    	 
		         sff.setContent(bibliotekar);
		      }else{
		    	  System.out.println(obr + "- "+ rec.getRN());
		    	 sff.setContent(obr);
		      }
		      f.removeSubfield('c');
		      f.removeSubfield('f');
		      f.add(sfc);
		      f.add(sff);
		     
		      }
		if (!update){
			return null;
		}else{
		  return rec;
		}
	}
	
	private static void initHashMap(){
		bibliotekari.put("VA", "vesna");
		bibliotekari.put("KA", "kristina");
		bibliotekari.put("DL", "jara");
		bibliotekari.put("LD", "jara");
		bibliotekari.put("DV", "duska");
		bibliotekari.put("SM", "sanja");
		bibliotekari.put("NC", "Nebojsa");
		bibliotekari.put("ST", "trkulja");
		bibliotekari.put("JP", "Jelena");
		bibliotekari.put("DZ", "Draga");
		bibliotekari.put("NR", "nada");
		bibliotekari.put("AP", "Ana");
	}

}
