package com.ftninformatika.bisis.circ.warnings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;



public class HistoryTableModel extends AbstractTableModel {
	
	private static final int COL_COUNT = 9;
	private String[] columnNames = {"Broj opomene","Tip opomene", "Datum slanja", "Rok vra\u0107anja", "Broj \u010dlana", "Prezime i Ime", "Inventarni broj", "Datum vra\u0107anja", "Napomena"};
	private List<Object[]> data= null;
		
	
	public HistoryTableModel(){
		data = new ArrayList<Object[]>();
	}
	
	public void setData(List<Object[]> data){
		try{	
			this.data = data;
			fireTableDataChanged();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void resetModel(){
		data.clear();
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return COL_COUNT;
	}
	
	public int getRowCount(){
		return data.size();
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }
	
	public Object getValueAt(int row, int col){
    switch (col){
    case 0: return data.get(row)[0];
    case 1: return data.get(row)[1];
    case 2: return data.get(row)[2];
    case 3: return data.get(row)[3];
    case 4: return data.get(row)[4];
    case 5: return data.get(row)[5] + " " + data.get(row)[6];
    case 6: return data.get(row)[7];
    case 7: return data.get(row)[8];
    case 8: return data.get(row)[9];
    default: return null;
    }
		
	}
	
	public void setValueAt(Object aValue, int row, int col){
	}
	
	public Class getColumnClass(int col) {
		switch (col){
		 case 0: return String.class;
		 case 1: return String.class;
		 case 2: return Date.class;
		 case 3: return Date.class;
		 case 4: return String.class;
		 case 5: return String.class;
		 case 6: return String.class;
		 case 7: return Date.class;
		 case 8: return String.class;
		 default: return String.class;
		}
    }
	
	public boolean isCellEditable(int row, int col) {
        return false;
    }
	
}
