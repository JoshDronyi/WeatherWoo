package com.example.weatherwoo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.weatherwoo.model.WeatherResponse;
import com.example.weatherwoo.repository.Repository;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class MainViewModel extends AndroidViewModel {
    public static final String MAIN_VIEW_MODEL_TAG = "TAG_MAIN_VIEW_MODEL";


    //View Model for the Main activity
    public MainViewModel(@NonNull Application application) {
        super(application);

    }

    public Call<WeatherResponse> getWeatherCall(String latitude, String longitude) {
        Log.e(MAIN_VIEW_MODEL_TAG, "getWeatherCall method in MainViewModel: calling Repo with lat: " + latitude + " & lon: " + longitude);
        return Repository.getInstance()
                .getWeatherCall(latitude, longitude);
    }

    public Observable<WeatherResponse> getWeatherObservable(String latitude, String longitude) {
        return Repository.getInstance()
                .getWeatherObservable(latitude, longitude);
    }

    public Single<WeatherResponse> getWeatherSingle(String latitude, String longitude) {
        return Repository.getInstance()
                .getWeatherSingle(latitude, longitude);
    }

    public Maybe<WeatherResponse> getWeatherMaybe(String latitude, String longitude) {
        return Repository.getInstance()
                .getWeatherMaybe(latitude, longitude);
    }

    public Flowable<WeatherResponse> getWeatherFlowable(String latitude, String longitude) {
        return Repository.getInstance()
                .getWeatherFlowable(latitude, longitude);
    }

}
