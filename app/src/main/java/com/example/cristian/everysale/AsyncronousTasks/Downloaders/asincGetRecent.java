package com.example.cristian.everysale.AsyncronousTasks.Downloaders;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Fragments.HomeActivity.tabRecentOffers;
import com.example.cristian.everysale.Parsers.SearchResponseParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class asincGetRecent extends AsyncTask<Long, Void, Void> {

    private tabRecentOffers tabRecentOffers;
    private SearchResponse searchResponse;
    private final String fileName = "recentXML.xml";

    public asincGetRecent(tabRecentOffers tabRecentOffers){
        this.tabRecentOffers = tabRecentOffers;
        searchResponse = null;
    }


    @Override
    protected Void doInBackground(Long... params) {

        Long upperLimit = Long.MAX_VALUE;
        if(params.length > 0){
            upperLimit = params[0];
        }
        SharedPreferences savedValues = tabRecentOffers.getContext().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        long userId = savedValues.getLong("userId", 0);

        try{
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/recentInsertion.php?userId=" +
            String.valueOf(userId) + "&upperLimit=" + String.valueOf(upperLimit));

               InputStream inputStream = url.openStream();

            FileOutputStream outputStream = tabRecentOffers.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            while(bytesRead != -1){

                outputStream.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }
            outputStream.close();
            inputStream.close();

            //Inizio parsing file XML
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            SearchResponseParser responseParser = new SearchResponseParser();
            reader.setContentHandler(responseParser);

            FileInputStream fileInputStream = tabRecentOffers.getContext().openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);
            searchResponse = responseParser.getSearchResponse();
        }
        catch(Exception e){
            Log.e("Debug", "Response: " + e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(Void result){

        //Log.e("Debug", String.valueOf(this.searchResponse.getInsertionCount()));
        tabRecentOffers.setSearchResponse(searchResponse);
    }
}