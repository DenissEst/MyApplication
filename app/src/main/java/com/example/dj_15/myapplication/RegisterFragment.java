package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.net.URLEncoder;

/**
 * Created by Carlotta on 23/03/2017.
 */

public class RegisterFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private EditText name;
    private RadioGroup gender;
    private RadioButton selectedGender;
    private EditText username;
    private EditText password;
    private EditText confPass;
    private Button register;
    private TextView redirect;
    private String idGender;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        name = (EditText) view.findViewById(R.id.nome);
        gender = (RadioGroup) view.findViewById(R.id.radio_group);
        username = (EditText) view.findViewById(R.id.username2);
        password = (EditText) view.findViewById(R.id.password2);
        confPass = (EditText) view.findViewById(R.id.conf_pass);
        redirect = (TextView) view.findViewById(R.id.redirect);
        register = (Button) view.findViewById(R.id.register);

        name.setOnEditorActionListener(this);
        username.setOnEditorActionListener(this);
        password.setOnEditorActionListener(this);
        confPass.setOnEditorActionListener(this);
        redirect.setOnClickListener(this);
        register.setOnClickListener(this);
        gender.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.redirect):
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new LoginFragment()).commit();
                break;
            case(R.id.register):
                String[] params = new String[5];

                params[0] = name.getText().toString();
                params[1] = idGender;
                params[2] = username.getText().toString();
                params[3] = password.getText().toString();
                params[4] = confPass.getText().toString();


        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if(i == R.id.fem)
            idGender = "Donna";
        else
            idGender = "Uomo";
    }
}