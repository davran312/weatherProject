package com.example.virus.weatherprojectfinalversion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.virus.weatherprojectfinalversion.R;
import com.example.virus.weatherprojectfinalversion.adapters.ListViewListenerAdapter;
import com.example.virus.weatherprojectfinalversion.customClasses.Tools;
import com.example.virus.weatherprojectfinalversion.models.children.TimeWeather;
import com.example.virus.weatherprojectfinalversion.models.children.WeatherCountry;
import com.example.virus.weatherprojectfinalversion.models.children.WeatherTemperature;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentLocationFragment extends Fragment {
    public static final String EXTRA_DESCRIPTION ="description";
    public static final String EXTRA_LOCATION_NAME ="location";
    public static final String EXTRA_TEMPERATURE ="temp";
    public static final String EXTRA_COUNTRY_NAME = "country";
    FrameLayout frameLayout;
    ListView gridView;
    ListViewListenerAdapter adapter;
    public static List<TimeWeather> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_location, container, false);
        frameLayout = view.findViewById(R.id.frame_container);
        gridView = view.findViewById(R.id.gw_week_weather);
        setupFragmentUi(view);
        return view;
    }
    private void setupFragmentUi(View view){
        boolean findingCurrentLocationSkipped = getActivity().getIntent().getExtras().getBoolean("boolean");
        if(findingCurrentLocationSkipped)
            openFrameLayout();
        else {
            frameLayout.setVisibility(View.GONE);
            adapter = new ListViewListenerAdapter(getActivity().getApplicationContext(),R.layout.gridview_days_weather,list);
            adapter.notifyDataSetChanged();
            gridView.setAdapter(adapter);
            WeatherTemperature weatherTemperature = getActivity().getIntent().getParcelableExtra(EXTRA_TEMPERATURE);
            TextView tvCurrentPositionName = view.findViewById(R.id.tv_current_position_name);
            TextView tvWeatherDescription = view.findViewById(R.id.tv_description);
            TextView tvWeatherTemperature = view.findViewById(R.id.tv_temperature);
            ImageView imageView = view.findViewById(R.id.img_current_weather);
            TextView tvCurrentDate = view.findViewById(R.id.tv_date_current_location);
            TextView tvCurrentMax = view.findViewById(R.id.tv_current_max);
            TextView tvCurrentMin = view.findViewById(R.id.tv_current_min);
            DateFormat df = new SimpleDateFormat("EEE, MMM d");
            String date = df.format(Calendar.getInstance().getTime());


            tvWeatherTemperature.append(String.format("%.0f°", (weatherTemperature.getTemperature() - 273)));
            tvCurrentDate.setText(date);
            tvCurrentMax.setText(String.format("High: %.0f°", (weatherTemperature.getMaxTemperature() - 273)));
            tvCurrentMin.setText(String.format("Low: %.0f°", (weatherTemperature.getMinTemperature() - 273)));
            String country = getActivity().getIntent().getExtras().getString(EXTRA_COUNTRY_NAME);

            tvCurrentPositionName.setText(country+" ");
            tvCurrentPositionName.append(getActivity().getIntent().getStringExtra(EXTRA_LOCATION_NAME));
            tvWeatherDescription.setText(getActivity().getIntent().getStringExtra(EXTRA_DESCRIPTION));
            Tools.chooseImage(tvWeatherDescription,imageView);
            }

        }

//       tvWeatherPressure.append(Double.toString(weatherTemperature.getPressure()));
//


    private void openFrameLayout() {
        GeolocationOffFragment fragment = new GeolocationOffFragment();
        FragmentTransaction fg = getFragmentManager().beginTransaction();
        fg.replace(R.id.frame_container,fragment);
        fg.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fg.commit();

    }
}
