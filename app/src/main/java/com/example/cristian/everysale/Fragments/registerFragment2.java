package com.example.cristian.everysale.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;

import com.example.cristian.everysale.AsyncronousTasks.asincDownloadProvinces;
import com.example.cristian.everysale.AsyncronousTasks.asincDownloadRegions;
import com.example.cristian.everysale.BaseClasses.Province;
import com.example.cristian.everysale.BaseClasses.Region;
import com.example.cristian.everysale.Interfaces.SpinnerSetup;
import com.example.cristian.everysale.R;

import java.util.ArrayList;
import java.util.Iterator;

public class registerFragment2 extends Fragment implements OnClickListener, OnItemSelectedListener, SpinnerSetup {

    private SharedPreferences savedValues;

    private EditText nameEditText;
    private EditText surnameEditText;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private EditText mobileEditText;

    private ArrayList<Integer> regionsCode = new ArrayList<>();
    private ArrayList<Integer> provincesCode = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_2, container, false);

        nameEditText = (EditText) view.findViewById(R.id.nomeEditText);
        surnameEditText = (EditText) view.findViewById(R.id.cognomeEditText);
        regionSpinner = (Spinner) view.findViewById(R.id.regioneSpinner);
        citySpinner = (Spinner) view.findViewById(R.id.cittaSpinner);
        mobileEditText = (EditText) view.findViewById(R.id.cellulareEditText);

        regionSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);
        view.findViewById(R.id.forwardButton).setOnClickListener(this);
        view.findViewById(R.id.backButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forwardButton:
                getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment3(), "registerFragment3").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                break;

            case R.id.backButton:
                getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(), "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        //per prima cosa, setto l'adapter per lo spinner delle regioni
        new asincDownloadRegions(getContext(), this).execute();
        //setCitySpinner();
        //poi, prelevo i dati per riempire eventuali campi già compilati
        nameEditText.setText(savedValues.getString("name", ""));
        surnameEditText.setText(savedValues.getString("surname", ""));
        //regionSpinner.setSelection(savedValues.getInt("regionPosition", 0));

        mobileEditText.setText(savedValues.getString("mobilePhone", ""));

        String message = savedValues.getString("message", "");
        if(message.contains("email")) {
            nameEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(nameEditText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if(message.contains("username")) {
            surnameEditText.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(surnameEditText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
    }

    @Override
    public void onPause(){

        Editor editor = savedValues.edit();

        editor.putString("name", nameEditText.getText().toString());
        editor.putString("surname", surnameEditText.getText().toString());
        editor.putInt("regionPosition", regionSpinner.getSelectedItemPosition());
        editor.putInt("cityPosition", citySpinner.getSelectedItemPosition());
        editor.putString("mobilePhone", mobileEditText.getText().toString());

        editor.commit();

        super.onPause();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("EverySale", String.valueOf(view.getId() + " " + id + " " + R.id.regioneTextView + " " + parent.getId() + " " + parent));
        if(String.valueOf(parent).contains("regioneSpinner")){
            Log.d("EverySale", "Regione selezionata");
            new asincDownloadProvinces(getContext(), this, regionsCode.get(position)).execute();
        }
        else if(String.valueOf(parent).contains("cittaSpinner")){
            Log.d("EverySale", "Provincia selezionata");
            //new asincDownloadProvinces(getContext(), this, provincesCode.get(position)).execute();
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
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, regionsName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        regionSpinner.setOnItemSelectedListener(this);
        Log.d("EverySale", "Regioni inserite");
    }

    public void setupProvinces(ArrayList<Province> result){
        Log.d("EverySale", "Inserimento province in corso...");
        Iterator<Province> prov = result.iterator();
        ArrayList<String> provinces = new ArrayList<>();
        while(prov.hasNext()){
            provinces.add(prov.next().getProvinceName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(this);
        Log.d("EverySale", "Province inserite");
    }

    public void setupMunicipalities(){

    }

    /*private void setCitySpinner(){
        ArrayAdapter<CharSequence> adapter=null;
        int region=regionSpinner.getSelectedItemPosition();
        if(region==0) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region0_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==1) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region1_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==2) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region2_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==3) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region3_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==4) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region4_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==5) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region5_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==6) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region6_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==7) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region7_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==8) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region8_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==9) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region9_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==10) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region10_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==11) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region11_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==12) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region12_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==13) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region13_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==14) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region14_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==15) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region15_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==16) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region16_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==17) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region17_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==18) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region18_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==19) {
            adapter = ArrayAdapter.createFromResource(getContext(), R.array.fregister2_region19_spinner, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
    }*/
}
