package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincGetMyInsertions;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincGetRecent;
import com.example.cristian.everysale.BaseClasses.InsertionArrayAdapter;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.R;

import java.util.ArrayList;

public class MyInsertionsActivity extends navigationDrawerActivity implements OnRefreshListener, OnScrollListener, ListTab {

    private boolean loading = false;
    private boolean thereIsMore = true;
    private int oldItemsCount = 0;

    private int previousFirstVisibleItem;

    private ArrayList<InsertionPreview> previewArrayList;
    private SearchResponse searchResponse;
    private InsertionArrayAdapter adapter;

    private SwipeRefreshLayout refreshLayout;
    private ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_my_insertions, null, false);
        drawerLayout.addView(view, 0);

        itemsListView = (ListView) ((ViewGroup) view).getChildAt(1);

        new asincGetMyInsertions(this).execute();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.recent_refresh_layout);
        itemsListView.setOnScrollListener(this);
        //itemsListView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

        previousFirstVisibleItem = 0;
        previewArrayList = new ArrayList<>();

        searchResponse = null;
        adapter = new InsertionArrayAdapter(this.getApplicationContext(), this);
        itemsListView.setAdapter(adapter);
        //aggiungere asinctask
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void setSearchResponse(SearchResponse searchResponse){

        if(searchResponse == null){
            Toast.makeText(this.getApplicationContext(), "Connessione internet assente", Toast.LENGTH_LONG).show();
            return;
        }
        refreshLayout.setRefreshing(false);
        adapter.addAll(searchResponse.getInsertions());
        Log.e("Debug", "Adapter: " + String.valueOf(adapter.getCount()));
        loading = false;
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        this.thereIsMore = true;
        // aggiungere asinctask
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
                // new asincGetRecent(this).execute(upperLimit);
                refreshLayout.setRefreshing(true);
            }
        }
        else if(previousFirstVisibleItem > firstVisibleItem){//sto scrollando verso l'alto

        }
        previousFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void goToInsertion(long pos){
        Intent intent = new Intent(this, InsertionActivity.class);
        intent.putExtra("insertionId", pos);
        startActivity(intent);
    }
}
