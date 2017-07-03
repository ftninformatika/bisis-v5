package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.records.Record;
import com.google.gson.JsonObject;
import org.springframework.data.repository.query.Param;
import retrofit2.http.*;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.ArrayList;

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

    @GET("/records/59391f89ea9b8fbe1ed417f7")
    Call<Record> getOneRecord();

    @GET("/records?size=10&")
    Call<JsonObject> getAllRecords(@Query("number") int pageNumber);

    @Headers({"ContentType: application/json"})
    @GET("/records/search/getByID")
    Call<Record> getRecordById(@Query("id") int id);

    @GET("/expand_prefix_controller")
    Call<ArrayList<String>> getExpand();

    @GET("/configs/search/getByLibraryName")
    Call<LibraryConfiguration> getConfiguration(@Query("libName")String libName);

    @GET("/records/search/deleteByRecordID")
    Call<Long> deleteRecordByRecId(@Query("id") int id);
}