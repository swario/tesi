package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.MainActivity;
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
                getFragmentManager().beginTransaction().remove(this).add(R.id.frame_container, new registerFragment2(), "registerFragment2").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        emailText.setText(savedValues.getString("email", ""));
        usernameText.setText(savedValues.getString("registerUsername", ""));
        password.setText(savedValues.getString("registerPassword", ""));
        confirmPassword.setText(savedValues.getString("confirmPassword", ""));

        String message = savedValues.getString("message", "");
        if(message.contains("email")) {
            emailText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(emailText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if(message.contains("username")) {
            usernameText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(usernameText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        if(message.contains("password")) {
            password.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(password, InputMethodManager.SHOW_IMPLICIT);
            return;
        }

    }

    @Override
    public void onPause(){

        Editor editor = savedValues.edit();

        editor.putString("email", emailText.getText().toString());
        editor.putString("registerUsername", usernameText.getText().toString());
        editor.putString("resisterPassword", password.getText().toString());
        editor.putString("confirmPassword", confirmPassword.getText().toString());

        editor.commit();

        super.onPause();
    }
}