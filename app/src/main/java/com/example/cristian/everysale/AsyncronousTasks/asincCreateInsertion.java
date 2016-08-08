package com.example.cristian.everysale.AsyncronousTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.Activity.aNewInsertionActivity;

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

/**
 * Created by Giorgiboy on 08/08/2016.
 */
public class asincCreateInsertion extends AsyncTask<String, Void, String> {

    private aNewInsertionActivity activity;
    private SharedPreferences savedValues;

    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/newInsertionMobile.php";

    public asincCreateInsertion(aNewInsertionActivity aNewInsertionActivity){

        this.activity = aNewInsertionActivity;
        savedValues = this.activity.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }

    @Override
    protected String doInBackground(String... params) {

        long userId = savedValues.getLong("userId", 1);

        String name = params[0];
        String region = params[1];
        String province = params[2];
        String municipality = params[3];
        String address = params[4];
        String shop_name = params[5];
        String expiration_date = params[6];
        String photo = params[7];
        String price = params[8];
        String description = params[9];

        DataOutputStream outputStream = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try{
            if(photo!=null){

                HttpURLConnection connection = null;
                FileInputStream inputStream = new FileInputStream(new File(photo));

                List<NameValuePair> pairList = new ArrayList<>();
                pairList.add(new BasicNameValuePair("name", name));
                pairList.add(new BasicNameValuePair("region", region));
                pairList.add(new BasicNameValuePair("province", province));
                pairList.add(new BasicNameValuePair("municipality", municipality));
                pairList.add(new BasicNameValuePair("address", address));
                pairList.add(new BasicNameValuePair("shopName", shop_name));
                pairList.add(new BasicNameValuePair("expirationDate", expiration_date));
                pairList.add(new BasicNameValuePair("price", price));
                pairList.add(new BasicNameValuePair("description", description));

                URL url = new URL(this.URL + getQuery(pairList));
                connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                outputStream = new DataOutputStream( connection.getOutputStream() );
                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
                outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + photo +"\"" + lineEnd);
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

            }else {
                URL url = new URL(URL);

                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                data += "&" + URLEncoder.encode("region", "UTF-8") + "=" + URLEncoder.encode(region, "UTF-8");
                data += "&" + URLEncoder.encode("province", "UTF-8") + "=" + URLEncoder.encode(province, "UTF-8");
                data += "&" + URLEncoder.encode("municipality", "UTF-8") + "=" + URLEncoder.encode(municipality, "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
                data += "&" + URLEncoder.encode("shopName", "UTF-8") + "=" + URLEncoder.encode(shop_name, "UTF-8");
                data += "&" + URLEncoder.encode("expirationDate", "UTF-8") + "=" + URLEncoder.encode(expiration_date, "UTF-8");
                data += "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8");
                data += "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");

                URLConnection connection = url.openConnection();

                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                writer.write(data);
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
        }catch (Exception e){
            return e.getMessage() + "eccezione di qualche tipo";
        }
    }

    @Override
    protected void onPostExecute(String result){
        Log.e("Debug", result);
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
