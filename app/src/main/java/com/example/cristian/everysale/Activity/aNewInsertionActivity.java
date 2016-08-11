package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadMunicipalities;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadProvinces;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadRegions;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincCreateInsertion;
import com.example.cristian.everysale.BaseClasses.Province;
import com.example.cristian.everysale.BaseClasses.Region;
import com.example.cristian.everysale.BaseClasses.imagePicker.ImagePickerActivity;
import com.example.cristian.everysale.BaseClasses.imagePicker.imageUtility;
import com.example.cristian.everysale.Interfaces.SpinnerSetup;
import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class aNewInsertionActivity extends navigationDrawerActivity implements OnClickListener, OnItemSelectedListener, SpinnerSetup {

    private EditText titleEditText;
    private EditText priceEditText;
    private EditText shopEditText;
    private Spinner regionSpinner;
    private Spinner provinceSpinner;
    private Spinner municipalitySpinner;
    private EditText addressEditText;
    private DatePicker expirationDatePicker;
    private EditText descriptionEditText;
    private Button imageButton;
    private Button cancelButton;
    private Button submitButton;
    private ImageView insertionImageView;

    private String imgPath;
    //gestore immagini
    private imageUtility bitmapConverter=null;
    private ArrayList<Integer> regionsCode = new ArrayList<>();
    private ArrayList<Integer> provincesCode = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.new_insertion_layout , null, false);
        drawerLayout.addView(contentView, 0);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        shopEditText = (EditText) findViewById(R.id.shopEditText);
        regionSpinner = (Spinner) findViewById(R.id.insertionRegionSpinner);
        provinceSpinner = (Spinner) findViewById(R.id.insertionProvinceSpinner);
        municipalitySpinner = (Spinner) findViewById(R.id.insertionMunicipalitySpinner);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        expirationDatePicker = (DatePicker) findViewById(R.id.expirationDatePicker);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        insertionImageView = (ImageView) findViewById(R.id.itemImageView);
        imageButton = (Button) findViewById(R.id.newImageButton);
        cancelButton = (Button) findViewById(R.id.newCancelButton);
        submitButton = (Button) findViewById(R.id.newSubmitButton);

        //expirationDateCheckBox.setOnc
        imageButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        //inizializzo gestore immagini
        bitmapConverter=new imageUtility();
        savedValues.edit().putString("imgPath", null).commit();

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        expirationDatePicker.setMinDate(cal.getTimeInMillis());
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        expirationDatePicker.setMaxDate(cal.getTimeInMillis());
    }

    public void onClick(View v) {

        switch (v.getId()){
            case R.id.newCancelButton:
                Intent backHome = new Intent(this, Main2Activity.class);
                startActivity(backHome);
                this.finish();
                break;

            case R.id.newSubmitButton:
                if(CheckForm()) {
                    SendInsertion();
                }
                break;

            case R.id.newImageButton:
                Intent intent = new Intent(this, ImagePickerActivity.class);
                startActivity(intent);
                break;

            default:
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        new asincDownloadRegions(this.getApplicationContext(), this).execute();
        imgPath=savedValues.getString("imgPath", null);
        insertionImageView.setImageBitmap(bitmapConverter.getBitmap(imgPath));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(String.valueOf(parent).contains("RegionSpinner")){
            Log.d("EverySale", "Regione selezionata");
            new asincDownloadProvinces(this.getApplicationContext(), this, regionsCode.get(position)).execute();
        }
        else if(String.valueOf(parent).contains("ProvinceSpinner")){
            Log.d("EverySale", "Provincia selezionata");
            new asincDownloadMunicipalities(this.getApplicationContext(), this, provincesCode.get(position)).execute();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setupRegions(ArrayList<Region> result){
        Log.d("EverySale", "Inserimento regioni in corso...");
        Iterator<Region> reg = result.iterator();
        ArrayList<String> regionsName = new ArrayList<>();
        regionsCode.clear();
        while(reg.hasNext()){
            Region temp = reg.next();
            regionsName.add(temp.getRegionName());
            regionsCode.add(temp.getRegionCode());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, regionsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        regionSpinner.setOnItemSelectedListener(this);
        Log.d("EverySale", "Regioni inserite");
    }

    public void setupProvinces(ArrayList<Province> result){
        Log.d("EverySale", "Inserimento province in corso...");
        Iterator<Province> prov = result.iterator();
        ArrayList<String> provincesName = new ArrayList<>();
        provincesCode.clear();
        while(prov.hasNext()){
            Province temp = prov.next();
            provincesName.add(temp.getProvinceName());
            provincesCode.add(temp.getProvinceCode());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, provincesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter);
        provinceSpinner.setOnItemSelectedListener(this);
        Log.d("EverySale", "Province inserite");
    }

    public void setupMunicipalities(ArrayList<String> result){
        Log.d("EverySale", "Inserimento comuni in corso...");
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, result);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitySpinner.setAdapter(adapter);
        Log.d("EverySale", "Comuni inseriti");
    }

    public void OnServerResponse(String response){
        if(response.contains("success")){
            Intent intent = new Intent(this, MyInsertionsActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private void SendInsertion(){

        String name = titleEditText.getText().toString();
        String region = regionSpinner.getSelectedItem().toString();
        String province = provinceSpinner.getSelectedItem().toString();
        String municipality = municipalitySpinner.getSelectedItem().toString();
        String price = priceEditText.getText().toString();
        String shopName = shopEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String address = addressEditText.getText().toString();

        if(expirationDatePicker == null){
            Log.e("Debug", "LA BORRA!!!");
        }
        int day = expirationDatePicker.getDayOfMonth();
        int month = expirationDatePicker.getMonth();
        int year = expirationDatePicker.getYear();

        String expirationDate = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(day);
        new asincCreateInsertion(this).execute(name, region, province, municipality, address, shopName, expirationDate,
                price, description, this.imgPath);
    }

    private boolean CheckForm(){
        if (titleEditText.getText().toString().equals("")){
            Toast.makeText(this, "Inserisci un titolo", Toast.LENGTH_SHORT).show();
            titleEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(titleEditText, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }
        if (priceEditText.getText().toString().equals("")){
            Toast.makeText(this, "Inserisci un titolo", Toast.LENGTH_SHORT).show();
            priceEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(priceEditText, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }
        if (shopEditText.getText().toString().equals("")){
            Toast.makeText(this, "Inserisci un titolo", Toast.LENGTH_SHORT).show();
            shopEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(shopEditText, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }
        if (descriptionEditText.getText().toString().equals("")){
            Toast.makeText(this, "Inserisci un titolo", Toast.LENGTH_SHORT).show();
            descriptionEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(descriptionEditText, InputMethodManager.SHOW_IMPLICIT);
            return false;
        }
        return true;
    }
}
