package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;

import com.example.cristian.everysale.R;
import com.example.cristian.everysale.asincRegister;

public class registerFragment3 extends Fragment implements OnClickListener {

    private SharedPreferences savedValues;

    private CheckBox dataAllowCheckbox;


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

        view.findViewById(R.id.backButton).setOnClickListener(this);
        view.findViewById(R.id.registerSubmitButton).setOnClickListener(this);
        dataAllowCheckbox = (CheckBox) view.findViewById(R.id.dataAllowCheckBox);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backButton:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new registerFragment2()).addToBackStack(null).commit();
                break;

            case R.id.registerSubmitButton:

                String email = savedValues.getString("email", "");
                String username = savedValues.getString("username", "");
                String password = savedValues.getString("password", "");
                String confirmPassword = savedValues.getString("confirmPassword", "");
                if(!password.equals(confirmPassword) ){
                    Toast.makeText(getContext(), "Password non coincidenti" + password + " " + confirmPassword, Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.frame_container, new registerFragment1()).addToBackStack(null).commit();
                    return;
                }
                String name = savedValues.getString("name", "");
                String surname = savedValues.getString("surname", "");
                String region = getResources().getStringArray(R.array.fregister2_regions_spinner)
                        [savedValues.getInt("regionPosition",0)];
                String city = getResources().getStringArray(R.array.fregister2_region0_spinner)
                        [savedValues.getInt("cityPosition",0)];
                String mobilePhone = savedValues.getString("mobilePhone", "");
                String dataAllow = null;
                if(dataAllowCheckbox.isChecked()){
                    dataAllow += "1";
                }
                else{
                    dataAllow += "0";
                }

                new asincRegister(getContext()).execute(email, username, password, name, surname, region, city, mobilePhone, dataAllow);
                break;
        }
    }
}
