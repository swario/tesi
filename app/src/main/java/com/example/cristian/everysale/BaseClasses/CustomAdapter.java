package com.example.cristian.everysale.BaseClasses;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.asincImageDownload;

import java.text.DecimalFormat;
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
    private Iterator<Long> id;
    private ArrayList<String> items;
    private static LayoutInflater inflater = null;
    private ListTab tab = null;

    public CustomAdapter(Context context, Activity activity, ArrayList<String> images, ArrayList<String> titles, ArrayList<String> prices, ArrayList<String> cities, ArrayList<Float> rating, ArrayList<Long> insertionsId, ListTab listTab) {

        this.context = context;
        this.activity = activity;
        image = images.iterator();
        title = titles.iterator();
        price = prices.iterator();
        city = cities.iterator();
        rate = rating.iterator();
        id = insertionsId.iterator();
        items = titles;
        this.tab = listTab;

        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            holder.title.setEnabled(false);
            holder.title.setText(title.next());
            holder.title.setEnabled(true);
            String number = new DecimalFormat("0.00").format(Double.parseDouble(price.next()));
            holder.price.setText(number + " €");
            holder.city.setText(city.next());
            holder.rating.setMax(5);
            holder.rating.setNumStars(5);
            holder.rating.setStepSize((float) 0.1);
            holder.rating.setRating(rate.next());
            new asincImageDownload(context, activity).execute(image.next(), holder.img);
            final long currentId = id.next();
            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    tab.goToInsertion(currentId);
                }
            });
        }
        return rowView;
    }
}