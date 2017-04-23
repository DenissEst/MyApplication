package com.example.dj_15.myapplication;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private String lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            LoginFragment login = (LoginFragment) getFragmentManager().findFragmentById(R.id.login_fragment);

            if(login != null){
                android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.add(R.id.frag_container, login).commit();
            }else{
                RegisterFragment reg = (RegisterFragment) getFragmentManager().findFragmentById(R.id.register_fragment);
                if(reg != null){
                    android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                    trans.add(R.id.frag_container, reg).commit();
                }
            }
        }else{
            RegisterFragment register = (RegisterFragment) getFragmentManager().findFragmentById(R.id.frag_container);

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
}
