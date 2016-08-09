package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.Activity.MyInsertionsActivity;
import com.example.cristian.everysale.Activity.MyInsertionsActivity;
import com.example.cristian.everysale.BaseClasses.SearchResponse;
import com.example.cristian.everysale.Parsers.SearchResponseParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class asincGetMyInsertions extends AsyncTask<Long, Void, SearchResponse> {

    private MyInsertionsActivity activity;
    private final String fileName = "myInsertionsXML.xml";
    private Context context;

    public asincGetMyInsertions(MyInsertionsActivity activity){
        this.activity = activity;
        this.context = this.activity.getBaseContext();
    }

    @Override
    protected SearchResponse doInBackground(Long... params) {
        Long upperLimit = Long.MAX_VALUE;
        if(params.length > 0){
            upperLimit = params[0];
        }
        SharedPreferences savedValues = this.context.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        long userId = savedValues.getLong("userId", 1);

        try{
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/getFavorites.php?userId=" +
                    String.valueOf(userId) + "&upperLimit=" + String.valueOf(upperLimit));

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

        this.activity.setSearchResponse(result);
    }
}
