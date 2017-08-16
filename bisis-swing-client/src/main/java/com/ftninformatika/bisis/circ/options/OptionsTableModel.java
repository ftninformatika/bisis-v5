package com.ftninformatika.bisis.circ.options;

import javax.swing.table.AbstractTableModel;

import options.OptionsDocument;
import options.OptionsDocument.Options.Client;

public class OptionsTableModel extends AbstractTableModel {
	
	private OptionsDocument doc = null;
	private static final int COL_COUNT = 2;
	private String[] columnNames = {"Mac adresa", "Napomena"};
	
	public OptionsTableModel(OptionsDocument doc){
		this.doc = doc;
	}
	
	public OptionsTableModel(){
		
	}
	
	public void setDoc(OptionsDocument doc){
		if (doc != null){
			this.doc = doc;
			fireTableDataChanged();
		}
	}
	
	public void resetModel(){
		doc = null;
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return COL_COUNT;
	}
	
	public int getRowCount(){
		if (doc == null){
			return 0;
		}else{
			return doc.getOptions().getClientArray().length;
		}
	}
	
	public String getColumnName(int col) {
     return columnNames[col];
  }
	
	public Object getValueAt(int row, int col){
		switch (col){
		 case 0: return doc.getOptions().getClientArray(row).getMac();
		 case 1: return doc.getOptions().getClientArray(row).getNote();
		 default: return null;
		}
	}
  
  public Client getClient(int row){
    return doc.getOptions().getClientArray(row);
  }
  
  public void removeRow(int row){
    doc.getOptions().removeClient(row);
    fireTableRowsDeleted(row, row);
  }
  
  public void addRow(){
    
  }
	
	public Class getColumnClass(int col) {
	  return String.class;
  }
	
	public boolean isCellEditable(int row, int col) {   
     return false;
  }
	
	public void setValueAt(Object value, int row, int col) {
	}

}
