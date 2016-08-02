package com.example.cristian.everysale.BaseClasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.MainActivity;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.asincronousTasks.asincImageDownload;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomAdapter extends BaseAdapter{

    private Context context;
    private Activity activity;
    private Iterator<String> image;
    private Iterator<String> title;
    private Iterator<String> price;
    private Iterator<String> city;
    private Iterator<Float> rate;
    private static LayoutInflater inflater=null;

    public CustomAdapter(Context context, Activity activity, ArrayList<String> images, ArrayList<String> titles, ArrayList<String> prices, ArrayList<String> cities, ArrayList<Float> rating) {
        this.context = context;
        this.activity = activity;
        image = images.iterator();
        title = titles.iterator();
        price = prices.iterator();
        city = cities.iterator();
        rate = rating.iterator();

        inflater = ( LayoutInflater ) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        ImageView img;
        TextView title;
        TextView price;
        TextView city;
        RatingBar rating;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.listview_item_layout, null);
        holder.img = (ImageView) rowView.findViewById(R.id.item_icon);
        holder.title = (TextView) rowView.findViewById(R.id.item_title);
        holder.price = (TextView) rowView.findViewById(R.id.item_price);
        holder.city = (TextView) rowView.findViewById(R.id.item_city);
        holder.rating = (RatingBar) rowView.findViewById(R.id.item_ratingBar);
        if(title.hasNext()) {
            holder.title.setText(title.next());
            holder.price.setText(price.next());
            holder.city.setText(city.next());
            holder.rating.setMax(5);
            holder.rating.setNumStars(5);
            holder.rating.setStepSize((float) 0.1);
            //holder.rating.setProgressTintList();
            holder.rating.setRating(rate.next());
            new asincImageDownload(context, activity).execute(image.next(), holder.img);
            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "You Clicked Titolo", Toast.LENGTH_LONG).show();
                }
            });
        }
        return rowView;
    }
}