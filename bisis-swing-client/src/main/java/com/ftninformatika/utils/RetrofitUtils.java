package com.ftninformatika.utils;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.service.AuthenticationResponse;
import com.ftninformatika.bisis.service.BisisService;
import com.ftninformatika.bisis.service.AuthenticationRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitUtils {

    public static String acquireToken(String serverUrl, String username, String password) throws IOException {
			/*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();  // zbog proxija u jugodrvu ako gadja neki public api
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			okHttpClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS));*/
        Retrofit rf = new Retrofit.Builder()
                //.client(okHttpClient.build())
                .baseUrl(serverUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        BisisService bs = rf.create(BisisService.class);

        Call<AuthenticationResponse> ans = bs.authenticate(new AuthenticationRequest(username,password));
        AuthenticationResponse authenticationResponse = ans.execute().body();

        if(authenticationResponse == null) {
            return null;
        } else {
            return authenticationResponse.getToken();
        }
    }


    public static LibraryConfiguration acquireLibraryConfiguration(String libNanme, Retrofit preparedRetrofitInstance){
        LibraryConfiguration retVal = null;

        BisisService bisisService = preparedRetrofitInstance.create(BisisService.class);
        Call<LibraryConfiguration> lib = bisisService.getConfiguration(libNanme);

        try {
            retVal = lib.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

}
