package com.example.dj_15.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.dj_15.myapplication.R.mipmap.ic_launcher;

/**
 * Created by Carlotta on 27/04/2017.
 */

public class LibraryActivity extends AppCompatActivity implements View.OnClickListener{

    private Button search;
    private ImageView profile;
    private Button library;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLib);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Books");
        toolbar.setLogo(ic_launcher);

        if(savedInstanceState == null){
            ProfileFragment profilo = (ProfileFragment) getFragmentManager().findFragmentById(R.id.library_container);
            if(profilo == null){
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.add(R.id.library_container, new ProfileFragment()).commit();
            }
        }

        search = (Button) findViewById(R.id.search);
        profile = (ImageView) findViewById(R.id.profile);
        library = (Button) findViewById(R.id.books);
    }

    @Override
    protected void onStart() {
        super.onStart();

        search.setOnClickListener(this);
        profile.setOnClickListener(this);
        library.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search:
                Intent intentApriAS = new Intent(this, SearchActivity.class);
                startActivity(intentApriAS);
                this.finish();
                break;
            case R.id.profile:
                android.app.FragmentTransaction profile = getFragmentManager().beginTransaction();
                profile.replace(R.id.library_container, new ProfileFragment()).commit();
                break;
            case R.id.books:
                break;
        }
    }
}
