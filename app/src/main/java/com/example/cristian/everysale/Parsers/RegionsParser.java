package com.example.cristian.everysale.Parsers;

import com.example.cristian.everysale.BaseClasses.Region;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Cristian on 07/08/2016.
 */
public class RegionsParser extends DefaultHandler {

    private boolean isRegion = false;
    private boolean isRegionCode = false;
    private boolean isRegionName = false;

    private Region region;
    private ArrayList<Region> regions;

    public ArrayList<Region> getRegions(){
        return regions;
    }
    public void startDocument(){
        region = new Region();
        regions = new ArrayList<>();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("region")){
            isRegion = true;
            return;
        }
        if(qName.equals("code")){
            isRegionCode = true;
            return;
        }
        if(qName.equals("name")){
            isRegionName = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName){

        if(qName.equals("region")){
            regions.add(region);
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ) {

        String s = new String(ch, start, lenght);
        if (isRegion) {
            isRegion = false;
            region = new Region();
        }
        if (isRegionCode) {
            isRegionCode = false;
            region.setRegionCode(Integer.parseInt(s));
        }
        if (isRegionName) {
            isRegionName = false;
            region.setRegionName(s);
        }
    }
}
