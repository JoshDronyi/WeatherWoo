package com.example.weatherwoo.repository;

import android.util.Log;

import com.example.weatherwoo.model.WeatherResponse;
import com.example.weatherwoo.repository.remote.RetrofitInstance;
import com.example.weatherwoo.repository.remote.WeatherService;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Repository {

    private static final String API_KEY = "1d94d478a0954fa7d604f9fdc522c770";
    private WeatherService service;
    public static final String REPOSITORY_ERROR_TAG = "TAG_REPOSITORY";


    private Repository() {
        service = RetrofitInstance
                .getInstance()
                .create(WeatherService.class);
    }

    private static final class InstanceHolder {
        private static final Repository INSTANCE = new Repository();
    }

    public static Repository getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Call<WeatherResponse> getWeatherCall(String latitude, String longitude) {
        Log.e(REPOSITORY_ERROR_TAG, "getWeatherCall in Repository:\n apikey:" + API_KEY + " \n lon: " + longitude + "\n lat: " + latitude);

        return service.getWeatherCall(
                API_KEY,
                latitude,
                longitude
        );
    }

    public Observable<WeatherResponse> getWeatherObservable(String latitude,String longitude) {
        return service.getWeatherObservable(
                API_KEY,
                latitude,
                longitude
        );
    }

    public Single<WeatherResponse> getWeatherSingle(String latitude, String longitude) {
        return service.getWeatherSingle(API_KEY,
                latitude,
                longitude);
    }

    public Maybe<WeatherResponse> getWeatherMaybe(String latitude, String longitude) {
        return service.getWeatherMaybe(API_KEY,
                latitude,
                longitude);
    }

    public Flowable<WeatherResponse> getWeatherFlowable(String latitude, String longitude) {
        return service.getWeatherFlowable(API_KEY,
                latitude,
                longitude);
    }

}