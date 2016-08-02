package com.example.cristian.everysale.asincronousTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.example.cristian.everysale.Main2Activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class asincLogin extends AsyncTask<String, Void, String> {

    SharedPreferences savedValues;

    private Context context;
    private Activity activity;

    private String username;
    private String password;

    public asincLogin(Context context, Activity activity){

        savedValues = activity.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        this.username =  params[0];
        this.password =  params[1];

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
        if (result == null){
            Toast.makeText(context, "Connessione Internet assente", Toast.LENGTH_LONG).show();
        }
        else if(result.contains("No address associated with hostname")) {
            Toast.makeText(context, "Connessione Internet assente", Toast.LENGTH_LONG).show();
        }
        else if(result.contains("success")){
            Editor editor = savedValues.edit();

            editor.putString("username", this.username);
            editor.putString("password", this.username);
            String[] array = result.split("-");
            int id = Integer.parseInt(array[1]);
            editor.putInt("userId", id);

            editor.commit();

            Intent openPage1 = new Intent(activity , Main2Activity.class);
            activity.startActivity(openPage1);
            activity.finish();

        }
        else if(result.contains("wrong input")){

            Toast.makeText(context, "Username o password\nnon corretti", Toast.LENGTH_LONG).show();

        }
    }
}
