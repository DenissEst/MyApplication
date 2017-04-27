package com.example.dj_15.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            RegisterFragment register = (RegisterFragment) getFragmentManager().findFragmentById(R.id.frag_container);

            if (register == null) {
                android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.add(R.id.frag_container, new RegisterFragment(), "register").commit();
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
