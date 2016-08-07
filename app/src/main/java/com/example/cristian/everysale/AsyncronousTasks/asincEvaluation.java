package com.example.cristian.everysale.AsyncronousTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.Activity.InsertionActivity;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.Fragments.InsertionDisplayFragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public class asincEvaluation extends AsyncTask<Object, Void, String> {

    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/evaluateInsertion.php";

    private InsertionDisplayFragment fragment;

    public asincEvaluation(InsertionDisplayFragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected String doInBackground(Object... params) {

        long insertionId = (Long) params[0];
        long userId = (Long) params[1];
        float rating = (Float) params[2];
        String feedbackText = (String) params[3];

        try{
            java.net.URL url = new URL(URL);
            String data = URLEncoder.encode("insertionId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(insertionId), "UTF-8");
            data += "&" + URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8");
            data += "&" + URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(rating), "UTF-8");
            data += "&" + URLEncoder.encode("feedbackText", "UTF-8") + "=" + URLEncoder.encode(feedbackText, "UTF-8");
            URLConnection connection = url.openConnection();
            Log.e("Debug", data);

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

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        this.fragment.OnFeebackResponse(result);
    }
}
