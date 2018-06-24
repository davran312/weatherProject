package com.example.virus.weatherprojectfinalversion.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.virus.weatherprojectfinalversion.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseLocationFragment extends Fragment{
    List<String> countries;
    ListView listView ;
    FrameLayout frameLayout;
    ArrayList<String> countryList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_location,container,false);
        listView = view.findViewById(R.id.lw_choose_location);
        frameLayout = view.findViewById(R.id.country_weather_fragment);
        frameLayout.setVisibility(View.GONE);
        try {
            countries = uploadAllCountriesFromFile();
             } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setAdapter(new ArrayAdapter<>(inflater.getContext(),android.R.layout.simple_list_item_1,countries));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frameLayout.setVisibility(View.VISIBLE);
                CountryWeatherFragment.setCountryName(countryList.get(position));
                openFrameOfCountry();
            }
        });


        return view;
    }




    private void openFrameOfCountry() {
        CountryWeatherFragment fragment = new CountryWeatherFragment();
        FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
        fragmentManager.replace(R.id.country_weather_fragment,fragment);
        fragmentManager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentManager.addToBackStack("fragment");
        fragmentManager.commit();
    }


    private ArrayList<String> uploadAllCountriesFromFile() throws IOException {
        InputStream path = null;
        try {
            path = getResources().getAssets().open("countries");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(path));
        String line;
        while((line = bufferedReader.readLine())!= null){
            countryList.add(line);
            }

        return countryList;
    }


}
