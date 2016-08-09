package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadMunicipalities;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadProvinces;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadRegions;
import com.example.cristian.everysale.AsyncronousTasks.Downloaders.asincDownloadUser;
import com.example.cristian.everysale.AsyncronousTasks.Senders.asincProfileUpdate;
import com.example.cristian.everysale.BaseClasses.Province;
import com.example.cristian.everysale.BaseClasses.Region;
import com.example.cristian.everysale.BaseClasses.User;
import com.example.cristian.everysale.BaseClasses.imagePicker.ImagePickerActivity;
import com.example.cristian.everysale.BaseClasses.imagePicker.imageUtility;
import com.example.cristian.everysale.Interfaces.SpinnerSetup;
import com.example.cristian.everysale.Interfaces.UserDownloader;
import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.Iterator;

public class ModifyProfileActivity extends navigationDrawerActivity implements OnClickListener, OnItemSelectedListener, SpinnerSetup, UserDownloader {

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText nameEditText;
    private EditText surnameEditText;
    private Spinner regionSpinner;
    private Spinner provinceSpinner;
    private Spinner municipalitySpinner;
    private EditText mobileEditText;
    private CheckBox dataAllowCheckbox;
    private ImageView profileImageView;
    private Button imageButton;
    private Button cancelButton;
    private Button updateButton;

    private String imgPath=null;
    private imageUtility bitmapConverter=null;
    private ArrayList<Integer> regionsCode = new ArrayList<>();
    private ArrayList<Integer> provincesCode = new ArrayList<>();

    private SharedPreferences savedValues;
    private User user;

    private final String imageAddress = "http://webdev.dibris.unige.it/~S3928202/Progetto/profilePics/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        new asincDownloadUser(this, this, true).execute(savedValues.getLong("userId", 1));
        View contentView = inflater.inflate(R.layout.modify_profile_layout, null, false);
        drawerLayout.addView(contentView, 0);

        emailEditText = (EditText) findViewById(R.id.modifyEmailEditText);
        usernameEditText = (EditText) findViewById(R.id.modifyUsernameEditText);
        passwordEditText = (EditText) findViewById(R.id.modifyPasswordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.modifyConfirmPasswordEditText);
        nameEditText = (EditText) findViewById(R.id.modifyNameEditText);
        surnameEditText = (EditText) findViewById(R.id.modifySurnameEditText);
        regionSpinner = (Spinner) findViewById(R.id.modifyRegionSpinner);
        provinceSpinner = (Spinner) findViewById(R.id.modifyProvinceSpinner);
        municipalitySpinner = (Spinner) findViewById(R.id.modifyMunicipalitySpinner);
        mobileEditText = (EditText) findViewById(R.id.modifyMobileEditText);
        dataAllowCheckbox = (CheckBox) findViewById(R.id.modifyDataAllowCheckBox);
        profileImageView = (ImageView) findViewById(R.id.modifyInsertionImageView);
        imageButton = (Button) findViewById(R.id.modifyImageButton);
        cancelButton = (Button) findViewById(R.id.modifyCancelButton);
        updateButton = (Button) findViewById(R.id.modifyUpdateButton);

        //setto listener
        regionSpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.modifyImageButton).setOnClickListener(this);
        findViewById(R.id.modifyCancelButton).setOnClickListener(this);
        findViewById(R.id.modifyUpdateButton).setOnClickListener(this);

        //inizializzo gestore immagine
        bitmapConverter=new imageUtility();
        savedValues.edit().putString("imgPath", null).commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.modifyCancelButton:
                Intent backHome = new Intent(this, Main2Activity.class);
                startActivity(backHome);
                this.finish();
                break;

            case R.id.modifyUpdateButton:
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.toString();
                if(password.equals(confirmPassword)){
                    updateProfile();
                }else{
                    Toast.makeText(this, "Le due password non coincidono", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.modifyImageButton:
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
        imgPath=savedValues.getString("imgPath", null);
        profileImageView.setImageBitmap(bitmapConverter.getBitmap(imgPath));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("EverySale", (String.valueOf(parent)));
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
        int position = regionsName.indexOf(user.getRegion());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, regionsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        regionSpinner.setOnItemSelectedListener(this);
        regionSpinner.setSelection(position);
        Log.d("EverySale", "Regioni inserite");
        new asincDownloadProvinces(this.getApplicationContext(), this, regionsCode.get(position)).execute();
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
        int position = provincesName.indexOf(user.getProvince());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, provincesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter);
        provinceSpinner.setOnItemSelectedListener(this);
        provinceSpinner.setSelection(position);
        Log.d("EverySale", "Province inserite");
        new asincDownloadMunicipalities(this.getApplicationContext(), this, provincesCode.get(position)).execute();
    }

    public void setupMunicipalities(ArrayList<String> result){
        Log.d("EverySale", "Inserimento comuni in corso...");
        int position = result.indexOf(user.getMunicipality());
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, result);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        municipalitySpinner.setAdapter(adapter);
        municipalitySpinner.setSelection(position);
        Log.d("EverySale", "Comuni inseriti");
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        new asincDownloadRegions(this.getApplicationContext(), this).execute();
        SetUpLayout();
    }

    private void SetUpLayout(){

        emailEditText.setText(user.getEmail());
        usernameEditText.setText(user.getUserName());
        nameEditText.setText(user.getName());
        surnameEditText.setText(user.getSurname());
        if(user.getMobile().equals("no mobile") || user.getMobile() == null){
            mobileEditText.setText("Cellulare non disponibile");
        }
        else {
            mobileEditText.setText(user.getMobile());
        }
        dataAllowCheckbox.setChecked(user.getDataAllow());
    }

    private void updateProfile(){

        String email = emailEditText.getText().toString();
        String username = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String region = regionSpinner.getSelectedItem().toString();
        String province = provinceSpinner.getSelectedItem().toString();
        String municipality = municipalitySpinner.getSelectedItem().toString();
        String dataAllow="";

        if(dataAllowCheckbox.isChecked()){
            dataAllow += "1";
        }
        else{
            dataAllow += "0";
        }
        new asincProfileUpdate(this).execute(email, username, password, region, province, municipality, name, surname,
                mobile, dataAllow, imgPath);
    }
}
