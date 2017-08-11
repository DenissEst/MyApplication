package com.example.dj_15.myapplication;

/**
 * Created by Monica on 08/08/2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
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

public class ChangeFollowThread extends AsyncTask<String, String, String> {


    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private Context context;
    private View view;
    HttpURLConnection connection;
    URL url=null;

    public ChangeFollowThread(Context context, View view){
        this.context = context;
        this.view = view;
    }

    @Override
    protected String doInBackground(String... params) {
        String myCookie =  "session=" + params[0];
        try{
            url = new URL("http://charlytime92.altervista.org/change_follow.php");
        }catch(MalformedURLException e){
            e.printStackTrace();
            return "Malformed URL";
        }
        try{
            connection =(HttpURLConnection)url.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Cookie", myCookie);
            connection.setDoInput(true);
            connection.setDoInput(true);
            connection.connect();

            //append parameters to url
            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("id_follow", params[1]);
            builder.appendQueryParameter("state", params[2]);
            String query = builder.build().getEncodedQuery();
            //apre la connessione

            OutputStream os=connection.getOutputStream();
            BufferedWriter write =new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            write.write(query);
            write.flush();
            os.close();
        } catch(IOException e1) {
            e1.printStackTrace();
            return "Errore invio richiesta";
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
                return ("Errore connessione");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return "Errore ricezione risposta";
        }finally {
            connection.disconnect();
        }

    }

    protected void onPostExecute(String result) {
        if (result.equals("Errore invio richiesta")) {
            Toast.makeText(context, "ERROR SENDING REQUEST", Toast.LENGTH_LONG).show();
        } else if (result.equals("Errore connessione")) {
            Toast.makeText(context, "ERROR CONNECTION", Toast.LENGTH_LONG).show();
        } else if (result.equals("Errore ricezione risposta")) {
            Toast.makeText(context, "ERROR FETCHING RESULT", Toast.LENGTH_LONG).show();
        } else if (result.equalsIgnoreCase("NO")) {
            Toast.makeText(context, "NESSUN FOLLOWING/FOLLOWER", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(context, "HAI CAMBIATO CORRETTEMENTE", Toast.LENGTH_LONG).show();
            android.app.FragmentManager man = ((Activity) context).getFragmentManager();
            FragmentTransaction tran = man.beginTransaction();
            tran.replace(R.id.preferencesLayout, new FollowFragment()).addToBackStack("REPLACE").commit();
        }
    }

}
