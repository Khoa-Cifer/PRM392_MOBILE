package com.myfirstandroidjava.salesapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Make sure to use your computer's IP address, not localhost
    // 10.0.2.2 is the special alias for the Android emulator to access the host machine's localhost
    private static final String BASE_URL = "http://10.0.2.2:5099/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}