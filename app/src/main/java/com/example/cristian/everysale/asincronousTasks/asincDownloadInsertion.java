package com.example.cristian.everysale.asincronousTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.BaseClasses.Insertion;
import com.example.cristian.everysale.InsertionActivity;
import com.example.cristian.everysale.fragment.tabFavorite;
import com.example.cristian.everysale.fragment.tabRecentOffers;
import com.example.cristian.everysale.parsersXML.InsertionParser;
import com.example.cristian.everysale.parsersXML.SearchResponseParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Giorgiboy on 03/08/2016.
 */
public class asincDownloadInsertion extends AsyncTask<Long, Void, Insertion> {

    private InsertionActivity activity;
    private tabFavorite favorite;
    private String fileName = "insertion.xml";
    private Context context;

    public asincDownloadInsertion(InsertionActivity activity){
        this.activity = activity;
        this.favorite = null;
        this.context = activity.getBaseContext();
    }

    public asincDownloadInsertion(tabFavorite tabFavorite){
        this.favorite = tabFavorite;
        this.activity = null;
        this.context = tabFavorite.getContext();
    }


    @Override
    protected Insertion doInBackground(Long... params) {

        long insertionId = params[0];

        try{

            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/getInsertion.php?insertionId="
            + String.valueOf(insertionId));

            InputStream inputStream = url.openStream();

            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

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

            InsertionParser insertionParser = new InsertionParser();
            reader.setContentHandler(insertionParser);

            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);

            return insertionParser.getInsertion();

        }
        catch (Exception e){
            Log.e("Debug", e.getMessage());
        }

        return null;
    }

     @Override
     protected void onPostExecute(Insertion result){

         if(favorite== null){
             activity.setUpInsertion(result);
         }
     }
}
