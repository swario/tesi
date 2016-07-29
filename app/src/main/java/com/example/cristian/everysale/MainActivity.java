package com.example.cristian.everysale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button loginButton;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference to the wigets
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        //set listener
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    //implement listener
    public void onClick(View v){
        switch (v.getId()){
            case R.id.loginButton:
                setContentView(R.layout.fragment_login);
                break;
            case R.id.registerButton:
                setContentView(R.layout.fragment_register_1);
                break;
        }
    }
}
