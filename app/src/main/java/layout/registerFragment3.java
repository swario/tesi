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

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backButton:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new registerFragment2()).addToBackStack(null).commit();
                break;

            case R.id.registerSubmitButton:



                new asincRegister(getContext()).execute();
                break;
        }
    }
}
