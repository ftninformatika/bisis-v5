package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */

import com.ftninformatika.bisis.circ.*;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.circ.wrappers.WarningsData;
import com.ftninformatika.bisis.coders.*;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.librarian.dto.ProcessTypeDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.*;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.SearchModelCirc;
import com.ftninformatika.bisis.search.SearchModelMember;
import com.ftninformatika.utils.PathDate;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

public interface BisisService {

    @POST("auth")
    Call<ResponseBody> getToken(@Body UserCredentials creds);

    @GET("circ")
    Call<ResponseBody> getMembers();

    @GET("libraries")
    Call<ResponseBody> getLibs();

    @GET("configs/search/getByLibraryName")
    Call<LibraryConfiguration> getConfiguration(@Query("libName") String libName);

    @POST("coders/process_types")
    Call<Void> addProcessType(@Body ProcessTypeDTO processType);

    @GET("coders/process_types/getByLibrary")
    Call<List<ProcessTypeDTO>> getProcessTypesForLibrary(@Query("libName") String libName);

    @GET("sveske/{invNum}")
    Call<Sveska> getSveskaByInvNum(@Path("invNum") String invNum);

    @GET("lendings/getLendingsByUserId")
    Call<List<Lending>> getLendingsByUserId(@Query("userId") String userId);

//primerci--------------------------------------------------------------

    @GET("primerci/{ctlgno}")
    Call<Primerak> getPrimerakByInvNum(@Path("ctlgno") String ctlgno);

//members---------------------------------------------------------------

//    @GET("/members/getById")
//    Call<Member> getMemberById(@Query("userId") String userId);

    @GET("members/memberExist")
    Call<String> memberExist(@Query("userId") String userId);

    @POST("members/addUpdate")
    Call<Member> addUpdateMember(@Body Member member);

    /**
     *
     * @param userId - ID korisnika (nije mongoId!!!)
     * @param librarianId - mongodId bibliotekara
     * @return null - ako ne pronadje bibliotekara ili korisnika
     *         MemberData objekat, bez inUseBy propertija (inUseBy azuriran i sacuvan kod Member- a) - ako je uspoesno zakljucao
     *         MemberData objekat, koji sadrzi samo inUseBy (ostalo null) - ako je vec zakljucan
     */
    @GET("members/getAndLock")
    Call<MemberData> getAndLockMemberById(@Query("userId") String userId, @Query("librarianId") String librarianId);

    /**
     *
     * @param userId
     * @return false - ako ne postoji korisnik za taj userId
     *         true - ako postoji i promeni inUseBy na null
     */
    @GET("members/releaseById")
    Call<Boolean> releaseMemberById(@Query("userId") String userId);

    @GET("members/getMemberDataById")
    Call<MemberData> getMemberDataById(@Query("userId") String userId);

    @POST("members/addUpdateMemberData")
    Call<MemberData> addUpdateMemberData(@Body MemberData memberData);

//librarians------------------------------------------------------------

    @GET("librarians/getByUsername")
    Call<LibrarianDTO> getLibrarianByUsername(@Query("username") String username);

    @GET("mongo_repository_librarians/search/getByUsername")
    Call<LibrarianDTO> getLibrarian(@Query("username") String username);

    @GET("librarians/getByLibrary")
    Call<List<LibrarianDTO>> getAllLibrarinasInThisLibrary(@Query("library") String library);

    @POST("mongo_repository_librarians")
    Call<Void> createLibrarian(@Body LibrarianDTO librarian);

    @POST("librarians/update")
    Call<Boolean> updateLibrarian(@Body LibrarianDTO librarian);

    @HTTP(method = "DELETE", path = "mongo_repository_librarians", hasBody = true)
    Call<Void> deleteLibraian(@Body LibrarianDTO librarian);

//records---------------------------------------------------------------

    @GET("mongo_repository_records?size=20&")
    Call<JsonObject> getAllRecords(@Query("number") int pageNumber);

    @GET("records/{recordId}")
    Call<Record> getOneRecord(@Path("recordId") String recordId);

    @GET("mongo_repository_records/search/deleteByRecordID")
    Call<Long> deleteRecordByRecId(@Query("id") int id);

    @GET("mongo_repository_records/search/getByID")
    Call<Record> getRecordById(@Query("id") int id);

    @POST("records/search_ids") //vraca kolekciju id-jeva
    Call<List<String>> searchRecordsIds(@Body SearchModel searchModel);

    @POST("records/search_ids_result") //vraca kolekciju id-jeva
    Call<Result> searchRecordsIdsResult(@Body SearchModel searchModel);

    @POST("records/search_ids_circ") //vraca kolekciju id-jeva
    Call<List<String>> searchRecordsIdsCirc(@Body SearchModelCirc searchModel);

    @POST("records/multiple_ids")
    Call<List<Record>> getRecordsByIds(@Body List<String> idList);

    @GET("expand_prefix_controller")
    Call<List<String>> getExpand(@Query("prefix") String prefix, @Query("text") String text);

    @POST("records/query")
    Call<List<Record>> queryRecords(@Body SearchModel sm);

    @POST("records/querywrapper")
    Call<List<RecordResponseWrapper>> queryRecordsWrapper(@Body SearchModel sm);

    @GET("records/clear_elastic")
    Call<Boolean> clearElasticStorage();

    @GET("records/fill_elastic")
    Call<Boolean> fillElasticStorage();

    @POST("records")
    Call<Record> createRecord(@Body Record rec);

    @POST("records")
    Call<Record> updateRecord(@Body Record rec);

    @GET("records/get_and_lock")
    Call<Record> getAndLockRecord(@Query("recId") String recId, @Query("librarianId") String librarianId);

    @GET("records/lock")
    Call<String> lockRecord(@Query("recId") String recId, @Query("lirbrarianId") String librarianId);

    @GET("records/unlock")
    Call<String> unlockRecord(@Query("recId") String recId);

    @GET("records/delete/{mongoID}")
    Call<Boolean> deleteRecord(@Path("mongoID") String mongoID);

    @GET("records/getRecord")
    Call<Record> getRecordByCtlgNo(@Query("ctlgno") String ctlgno);
    // full record

    @GET("records/getWrapperRecord")
    Call<Record> getWrapperRecordByCtlgNo(@Query("ctlgno") String ctlgno);
    // wrappovani

    //coders----------------------------------------------
    @GET("coders/accession_register")
    Call<List<AccessionRegister>> getAccessionRegs(@Query("libName")String libName);

    @GET("coders/acquisiton_type")
    Call<List<Acquisition>> getAcquisitonTypes(@Query("libName")String libName);

    @GET("coders/availability")
    Call<List<Availability>> getAvailabilities(@Query("libName")String libName);

    @GET("coders/binding")
    Call<List<Binding>> getBindings(@Query("libName")String libName);

    @GET("coders/format")
    Call<List<Format>> getFormats(@Query("libName")String libName);

    @GET("coders/internal_mark")
    Call<List<InternalMark>> getInterMarks(@Query("libName")String libName);

    @GET("coders/item_status")
    Call<List<ItemStatus>> getStatusCoders(@Query("libName")String libName);

    @GET("coders/location")
    Call<List<Location>> getLocations(@Query("libName")String libName);

    @GET("coders/sublocation")
    Call<List<Sublocation>> getSubLocations(@Query("libName")String libName);

    @GET("coders/increment_counter")
    Call<Integer> incrementCounter(@Query("counterKey")String counterKey);

    @GET("coders/tasks")
    Call<List<Task>> getTasks(@Query("libName")String libName);

//coders circulation----------------------------------------------------------


    @GET("coders/circlocation")
    Call<List<CircLocation>> getCircLocations(@Query("libName")String libName);

    @GET("coders/counters")
    Call<List<Counter>> getCounters(@Query("libName")String libName);

    @GET("coders/corporatemember")
    Call<List<CorporateMember>> getCorporateMembers(@Query("libName")String libName);

    @GET("coders/education")
    Call<List<EducationLvl>> getEducationLvls(@Query("libName")String libName);

    @GET("coders/language")
    Call<List<Language>> getLanguages(@Query("libName")String libName);

    @GET("coders/place")
    Call<List<Place>> getPlaces(@Query("libName")String libName);

    @GET("coders/education")
    Call<List<EducationLvl>> getEducations(@Query("libName")String libName);

    @GET("coders/membership")
    Call<List<Membership>> getMemberships(@Query("libName")String libName);

    @POST("memberships")
    Call<Membership> addMembership(@Body Membership membership);

    @HTTP(method = "DELETE", path = "memberships", hasBody = true)
    Call<Boolean> deleteMembership(@Body String membershipId);
    //vratice true ako je uspesno obrisan, a ako ga nije pronasao vraca false!

    @GET("coders/membership_type")
    Call<List<MembershipType>> getMembershipTypes(@Query("libName")String libName);

    @GET("coders/user_category")
    Call<List<UserCategory>> getUserCategories(@Query("libName")String libName);

    @GET("coders/warning_type")
    Call<List<WarningType>> getWarningTypes(@Query("libName")String libName);

    @GET("coders/warning_counter")
    Call<List<WarningCounter>> getWarningCounters(@Query("libName")String libName);

    @GET("coders/organization")
    Call<List<Organization>> getOrganizations(@Query("libName")String libName);

    @GET("circ_configs/search/findByLibrary")
    Call<CircConfig> getCircConfigs(@Query("libname") String libName);

    @POST("circ_configuration/save")
    Call<CircConfig> saveCircConfigs(@Body CircConfig circConfig);

    @GET("corporate_members/getById")
    Call<CorporateMember> getCorporateMemberById(@Query("userId") String userId);

    /**
     *
     * @param location - locationCode
     * @return za odgovarajucu lokaciju treba da poveca last_user_id i vrati vrednost (pogledati komandu GetLastUserId)
     *         vraca null ako nesto ne valja
     */
    @GET("circ_location/lastUserId")
    Call<Integer> getLastUserId(@Query("location") String location);

    @POST("corporate_members/save")
    Call<Boolean> saveCorporateMember(@Body CorporateMember corporateMember);

    @GET("itemAvailabilities/getByCtlgNo")
    Call<ItemAvailability> getItemAvailability(@Query("ctlgno") String ctlgno);

   //reports

    @GET ("reports/all")
    Call<List<String>> getReports(@Query("reportType") String reportType, @Query("reportName") String reportName);

    @GET ("reports/byFullName")
    Call<GeneratedReport> getReport(@Query("reportFullName") String reportFullName);


    @POST ("search/circ/recordIds")
    Call<List<String>> searchBooks(@Body SearchModelCirc searchModel);


    @POST("records/multiple_ids_wrapper")
    Call<List<RecordResponseWrapper>> getRecordsAllDataByIds(@Body List<String> idList);


    @POST ("search/circ/members")
    Call<List<Member>> searchMembers(@Body SearchModelMember searchModel);

    @GET ("members/getCharged")
    Call<Member> getChargedUser(@Query("ctlgNo") String ctlgNo);

    @GET ("members/getLending")
    Call<Lending> getLending(@Query("ctlgNo") String ctlgNo);

    @POST ("members/dischargeBook")
    Call<Boolean> dischargeBook(@Body Lending lending);


    //circ reports

    @GET ("circ_report/get_lending_history")
    Call<List<Report>> getLendingHistory(@Query("memberNo") String memberNo, @Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_members_with_categories")
    Call<List<Report>> getMembersWithCategories(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_book_history_report")
    Call<List<Report>> getBookHistoryReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("ctlgno") String ctlgNo, @Query("location") String location);

    @GET ("circ_report/get_librarian_report")
    Call<List<Report>> getLibrarianReport(@Query("date") PathDate date, @Query("location") String location);

    @GET ("circ_report/get_mmbr_type_report")
    Call<List<Report>> getMmbrTypeReport(@Query("date") PathDate date, @Query("location") String location);

    @GET ("circ_report/get_visitors_report")
    Call<List<Report>> getVisitorsReport(@Query("date") PathDate date, @Query("location") String location);

    @GET ("circ_report/get_member_book_report")
    Call<List<Report>> getMemberBookReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_group_report")
    Call<List<CorporateMember>> getGroupReport(@Query("location") String location);

    @GET ("circ_report/get_member_by_group_report")
    Call<List<Member>> getMemberByGroupReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("institution") String institution, @Query("location") String location);

    @GET ("circ_report/get_best_reader_report")
    Call<List<Report>> getBestReaderReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_best_book_report")
    Call<List<Report>> getBestBookReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_librarian_statistic_report")
    Call<List<Report>> getLibrarianStatisticReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_blocked_report")
    Call<List<Report>> getBlockedReport( @Query("location") String location);

    @GET ("circ_report/get_lend_return_language_report")
    Call<Map<String, Report>> getLendReturnLanguageReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_categoria_report")
    Call<List<Report>> getCategoriaReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_mmbr_type_struct_report")
    Call<List<Report>> getMmbrTypeStructReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_free_signing_report")
    Call<Long> getFreeSigningReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_users_number_report")
    Call<Long> getUsersNumberReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_gender_report")
    Call<List<Report>> getGenderReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_best_book_udk")
    Call<List<Report>> getBestBookUdk(@Query("start") PathDate start, @Query("end") PathDate end, @Query("udk") String udk, @Query("location") String location);

    @GET ("circ_report/get_lend_return_udk_report")
    Call<Map<String, Report>> getLendReturnUdkReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_ctgr_udk_report")
    Call<Map<String, Report>> getCtgrUdkReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_picturebooks_report")
    Call<Report> getPicturebooksReport(@Query("start") PathDate start, @Query("end") PathDate end/*, @Query("location") String location*/);

    @GET ("circ_report/get_visitor_structure_report")
    Call<Map<String, Map<String, Integer>>> getVisitorStructureReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET ("circ_report/get_zb_statistic_report")
    Call<Report> getZbStatisticReport(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @GET("circ_report/get_total_signed_from_start_of_year")
    Call<Integer> getTotalSignedMembersFromStartOfYear(@Query("locaton") String location);


    @GET ("members/getWarnMembers")
    Call<List<MemberData>> getWarnMembers(@Query("start") PathDate start, @Query("end") PathDate end, @Query("location") String location);

    @POST("members/addWarnings")
    Call<Boolean> addWarnings(@Body WarningsData warningsData);

    @POST("coders/addWarningType")
    Call<Boolean> addWarningType(@Body WarningType warningType);

}
