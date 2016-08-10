package com.example.cristian.everysale.Parsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Cristian on 07/08/2016.
 */
public class MunicipalitiesParser extends DefaultHandler {

    private boolean isMunicipality = false;
    private boolean isMunicipalityName = false;

    private String municipality;
    private ArrayList<String> municipalities;

    public ArrayList<String> getMunicipalities(){
        return municipalities;
    }

    public void startDocument(){
        municipality = new String();
        municipalities = new ArrayList<>();
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes att){

        if(qName.equals("municipality")){
            isMunicipality = true;
            return;
        }
        if(qName.equals("name")){
            isMunicipalityName = true;
            return;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName){

        if(qName.equals("municipality")){
            municipalities.add(municipality);
            return;
        }
    }

    public void characters(char ch[], int start, int lenght ) {

        String s = new String(ch, start, lenght);
        if (isMunicipality) {
            isMunicipality = false;
            municipality = new String();
        }
        if (isMunicipalityName) {
            isMunicipalityName = false;
            municipality = s;
        }
    }
}
