package com.example.cristian.everysale;

/**
 * Created by Giorgiboy on 29/07/2016.
 */
public class Feedback {

    private String description;
    private int userId;
    private String userName;

    public Feedback(String description, int userId, String userName){
        this.description = description;
        this.userId = userId;
        this.userName = userName;
    }

    public String getDescription(){ return description; }
    public String getUserName(){ return userName; }
    public int getUserId(){ return  userId; }

}
