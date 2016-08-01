package com.example.cristian.everysale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Giorgiboy on 29/07/2016.
 */
public class asincLogin extends AsyncTask<String, Void, String> {

    private Context context;
    public Boolean logged;
    private Activity activity;

    public asincLogin(Context context, Activity activity){

        this.context = context;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        String username =  params[0];
        String password =  params[1];

        try{
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/login.php");
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());

            streamWriter.write(data);
            streamWriter.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder("");
            String response = "";

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

    @Override
    protected void onPostExecute(String result){
        if(result.contains("No address associated with hostname")) {
            Toast.makeText(context, "Connessione Internet assente", Toast.LENGTH_LONG).show();
            logged= false;
        }
        else{
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent openPage1 = new Intent(activity , Main2Activity.class);
            // passo all'attivazione dell'activity Pagina.java
            activity.startActivity(openPage1);
            activity.finish();

        }
    }
}
