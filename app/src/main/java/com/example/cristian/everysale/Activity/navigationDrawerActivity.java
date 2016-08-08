package com.example.cristian.everysale.Activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.Listeners.MenuListener;
import com.example.cristian.everysale.R;

public class navigationDrawerActivity extends AppCompatActivity {

    protected SharedPreferences savedValues;

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;

    //icona toolbar
    protected ActionBarDrawerToggle mDrawerToggle;
    protected String mActivityTitle;

    private Integer EasterEgg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        MenuListener menuListener = new MenuListener(this, navigationView, drawerLayout);
        drawerLayout.addDrawerListener(menuListener);
        navigationView.setNavigationItemSelectedListener(menuListener);

        //icona toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mActivityTitle = getTitle().toString();
        setupDrawer();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Non bisogna fare l'inflate di questo menu perchè altrimenti compare il pulsante in alto a destra
        //getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
        return true;
    }

    //funzionamento menu laterale
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                setNavigationViewPreferences();

                EasterEgg++;
                if(EasterEgg>7)Toast.makeText(getApplication(), "Carino vero?!?!?!?" , Toast.LENGTH_LONG).show();

            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                EasterEgg++;
                if(EasterEgg>20)Toast.makeText(getApplication(), "Carino vero?!?!?!?" , Toast.LENGTH_LONG).show();
            }





        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
    }

    public void setNavigationViewPreferences(){
        TextView username = (TextView) findViewById(R.id.nav_header_username);
        String sname = savedValues.getString("username", "ulisse rimane comunque il piu bello");
        username.setText(sname);

        /* devo ancora impostare alteza dinamica header
        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.layout_nav_header_drawer);


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN){
            //relativeLayout.setMinimumHeight(235);
            params.height = 100;
        } else{
            //relativeLayout.setMinimumHeight(180);
            // do something for phones running an SDK before lollipop
            params.height = 100;
        }
        relativeLayout.setLayoutParams(params);
        */
    }

}
