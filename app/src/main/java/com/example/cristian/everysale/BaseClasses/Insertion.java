package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

public class Insertion {

    private boolean isFavorite;
    private long insertion_id;
    private String name;
    private boolean evaluated;
    private float price;
    private String city;
    private String address;
    private String shop_name;
    private int insertionist_id;
    private String insertionist_name;
    private float insertionist_rate;
    private String insertion_date;
    private String expiration_date;
    private String description;
    private String photo_url;
    private ArrayList<Feedback> feedbacks;

    public Insertion(int insertion_id, String name, float price, String city, String address, int insertionist_id,
                     String insertionist_name, float insertionist_rate, String insertion_date, String expiration_date,
                     String description, String photo_url, Feedback... params){

        this.insertion_id = insertion_id;
        this.name = name;
        this.price = price;
        this.city = city;
        this.address = address;
        this.insertionist_id = insertionist_id;
        this.insertionist_name = insertionist_name;
        this.insertionist_rate = insertionist_rate;
        this.insertion_date = insertion_date;
        this.expiration_date = expiration_date;
        this.description = description;
        this.photo_url = photo_url;

        feedbacks = new ArrayList<>();
        for(int i = 0; i < params.length; i++){
            feedbacks.add(params[i]);
        }
    }

    public Insertion(){
        feedbacks = new ArrayList<>();
    }

    public void setFavorite(boolean isFavorite){this.isFavorite = isFavorite;}
    public void setInsertion_id(long insertion_id){ this.insertion_id = insertion_id; }
    public void setName(String name) { this.name = name;}
    public void setEvaluated(boolean evaluated){ this.evaluated = evaluated;}
    public void setPrice(float price) { this.price = price;}
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }
    public void setShopName(String shopName){ this.shop_name = shopName; }
    public void setInsertionist_id(int insertionist_id) { this.insertionist_id = insertionist_id; }
    public void setInsertionist_name(String insertionist_name) { this.insertionist_name = insertionist_name; }
    public void setInsertionist_rate(float insertionist_rate) { this.insertionist_rate = insertionist_rate; }
    public void setInsertion_date(String insertion_date) { this.insertion_date = insertion_date; }
    public void setExpiration_date(String expiration_date) { this.expiration_date = expiration_date; }
    public void setDescription (String description) { this.description = description; }
    public void setPhoto_url (String photo_url) { this.photo_url = photo_url; }

    public boolean isFavorite(){ return isFavorite;}
    public long getInsertion_id() { return  insertion_id; }
    public String getName() { return  name; }
    public boolean isEvaluated(){return evaluated;}
    public float getPrice(){ return  price; }
    public String getCity(){ return city; }
    public String getAddress() { return address; }
    public String getShopName(){ return shop_name; }
    public int getInsertionist_id() { return insertionist_id; }
    public String getInsertionist_name() { return  insertionist_name; }
    public float getInsertionist_rate() { return  insertionist_rate; }
    public String getInsertion_date() { return  insertion_date; }
    public String getExpiration_date() { return expiration_date; }
    public String getDescription(){ return  description; }
    public String getPhoto_url(){ return photo_url; }
    public ArrayList<Feedback> getFeedbacks(){ return feedbacks; }

    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }
}
