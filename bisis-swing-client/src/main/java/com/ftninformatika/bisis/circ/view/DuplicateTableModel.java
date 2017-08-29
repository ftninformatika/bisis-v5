package com.ftninformatika.bisis.circ.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.models.circ.pojo.Duplicate;



public class DuplicateTableModel extends AbstractTableModel implements Serializable {


    protected List<Duplicate>  data;
    protected List<String>  columnIdentifiers;

    public DuplicateTableModel() {
    	columnIdentifiers = new ArrayList<String>();
    	columnIdentifiers.add(Messages.getString("circulation.date")); //$NON-NLS-1$
    	columnIdentifiers.add(Messages.getString("circulation.duplicatenumber")); //$NON-NLS-1$
    	data = new ArrayList<Duplicate>();
    }
    
    public void setData(Set data){
      this.data.clear();
      Iterator it = data.iterator();
      while (it.hasNext()){
        this.data.add((Duplicate)it.next());
      }
    	fireTableDataChanged();
    }
    
//		 Manipulating rows
    
	    public void addRow() {
	    	int row = getRowCount();
        int dupno;
        if (row == 0){
          dupno = 1;
        }else {
          dupno = data.get(row-1).getDupNo() + 1;
        }
	    	Duplicate rowData = new Duplicate();
	    	rowData.setDupDate(new Date());
        rowData.setDupNo(dupno);
        data.add(rowData);
        Cirkulacija.getApp().getUserManager().addDuplicate(rowData);
	    	fireTableRowsInserted(row, row);
	    }


	    public void removeRow(int row) {
        
	      Duplicate dup = data.remove(row);
        Cirkulacija.getApp().getUserManager().removeDuplicate(dup);
	      fireTableRowsDeleted(row, row);
	    }


		//
//		 Implementing the TableModel interface
		//

	    public int getRowCount() {
	        return data.size();
	    }

	    public int getColumnCount() {
	        return columnIdentifiers.size();
	    }

	    public String getColumnName(int column) {
	        Object id = null; 
		// This test is to cover the case when 
		// getColumnCount has been subclassed by mistake ... 
		if (column < columnIdentifiers.size()) {  
		    id = columnIdentifiers.get(column); 
		}
	        return (id == null) ? super.getColumnName(column) 
	                            : id.toString();
	    }
      
      public void setColumnName(int column, String text) {
        if (column < columnIdentifiers.size()) {  
          columnIdentifiers.set(column, text); 
          fireTableStructureChanged();
        }
      }

	    public boolean isCellEditable(int row, int column) {
	        return true;
	    }

	    public Object getValueAt(int row, int column) {
	        Duplicate rowData = data.get(row);
	        switch (column){
	        	case 0: return rowData.getDupDate();
	        	case 1: return rowData.getDupNo();
	        	default: return null;
			}
	    }

	    public void setValueAt(Object aValue, int row, int column) {
	        Duplicate rowData = data.get(row);
	        switch (column){
	        	case 0: rowData.setDupDate((Date)aValue);
	        			break;
	        	case 1: rowData.setDupNo(((Integer)aValue).intValue());
	        }
	        fireTableCellUpdated(row, column);
	        Cirkulacija.getApp().getUserManager().updateDuplicate(rowData);
	    }

	    public Class getColumnClass(int col) {
			switch (col){
			 case 0: return Date.class;
			 case 1: return Integer.class;
			 default: return String.class;
			}
	    }
}
