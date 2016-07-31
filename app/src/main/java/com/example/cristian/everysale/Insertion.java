package com.example.cristian.everysale;

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
    private Feedback[] feedbacks;

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

        for(int i = 0; i < params.length; i++){

            feedbacks[i] = params[i];
        }
    }
}
