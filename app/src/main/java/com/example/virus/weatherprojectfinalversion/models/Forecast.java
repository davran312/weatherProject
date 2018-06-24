package com.example.virus.weatherprojectfinalversion.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.virus.weatherprojectfinalversion.models.children.TimeWeather;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Forecast implements Parcelable {
    @SerializedName("list")
    private List<TimeWeather> timeWeatherList;

    protected Forecast(Parcel in) {
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

    public List<TimeWeather> getTimeWeatherList() {
        return timeWeatherList;
    }

    public void setTimeWeatherList(List<TimeWeather> timeWeatherList) {
        this.timeWeatherList = timeWeatherList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(timeWeatherList);
    }
}
