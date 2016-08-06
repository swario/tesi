package com.example.cristian.everysale.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cristian.everysale.BaseClasses.imagePicker.Utility;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.asincRegister;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.cristian.everysale.BaseClasses.getFilePath.getFilePath;

public class registerFragment3 extends Fragment implements OnClickListener {

    /*//image picker
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video
    */

    private Button imageButton;
    private CheckBox dataAllowCheckbox;
    private SharedPreferences savedValues;
    private ImageView ivImage=null;
    private String userChoosenTask;

    //image picker
    private String imgPath=null;
    private Bitmap bitimg;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    //image picker

    @Override
    public void onCreate(Bundle savedInstanceState) {
        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_3, container, false);

        savedValues = getActivity().getSharedPreferences("SavedValues", getActivity().MODE_PRIVATE);
        ivImage = (ImageView) view.findViewById(R.id.imageView1);
        imageButton = (Button) view.findViewById(R.id.imageButton);
        dataAllowCheckbox = (CheckBox) view.findViewById(R.id.dataAllowCheckBox);

        imageButton.setOnClickListener(this);
        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.registerSubmitButton).setOnClickListener(this);

        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        if(savedValues.getInt("dataAllowCheckBox",0)==0){
            dataAllowCheckbox.setChecked(false);
        }else if (savedValues.getInt("dataAllowCheckBox", 0) == 1){
            dataAllowCheckbox.setChecked(true);
        }

        if(imgPath==null) imgPath=savedValues.getString("imgPath",null);

        File imgFile=null;

        if(imgPath!=null)  imgFile = new  File(imgPath);

        if(imgFile!=null && imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ivImage.setImageBitmap(myBitmap);

        }
        //Toast.makeText(getContext(),"onresume  "+ savedValues.getString("imgPath", "") , Toast.LENGTH_LONG).show();
        //a noi piace
    }

    @Override
    public void onPause(){

        SharedPreferences.Editor editor = savedValues.edit();
        if(dataAllowCheckbox.isChecked()){
            editor.putInt("dataAllowCheckBox",1);
        }else {
            editor.putInt("dataAllowCheckBox",0);
        }
        editor.putString("imgPath", imgPath);
        editor.commit();
        //Toast.makeText(getContext(),"onpause saved  "+ savedValues.getString("imgPath", "") , Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageButton:
                // capture picture
                selectImage();

                break;

            case R.id.backButton:
                getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment2(), "registerFragment2").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                break;

            case R.id.registerSubmitButton:

                String email = savedValues.getString("email", "");
                String username = savedValues.getString("registerUsername", "");
                String password = savedValues.getString("registerPassword", "");
                String confirmPassword = savedValues.getString("confirmPassword", "");
                if(!password.equals(confirmPassword) ){
                    Toast.makeText(getContext(), "Password: " + password + " Confirm: " + confirmPassword, Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new registerFragment1()).addToBackStack(null).commit();
                    return;
                }

                int regionPosition = savedValues.getInt("regionPosition",0);

                String name = savedValues.getString("name", "");
                String surname = savedValues.getString("surname", "");
                String region = getResources().getStringArray(R.array.fregister2_regions_spinner)
                        [regionPosition];
                String city = getResources().getStringArray(getCitySpinner(regionPosition))
                        [savedValues.getInt("registerCityPosition",0)];
                String mobilePhone = savedValues.getString("registerMobilePhone", "");
                String dataAllow = null;
                if(dataAllowCheckbox.isChecked()){
                    dataAllow += "1";
                }
                else{
                    dataAllow += "0";
                }

                if(email.equals("")){//da aggiungere il controllo dell'email
                    Toast.makeText(getContext(), "Email non valida",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = savedValues.edit();
                    editor.putString("message", "wrong_email").commit();
                    getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(),
                            "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    return;
                }
                if(password.equals("")){//da aggiungere il controllo dell'email
                    Toast.makeText(getContext(), "Password non valida",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = savedValues.edit();
                    editor.putString("message", "wrong_password").commit();
                    getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(),
                            "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    return;
                }
                if(username.equals("")){
                    Toast.makeText(getContext(), "Username non valido",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = savedValues.edit();
                    editor.putString("message", "wrong_username").commit();
                    getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment1(),
                            "registerFragment1").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    return;
                }
                if(name.equals("")){
                    Toast.makeText(getContext(), "Nome non valido",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = savedValues.edit();
                    editor.putString("message", "wrong_name").commit();
                    getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment2(),
                            "registerFragment2").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    return;
                }
                if(surname.equals("")){
                    Toast.makeText(getContext(), "Cognome non valido",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = savedValues.edit();
                    editor.putString("message", "wrong_surname").commit();
                    getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment2(),
                            "registerFragment2").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    return;
                }

                new asincRegister(getContext(), getActivity()).execute(email, username, password, name, surname, region, city, mobilePhone, dataAllow);
                break;
        }
    }

    //image picker2

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Scatta foto"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Scegli dalla Galleria"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Scatta foto", "Scegli dalla Galleria",
                "Annulla" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Aggiungi foto!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getActivity());

                if (items[item].equals("Scatta foto")) {
                    userChoosenTask = "Scatta foto";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Scegli dalla Galleria")) {
                    userChoosenTask = "Scegli dalla Galleria";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Seleziona File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DCIM+"/",
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
        bitimg=thumbnail;
        imgPath = destination.getAbsolutePath();
        //Toast.makeText(getContext(), "oncaptureimgres  " +imgPath , Toast.LENGTH_LONG).show();

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            imgPath=getFilePath(getContext(),data.getData());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getContext(),"ongallery  " + imgPath , Toast.LENGTH_LONG).show();

        ivImage.setImageBitmap(bm);
        bitimg=bm;
    }


    //end image picke2


    private int getCitySpinner(int region){

        switch(region){
            case 0:
                return R.array.fregister2_region0_spinner;
            case 1:
                return R.array.fregister2_region1_spinner;
            case 2:
                return R.array.fregister2_region2_spinner;
            case 3:
                return R.array.fregister2_region3_spinner;
            case 4:
                return R.array.fregister2_region4_spinner;
            case 5:
                return R.array.fregister2_region5_spinner;
            case 6:
                return R.array.fregister2_region6_spinner;
            case 7:
                return R.array.fregister2_region7_spinner;
            case 8:
                return R.array.fregister2_region8_spinner;
            case 9:
                return R.array.fregister2_region9_spinner;
            case 10:
                return R.array.fregister2_region10_spinner;
            case 11:
                return R.array.fregister2_region11_spinner;
            case 12:
                return R.array.fregister2_region12_spinner;
            case 13:
                return R.array.fregister2_region13_spinner;
            case 14:
                return R.array.fregister2_region14_spinner;
            case 15:
                return R.array.fregister2_region15_spinner;
            case 16:
                return R.array.fregister2_region16_spinner;
            case 17:
                return R.array.fregister2_region17_spinner;
            case 18:
                return R.array.fregister2_region18_spinner;
            case 19:
                return R.array.fregister2_region19_spinner;
            default:
                return R.array.fregister2_region0_spinner;
        }
    }

}
