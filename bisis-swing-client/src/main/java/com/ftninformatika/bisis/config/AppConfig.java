package com.ftninformatika.bisis.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.admin.coders.CodersHelper;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.utils.RetrofitUtils;
import lombok.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.log4j.Logger;
import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class AppConfig {

  private String serverUrl;// = "http://localhost:8080/bisisWS";
  private String baseUrl;// = "http://localhost:8080/bisisWS";
  private Librarian librarian;
  private String library;
  private Retrofit retrofit;
  private LibraryConfiguration clientConfig;
  private CodersHelper codersHelper;
  private static Logger log = Logger.getLogger(AppConfig.class);
  private String loginUsername;
  private String loginPassword;
  private String token;

  public AppConfig(String serverUrl, Librarian librarian, String library, String token) {
    this.serverUrl = serverUrl;
    this.librarian = librarian;
    this.library = library;

    setRetrofit(library);

//    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//
//    String finalToken = token;
//    okHttpClient.addInterceptor(chain -> {
//      Request req = chain.request();
//      Request.Builder newRequest = req.newBuilder()
//          .header("Authorization", finalToken)
//          .header("Library", library);
//      return chain.proceed(newRequest.build());
//    });
//
//
//    ObjectMapper jacksonMapper = new ObjectMapper();
//    jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//    retrofit = new Retrofit.Builder()
//        .baseUrl(serverUrl)
//        .client(okHttpClient.build())
//        .addConverterFactory(new CustomConverterFactory())
//        .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
//        .build();

  }

  public void initCoders(){
    this.codersHelper = new CodersHelper();
    this.codersHelper.init();
  }

  public void setRetrofit(String domain) {
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    okHttpClient.readTimeout(75, TimeUnit.SECONDS);


    okHttpClient.addInterceptor(chain -> {
      Request req = chain.request();
      Request.Builder newRequest = req.newBuilder()
          .header("Authorization", "Bearer " + token)
          .header("Library", domain);
      Response response = chain.proceed(newRequest.build());

      if (response.code() == 401 || response.code() == 403) {
        BisisApp.createRetrofit(loginUsername, loginPassword);
        newRequest = req.newBuilder()
                .header("Authorization", "Bearer " + token)
                .header("Library", domain);
        response = chain.proceed(newRequest.build());
      }
      return response;
    });

      HttpLoggingInterceptor.Logger fileLogger = new HttpLoggingInterceptor.Logger() {
          @Override
          public void log(String s) {
              log.info(s);
          }
      };

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(fileLogger);
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    okHttpClient.addInterceptor(interceptor);


    ObjectMapper jacksonMapper = new ObjectMapper();
    jacksonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    this.retrofit = new Retrofit.Builder()
        .baseUrl(serverUrl)
        .client(okHttpClient.build())
        .addConverterFactory(new CustomConverterFactory())
        .addConverterFactory(JacksonConverterFactory.create(jacksonMapper))
        .build();
  }

  public void setLibraryConfiguration(String libName, Retrofit ret){
    clientConfig = RetrofitUtils.acquireLibraryConfiguration(libName, ret);
  }
}
