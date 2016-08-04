package com.example.cristian.everysale.asincronousTasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cristian.everysale.InsertionActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Giorgiboy on 04/08/2016.
 */
public class asincRemoveFavorite extends AsyncTask<Long, Void, String>{

    private InsertionActivity activity;

    public asincRemoveFavorite(InsertionActivity activity){
        this.activity=activity;
    }

    @Override
    protected String doInBackground(Long... params) {
        long userId = params[0];
        long insertionId = params[1];

        try{
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/removeFavorite.php");
            String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8");
            data += "&" + URLEncoder.encode("insertionId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(insertionId), "UTF-8");

            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());

            streamWriter.write(data);
            streamWriter.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder("");
            String response = null;

            while((response = reader.readLine())!= null){
                Log.d("Debug", "Risposta: " + response);
                stringBuilder.append(response);
                break;
            }
            return stringBuilder.toString();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result){

        if(result.contains("success")){
            this.activity.setFavorite(false);
        }
        else if(result.contains("fail")){

        }
        else if(result == null || result.contains("No address associated with hostname")){
            Toast.makeText(this.activity, "Connessione Internet assente", Toast.LENGTH_LONG).show();
        }
    }
}