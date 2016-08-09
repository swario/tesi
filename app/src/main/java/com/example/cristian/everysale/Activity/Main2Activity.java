package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cristian.everysale.Fragments.HomeActivity.viewPagerAdapter;
import com.example.cristian.everysale.Layouts.SlidingTabLayout;
import com.example.cristian.everysale.R;


public class Main2Activity extends navigationDrawerActivity {

    private SharedPreferences savedValues;

    ViewPager pager;
    viewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"In zona","Recenti","preferiti"};
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_main2, null, false);
        drawerLayout.addView(contentView, 0);

        if(savedValues.getString("username", "").equals("")){
            Log.e("debug", "Username: " + savedValues.getString("username", ""));

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles for the Tabs and Number Of Tabs.
        adapter =  new viewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assigning the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
        // Faccio vedere per prima la tab centrale
        pager.setCurrentItem(1);

    }

}