package com.example.dj_15.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Monica on 25/05/2017.
 */

public class CustomAdapterOptimize extends ArrayAdapter<FollowUser> implements View.OnClickListener{

    private SharedPreferences sharedPreferences;

    public CustomAdapterOptimize(Context context, int resource, List<FollowUser> users) {
        super(context, resource, users);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        sharedPreferences = this.getContext().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.follow_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.profilephoto = (ImageView)convertView.findViewById(R.id.profilephoto);
            viewHolder.userId = (TextView)convertView.findViewById(R.id.whoFollow);
            viewHolder.you = (Button)convertView.findViewById(R.id.follow);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FollowUser user = getItem(position);
        if(user.getProfilephoto()=="NULL"){
            //
        }else{
            //
        }
        viewHolder.userId.setText(user.getUserId());
        if(user.getYou().equals("StartFollowing")){
            viewHolder.you.setText("Inizia a seguire");
        }else if(user.getYou().equals("StopFollowing")){
            viewHolder.you.setText("Smetti di seguire");

        }

        viewHolder.you.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                new ChangeFollowThread(getContext(), view).execute(sharedPreferences.getString("SESSIONID", null), user.getUserId().toString(), user.getYou().toString());
            }
        });

        return convertView;
    }

    @Override
    public void onClick(View v) {

    }

    private class ViewHolder{
        public ImageView profilephoto;
        public TextView userId;
        public Button you;

    }

}
