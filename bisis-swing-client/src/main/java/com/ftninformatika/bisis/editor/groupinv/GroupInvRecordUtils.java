package com.ftninformatika.bisis.editor.groupinv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import org.apache.lucene.search.MatchAllDocsQuery;


public class GroupInvRecordUtils {
	
	//private static List<Record> updatedRecords = new ArrayList<Record>(); 
	private static List<String> titles = new ArrayList<String>();
	
	public static Record getRecordForInv(String invBroj){
		Record retVal = null;
		try {
			retVal = BisisApp.bisisService.getRecordByCtlgNo(invBroj).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
			retVal = null;
		}
		return retVal;
	}
	
	public static List<Record> getRecordsForInvs(List<String> invList){
		List<Record> retVal = new ArrayList<>();

		for(String in: invList){
			Record r = getRecordForInv(in);
			retVal.add(r);
		}
		return retVal;
	}
	

	
	/*
	 * pronalazi zapis sa inventarnim brojem invBroj
	 * stavlja ga u listu zapisa za azuriranje - updatedRecords
	 * a vraca konkretan primerak, godinu ili svesku
	 * na koji se odnosi prosledjeni inventarni broj 
	 */
	public static Object loadItem(String invBroj){
		Record rec = getRecordForInv(invBroj);
		if(rec==null) return null;
		if(rec.getSubfieldContent("200a")!=null)
			titles.add(rec.getSubfieldContent("200a"));
		else
			titles.add("");
		if(rec!=null){		
				//updatedRecords.add(rec);
			for(Primerak p:rec.getPrimerci())
				if(p.getInvBroj().equals(invBroj))				
					return p;
			for(Godina g:rec.getGodine()){
				if(g.getInvBroj().equals(invBroj))
					return g;
				else
					for(Sveska s:g.getSveske())
						if(s.getInvBroj().equals(invBroj))
							return s;				
			}
		}
		return null;			
	}
	
/*
	public static Record getRecord(int index){
		return updatedRecords.get(index);
	}
*/	
	
	public static String getTitle(int index){
		return titles.get(index);
	}
	
	public static void clearTitles(){
		titles.clear();
	}
	
	
	
	
	
	

}
