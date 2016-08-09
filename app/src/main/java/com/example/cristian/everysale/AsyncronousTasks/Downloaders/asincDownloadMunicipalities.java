package com.example.cristian.everysale.AsyncronousTasks.Downloaders;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cristian.everysale.Interfaces.SpinnerSetup;
import com.example.cristian.everysale.Parsers.MunicipalitiesParser;
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
public class asincDownloadMunicipalities extends AsyncTask<Void, Void, ArrayList<String>> {

    private String fileName = "municipalities.xml";
    private Context context;
    private SpinnerSetup spinnerSetup;
    private int provinceCode;

    public asincDownloadMunicipalities(Context cont, SpinnerSetup spinner, int provCode){
        context = cont;
        spinnerSetup = spinner;
        provinceCode = provCode;
    }

    protected ArrayList<String> doInBackground(Void... params) {
        Log.e("Municipalities", "Lanciato");

        try {
            URL url = new URL("http://webdev.dibris.unige.it/~S3928202/Progetto/phpMobile/getMunicipalities.php?provinceCode=" + provinceCode);

            InputStream inputStream = url.openStream();

            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead);
                bytesRead = inputStream.read(buffer);
            }
            Log.e("Municipalities", "File Letto");
            outputStream.close();
            inputStream.close();

            //Inizio parsing file XML
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            MunicipalitiesParser municipalitiesParser = new MunicipalitiesParser();
            reader.setContentHandler(municipalitiesParser);

            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputSource inputSource = new InputSource(fileInputStream);
            reader.parse(inputSource);
            return municipalitiesParser.getMunicipalities();
        }
        catch (Exception e){
                Log.e("Municipalities", "Eccezione: " + e.getMessage());
                return null;
            }
        }

    @Override
    protected void onPostExecute(ArrayList<String> result){
        if(result!= null){
            Log.e("Municipalities", String.valueOf(result.size()));
            spinnerSetup.setupMunicipalities(result);
        }
    }
}
