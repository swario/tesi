package com.example.cristian.everysale.Parsers;

import com.example.cristian.everysale.BaseClasses.Region;

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

    public RegionsParser(){
        region = new Region();
        regions = new ArrayList<>();
    }


}
