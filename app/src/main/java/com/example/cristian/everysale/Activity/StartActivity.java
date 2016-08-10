package com.example.cristian.everysale.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cristian.everysale.Interfaces.LoginPerformer;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincLogin;

public class StartActivity extends AppCompatActivity implements OnClickListener, LoginPerformer {

    private SharedPreferences savedValues;
    private ProgressBar loadingBar;
    private TextView messageText;

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
        messageText = (TextView) findViewById(R.id.loading_text_view);
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
            new asincLogin(this, this).execute(username, password);
        }
    }

    @Override
    public void onClick(View v) {
        retryButton.setVisibility(View.GONE);
        loadingBar.setVisibility(View.VISIBLE);
        messageText.setText("Accesso in corso");
        new asincLogin(this, this).execute(this.username, this.password);
    }

    public void onLoginFailed(){
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnLoginSuccess(String username, String password, long id){

        Editor editor = savedValues.edit();

        editor.putLong("userId", id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void OnConnectionAbsent() {

        loadingBar.setVisibility(View.GONE);
        messageText.setText("Connessione Assente");
        retryButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnLoginFailed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("goToLogin", true);
        startActivity(intent);
        this.finish();
    }
}
