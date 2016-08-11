package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.BaseClasses.DownloadType;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Interfaces.ListTab;
import com.example.cristian.everysale.Parsers.SearchResponseParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class asincDownloadInsertions  extends AsyncTask<Long, Void, SearchResponse>{

    private ListTab tab;
    private Activity activity;
    private Context context;

    private String addition="";
    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/";
    private String fileName;

    public asincDownloadInsertions(Activity activity, ListTab tab, DownloadType type){
        this.tab = tab;
        this.activity = activity;
        this.context = this.activity.getBaseContext();

        switch (type){
            case MyInsertions:
                URL += "getMyInsertions.php";
                fileName = "myInsertion.xml";
                break;
            case Recent:
                URL += "recentInsertion.php";
                fileName = "recentInsertion.xml";
                break;
            case Favorite:
                URL += "getFavorites.php";
                fileName = "favorites.xml";
                break;
        }
    }

    public asincDownloadInsertions(Activity activity, ListTab tab,
                                   String region, String province, String municipality){
        this.tab = tab;
        this.activity = activity;
        this.context = this.activity.getBaseContext();
        this.fileName = "nearby.xml";
        URL += "getNearby.php";
        addition += "&region=" + region + "&province=" + province +"&municipality=" + municipality;
    }

    public asincDownloadInsertions(Activity activity, ListTab tab, String searchKey){
        this.tab = tab;
        this.activity = activity;
        this.context = this.activity.getBaseContext();
        this.fileName = "search.xml";
        this.URL += "searchMobile.php";
        addition += "&searchKey=" + searchKey;
    }

    @Override
    protected SearchResponse doInBackground(Long... params) {
        long upperLimit = Long.MAX_VALUE;
        if(params.length >0){
            upperLimit = params[0];
        }
        SharedPreferences savedValues = this.context.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        long userId = savedValues.getLong("userId", 1);

        try{
            String finalUrl = this.URL +"?userId=" +
                    String.valueOf(userId) + "&upperLimit=" + String.valueOf(upperLimit) + addition;

            //Log.e("Debug", finalUrl);
            java.net.URL url = new URL(this.URL +"?userId=" +
                    String.valueOf(userId) + "&upperLimit=" + String.valueOf(upperLimit) + addition);

            InputStream inputStream = url.openStream();

            FileOutputStream outputStream = this.context.openFileOutput(fileName, Context.MODE_PRIVATE);

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

            FileInputStream fileInputStream = this.context.openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);
            return responseParser.getSearchResponse();
        }
        catch(Exception e){

            Log.e("Debug", "Response: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(SearchResponse result){
        this.tab.SetResponse(result);
    }
}
