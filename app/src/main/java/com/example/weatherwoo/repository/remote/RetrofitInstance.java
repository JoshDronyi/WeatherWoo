package com.example.weatherwoo.repository.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private RetrofitInstance() {

    }

    private static class RetrofitHolder {
        private static Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(RemoteConstants.DEFAULT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        private static Retrofit changeBaseURL(String newUrl) {
            return  INSTANCE = new Retrofit.Builder()
                    .baseUrl(newUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
    }

    public static Retrofit getInstance() {
        return RetrofitHolder.INSTANCE;
    }

    public static Retrofit changeBaseURL(String newUrl) {
        return RetrofitHolder.changeBaseURL(newUrl);
    }
}
