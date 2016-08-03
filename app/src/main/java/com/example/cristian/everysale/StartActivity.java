package com.example.cristian.everysale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cristian.everysale.asincronousTasks.asincLogin;

public class StartActivity extends AppCompatActivity {

    SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        String username = savedValues.getString("username", "");
        String password = savedValues.getString("password", "");
        ((SwipeRefreshLayout)  findViewById(R.id.start_activity_refresh_layout)).setRefreshing(true);

        if(username.equals("") || password.equals("")){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        else{
            new asincLogin(this).execute(username, password);
        }
    }
}
