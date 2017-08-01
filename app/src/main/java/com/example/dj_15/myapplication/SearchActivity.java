package com.example.dj_15.myapplication;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.example.dj_15.myapplication.R.mipmap.ic_launcher;

/**
 * Created by Carlotta on 04/05/2017.
 */

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    /*  NO BUONO: Ricrea l'activity sopra quella attuale e non mostra i risultati.
     *  TODO controlla perchè fa così.
     */


    private ArrayList<String> myBooks; //TODO riempire al login?
    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);

        toolbar.setLogo(ic_launcher);
        toolbar.setTitle("Books");

        aQuery = new AQuery(this);

        android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.add(R.id.search_container, new SearchFragment());
        trans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(Intent.ACTION_SEARCH);
        intent.putExtra("query", query);
        setIntent(intent);

        android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
        trans.replace(R.id.search_container, new SearchFragment());
        trans.commit();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }




}