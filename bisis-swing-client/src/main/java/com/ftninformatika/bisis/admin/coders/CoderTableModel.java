package com.ftninformatika.bisis.admin.coders;

import com.ftninformatika.utils.Messages;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.MessageFormat;import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import com.ftninformatika.bisis.BisisApp;


public class CoderTableModel extends AbstractTableModel {

  public CoderTableModel(Table table) {
    this.table = table;
    data = new ArrayList<ArrayList<Object>>();
  }
  
  public int getColumnCount() {
    if (table.getColumns().get(0).getName().equals("id"))
      return table.getColumns().size() - 1 ;
    return table.getColumns().size();
  }
  
  public boolean isCellEditable(int row, int col) {
    return !table.getColumns().get(col).isKey();
  }
  
  public String getColumnName(int col) {
    if (table.getColumns().get(0).getName().equals("id"))
      return table.getColumns().get(col + 1).getCaption();
    return table.getColumns().get(col).getCaption();
  }
  
  public Class<?> getColumnClass(int col) {
    int type = table.getColumns().get(col).getType();
    switch (type) {
      case Types.INTEGER:
        return Integer.class;
      case Types.VARCHAR:
      case Types.CHAR:
        return String.class;
      case Types.DECIMAL:
        return BigDecimal.class;
      case Types.DATE:
        return Date.class;
      case Types.BOOLEAN:
        return Boolean.class;
      default:
        return Object.class;
    }
  }
  
  public Object getValueAt(int row, int col) {
    if (table.getColumns().get(0).getName().equals("id"))
      return data.get(row).get(col + 1);
    return data.get(row).get(col);
  }
  
  public int getRowCount() {
    return data.size();
  }
  
  public void setValueAt(Object value, int row, int col) {
    if (table.getColumns().get(0).getName().equals("id"))
      data.get(row).set(col + 1, value);
    else
      data.get(row).set(col, value);
  }
  

  public void refresh() throws Exception{
    data.clear();
    data = BisisApp.appConfig.getCodersHelper().getCoderTableModelList(table.getName());
    fireTableDataChanged();

  }

  public void update(int row) throws Exception{
      ArrayList<Object> rowData = data.get(row);
      ArrayList<Object> response = CoderManager.updateCoder(rowData, table.getName());
      if (response.size() > 0) {
        fireTableDataChanged();
      }
      else {
        throw new Exception("Грешка при покушају измене шифарника!");
      }
  }

  public void insert(ArrayList<String> row) throws Exception {
	  
	ArrayList<Object> newRow = new ArrayList<Object>();
    int index = 0;
    for (String s : row) {
      switch (table.getColumns().get(index).getType()) {
        case Types.INTEGER:
          try {
			newRow.add(Integer.parseInt(s.trim()));
          } catch (NumberFormatException e){
			throw new Exception (MessageFormat.format(Messages.getString("fieldformat.0.mustbenumber"), table.getColumns().get(index).getName()));
          }
          break;
        case Types.CHAR:
        case Types.VARCHAR:
          newRow.add(s.trim());
          break;
        case Types.DECIMAL:
          try {
        	  newRow.add(new BigDecimal(s.trim()));
          } catch (NumberFormatException e){
  			throw new Exception (MessageFormat.format(Messages.getString("fieldformat.0.mustbedecimal"), table.getColumns().get(index).getName()));
          }
        case Types.DATE:
          try {
        	  newRow.add(sdf.parse(s.trim()));
          } catch (ParseException e){
    		  throw new Exception (Messages.getString("dateformat") );
           }
        default:
      }
      index++;

    }
    ArrayList<Object> response = CoderManager.insertCoder(newRow, table.getName());

	  if (response.size() > 0){
            data.add(response);
            fireTableDataChanged();
	  } else {
		  throw new Exception ("Шифарник није уписан!");
	  }
  }

  public void delete(int row) throws Exception {
    ArrayList<Object> rowData = data.get(row);
    Boolean response = CoderManager.deleteCoder(String.valueOf(rowData.get(0)), table.getName());
    if (response == true){
        data.remove(row);
        fireTableDataChanged();

    } else {
    	  throw new Exception ("Грешка при покушају брисања шифарника!");
    }
  }

  private ArrayList<ArrayList<Object>> data;
  private Table table;
  
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
}
