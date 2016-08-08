package com.example.cristian.everysale.AsyncronousTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import com.example.cristian.everysale.Activity.Main2Activity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class asincRegister extends AsyncTask<String, Void, String> {

    SharedPreferences savedValues;

    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/registerMobile.php";
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
        String filePath = params[9];

        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try{
            if(filePath != null){
                FileInputStream fileInputStream = new FileInputStream(new File(filePath) );

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("email", email));
                parameters.add(new BasicNameValuePair("password", password));
                parameters.add(new BasicNameValuePair("username", username));
                parameters.add(new BasicNameValuePair("nome", name));
                parameters.add(new BasicNameValuePair("cognome", surname));
                parameters.add(new BasicNameValuePair("regione", region));
                parameters.add(new BasicNameValuePair("citta", city));
                parameters.add(new BasicNameValuePair("mobile_phone", mobile));
                parameters.add(new BasicNameValuePair("dataAllow", dataAllow));
                URL url = new URL(URL + getQuery(parameters));
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                outputStream = new DataOutputStream( connection.getOutputStream() );
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + filePath +"\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                fileInputStream.close();
                outputStream.flush();
                outputStream.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder("");
                String response = null;

                while((response = reader.readLine()) != null){
                    stringBuilder.append(response);
                    break;
                }
                return stringBuilder.toString();
            }
            else{
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

                URLConnection connection_2 = url.openConnection();

                connection_2.setDoOutput(true);
                OutputStreamWriter streamWriter = new OutputStreamWriter(connection_2.getOutputStream());

                streamWriter.write(data);
                streamWriter.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection_2.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder("");
                String response = null;

                while((response = reader.readLine()) != null){
                    stringBuilder.append(response);
                    break;
                }
                return stringBuilder.toString();
            }

        }
        catch (Exception e){
            return e.getMessage() + "eccezione di qualche tipo";
        }
    }

    @Override
    protected void onPostExecute(String result){

        Log.e("Debug", result);
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

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first){
                first = false;
                result.append("?");
            }
            else {
                result.append("&");
            }
            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
