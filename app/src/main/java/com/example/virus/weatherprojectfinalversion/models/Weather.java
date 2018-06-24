package com.example.virus.weatherprojectfinalversion.models;
//
import android.os.Parcel;
import android.os.Parcelable;

import com.example.virus.weatherprojectfinalversion.models.children.WeatherCountry;
import com.example.virus.weatherprojectfinalversion.models.children.WeatherDescription;
import com.example.virus.weatherprojectfinalversion.models.children.WeatherTemperature;
import com.example.virus.weatherprojectfinalversion.models.children.WeatherWindSpeed;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather  implements Parcelable{
    @SerializedName("weather")
    private List<WeatherDescription> weatherDescriptionList;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private WeatherTemperature weatherTemperature;

    @SerializedName("sys")
    private WeatherCountry country;
    @SerializedName("name")
    private String currentPositionName;
    @SerializedName("wind")
    private WeatherWindSpeed weatherWindSpeed;

    protected Weather(Parcel in) {
        weatherDescriptionList = in.createTypedArrayList(WeatherDescription.CREATOR);
        base = in.readString();
        weatherTemperature = in.readParcelable(WeatherTemperature.class.getClassLoader());
        currentPositionName = in.readString();
        weatherWindSpeed = in.readParcelable(WeatherWindSpeed.class.getClassLoader());
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    //
      public String getCurrentPositionName() {
          return currentPositionName;
        }
//
    public void setCurrentPositionName(String currentPositionName) {
        this.currentPositionName = currentPositionName;
    }
//
    public List<WeatherDescription> getDescription() {
        return weatherDescriptionList;
    }

    public void setDescription(List<WeatherDescription> descriptionList) {
        weatherDescriptionList = descriptionList;
    }

    public WeatherTemperature getTemperature() {
        return weatherTemperature;
    }

    public WeatherWindSpeed getWeatherWindSpeed() {
        return weatherWindSpeed;
    }

    public void setWeatherWindSpeed(WeatherWindSpeed weatherWindSpeed) {
        this.weatherWindSpeed = weatherWindSpeed;
    }

    public void setTemperature(WeatherTemperature temperature) {
        weatherTemperature = temperature;
    }

    public WeatherCountry getCountry() {
        return country;
    }

    public void setCountry(WeatherCountry country) {
        this.country = country;
   }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(weatherDescriptionList);
        dest.writeString(base);
        dest.writeParcelable(weatherTemperature, flags);
        dest.writeString(currentPositionName);
        dest.writeParcelable(weatherWindSpeed, flags);
    }
}

