package com.example.cristian.everysale;

import java.util.ArrayList;

public class SearchResponse {

    private String searchKey;
    private ArrayList<Insertion> insertions;

    public SearchResponse(){
        this.insertions = new ArrayList<>();
    }

    public void setSearchKey(String sk) { this.searchKey = sk; }

    public void addInsertion(Insertion insertion){

        insertions.add(insertion);
    }
}