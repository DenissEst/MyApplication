package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by dj-15 on 18/05/2017.
 */

public class ChangeProfile extends Fragment implements EditText.OnEditorActionListener,View.OnClickListener,RadioGroup.OnCheckedChangeListener {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private EditText name;
    private EditText info;
    private ImageView image;
    private TextView username;
    private RadioGroup gender;
    private Button change;
    private String idGender;
    private String[] params = new String[4];
    private MyDatabase userDB;
    private SharedPreferences savedData;
    private Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedData = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.change_profile, container, false);

        name = (EditText) view.findViewById(R.id.change_name);
        image = (ImageView)view.findViewById(R.id.change_imag);
        info = (EditText) view.findViewById(R.id.change_info);
        gender = (RadioGroup) view.findViewById(R.id.radio_group_change);
        username = (TextView) view.findViewById(R.id.username_pgchange);
        change =(Button)view.findViewById(R.id.save);

        userDB = new MyDatabase(getActivity());
        cursor = userDB.getUser(savedData.getString("user",""));
        if(cursor != null){
            username.setText(savedData.getString("user", ""));
            String nombre = cursor.getString(cursor.getColumnIndex("name"));
            String infors = cursor.getString(cursor.getColumnIndex("info"));
            name.setText(nombre);
            info.setText(infors);
        }


        name.setOnEditorActionListener(this);
        info.setOnClickListener(this);
        gender.setOnCheckedChangeListener(this);
        change.setOnClickListener(this);

        return view;
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
        if (i == R.id.woman)
            idGender = "Donna";
        else
            idGender = "Uomo";
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.save):
                params[0] = name.getText().toString();
                params[1] = idGender;
                params[2] = savedData.getString("user", "");
                params[3] = info.getText().toString();

                new ChangeThread().execute(params[0], params[1], params[2], params[3]);
                break;
        }


    }

    class ChangeThread extends AsyncTask<String, Void, String> {
        HttpURLConnection connection;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {

                url = new URL("http://charlytime92.altervista.org/change.php");
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", params[2]).appendQueryParameter("sesso", params[1]).appendQueryParameter("name", params[0]).appendQueryParameter("info",params[3]);

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
            } catch (
                    IOException e2) {
                e2.printStackTrace();
                return "exception";
            } finally {
                connection.disconnect();
            }
        }


        protected void onPostExecute(String result) {

            if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("error")) {
                Toast.makeText(getActivity(), " error UpDate db", Toast.LENGTH_LONG).show();
            }else if(result.equalsIgnoreCase("ok")) {

                userDB.open();
                //upDate(String name, String user, String sesso ,String info)
                if (userDB.upDate(params[0], params[2], params[1], params[3]) == true) {
                    Toast.makeText(getActivity(), "UPDATE ok", Toast.LENGTH_SHORT).show();
                    Intent intentApriAS = new Intent(getActivity(), LibraryActivity.class);
                    startActivity(intentApriAS);
                    getActivity().finish();

                } else {
                    Toast.makeText(getActivity(), "UPDATE local error", Toast.LENGTH_SHORT).show();
                }

            }else if(result.equalsIgnoreCase("id_name")) {
                Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}









