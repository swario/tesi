package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cristian.everysale.AsyncronousTasks.asincDownloadUser;
import com.example.cristian.everysale.BaseClasses.User;
import com.example.cristian.everysale.Interfaces.UserDownloader;
import com.example.cristian.everysale.R;

public class ShowProfileActivity extends navigationDrawerActivity implements UserDownloader{

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            long userId = intent.getLongExtra("userId", 1);
            new asincDownloadUser(this, this, false).execute(userId);
        }else  {
            //cose da fare
        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_profile , null, false);
        drawerLayout.addView(contentView, 0);

        setContentView(R.layout.activity_show_profile);
    }

    @Override
    public void setUser(User user) {
        this.user = user;

        SetUpLayout();
    }

    private void SetUpLayout(){
        //inserisco i dati
        if(user.getDataAllow()){
            //mostro i dati e li riempo
            if(user.getMobile() == null){
                //nascondo il cellulare
            }
        }
        else{
            //nascondo i dati
        }
    }
}
