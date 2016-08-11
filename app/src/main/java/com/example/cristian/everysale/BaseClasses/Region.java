package com.example.cristian.everysale.BaseClasses;

import java.util.ArrayList;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public class Region {

    private int regionCode;
    private String regionName;
    private ArrayList<Province> provinces;

    public Region(){
        this.provinces = new ArrayList<>();
    }

    public void addRegion(Province provincia){ this.provinces.add(provincia);}

    public ArrayList<Province> getProvinces(){ return this.provinces; }
    public String getRegionName(){ return this.regionName;}
    public int getRegionCode(){ return this.regionCode;}

    public void setRegionName(String Name){ this.regionName = Name;}
    public void setRegionCode(int code){ this.regionCode = code;}
}
