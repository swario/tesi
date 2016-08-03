package com.example.cristian.everysale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.asincronousTasks.asincDownloadInsertion;
import com.example.cristian.everysale.asincronousTasks.asincImageDownload;

import org.w3c.dom.Text;

public class InsertionActivity extends AppCompatActivity {

    private Insertion insertion;
    private long insertionId;

    private TextView titleTextView;
    private ImageView imageView;
    private TextView priceTextView;
    private TextView cityTextView;
    private TextView addressTextView;
    private TextView shopTextView;
    private Button insertionistButton;
    private RatingBar ratingBar;
    private TextView insertionDate;
    private TextView expirationDate;
    private TextView description;

    private final String imageAddress = "http://webdev.dibris.unige.it/~S3928202/Progetto/itemPics/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            this.insertionId = intent.getLongExtra("insertionId", 0);
        }
        Toast.makeText(this, String.valueOf(insertionId), Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_insertion);

        new asincDownloadInsertion(this).execute(this.insertionId);

        titleTextView = (TextView) findViewById(R.id.insertion_title);
        imageView = (ImageView) findViewById(R.id.insertion_image);
        priceTextView = (TextView) findViewById(R.id.insertion_price_value);
        cityTextView = (TextView) findViewById(R.id.insertion_city_value);
        addressTextView = (TextView) findViewById(R.id.insertion_address_value);
        shopTextView = (TextView) findViewById(R.id.insertion_shop_value);
        insertionistButton = (Button) findViewById(R.id.item_insertionist_value);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        insertionDate = (TextView) findViewById(R.id.item_date1_value);
        expirationDate = (TextView) findViewById(R.id.item_date2_value);
        description = (TextView) findViewById(R.id.item_description_value);
    }

    public void setUpInsertion(Insertion insertion){

        this.insertion = insertion;
        setUpLayout();
    }

    public void setUpLayout(){
        new asincImageDownload(getBaseContext(), this).execute(imageAddress + insertion.getPhoto_url(), imageView);
        titleTextView.setText(insertion.getName());
        priceTextView.setText(String.valueOf(insertion.getPrice()) + "€");
        cityTextView.setText(insertion.getCity());
        addressTextView.setText(insertion.getAddress());
        shopTextView.setText(insertion.getShopName());
        insertionistButton.setText(insertion.getInsertionist_name());

    }
}
