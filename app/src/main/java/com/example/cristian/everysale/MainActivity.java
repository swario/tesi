package com.example.cristian.everysale;

import android.content.Intent;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import layout.mainFragment.OnFragmentInteractionListener;
import layout.mainFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("mytest");
        actionBar.setTitle("vogella.com");
        actionBar.setLogo(R.drawable.ic_menu_camera);
        actionBar.setDisplayUseLogoEnabled(true);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new mainFragment()).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent backHome = new Intent(this, MainActivity.class);
            startActivity(backHome);
            this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment1 = getFragmentManager().findFragmentById(R.layout.fragment_register_1);
        if(fragment1!=null){
            Intent backHome = new Intent(this, MainActivity.class);
            startActivity(backHome);
            this.finish();
            super.onBackPressed();
        }
        Fragment fragment2 = getFragmentManager().findFragmentById(R.layout.fragment_register_2);
        if(fragment2!=null){
            Intent backHome = new Intent(this, MainActivity.class);
            startActivity(backHome);
            this.finish();
            super.onBackPressed();
        }
        Fragment fragment3 = getFragmentManager().findFragmentById(R.layout.fragment_register_3);
        if(fragment3!=null){
            Intent backHome = new Intent(this, MainActivity.class);
            startActivity(backHome);
            this.finish();
            super.onBackPressed();
        }
    }
}
