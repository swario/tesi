package com.example.cristian.everysale.Fragments.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristian.everysale.Activity.InsertionActivity;
import com.example.cristian.everysale.BaseClasses.InsertionArrayAdapter;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincGetRecent;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.R;

import java.util.ArrayList;

public class tabRecentOffers extends ListFragment implements OnRefreshListener, OnScrollListener, ListTab{

    private boolean loading = false;
    private boolean thereIsMore = true;
    private int oldItemsCount = 0;

    private int previousFirstVisibleItem;

    private ArrayList<InsertionPreview> previewArrayList;
    private SearchResponse searchResponse;
    private InsertionArrayAdapter adapter;

    private SwipeRefreshLayout refreshLayout;
    private ListView itemsListView;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recent_listview,container,false);

        itemsListView = (ListView) ((ViewGroup) view).getChildAt(1);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recent_refresh_layout);
        itemsListView.setOnScrollListener(this);
        //itemsListView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

        previousFirstVisibleItem = 0;
        previewArrayList = new ArrayList<>();

        searchResponse = null;
        adapter = new InsertionArrayAdapter(getContext(), getActivity());
        itemsListView.setAdapter(adapter);
        new asincGetRecent(this).execute();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void setSearchResponse(SearchResponse searchResponse){

        if(searchResponse == null){
            Toast.makeText(getContext(), "Connessione internet assente", Toast.LENGTH_LONG).show();
            return;
        }
        refreshLayout.setRefreshing(false);
        adapter.addAll(searchResponse.getInsertions());
        /*if(this.searchResponse == null){
            this.searchResponse = searchResponse;
            adapter = new InsertionArrayAdapter(getContext(), getActivity());
            adapter.addAll(searchResponse.getInsertions());
            itemsListView.setAdapter(adapter);
            //adapter.addAll(this.searchResponse.getInsertions());
        } else {
            oldItemsCount = this.searchResponse.getInsertionCount();
            for(int i = 0; i < searchResponse.getInsertionCount(); i++) {
                adapter.add(searchResponse.getInsertion(i));
            }
        }
        if(oldItemsCount == this.searchResponse.getInsertionCount()){
            thereIsMore = false;
        }
        oldItemsCount = this.searchResponse.getInsertionCount();
        loaded = true;*/
        Log.e("Debug", "Adapter: " + String.valueOf(adapter.getCount()));
        loading = false;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        this.thereIsMore = true;
        new asincGetRecent(this).execute();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem > previousFirstVisibleItem){//sto scrollando verso il basso

            if((firstVisibleItem + visibleItemCount) >= totalItemCount && !loading){// sono  giunto alla fine della lista

                loading = true;
                long upperLimit = adapter.getItem(adapter.getCount() -1).getInsertionId();
                new asincGetRecent(this).execute(upperLimit);
                refreshLayout.setRefreshing(true);
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

    /*@Override
    public void onGlobalLayout() {
        if(loaded){
            if((itemsListView.getLastVisiblePosition() >= (itemsListView.getChildCount() - 1)) && thereIsMore){
                long upperLimit = this.searchResponse.getInsertion(this.searchResponse.getInsertionCount() -1).getInsertionId();
                new asincGetRecent(this).execute(upperLimit);
            }
            if(oldItemsCount < this.searchResponse.getInsertionCount()){
                Log.e("Debug", "Quanti:" + String.valueOf(this.oldItemsCount));
                itemsListView.smoothScrollToPosition(oldItemsCount);
            }
        }
    }*/
}