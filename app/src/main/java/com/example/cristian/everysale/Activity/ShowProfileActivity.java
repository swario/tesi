package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.cristian.everysale.R;

public class ShowProfileActivity extends navigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_show_profile , null, false);
        drawerLayout.addView(contentView, 0);


        setContentView(R.layout.activity_show_profile);
    }
}
