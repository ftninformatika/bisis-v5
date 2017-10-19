package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.circ.wrappers.MemberData;
import com.ftninformatika.bisis.models.coders.*;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.bisis.search.SearchModel;
import com.google.gson.JsonObject;
import okhttp3.RequestBody;
import retrofit2.http.*;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.List;

public interface BisisService {

    @POST("/auth")
    Call<ResponseBody> getToken(@Body UserCredentials creds);

    @GET("/circ")
    Call<ResponseBody> getMembers();

    @GET("/libraries")
    Call<ResponseBody> getLibs();

    @GET("/configs/search/getByLibraryName")
    Call<LibraryConfiguration> getConfiguration(@Query("libName") String libName);

    @POST("/coders/process_types")
    Call<Void> addProcessType(@Body ProcessTypeDTO processType);

    @GET("/coders/process_types/getByLibrary")
    Call<List<ProcessTypeDTO>> getProcessTypesForLibrary(@Query("libName") String libName);

    @GET("sveske/{invNum}")
    Call<Sveska> getSveskaByInvNum(@Path("invNum") String invNum);

    @GET("/lendings/getLendingsByUserId")
    Call<List<Lending>> getLendingsByUserId(@Query("userId") String userId);

//primerci--------------------------------------------------------------

    @GET("primerci/{ctlgno}")
    Call<Primerak> getPrimerakByInvNum(@Path("ctlgno") String ctlgno);

//members---------------------------------------------------------------

//    @GET("/members/getById")
//    Call<Member> getMemberById(@Query("userId") String userId);

    @GET("/members/memberExist")
    Call<String> memberExist(@Query("userId") String userId);

    @POST("/members/addUpdate")
    Call<Member> addUpdateMember(@Body Member member);

//librarians------------------------------------------------------------

    @GET("/librarians/getByUsername")
    Call<LibrarianDTO> getLibrarianByUsername(@Query("username") String username);

    @GET("/mongo_repository_librarians/search/getByUsername")
    Call<LibrarianDTO> getLibrarian(@Query("username") String username);

    @GET("/librarians/getByLibrary")
    Call<List<LibrarianDTO>> getAllLibrarinasInThisLibrary(@Query("library") String library);

    @POST("/mongo_repository_librarians")
    Call<Void> createLibrarian(@Body LibrarianDTO librarian);

    @POST("/librarians/update")
    Call<Boolean> updateLibrarian(@Body LibrarianDTO librarian);

    @HTTP(method = "DELETE", path = "/mongo_repository_librarians", hasBody = true)
    Call<Void> deleteLibraian(@Body LibrarianDTO librarian);

//records---------------------------------------------------------------
    @GET("/records/getRecordForPrimerak")
    Call<Record> getRecordForPrimerak(@Query("ctlgno") String ctlgno);

    @GET("/records/getRecordForSveska")
    Call<Record> getRecordForSveska(@Query("ctlgno") String ctlgno);

    @GET("/mongo_repository_records?size=20&")
    Call<JsonObject> getAllRecords(@Query("number") int pageNumber);

    @GET("/records/{recordId}")
    Call<Record> getOneRecord(@Path("recordId") String recordId);

    @GET("/mongo_repository_records/search/deleteByRecordID")
    Call<Long> deleteRecordByRecId(@Query("id") int id);

    @GET("/mongo_repository_records/search/getByID")
    Call<Record> getRecordById(@Query("id") int id);

    @POST("/records/search_ids") //vraca kolekciju id-jeva
    Call<List<String>> searchRecordsIds(@Body SearchModel searchModel);

    @POST("/records/multiple_ids")
    Call<List<Record>>  getRecordsByIds(@Body List<String> idList);

    @GET("/expand_prefix_controller")
    Call<List<String>> getExpand(@Query("prefix") String prefix, @Query("text") String text);

    @POST("/records/query")
    Call<List<Record>> queryRecords(@Body SearchModel sm);

    @GET("/records/clear_elastic")
    Call<Boolean> clearElasticStorage();

    @GET("/records/fill_elastic")
    Call<Boolean> fillElasticStorage();

    @POST("/records")
    Call<Record> createRecord(@Body Record rec);

    @POST("/records")
    Call<Record> updateRecord(@Body Record rec);

    @GET("/records/get_and_lock")
    Call<Record> getAndLockRecord(@Query("recId") String recId, @Query("librarianId") String librarianId);

    @GET("/records/lock")
    Call<String> lockRecord(@Query("recId") String recId, @Query("lirbrarianId") String librarianId);

    @GET("/records/unlock")
    Call<String> unlockRecord(@Query("recId") String recId);

    @HTTP(method = "DELETE", path = "/records", hasBody = true)
    Call<Boolean> deleteRecord(String recID);

    @GET("/records/getRecord")
    Call<Record> getRecordByCtlgNo(@Query("ctlgno") String ctlgno);
    // full record

    @GET("/records/getWrapperRecord")
    Call<Record> getWrapperRecordByCtlgNo(@Query("ctlgno") String ctlgno);
    // wrappovani

    //coders----------------------------------------------
    @GET("/coders/accession_register")
    Call<List<AccessionRegister>> getAccessionRegs(@Query("libName")String libName);

    @GET("/coders/acquisiton_type")
    Call<List<Acquisition>> getAcquisitonTypes(@Query("libName")String libName);

    @GET("/coders/availability")
    Call<List<Availability>> getAvailabilities(@Query("libName")String libName);

    @GET("/coders/binding")
    Call<List<Binding>> getBindings(@Query("libName")String libName);

    @GET("/coders/format")
    Call<List<Format>> getFormats(@Query("libName")String libName);

    @GET("/coders/internal_mark")
    Call<List<InternalMark>> getInterMarks(@Query("libName")String libName);

    @GET("/coders/item_status")
    Call<List<ItemStatus>> getStatusCoders(@Query("libName")String libName);

    @GET("/coders/location")
    Call<List<Location>> getLocations(@Query("libName")String libName);

    @GET("/coders/sublocation")
    Call<List<Sublocation>> getSubLocations(@Query("libName")String libName);

//coders circulation----------------------------------------------------------

    @GET("/coders/circlocation")
    Call<List<CircLocation>> getCircLocations(@Query("libName")String libName);

    @GET("/coders/corporatemember")
    Call<List<CorporateMember>> getCorporateMembers(@Query("libName")String libName);

    @GET("/coders/education")
    Call<List<EducationLvl>> getEducationLvls(@Query("libName")String libName);

    @GET("/coders/language")
    Call<List<Language>> getLanguages(@Query("libName")String libName);

    @GET("/coders/place")
    Call<List<Place>> getPlaces(@Query("libName")String libName);

    @GET("/coders/education")
    Call<List<EducationLvl>> getEducations(@Query("libName")String libName);

    @GET("/coders/membership")
    Call<List<Membership>> getMemberships(@Query("libName")String libName);

    @POST("/memberships")
    Call<Membership> addMembership(@Body Membership membership);

    @HTTP(method = "DELETE", path = "/memberships", hasBody = true)
    Call<Boolean> deleteMembership(@Body String membershipId);
    //vratice true ako je uspesno obrisan, a ako ga nije pronasao vraca false!

    @GET("/coders/membership_type")
    Call<List<MembershipType>> getMembershipTypes(@Query("libName")String libName);

    @GET("/coders/user_category")
    Call<List<UserCategory>> getUserCategories(@Query("libName")String libName);

    @GET("/coders/warning_type")
    Call<List<WarningType>> getWarningTypes(@Query("libName")String libName);

    @GET("/coders/organization")
    Call<List<Organization>> getOrganizations(@Query("libName")String libName);

    @GET("/circ_configs/search/findByLibrary")
    Call<CircConfig> getCircConfigs(@Query("libname") String libName);

    @GET("/corporate_members/getById")
    Call<CorporateMember> getCorporateMemberById(@Query("userId") String userId);

    @GET("circ_location/lastUserId")
    Call<Integer> getLastUserId(@Query("location") Integer location);
    //za odgovarajucu lokaciju treba da poveca last_user_id i vrati vrednost (pogledati komandu GetLastUserId)
    //vraca null ako nesto ne valja, location_id je location, iz hedera info koja je biblioteka,vazi i za prethodne

    //TODO


    @POST("/members/saveCorporateUser")
    Call<Boolean> saveCorporateMember(@Body CorporateMember corporateMember);
    //fali i informacija kod kog korisnika se cuva????







//------------za posle

    @GET("/members/getAndLockById")
    Call<MemberData> getAndLockMemberById(@Query("userId") String userId, @Query("librarianId") String librarianId);
    //librarianId vuces iz appConfig.getLibrarian().getId treba da update-uje inUseBy,
    //vraca objekat sa samo inUseBy ako je zakljucan, vraca sve ako je tek zakljucao i inUseBy null(a u bazi cuva inUseBy bibliotekara)
    //vraca null ako ne postoji


    @GET("/members/getById")
    Call<MemberData> getMemberById(@Query("userId") String userId);
    //  getandlock, unlock i get klasican, samo promeniti sta vraca

    @GET("/members/releaseById")
    Call<Boolean> releaseMemberById(@Query("userId") String userId);
    // treba da update-uje inUseBy na null, unlock

    @POST("/members/addUpdate")
    Call<Boolean> addUpdateMember(@Body MemberData memberData);
    // potrpati sve u svoje kolekcije, vrati true, false
}