package com.ftninformatika.bisis.hitlist;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.inventar.InventarConstraints;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.string.Signature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Bojana
 * model za tablu koja se prikazuje prilikom 
 * pregleda rezultata pretrage
 * 
 * model je jedinstven i za godine i za primerke
 * ali se na osnovu zapisa record proverava o kom
 * tipu inventara se radi
 * 
 * tabela ce iamti 5 kolona i to one koje se inace
 * nalaze na pocetku tabele za inventar u obradi
 * odnosno, prvih 5 kolona koje su navedene 
 * u konfiguracionom fajlu u kategoriji
 * 
 * cataloguing/primerciModel i godineModel
 * 
 *  
 */


public class InventarTabTableModel extends AbstractTableModel {
	
		private Record record;
		
		//da li zapis ima primerke ili godine
		// ako je true ima primerke inace godine
		private boolean isMonograph = true;
		
		
		private String[] primerakAll;
		private String[] godineAll;
		
		private String[] columnsPrimerci;
		private String[] columnsGodine;
  private String[] columnSetPrimerci;
  private String[] columnSetGodine;
	
  private static final int MAX_COLUMN_COUNT = 8;
  
  private int primerciColumnCount;
  private int godineColumnCount;
  
  private static Log log = LogFactory.getLog(InventarTabTableModel.class.getName());
  
	public InventarTabTableModel(){
		super();
		// opsta tabela kolona za primerke
		primerakAll = new String[17];
    primerakAll[0] = "Inventarni broj";
    primerakAll[1] = "Datum inventarisanja";
    primerakAll[2] = "Status";
    primerakAll[3] = "Signatura";
    primerakAll[4] = "Odeljenje";
    primerakAll[5] = "Broj ra\u010duna";
    primerakAll[6] = "Datum ra\u010duna";
    primerakAll[7] = "Cena";
    primerakAll[8] = "Dobavlja\u010d";
    primerakAll[9] = "Finansijer";    
    primerakAll[10] = "Povez";
    primerakAll[11] = "Na\u010din nabavke";       
    primerakAll[12] = "Napomene";
    primerakAll[13] = "Dostupnost";
    primerakAll[14] = "Inventator";
    primerakAll[15] = "Datum statusa";
    primerakAll[16] = "Usmeravanje";
    
    //opsta tabela kolona za godine
    godineAll = new String[17];
    godineAll[0] = "Inventarni broj";
    godineAll[1] = "Datum inventarisanja";
    godineAll[2] = "Signatura";
    godineAll[3] = "Odeljenje";
    godineAll[4] = "Broj ra\u010duna";
    godineAll[5] = "Datum ra\u010duna";
    godineAll[6] = "Cena";
    godineAll[7] = "Dobavlja\u010d";
    godineAll[8] = "Finansijer";
    godineAll[9] = "Povez";
    godineAll[10] = "Na\u010din nabavke";
    godineAll[11] = "Napomene";
    godineAll[12] = "Godi\u0161te";
    godineAll[13] = "Godina";
    godineAll[14] = "Broj";
    godineAll[15] = "Dostupnost";
    godineAll[16] = "Inventator";
    
    //kolone za primerke
    String columnSetStrPrimerci = BisisApp.appConfig.getClientConfig().getPrimerciModel();
    columnSetPrimerci = columnSetStrPrimerci.split(" ");
    if(columnSetPrimerci.length<MAX_COLUMN_COUNT)
    	primerciColumnCount = columnSetPrimerci.length;
    else
    	primerciColumnCount = MAX_COLUMN_COUNT;
    
    columnsPrimerci = new String[primerciColumnCount];
    for(int i=0;i<primerciColumnCount;i++){
      columnsPrimerci[i] = primerakAll[Integer.parseInt(columnSetPrimerci[i])];      
    }		
        
    //kolone za godine
    String columnSetStrGodine = BisisApp.appConfig.getClientConfig().getCatalougingGodineModel();
    columnSetGodine = columnSetStrGodine.split(" ");
    if(columnSetGodine.length<MAX_COLUMN_COUNT)
    	godineColumnCount=columnSetGodine.length;
    else
    	godineColumnCount = MAX_COLUMN_COUNT;
    
    columnsGodine = new String[godineColumnCount];
    for(int i=0;i<godineColumnCount;i++){
      columnsGodine[i] = godineAll[Integer.parseInt(columnSetGodine[i])];      
    }  
    
	}
	
	private void setIsMonograph(){
		if((record.getGodine()!=null && record.getGodine().size()>0) 
    		|| record.getPubType()==2)
    	isMonograph = false;
		else
			isMonograph = true;
	}	

	public int getColumnCount() {
		if(isMonograph)
			return primerciColumnCount;
		else
			return godineColumnCount;
	}

	public int getRowCount() {
		if(record==null) return 0;
		if(isMonograph)
			return record.getPrimerci().size();
		else
			return record.getGodine().size();	
	}
	
	public String getColumnName(int column){
		 if(isMonograph)
			 return columnsPrimerci[column];
		 else
			 return columnsGodine[column];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(isMonograph){
			Primerak p = record.getPrimerci().get(rowIndex);
			return getValueForColumn(p,Integer.parseInt(columnSetPrimerci[columnIndex]));
		}else{			
			Godina g = record.getGodine().get(rowIndex);
			return getValueForColumn(g, Integer.parseInt(columnSetGodine[columnIndex]));
		}	
	}
	
	public Object getValueForColumn(Godina g, int columnIndex) {   
		 try{      
	      if(g!=null){
	        switch(columnIndex){
	        case 0: return g.getInvBroj(); 
	        case 1: if(g.getDatumInventarisanja()!=null)
	              return InventarConstraints.sdf.format(g.getDatumInventarisanja());
	            else return "";        
	        case 2: return Signature.format(g);
	        case 3: return g.getOdeljenje();
	        case 4: return g.getBrojRacuna();     
	        case 5: if(g.getDatumRacuna()!=null)
	              return InventarConstraints.sdf.format(g.getDatumRacuna());
	            else
	              return "";    
	        case 6:  return g.getCena();
	        case 7:  return g.getDobavljac();
	        case 8:  return g.getFinansijer();
	        case 9:  return g.getPovez();
	        case 10: return g.getNacinNabavke();
	        case 11: return g.getNapomene();
	        case 12: return g.getGodiste();
	        case 13: return g.getGodina();
	        case 14: return g.getBroj();
	        case 15: return g.getDostupnost();
	        case 16: return g.getInventator();
	        }     
	      }
	    }catch(Exception ex){
	      log.fatal(ex);
	    }
	    return null;    
	}
	
	public Object getValueForColumn(Primerak p, int columnIndex) {   
		try{
      if(p!=null){
        switch(columnIndex){
        case 0: return p.getInvBroj(); 
        case 1: if(p.getDatumInventarisanja()!=null)
              return InventarConstraints.sdf.format(p.getDatumInventarisanja());
            else return "";
        case 2: return p.getStatus();
        case 3: return Signature.format(p);     
        case 4: return p.getOdeljenje();
        case 5: return p.getBrojRacuna();     
        case 6: if(p.getDatumRacuna()!=null)
              return InventarConstraints.sdf.format(p.getDatumRacuna());
            else
              return "";    
        case 7:  return p.getCena();
        case 8:  return p.getDobavljac();
        case 9:  return p.getFinansijer();
        case 10: return p.getPovez();
        case 11: return p.getNacinNabavke();
        case 12: return p.getNapomene();  
        case 13: return p.getDostupnost();
        case 14: return p.getInventator();
        case 15: return p.getDatumStatusa();
        case 16: return p.getUsmeravanje();
        }     
      } 
    }catch(Exception ex){
      log.fatal(ex);
    }
    return null;
	  }	
	
	public void setRecord(Record rec){
		record = rec;
		setIsMonograph();
		fireTableStructureChanged();	
		fireTableChanged(new TableModelEvent(this));				
	}	
	
	public int getColumnIndex(String columnName){
		if(isMonograph)
			for(int i=0;i<columnsPrimerci.length;i++){				
				if(columnsPrimerci[i].equals(columnName)) return i;
			}
		else
			for(int i=0;i<columnsGodine.length;i++){				
				if(columnsGodine[i].equals(columnName)) return i;
			}		
		return -1;			
	}
	
	public boolean isSifriranaKolona(int colIndex){
		return
			colIndex == getColumnIndex("Status") ||
			colIndex == getColumnIndex("Odeljenje") ||
			colIndex == getColumnIndex("Na\u010din nabavke") ||
			colIndex == getColumnIndex("Povez") ||
			colIndex == getColumnIndex("Dostupnost");
			
	}
}
