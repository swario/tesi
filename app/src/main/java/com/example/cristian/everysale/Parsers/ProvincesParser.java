package com.example.cristian.everysale.Parsers;

import com.example.cristian.everysale.BaseClasses.Province;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Cristian on 07/08/2016.
 */
public class ProvincesParser extends DefaultHandler {

    private boolean isProvince = false;
    private boolean isProvinceCode = false;
    private boolean isProvinceName = false;

    private Province province;
    private ArrayList<Province> provinces;

    public ArrayList<Province> getProvinces(){
        return provinces;
    }

    public void startDocument(){
        province = new Province();
        provinces = new ArrayList<>();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("province")){
            isProvince = true;
            return;
        }
        if(qName.equals("code")){
            isProvinceCode = true;
            return;
        }
        if(qName.equals("name")){
            isProvinceName = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName){

        if(qName.equals("province")){
            provinces.add(province);
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ) {

        String s = new String(ch, start, lenght);
        if (isProvince) {
            isProvince = false;
            province = new Province();
        }
        if (isProvinceCode) {
            isProvinceCode = false;
            province.setProvinceCode(Integer.parseInt(s));
        }
        if (isProvinceName) {
            isProvinceName = false;
            province.setProvinceName(s);
        }
    }
}
