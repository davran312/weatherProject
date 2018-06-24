package com.example.virus.weatherprojectfinalversion.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.virus.weatherprojectfinalversion.R;
import com.example.virus.weatherprojectfinalversion.adapters.ListViewListenerAdapter;
import com.example.virus.weatherprojectfinalversion.customClasses.RetrofitCreator;
import com.example.virus.weatherprojectfinalversion.customClasses.Tools;
import com.example.virus.weatherprojectfinalversion.models.Forecast;
import com.example.virus.weatherprojectfinalversion.models.Weather;
import com.example.virus.weatherprojectfinalversion.models.children.TimeWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryWeatherFragment extends Fragment {
    public static String countryName;
    private ListView gridView;
    List<TimeWeather> list = new ArrayList<>();
    ListViewListenerAdapter adapter;
    TextView countryDate ;


    public static void setCountryName(String countryName) {
        CountryWeatherFragment.countryName = countryName;
    }

    public CountryWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_weather, container, false);

        gridView = view.findViewById(R.id.gw_week_weather);
        requestWeatherDataFromServer(view);
        requestForecastDataFromServer();

        return view;
    }

    private void requestForecastDataFromServer() {
        Call<Forecast> call = new RetrofitCreator()
                .initializeCurrentForecastLocationDataFromJson(countryName, RetrofitCreator.BASE_API);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.body() != null) {
                    List<TimeWeather> temp = response.body().getTimeWeatherList();
                    for (int i = 12; i < temp.size(); i += 8) {
                        list.add(temp.get(i));
                    }
                    list.add(temp.get(temp.size()-8));

                    adapter =new ListViewListenerAdapter(getActivity().getApplicationContext(),
                            R.layout.gridview_days_weather, list);
                    adapter.notifyDataSetChanged();
                    gridView.setAdapter(adapter);


                }
            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {

            }
        });
    }

    private void requestWeatherDataFromServer(final View view) {
        Call<Weather> call = new RetrofitCreator()
                .initializeCurrentLocationDataFromJson
                        (countryName, RetrofitCreator.BASE_API);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                initializeXmlWeatherRequest(response, view);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }


    public void initializeXmlWeatherRequest(Response<Weather> response, View view) {
        TextView countryName = view.findViewById(R.id.tv_country_name);
        countryDate = view.findViewById(R.id.tv_date_country_location);
        TextView countryDescription = view.findViewById(R.id.tv_country_description);
        TextView countryTemperature = view.findViewById(R.id.tv__country_temperature);
        ImageView countryWeather = view.findViewById(R.id.img_country_weather);
        ImageView countryNotFound = view.findViewById(R.id.img_not_found);
        TextView counrtyMax = view.findViewById(R.id.tv_country_max);
        TextView countryMin = view.findViewById(R.id.tv_country_min);


        countryWeather.setVisibility(View.VISIBLE);
        DateFormat df = new SimpleDateFormat("EEE, MMM d");
        String date = df.format(Calendar.getInstance().getTime());
        if (response.isSuccessful()) {
            countryNotFound.setVisibility(View.GONE);
            countryName.setText(response.body().getCurrentPositionName());
            countryDate.setText(date);
            counrtyMax.setText(String.format("High: %.0f°",
                    (response.body().getTemperature().getMaxTemperature() - 273)));
            countryMin.setText(String.format("Low: %.0f°",
                    (response.body().getTemperature().getMinTemperature() - 273)));
            countryTemperature.setText(String.format("%.0f°", (response.body().getTemperature().getTemperature() - 273)));
            countryDescription.setText(response.body().getDescription().get(0).getDescription());
            Tools.chooseImage(countryDescription,countryWeather);

        } else {

            countryName.setText(getResources().getString(R.string.country_was_not_found));
            countryNotFound.setVisibility(View.VISIBLE);
            countryWeather.setVisibility(View.INVISIBLE);
        }
    }




}

