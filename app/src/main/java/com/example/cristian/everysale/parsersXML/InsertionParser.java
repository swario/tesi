package com.example.cristian.everysale.parsersXML;

import com.example.cristian.everysale.BaseClasses.Feedback;
import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.BaseClasses.InsertionPreview;
import com.example.cristian.everysale.BaseClasses.SearchResponse;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class InsertionParser extends DefaultHandler {

    private Feedback feedback;
    private Insertion insertion;

    private boolean isInsertion = false;
    private boolean isInsertionId = false;
    private boolean isName = false;
    private boolean isPrice = false;
    private boolean isCity = false;
    private boolean isAddress = false;
    private boolean isInsertionistId = false;
    private boolean isInsertionistName = false;
    private boolean isRate = false;
    private boolean isInsertionDate = false;
    private boolean isExpirationDate = false;
    private boolean isDescription = false;
    private boolean isPhoto = false;
    private boolean isRatingUserId = false;
    private boolean isRatingUserName = false;
    private boolean isFeedBackText = false;

    public Insertion getInsertion(){ return this.insertion;}

    public void startDocument(){

        feedback = new Feedback();
        insertion = new Insertion();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("insertion")){
            isInsertion = true;
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
        if(qName.equals("city")){
            isCity = true;
            return;
        }
        if(qName.equals("address")) {
            isAddress = true;
            return;
        }
        if(qName.equals("insertionist_id")){
            isInsertionistId = true;
            return;
        }
        if(qName.equals("insertionist_name")){
            isInsertionistName = true;
            return;
        }
        if(qName.equals("rate")){
            isRate = true;
            return;
        }
        if(qName.equals("insertion_date")){
            isInsertionDate = true;
            return;
        }
        if(qName.equals("expiration_date")){
            isExpirationDate = true;
            return;
        }
        if(qName.equals("description")){
            isDescription = true;
            return;
        }
        if(qName.equals("photo_url")){
            isPhoto = true;
            return;
        }
        if(qName.equals("feedback")){
            feedback = new Feedback();
            return;
        }
        if(qName.equals("rating_user_id")){
            isRatingUserId = true;
            return;
        }
        if(qName.equals("rating_user_name")){
            isRatingUserName = true;
            return;
        }
        if(qName.equals("feedbackText")){
            isFeedBackText = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName){

        if(qName.equals("feedback")){
            insertion.addFeedback(feedback);
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ){

        String s = new String(ch, start, lenght);
        if(isInsertion){
            isInsertion = false;
            if(s.equals("no such insertion")){
                insertion = null;
            }
        }
        if(isInsertionId){
            isInsertionId = false;
            insertion.setInsertion_id(Integer.parseInt(s));
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
        if(isAddress){
            isAddress = false;
            insertion.setAddress(s);
        }
        if(isInsertionistId){
            isInsertionistId = false;
            insertion.setInsertionist_id(Integer.parseInt(s));
        }
        if(isInsertionistName){
            isInsertionistName = false;
            insertion.setInsertionist_name(s);
        }
        if(isRate){
            isRate = false;
            insertion.setInsertionist_rate(Float.parseFloat(s));
        }
        if(isInsertionDate){
            isInsertionDate = false;
            insertion.setInsertion_date(s);
        }
        if(isExpirationDate){
            isExpirationDate = false;
            insertion.setExpiration_date(s);
        }
        if(isDescription){
            isDescription = false;
            insertion.setDescription(s);
        }
        if(isPhoto){
            isPhoto = false;
            insertion.setPhoto_url(s);
        }
        if(isRatingUserId){
            isRatingUserId = false;
            feedback.setUserId(Integer.parseInt(s));
        }
        if(isRatingUserName){
            isRatingUserName = false;
            feedback.setUserName(s);
        }
        if(isFeedBackText){
            isFeedBackText = false;
            feedback.setfeedbackText(s);
        }
    }
}

