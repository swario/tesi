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
    //public  String file="img.jpg";

    public asincImageDownload (Context context, Activity activity){

        this.context = context;
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {

        this.image = (String) params[0];
        this.holder = (ImageView) params[1];

        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeStream((InputStream)new URL(image).getContent());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    /*    try{
            //InputStream in = new java.net.URL(image).openStream();
            //img = BitmapFactory.decodeStream(in);
            URL url = new URL(image);

            URLConnection connection = url.openConnection();


            connection.setDoOutput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            FileOutputStream output = context.openFileOutput(file, Context.MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            while(bytesRead!=-1){
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
            output.close();
            input.close();
            Log.d(TAG, "Image downloaded");
            return file;
        }
        catch (Exception e){
            Log.d(TAG, "Image download failed");
            return null;
        }*/
    }

    @Override
    protected void onPostExecute(Bitmap file){
        if(file != null){
            this.holder.setImageBitmap(file);
        }
        else{
            Log.d(TAG, "Image Does Not exist or Network Error");
        }
        /*if (file == null){
            Log.d(TAG, "Immagine NON elaborata");
        }
        else{
            Log.d(TAG, "Immagine elaborata");
            OpenFile
            holder.setImageBitmap(file);
        }*/
    }
}
