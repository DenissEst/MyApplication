package com.example.dj_15.myapplication;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Monica on 28/04/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private TextView username;
    private TextView name;

    private Button logout;
    private Button modifica;
    private SharedPreferences savedData;
    private  MyDatabase userDB;
    private Cursor cursor;
    private String users,nombre,sex;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedData = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment, container, false);

        username = (TextView) view.findViewById(R.id.usernameprofile);
        logout = (Button) view.findViewById(R.id.logout);
        modifica = (Button) view.findViewById(R.id.change_button);
        name = (TextView) view.findViewById(R.id.nameprofile);

        userDB = new MyDatabase(getActivity());

        if(isOnline()){
            //aggiorno database locale
        }
        cursor = userDB.getUser(savedData.getString("user",""));
        if(cursor != null){
            users = cursor.getString(cursor.getColumnIndex("username"));
            nombre = cursor.getString(cursor.getColumnIndex("name"));
            sex = cursor.getString(cursor.getColumnIndex("sesso"));
            name.setText(nombre);
            username.setText(users);
        }


        logout.setOnClickListener(this);
        modifica.setOnClickListener(this);
        expandableListView = (ExpandableListView) view.findViewById(R.id.exlistviewProfile);
        expandableListDetail = ExpandableListProfile.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListProfile(this.getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity().getApplicationContext(), expandableListTitle.get(groupPosition)
                        + "List Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(), expandableListTitle.get(groupPosition) + "->" + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        return view;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logout:
                SharedPreferences.Editor editor = savedData.edit();
                editor.remove("user");
                editor.commit();

                Intent intentApriAS = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intentApriAS);
                this.getActivity().finish();
                break;

            case R.id.change_button:
                android.app.FragmentTransaction change = getFragmentManager().beginTransaction();
                change.replace(R.id.library_container,new ChangeProfile()).commit();
                break;

        }


    }







}
