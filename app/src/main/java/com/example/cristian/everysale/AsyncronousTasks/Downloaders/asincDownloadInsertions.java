package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Interfaces.ListTab;

/**
 * Created by Giorgiboy on 09/08/2016.
 */
public class asincDownloadInsertions  extends AsyncTask<Long, Void, SearchResponse>{

    public ListTab tab;
    public Activity activity;

    public asincDownloadInsertions(Activity activity, ListTab tab){
        this.tab = tab;
        this.activity = activity;
    }

    @Override
    protected SearchResponse doInBackground(Long... params) {
        return null;
    }
}
