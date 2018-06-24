package com.example.virus.weatherprojectfinalversion.models.children;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WeatherTemperature implements Parcelable {
    @SerializedName("temp")
    private double temperature;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;
    @SerializedName("temp_max")
    private double maxTemperature;
    @SerializedName("temp_min")
    private double minTemperature;

    protected WeatherTemperature(Parcel in) {
        temperature = in.readDouble();
        maxTemperature = in.readDouble();
        minTemperature = in.readDouble();
        pressure = in.readDouble();
        humidity = in.readDouble();
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public static final Creator<WeatherTemperature> CREATOR = new Creator<WeatherTemperature>() {
        @Override
        public WeatherTemperature createFromParcel(Parcel in) {
            return new WeatherTemperature(in);
        }

        @Override
        public WeatherTemperature[] newArray(int size) {
            return new WeatherTemperature[size];
        }
    };

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
                dest.writeDouble(temperature);
        dest.writeDouble(pressure);
        dest.writeDouble(maxTemperature);
        dest.writeDouble(minTemperature);
        dest.writeDouble(humidity);
    }
}
