package com.example.virus.weatherprojectfinalversion.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.virus.weatherprojectfinalversion.R;
import com.example.virus.weatherprojectfinalversion.adapters.ListViewListenerAdapter;
import com.example.virus.weatherprojectfinalversion.customClasses.RetrofitCreator;
import com.example.virus.weatherprojectfinalversion.fragments.CurrentLocationFragment;
import com.example.virus.weatherprojectfinalversion.models.Forecast;
import com.example.virus.weatherprojectfinalversion.models.Weather;
import com.example.virus.weatherprojectfinalversion.models.children.TimeWeather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentLocationSetterActivity extends AppCompatActivity {
   private LocationManager locationManager;
   private Button btFindCurrentLocation;
   private TextView tvCurrentLocation;
   private boolean gotCurrentPosition;
   private Button btSkipFinding;
   private boolean booleanSkipPressed ;
   boolean isStarted;
   Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_location_setter);
        gotCurrentPosition = false;
        isStarted = false;
        booleanSkipPressed = false;
        intent = new Intent(this,MainActivity.class);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        initializeXmlAtributes();
        setupLocationManager();
    }


    private void initializeXmlAtributes() {

        btFindCurrentLocation = findViewById(R.id.bt_current_location);
        tvCurrentLocation = findViewById(R.id.tv_current_location);
        btSkipFinding = findViewById(R.id.bt_skip);
        btFindCurrentLocation.setVisibility(View.INVISIBLE);
        btSkipFinding.setVisibility(View.INVISIBLE);



        btFindCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                startRequestForLocation();
          }
        });
        btSkipFinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanSkipPressed  = true;
                Intent intent = new Intent(CurrentLocationSetterActivity.this,MainActivity.class);
                intent.putExtra("boolean",booleanSkipPressed);
                startActivity(intent);
            }
        });
    }

    private void setupLocationManager() {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                }
            }
            else startRequestForLocation();
        }

    private void startRequestForLocation() {
        tvCurrentLocation.setText(getResources().getString(R.string.msg_wait));
        try
        {       if(!gotCurrentPosition)
                    locationManager.requestLocationUpdates("gps", 100,2 , new GPSTracker());
        }catch (SecurityException ex){}

    }


    public class GPSTracker implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
                callForecastWeather(location.getLatitude(),location.getLongitude());
            if(!gotCurrentPosition && isStarted) {
                setConnectionWithApi(location.getLatitude(), location.getLongitude());
                tvCurrentLocation.setText(location.getLatitude() + " " + location.getLongitude());
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            tvCurrentLocation.setText(getResources().getString(R.string.msg_wait));
            btFindCurrentLocation.setVisibility(View.INVISIBLE);
            btSkipFinding.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onProviderDisabled(String provider) {
            tvCurrentLocation.setText(getResources().getString(R.string.gps_disabled_text));
            btFindCurrentLocation.setVisibility(View.VISIBLE);
            btSkipFinding.setVisibility(View.VISIBLE);

        }
    }

    private void setConnectionWithApi(double latitude, double longitude) {
        Call<Weather> call = new RetrofitCreator().initializeCurrentLocationDataFromJson
                (Double.toString(latitude),Double.toString(longitude), RetrofitCreator.BASE_API);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                startIntent(response);
                gotCurrentPosition = true;
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d("error",t.getMessage(),t.fillInStackTrace());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    private void callForecastWeather(double latitude,double longitude) {
        Call<Forecast> forecastCall = new RetrofitCreator()
                .initializeCurrentForecastLocationDataFromJson(String.valueOf(latitude),
                        String.valueOf(longitude),RetrofitCreator.BASE_API);
        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.body() != null) {
                    List<TimeWeather> temp = response.body().getTimeWeatherList();
                    for (int i = 12; i < temp.size(); i += 8) {
                        CurrentLocationFragment
                        .list.add(temp.get(i));
                    }
                    CurrentLocationFragment.list.add(temp.get(temp.size()-8));
                    isStarted = true;
                }
            }
            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {

            }
        });
    }


    private void startIntent(Response<Weather> response) {
        sendResponseDataToFragment(response);
        startActivity(intent);
        this.finish();

    }

    private void sendResponseDataToFragment(Response<Weather> response) {
        intent.putExtra(CurrentLocationFragment.EXTRA_LOCATION_NAME,response.body().getCurrentPositionName());
        intent.putExtra(CurrentLocationFragment.EXTRA_TEMPERATURE,response.body().getTemperature());
        intent.putExtra(CurrentLocationFragment.EXTRA_DESCRIPTION,response.body().getDescription().get(0).getDescription());
        intent.putExtra(CurrentLocationFragment.EXTRA_COUNTRY_NAME,response.body().getCountry().getCountry());
 }

}
