package com.example.cristian.everysale;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import layout.loginFragment;
import layout.loginFragment.OnFragmentInteractionListener;
import layout.mainFragment;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private Button loginButton;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new mainFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
