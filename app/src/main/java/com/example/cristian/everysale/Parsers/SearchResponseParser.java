package com.example.cristian.everysale.Parsers;

import android.util.Log;

import com.example.cristian.everysale.BaseClasses.Feedback;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SearchResponseParser extends DefaultHandler {

    private InsertionPreview insertion;
    private SearchResponse searchResponse;

    private boolean isSearchKey = false;
    private boolean isInsertionCount = false;
    private boolean isInsertionId = false;
    private boolean isName = false;
    private boolean isPrice = false;
    private boolean isCity = false;
    private boolean isRate = false;
    private boolean isPhoto = false;

    public SearchResponse getSearchResponse(){ return this.searchResponse; }

    public void startDocument(){

        insertion = new InsertionPreview();
        searchResponse = new SearchResponse();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("searchKey")){
            isSearchKey = true;
            return;
        }
        if(qName.equals("insertionsCount")){
            isInsertionCount = true;
            return;
        }
        if(qName.equals("insertion")){
            insertion = new InsertionPreview();
            return;
        }
        if(qName.equals("insertion_id")){
            isInsertionId = true;
            return;
        }
        if(qName.equals("name")){
            isName = true;
            return;
        }
        if(qName.equals("price")){
            isPrice = true;
            return;
        }
        if(qName.equals("municipality")){
            isCity = true;
            return;
        }
        if(qName.equals("rate")){
            isRate = true;
            return;
        }
        if(qName.equals("photo_url")) {
            isPhoto = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName){

        if(qName.equals("insertion")){
            searchResponse.addInsertion(insertion);
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ){

        String s = new String(ch, start, lenght);
        if(isSearchKey){
            isSearchKey = false;
            searchResponse.setSearchKey(s);
        }
        if(isInsertionCount){
            isInsertionCount = false;
            searchResponse.setInsertionCount(Integer.parseInt(s));
        }
        if(isInsertionId){
            isInsertionId = false;
            insertion.setInsertion_id(Integer.parseInt(s));
        }
        if(isRate){
            isRate = false;
            insertion.setRate(Float.parseFloat(s));
        }
        if(isName){
            isName = false;
            insertion.setName(s);
        }
        if(isPrice){
            isPrice = false;
            insertion.setPrice(Float.parseFloat(s));
        }
        if(isCity){
            isCity = false;
            insertion.setCity(s);
        }
        if(isPhoto){
            isPhoto = false;
            insertion.setPhoto(s);
        }
    }
}
