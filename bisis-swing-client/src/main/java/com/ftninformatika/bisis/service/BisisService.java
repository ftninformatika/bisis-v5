package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.coders.*;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchModel;
import com.google.gson.JsonObject;
import retrofit2.http.*;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.List;

public interface BisisService {

    @Headers({"ContentType: application/json"})
    @POST("/auth")
    Call<ResponseBody> getToken(@Body UserCredentials creds);

    @GET("/circ")
    Call<ResponseBody> getMembers();

    @GET("/libraries")
    Call<ResponseBody> getLibs();

    @GET("/configs/search/getByLibraryName")
    Call<LibraryConfiguration> getConfiguration(@Query("libName") String libName);

    @POST("/process_types")
    Call<Void> addProcessType(@Body ProcessType processType);

    @GET("/process_types/search/getProcessTypesByLibNameIsNullOrLibName")
    Call<JsonObject> getProcessTypesForLibrary(@Query("libName") String libName);

//librarians------------------------------------------------------------
    @GET("/librarians/search/getByUsername")
    Call<Librarian> getLibrarian(@Query("username") String username);

    @GET("/librarians/search/getLibrariansByBiblioteka")
    Call<JsonObject> getAllLibrarinasInThisLibrary(@Query("library") String library);

    @POST("/librarians")
    Call<Void> createLibrarian(@Body Librarian librarian);

    @PUT("/librarians")
    Call<Librarian> updateLibrarian(@Body Librarian librarian);

    @HTTP(method = "DELETE", path = "/librarians", hasBody = true)
    Call<Void> deleteLibraian(@Body Librarian librarian);


//records---------------------------------------------------------------
    @GET("/mongo_repository_records?size=20&")
    Call<JsonObject> getAllRecords(@Query("number") int pageNumber);

    @GET("/records/{recordId}")
    Call<Record> getOneRecord(@Path("recordId") String recordId);

    @GET("/mongo_repository_records/search/deleteByRecordID")
    Call<Long> deleteRecordByRecId(@Query("id") int id);

    @Headers({"ContentType: application/json"})
    @GET("/mongo_repository_records/search/getByID")
    Call<Record> getRecordById(@Query("id") int id);

    @POST("/records/search")
    Call<List<Record>> searchRecords(@Body String queryString);

    @GET("/expand_prefix_controller")
    Call<List<String>> getExpand(@Query("prefix") String prefix, @Query("text") String text);

    @POST("/records/query")
    Call<List<Record>> queryRecords(@Body SearchModel sm);

    @GET("/records/clear_elastic")
    Call<String> clearElasticStorage();

    @GET("/records/fill_elastic")
    Call<String> fillElasticStorage();

    @POST("/records")
    Call<Record> createRecord(@Body Record rec);

    @POST("/records")
    Call<Record> updateRecord(@Body Record rec);

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

    @GET("/coders/membership_type")
    Call<List<MembershipType>> getMembershipTypes(@Query("libName")String libName);

    @GET("/coders/user_category")
    Call<List<UserCategory>> getUserCategories(@Query("libName")String libName);

    @GET("/coders/warning_type")
    Call<List<WarningType>> getWarningTypes(@Query("libName")String libName);

    @GET("/coders/organization")
    Call<List<Organization>> getOrganizations(@Query("libName")String libName);




}