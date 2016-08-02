package com.example.cristian.everysale.BaseClasses;

/**
 * Created by Giorgiboy on 01/08/2016.
 */
public class User {

    private long user_id;
    private String userName;
    private String name;
    private String surname;
    private float rating;
    private String photo;
    private String city;
    private String region;
    private String mobile;

    public User(){

        name = "not available";
        surname = "not available";
        mobile = "not available";
    }

    public void setUser_id(long id){this.user_id=id;}
    public void setUserName(String username) {this.userName=username;}
    public void setName(String name){this.name=name;}
    public void setSurname(String surname){this.surname=surname;}
    public void setRating(float rating){this.rating=rating;}
    public void setPhoto(String photo){this.photo=photo;}
    public void setCity(String city){this.city=city;}
    public void setRegion(String region){this.region=region;}
    public void setMobile(String mobile){this.mobile=mobile;}

    public long getUser_id(){return user_id;}
    public String getUserName(){return userName;}
    public String getName(){return name;}
    public String getSurname(){return surname;}
    public float getRating(){return rating;}
    public String getPhoto(){return photo;}
    public String getCity(){return  city;}
    public String getRegion(){return region;}
    public String getMobile(){return mobile;}

}
