package com.example.virus.weatherprojectfinalversion.models.children;


import com.google.gson.annotations.SerializedName;

public class WeatherCountry {
    @SerializedName("country")
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
