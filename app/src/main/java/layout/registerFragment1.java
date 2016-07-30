package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.R;

public class registerFragment1 extends Fragment implements OnClickListener {

    private Button forwardButton;

    private EditText emailText;
    private EditText usernameText;
    private EditText password;
    private EditText confirmPassword;

    private SharedPreferences savedValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_1, container, false);

        forwardButton = (Button) view.findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(this);

        emailText = (EditText) view.findViewById(R.id.emailEditText);
        usernameText = (EditText) view.findViewById(R.id.usernameEditText);
        password = (EditText) view.findViewById(R.id.passwordEditText);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPasswordEditText);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forwardButton:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new registerFragment2()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        emailText.setText(savedValues.getString("email", ""));
        usernameText.setText(savedValues.getString("username", ""));
        password.setText(savedValues.getString("password", ""));
        confirmPassword.setText(savedValues.getString("confirmPassword", ""));

    }

    @Override
    public void onPause(){

        Editor editor = savedValues.edit();

        editor.putString("email", emailText.getText().toString());
        editor.putString("username", usernameText.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.putString("confirmPassword", confirmPassword.getText().toString());

        editor.commit();

        super.onPause();
    }
}
