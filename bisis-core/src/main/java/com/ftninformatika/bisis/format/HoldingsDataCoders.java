package com.ftninformatika.bisis.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HoldingsDataCoders{
	
  private static Log log = LogFactory.getLog(HoldingsDataCoders.class.getName());
	
	static private List<UItem> sif_Odeljenje = new ArrayList<UItem>(); 
	static private List<UItem> sif_Format = new ArrayList<UItem>();
	static private List<UItem> sif_Status = new ArrayList<UItem>();
	static private List<UItem> sif_Povez = new ArrayList<UItem>();
	static private List<UItem> sif_Podlokacija = new ArrayList<UItem>();
	static private List<UItem> sif_NacinNabavke = new ArrayList<UItem>();
	static private List<UItem> sif_InternaOznaka = new ArrayList<UItem>();
	static private List<UItem> sif_InvKnj = new ArrayList<UItem>();
	static private List<UItem> sif_Dostupnost = new ArrayList<UItem>();
	static private List<UItem> sif_992b = new ArrayList<UItem>();
	static private List<UItem> sif_Librarian = new ArrayList<UItem>();
  
  //zaduzivost statusa
	static private HashMap<String, Integer> zaduzivostStatusa = new HashMap<String,Integer>(); 
	static private String rashodCode = null;
	  
  
  
	public static final int ODELJENJE_CODER = 				0;
	public static final int FORMAT_CODER =    				1;
	public static final int STATUS_CODER =    				2;
	public static final int POVEZ_CODER =     				3;
	public static final int PODLOKACIJA_CODER =   		4;
	public static final int NACINNABAVKE_CODER =  		5;
	public static final int INTERNAOZNAKA_CODER =   	6;
	public static final int INVENTARNAKNJIGA_CODER =	7;
	public static final int DOSTUPNOST_CODER =     		8;
	public static final int _992b_CODER =     		9;
	public static final int LIBRARIAN_CODER =     		10;
	
/*	static{		
		init();
	}*/
  
  public static void loadData(/*Service service*/) throws Exception{
	try{
	/*	ExecuteQueryCommand command = new ExecuteQueryCommand();
		
	    command.setSqlString("SELECT * FROM Odeljenje");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Odeljenje = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM SigFormat");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Format = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Status_Primerka");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Status = createList(command.getResults());
	    loadZaduzivost(command.getResults()); 
	    command.clear(); 
	    
	    command.setSqlString("SELECT * FROM Povez");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Povez = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Podlokacija");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Podlokacija = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Nacin_nabavke");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_NacinNabavke = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Interna_oznaka");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_InternaOznaka = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Invknj");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_InvKnj = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Dostupnost");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Dostupnost = createList(command.getResults());
	    command.clear();
	    
	    command.setSqlString("SELECT * FROM Sifarnik_992b");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_992b = createList(command.getResults());
	    command.clear();
	      
	    command.setSqlString("SELECT username, concat(ime, ' ',prezime) as ime FROM Bibliotekari where obrada =1");
	    command = (ExecuteQueryCommand)service.executeCommand(command);
	    sif_Librarian = createList(command.getResults());
	    command.clear();  */

	} catch (Exception e){
		e.printStackTrace();
	  	 throw new Exception("Gre\u0161ka pri ucitavanju podataka!");
	}   
  }
	
/*	public static void init(){
    Connection conn = BisisApp.getConnection();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM Odeljenje;");
      sif_Odeljenje = createList(rs);     
      rs = stmt.executeQuery("SELECT * FROM SigFormat;");
      sif_Format = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Status_Primerka");
      sif_Status = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Povez");
      sif_Povez = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Podlokacija");
      sif_Podlokacija = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Nacin_nabavke");
      sif_NacinNabavke = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Interna_oznaka");
      sif_InternaOznaka = createList(rs);
      rs = stmt.executeQuery("SELECT * FROM Invknj");
      sif_InvKnj = createList(rs);     
      rs = stmt.executeQuery("SELECT * FROM Dostupnost");
      sif_Dostupnost = createList(rs);     
    } catch (SQLException ex) {      
      log.fatal(ex);
    }   
  }*/
	
	private static List<UItem> createList(ArrayList<ArrayList<Object>> l){
		List<UItem> list = new ArrayList<UItem>();
		UItem ui;
		List<Object> tmp;
		if (l==null)
			return list;
		Iterator<ArrayList<Object>> it = l.iterator();
		while(it.hasNext()){
			tmp = it.next();
			if(tmp.get(1)!=null)
				ui = new UItem(tmp.get(0).toString(),tmp.get(1).toString());
			else
				ui = new UItem(tmp.get(0).toString(),"");
			list.add(ui);
		}
		return list;
	}
	
	/*
	 * vraca vrednost za prosledjeni kod iz prosledjene liste
	 * ili null ako sifre nema u listi
	 */
	private static String getValueFromList(List<UItem> sif_list,String code){
		for(UItem ui:sif_list){
			if(ui.getCode().equals(code)) return ui.getValue();
		}
		return null;
	}
	
	private static boolean containsCode(String code,List<UItem> list){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getCode().equals(code)) return true;
		}
		return false;
	}
	
	private static void loadZaduzivost(ArrayList<ArrayList<Object>> l){
		zaduzivostStatusa.clear();		
		Iterator<ArrayList<Object>> it = l.iterator();
		List<Object> tmp;
		while(it.hasNext()){
			tmp = it.next();
			zaduzivostStatusa.put(tmp.get(0).toString().toLowerCase(),(Integer)tmp.get(2));
			if (((Integer)tmp.get(2)).intValue() == 2){
				rashodCode = tmp.get(0).toString();
			}
		}
	}
	
	
	/*
	 * vraca vrednost za prosledjenu sifru i to iz bilo kog sifarnika
	 * u kom sifarniku ce se traziti sifra zavisi od prvog parametra
	 * u kom se prosledjuje jedna od konstanti definisanih u ovoj klasi 
	 * 
	 */
	public static String getValue(int coder_ref, String code){
		switch(coder_ref){
			case ODELJENJE_CODER: return getValueFromList(sif_Odeljenje, code);
			case FORMAT_CODER: return getValueFromList(sif_Format, code);
			case STATUS_CODER: return getValueFromList(sif_Status, code);
			case POVEZ_CODER: return getValueFromList(sif_Povez, code);
			case PODLOKACIJA_CODER: return getValueFromList(sif_Podlokacija, code);
			case NACINNABAVKE_CODER: return getValueFromList(sif_NacinNabavke, code);
			case INTERNAOZNAKA_CODER: return getValueFromList(sif_InternaOznaka, code);
			case INVENTARNAKNJIGA_CODER: return getValueFromList(sif_InvKnj, code);
			case DOSTUPNOST_CODER: return getValueFromList(sif_Dostupnost, code);
			case _992b_CODER: return getValueFromList(sif_992b, code);
			case LIBRARIAN_CODER: return getValueFromList(sif_Librarian, code);
		}		
		return null;
		
	}
	
	public static List<UItem> getCoder(int coder_ref){
		switch(coder_ref){
			case ODELJENJE_CODER: return sif_Odeljenje;
			case FORMAT_CODER: return sif_Format;
			case STATUS_CODER: return sif_Status;
			case POVEZ_CODER: return sif_Povez;
			case PODLOKACIJA_CODER: return sif_Podlokacija;
			case NACINNABAVKE_CODER: return sif_NacinNabavke;
			case INTERNAOZNAKA_CODER: return sif_InternaOznaka;
			case INVENTARNAKNJIGA_CODER: return sif_InvKnj;
			case DOSTUPNOST_CODER: return sif_Dostupnost;
			case _992b_CODER: return sif_992b;
			case LIBRARIAN_CODER: return sif_Librarian;
		}		
		return null;	
	}
	
	public static int getZaduzivostStatusa(String code){
		return zaduzivostStatusa.get(code.toLowerCase());
	}
	
	public static String getRashodCode(){
		return rashodCode;
	}
	
	public static boolean isValidOdeljenje(String code){
    if(sif_Odeljenje.size()>0)
      return containsCode(code,sif_Odeljenje);
    else return true;
	}
	
	public static boolean isValidFormat(String code){
		return containsCode(code,sif_Format);
	}
	
	public static boolean isValidStatus(String code){
		return containsCode(code,sif_Status);
	}
	
	public static boolean isValidPovez(String code){
		return containsCode(code,sif_Povez);
	}
	
	public static boolean isValidPodlokacija(String code){
		return containsCode(code,sif_Podlokacija);
	}
	
	public static boolean isValidNacinNabavke(String code){
		return containsCode(code,sif_NacinNabavke);
	}
	
	public static boolean isValidInternaOznaka(String code){
		return containsCode(code,sif_InternaOznaka);
	}
  
	public static boolean isValidDostupnost(String code){
		return containsCode(code,sif_Dostupnost);
	}
	
	public static boolean isValid992b(String code){
		return containsCode(code,sif_992b);
	}
	
	public static boolean isValidLibrarian(String code){
		return containsCode(code,sif_Librarian);
	}
}
