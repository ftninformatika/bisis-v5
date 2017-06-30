package com.ftninformatika.utils;

import com.ftninformatika.bisis.config_model.LibraryConfiguration;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.service.BisisService;
import com.ftninformatika.bisis.service.UserCredentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RetrofitUtils {

    public static String acquireToken(String serverUrl, String username, String password) {
			/*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();  // zbog proxija u jugodrvu ako gadja neki public api
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			okHttpClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS));*/
        Retrofit rf = new Retrofit.Builder()
                //.client(okHttpClient.build())
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BisisService bs = rf.create(BisisService.class);

        Call<ResponseBody> ans = bs.getToken(new UserCredentials(username,password));
        final String[] token = new String[1];
        try {
            token[0] = ans.execute().body().string();
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
        token[0] = token[0].split(":")[1];
        token[0] = token[0].substring(1, token[0].length()-2);

        return token[0];
    }


    public static LibraryConfiguration acquireLibraryConfiguration(String libNanme, Retrofit preparedRetrofitInstance){
        LibraryConfiguration retVal = null;

        BisisService bisisService =preparedRetrofitInstance.create(BisisService.class);
        Call<LibraryConfiguration> lib = bisisService.getConfiguration(libNanme);

        try {
            retVal = lib.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

}
