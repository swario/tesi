package com.example.cristian.everysale.AsyncronousTasks.Senders;

import android.os.AsyncTask;

import com.example.cristian.everysale.Activity.InsertionActivity;
import com.example.cristian.everysale.Interfaces.Deleter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Giorgiboy on 09/08/2016.
 */
public class asincDeleteInsertion extends AsyncTask<Long, Void, String>{

    private InsertionActivity activity;
    private Deleter deleter;

    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/deleteInsertion.php";

    public asincDeleteInsertion(InsertionActivity activity, Deleter deleter){
        this.activity = activity;
        this.deleter = deleter;
    }

    @Override
    protected String doInBackground(Long... params) {

        long userId = params[0];
        long insertionId = params[1];

        try{
            URL url = new URL(this.URL);

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
                stringBuilder.append(response);
                break;
            }
            return stringBuilder.toString();
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    protected void onPostExecute(String result){

        activity.OnDeletion(result);
    }
}
