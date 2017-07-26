package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */
import com.ftninformatika.bisis.coders.StatusPrimerka;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchModel;
import com.google.gson.JsonObject;
import org.springframework.data.repository.query.Param;
import retrofit2.http.*;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.List;

public interface BisisService {

    @Headers({"ContentType: application/json"})
    @POST("/auth")
    Call<ResponseBody> getToken(@Body UserCredentials creds);

    @GET("/members")
    Call<ResponseBody> getMembers();

    @Headers({"ContentType: application/json"})
    @GET("/libraries")
    Call<ResponseBody> getLibs();

    @Headers({"ContentType: application/json"})
    @GET("/librarians/search/getByUsername")
    Call<Librarian> getLibrarian(@Query("username") String username);

    @GET("/librarians/search/getLibrariansByBiblioteka")
    Call<JsonObject> getAllLibrarinasInThisLibrary(@Query("library") String library);

    @GET("/configs/search/getByLibraryName")
    Call<LibraryConfiguration> getConfiguration(@Query("libName")String libName);

    @GET("/mongo_repository_records?size=20&")
    Call<JsonObject> getAllRecords(@Query("number") int pageNumber);

    @GET("/records/59391f89ea9b8fbe1ed417f7")
    Call<Record> getOneRecord();

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

    @GET("/status_primerka")
    Call<JsonObject> getStatusCoders();

    @GET("/records/clear_elastic")
    Call<String> clearElasticStorage();

    @GET("/records/fill_elastic")
    Call<String> fillElasticStorage();

    @POST("/records")
    Call<Record> createRecord(@Body Record rec);

    @PUT("/records")
    Call<Record> updateRecord(@Body Record rec);

}