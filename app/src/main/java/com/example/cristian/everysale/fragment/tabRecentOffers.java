package com.example.cristian.everysale.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.cristian.everysale.BaseClasses.CustomAdapter;
import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.HashMap;

public class tabRecentOffers extends ListFragment {

    private ListView itemsListView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent_listview, container, false);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        setListView();
    }

    private void setListView(){
        ArrayList<String> images = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Float> rating = new ArrayList<>();
        String icon = "http://webdev.dibris.unige.it/~S3928202/Progetto/img/wrongIcon.jpg";
        String title = "Titolo";
        String city = "Città";
        String price = "25 €";
        float rate= (float) 2.5;
        for(int i=0; i<10; i++){
            images.add(icon);
            titles.add(title);
            prices.add(price);
            cities.add(city);
            rating.add(rate);
        }
        int resource = R.layout.listview_item_layout;
        String[] from = {"icon", "title", "price", "city"/*, "rating"*/};
        int[] to = {R.id.item_icon, R.id.item_title, R.id.item_price, R.id.item_city/*, R.id.item_ratingBar*/};
        CustomAdapter adapter= new CustomAdapter(getContext(), getActivity(), images, titles, prices, cities, rating);
        setListAdapter(adapter);
    }
}