package com.example.cristian.everysale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cristian.everysale.BaseClasses.Feedback;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.asincronousTasks.asincAddFavorite;
import com.example.cristian.everysale.asincronousTasks.asincDownloadInsertion;
import com.example.cristian.everysale.asincronousTasks.asincImageDownload;
import com.example.cristian.everysale.asincronousTasks.asincRemoveFavorite;

import java.util.ArrayList;
import java.util.HashMap;

public class InsertionActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {

    private Insertion insertion;
    private long insertionId;
    private SharedPreferences savedValues;
    private boolean isFavorite;
    private boolean isEvaluated;

    private Toolbar toolbar;
    private Menu menu;

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

    private ListView listView;

    private final String imageAddress = "http://webdev.dibris.unige.it/~S3928202/Progetto/itemPics/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            this.insertionId = intent.getLongExtra("insertionId", 0);
        }
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
        listView = (ListView) findViewById(R.id.listView);

        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setActionBar(toolbar);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    public void setUpInsertion(Insertion insertion){

        this.insertion = insertion;
        if(this.insertion != null){
            if (this.insertion.isFavorite()){
                this.isFavorite = true;
                menu.getItem(1).setTitle("Remove Favorite");
                menu.getItem(1).setIcon(R.drawable.ic_star_24dp);
            }
            long id = getSharedPreferences("SavedValues", MODE_PRIVATE).getLong("userId", 1);

            if(this.insertion.getInsertionist_id() == id) {
                menu.getItem(0).setTitle("Cancella");
                menu.getItem(0).setIcon(R.drawable.ic_delete_24dp);
            }
            else if (this.insertion.isEvaluated()){
                    this.isEvaluated = true;
                    menu.getItem(0).setVisible(false);
            }
            setUpLayout();
        }
        else{
            this.finish();
        }
    }

    private void setUpLayout(){

        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.1);
        new asincImageDownload(getBaseContext(), this).execute(imageAddress + insertion.getPhoto_url(), imageView);
        titleTextView.setText(insertion.getName());
        priceTextView.setText(String.valueOf(insertion.getPrice()) + "€");
        cityTextView.setText(insertion.getCity());
        addressTextView.setText(insertion.getAddress());
        shopTextView.setText(insertion.getShopName());
        insertionistButton.setText(insertion.getInsertionist_name());
        ratingBar.setRating(insertion.getInsertionist_rate());
        insertionDate.setText(insertion.getInsertion_date());
        expirationDate.setText(insertion.getExpiration_date());
        description.setText(insertion.getDescription());

        insertionistButton.setOnClickListener(this);
        FeedBackSetUp();
    }

    private void FeedBackSetUp(){

        ArrayList<Feedback> feedbacks = insertion.getFeedbacks();
        if(feedbacks.size() == 0){
            ((TextView) findViewById(R.id.item_feedback)).setText("Non ci sono feedback");
            return;
        }
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        for(int i = 0; i < feedbacks.size(); i++){
            HashMap<String, String> map = new HashMap<String, String>();
            Feedback feedback = feedbacks.get(i);
            map.put("user", feedback.getUserName());
            map.put("comment", feedback.getDescription());
            data.add(map);
        }

        int resource = R.layout.feedback_list_item;
        String[] from = {"user", "comment"};
        int[] to = {R.id.feedback_item_username, R.id.feedback_item_comment};
        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Utente n° " + String.valueOf(insertion.getInsertionist_id()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        long userId = insertion.getFeedbacks().get(position).getUserId();
    }


    //Favorite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.insertion_menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.add_to_favorite_button:
                long userId = savedValues.getLong("userId", 1);
                if(this.isFavorite){
                    new asincRemoveFavorite(this).execute(userId, this.insertionId);
                    break;
                }
                else{
                    new asincAddFavorite(this).execute(userId ,this.insertionId);
                }
                break;

            case R.id.rate_insertion_button:
                Toast.makeText(this, "cazzi", Toast.LENGTH_LONG).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFavorite(boolean isFavorite){
        this.isFavorite = isFavorite;
        if(isFavorite){
            menu.getItem(1).setTitle("Remove Favorite");
            menu.getItem(1).setIcon(R.drawable.ic_star_24dp);
        }
        else{
            menu.getItem(1).setTitle("Add to Favorite");
            menu.getItem(1).setIcon(R.drawable.ic_star_outline_24dp);
        }
    }
}
