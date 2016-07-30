package com.example.cristian.everysale;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class asincRegister extends AsyncTask<String, Void, String> {

    private Context context;

    public asincRegister(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String email = params[0];
        String username = params[1];
        String password = params[2];
        String name = params[3];
        String surname = params[4];
        String region = params[5];

        return null;
    }
}
