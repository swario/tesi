package com.example.cristian.everysale.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.Activity.InsertionActivity;
import com.example.cristian.everysale.AsyncronousTasks.asincDownloadInsertion;
import com.example.cristian.everysale.AsyncronousTasks.asincEvaluation;
import com.example.cristian.everysale.AsyncronousTasks.asincImageDownload;
import com.example.cristian.everysale.BaseClasses.Feedback;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.HashMap;

public class InsertionDisplayFragment extends Fragment implements OnClickListener, OnItemClickListener, OnRatingBarChangeListener {

    private Insertion insertion;
    private long insertionId;
    private boolean isFavorite;
    private boolean isEvaluated;
    private SharedPreferences savedValues;

    private Menu menu;

    private InsertionActivity activity;
    private ScrollView scrollView;

    private RelativeLayout feedbackBox;
    private RatingBar feedbackRatingBar;
    private EditText feedbackEditText;
    private TextView feedbackRateTextView;

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
    private Button sendFeebackButton;

    private final String imageAddress = "http://webdev.dibris.unige.it/~S3928202/Progetto/itemPics/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_insertion_display, container, false);

        scrollView = (ScrollView) view.findViewById(R.id.insertion_scroll_view);

        feedbackBox = (RelativeLayout) view.findViewById(R.id.feedback_box);
        feedbackRatingBar = (RatingBar) view.findViewById(R.id.feedbackRatingBar);
        feedbackEditText = (EditText) view.findViewById(R.id.feedbackEditText);
        feedbackRateTextView = (TextView) view.findViewById(R.id.feedbackRateTextView);


        titleTextView = (TextView) view.findViewById(R.id.insertion_title);
        imageView = (ImageView) view.findViewById(R.id.insertion_image);
        priceTextView = (TextView) view.findViewById(R.id.insertion_price_value);
        cityTextView = (TextView) view.findViewById(R.id.insertion_city_value);
        addressTextView = (TextView) view.findViewById(R.id.insertion_address_value);
        shopTextView = (TextView) view.findViewById(R.id.insertion_shop_value);
        insertionistButton = (Button) view.findViewById(R.id.item_insertionist_value);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        insertionDate = (TextView) view.findViewById(R.id.item_date1_value);
        expirationDate = (TextView) view.findViewById(R.id.item_date2_value);
        description = (TextView) view.findViewById(R.id.item_description_value);
        listView = (ListView) view.findViewById(R.id.listView);
        sendFeebackButton = (Button) view.findViewById(R.id.feedbackSubmitButton);

        feedbackRatingBar.setOnRatingBarChangeListener(this);

        feedbackBox.setVisibility(View.GONE);

        activity = (InsertionActivity) getActivity();
        this.menu = activity.menu;
        //toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setActionBar(toolbar);
        this.insertionId = activity.getInsertionId();

        new asincDownloadInsertion(this).execute(this.insertionId);
        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        return view;
    }


    public void setUpInsertion(Insertion insertion){

        this.insertion = insertion;
        if(this.insertion != null){
            if (this.insertion.isFavorite()){
                this.isFavorite = true;
                this.activity.setFavorite(true);
                menu.getItem(1).setTitle("Remove Favorite");
                menu.getItem(1).setIcon(R.drawable.ic_star_24dp);
            }
            long id = savedValues.getLong("userId", 1);

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
            getActivity().finish();
        }
    }

    private void setUpLayout(){

        ratingBar.setMax(5);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float) 0.1);
        new asincImageDownload(getContext(), getActivity()).execute(imageAddress + insertion.getPhoto_url(), imageView);
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
        sendFeebackButton.setOnClickListener(this);
        FeedBackSetUp();
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ready, move up
                scrollView.smoothScrollTo(0, 0);
            }
        });
    }

    private void FeedBackSetUp(){

        ArrayList<Feedback> feedbacks = insertion.getFeedbacks();
        if(feedbacks.size() == 0){
            ((TextView) activity.findViewById(R.id.item_feedback)).setText("Non ci sono feedback");
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
        SimpleAdapter adapter = new SimpleAdapter(activity, data, resource, from, to);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.feedbackSubmitButton:
                sendFeedback();
                break;

            case R.id.item_insertionist_value:
                Toast.makeText(activity, "Utente n° " + String.valueOf(insertion.getInsertionist_id()), Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        long userId = insertion.getFeedbacks().get(position).getUserId();
        Toast.makeText(getContext(), "Utente: " + String.valueOf(userId), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
        if(fromUser){
            if(rating<1){
                feedbackRateTextView.setText("1");
                ratingBar.setRating(1);
            }
            else{
                feedbackRateTextView.setText("" + rating);
            }
        }
    }

    public void OnFeebackResponse(String response){

        if(response == null){
            Toast.makeText(getContext(), "Connessione Internet Assente", Toast.LENGTH_LONG).show();
        }
        else if(response.contains("good")){
            feedbackBox.setVisibility(View.GONE);
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
        else if (response.contains("No address associated with hostname")){
            Toast.makeText(getContext(), "Connessione Internet Assente", Toast.LENGTH_LONG).show();
        }
    }

    public void sendFeedback(){
        String feedbackText = feedbackEditText.getText().toString();
        float rate = Float.parseFloat(feedbackRateTextView.getText().toString());
        Long userId = savedValues.getLong("userId", 1);

        new asincEvaluation(this).execute(this.insertionId, userId, Float.valueOf(rate), feedbackText);
    }
}
