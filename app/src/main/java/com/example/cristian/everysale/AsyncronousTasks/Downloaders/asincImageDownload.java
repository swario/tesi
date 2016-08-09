package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

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
