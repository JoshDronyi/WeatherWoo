package com.example.weatherwoo.view.mainactivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherwoo.R;
import com.example.weatherwoo.model.Currently;
import com.example.weatherwoo.model.Daily;
import com.example.weatherwoo.model.Hourly;
import com.example.weatherwoo.model.WeatherResponse;
import com.example.weatherwoo.view.adapter.DailyAdapter;
import com.example.weatherwoo.view.adapter.HourlyAdapter;
import com.example.weatherwoo.viewmodel.MainViewModel;
import com.google.android.material.textview.MaterialTextView;

import java.net.URI;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    //region Location Variables
    private LocationManager manager;
    private LocationListener listener;
    private Location currentLocation;
    private ConstraintLayout currentBackground;
    //endregion

    MaterialTextView tvCity, tvTemp, tvForecast;
    RecyclerView rvDaily, rvHourly;
    HourlyAdapter hourlyAdapter;
    DailyAdapter dailyAdapter;
    LinearLayoutManager hourlyManager;
    LinearLayoutManager dailyManager;
    DividerItemDecoration verticalSplit;
    DividerItemDecoration horizontalSplit;
    //endregion

    private static final int LOCATION_REQUEST_CODE = 1000;
    public static final String MAIN_ERROR_TAG = "TAG_MAIN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvDaily = findViewById(R.id.rv_daily);
        rvHourly = findViewById(R.id.rv_hourly);

        hourlyManager = new LinearLayoutManager(this);
        hourlyManager.setOrientation(RecyclerView.HORIZONTAL);
        rvHourly.setLayoutManager(hourlyManager);

        dailyManager = new LinearLayoutManager(this);
        dailyManager.setOrientation(RecyclerView.VERTICAL);
        rvDaily.setLayoutManager(dailyManager);

        verticalSplit = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvDaily.addItemDecoration(verticalSplit);

        horizontalSplit = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        rvHourly.addItemDecoration(horizontalSplit);


        //region Location Set up
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                getWeatherData();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent settingsIntent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                Uri packageName = Uri.fromParts("package", getPackageName(), null);
                settingsIntent.putExtra("package", packageName);
                startActivity(settingsIntent);

            }
        };
        Log.e(MAIN_ERROR_TAG, "onCreate: Requesting Location now");
        requestLocation();
        //endregion

        //region Variable Set Up
        viewModel = ViewModelProviders.of(this)
                .get(MainViewModel.class);
        //endregion

    }

    private void getWeatherData() {
        viewModel.getWeatherCall(String.valueOf(currentLocation.getLongitude()), String.valueOf(currentLocation.getLatitude()))
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.body() != null) {
                            Log.e(MAIN_ERROR_TAG, "getWeatherCall's onResponse Method: Response body was " + response.body());
                            setUpViews(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e(MAIN_ERROR_TAG, "getWeatherCall's onFailure Method: Response body was " + t.getMessage());
                        t.printStackTrace();
                    }
                });
    }

    private void setUpViews(WeatherResponse response) {
        Log.e(MAIN_ERROR_TAG, "setUpViews method: Beginning to set up views with the response: " + response.toString());
        //region View Variable declaration

        tvCity = findViewById(R.id.tv_city);
        tvTemp = findViewById(R.id.tv_temp);
        tvForecast = findViewById(R.id.tv_forecast);
        currentBackground = findViewById(R.id.currently_background);


        loadCurrently(response);
        loadDaily(response.getDaily());
        loadHourly(response.getHourly());
    }

    private void loadCurrently(WeatherResponse response) {
        Currently currentWeather = response.getCurrently();

        int startIndex = response.getTimezone().indexOf("/") + 1;
        String cityName = response.getTimezone().substring(startIndex);
        String formattedCity;
        if (cityName.contains("_")) {
            String[] cityNameArray = cityName.split("_");
            formattedCity = cityNameArray[0] + " " + cityNameArray[1];
        } else {
            formattedCity = cityName;
        }
        tvCity.setText(formattedCity);

        Log.e(MAIN_ERROR_TAG, "setUpViews: Forecast value = " + currentWeather.getSummary());

        switch (currentWeather.getSummary().toLowerCase()) {
            case "snow":
            case "rain":
            case "sleet":
            case "hail":
                currentBackground.setBackgroundColor(getResources().getColor(R.color.darkSkyDay));
                break;
            case "cloudy":
            case "partly cloudy":
            case "fog":
            case "clear":
                currentBackground.setBackgroundColor(getResources().getColor(R.color.clearSkyDay));
                break;
            case "partly-cloudy":
            case "clear-night":
                currentBackground.setBackgroundColor(getResources().getColor(R.color.nightSky));
                break;
        }


        tvForecast.setText(currentWeather.getSummary());

        long tempInt = Math.round(currentWeather.getTemperature());
        String tempString = String.format(Locale.US, "% d\u00B0", tempInt);
        tvTemp.setText(tempString);
    }

    private void loadHourly(Hourly hourlyWeather) {

        hourlyAdapter = new HourlyAdapter(hourlyWeather.getData());
        rvHourly.setAdapter(hourlyAdapter);
    }

    private void loadDaily(Daily dailyWeather) {

        dailyAdapter = new DailyAdapter(dailyWeather.getData());
        rvDaily.setAdapter(dailyAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (LOCATION_REQUEST_CODE == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
        }

    }

    private void requestLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET};
                requestPermissions(permissions, LOCATION_REQUEST_CODE);
                return;
            } else {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, listener);
            }
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, listener);
    }
}
