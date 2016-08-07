package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public class Region {

    private int regionCode;
    private String regionName;
    private ArrayList<Provincia> provinces;

    public Region(){
        this.provinces = new ArrayList<>();
    }

    public void addRegion(Provincia provincia){ this.provinces.add(provincia);}

    public ArrayList<Provincia> getProvinces(){ return this.provinces; }
    public String getRegionName(){ return this.regionName;}
    public int getRegionCode(){ return this.regionCode;}

    public void setRegionName(String Name){ this.regionName = Name;}
    public void setRegionCode(int code){ this.regionCode = code;}
}
