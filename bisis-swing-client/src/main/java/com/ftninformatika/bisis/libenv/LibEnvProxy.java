/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.LibrarianManager;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.ProcessTypeBuilder;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Petar
 *
 */
public class LibEnvProxy {


	/***
	 *
	 * @return list of all Librarians in current Library or null
	 */
	public static List<Librarian> getAllLibrarians(){
		List<Librarian> librarians = null;
		try {
			List<LibrarianDTO> libList = BisisApp.bisisService.getAllLibrarinasInThisLibrary(BisisApp.appConfig.getLibrary()).execute().body();

			librarians = libList.stream().map(i -> LibrarianManager.initializeLibrarianFromDTO(i))
										 .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return librarians;
	 }
	
	public static List<ProcessType> getAllProcTypes(){
		List<ProcessType> processTypeList = null;
		try {
			List<ProcessTypeDTO> processTypeListDTO = BisisApp.bisisService.getProcessTypesForLibrary(BisisApp.appConfig.getLibrary()).execute().body();

			processTypeList = processTypeListDTO.stream().map(i -> ProcessTypeBuilder.buildProcessTypeFromDTO(i))
														 .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processTypeList;
		}

	
	public static boolean addLibrarian(Librarian lib){		

		String napomena = lib.getNapomena().replace("'", "").replace("\"", "");
		lib.setNapomena(napomena);
		lib.setBiblioteka(BisisApp.appConfig.getLibrary());
		LibrarianDTO librarianDTO = LibrarianManager.initializeDTOFromLibrarian(lib);
		try {
			 BisisApp.bisisService.createLibrarian(librarianDTO).execute();
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
		lib.setNapomena(napomena);
		LibrarianDTO librarianDTO = LibrarianManager.initializeDTOFromLibrarian(lib);
		Librarian l = null;
		try {
			LibrarianDTO l1 = BisisApp.bisisService.updateLibrarian(librarianDTO).execute().body();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean deleteLibrarian(Librarian lib){

		try {
			BisisApp.bisisService.deleteLibraian(LibrarianManager.initializeDTOFromLibrarian(lib)).execute();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean addProcessType(ProcessType pt){	

		pt.setLibName(BisisApp.appConfig.getLibrary());
		ProcessTypeDTO processTypeDTO = ProcessTypeBuilder.buildDTOFromProcessType(pt);
		try {
			BisisApp.bisisService.addProcessType(processTypeDTO).execute();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	public static boolean updateProcessType(ProcessType pt){


		pt.setLibName(BisisApp.appConfig.getLibrary());
		ProcessTypeDTO processTypeDTO = ProcessTypeBuilder.buildDTOFromProcessType(pt);
		try {
			BisisApp.bisisService.addProcessType(processTypeDTO).execute();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		}
	
	public static boolean deleteProcessType(ProcessType pt){
		
 		return false;
	}
	
	private static int getNextTipobrId(){

		return -1;

	}
		
	
	private static Log log = LogFactory.getLog(LibEnvProxy.class);
}
