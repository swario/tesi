package com.example.cristian.everysale.AsyncronousTasks.Senders;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.Activity.ModifyProfileActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
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

public class asincProfileUpdate extends AsyncTask<String, Void, String>{

    private ModifyProfileActivity activity;
    private SharedPreferences savedValues;
    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/modifyProfileMobile.php";

    private String username;
    private String password;

    public asincProfileUpdate(ModifyProfileActivity activity){
        this.activity = activity;
        savedValues = this.activity.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }

    @Override
    protected String doInBackground(String... params) {

        String email = params[0];
        this.username = params[1];
        this.password = params[2];
        String region = params[3];
        String province = params[4];
        String municipality = params[5];
        String name = params[6];
        String surname = params[7];
        String mobile = params[8];
        String dataAllow = params[9];
        String fileName = params[10];

        long userId = savedValues.getLong("userId", 1);

        DataOutputStream outputStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        List<NameValuePair> pairList = new ArrayList<>();
        pairList.add(new BasicNameValuePair("userId", String.valueOf(userId)));
        pairList.add(new BasicNameValuePair("email", email));
        pairList.add(new BasicNameValuePair("username", username));
        pairList.add(new BasicNameValuePair("password", password));
        pairList.add(new BasicNameValuePair("region", region));
        pairList.add(new BasicNameValuePair("province", province));
        pairList.add(new BasicNameValuePair("municipality", municipality));
        pairList.add(new BasicNameValuePair("name", name));
        pairList.add(new BasicNameValuePair("surname", surname));
        pairList.add(new BasicNameValuePair("mobile", mobile));
        pairList.add(new BasicNameValuePair("dataAllow", dataAllow));
        try{
            if(fileName != null){
                //Log.e("Debug", this.URL + "?" + getQuery(pairList));
                HttpURLConnection connection = null;
                FileInputStream inputStream = new FileInputStream(new File(fileName));

                URL url = new URL(this.URL + "?" + getQuery(pairList));
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                outputStream = new DataOutputStream( connection.getOutputStream() );
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName +"\"" + lineEnd);
                outputStream.writeBytes(lineEnd);

                bytesAvailable = inputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // Read file
                bytesRead = inputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    outputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = inputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = inputStream.read(buffer, 0, bufferSize);
                }

                outputStream.writeBytes(lineEnd);
                outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                inputStream.close();
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
            else {
                //Log.e("Debug", this.URL + "?" + getQuery(pairList));
                URL url = new URL(URL);
                URLConnection connection = url.openConnection();

                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                writer.write(getQuery(pairList));
                writer.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder builder = new StringBuilder("");
                String response = null;

                while ((response = reader.readLine()) != null){
                    builder.append(response);
                    break;
                }
                return builder.toString();
            }
        }
        catch (Exception e){
            Log.e("Debug", e.getMessage());
        }

        return null;
    }

    protected void onPostExecute(String result){

        if(result.contains("success")){

            Editor editor = savedValues.edit();

            editor.putString("username", this.username);
            if(!password.isEmpty()){
                editor.putString("password", this.password);
            }
            editor.commit();
        }
        this.activity.OnResponse(result);
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first){
                first = false;
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
