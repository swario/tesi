package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;

import com.example.cristian.everysale.R;

public class registerFragment2 extends Fragment implements OnClickListener, AdapterView.OnItemSelectedListener {

    private SharedPreferences savedValues;

    private EditText nameEditText;
    private EditText surnameEditText;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private EditText mobileEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_2, container, false);

        nameEditText = (EditText) view.findViewById(R.id.nomeEditText);
        surnameEditText = (EditText) view.findViewById(R.id.cognomeEditText);
        regionSpinner = (Spinner) view.findViewById(R.id.regioneSpinner);
        citySpinner = (Spinner) view.findViewById(R.id.cittaSpinner);
        mobileEditText = (EditText) view.findViewById(R.id.cellulareEditText);

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fregister2_regions_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter);
        regionSpinner.setOnItemSelectedListener(this);

        //poi, prelevo i dati per riempire eventuali campi già compilati
        nameEditText.setText(savedValues.getString("name", ""));
        surnameEditText.setText(savedValues.getString("surname", ""));
        regionSpinner.setSelection(savedValues.getInt("regionPosition", 0));

        adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.fregister2_region0_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setSelection(savedValues.getInt("cityPosition", 0));
        mobileEditText.setText(savedValues.getString("mobilePhone", ""));
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
