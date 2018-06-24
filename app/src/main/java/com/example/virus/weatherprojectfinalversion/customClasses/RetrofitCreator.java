package com.example.virus.weatherprojectfinalversion.customClasses;


import com.example.virus.weatherprojectfinalversion.apies.Api;
import com.example.virus.weatherprojectfinalversion.models.Forecast;
import com.example.virus.weatherprojectfinalversion.models.Weather;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {
    public static final String BASE_API = "6c4073d27817692d5ee59160e39a468f";

    public Call<Weather> initializeCurrentLocationDataFromJson(String lat, String lon, String appid) {
        final ArrayList<String> names = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        Call<Weather> call = api.getByCurrentLocation(lat, lon, appid);


        return call;
    }

        public Call<Forecast> initializeCurrentForecastLocationDataFromJson(String country,String appid){
        final ArrayList<String> names = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        Call<Forecast> call = api.getForecast(country,appid);

        return call;
}
    public Call<Forecast> initializeCurrentForecastLocationDataFromJson(String lat,String lon,String appid){
        final ArrayList<String> names = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        Call<Forecast> call = api.getForecast(lat,lon,appid);

        return call;
    }
    public Call<Weather> initializeCurrentLocationDataFromJson(String country, String appid) {
        final ArrayList<String> names = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        final Api api = retrofit.create(Api.class);
        Call<Weather> call = api.getByCountryName(country, appid);
        return call;
    }
}


