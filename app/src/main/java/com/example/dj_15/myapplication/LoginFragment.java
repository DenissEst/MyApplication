package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.net.ProtocolException;
import java.net.URL;


/**
 * Created by Monica on 21/03/2017.
 */

public class LoginFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private ImageView imageView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView redirect;
    private SharedPreferences savedData;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        savedData = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }

    @Override
    public void onResume() {
        usernameEditText.setText(savedData.getString("user", ""));
        super.onResume();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        usernameEditText = (EditText) view.findViewById(R.id.username);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        loginButton = (Button) view.findViewById(R.id.login);
        redirect = (TextView) view.findViewById(R.id.login_redirect);

        usernameEditText.setOnEditorActionListener(this);
        passwordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        redirect.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.login_redirect):
                getFragmentManager().beginTransaction().replace(R.id.frag_container, new RegisterFragment(), "register").commit();
                break;
            case(R.id.login):
                String[] check = new String[2];
                check[0] = usernameEditText.getText().toString();
                check[1] = passwordEditText.getText().toString();
                new LoginThread().execute(check[0], check[1]);
                 /*       if(check[0] == null){
                            Toast.makeText(getContext(), "Non hai inserito il tuo username!", Toast.LENGTH_SHORT).show();
                        }
                        Intent openprint = new Intent(getActivity(), PrintActivity.class);
                        openprint.putExtra("username", "check[0]");
                        openprint.putExtra("password", "check[1]");

                        startActivity(openprint);

                */
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            return true;
        }
        return false;
    }

    class LoginThread extends AsyncTask<String, String, String>{
        HttpURLConnection connection;
        URL url=null;
        String myUsername;

        @Override
        protected String doInBackground(String...params) {

            myUsername = params[0];

            try{
                url = new URL("http://charlytime92.altervista.org/control_login.php");
            }catch(MalformedURLException e){
                e.printStackTrace();
                return "exception";
            }

            try{
                connection =(HttpURLConnection)url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                connection.setRequestMethod("POST");

                connection.setDoInput(true);
                connection.setDoInput(true);

                //append parameters to url
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", params[0]).appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();
                //apre la connessione
                OutputStream os=connection.getOutputStream();
                BufferedWriter write =new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                write.write(query);
                write.flush();
                os.close();
                connection.connect();
            } catch(IOException e1) {
                e1.printStackTrace();
                return "exception";
            }


            try{
                int response= connection.getResponseCode();
                if(response==HttpURLConnection.HTTP_OK){
                    //leggo i dati e li mando sul server
                    InputStream input =connection.getInputStream();
                    BufferedReader reader =new BufferedReader(new InputStreamReader(input));

                    StringBuilder result =new StringBuilder();
                    String line;


                    while( (line=reader.readLine()) !=null){
                        result.append(line);
                    }

                    return (result.toString());
                }else{
                    return ("unsuccessful");
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                return "exception";
            }finally {
                connection.disconnect();
            }
        }

        protected void onPostExecute(String result){
            if(result.equalsIgnoreCase("ok")){
                SharedPreferences.Editor editor = savedData.edit();
                editor.putString("user", myUsername);
                editor.commit();

                Intent intentApriAS = new Intent(getActivity(), LibraryActivity.class);
                intentApriAS.putExtra("myUsername", myUsername);
                startActivity(intentApriAS);
                getActivity().finish();
            }else if(result.equalsIgnoreCase("error")){
                Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_LONG).show();

            }else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getActivity(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }
    }
}

