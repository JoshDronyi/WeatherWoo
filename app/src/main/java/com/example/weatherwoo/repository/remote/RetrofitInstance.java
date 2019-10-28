package com.example.weatherwoo.repository.remote;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private RetrofitInstance() {
    }

    private static class InstanceHolder {
        private static final Retrofit INSTANCE = new Retrofit
                .Builder()
                .baseUrl(RemoteConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
