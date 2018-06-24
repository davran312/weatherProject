package com.example.virus.weatherprojectfinalversion.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.virus.weatherprojectfinalversion.R;
import com.example.virus.weatherprojectfinalversion.adapters.SectionPagerAdapter;
import com.example.virus.weatherprojectfinalversion.fragments.ChooseLocationFragment;
import com.example.virus.weatherprojectfinalversion.fragments.CurrentLocationFragment;
import com.example.virus.weatherprojectfinalversion.fragments.FindLocationFragment;

public class MainActivity extends  AppCompatActivity {
    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabsOfViewPager();
        final View view = View.inflate(getApplicationContext(), R.layout.fragment_current_location, null);
        frameLayout = view.findViewById(R.id.frame_container);

    }

    @Override
    public void onBackPressed() {
        if((getSupportFragmentManager().getBackStackEntryCount() != 0))
            getSupportFragmentManager().popBackStack();
        if(viewPager.getCurrentItem()==0) {
            super.onBackPressed();
            this.finish();
        }
        if((viewPager.getCurrentItem()>0) && getSupportFragmentManager().getBackStackEntryCount() ==0)
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1);

        FrameLayout countryWeatherLayout = findViewById(R.id.country_weather_fragment);
        if(countryWeatherLayout.getVisibility()==View.VISIBLE)
            countryWeatherLayout.setVisibility(View.GONE);

    }

    private void setupTabsOfViewPager() {
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
        TabLayout tableLayout = findViewById(R.id.tabs);
        tableLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        sectionPagerAdapter.addFragment(new CurrentLocationFragment(),getResources().getString(R.string.tab1));
        sectionPagerAdapter.addFragment(new ChooseLocationFragment(),getResources().getString(R.string.tab2));
        sectionPagerAdapter.addFragment(new FindLocationFragment(),getResources().getString(R.string.tab3));

        viewPager.setAdapter(sectionPagerAdapter);

    }

}
