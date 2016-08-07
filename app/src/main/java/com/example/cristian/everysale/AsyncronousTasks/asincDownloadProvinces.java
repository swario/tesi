package com.example.cristian.everysale.AsyncronousTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.BaseClasses.Province;
import com.example.cristian.everysale.BaseClasses.Region;
import com.example.cristian.everysale.Interfaces.SpinnerSetup;
import com.example.cristian.everysale.Parsers.ProvincesParser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Cristian on 07/08/2016.
 */
public class asincDownloadProvinces extends AsyncTask<Void, Void, ArrayList<Province>> {
    private String fileName = "provincies.xml";
    private Context context;
    private SpinnerSetup spinnerSetup;
    private int regionCode;

    public asincDownloadProvinces(Context cont, SpinnerSetup spinner, int regCode){
        context = cont;
        spinnerSetup = spinner;
        regionCode = regCode;
    }

    protected ArrayList<Province> doInBackground(Void... params) {
        Log.e("Debug", "Lanciato");

        try {

            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/getRegions.php?regioCode=" + regionCode);

            InputStream inputStream = url.openStream();

            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            while (bytesRead != -1) {

                outputStream.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }
            Log.e("Debug", "File Letto");
            outputStream.close();
            inputStream.close();

            //Inizio parsing file XML
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            ProvincesParser provincesParser = new ProvincesParser();
            reader.setContentHandler(provincesParser);

            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);
            return provincesParser.getProvinces();
        }
        catch (Exception e){
            Log.e("Debug", "Eccezione: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result){
        if(result!= null){
            Log.e("Debug", String.valueOf(result.size()));
            spinnerSetup.setupProvinces(result);
        }
    }
}
