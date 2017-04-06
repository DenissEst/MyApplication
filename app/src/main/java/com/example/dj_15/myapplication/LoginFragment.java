package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Intent;
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

/**
 * Created by Monica on 21/03/2017.
 */

public class LoginFragment extends Fragment implements TextView.OnEditorActionListener, View.OnClickListener {


    private ImageView imageView;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        usernameEditText = (EditText) view.findViewById(R.id.username);
        passwordEditText = (EditText) view.findViewById(R.id.password);
        loginButton = (Button) view.findViewById(R.id.login);

        usernameEditText.setOnEditorActionListener(this);
        passwordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String[] check = new String[2];
        check[0] = usernameEditText.getText().toString();
        check[1] = passwordEditText.getText().toString();
 /*       if(check[0] == null){
            Toast.makeText(getContext(), "Non hai inserito il tuo username!", Toast.LENGTH_SHORT).show();
        }
        Intent openprint = new Intent(getActivity(), PrintActivity.class);
        openprint.putExtra("username", "check[0]");
        openprint.putExtra("password", "check[1]");

        startActivity(openprint);

*/
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            return true;
        }
        return false;
    }

    class LoginThread extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String[] check) {

            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("username", check[0]);
                jsonObject.put("password", check[1]);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //HttpClient httpClient = new DefaultHttpClient();


            return null;
        }
    }





}

