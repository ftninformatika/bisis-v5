package com.ftninformatika.bisis.editor.inventar;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.editor.recordtree.RecordUtils;
import com.ftninformatika.bisis.records.Sveska;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SveskeTableModel extends AbstractTableModel {
  
 // private Godina godina;
  
  private List<Sveska> sveske;
  
  private String[] columns; 
  
  
  private static Log log = LogFactory.getLog(SveskeTableModel.class.getName());

  public SveskeTableModel(List<Sveska> sveske) {
    this.sveske = sveske;
    columns = new String[7];
    columns[0] = "Inventarni broj";
    columns[1] = "Broj sveske";        
    columns[2] = "Knjiga";
    columns[3] = "Status";
    columns[4] = "Datum statusa";
    columns[5] = "Inventator";
    columns[6] = "Cena";
  }  
  

  public int getColumnCount() {  
    return columns.length;
  }
  
  public String getColumnName(int column){
    return columns[column];
  }

  public int getRowCount() {
    if(sveske==null)return 0;
      return sveske.size();    
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    if(sveske==null) return null;
    Sveska s = sveske.get(rowIndex);
    switch(columnIndex){
      case 0: return s.getInvBroj();
      case 1: return s.getBrojSveske();
      case 2: return s.getKnjiga();
      case 3: return s.getStatus();
      case 4: if(s.getDatumStatusa()!=null)
        				return InventarConstraints.sdf.format(s.getDatumStatusa());
      				else
      					return "";    
      case 5: return s.getInventator();
      case 6: return s.getCena();
      
      
    }    
    return null;
  }

  public List<Sveska> getSveske() {
    return sveske;
  }

  public void setSveske(List<Sveska> sveske) {  	
    this.sveske = sveske;    
    fireTableDataChanged();
  }
  
  public Sveska getRow(int i){
    return sveske.get(i);
  }
  
  public void updateSveska(Sveska s) throws InventarniBrojException{  
    // identifikacija sveske se vrsi preko 
    // ako nema inventarnog broja generise se nova sveska
    String invBroj = s.getInvBroj();    
    boolean found = false;
    int index = -1;
    int i = 0;    
    while(!found && i<sveske.size()){
        if(sveske.get(i).getInvBroj().equals(invBroj)){
          found = true;
          index = i;
        }else i++;
      }
      if(found){
      	s.setSveskaID(sveske.get(index).getSveskaID());
      	s.setStanje(sveske.get(index).getStanje());
       sveske.set(index,s);
      }else{
        //provera jedinstvenosti inventarnog broja
      		//moze postojati inv broj u nekoj drugoj godini u ovom 
      		// zapisu koji jos nije sacuvan
      		if(RecordUtils.invBrojSveskePostojiUZapisu(s.getInvBroj()))
      			throw new InventarniBrojException("Dupli inventarni broj (tekuci zapis)!");      	
        if(!InventarValidation.validateInvBrojUnique(s.getInvBroj()).equals(""))
          throw new InventarniBrojException(InventarValidation.validateInvBrojUnique(s.getInvBroj()));
        s.setStanje(0);
        sveske.add(s);        
      } 
   
    fireTableDataChanged();
  }
  
  public void updateSveske(int[] rows, Sveska s){
    for(int i:rows){    
      Sveska currSveska = sveske.get(i);
      if(s.getBrojSveske()!=null) currSveska.setBrojSveske(s.getBrojSveske());
      if(s.getCena()!=null) currSveska.setCena(s.getCena());
      if(s.getStatus()!=null) currSveska.setStatus(s.getStatus());
      if(s.getKnjiga()!=null) currSveska.setKnjiga(s.getKnjiga());      
    }
    fireTableDataChanged();
  }
  
  public void deleteRow(int row){
    if(sveske.size()>0 && row>-1)     
        sveske.remove(row);   
    fireTableDataChanged();
  }
  
  public boolean isCellEditable(int row, int col) {
  	return col>3;
  }
  
  public void setValueAt(Object value, int row, int col) {
  	Sveska s = sveske.get(row);
  	String str = (String)value;
  	switch(col){
  	 /*case 0: s.setInvBroj(str); break;
     case 1: s.setBrojSveske(str); break;
     case 2: s.setKnjiga(str); break;*/
  	 case 4: 
  		 try {
						s.setDatumStatusa(InventarConstraints.sdf.parse(str));
  		 } catch (ParseException e) {				
			 } 
  		 break;
  	 case 5: s.setInventator(str);break;
     case 6:
    	 try{    
    		 s.setCena(new BigDecimal(str));
    	 }catch(NumberFormatException e){    		 
    	 }break;
     
  	}  	  	
    fireTableCellUpdated(row, col);
  }
  
  



}
