package com.ftninformatika.bisis.circ.warnings;

import com.ftninformatika.bisis.circ.wrappers.MemberData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;



public class HistoryTableModel extends AbstractTableModel {
	
	private static final int COL_COUNT = 9;
	private String[] columnNames = {"Broj opomene","Tip opomene", "Datum slanja", "Rok vra\u0107anja", "Broj \u010dlana", "Prezime i Ime", "Inventarni broj", "Datum vra\u0107anja", "Napomena"};
	private List<List<String>> data= null;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		
	
	public HistoryTableModel(){
		data = new ArrayList<List<String>>();
	}
	
	public void setData(List<MemberData> data){
		try{
			if (data != null) {
				data.forEach(m -> {
					m.getLendings().forEach(l -> {
						l.getWarnings().forEach(w -> {
							List<String> row = new ArrayList<>();
							row.add(0, w.getWarnNo());
							row.add(1, w.getWarningType());
							row.add(2, sdf.format(w.getWarningDate()));
							row.add(3, sdf.format(w.getDeadline()));
							row.add(4, m.getMember().getUserId());
							row.add(5, m.getMember().getLastName() + " " + m.getMember().getFirstName());
							row.add(6, l.getCtlgNo());
							row.add(7, l.getReturnDate() != null ? sdf.format(l.getReturnDate()) : "");
							row.add(8, w.getNote());
							this.data.add(row);

						});
					});
				});
			}
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
	
	public String getValueAt(int row, int col){
		return data.get(row).get(col);
    }
	
	public void setValueAt(Object aValue, int row, int col){
	}
	
	public Class getColumnClass(int col) {
		return String.class;
    }
	
	public boolean isCellEditable(int row, int col) {
        return false;
    }
	
}
