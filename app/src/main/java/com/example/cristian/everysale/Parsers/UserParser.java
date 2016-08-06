package com.example.cristian.everysale.Parsers;

import com.example.cristian.everysale.BaseClasses.User;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Giorgiboy on 01/08/2016.
 */
public class UserParser extends DefaultHandler {

    private boolean isUser = false;
    private boolean isUserId = false;
    private boolean isUserName = false;
    private boolean isName = false;
    private boolean isSurname = false;
    private boolean isPhoto = false;
    private boolean isRating = false;
    private boolean isRegion = false;
    private boolean isCity = false;
    private boolean isMobile = false;

    private User user;

    public void startDocument(){

        user = new User();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("")){
            isUser = true;
            return;
        }
        if(qName.equals("user_id")){
            isUserId = true;
            return;
        }
        if(qName.equals("username")){
            isUserName = true;
            return;
        }
        if(qName.equals("region")){
            isRegion = true;
            return;
        }
        if(qName.equals("city")){
            isCity = true;
            return;
        }
        if(qName.equals("rating")){
            isRating = true;
            return;
        }
        if(qName.equals("photo")){
            isPhoto = true;
            return;
        }
        if(qName.equals("name")){
            isName = true;
            return;
        }
        if(qName.equals("surname")){
            isSurname = true;
            return;
        }
        if(qName.equals("mobile")){
            isMobile = true;
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ){

        String s = new String(ch, start, lenght);
        if(isUser){
            isUser = false;
            if(s.equals("no such user")){
                user = null;
            }
        }
        if(isUserId){
            isUserId = false;
            user.setUser_id(Integer.parseInt(s));
        }
        if(isUserName){
            isUserName = false;
            user.setUserName(s);
        }
        if(isRegion){
            isRegion = false;
            user.setRegion(s);
        }
        if(isCity){
            isCity = false;
            user.setCity(s);
        }
        if(isRating){
            isRating = false;
            user.setRating(Float.parseFloat(s));
        }
        if(isPhoto){
            isPhoto = false;
            user.setPhoto(s);
        }
        if(isName){
            isName = false;
            user.setName(s);
        }
        if(isSurname){
            isSurname = false;
            user.setSurname(s);
        }
        if(isMobile){
            isMobile = false;
            user.setMobile(s);
        }
    }


}
