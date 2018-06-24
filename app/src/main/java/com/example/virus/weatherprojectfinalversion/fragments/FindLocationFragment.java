package com.example.virus.weatherprojectfinalversion.fragments;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
public class FindLocationFragment extends Fragment {
    EditText editText;
    CountryWeatherFragment countryWeatherFragment;
    ListViewListenerAdapter adapter;
    private ListView gridView;
    TextView countryDate ;
    List<TimeWeather> list = new ArrayList<>();

    public FindLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_find_location, container, false);
        editText = view.findViewById(R.id.et_find_city);

        countryWeatherFragment = new CountryWeatherFragment();
        final TextView countryName = view.findViewById(R.id.tv_country_name);
        final ImageView countryImage = view.findViewById(R.id.img_not_found);
        gridView = view.findViewById(R.id.gw_week_weather);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                countryName.setText("Enter country");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().length()==0){
                    countryName.setText("Enter country");
                    countryImage.setVisibility(View.INVISIBLE);
                }
                else {
//

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText.getText().length()==0){
                    countryName.setText("Enter country");
                    countryImage.setVisibility(View.INVISIBLE);
                }
                requestDataFromServer(editText,view);
                requestForecastFromServer(editText);



            }
        });
        return view;
    }

    private void requestForecastFromServer(EditText editText)         {
        Call<Forecast> call = new RetrofitCreator()
                .initializeCurrentForecastLocationDataFromJson(editText.getText().toString(), RetrofitCreator.BASE_API);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.body() != null) {
                    List<TimeWeather> temp = response.body().getTimeWeatherList();
                    for (int i = 12; i < temp.size(); i += 8) {
                        list.add(temp.get(i));
                    }
                    list.add(temp.get(temp.size()-8));

                } else
                    list.clear();

                adapter = new ListViewListenerAdapter(getActivity().getApplicationContext(),
                        R.layout.gridview_days_weather, list);
                adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void requestDataFromServer(EditText editText, final View view) {
        Call<Weather> weatherCall = new RetrofitCreator().
                initializeCurrentLocationDataFromJson(editText.getText().toString(),RetrofitCreator.BASE_API);

        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                initializeXml(view,response);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }

    private void initializeXml(View view,Response<Weather> response) {
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
        if(response.isSuccessful()) {
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

            setVisibilities(countryWeather,countryTemperature,counrtyMax,countryMin,
                    countryDescription,countryDate,gridView,View.VISIBLE);

        }
        else {

            countryName.setText(getResources().getString(R.string.country_was_not_found));
            countryNotFound.setVisibility(View.VISIBLE);
            setVisibilities(countryWeather,countryTemperature,counrtyMax,countryMin,
                    countryDescription,countryDate,gridView,View.INVISIBLE);
        }
    }
    private void setVisibilities(ImageView weather, TextView temperature,TextView max,TextView min,
                                 TextView description, TextView date,ListView gridView, int visible) {
        weather.setVisibility(visible);
        temperature.setVisibility(visible);
        date.setVisibility(visible);
        description.setVisibility(visible);
        max.setVisibility(visible);
        min.setVisibility(visible);
        gridView.setVisibility(visible);

    }


    }





