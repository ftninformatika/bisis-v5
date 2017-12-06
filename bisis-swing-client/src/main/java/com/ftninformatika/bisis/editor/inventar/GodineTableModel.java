package com.ftninformatika.bisis.editor.inventar;

import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.utils.string.Signature;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class GodineTableModel extends AbstractTableModel {
  
  private String[] godineAll;
  private String[] columns;
  private String[] columnSet;
  
  private static Log log = LogFactory.getLog(GodineTableModel.class.getName());
  
  public GodineTableModel(){
    super();
    godineAll = new String[17];
    godineAll[0] = Messages.getString("INV_NUM");
    godineAll[1] = Messages.getString("INV_DATE");
    godineAll[2] = Messages.getString("SIGNATURE");
    godineAll[3] = Messages.getString("LOCATION");
    godineAll[4] = Messages.getString("BILL_NUMBER");
    godineAll[5] = Messages.getString("BILL_DATE");
    godineAll[6] = Messages.getString("PRICE");
    godineAll[7] = Messages.getString("ACQ");
    godineAll[8] = Messages.getString("FINANCIER");
    godineAll[9] = Messages.getString("BINDING");
    godineAll[10] = Messages.getString("EDITOR_ACQ_TYPE");
    godineAll[11] = Messages.getString("NOTES");
    godineAll[12] = Messages.getString("YEARING");
    godineAll[13] = Messages.getString("YEAR");
    godineAll[14] = Messages.getString("NUMBER");
    godineAll[15] = Messages.getString("AVAILABILITY");
    godineAll[16] = Messages.getString("INVENTOR");
    String columnSetStr = BisisApp.appConfig.getClientConfig().getCataloguingGodineModel();
    columnSet = columnSetStr.split(" ");
    columns = new String[columnSet.length];
    for(int i=0;i<columnSet.length;i++){
      columns[i] = godineAll[Integer.parseInt(columnSet[i])];      
    }   
  }  

  public int getColumnCount() {   
    return columns.length;
  }
  
  public String getColumnName(int column){
    return columns[column];
  }

  public int getRowCount() {   
    return CurrRecord.brojGodina();
  }
  
  public Godina getRow(int rowIndex){
    return CurrRecord.getGodina(rowIndex);
    
  }

  public Object getValueAt(int rowIndex, int columnIndex) {  
    Godina g = CurrRecord.getGodina(rowIndex);
    return getValueForColumn(g,Integer.parseInt(columnSet[columnIndex]));    
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
  
  public void initializeModel(){
    this.fireTableDataChanged();
  }
  
  
  public void updateGodina(Godina g, boolean changeInvBr) throws InventarniBrojException{
//  identifikacija godina se vrsi preko inventarnog broja
    // ako nema inventarnog broja generise se nova godina
    String invBroj = g.getInvBroj();   
    boolean found = false;
    int index = -1;
    int i = 0;
    boolean hasInvBroj = g.getInvBroj()==null || !g.getInvBroj().endsWith("##");
    if(hasInvBroj){
      while(!found && i<CurrRecord.brojGodina()){
        if(CurrRecord.getGodina(i).getInvBroj()!=null && CurrRecord.getGodina(i).getInvBroj().equals(invBroj)){
          found = true;
          index = i;
        }else i++;
      }
      if(found){
      	if(changeInvBr && !InventarValidation.validateInvBrojUnique(g.getInvBroj()).equals("")){
        throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(g.getInvBroj()));
      	}
      	 g.setGodinaID(CurrRecord.record.getGodine().get(index).getGodinaID());
        CurrRecord.record.getGodine().set(index,g);
      }else{
        // provera jedinstvenosti inventarnog broja
        if(!InventarValidation.validateInvBrojUnique(g.getInvBroj()).equals(""))
          throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(g.getInvBroj()));
        for(Sveska s:g.getSveske()){
        	if(!InventarValidation.validateInvBrojUnique(s.getInvBroj()).equals(""))
        		throw new InventarniBrojException(Messages.getString("YEAR_CAN_NOT_BE_SAVED"));
        }
        CurrRecord.addGodina(g);
      }      
    }else{
      throw new InventarniBrojException(Messages.getString("MUST_ENTER_INV_NUM"));
    }
    fireTableDataChanged();
  }  
  
  /*
   * 9.9.2008. Za napomene i inventatora se dodaje vrednost
   */
  public void updateGodine(int[]rows, Godina g){
    for(int i=0;i<rows.length;i++){
      Godina currg = CurrRecord.getGodina(rows[i]);
      if(g.getNacinNabavke()!=null) currg.setNacinNabavke(g.getNacinNabavke());
      if(g.getPovez()!=null) currg.setPovez(g.getPovez());     
      if(g.getBrojRacuna()!=null) currg.setBrojRacuna(g.getBrojRacuna());
      if(g.getDatumRacuna()!=null) currg.setDatumRacuna(g.getDatumRacuna());
      if(g.getFinansijer()!=null) currg.setFinansijer(g.getFinansijer());
      if(g.getDobavljac()!=null) currg.setDobavljac(g.getDobavljac());
      if(g.getCena()!=null) currg.setCena(g.getCena());
      if(g.getSigUDK()!=null) currg.setSigUDK(g.getSigUDK());
      if(g.getSigPodlokacija()!=null) currg.setSigPodlokacija(g.getSigPodlokacija());
      if(g.getSigFormat()!=null) currg.setSigFormat(g.getSigFormat());
      if(g.getSigIntOznaka()!=null) currg.setSigIntOznaka(g.getSigIntOznaka());
      if(g.getSigDublet()!=null) currg.setSigDublet(g.getSigDublet());
      if(g.getSigNumerusCurens()!=null) currg.setSigNumerusCurens(g.getSigNumerusCurens());
      if(g.getDatumInventarisanja()!=null) currg.setDatumInventarisanja(g.getDatumInventarisanja());   
      if(g.getNapomene()!=null){
      	if(currg.getNapomene()==null)      		
      		currg.setNapomene(g.getNapomene());
      	else
      		currg.setNapomene(currg.getNapomene()+g.getNapomene());
      }
      if(g.getGodina()!=null) currg.setGodina(g.getGodina());
      if(g.getGodiste()!=null) currg.setGodiste(g.getGodiste());
      if(g.getBroj()!=null) currg.setBroj(g.getBroj());
      if(g.getDostupnost()!=null) currg.setDostupnost(g.getDostupnost());
      if(g.getInventator()!=null){
      	if(currg.getInventator()==null)
      		currg.setInventator(g.getInventator());
      	else
      		currg.setInventator(currg.getInventator()+g.getInventator());
      }
    }
    this.fireTableDataChanged();
  }
  
  public void deleteRow(int row){
    CurrRecord.removeGodina(row);
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
 			colIndex == getColumnIndex(Messages.getString("STATUS")) ||
 			colIndex == getColumnIndex(Messages.getString("LOCATION")) ||
 			colIndex == getColumnIndex(Messages.getString("EDITOR_ACQ_TYPE")) ||
 			colIndex == getColumnIndex(Messages.getString("BINDING")) ||
 			colIndex == getColumnIndex(Messages.getString("AVAILABILITY"));
 			
 	}
}
