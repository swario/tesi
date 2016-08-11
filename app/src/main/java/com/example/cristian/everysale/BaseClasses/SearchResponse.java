package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

public class SearchResponse {

    private String searchKey;
    private ArrayList<InsertionPreview> insertions;
    private int insertionCount;

    public SearchResponse(){
        this.insertions = new ArrayList<>();
    }

    public void setSearchKey(String sk) { this.searchKey = sk; }
    public void setInsertionCount(int count) { this.insertionCount = count; }

    public int getInsertionCount(){ return insertionCount; }
    public InsertionPreview getInsertion( int pos){ return insertions.get(pos); }

    public void addInsertion(InsertionPreview insertion){

        insertions.add(insertion);
    }
    public ArrayList<InsertionPreview> getInsertions(){ return this.insertions;}

    public void merge(SearchResponse toBeMerged){

        int number = toBeMerged.getInsertionCount();
        for(int i = 0; i < number; i++){

            insertions.add(toBeMerged.getInsertion(i));
        }
        this.insertionCount += number;
    }
}