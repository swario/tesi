package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

public class Province {

    private int code;
    private String name;
    private ArrayList<String> municipality;

    public Province(){
        municipality = new ArrayList<>();
    }

    public void addMunicipality(String municipality){this.municipality.add(municipality);}
    public void setProvinceCode(int code){this.code=code;}
    public void setProvinceName(String name){this.name=name;}

    public ArrayList<String> getMunicipality(){ return this.municipality;}
    public int getProvinceCode(){return this.code;}
    public String getProvinceName(){ return this.name;}


}
