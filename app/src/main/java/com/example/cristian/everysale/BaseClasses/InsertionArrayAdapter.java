package com.example.cristian.everysale.BaseClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.AsyncronousTasks.asincImageDownload;
import com.example.cristian.everysale.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public class InsertionArrayAdapter extends ArrayAdapter<InsertionPreview> {

    private Activity activity;

    public InsertionArrayAdapter(Context context, Activity activity) {
        super(context,0);
        this.activity = activity;
    }

    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView price;
        TextView city;
        RatingBar rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = new ViewHolder();
        final InsertionPreview preview = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_layout, parent, false);
        }
        holder.title = (TextView) convertView.findViewById(R.id.item_title);
        holder.price = (TextView) convertView.findViewById(R.id.item_price);
        holder.img = (ImageView) convertView.findViewById(R.id.item_icon);
        holder.city = (TextView) convertView.findViewById(R.id.item_city);
        holder.rating = (RatingBar) convertView.findViewById(R.id.item_ratingBar);

        holder.title.setText(preview.getName());
        String number = new DecimalFormat("0.00").format(Double.parseDouble(String.valueOf(preview.getPrice())));
        holder.price.setText(number + " €");
        new asincImageDownload(this.activity, this.activity).execute("http://webdev.dibris.unige.it/~S3928202/Progetto/itemPics/" +preview.getImage(), holder.img);
        holder.city.setText(preview.getCity());
        holder.rating.setNumStars(5);
        holder.rating.setMax(5);
        holder.rating.setRating(preview.getRate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), String.valueOf(preview.getInsertionId()), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }
}
