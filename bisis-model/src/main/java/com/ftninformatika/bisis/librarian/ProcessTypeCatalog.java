package com.ftninformatika.bisis.librarian;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class ProcessTypeCatalog {

  public static void register(ProcessType pt) {
    if (pt == null)
      return;
    processTypes.put(pt.getName(), pt);
  }
  
  public static ProcessType get(String name) {
    return processTypes.get(name);
  }
  
  public static void init(Connection conn) {
    try {
      processTypes.clear();
      Statement stmt = conn.createStatement();
      ResultSet rset = stmt.executeQuery(
          "SELECT tipobr_spec FROM Tipovi_obrade");
      while (rset.next()) {
        ProcessType pt = ProcessType.getProcessType(rset.getString(1));
        register(pt);
      }
      rset.close();
      stmt.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  
  public static void init() {   
    /* processTypes.clear();
     ExecuteQueryCommand command = new ExecuteQueryCommand();
   		command.setSqlString("SELECT tipobr_spec FROM Tipovi_obrade"); 
   		command = (ExecuteQueryCommand)	BisisApp.getJdbcService().executeCommand(command);
   		ArrayList<ArrayList<Object>> result = command.getResults();		
   		for(int i=0;i<result.size();i++){     
   			ProcessType pt = ProcessType.getProcessType((String)result.get(i).get(0));
       register(pt);
     }    */
    }
  
  private static HashMap<String, ProcessType> processTypes = 
    new HashMap<String, ProcessType>();
}
