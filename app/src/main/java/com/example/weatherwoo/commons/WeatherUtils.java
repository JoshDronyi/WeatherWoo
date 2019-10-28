package com.example.weatherwoo.commons;

import com.example.weatherwoo.R;

public class WeatherUtils {
    public static int getweatherIcon(String icon) {
        switch (icon) {
            case "cloudy":
                return R.drawable.cloudy;
            case "snow":
                return R.drawable.snow;
            case "rain":
                return R.drawable.rain;
            case "partly-cloudy-day":
                return R.drawable.partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.partly_cloudy_night ;
            case "sleet":
                return R.drawable.sleet;
            case "fog":
                return R.drawable.fog;
            case "clear-day":
                return R.drawable.clear_day;
            case "clear-night":
                return R.drawable.clear_night ;
            case "hail":
                return R.drawable.hail;
        }
        return 0;
    }
}
