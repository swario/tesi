package com.example.cristian.everysale.asincronousTasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cristian.everysale.Main2Activity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class asincImageDownload extends AsyncTask<Object, Void, Bitmap> {

    private Context context;
    private Activity activity;

    private String image;
    private ImageView holder;

    public final String TAG="EverySale";
    public final String FILE="img.jpg";

    public asincImageDownload (Context context, Activity activity){

        this.context = context;
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {

        this.image = (String) params[0];
        this.holder = (ImageView) params[1];

        Bitmap img = null;

        try{
            InputStream in = new java.net.URL(image).openStream();
            img = BitmapFactory.decodeStream(in);
            /*URL url = new URL(image);

            URLConnection connection = url.openConnection();


            connection.setDoOutput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            FileOutputStream output = context.openFileOutput(FILE, Context.MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            while(bytesRead!=-1){
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
            output.close();
            input.close();
            FileInputStream fileInputStream =
            return FILE;*/
            Log.d(TAG, "Image downloaded");
        }
        catch (Exception e){
            Log.d(TAG, "Image download failed");
            //return null;
        }
        return img;
    }

    @Override
    protected void onPostExecute(Bitmap file){
        if (file == null){
            Log.d(TAG, "Immagine NON elaborata");
        }
        else{
            Log.d(TAG, "Immagine elaborata");
            holder.setImageBitmap(file);
        }
    }
}
