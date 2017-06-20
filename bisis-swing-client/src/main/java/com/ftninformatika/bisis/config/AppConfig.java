package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;


/**
 * Created by Petar on 6/20/2017.
 */

public abstract class AppConfig {

    private String serverUrl = "http://127.0.0.1:8080";
    private Librarian librarian;
    private String library;
    private Retrofit retrofit;

    public AppConfig(String serverUrl, Librarian librarian, String library, String token) {
        this.serverUrl = serverUrl;
        this.librarian = librarian;
        this.library = library;


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();


        String finalToken = token;
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request req = chain.request();
                Request.Builder newRequest = req.newBuilder()
                        .header("Authorization", finalToken)
                        .header("Library", library);



                return chain.proceed(newRequest.build());
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public AppConfig() {
    }


    public void setRetrofit(String token, String domain){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();


        String finalToken = token;
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request req = chain.request();
                Request.Builder newRequest = req.newBuilder()
                        .header("Authorization", finalToken)
                        .header("Library", domain);



                return chain.proceed(newRequest.build());
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    public Retrofit getRetrofit(){
        return this.retrofit;
    }

    private static String getDomainFromUsername(String username){
        String retVal = "";

        retVal = username.split(".com")[0].split("@")[1];

        return retVal;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public String getLibrary() {
        return library;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setLibrarian(Librarian librarian) {
        this.librarian = librarian;
    }

    public void setLibrary(String library) {
        this.library = library;
    }
}
