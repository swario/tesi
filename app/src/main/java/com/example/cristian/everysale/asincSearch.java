package com.example.cristian.everysale;


import android.os.AsyncTask;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class asincSearch extends AsyncTask<String, Void, Void> {


    @Override
    protected Void doInBackground(String... params) {

        String searchKey = params[0];

        try{
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(searchKey, "UTF-8");

            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/login.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());

            outputStreamWriter.write(data);
        }
        catch(Exception e){
        }
        return null;
    }
}
