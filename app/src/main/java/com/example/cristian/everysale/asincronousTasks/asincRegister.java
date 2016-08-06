package com.example.cristian.everysale.asincronousTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cristian.everysale.Main2Activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class asincRegister extends AsyncTask<String, Void, String> {

    SharedPreferences savedValues;

    private Context context;
    private Activity activity;

    private String username;
    private String password;

    public asincRegister(Context context, Activity activity){

        savedValues = activity.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        String email = params[0];
        this.username = params[1];
        this.password = params[2];
        String name = params[3];
        String surname = params[4];
        String region = params[5];
        String city = params[6];
        String mobile = params[7];
        String dataAllow = params[8];

        try{
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/registerMobile.php");

            String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("cognome", "UTF-8") + "=" + URLEncoder.encode(surname, "UTF-8");
            data += "&" + URLEncoder.encode("regione", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8");
            data += "&" + URLEncoder.encode("citta", "UTF-8") + "=" + URLEncoder.encode(city, "UTF-8");
            data += "&" + URLEncoder.encode("mobile_phone", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
            data += "&" + URLEncoder.encode("dataAllow", "UTF-8") + "=" + URLEncoder.encode(dataAllow, "UTF-8");

            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());

            streamWriter.write(data);
            streamWriter.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder stringBuilder = new StringBuilder("");
            String response = null;

            while((response = reader.readLine()) != null){
                stringBuilder.append(response);
                break;
            }
            return stringBuilder.toString();

        }
        catch (Exception e){
            return e.getMessage() + "eccezione di qualche tipo";
        }
    }

    @Override
    protected void onPostExecute(String result){

        if(result.contains("success")){

            SharedPreferences.Editor editor = savedValues.edit();

            editor.putString("username", this.username);
            editor.putString("password", this.username);
            editor.putLong("userId", Long.parseLong(result.substring(8)));

            editor.commit();

            Intent openPage1 = new Intent(activity , Main2Activity.class);
            activity.startActivity(openPage1);
            activity.finish();
        }
    }
}
