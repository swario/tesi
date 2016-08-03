package com.example.cristian.everysale.BaseClasses;

/**
 * Created by Giorgiboy on 01/08/2016.
 */
public class InsertionPreview {

    private long insertion_id;
    private String name;
    private String city;
    private String photo;
    private float price;
    private float rate;

    public void setInsertion_id(long id){ this.insertion_id = id; }
    public void setName(String name){ this.name = name; }
    public void setCity(String city){ this.city = city; }
    public void setPhoto(String photo){ this.photo = photo; }
    public void setPrice(float price){ this.price = price; }
    public void setRate(float rate){ this.rate = rate; }

    public long getInsertionId(){ return this.insertion_id;}
    public String getName(){ return this.name;}
    public String getCity(){ return this.city;}
    public String getImage(){ return this.photo;}
    public float getPrice(){ return this.price;}
    public float getRate(){ return this.rate;}
}
