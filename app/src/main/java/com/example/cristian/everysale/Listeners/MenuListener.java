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
import com.example.cristian.everysale.Activity.MyInsertionsActivity;
import com.example.cristian.everysale.Activity.SearchActivity;
import com.example.cristian.everysale.Activity.StartActivity;
import com.example.cristian.everysale.Activity.aNewInsertionActivity;
import com.example.cristian.everysale.R;


public class MenuListener implements DrawerListener, OnNavigationItemSelectedListener {

    private Activity activity;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Intent intent=null;

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

            case R.id.nav_search:
                if(activity instanceof SearchActivity){
                    Log.e("Debug", "Casa!");
                    drawerLayout.closeDrawers();
                }
                else if(activity instanceof Main2Activity){
                    intent = new Intent(this.activity, SearchActivity.class);
                    drawerLayout.closeDrawers();
                    activity.startActivity(intent);
                    break;
                }
                else{
                    intent = new Intent(this.activity, SearchActivity.class);
                    activity.startActivity(intent);
                    this.activity.finish();
                }
                break;

            case R.id.nav_home:
                if(activity instanceof Main2Activity){
                    Log.e("Debug", "Casa!");
                    drawerLayout.closeDrawers();
                    break;
                }
                else{
                    intent = new Intent(this.activity, Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(intent);
                    this.activity.finish();
                    break;
                }

            case R.id.nav_my_account:
                if(activity instanceof ModifyProfileActivity){
                    Log.e("Debug", "modify profile!");
                    drawerLayout.closeDrawers();
                    break;
                }
                else if(activity instanceof Main2Activity){
                    intent = new Intent(this.activity, ModifyProfileActivity.class);
                    drawerLayout.closeDrawers();
                    activity.startActivity(intent);
                    break;
                }
                else{
                    intent = new Intent(this.activity, ModifyProfileActivity.class);
                    activity.startActivity(intent);
                    this.activity.finish();
                    break;
                }

            case R.id.nav_my_insertions:
                if(activity instanceof MyInsertionsActivity){
                    Log.e("Debug", "my insertion!");
                    drawerLayout.closeDrawers();
                    break;
                }
                else if(activity instanceof Main2Activity){
                    intent = new Intent(this.activity, MyInsertionsActivity.class);
                    drawerLayout.closeDrawers();
                    activity.startActivity(intent);
                    break;
                }
                else{
                    intent = new Intent(this.activity, MyInsertionsActivity.class);
                    activity.startActivity(intent);
                    this.activity.finish();
                    break;
                }

            case R.id.nav_new_insertion:
                if(activity instanceof aNewInsertionActivity){
                    Log.e("Debug", "new insertion!");
                    drawerLayout.closeDrawers();
                    break;
                }
                else if(activity instanceof Main2Activity){
                    intent = new Intent(this.activity, aNewInsertionActivity.class);
                    drawerLayout.closeDrawers();
                    activity.startActivity(intent);
                    break;
                }
                else{
                    intent = new Intent(this.activity, aNewInsertionActivity.class);
                    activity.startActivity(intent);
                    this.activity.finish();
                    break;
                }

            case R.id.nav_disconnect:
                activity.getSharedPreferences("SavedValues", activity.MODE_PRIVATE).edit().clear().commit();
                intent = new Intent(this.activity, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
                this.activity.finish();
                break;

            default:
                Log.e("Debug", "default");
                break;

        }
        return false;
    }
}
