package com.example.cristian.everysale;

import java.util.ArrayList;

public class Insertion {

    private int insertion_id;
    private String name;
    private float price;
    private String city;
    private String address;
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

    public void setInsertion_id(int insertion_id){ this.insertion_id = insertion_id; }
    public void setName(String name) { this.name = name;}
    public void setPrice(float price) { this.price = price;}
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }
    public void setInsertionist_id(int insertionist_id) { this.insertionist_id = insertionist_id; }
    public void setInsertionist_name(String insertionist_name) { this.insertionist_name = insertionist_name; }
    public void setInsertionist_rate(float insertionist_rate) { this.insertionist_rate = insertionist_rate; }
    public void setInsertion_date(String insertion_date) { this.insertion_date = insertion_date; }
    public void setExpiration_date(String expiration_date) { this.expiration_date = expiration_date; }
    public void setDescription (String description) { this.description = description; }
    public void setPhoto_url (String photo_url) { this.photo_url = photo_url; }

    public void addFeedback(Feedback feedback){
        feedbacks.add(feedback);
    }
}
