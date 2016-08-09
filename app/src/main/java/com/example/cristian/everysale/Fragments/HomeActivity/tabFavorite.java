package com.example.cristian.everysale.Fragments.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadInsertions;
import com.example.cristian.everysale.BaseClasses.DownloadType;
import com.example.cristian.everysale.BaseClasses.InsertionArrayAdapter;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Activity.InsertionActivity;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.R;

public class tabFavorite extends ListFragment implements OnRefreshListener, OnScrollListener, ListTab {

    private boolean thereIsMore = true;
    private int previousFirstVisibleItem;

    private SearchResponse searchResponse;
    private boolean loading = false;
    private SwipeRefreshLayout refreshLayout;
    private SharedPreferences savedValues;
    private InsertionArrayAdapter adapter;

    private ListView itemsListView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favorite_listview,container,false);

        itemsListView = (ListView) ((ViewGroup) view).getChildAt(1);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.favorite_refresh_layout);
        itemsListView.setOnScrollListener(this);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);

        previousFirstVisibleItem = 0;

        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);

        searchResponse = null;
        adapter = new InsertionArrayAdapter(getContext(), getActivity());
        itemsListView.setAdapter(adapter);

        new asincDownloadInsertions(getActivity(), this, DownloadType.Favorite).execute();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        thereIsMore = true;
        loading = true;
        new asincDownloadInsertions(getActivity(), this, DownloadType.Favorite).execute();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(firstVisibleItem > previousFirstVisibleItem){//sto scrollando verso il basso

            if((firstVisibleItem + visibleItemCount) >= totalItemCount && !loading){// sono  giunto alla fine della lista

                long upperLimit = searchResponse.getInsertion(searchResponse.getInsertionCount() -1).getInsertionId();
                new asincDownloadInsertions(getActivity(), this, DownloadType.Favorite).execute(upperLimit);
                loading = true;
            }
        }
        else if(previousFirstVisibleItem > firstVisibleItem){//sto scrollando verso l'alto

        }
        previousFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void goToInsertion(long pos) {
        Intent intent = new Intent(getActivity(), InsertionActivity.class);
        intent.putExtra("insertionId", pos);
        startActivity(intent);
    }

    @Override
    public void SetResponse(SearchResponse response) {

        if(response == null){
            Toast.makeText(getContext(), "Connessione internet assente", Toast.LENGTH_LONG).show();
            return;
        }
        refreshLayout.setRefreshing(false);
        adapter.addAll(response.getInsertions());

        Log.e("Debug", "Adapter: " + String.valueOf(adapter.getCount()));
        loading = false;
    }
}