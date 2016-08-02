package com.example.cristian.everysale.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.cristian.everysale.asincronousTasks.asincGetRecent;
import com.example.cristian.everysale.BaseClasses.CustomAdapter;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.asincronousTasks.asincGetRecent;

import java.util.ArrayList;
import java.util.HashMap;

public class tabRecentOffers extends ListFragment implements SwipeRefreshLayout.OnRefreshListener{

    private int previousFirstVisibleItem;
    
    private SearchResponse searchResponse;
    private SwipeRefreshLayout refreshLayout;

    private ListView itemsListView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("Debug", "inflato");
        view = inflater.inflate(R.layout.recent_listview,container,false);
        Log.e("Debug", "Inizio");

        //itemsListView = (ListView) inflater.inflate(R.layout.recent_listview,container,false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recent_refresh_layout);
        view.setOnScrollChangeListener(this);

        refreshLayout.setOnRefreshListener(this);
        Log.e("Debug", "Listener settato");

        previousFirstVisibleItem = 0;


        searchResponse = null;
        new asincGetRecent(this).execute();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void setListView(){

        ArrayList<String> images;
        ArrayList<String> titles;
        ArrayList<String> prices;
        ArrayList<String> cities;
        ArrayList<Float> rating;

        String icon = "http://webdev.dibris.unige.it/~S3928202/Progetto/img/favicon.jpg";
        String title = "Titolo1";
        String price = "25 €";
        String city = "Città1"
        float rate = (float) 2.5;

        for(int i=0; i < searchResponse.getInsertionCount(); i++){
            InsertionPreview preview = searchResponse.getInsertion(i);
            HashMap<String, String> map = new HashMap<>();
            images.add(icon);
            titles.add(title);
            price.add(price);
            city.add(city);
            rating.add(rate);
            data.add(map);
        }
        CustomAdapter adapter= new CustomAdapter(context, activity, images, titles, prices, cities, rating);
        setListAdapter(adapter);
    }

    public void setSearchResponse(SearchResponse searchResponse){
        if(this.searchResponse == null){
            this.searchResponse = searchResponse;
            refreshLayout.setRefreshing(false);
        } else {
            this.searchResponse.merge(searchResponse);
        }
        Toast.makeText(getContext(), "Totale inserzioni: " + String.valueOf(this.searchResponse.getInsertionCount()),
                Toast.LENGTH_LONG).show();
        setListView();
    }

    @Override
    public void onRefresh() {
        searchResponse = null;
        new asincGetRecent(this).execute();
    }
    
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if(scrollY > oldScrollY){
            Toast.makeText(getContext(), "Scrolla verso il basso", Toast.LENGTH_LONG).show();
        }
    }
}