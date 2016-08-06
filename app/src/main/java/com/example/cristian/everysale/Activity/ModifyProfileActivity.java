package com.example.cristian.everysale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.cristian.everysale.R;

public class ModifyProfileActivity extends AppCompatActivity implements OnClickListener, OnItemSelectedListener {

    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText nameEditText;
    private EditText surnameEditText;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private EditText mobileEditText;
    private CheckBox dataAllowCheckbox;
    private ImageView insertionImageView;
    private Button imageButton;
    private Button cancelButton;
    private Button updateButton;

    private final String imageAddress = "http://webdev.dibris.unige.it/~S3928202/Progetto/profilePics/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_profile_layout);

        emailEditText = (EditText) findViewById(R.id.modifyEmailEditText);
        usernameEditText = (EditText) findViewById(R.id.modifyUsernameEditText);
        passwordEditText = (EditText) findViewById(R.id.modifyPasswordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.modifyConfirmPasswordEditText);
        nameEditText = (EditText) findViewById(R.id.modifyNameEditText);
        surnameEditText = (EditText) findViewById(R.id.modifySurnameEditText);
        citySpinner = (Spinner) findViewById(R.id.modifyCitySpinner);
        regionSpinner = (Spinner) findViewById(R.id.modifyRegionSpinner);
        mobileEditText = (EditText) findViewById(R.id.modifyMobileEditText);
        dataAllowCheckbox = (CheckBox) findViewById(R.id.modifyDataAllowCheckBox);
        insertionImageView = (ImageView) findViewById(R.id.modifyInsertionImageView);
        imageButton = (Button) findViewById(R.id.imageButton);
        cancelButton = (Button) findViewById(R.id.modifyCancelButton);
        updateButton = (Button) findViewById(R.id.modifyUpdateButton);

        //setto listener
        regionSpinner.setOnItemSelectedListener(this);
        citySpinner.setOnItemSelectedListener(this);
        findViewById(R.id.modifyImageButton).setOnClickListener(this);
        findViewById(R.id.modifyCancelButton).setOnClickListener(this);
        findViewById(R.id.modifyUpdateButton).setOnClickListener(this);

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
                //getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(), "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                break;
            case R.id.modifyImageButton:

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setCitySpinner();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCitySpinner(){
        ArrayAdapter<CharSequence> adapter=null;
        int region=regionSpinner.getSelectedItemPosition();
        if(region==0) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region0_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==1) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region1_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==2) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region2_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==3) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region3_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==4) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region4_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==5) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region5_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==6) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region6_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==7) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region7_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==8) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region8_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==9) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region9_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==10) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region10_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==11) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region11_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==12) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region12_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==13) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region13_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==14) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region14_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==15) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region15_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==16) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region16_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==17) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region17_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==18) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region18_spinner, android.R.layout.simple_spinner_item);
        }
        else if(region==19) {
            adapter = ArrayAdapter.createFromResource(this, R.array.fregister2_region19_spinner, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);
    }
}
