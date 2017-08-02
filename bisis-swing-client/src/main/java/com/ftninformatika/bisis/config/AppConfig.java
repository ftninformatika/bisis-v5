package com.ftninformatika.bisis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.utils.GsonUTCDateAdapter;
import com.ftninformatika.utils.RetrofitUtils;
import com.google.gson.*;
import lombok.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class AppConfig {

  private String serverUrl = "http://127.0.0.1:8080";
  private String baseUrl = "http://localhost:8080";
  private Librarian librarian;
  private String library;
  private Retrofit retrofit;
  private LibraryConfiguration clientConfig;
  private CodersHelper codersHelper;

  public AppConfig(String serverUrl, Librarian librarian, String library, String token) {
    this.serverUrl = serverUrl;
    this.librarian = librarian;
    this.library = library;

    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

    String finalToken = token;
    okHttpClient.addInterceptor(chain -> {
      Request req = chain.request();
      Request.Builder newRequest = req.newBuilder()
          .header("Authorization", finalToken)
          .header("Library", library);
      return chain.proceed(newRequest.build());
    });

    Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
                    .create();

    retrofit = new Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient.build())
        .build();

  }

  public void initCoders(){
    this.codersHelper = new CodersHelper();
    this.codersHelper.init();
  }

  public void setRetrofit(String token, String domain) {
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();


    String finalToken = token;
    okHttpClient.addInterceptor(chain -> {
      Request req = chain.request();
      Request.Builder newRequest = req.newBuilder()
          .header("Authorization", finalToken)
          .header("Library", domain);
      return chain.proceed(newRequest.build());
    });

    Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Date.class, new GsonUTCDateAdapter())
            .create();

    this.retrofit = new Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();



  }

  public void setLibraryConfiguration(String libName, Retrofit ret){

    clientConfig = RetrofitUtils.acquireLibraryConfiguration(libName, ret);

  }
}
