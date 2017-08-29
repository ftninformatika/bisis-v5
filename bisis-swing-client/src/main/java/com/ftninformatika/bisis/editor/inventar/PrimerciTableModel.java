/**
 * 
 */
package com.ftninformatika.bisis.editor.inventar;

import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.utils.string.Signature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


/**
 * @author Bojana
 *   
 */
public class PrimerciTableModel extends AbstractTableModel {

		private String[] primerakAll;
  private String[] columns;
  private String[] columnSet;
  
  private static Log log = LogFactory.getLog(PrimerciTableModel.class.getName());
	
	
	public PrimerciTableModel() {
		super();
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
    String columnSetStr = BisisApp.appConfig.getClientConfig().getCatalougingPrimerciModel();
    columnSet = columnSetStr.split(" ");
    columns = new String[columnSet.length];
    for(int i=0;i<columnSet.length;i++){
      columns[i] = primerakAll[Integer.parseInt(columnSet[i])];      
    }    
	}

	
	public int getRowCount() {		
		return CurrRecord.brojPrimeraka();
	}
	
	public int getColumnCount() {		
		return columns.length;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Primerak p = CurrRecord.getPrimerak(rowIndex);
    return getValueForColumn(p,Integer.parseInt(columnSet[columnIndex]));		
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
  
	public void updatePrimerak(Primerak p, boolean changeInvBr) throws InventarniBrojException{
		// identifikacija primerka se vrsi preko inventarnog broja
		// ako nema inventarnog broja generise se novi primerak		
		String invBroj = p.getInvBroj();		
		boolean found = false;
		int index = -1;
		int i = 0;	
		while(!found && i<CurrRecord.brojPrimeraka()){
				if(CurrRecord.getPrimerak(i).getInvBroj().substring(2)
						.equals(invBroj.substring(2))){
					found = true;
					index = i;
				}else i++;
			}
		
		if(found){
			if(changeInvBr && !InventarValidation.validateInvBrojUnique(p.getInvBroj()).equals("")){
		        throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(p.getInvBroj()));
			}
			p.setPrimerakID(CurrRecord.record.getPrimerci().get(index).getPrimerakID());
			p.setStanje(CurrRecord.record.getPrimerci().get(index).getStanje());
			CurrRecord.record.getPrimerci().set(index,p);				
		}	
		else{
			//provera jedinstvenosti inventarnog broja
			if(!InventarValidation.validateInvBrojUnique(p.getInvBroj()).equals("")){
		        throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(p.getInvBroj()));
			}
			p.setStanje(0);
			CurrRecord.addPrimerak(p);


           }
		fireTableDataChanged();   
	}
  
  public void deleteRow(int row){   
     CurrRecord.removePrimerak(row);
     fireTableDataChanged();     
  }
	
	public Primerak getRow(int rowIndex){
		return CurrRecord.getPrimerak(rowIndex);
	}	
	
	public void initializeModel(){
		this.fireTableDataChanged();
	}
  
  /*menja vrednosti u primercima ciji redni brojevi 
  		se nalaze u nizu rows, a tako da oni imaju sve podatke
  		iz primerka p
	 		9.9.2008. 
	 		za napomene i inventatira se string dodaje na postojeci string
	 		u tim poljima
	*/
  public void updatePrimerci(int[]rows, Primerak p){
    for(int i=0;i<rows.length;i++){
      Primerak currP = CurrRecord.getPrimerak(rows[i]);
      if(p.getNacinNabavke()!=null) currP.setNacinNabavke(p.getNacinNabavke());
      if(p.getPovez()!=null) currP.setPovez(p.getPovez());     
      if(p.getBrojRacuna()!=null) currP.setBrojRacuna(p.getBrojRacuna());
      if(p.getDatumRacuna()!=null) currP.setDatumRacuna(p.getDatumRacuna());
      if(p.getFinansijer()!=null) currP.setFinansijer(p.getFinansijer());
      if(p.getDobavljac()!=null) currP.setDobavljac(p.getDobavljac());
      if(p.getCena()!=null) currP.setCena(p.getCena());
      if(p.getSigUDK()!=null) currP.setSigUDK(p.getSigUDK());
      if(p.getSigPodlokacija()!=null) currP.setSigPodlokacija(p.getSigPodlokacija());
      if(p.getSigFormat()!=null) currP.setSigFormat(p.getSigFormat());
      if(p.getSigIntOznaka()!=null) currP.setSigIntOznaka(p.getSigIntOznaka());
      if(p.getSigNumerusCurens()!=null) currP.setSigNumerusCurens(p.getSigNumerusCurens());
      if(p.getSigDublet()!=null) currP.setSigDublet(p.getSigDublet());
      if(p.getDatumInventarisanja()!=null) currP.setDatumInventarisanja(p.getDatumInventarisanja());     
      if(p.getStatus()!=null) currP.setStatus(p.getStatus());
      if(p.getNapomene()!=null){
      	if(currP.getNapomene()==null)
      		currP.setNapomene(p.getNapomene());
      	else
      		currP.setNapomene(currP.getNapomene()+p.getNapomene());
      }
      if(p.getDostupnost()!=null) currP.setDostupnost(p.getDostupnost());      
      if(p.getInventator()!=null){
      	if(currP.getInventator()==null)
      		currP.setInventator(p.getInventator());
      	else
      		currP.setInventator(currP.getInventator()+p.getInventator());
      }
      if(p.getDatumStatusa()!=null) currP.setDatumStatusa(p.getDatumStatusa());
      if(p.getUsmeravanje()!=null) currP.setUsmeravanje(p.getUsmeravanje());
    }
    fireTableDataChanged();    
  }
  
  public int getColumnIndex(String columnName){ 		
 		for(int i=0;i<columns.length;i++){				
 				if(columns[i].equals(columnName)) return i;
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
