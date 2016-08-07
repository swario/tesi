package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.example.cristian.everysale.R;

public class SearchActivity extends navigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawerLayout.addView(contentView,0);

        SearchView searchView = (SearchView) findViewById(R.id.searchView);

        //setto focus e apro tastiera
        searchView.requestFocus();
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
    }
}
