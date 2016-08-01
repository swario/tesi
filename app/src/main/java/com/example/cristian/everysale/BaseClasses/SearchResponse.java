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

    public int getInsertionCount(){ return insertionCount; }
    public Insertion getInsertion( int pos){ return insertions.get(pos); }

    public void addInsertion(Insertion insertion){

        insertions.add(insertion);
    }

    public void merge(SearchResponse toBeMerged){

        int number = toBeMerged.getInsertionCount();
        for(int i = 0; i < number; i++){

            insertions.add(toBeMerged.getInsertion(i));
        }
        this.insertionCount += number;
    }
}