package com.ftninformatika.bisis.circ.warnings;

import javax.swing.table.AbstractTableModel;

import warning.RootDocument;

public class WarningTableModel extends AbstractTableModel {
	
	private RootDocument doc = null;
	private int[] check = null;
	private static final int COL_COUNT = 5;
	private String[] columnNames = {"\u0160tampa se", "Broj \u010dlana", "Prezime", "Ime", "Broj publikacija"};
	
	public WarningTableModel(RootDocument doc){
		this.doc = doc;
		check = new int[doc.getRoot().getOpomenaArray().length];
		for (int i = 0; i < check.length; i++){
			check[i] = 1;
		}
	}
	
	public WarningTableModel(){
		
	}
	
	public void setDoc(RootDocument doc){
		if (doc != null){
			this.doc = doc;
			check = new int[doc.getRoot().getOpomenaArray().length];
			for (int i = 0; i < check.length; i++){
				check[i] = 1;
			}
			fireTableDataChanged();
		}
	}
	
	public void resetModel(){
		doc = null;
		check = null;
		fireTableDataChanged();
	}
	
	public boolean printOrNot(int i){
		if (check[i] == 1)
			return true;
		else
			return false;
	}
	
	public int getColumnCount(){
		return COL_COUNT;
	}
	
	public int getRowCount(){
		if (doc == null){
			return 0;
		}else{
			return doc.getRoot().getOpomenaArray().length;
		}
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public Object getValueAt(int row, int col){
		
		switch (col){
		 case 0: if (check[row] == 1){
					 return new Boolean(true);
				 }else{
					 return new Boolean(false);
				 }
		 case 1: return doc.getRoot().getOpomenaArray(row).getPodaci().getUserid();
		 case 2: return doc.getRoot().getOpomenaArray(row).getPodaci().getPrezime();
		 case 3: return doc.getRoot().getOpomenaArray(row).getPodaci().getIme();
		 case 4: return new Integer(doc.getRoot().getOpomenaArray(row).getBody().getTabelaArray().length);
		 default: return null;
		}
		
	}
	
	public Class getColumnClass(int col) {
		if (col == 0){
			return Boolean.class;
		}else if (col == 4){
			return Integer.class;
		}else {
			return String.class;
		}
    }
	
	public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }
	
	public void setValueAt(Object value, int row, int col) {
		if (col == 0){
			if (((Boolean)value).booleanValue() == true){
				check[row] = 1;
			}else{
				check[row] = 0;
			}
			fireTableCellUpdated(row, col);
		}
	}

}
