package com.example.weatherwoo.repository.remote;

import androidx.room.Query;

import com.example.weatherwoo.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    @GET("/{longitude},{lattitude}")
    Call<WeatherResponse> getWeather(@Path("longitude") double longitude, @Path("lattitude") double lattitude);
}
