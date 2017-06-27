package com.ftninformatika.bisis.admin.coders;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class CoderTableModel extends AbstractTableModel {

  public CoderTableModel(Table table) {
    this.table = table;
    data = new ArrayList<ArrayList<Object>>();
  }
  
  public int getColumnCount() {
    return table.getColumns().size();
  }
  
  public boolean isCellEditable(int row, int col) {
    return !table.getColumns().get(col).isKey();
  }
  
  public String getColumnName(int col) {
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
    return data.get(row).get(col);
  }
  
  public int getRowCount() {
    return data.size();
  }
  
  public void setValueAt(Object value, int row, int col) {
    data.get(row).set(col, value);
  }
  
//  public void refresh() {
//    data.clear();
//    try {
//      Statement stmt = BisisApp.getConnection().createStatement();
//      ResultSet rset = stmt.executeQuery(getSelect());
//      while (rset.next()) {
//        ArrayList<Object> row = new ArrayList<Object>();
//        data.add(row);
//        int index = 1;
//        for (Column c : table.getColumns()) {
//          switch (c.getType()) {
//            case Types.INTEGER:
//              row.add(rset.getInt(index));
//              break;
//            case Types.CHAR:
//            case Types.VARCHAR:
//              row.add(rset.getString(index).trim());
//              break;
//            case Types.DECIMAL:
//              row.add(rset.getBigDecimal(index));
//              break;
//            case Types.DATE:
//              row.add(rset.getDate(index));
//              break;
//            default:
//              row.add(rset.getObject(index));
//          }
//          index++;
//        }
//      }
//      rset.close();
//      stmt.close();
//      fireTableDataChanged();
//    } catch (Exception ex) {
//      ex.printStackTrace();
//    }
//  }
  
  public void refresh() throws Exception{
    data.clear();
    /*ExecuteQueryCommand command = new ExecuteQueryCommand();
    command.setSqlString(getSelect());
    command = (ExecuteQueryCommand)BisisApp.getJdbcService().executeCommand(command);
    if (command != null){
	    if (command.getResults() == null){
	    	throw new Exception("Gre\u0161ka pri ucitavanju podataka!");
	    } else {
		    data = command.getResults();
		    fireTableDataChanged();
	    }
    } else {
    	throw new Exception ("Gre\u0161ka u konekciji s bazom podataka!");
    }*/
  }
  
//  public void update(int row) {
//    try {
//      PreparedStatement stmt = BisisApp.getConnection().prepareStatement(
//          getUpdate());
//      int colIndex = 0;
//      int stmtIndex = 1;
//      for (Column c : table.getColumns()) {
//        if (!c.isKey()) 
//          setValue(stmt, c.getType(), stmtIndex++, data.get(row).get(colIndex));
//        colIndex++;
//      }
//      colIndex = 0;
//      for (Column c : table.getColumns()) {
//        if (c.isKey())
//          setValue(stmt, c.getType(), stmtIndex++, data.get(row).get(colIndex));
//        colIndex++;
//      }      
//      stmt.executeUpdate();
//      BisisApp.getConnection().commit();      
//      fireTableDataChanged();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
  
  public void update(int row) throws Exception{  
	  /*ExecuteUpdateCommand command = new ExecuteUpdateCommand();
      command.setSqlString(getUpdate(row));
      command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
      if (command != null){
	      if (command.getRowCount() == 1){
		      fireTableDataChanged(); 
	      } else {
	    	  throw command.getException();
	      }
      } else {
      	throw new Exception ("Gre\u0161ka u konekciji s bazom podataka!");
      }*/
  }
  
//  public void insert(ArrayList<String> row) throws HoldingsIntegrityViolationException {
//    ArrayList<Object> newRow = new ArrayList<Object>();
//    int index = 0;
//    for (String s : row) {
//      switch (table.getColumns().get(index).getType()) {
//        case Types.INTEGER:
//          newRow.add(Integer.parseInt(s.trim()));
//          break;
//        case Types.CHAR:
//        case Types.VARCHAR:
//          newRow.add(s.trim());
//          break;
//        case Types.DECIMAL:
//          newRow.add(new BigDecimal(s.trim()));
//        case Types.DATE:
//          try { newRow.add(sdf.parse(s.trim())); } catch (Exception e) { newRow.add(null); }
//        default:
//      }
//      index++;
//    }
//    try {
//      PreparedStatement stmt = BisisApp.getConnection().prepareStatement(
//          getInsert());
//      int colIndex = 0;
//      for (Column c : table.getColumns()) {
//        setValue(stmt, c.getType(), colIndex+1, newRow.get(colIndex));
//        colIndex++;
//      }
//      stmt.executeUpdate();
//      BisisApp.getConnection().commit();
//      data.add(newRow);
//      fireTableDataChanged();
//    }catch(MySQLIntegrityConstraintViolationException e1){
//    	throw new HoldingsIntegrityViolationException(
//    			"Uneta \u0161ifra ve\u0107 postoji!");
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
  
  public void insert(ArrayList<String> row) throws Exception {
	  
	ArrayList<Object> newRow = new ArrayList<Object>();
    int index = 0;
    for (String s : row) {
      switch (table.getColumns().get(index).getType()) {
        case Types.INTEGER:
          try {
			newRow.add(Integer.parseInt(s.trim()));
          } catch (NumberFormatException e){
			throw new Exception ("Format polja "+ table.getColumns().get(index).getName() + " mora biti broj!");
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
  			throw new Exception ("Format polja "+ table.getColumns().get(index).getName() + " mora biti decimalan broj!");
          }
        case Types.DATE:
          try {
        	  newRow.add(sdf.parse(s.trim()));
          } catch (ParseException e){
    		  throw new Exception ("Format za datum mora biti dd.MM.yyyy!" );
           }
        default:
      }
      index++;
    }
	  
	  /*ExecuteUpdateCommand command = new ExecuteUpdateCommand();
	  command.setSqlString(getInsert(newRow));
	  command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
	  if (command != null){
		  if (command.getRowCount() == 1){
			  data.add(newRow);
		      fireTableDataChanged();
		  } else if (command.getException() instanceof MySQLIntegrityConstraintViolationException){
			  throw new HoldingsIntegrityViolationException
				("Uneta \u0161ifra ve\u0107 postoji!");
		  } else {
			  throw command.getException();
		  }
	  } else {
		  throw new Exception ("Gre\u0161ka u konekciji s bazom podataka!");
	  }*/
  }
  
//  public void delete(int row) throws HoldingsIntegrityViolationException {
//    try {
//      PreparedStatement stmt = BisisApp.getConnection().prepareStatement(
//          getDelete());
//      int colIndex = 0;
//      int keyIndex = 1;
//      for (Column c : table.getColumns()) {
//        if (c.isKey())
//          setValue(stmt, c.getType(), keyIndex++, data.get(row).get(colIndex));
//        colIndex++;
//      }
//      stmt.executeUpdate();
//      BisisApp.getConnection().commit();
//      data.remove(row);
//      fireTableDataChanged();
//     }catch(MySQLIntegrityConstraintViolationException e1){
//    	 throw new HoldingsIntegrityViolationException
//    	 			("\u0160ifra ne mo\u017ee biti obrisana!\nPostoje primerci/godine/sveske za koje je uneta selektovana \u0161ifra.");
//     }catch (SQLException e) {    	
//      e.printStackTrace();
//    }
//  }
  
  public void delete(int row) throws Exception {
      /*ExecuteUpdateCommand command = new ExecuteUpdateCommand();
      command.setSqlString(getDelete(row));
      command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
      
      if (command != null){
	      if (command.getRowCount() == 1){
	    	  data.remove(row);
		      fireTableDataChanged(); 
	      } else if (command.getException() instanceof MySQLIntegrityConstraintViolationException){
	    	  throw new HoldingsIntegrityViolationException
	 			("\u0160ifra ne mo\u017ee biti obrisana!\nPostoje primerci/godine/sveske za koje je uneta selektovana \u0161ifra.");
	      } else {
			 throw command.getException();
	      }
      } else {
    	  throw new Exception ("Gre\u0161ka u konekciji s bazom podataka!");
      }*/
  }
  
//  private void setValue(PreparedStatement stmt, int type, int index,
//      Object value) throws SQLException {
//    switch (type) {
//      case Types.INTEGER:
//        stmt.setInt(index, Integer.parseInt(value.toString()));
//        break;
//      case Types.CHAR:
//      case Types.VARCHAR:
//        stmt.setString(index, ((String)value).trim());
//        break;
//      case Types.DECIMAL:
//        stmt.setBigDecimal(index, (BigDecimal)value);
//        break;
//      case Types.DATE:
//        stmt.setDate(index, new java.sql.Date(((Date)value).getTime()));
//        break;
//      default:
//        stmt.setObject(index, value);
//    }
//  }
  
  private String getSqlValue(int type, Object value) {
    switch (type) {
      case Types.INTEGER:
        return value.toString();
      case Types.CHAR:
      case Types.VARCHAR:
        return "'" + ((String)value).trim()+ "'";
      case Types.DECIMAL:
    	 return value.toString();
      case Types.DATE:
    	 return "'" + (new java.sql.Date(((Date)value).getTime())).toString()+ "'";
      default:
    	return "'" + value.toString()+ "'";
    }
  }
  
  private String getSelect() {
    if (select != null)
      return select;
    StringBuffer retVal = new StringBuffer();
    retVal.append("SELECT ");
    int i = 0;
    for (Column c : table.getColumns()) {
      if (i++ > 0)
        retVal.append(", ");
      retVal.append(c.getName());
    }
    retVal.append(" FROM ");
    retVal.append(table.getName());
    select = retVal.toString();
    return select;
  }
  
//  private String getDelete() {
//    if (delete != null)
//      return delete;
//    StringBuffer retVal = new StringBuffer();
//    retVal.append("DELETE FROM ");
//    retVal.append(table.getName());
//    retVal.append(" WHERE ");
//    int i = 0;
//    for (Column c : table.getColumns()) {
//      if (c.isKey()) {
//        if (i++ > 0)
//          retVal.append(" AND ");
//        retVal.append(c.getName());
//        retVal.append("=?");
//      }
//    }
//    delete = retVal.toString();
//    return delete;
//  }
  
  private String getDelete(int row) {
    StringBuffer retVal = new StringBuffer();
    retVal.append("DELETE FROM ");
    retVal.append(table.getName());
    retVal.append(" WHERE ");
    int i = 0;
    int colIndex = 0;
    for (Column c : table.getColumns()) {
      if (c.isKey()) {
        if (i++ > 0)
          retVal.append(" AND ");
        retVal.append(c.getName());
        retVal.append("=");
        retVal.append(getSqlValue(c.getType(), data.get(row).get(colIndex)));
      }
      colIndex++;
    }
    return retVal.toString();
  }
  
//  private String getUpdate() {
//    if (update != null)
//      return update;
//    StringBuffer retVal = new StringBuffer();
//    retVal.append("UPDATE ");
//    retVal.append(table.getName());
//    retVal.append(" SET ");
//    int i = 0;
//    for (Column c : table.getColumns()) {
//      if (!c.isKey()) {
//        if (i++ > 0)
//          retVal.append(", ");
//        retVal.append(c.getName());
//        retVal.append("=?");
//      }
//    }
//    retVal.append(" WHERE ");
//    i = 0;
//    for (Column c : table.getColumns()) {
//      if (c.isKey()) {
//        if (i++ > 0)
//          retVal.append(", ");
//        retVal.append(c.getName());
//        retVal.append("=?");
//      }
//    }
//    update = retVal.toString();
//    return update;
//  }
  
  private String getUpdate(int row) {
    StringBuffer retVal = new StringBuffer();
    retVal.append("UPDATE ");
    retVal.append(table.getName());
    retVal.append(" SET ");
    int i = 0;
    int colIndex = 0;
    for (Column c : table.getColumns()) {
      if (!c.isKey()) {
        if (i++ > 0)
          retVal.append(", ");
        retVal.append(c.getName());
        retVal.append("=");
        retVal.append(getSqlValue(c.getType(), data.get(row).get(colIndex)));
      }
      colIndex++;
    }
    retVal.append(" WHERE ");
    i = 0;
    colIndex = 0;
    for (Column c : table.getColumns()) {
      if (c.isKey()) {
        if (i++ > 0)
          retVal.append(", ");
        retVal.append(c.getName());
        retVal.append("=");
        retVal.append(getSqlValue(c.getType(), data.get(row).get(colIndex)));
      }
      colIndex++;
    }
    return retVal.toString();
  }
  
//  private String getInsert() {
//    if (insert != null)
//      return insert;
//    StringBuffer retVal = new StringBuffer();
//    retVal.append("INSERT INTO ");
//    retVal.append(table.getName());
//    retVal.append(" (");
//    int i = 0;
//    for (Column c : table.getColumns()) {
//      if (i++ > 0)
//        retVal.append(", ");
//      retVal.append(c.getName());
//    }
//    retVal.append(") VALUES (");
//    i = 0;
//    for (Column c : table.getColumns()) {
//      if (i++ > 0)
//        retVal.append(", ");
//      retVal.append("?");
//    }
//    retVal.append(")");
//    insert = retVal.toString();
//    return insert;
//  }
  
  private String getInsert(ArrayList<Object> row) {
    StringBuffer retVal = new StringBuffer();
    retVal.append("INSERT INTO ");
    retVal.append(table.getName());
    retVal.append(" (");
    int i = 0;
    for (Column c : table.getColumns()) {
      if (i++ > 0)
        retVal.append(", ");
      retVal.append(c.getName());
    }
    retVal.append(") VALUES (");
    int index = 0;
    for (Object s : row) {
      if (index > 0)
        retVal.append(", ");
      retVal.append(getSqlValue(table.getColumns().get(index).getType(), s));
      index++;
    }
    retVal.append(")");
    return retVal.toString();
  }
  
  private ArrayList<ArrayList<Object>> data;
  private Table table;
  private String select;
  private String delete;
  private String update;
  private String insert;
  
  private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
  
  private static Log log = LogFactory.getLog(CoderTableModel.class);
}
