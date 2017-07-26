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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author dimicb
 *
 */
public class LibEnvProxy {
	

	
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
		//List<ProcessType> processTypeList = BisisApp.appConfig.getLibrarian().getContext().getProcessTypes();//TODO- ovde treba na nivou biblioteke sve tipove obrade da izvuce

		List<ProcessType> processTypeList = null;

		try {
			processTypeList =(List<ProcessType>) GsonUtils.getCollectionFromJsonObject(ProcessType.class, BisisApp.bisisService.getProcessTypesForLibrary(BisisApp.appConfig.getLibrary()).execute().body());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return processTypeList;
		}

	
	public static boolean addLibrarian(Librarian lib){		

		String napomena = lib.getNapomena().replace("'", "").replace("\"", "");
		lib.setNapomena(napomena);
		lib.setBiblioteka(BisisApp.appConfig.getLibrary());
		try {
			 BisisApp.bisisService.createLibrarian(lib).execute();
			 return true;
		} catch (IOException e) {
			 e.printStackTrace();
			 return false;
		}

	}
	
	public static boolean updateLibrarian(Librarian lib){

		
		String napomena = "";
		if (lib.getNapomena() != null)
			napomena = lib.getNapomena().replace("'", "").replace("\"", "");
		Librarian l = null;
		try {
			l = BisisApp.bisisService.updateLibrarian(lib).execute().body();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean deleteLibrarian(Librarian lib){

		try {
			BisisApp.bisisService.deleteLibraian(lib).execute();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean addProcessType(ProcessType pt){	
		
		return false;
	}
	
	public static boolean updateProcessType(ProcessType pt){
		

			return false;
		}
	
	public static boolean deleteProcessType(ProcessType pt){
		
 		return false;
	}
	
	private static int getNextTipobrId(){

		return -1;

	}
		
	
	private static Log log = LogFactory.getLog(LibEnvProxy.class);
}
