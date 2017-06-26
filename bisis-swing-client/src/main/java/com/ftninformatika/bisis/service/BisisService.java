package com.ftninformatika.bisis.service;

/**
 * Created by Petar on 6/20/2017.
 */
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.records.Record;
import retrofit2.http.*;
import okhttp3.ResponseBody;
import retrofit2.Call;

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

}