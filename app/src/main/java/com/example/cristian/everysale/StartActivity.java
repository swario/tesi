package com.example.cristian.everysale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cristian.everysale.asincronousTasks.asincLogin;

public class StartActivity extends AppCompatActivity {

    SharedPreferences savedValues;
    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        String username = savedValues.getString("username", "");
        String password = savedValues.getString("password", "");

        loadingBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        loadingBar.setVisibility(View.VISIBLE);

        if(username.equals("") || password.equals("")){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        else{
            new asincLogin(this, loadingBar).execute(username, password);
        }
    }
}
