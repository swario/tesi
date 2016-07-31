package com.example.cristian.everysale;

/**
 * Created by Giorgiboy on 29/07/2016.
 */
public class Feedback {

    private String feedbackText;
    private int userId;
    private String userName;

    public Feedback(String description, int userId, String userName){
        this.feedbackText = description;
        this.userId = userId;
        this.userName = userName;
    }

    public Feedback(){}

    public String getDescription(){ return feedbackText; }
    public String getUserName(){ return userName; }
    public int getUserId(){ return  userId; }

    public void setfeedbackText(String feedbackText){ this.feedbackText = feedbackText; }
    public void setUserId(int id){ this.userId = id;}
    public void setUserName(String name){ this.userName = name; }

}
