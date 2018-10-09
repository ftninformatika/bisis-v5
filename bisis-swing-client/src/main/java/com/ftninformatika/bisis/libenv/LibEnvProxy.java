/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.auth.model.Authority;
import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.LibrarianManager;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.ProcessTypeBuilder;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import org.apache.log4j.Logger;


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


	public static List<Location> getLocations(){
		List<Location> locations = null;
		try {
			locations = BisisApp.bisisService.getLocations(BisisApp.appConfig.getLibrary()).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}

	public static List<CircLocation> getCircLocations(){
		List<CircLocation> locations = null;
		try {
			locations = BisisApp.bisisService.getCircLocations(BisisApp.appConfig.getLibrary()).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return locations;
	}

	
	public static boolean addLibrarian(Librarian lib){		

		String napomena = lib.getNapomena().replace("'", "").replace("\"", "");
		lib.setNapomena(napomena);
		LibrarianDTO librarianDTO = LibrarianManager.initializeDTOFromLibrarian(lib);
		librarianDTO.setAuthorities(Arrays.asList(new Authority[]{Authority.ROLE_ADMIN}));
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
		librarianDTO.setAuthorities(Arrays.asList(new Authority[]{Authority.ROLE_ADMIN}));
		try {
			BisisApp.bisisService.updateLibrarian(librarianDTO).execute().body();
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
		
	
	private static Logger log = Logger.getLogger(LibEnvProxy.class);
}
