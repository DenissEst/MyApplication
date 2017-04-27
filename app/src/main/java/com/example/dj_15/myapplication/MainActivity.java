package com.example.dj_15.myapplication;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            RegisterFragment register = (RegisterFragment) getFragmentManager().findFragmentById(R.id.frag_container);

            if(register == null){
                android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.add(R.id.frag_container, new RegisterFragment());
                trans.commit();
            }
        }

    }
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
