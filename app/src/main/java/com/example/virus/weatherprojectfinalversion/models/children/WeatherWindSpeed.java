package com.example.virus.weatherprojectfinalversion.models.children;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WeatherWindSpeed  implements Parcelable{
    @SerializedName("speed")
    private double speed;

    protected WeatherWindSpeed(Parcel in) {
        speed = in.readDouble();
    }

    public static final Creator<WeatherWindSpeed> CREATOR = new Creator<WeatherWindSpeed>() {
        @Override
        public WeatherWindSpeed createFromParcel(Parcel in) {
            return new WeatherWindSpeed(in);
        }

        @Override
        public WeatherWindSpeed[] newArray(int size) {
            return new WeatherWindSpeed[size];
        }
    };

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(speed);
    }
}
