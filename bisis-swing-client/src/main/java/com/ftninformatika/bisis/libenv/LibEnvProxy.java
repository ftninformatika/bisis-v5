/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisis.librarian.db.ProcessTypeDB;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Petar
 *
 */
public class LibEnvProxy {


	/***
	 *
	 * @return list of all Librarians in current Library or null
	 */
	public static List<LibrarianDB> getAllLibrarians(){
		List<LibrarianDB> librarians = new ArrayList<>();
		try {
			librarians = BisisApp.bisisService.getAllLibrarinasInThisLibrary(BisisApp.appConfig.getLibrary()).execute().body();
//			librarians = libList.stream().map(i -> new Librarian(i))
//										 .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return librarians;
	 }
	
	public static List<ProcessType> getAllProcTypes(){
		List<ProcessType> processTypeList = new ArrayList<ProcessType>();
		try {
			List<ProcessTypeDB> processTypeListDB = BisisApp.bisisService.getProcessTypesForLibrary(BisisApp.appConfig.getLibrary()).execute().body();

			for(ProcessTypeDB ptDB:processTypeListDB){
				processTypeList.add(new ProcessType(ptDB));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return processTypeList;
	}


//	public static List<Location> getLocations(){
//		List<Location> locations = null;
//		try {
//			locations = BisisApp.bisisService.getLocations(BisisApp.appConfig.getLibrary()).execute().body();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return locations;
//	}
//
//	public static List<CircLocation> getCircLocations(){
//		List<CircLocation> locations = null;
//		try {
//			locations = BisisApp.bisisService.getCircLocations(BisisApp.appConfig.getLibrary()).execute().body();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return locations;
//	}
//
//
//	public static boolean addLibrarian(LibrarianDB lib){
//
//		if (lib.getNapomena() != null) {
//			String napomena = lib.getNapomena().replace("'", "").replace("\"", "");
//			lib.setNapomena(napomena);
//		}
//		try {
//			 BisisApp.bisisService.createLibrarian(lib).execute();
//			 return true;
//		} catch (IOException e) {
//			 e.printStackTrace();
//			 return false;
//		}
//	}

	public static boolean updateLibrarian(LibrarianDB lib){

		if (lib.getNapomena() != null) {
			String napomena = lib.getNapomena().replace("'", "").replace("\"", "");
			lib.setNapomena(napomena);
		}
		try {
			BisisApp.bisisService.updateLibrarian(lib).execute().body();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateLibrarianContext(Librarian lib) {
		LibrarianDB librarianDB = new LibrarianDB(lib);
		return updateLibrarian(librarianDB);
	}

//	public static String createOpacWebAccount(LibrarianDB librarian) throws Exception {
//		if (librarian == null)
//			throw new Exception(Messages.getString("USER_MANAGER_USER_NOT_LOADED"));
//		if (DataValidator.validateEmail(librarian.getEmail()) == DataErrors.EMAIL_FORMAT_INVALID)
//			throw new Exception(Messages.getString(DataErrors.EMAIL_FORMAT_INVALID.getMessageKey()));
//		if (librarian.hasRole(Librarian.Role.OPACADMIN))
//			throw new Exception(Messages.getString(""));
//		LibraryMember libraryMember = new LibraryMember();
//		libraryMember.setAuthorities(new ArrayList<>(Arrays.asList(Authority.ROLE_ADMIN)));
//		libraryMember.setUsername(librarian.getEmail());
//		libraryMember.setLibraryPrefix(BisisApp.appConfig.getLibrary());
////		libraryMember.setIndex();
//        libraryMember.setLibrarianIndex(librarian.get_id());
//		libraryMember.setProfileActivated(false);
//		Response<LibraryMember> createdMemberResp = BisisApp.bisisService.createWebAccount(libraryMember).execute();
//		if (createdMemberResp.code() == 409)
//			throw new Exception(Messages.getString("USER_MANAGER_EMAIL_ALREADY_EXIST"));
//		if (createdMemberResp.code() == 417)
//			throw new Exception(Messages.getString("USER_MANAGER_INVALID_USER_DATA"));
//		if (createdMemberResp.body() == null)
//			throw new Exception(Messages.getString("USER_MANAGER_CONNECTION_ERROR"));
//		return Messages.getString("USER_MANAGER_ACTIVATION_EMAIL_SENT");
//	}
//
//	public static boolean deleteLibrarian(LibrarianDB lib){
//
//		try {
//			BisisApp.bisisService.deleteLibraian(lib).execute();
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
	
	public static ProcessType saveProcessType(ProcessType pt){
		ProcessTypeDB processTypeDB = new ProcessTypeDB(pt);
		try {
			return new ProcessType(BisisApp.bisisService.saveProcessType(processTypeDB).execute().body());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean deleteProcessType(ProcessType pt){
		
 		return false;
	}
		
	
	private static Logger log = Logger.getLogger(LibEnvProxy.class);
}
