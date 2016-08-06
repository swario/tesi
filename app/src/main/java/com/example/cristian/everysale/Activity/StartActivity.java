package com.example.cristian.everysale.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.asincLogin;

public class StartActivity extends AppCompatActivity implements OnClickListener {

    private SharedPreferences savedValues;
    private ProgressBar loadingBar;

    private String username;
    private String password;

    private Button retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        this.username = savedValues.getString("username", "");
        this.password = savedValues.getString("password", "");

        retryButton = (Button) findViewById(R.id.retry_button);
        loadingBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        retryButton.setVisibility(View.GONE);
        new asincLogin(this, loadingBar).execute(this.username, this.password);
    }

    public void onLoginFailed(){
        retryButton.setVisibility(View.VISIBLE);
    }
}
