package com.example.cristian.everysale.BaseClasses;

/**
 * Created by Giorgiboy on 01/08/2016.
 */
public class User {

    private long user_id;
    private String email;
    private String userName;
    private String name;
    private String surname;
    private float rating;
    private String photo;
    private String region;
    private String province;
    private String municipality;
    private String mobile;
    private boolean emailSend;
    private float ratingThreshold;
    private boolean dataAllow;

    public User(){

        name = null;
        surname = null;
        mobile = null;
    }

    public void setUser_id(long id){this.user_id=id;}
    public void setEmail(String email) {this.email = email;}
    public void setUserName(String username) {this.userName=username;}
    public void setName(String name){this.name=name;}
    public void setSurname(String surname){this.surname=surname;}
    public void setRating(float rating){this.rating=rating;}
    public void setPhoto(String photo){this.photo=photo;}
    public void setRegion(String region){this.region=region;}
    public void setProvince(String province) {this.province = province;}
    public void setMunicipality(String municipality){this.municipality = municipality;}
    public void setMobile(String mobile){this.mobile=mobile;}
    public void setEmailSend(boolean emailSend){this.emailSend=emailSend;}
    public void setRatingThreshold(float threshold){this.ratingThreshold=threshold;}
    public void setDataAllow(boolean dataAllow){this.dataAllow=dataAllow;}

    public long getUser_id(){return user_id;}
    public String getEmail() {return email;}
    public String getUserName(){return userName;}
    public String getName(){return name;}
    public String getSurname(){return surname;}
    public float getRating(){return rating;}
    public String getPhoto(){return photo;}
    public String getMunicipality(){return municipality;}
    public String getProvince() {return province;}
    public String getRegion(){return region;}
    public String getMobile(){return mobile;}
    public boolean getEmailSend(){return emailSend;}
    public float getRatingThreshold(){return ratingThreshold;}
    public boolean getDataAllow(){return dataAllow;}
}
