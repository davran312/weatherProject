package com.example.virus.weatherprojectfinalversion.models.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeWeather {
    @SerializedName("main")
    WeatherTemperature temperature;
    @SerializedName("weather")
    private List<WeatherDescription> weatherDescriptionList;
    @SerializedName("wind")
    private WeatherWindSpeed windSpeed;


    protected TimeWeather(Parcel in) {
        temperature = in.readParcelable(WeatherTemperature.class.getClassLoader());
    }


    public WeatherTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(WeatherTemperature temperature) {
        this.temperature = temperature;
    }

    public List<WeatherDescription> getWeatherDescriptionList() {
        return weatherDescriptionList;
    }

    public void setWeatherDescriptionList(List<WeatherDescription> weatherDescriptionList) {
        this.weatherDescriptionList = weatherDescriptionList;
    }

    public WeatherWindSpeed getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WeatherWindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }

}