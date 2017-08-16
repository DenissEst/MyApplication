package com.example.dj_15.myapplication;

import android.net.Uri;
import android.os.AsyncTask;

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
 * Created by Carlotta on 14/08/2017.
 */

public class BookThread extends AsyncTask<JSONObject, Void, Void> {

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=15000;

    private HttpURLConnection connection;
    private URL url=null;
    private JSONObject json;

    @Override
    protected Void doInBackground(JSONObject... jsonObjects) {
        json = jsonObjects[0];

        try{
            url = new URL("http://charlytime92.altervista.org/new_book.php");
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod("POST");

            connection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder().appendQueryParameter("username", json.getString("user")).appendQueryParameter("data", json.getJSONArray("books").toString());
            String query = builder.build().getEncodedQuery();

            OutputStream os = connection.getOutputStream();
            BufferedWriter write = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            write.write(query);
            write.flush();
            os.close();
            connection.connect();
        }catch(IOException e1){
            e1.printStackTrace();
        }catch(JSONException e2){
            e2.printStackTrace();
        }

        try{
            int response = connection.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                StringBuilder result = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    result.append(line);
                }
            }
        }catch(IOException e3){
            e3.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return null;
    }
}
