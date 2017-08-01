package com.example.dj_15.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlotta on 18/05/2017.
 */

public class SearchFragment extends Fragment {

    private ArrayList<Book> books;
    private ListView listView;
    private AdapterSearchList adapter;
    private AQuery aQuery;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aQuery = new AQuery(this.getActivity());
        books = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.results);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getActivity().getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra("query");
            doMySearch(query);
        }
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
            String url = "https://www.googleapis.com/books/v1/volumes?q=" + search + "&maxResults=40";
            aQuery.ajax(url, JSONObject.class, this, "print");
        }else{
            //TODO ricerca offline
            isOnline();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void print(String url, JSONObject json, AjaxStatus status){
        if(json != null){
            try {
                JSONArray array = json.getJSONArray("items");
                for(int i = 0; i < array.length(); i++)
                    books.add(new Book(array.getJSONObject(i).getJSONObject("volumeInfo")));
                adapter = new AdapterSearchList(getActivity(), R.layout.result_line, books);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
