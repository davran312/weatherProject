package com.example.virus.weatherprojectfinalversion.models.children;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WeatherDescription  implements Parcelable{
//    @SerializedName("0")
//    private Children weatherDescription;
//
//    public Children getWeatherDescription() {
//        return weatherDescription;
//    }
//
//    public void setWeatherDescription(Children weatherDescription) {
//        this.weatherDescription = weatherDescription;
//    }
    @SerializedName("description")
        private String description;

    protected WeatherDescription(Parcel in) {
        description = in.readString();
    }

    public static final Creator<WeatherDescription> CREATOR = new Creator<WeatherDescription>() {
        @Override
        public WeatherDescription createFromParcel(Parcel in) {
            return new WeatherDescription(in);
        }

        @Override
        public WeatherDescription[] newArray(int size) {
            return new WeatherDescription[size];
        }
    };

    public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
    }
}
