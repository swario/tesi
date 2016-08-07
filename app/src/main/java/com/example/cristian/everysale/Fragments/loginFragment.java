package com.example.cristian.everysale.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.example.cristian.everysale.Activity.Main2Activity;
import com.example.cristian.everysale.Interfaces.LoginPerformer;
import com.example.cristian.everysale.R;
import com.example.cristian.everysale.AsyncronousTasks.asincLogin;

public class loginFragment extends Fragment implements OnEditorActionListener, OnClickListener, LoginPerformer {


    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private SharedPreferences savedValues;

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
        savedValues = getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);

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

        new asincLogin(getActivity(), this).execute(username, password);

    }

    @Override
    public void OnLoginSuccess(String username, String password, long id) {
        SharedPreferences.Editor editor = savedValues.edit();

        editor.putLong("userId", id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

        Activity activity = getActivity();
        Intent intent = new Intent(activity, Main2Activity.class);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void OnConnectionAbsent() {

        Toast.makeText(getContext(), "Connessione Assente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnLoginFailed() {

        Toast.makeText(getContext(), "Dati non corretti", Toast.LENGTH_LONG).show();
    }
}
