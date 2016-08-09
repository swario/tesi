package com.example.cristian.everysale.AsyncronousTasks.Senders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.Activity.Main2Activity;
import com.example.cristian.everysale.Activity.MainActivity;
import com.example.cristian.everysale.Interfaces.LoginPerformer;
import com.example.cristian.everysale.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class asincLogin extends AsyncTask<String, Void, String> {

    private Activity activity;
    private Context context;
    private LoginPerformer performer;
    private ProgressBar loadingBar;

    private String username;
    private String password;

    public asincLogin( Activity activity, LoginPerformer loginPerformer){
        this.activity = activity;
        this.performer = loginPerformer;
        this.context = this.activity.getBaseContext();
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

            this.performer.OnConnectionAbsent();
        }
        else if(result.contains("No address associated with hostname")) {
            this.performer.OnConnectionAbsent();
        }
        else if(result.contains("success")){

            String[] array = result.split("-");
            long id = Long.parseLong(array[1]);
            performer.OnLoginSuccess(this.username, this.password, id);
        }
        else if(result.contains("wrong input")){

            performer.OnLoginFailed();
        }
    }
}
