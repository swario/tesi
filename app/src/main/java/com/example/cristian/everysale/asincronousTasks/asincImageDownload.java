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
    }

    @Override
    protected void onPostExecute(Bitmap file){
        if(file != null){
            this.holder.setImageBitmap(file);
        }
        else{
            Log.d(TAG, "Image Does Not exist or Network Error");
        }
    }
}
