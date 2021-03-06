package com.example.cristian.everysale.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadInsertions;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincDeleteInsertion;
import com.example.cristian.everysale.BaseClasses.DownloadType;
import com.example.cristian.everysale.BaseClasses.InsertionArrayAdapter;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Interfaces.Deleter;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.R;

import java.util.ArrayList;

public class MyInsertionsActivity extends navigationDrawerActivity implements OnRefreshListener, OnScrollListener, ListTab,Deleter{

    private boolean loading = false;
    private long userId;
    private long insertionId;

    private int previousFirstVisibleItem;

    private InsertionArrayAdapter adapter;
    private SharedPreferences savedValues;

    private SwipeRefreshLayout refreshLayout;
    private ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_my_insertions, null, false);
        drawerLayout.addView(view, 0);

        itemsListView = (ListView) ((ViewGroup) view).getChildAt(1);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.myinsertions_refresh_layout);
        itemsListView.setOnScrollListener(this);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        userId = savedValues.getLong("userId", 0);

        previousFirstVisibleItem = 0;
        adapter = new InsertionArrayAdapter(this, this);
        itemsListView.setAdapter(adapter);

        new asincDownloadInsertions(this, this, DownloadType.MyInsertions).execute();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        new asincDownloadInsertions(this, this, DownloadType.MyInsertions).execute();
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
                new asincDownloadInsertions(this, this, DownloadType.MyInsertions).execute(upperLimit);
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

    @Override
    public void SetResponse(SearchResponse response) {

        if(response == null){
            Toast.makeText(this, "Connessione internet assente", Toast.LENGTH_LONG).show();
            return;
        }
        refreshLayout.setRefreshing(false);
        adapter.addAll(response.getInsertions());
        loading = false;
    }


    @Override
    public void OnDeletion(String message) {
        if(message.contains("good")){
        }
    }
}
