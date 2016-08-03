package com.example.cristian.everysale.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cristian.everysale.R;


public class InsertionFragment extends Fragment {


    public static InsertionFragment newInstance(String param1, String param2) {
        InsertionFragment fragment = new InsertionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        long insertion_id = getArguments().getLong("insertionId");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insertion, container, false);
    }

}
