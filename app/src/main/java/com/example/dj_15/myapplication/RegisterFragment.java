package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Carlotta on 23/03/2017.
 */

public class RegisterFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private EditText name;
    private RadioGroup gender;
    private RadioButton selectedGender;
    private EditText username;
    private EditText password;
    private EditText confPass;
    private Button register;
    private TextView redirect;
    private String idGender;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        switch (view.getId()) {
            case (R.id.redirect):
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new LoginFragment()).commit();
                break;
            case (R.id.register):
                String[] params = new String[5];

                params[0] = name.getText().toString();
                params[1] = idGender;
                params[2] = username.getText().toString();
                params[3] = password.getText().toString();
                params[4] = confPass.getText().toString();

                new RegisterThread().execute(params[0],params[1],params[2],params[3],params[4]);

                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_UNSPECIFIED) {
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.fem)
            idGender = "Donna";
        else
            idGender = "Uomo";
    }

    class RegisterThread extends AsyncTask<String, Void, String> {
        HttpURLConnection connection;
        URL url = null;
        String myUsername;
        @Override
        protected String doInBackground(String... params) {
            myUsername=params[2];
            try {

                url = new URL("http://charlytime92.altervista.org/control_register.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception";
            }

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");

                connection.setDoInput(true);
                connection.setDoInput(true);

                //append parameters to url

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("name", params[0])
                        .appendQueryParameter("gender", params[1])
                        .appendQueryParameter("username", params[2])
                        .appendQueryParameter("password", params[3])
                        .appendQueryParameter("confPass", params[4]);

                String query = builder.build().getEncodedQuery();
                //apre la connessione
                OutputStream os = connection.getOutputStream();
                BufferedWriter write = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                write.write(query);
                write.flush();
                os.close();
                connection.connect();
            } catch (IOException e1) {
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response = connection.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    //leggo i dati e li mando sul server
                    InputStream input = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    StringBuilder result = new StringBuilder();
                    String line;


                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                return "exception";
            } finally {
                connection.disconnect();
            }

        }

        protected void onPostExecute(String result) {
            if (result.equalsIgnoreCase("ok")) {
                Intent intentApriAS = new Intent(getActivity(), LibraryActivity.class);
                intentApriAS.putExtra("myUsername",myUsername);
                startActivity(intentApriAS);
                getActivity().finish();
            } else if (result.equalsIgnoreCase("error_query")) {
                Toast.makeText(getActivity(), "non query", Toast.LENGTH_LONG).show();

            }else if(result.equalsIgnoreCase("username utilizzato")){
                Toast.makeText(getActivity(), "sei gia un utente registrato", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }
    }
}