/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.utils.GsonUtils;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author dimicb
 *
 */
public class LibEnvProxy {
	
	/*
	 * 
	 private static Connection conn; 
	
	static{
		conn = BisisApp.getConnection();
	}
	
	*/
	
	public static List<Librarian> getAllLibrarians(){

		List<Librarian> libList = null;

		try {
			libList = (List<Librarian>) GsonUtils.getCollectionFromJsonObject(Librarian.class,
						BisisApp.bisisService.getAllLibrarinasInThisLibrary(BisisApp.appConfig.getLibrary()).execute().body());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return libList;
	 }
		

	
	public static List<ProcessType> getAllProcTypes(){
		List<ProcessType> processTypeList = BisisApp.appConfig.getLibrarian().getContext().getProcessTypes();//TODO-hardcoded
		/*List<ProcessType> procTypeList = new ArrayList<ProcessType>();
		ExecuteQueryCommand command = new ExecuteQueryCommand();
		command.setSqlString(SELECT_PROCESSTYPES);		
		command = (ExecuteQueryCommand)	BisisApp.getJdbcService().executeCommand(command);
		if(command!=null){
		ArrayList<ArrayList<Object>> result = command.getResults();		
		for(int i=0;i<result.size();i++){
			ArrayList<Object> row = result.get(i);
			ProcessType pt = ProcessType.getProcessType((String)row.get(1));
			pt.setId((Integer)row.get(0));
			procTypeList.add(pt);
		}
		command.clear();
		return procTypeList;
		}else{*/
			return processTypeList;
		}

	
	public static boolean addLibrarian(Librarian lib){		
		
		/*int isCataloguing = lib.isCataloguing()? 1 : 0;
		int isCirculation = lib.isCirculation()? 1 : 0;
		int isAdmin = lib.isAdministration()? 1 : 0;		
		String napomena = lib.getNapomena().replace("'", "").replace("\"", "");		
		String sqlString = "INSERT INTO Bibliotekari (" +
		"username, password, ime, prezime, email, napomena, obrada, cirkulacija, " +
		"administracija, context) VALUES ('"+lib.getUsername()+"','"+lib.getPassword()+"','"+lib.getIme()
		+"','"+lib.getPrezime()+"','"+lib.getEmail()+"','"+napomena+"','"+isCataloguing+"','"
		+isCirculation+"','"+isAdmin+"','"+lib.getContext().toXML()+"')";		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
		if(command!=null)
			if(command.getException()==null){			
				return true;
			}else{
				log.fatal("Cannot add librarian with username: "+lib.getUsername());
				log.fatal(command.getException());			
				return false;
			}
		else*/ return false;
		
		/*
		AddLibrarianCommand addLibrarian = new AddLibrarianCommand();
		addLibrarian.setLibrarian(lib);
		BisisApp.getJdbcService().executeCommand(addLibrarian);		
		return addLibrarian.isSaved();
		*/
		/*
		try {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_LIBRARIAN);			
			pstmt.setString(1, lib.getUsername());
			pstmt.setString(2, lib.getPassword());
			pstmt.setString(3, lib.getIme());
			pstmt.setString(4, lib.getPrezime());
			pstmt.setString(5, lib.getEmail());
			pstmt.setString(6, lib.getNapomena());
			pstmt.setBoolean(7, lib.isCataloguing());
			pstmt.setBoolean(8, lib.isCirculation());
			pstmt.setBoolean(9, lib.isAdministration());
			pstmt.setString(10, lib.getContext().toXML());
			pstmt.executeUpdate();			
			conn.commit();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			log.fatal("Cannot add librarian with username: "+lib.getUsername());
			log.fatal(e);			
			return false;
		}*/		
	}
	
	public static boolean updateLibrarian(Librarian lib){
		
		/*int isCataloguing = lib.isCataloguing()? 1 : 0;
		int isCirculation = lib.isCirculation()? 1 : 0;
		int isAdmin = lib.isAdministration()? 1 : 0;		
		
		String napomena = "";
		if (lib.getNapomena() != null)
			napomena = lib.getNapomena().replace("'", "").replace("\"", "");
		
		String sqlString = "UPDATE Bibliotekari SET " +
			"password='"+lib.getPassword()+"', ime='"+lib.getIme()+"', prezime='"+lib.getPrezime()+"', email='"+
			lib.getEmail()+"', napomena='"+napomena+"', obrada='"+isCataloguing+"', " +
			"cirkulacija='"+isCirculation+"', administracija='"+isAdmin+"', context='"+lib.getContext().toXML()+
			"' WHERE username='"+lib.getUsername()+"';";
		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);
		if(command!=null){
		if(command.getException()==null){
			return true;
		}else{
			log.fatal("Cannot update librarian with username: "+lib.getUsername());
			log.fatal(command.getException());
			return false;
		}
		}else*/
			return false;
		/*
		try {
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_LIBRARIAN);			
			pstmt.setString(1, lib.getPassword());
			pstmt.setString(2, lib.getIme());
			pstmt.setString(3, lib.getPrezime());
			pstmt.setString(4, lib.getEmail());
			pstmt.setString(5, lib.getNapomena());
			pstmt.setBoolean(6, lib.isCataloguing());
			pstmt.setBoolean(7, lib.isCirculation());
			pstmt.setBoolean(8, lib.isAdministration());
			pstmt.setString(9, lib.getContext().toXML());
			pstmt.setString(10, lib.getUsername());
			pstmt.executeUpdate();			
			conn.commit();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			log.fatal("Cannot update librarian with username: "+lib.getUsername());
			log.fatal(e);
			e.printStackTrace();
			return false;
		}
		*/
	}
	
	public static boolean deleteLibrarian(Librarian lib){			
		/*String sqlString = "DELETE FROM Bibliotekari WHERE username='"+lib.getUsername()+"'";
		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);		
		if(command!=null)
			if(command.getException()==null){
				return true;
			}else{
				log.fatal("Cannot delete librarian with username: "+lib.getUsername());
				log.fatal(command.getException());
				return false;
			}
		else*/
			return false;
		
		/*
			try {
				PreparedStatement pstmt = conn.prepareStatement(DELETE_LIBRARIAN);
				pstmt.setString(1, lib.getUsername());
				pstmt.executeUpdate();
				conn.commit();
				pstmt.close();
				return true;
			} catch (SQLException e) {				
				log.fatal("Cannot delete librarian with username: "+lib.getUsername());
				log.fatal(e);				
				return false;
			}
			*/			
	}
	
	public static boolean addProcessType(ProcessType pt){	
		
		// TODO prepraviti da se ne poziva jedna komanda u drugoj
  
	/*	String sqlString = "INSERT INTO Tipovi_obrade(" +
			"tipobr_id,tipobr_spec) VALUES ('"+getNextTipobrId()+"','"+pt.toXML()+"')";
		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);		
		if(command!=null)
		if(command.getException()==null){			
			return true;
		}else{
			log.fatal("Cannot add process type, name: "+pt.getName());
			log.fatal(command.getException());
			return false;
		}
		else*/
			return false;
		
		/*
		try {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_PROCESSTYPE);
			pstmt.setInt(1, getNextTipobrId());
			pstmt.setString(2, pt.toXML());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			log.fatal("cannot add process type, name: "+pt.getName());
			log.fatal(e);
			return false;
		}	*/	
	}
	
	public static boolean updateProcessType(ProcessType pt){
		
	/*	String sqlString  = "UPDATE Tipovi_obrade SET " +
		"tipobr_spec='"+pt.toXML()+"' WHERE tipobr_id='"+pt.getId()+"'";;
		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);		
		if(command.getException()==null){
			return true;
		}else{
			log.fatal("Cannot update process type, name: "+pt.getName());
			log.fatal(command.getException());*/
			return false;
		}
		
		/*
		try {
			PreparedStatement pstmt = conn.prepareStatement(UPDATE_PROCESSTYPE);			
			pstmt.setString(1, pt.toXML());
			pstmt.setInt(2, pt.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			log.fatal("Cannot update process type, name: "+pt.getName());
			log.fatal(e);
			return false;
		}	
		
			*/
	//}
	
	public static boolean deleteProcessType(ProcessType pt){
		
 /* String sqlString = "DELETE from Tipovi_obrade WHERE tipobr_id='"+pt.getId()+"'";
		
		ExecuteUpdateCommand command = new ExecuteUpdateCommand();
		command.setSqlString(sqlString);
		command = (ExecuteUpdateCommand)BisisApp.getJdbcService().executeCommand(command);		
		if(command!=null)
			if(command.getException()==null){
				return true;
			}else{
				log.fatal("Cannot delete process type, name: "+pt.getName());
				log.fatal(command.getException());
				return false;
			}
		else */
			return false;
		/*
		try {
			PreparedStatement pstmt = conn.prepareStatement(DELETE_PROCESSTYPE);			
			pstmt.setInt(1,pt.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			return true;
		} catch (SQLException e) {
			log.fatal("Cannot delete process type, name: "+pt.getName());
			log.fatal(e);
			return false;
		}	
		*/	
	}
	
	private static int getNextTipobrId(){
		
		/*ExecuteQueryCommand command = new ExecuteQueryCommand();
		command.setSqlString("SELECT MAX(tipobr_id) FROM Tipovi_obrade"); 
		command = (ExecuteQueryCommand)	BisisApp.getJdbcService().executeCommand(command);
		if(command!=null){
			ArrayList<ArrayList<Object>> result = command.getResults();
			return (Integer)result.get(0).get(0)+1;
		}else*/
			return -1;
		
		/*
		Statement stmt;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(tipobr_id) FROM Tipovi_obrade");
			rs.next();
			int currId = rs.getInt(1);
			stmt.close();
			return currId+1;
		} catch (SQLException e) {
			log.fatal("Cannot create tipobr_id.");
			log.fatal(e);
			return 0;
		}	*/
	}
	
	private static String SELECT_LIBRARIANS = "SELECT username, password," +
			"ime, prezime, email, napomena, context, obrada, cirkulacija, " +
			"administracija FROM Bibliotekari"; 
		
	private static String SELECT_PROCESSTYPES = "SELECT tipobr_id,tipobr_spec FROM " +
			"Tipovi_obrade;";
	
	
	private static String UPDATE_LIBRARIAN = "UPDATE Bibliotekari SET " +
			"password=?, ime=?, prezime=?, email=?, napomena=?, obrada=?, " +
			"cirkulacija=?, administracija=?, context=? WHERE username=?";
	
	private static String DELETE_LIBRARIAN = "DELETE FROM Bibliotekari WHERE username=?";
	
	private static String INSERT_PROCESSTYPE = "INSERT INTO Tipovi_obrade(" +
			"tipobr_id,tipobr_spec) VALUES (?,?)";
	
	private static String UPDATE_PROCESSTYPE = "UPDATE Tipovi_obrade SET " +
			"tipobr_spec=? WHERE tipobr_id=?";
	
	private static String DELETE_PROCESSTYPE = "DELETE from Tipovi_obrade WHERE tipobr_id=?";
		
		
	
	private static Log log = LogFactory.getLog(LibEnvProxy.class);
}
