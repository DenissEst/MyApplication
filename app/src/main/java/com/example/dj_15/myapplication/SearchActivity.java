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

    private ArrayList<Book> books;
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
        books = new ArrayList<>();

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            android.app.FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.add(R.id.search_container, new SearchFragment());
            trans.commit();
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
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
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void doMySearch(String query){
        if(isOnline()){
            //TODO ricerca online
            String[] temp = query.split(" ");
            String search = "";
            for(int i = 0; i < temp.length; i++){
                if(search.equals(""))
                    search = temp[i];
                else
                    search += "+" + temp[i];
            }
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + search;
            aQuery.ajax(url, JSONObject.class, this, "print");
        }else{
            //TODO ricerca offline
        }
    }


    public void print(String url, JSONObject json, AjaxStatus status){
        if(json != null){
            try {
                JSONArray array = json.getJSONArray("items");
                for(int i = 0; i < array.length(); i++)
                    books.add(new Book(array.getJSONObject(i).getJSONObject("volumeInfo")));
                SearchFragment s = (SearchFragment) getFragmentManager().findFragmentById(R.id.search_container);
                s.showResult(books);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}