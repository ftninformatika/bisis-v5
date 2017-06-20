package com.ftninformatika.utils;

import com.ftninformatika.bisis.backend_api.BisisService;
import com.ftninformatika.bisis.backend_api.UserCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by Petar on 6/20/2017.
 */
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
            e.printStackTrace();
            return null;
        }
        token[0] = token[0].split(":")[1];
        token[0] = token[0].substring(1, token[0].length()-2);

        return token[0];
    }

}
