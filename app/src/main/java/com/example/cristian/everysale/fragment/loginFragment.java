package com.example.cristian.everysale.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cristian.everysale.R;
import com.example.cristian.everysale.asincronousTasks.asincLogin;

public class loginFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener{


    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;

    public loginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Get references to the widgets
        userEditText = (EditText) view.findViewById(R.id.userEditText);
        passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        loginButton = (Button) view.findViewById(R.id.login_button);

        // Set listener
        userEditText.setOnEditorActionListener(this);
        passwordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        Log.e("Listener", "Listener Settato");

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {

        String username = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals("")){

            Toast.makeText(getContext(),"Inserisci uno username/email", Toast.LENGTH_LONG).show();
            userEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(userEditText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }
        else if(password.equals("")){
            Toast.makeText(getContext(),"Inserisci la password", Toast.LENGTH_LONG).show();
            passwordEditText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(passwordEditText, InputMethodManager.SHOW_IMPLICIT);
            return;
        }

        new asincLogin(getContext(), getActivity()).execute(username, password);


        // definisco l'intenzione

    }
}
