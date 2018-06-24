package com.example.virus.weatherprojectfinalversion.customClasses;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.virus.weatherprojectfinalversion.R;

public class Tools {
    static public String getWeatherImage(String description) {
        if (description.contains("rain"))
            return "rain";
        if (description.contains("few"))
            return "sun_cloud";
        if (description.contains("sky"))
            return "sun";
        if (description.contains("scattered"))
            return "cloudly";
        if(description.contains("broken"))
            return "broken";
        else return "sun";
    }

    public static void chooseImage(TextView tvWeatherDescription, ImageView imageView) {
        String temp = Tools.getWeatherImage(tvWeatherDescription.getText().toString());
        switch (temp) {
            case "rain":
                imageView.setImageResource(R.drawable.rain);
                break;
            case "sun_cloud":
                imageView.setImageResource(R.drawable.sun_cloud);
                break;
            case "sun":
                imageView.setImageResource(R.drawable.sun);
                break;
            case "cloudly":
                imageView.setImageResource(R.drawable.cloudly);
                break;
            case "broken":
                imageView.setImageResource(R.drawable.brokenclouds);
        }
    }
}