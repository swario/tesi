package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.BaseClasses.User;
import com.example.cristian.everysale.Interfaces.UserDownloader;
import com.example.cristian.everysale.Parsers.UserParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Giorgiboy on 06/08/2016.
 */
public class asincDownloadUser extends AsyncTask<Long, Void, User> {

    private String URL = "http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/";
    private User user;
    private String fileName = "user.xml";
    private Context context;
    private UserDownloader downloader;

    public asincDownloadUser(Context context, UserDownloader downloader, boolean isSelf){

        user = null;
        this.context = context;
        this.downloader = downloader;
        if(isSelf){
            URL +="getMyAccount.php";
        }
        else {
            URL +="getUser.php";
        }
    }

    @Override
    protected User doInBackground(Long... params) {
        SharedPreferences savedValues = this.context.getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        long userId = savedValues.getLong("userId", 1);

        try{
            URL url = new URL(this.URL + "?userId=" + String.valueOf(userId));

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

            UserParser userParser = new UserParser();
            reader.setContentHandler(userParser);

            FileInputStream fileInputStream = this.context.openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);
            return userParser.getUser();
        }
        catch(Exception e){

            Log.e("Debug", "Response: " + e.getMessage());
        }
        return null;
    }

    protected void onPostExecute(User result){

        downloader.setUser(result);
    }
}
