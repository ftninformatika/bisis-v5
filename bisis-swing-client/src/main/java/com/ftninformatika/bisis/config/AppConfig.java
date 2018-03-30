package com.ftninformatika.bisis.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.utils.RetrofitUtils;
import lombok.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

  public AppConfig(String serverUrl, Librarian librarian, String library, String token) {
    this.serverUrl = serverUrl;
    this.librarian = librarian;
    this.library = library;

    setRetrofit(token, library);

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

  public void setRetrofit(String token, String domain) {
    OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
    okHttpClient.readTimeout(60, TimeUnit.SECONDS);


    String finalToken = token;
    okHttpClient.addInterceptor(chain -> {
      Request req = chain.request();
      Request.Builder newRequest = req.newBuilder()
          .header("Authorization", finalToken)
          .header("Library", domain);
      return chain.proceed(newRequest.build());
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
