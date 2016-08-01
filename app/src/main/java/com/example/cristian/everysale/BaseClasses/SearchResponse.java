package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

public class SearchResponse {

    private String searchKey;
    private ArrayList<Insertion> insertions;
    private int insertionCount;

    public SearchResponse(){
        this.insertions = new ArrayList<>();
    }

    public void setSearchKey(String sk) { this.searchKey = sk; }
    public void setInsertionCount(int count) { this.insertionCount = count; }

    public void addInsertion(Insertion insertion){

        insertions.add(insertion);
    }
}