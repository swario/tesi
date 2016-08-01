package com.example.cristian.everysale.fragment;

/**
 * Created by ulisse on 31/07/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cristian.everysale.R;

/**
 * Created by hp1 on 21-01-2015.
 */
public class tabFavorite extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_favorite,container,false);
        return v;
    }
}