package com.example.cristian.everysale.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.cristian.everysale.Activity.Main2Activity;
import com.example.cristian.everysale.Activity.ModifyProfileActivity;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.Activity.StartActivity;

public class MenuListener implements DrawerListener, OnNavigationItemSelectedListener {

    private Activity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    public MenuListener(Activity activity, NavigationView navigationView, DrawerLayout drawerLayout){
        this.activity = activity;
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        navigationView.bringToFront();
        drawerLayout.requestLayout();
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                if(activity instanceof Main2Activity){
                    Log.e("Debug", "Casa!");
                    drawerLayout.closeDrawers();
                    break;
                }
                else{
                    Intent intent = new Intent(this.activity, Main2Activity.class);
                    activity.startActivity(intent);
                    this.activity.finish();
                    break;
                }

            case R.id.nav_disconnect:
                activity.getSharedPreferences("SavedValues", activity.MODE_PRIVATE).edit().clear().commit();
                Intent intent = new Intent(this.activity, StartActivity.class);
                activity.startActivity(intent);
                this.activity.finish();
                break;


            case R.id.nav_my_account:
                intent = new Intent(this.activity, ModifyProfileActivity.class);
                activity.startActivity(intent);
                break;


            default:
                Log.e("Debug", "default");
                break;

        }
        return false;
    }
}
