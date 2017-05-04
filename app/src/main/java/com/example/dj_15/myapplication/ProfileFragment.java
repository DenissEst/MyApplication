package com.example.dj_15.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    //ExpandableListView expandableListView;
    //ExpandableListAdapter expandableListAdapter;
    //List<String> expandableListTitle;
    //HashMap<String, List<String>> expandableListDetail;
    private TextView username;
    private TextView name;
    private TextView followers;
    private TextView following;
    private Button logout;
    private SharedPreferences savedData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedData = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        username = (TextView) view.findViewById(R.id.usernameprofile);
        username.setText(getActivity().getIntent().getExtras().getString("myUsername"));
        followers = (TextView) view.findViewById(R.id.followers);
        following = (TextView) view.findViewById(R.id.following);
        logout = (Button) view.findViewById(R.id.logout);

        name = (TextView) view.findViewById(R.id.nameprofile);

        followers.setOnClickListener(this);
        following.setOnClickListener(this);
        logout.setOnClickListener(this);
        //expandableListView = (ExpandableListView) view.findViewById(R.id.exlistviewProfile);
        //expandableListDetail = ExpandableListProfile.getData();
        //expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        //expandableListAdapter = new CustomExpandableListProfile(this.getActivity(), expandableListTitle, expandableListDetail);
        //expandableListView.setAdapter(expandableListAdapter);
        //expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
           // @Override
           // public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getActivity().getApplicationContext(), expandableListTitle.get(groupPosition)
                       // + "List Expanded.", Toast.LENGTH_SHORT).show();
           // }
       // });

        //expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){
           // @Override
            //public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
              //  Toast.makeText(getActivity().getApplicationContext(), expandableListTitle.get(groupPosition) + "->" + expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
              //  return false;
          //  }
        //});


        return view;
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
            case R.id.followers:
                android.app.FragmentTransaction getfollowers = getFragmentManager().beginTransaction();
                SharedPreferences.Editor followers = savedData.edit();
                followers.putString("follow", "ers");
                followers.commit();
                FollowFragment nuovo = (FollowFragment) getFragmentManager().findFragmentById(R.id.preferencesLayout);
                if(nuovo == null){
                    getfollowers.add(R.id.preferencesLayout, new FollowFragment()).addToBackStack("ADD").commit();
                }else{
                    getfollowers.replace(R.id.preferencesLayout, new FollowFragment()).addToBackStack("REPLACE").commit();
                }
                break;
            case R.id.following:
                android.app.FragmentTransaction getfollowing = getFragmentManager().beginTransaction();
                SharedPreferences.Editor following = savedData.edit();
                following.putString("follow", "ing");
                following.commit();
                FollowFragment nuovo2 = (FollowFragment) getFragmentManager().findFragmentById(R.id.preferencesLayout);
                if(nuovo2 == null){
                    getfollowing.add(R.id.preferencesLayout, new FollowFragment()).addToBackStack("ADD").commit();
                }else{
                    getfollowing.replace(R.id.preferencesLayout, new FollowFragment()).addToBackStack("REPLACE").commit();
                }
                break;
        }


    }



    public void onBackPressed(){

    }
}
