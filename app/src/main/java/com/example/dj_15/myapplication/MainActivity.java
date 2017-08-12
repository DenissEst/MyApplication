package com.example.dj_15.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import static com.example.dj_15.myapplication.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences savedValues;
    protected static final int CONNECTION_TIMEOUT = 10000;
    protected static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        String user = savedValues.getString("user", "");
        if (user != "") {

            Intent intentApriAS = new Intent(this, LibraryActivity.class);
            intentApriAS.putExtra("myUsername", user);
            startActivity(intentApriAS);
            this.finish();
        } else {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Books");
            toolbar.setLogo(ic_launcher);

            if (savedInstanceState == null) {
                RegisterFragment register = (RegisterFragment) getFragmentManager().findFragmentById(R.id.frag_container);

                if (register == null) {
                    android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.add(R.id.frag_container, new RegisterFragment());
                    trans.commit();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class ComunicationThread extends AsyncTask<String, Void, String> {
        HttpURLConnection connection;
        URL url;
        String username;

        @Override
        protected String doInBackground(String... strings) {
            username = strings[0];
            try {
                url = new URL("http://charlytime92.altervista.org/session.php");
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

                Uri.Builder builder = new Uri.Builder().appendQueryParameter("user", username);

                String query = builder.build().getEncodedQuery();

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
                Intent intentApriAS = new Intent(getParent(), LibraryActivity.class);
                intentApriAS.putExtra("myUsername", username);
                startActivity(intentApriAS);
                getParent().finish();
            } else if (result.equalsIgnoreCase("error_query")) {
                Toast.makeText(getParent(), "non query", Toast.LENGTH_LONG).show();

            }else if(result.equalsIgnoreCase("username utilizzato")){
                Toast.makeText(getParent(), "sei gia un utente registrato", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getParent(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getParent(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
