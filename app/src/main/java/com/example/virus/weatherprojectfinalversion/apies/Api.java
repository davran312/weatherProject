package com.example.virus.weatherprojectfinalversion.apies;


import com.example.virus.weatherprojectfinalversion.models.Forecast;
import com.example.virus.weatherprojectfinalversion.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    public final String BASE_URL="http://api.openweathermap.org/";
    @GET("data/2.5/weather")
    public Call<Weather> getByCurrentLocation(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid);
    @GET("data/2.5/forecast")
    public Call<Forecast> getForecast(
            @Query("q") String country,
            @Query("appid") String appid);
    @GET("data/2.5/forecast")
    public Call<Forecast> getForecast(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid);
    @GET("data/2.5/weather")
    public Call<Weather> getByCountryName(
            @Query("q") String country,
            @Query("appid") String appid);
}
