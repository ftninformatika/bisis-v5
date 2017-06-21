package com.ftninformatika.bisis.config;

import com.ftninformatika.bisis.librarian.Librarian;
import lombok.*;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

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


    retrofit = new Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

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

    this.retrofit = new Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
