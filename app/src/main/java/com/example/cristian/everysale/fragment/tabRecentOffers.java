package com.example.cristian.everysale.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristian.everysale.InsertionActivity;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.asincronousTasks.asincGetFavorites;
import com.example.cristian.everysale.asincronousTasks.asincGetRecent;
import com.example.cristian.everysale.BaseClasses.CustomAdapter;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.R;

import java.util.ArrayList;

public class tabRecentOffers extends ListFragment implements OnRefreshListener, OnScrollListener, ListTab{

    private boolean thereIsMore = true;

    private int previousFirstVisibleItem;
    
    private SearchResponse searchResponse;
    private SwipeRefreshLayout refreshLayout;

    private ListView itemsListView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent_listview,container,false);

        itemsListView = (ListView) ((ViewGroup) view).getChildAt(1);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recent_refresh_layout);
        itemsListView.setOnScrollListener(this);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

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

        ArrayList<String> images = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> prices = new ArrayList<>();
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<Float> rating = new ArrayList<>();
        ArrayList<Long> insertionsId = new ArrayList<>();
        
        for(int i=0; i<searchResponse.getInsertionCount(); i++){

            InsertionPreview preview = searchResponse.getInsertion(i);

            images.add("http://webdev.dibris.unige.it/~S3928202/Progetto/itemPics/" + preview.getImage());
            titles.add(preview.getName());
            prices.add(String.valueOf(preview.getPrice()));
            cities.add(preview.getCity());
            rating.add(preview.getRate());
            insertionsId.add(preview.getInsertionId());
        }
        CustomAdapter adapter= new CustomAdapter(getContext(), getActivity(), images, titles, prices, cities, rating, insertionsId, this);
        itemsListView.setAdapter(adapter);
    }

    public void setSearchResponse(SearchResponse searchResponse){
        if(searchResponse == null){
            Toast.makeText(getContext(), "Connessione internet assente", Toast.LENGTH_LONG).show();
            return;
        }
        int oldItemCount = 0;
        refreshLayout.setRefreshing(false);
        if(this.searchResponse == null){
            this.searchResponse = searchResponse;
        } else {
            oldItemCount = searchResponse.getInsertionCount();
            this.searchResponse.merge(searchResponse);
        }
        if(oldItemCount == searchResponse.getInsertionCount()){
            thereIsMore = false;
        }
        //Toast.makeText(getContext(), "Totale inserzioni: " + String.valueOf(this.searchResponse.getInsertionCount()), Toast.LENGTH_LONG).show();
        setListView();
        if((itemsListView.getLastVisiblePosition() >= (itemsListView.getChildCount() - 1)) && thereIsMore){
            long upperLimit = searchResponse.getInsertion(searchResponse.getInsertionCount() -1).getInsertionId();
            new asincGetRecent(this).execute(upperLimit);
        }
    }

    @Override
    public void onRefresh() {
        searchResponse = null;
        new asincGetRecent(this).execute();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem > previousFirstVisibleItem){//sto scrollando verso il basso

            if((firstVisibleItem + visibleItemCount) >= totalItemCount){// sono  giunto alla fine della lista

                long upperLimit = searchResponse.getInsertion(searchResponse.getInsertionCount() -1).getInsertionId();
                new asincGetRecent(this).execute(upperLimit);
            }
        }
        else if(previousFirstVisibleItem > firstVisibleItem){//sto scrollando verso l'alto

        }
        previousFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void goToInsertion(long pos){

        Intent intent = new Intent(getActivity(), InsertionActivity.class);
        intent.putExtra("insertionId", pos);
        startActivity(intent);
    }

}