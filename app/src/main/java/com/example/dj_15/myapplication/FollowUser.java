package com.example.dj_15.myapplication;

import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Monica on 25/05/2017.
 */

public class FollowUser {

    private String profilephoto;
    private String userId;
    private String name;
    private String you;

    public FollowUser(String profilephoto, String userId, String name, String you){
        this.profilephoto = profilephoto;
        this.userId = userId;
        this.name = name;
        this.you = you;
    }

    public String getProfilephoto(){
        return profilephoto;
    }

    public void setProfilephoto(String profilephoto){
        this.profilephoto = profilephoto;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYou(){
        return you;
    }

    public void setYou(String you){
        this.you = you;
    }
}