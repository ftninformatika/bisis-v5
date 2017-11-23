package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.Member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class SearchUsersTableModel extends AbstractTableModel implements Serializable {

    protected List<Member> data;
    protected  List<String> columnIdentifiers;
      
    public SearchUsersTableModel() {
      columnIdentifiers=new ArrayList<String>();
      columnIdentifiers.add(Messages.getString("circulation.usernumber")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.firstname")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.lastname")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.parentname")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.umcn")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.place")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.address")); //$NON-NLS-1$
      data=new ArrayList<Member>();
    }
   
    public String getColumnName(int column) {
      Object id = null;
  		if (column < columnIdentifiers.size()) {  
  		    id = columnIdentifiers.get(column); 
  		}
      return (id == null) ? super.getColumnName(column) : id.toString();
    }

    public void setData(List data){
    	this.data = data;
    	fireTableDataChanged();
    }
    
    public void removeAll() {
      data.clear();
      fireTableDataChanged();
    }
    
    public int getRowCount() {
      return data.size();
    }

    public int getColumnCount() {
      return columnIdentifiers.size();
    }

    public Class getColumnClass(int col) {
       return String.class;
    }
    
    public boolean isCellEditable(int row, int column) {
      return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Member rowData = (Member) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowData.getUserId();
            case 1:
                return rowData.getFirstName();
            case 2:
                return rowData.getLastName();
            case 3:
                return rowData.getParentName();
            case 4:
                return rowData.getJmbg();
            case 5:
                return rowData.getCity();
            case 6:
                return rowData.getAddress();
            default:
                return null;
        }
        //			return data.get(rowIndex)[columnIndex];
    }
    
    public String getUser(int rowIndex) {
      return data.get(rowIndex).getUserId();
    }
  }
	 
