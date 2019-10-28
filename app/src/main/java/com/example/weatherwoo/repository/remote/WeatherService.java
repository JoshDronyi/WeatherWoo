package com.example.weatherwoo.repository.remote;

import com.example.weatherwoo.model.WeatherResponse;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    //region Retrofit
    @GET(RemoteConstants.WEATHER_PATH)
    Call<WeatherResponse> getWeatherCall(
            @Path("apiKey") String apiKey,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );
    //endregion

    //region Retrofit X RXJava
    @GET(RemoteConstants.WEATHER_PATH)
    Single<WeatherResponse> getWeatherSingle(
            @Path("apiKey") String apiKey,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );

    @GET(RemoteConstants.WEATHER_PATH)
    Observable<WeatherResponse> getWeatherObservable(
            @Path("apiKey") String apiKey,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );

    @GET(RemoteConstants.WEATHER_PATH)
    Maybe<WeatherResponse> getWeatherMaybe(
            @Path("apiKey") String apiKey,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );

    @GET(RemoteConstants.WEATHER_PATH)
    Flowable<WeatherResponse> getWeatherFlowable(
            @Path("apiKey") String apiKey,
            @Path("latitude") String latitude,
            @Path("longitude") String longitude
    );
    //endregion
}