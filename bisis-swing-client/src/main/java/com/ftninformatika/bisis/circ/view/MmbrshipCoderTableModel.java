package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.pojo.MembershipType;
import com.ftninformatika.bisis.circ.pojo.UserCategory;
import com.ftninformatika.bisis.circ.Membership;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;



public class MmbrshipCoderTableModel extends AbstractTableModel implements Serializable {


    protected List<Membership>  data;
    protected List<String>  columnIdentifiers;

    public MmbrshipCoderTableModel() {
    	columnIdentifiers = new ArrayList<String>();
        columnIdentifiers.add("Vrsta \u010dlanstva");
    	columnIdentifiers.add("Kategorija korisnika");   	
        columnIdentifiers.add("Cena");
		data = BisisApp.appConfig.getCodersHelper()
				.getMemberships().values().stream()
				.collect(Collectors.toList());
    }
    
//		 Manipulating rows
    
	    public void addRow(MembershipType mmbrTypes, UserCategory userCateg, Double cost) {
	    	int row = getRowCount();
	    	Membership rowData = null;
        
	       for (Membership m : data){
	          if (m.getMemberType() == mmbrTypes.getDescription() && m.getUserCateg() == userCateg.getDescription()){
	            rowData = m;
	          }
	        }
	        if (rowData == null){
	          rowData = new Membership();
	          rowData.setUserCateg(userCateg.getDescription());
	          rowData.setMemberType(mmbrTypes.getDescription());
	          rowData.setCost(cost);
	          rowData.setLibrary(BisisApp.appConfig.getLibrary());
                try {
                    rowData = BisisApp.bisisService.addMembership(rowData).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }

	          if (rowData.get_id() != null){
	            data.add(rowData);
	            fireTableRowsInserted(row, row);
	          }
	        }else{
	          rowData.setCost(cost);
	          rowData.setLibrary(BisisApp.appConfig.getLibrary());
                try {
                    rowData = BisisApp.bisisService.addMembership(rowData).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
	          if (rowData.get_id() != null)
	            fireTableRowsUpdated(0, row);
	        }
	    }


	    public void removeRows(int[] rows) {
        for (int i = rows.length-1; i >= 0; i--){
          Membership m = data.get(rows[i]);
          boolean deleted = false;
            try {
                deleted = BisisApp.bisisService.deleteMembership(m.get_id()).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

          if (deleted){
            data.remove(rows[i]);
            fireTableRowsDeleted(rows[i], rows[i]);
          }
        }
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
	        return false;
	    }

	    public Object getValueAt(int row, int column) {
	        Membership rowData = (Membership)data.get(row);
	        switch (column){
            	case 0: return rowData.getMemberType();
	        	case 1: return rowData.getUserCateg();
	        	case 2: return rowData.getCost();
	        	default: return null;
			}
	    }

	    public Class getColumnClass(int col) {
			switch (col){
			 case 0: return String.class;
			 case 1: return String.class;
			 case 2: return Double.class;
			 default: return String.class;
			}
	    }
}
