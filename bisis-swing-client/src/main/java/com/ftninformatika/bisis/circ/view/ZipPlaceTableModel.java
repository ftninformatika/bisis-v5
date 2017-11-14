package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Place;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;


//import com.gint.app.bisis4.client.circ.commands.GetAllCommand;



public class ZipPlaceTableModel extends AbstractTableModel implements Serializable{

  protected List<Place>  data;
  protected List<String>  columnIdentifiers;
  
	public ZipPlaceTableModel() {
    columnIdentifiers = new ArrayList<String>();
    columnIdentifiers.add(Messages.getString("circulation.zipcode")); //$NON-NLS-1$
    columnIdentifiers.add(Messages.getString("circulation.place"));     //$NON-NLS-1$
      data = BisisApp.appConfig.getCodersHelper()
              .getPlaces().values().stream()
              .collect(Collectors.toList());
	}
  
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
      Place rowData = (Place)data.get(row);
      switch (column){
        case 0: return rowData.getZip();
        case 1: return rowData.getCity();
        default: return null;
      }
  }
  
  public Class getColumnClass(int col) {
    return String.class;
  }

}
