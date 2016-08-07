package com.example.cristian.everysale.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.cristian.everysale.AsyncronousTasks.asincRegister;
import com.example.cristian.everysale.BaseClasses.imagePicker.ImagePickerActivity;
import com.example.cristian.everysale.BaseClasses.imagePicker.Utility;
import com.example.cristian.everysale.R;

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

    //image picker
    private String imgPath=null;
    //image picker

    private Utility bitmapConverter=null;

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
        if(savedValues.getInt("dataAllowCheckBox", 0)==0){
            dataAllowCheckbox.setChecked(false);
        }else if (savedValues.getInt("dataAllowCheckBox", 0) == 1){
            dataAllowCheckbox.setChecked(true);
        }
        imgPath=savedValues.getString("imgPath",null);
        Utility utility=new Utility();
        ivImage.setImageBitmap(utility.getBitmap(imgPath));
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
                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"ongallery  " + imgPath , Toast.LENGTH_LONG).show();
                bitmapConverter=new Utility();
                imgPath=savedValues.getString("imgPath",null);
                ivImage.setImageBitmap(bitmapConverter.getBitmap(imgPath));
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

                new asincRegister(getContext(), getActivity()).execute(email, username, password, name, surname, region,
                        city, mobilePhone, dataAllow, imgPath);
                break;
        }
    }

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
