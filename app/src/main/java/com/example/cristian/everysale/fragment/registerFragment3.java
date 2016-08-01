package com.example.cristian.everysale.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.cristian.everysale.R;
import com.example.cristian.everysale.asincronousTasks.asincRegister;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class registerFragment3 extends Fragment implements OnClickListener {

    /*//image picker
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri; // file url to store image/video
    */

    private Button imageButton;
    private CheckBox dataAllowCheckbox;
    private ImageView imageView;
    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_3, container, false);

        savedValues = getActivity().getSharedPreferences("SavedValues", getActivity().MODE_PRIVATE);
        imageView = (ImageView) view.findViewById(R.id.imageView1);
        imageButton = (Button) view.findViewById(R.id.imageButton);
        dataAllowCheckbox = (CheckBox) view.findViewById(R.id.dataAllowCheckBox);

        imageButton.setOnClickListener(this);
        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.registerSubmitButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageButton:
                // capture picture
                captureImage();
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
                    Toast.makeText(getContext(), "Password non coincidenti" + password + " " + confirmPassword, Toast.LENGTH_LONG).show();
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

    //image picker
    /**
    //Checking device has camera hardware or not
    private boolean isDeviceSupportCamera() {
        if (getActivity().getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    //Launching camera app to capture image
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    //Receiving activity result method will be called after closing the camera

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                // successfully captured the image
                // launching upload activity
                //launchUploadActivity(true);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity().getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    // Create a file Uri for saving an image or video
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    // Create a File for saving an image or video
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new   File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        }else {
            return null;
        }

        return mediaFile;
    }
    */

    //end image picker

    //image picker2




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
