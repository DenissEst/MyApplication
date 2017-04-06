package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.MediaStore;
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

/**
 * Created by Carlotta on 23/03/2017.
 */

public class RegisterFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener{

    private EditText name;
    private RadioGroup gender;
    private RadioButton fem;
    private RadioButton mas;
    private EditText username;
    private EditText password;
    private EditText confPass;
    private Button register;
    private TextView redirect;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.register_fragment, container, false);

        name = (EditText) view.findViewById(R.id.nome);
        gender = (RadioGroup) view.findViewById(R.id.radio_group);
        fem = (RadioButton) view.findViewById(R.id.fem);
        mas = (RadioButton) view.findViewById(R.id.mas);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        confPass = (EditText) view.findViewById(R.id.conf_pass);
        redirect = (TextView) view.findViewById(R.id.redirect);
        register = (Button) view.findViewById(R.id.register);

        name.setOnEditorActionListener(this);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gender, @IdRes int i) {
                if(i == R.id.fem){

                }else{

                }
            }
        });
        username.setOnEditorActionListener(this);
        password.setOnEditorActionListener(this);
        confPass.setOnEditorActionListener(this);
        redirect.setOnClickListener(this);
        register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.redirect):
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new LoginFragment()).commit();
                break;
            case(R.id.register):
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
