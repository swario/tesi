package com.example.cristian.everysale.BaseClasses;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public class Provincia {

    private int code;
    private String name;
    private ArrayList<String> comuni;

    public Provincia(){
        comuni = new ArrayList<>();
    }

    public void addComune(String comune){this.comuni.add(comune);}
    public void setCode(int code){this.code=code;}
    public void setName(String name){this.name=name;}

    public ArrayList<String> getComuni(){ return this.comuni;}
    public int getCode(){return this.code;}
    public String getName(){ return this.name;}


}
