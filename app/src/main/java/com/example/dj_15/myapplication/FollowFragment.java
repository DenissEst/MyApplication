package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Monica on 02/05/2017.
 */

public class FollowFragment extends Fragment implements View.OnClickListener {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    //private ImageView profilephoto;
    //private TextView whoFollow;
    //private Button follow;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.followgroup_layout, container, false);
        //profilephoto = (ImageView) view.findViewById(R.id.profilephoto);
        //whoFollow = (TextView) view.findViewById(R.id.whoFollow);
        //follow = (Button) view.findViewById(R.id.follow);

        //profilephoto.setOnClickListener(this);
        //whoFollow.setOnClickListener(this);
        //follow.setOnClickListener(this);
        Context context = getActivity();
        if(sharedPreferences.getString("follow", null).equals("ing")){
            new FollowThread(context, view).execute("following");
        }else if(sharedPreferences.getString("follow", null).equals("ers")){
            new FollowThread(context, view).execute("followers");
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedPreferences = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }

    }

    class FollowThread extends AsyncTask<String, String, String> {

        private Context context;
        private View view;
        HttpURLConnection connection;
        URL url=null;

        public FollowThread(Context context, View view){
            this.context = context;
            this.view = view;
        }

        @Override
        protected String doInBackground(String...params) {

            String myCookie =  "session=" + sharedPreferences.getString("SESSIONID", null);
            try{
                url = new URL("http://charlytime92.altervista.org/follower.php");
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
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("check", params[0]);
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

        protected void onPostExecute(String result){
            if(result.equals("Errore invio richiesta")){
                Toast.makeText(getActivity(), "ERROR SENDING REQUEST", Toast.LENGTH_LONG).show();
            }
            else if(result.equals("Errore connessione")){
                Toast.makeText(getActivity(), "ERROR CONNECTION", Toast.LENGTH_LONG).show();
            }
            else if(result.equals("Errore ricezione risposta")){
                Toast.makeText(getActivity(), "ERROR FETCHING RESULT", Toast.LENGTH_LONG).show();
            }
            else if(result.equalsIgnoreCase("NO")){
                Toast.makeText(getActivity(), "NESSUN FOLLOWING/FOLLOWER", Toast.LENGTH_LONG).show();
            }else{
                JSONArray data = null;
                try {
                    data = new JSONArray(result);
                    if(sharedPreferences.getString("follow", null).equals("ers")){
                        ListView listViewFollowers = (ListView) view.findViewById(R.id.followgroup_container);
                        List<FollowUser> list = new LinkedList();
                        for(int i =0; i<data.length(); i++){
                            String userId = data.getJSONObject(i).get("username").toString();
                            String profilePhoto = data.getJSONObject(i).get("foto").toString();
                            String name = data.getJSONObject(i).get("name").toString();
                            String you = "null";
                            if(data.getJSONObject(i).get("following").toString().equals('0')){
                                you = "StartFollowing";
                            }
                            list.add(i, new FollowUser(profilePhoto, userId, name, you));
                        }
                        CustomAdapterOptimize adapterOptimize = new CustomAdapterOptimize(context, R.layout.follow_layout, list);
                        listViewFollowers.setAdapter(adapterOptimize);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
