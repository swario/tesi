package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadInsertions;
import com.example.cristian.everysale.BaseClasses.InsertionArrayAdapter;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.R;

import java.util.List;

public class SearchActivity extends navigationDrawerActivity implements OnQueryTextListener, ListTab, OnRefreshListener {

    private SearchView searchView;
    private InsertionArrayAdapter adapter;
    private SharedPreferences savedValues;

    private SwipeRefreshLayout refreshLayout;
    private ListView itemsListView;
    private TextView responseText;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawerLayout.addView(contentView,0);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        responseText = (TextView) findViewById(R.id.searchResponseTextView);
        itemsListView = (ListView) findViewById(R.id.listView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.search_refresh_layout);
        adapter = new InsertionArrayAdapter(this, this);

        itemsListView.setAdapter(adapter);
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        //setto focus e apro tastiera
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.query = query;
        Log.e("Debug", query);
        new asincDownloadInsertions(this, this, query).execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    @Override
    public void goToInsertion(long pos) {

    }

    @Override
    public void SetResponse(SearchResponse response) {
        if(response == null){
            return;
        }
        refreshLayout.setRefreshing(false);
        adapter.addAll(response.getInsertions());
        if(adapter.getCount()>0){
            responseText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        new asincDownloadInsertions(this, this, query).execute();
    }
}
