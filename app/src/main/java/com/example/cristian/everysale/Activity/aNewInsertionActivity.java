package com.example.cristian.everysale.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cristian.everysale.BaseClasses.imagePicker.ImagePickerActivity;
import com.example.cristian.everysale.BaseClasses.imagePicker.imageUtility;
import com.example.cristian.everysale.R;

public class aNewInsertionActivity extends navigationDrawerActivity implements OnClickListener,OnItemSelectedListener {


    private Button imageButton;
    private Button cancelButton;
    private Button submitButton;
    private ImageView insertionImageView;

    private String imgPath;

    //gestore immagini
    private imageUtility bitmapConverter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.new_insertion_layout , null, false);
        drawerLayout.addView(contentView, 0);

        insertionImageView = (ImageView) findViewById(R.id.itemImageView);
        imageButton = (Button) findViewById(R.id.newImageButton);
        cancelButton = (Button) findViewById(R.id.newCancelButton);
        submitButton = (Button) findViewById(R.id.newSubmitButton);

        imageButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        //inizializzo gestore immagini
        bitmapConverter=new imageUtility();
        savedValues.edit().putString("imgPath", null).commit();

    }

    public void onClick(View v) {

        switch (v.getId()){
            case R.id.newCancelButton:
                Intent backHome = new Intent(this, Main2Activity.class);
                startActivity(backHome);
                this.finish();
                break;

            case R.id.newSubmitButton:
                //getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(), "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
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
        imgPath=savedValues.getString("imgPath", null);
        insertionImageView.setImageBitmap(bitmapConverter.getBitmap(imgPath));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //setCitySpinner();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
