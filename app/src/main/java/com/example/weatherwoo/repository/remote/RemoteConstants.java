package com.example.weatherwoo.repository.remote;

class RemoteConstants {

    static final String BASE_URL = "https://api.darksky.net";
    static final String WEATHER_PATH = "/forecast/{apiKey}/{longitude},{latitude}?exclude=[minutely,alerts,flags]";
}
