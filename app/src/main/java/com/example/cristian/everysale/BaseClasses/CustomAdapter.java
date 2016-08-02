package com.example.cristian.everysale.BaseClasses;

import android.content.Context;
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

public class CustomAdapter extends BaseAdapter{
    String [] title;
    String [] price;
    String [] city;
    Float [] rating;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] titleList, int[] images) {
        context=mainActivity;
        title =titleList;
        imageId=images;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
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
        rowView = inflater.inflate(R.layout.tab_recent_offers, null);
        holder.img=(ImageView) rowView.findViewById(R.id.item_icon);
        holder.title=(TextView) rowView.findViewById(R.id.item_title);
        holder.price=(TextView) rowView.findViewById(R.id.item_price);
        holder.city=(TextView) rowView.findViewById(R.id.item_city);
        holder.rating=(RatingBar) rowView.findViewById(R.id.item_ratingBar);
        holder.title.setText(title[position]);
        holder.price.setText(title[position]);
        holder.city.setText(title[position]);
        holder.rating.setMax(5);
        holder.rating.setNumStars(5);
        holder.rating.setRating(rating[position]);
        holder.img.setImageResource(imageId[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+ title[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}