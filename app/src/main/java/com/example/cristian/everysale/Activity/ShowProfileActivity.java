package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadUser;
import com.example.cristian.everysale.BaseClasses.User;
import com.example.cristian.everysale.Interfaces.UserDownloader;
import com.example.cristian.everysale.R;

public class ShowProfileActivity extends navigationDrawerActivity implements UserDownloader{

    private User user;

    private TextView usernameText;
    private ImageView profilePic;
    private RatingBar ratingBar;
    private TextView regionText;
    private TextView provinceText;
    private TextView municipalityText;
    private RelativeLayout relativeLayout;
    private TextView nameText;
    private TextView surnameText;
    private TextView mobileText;


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

        usernameText = (TextView) findViewById(R.id.usernameTextView);
        profilePic = (ImageView) findViewById(R.id.profileImageView);
        ratingBar = (RatingBar) findViewById(R.id.userRatingBar);
        regionText = (TextView) findViewById(R.id.showRegionTextView);
        provinceText = (TextView) findViewById(R.id.showProvinceTextView);
        municipalityText = (TextView) findViewById(R.id.showMunicipalityTextView);
        relativeLayout = (RelativeLayout) findViewById(R.id.dataAgreeBox);
        nameText = (TextView) findViewById(R.id.showNameTextView);
        surnameText = (TextView) findViewById(R.id.showSurnameTextView);
        mobileText = (TextView) findViewById(R.id.showPhonenumberTextView);

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
            relativeLayout.setVisibility(View.VISIBLE);
            if(user.getMobile() == null){
                mobileText.setText("Cellulare non disponibile");
            }
        }
        else{
            relativeLayout.setVisibility(View.GONE);
        }
    }
}
