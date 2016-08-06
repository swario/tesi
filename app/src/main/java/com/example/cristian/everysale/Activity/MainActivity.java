package com.example.cristian.everysale.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.cristian.everysale.R;
import com.example.cristian.everysale.Fragments.loginFragment;
import com.example.cristian.everysale.Fragments.mainFragment.OnFragmentInteractionListener;
import com.example.cristian.everysale.Fragments.mainFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Sottotitolo");
        actionBar.setTitle("Titolo");
        actionBar.setLogo(R.drawable.ic_menu_camera);
        actionBar.setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getBooleanExtra("goToLogin", false)) {
                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new loginFragment()).commit();
            }
            else{
                getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new mainFragment()).commit();
            }
        }
        else {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new mainFragment()).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final String TAG="EverySale";
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment fragment0 = getSupportFragmentManager().findFragmentByTag("loginFragment");
            Fragment fragment1 = getSupportFragmentManager().findFragmentByTag("registerFragment1");
            Fragment fragment2 = getSupportFragmentManager().findFragmentByTag("registerFragment2");
            Fragment fragment3 = getSupportFragmentManager().findFragmentByTag("registerFragment3");
            // Al posto di isVisible() si può usare anche isAdded()
            if(fragment0!=null && fragment0.isVisible()){
                Log.d(TAG, "Fragment 0");
                Intent backHome = new Intent(this, MainActivity.class);
                startActivity(backHome);
                this.finish();
            }
            else if(fragment1!=null && fragment1.isVisible()){
                Log.d(TAG, "Fragment 1");
                Intent backHome = new Intent(this, MainActivity.class);
                startActivity(backHome);
                this.finish();
            }
            else if(fragment2!=null && fragment2.isVisible()){
                Log.d(TAG, "Fragment 2");
                Intent backHome = new Intent(this, MainActivity.class);
                startActivity(backHome);
                this.finish();
            }
            else if(fragment3!=null && fragment3.isVisible()){
                Log.d(TAG, "Fragment 3");
                Intent backHome = new Intent(this, MainActivity.class);
                startActivity(backHome);
                this.finish();
            }
            else{
                Log.d(TAG, "Home");
                this.finish();
            }
            Log.d(TAG, "Back button pressed");
        }
        Log.d(TAG, "Back button NOT pressed");
        return super.onKeyDown(keyCode, event);
    }
}
