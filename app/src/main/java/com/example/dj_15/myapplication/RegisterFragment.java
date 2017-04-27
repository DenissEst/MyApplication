package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.os.Bundle;
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

public class RegisterFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener{

    private EditText name;
    private RadioGroup gender;
    private RadioButton selectedGender;
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

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.redirect):
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new LoginFragment()).commit();
                break;
            case(R.id.register):
                int select = gender.getCheckedRadioButtonId();
                selectedGender = (RadioButton) view.findViewById(select);
                String data_name = name.getText().toString();
                String data_gender = selectedGender.getText().toString();
                String data_user = username.getText().toString();
                String data_pass = password.getText().toString();
                String data_conf = confPass.getText().toString();

           /*     try{
                  //  URL pagina = new URL("");
                    String POST = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode(data_name, "UTF-8") + "&" + URLEncoder.encode("sesso", "UTF-8") + "=" + URLEncoder.encode(data_gender, "UTF-8") + "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(data_user, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(data_pass, "UTF-8") + "&" + URLEncoder.encode("confpass", "UTF-8") + "=" + URLEncoder.encode(data_conf, "UTF-8");

                }catch(Exception e){

                }
*/
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }
}
