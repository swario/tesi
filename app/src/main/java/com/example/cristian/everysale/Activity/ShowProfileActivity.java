package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadUser;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincImageDownload;
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

    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= null;
        this.imageUrl = getResources().getString(R.string.image_url);
        Intent intent = getIntent();
        if(intent != null){
            long userId = intent.getLongExtra("otherUser", 0);
            Log.e("Debug", "Ricevuto:" + String.valueOf(userId));
            new asincDownloadUser(this, this, false).execute(userId);
        }else  {
            Log.e("Debug", "C'è un errore");
        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_profile , null, false);
        drawerLayout.addView(contentView, 0);

        setContentView(R.layout.activity_show_profile);

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
    }

    @Override
    public void setUser(User user) {
        if(user == null){
            Log.e("Debug", "Pinoli");
            return;
        }
        this.user = user;
        SetUpLayout();
    }

    private void SetUpLayout(){
        usernameText.setText(user.getUserName());
        Log.e("Debug", user.getUserName());
        new asincImageDownload(this, this).execute(this.imageUrl + user.getPhoto(), profilePic);
        ratingBar.setNumStars(5);
        ratingBar.setMax(5);
        ratingBar.setStepSize((float) 0.1);
        ratingBar.setRating(user.getRating());
        regionText.setText(user.getRegion());
        provinceText.setText(user.getProvince());
        municipalityText.setText(user.getMunicipality());
        if(user.getDataAllow()){
            nameText.setText(user.getName());
            surnameText.setText(user.getSurname());
            relativeLayout.setVisibility(View.VISIBLE);
            Log.e("Debug", "boh");
            if(user.getMobile() == null){
                mobileText.setText("Cellulare non disponibile");
            }
            else{
                mobileText.setText(user.getMobile());
            }
        }
        else{
            relativeLayout.setVisibility(View.GONE);
        }
    }
}
