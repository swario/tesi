package com.example.cristian.everysale.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.example.cristian.everysale.R;

public class evaluateInsertionFragment extends Fragment implements OnRatingBarChangeListener{

    private RatingBar feedbackRatingBar;
    private TextView feedbackTextView;

    public evaluateInsertionFragment() {
        // Required empty public constructor
    }

    public static evaluateInsertionFragment newInstance(String param1, String param2) {
        evaluateInsertionFragment fragment = new evaluateInsertionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluate_insertion, container, false);

        feedbackRatingBar = (RatingBar) view.findViewById(R.id.feedbackRatingBar);
        feedbackTextView = (TextView) view.findViewById(R.id.feedbackRateTextView);

        feedbackRatingBar.setOnRatingBarChangeListener(this);

        return view;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser){
        if(fromUser){
            feedbackTextView.setText("" + rating);
        }
    }
}
