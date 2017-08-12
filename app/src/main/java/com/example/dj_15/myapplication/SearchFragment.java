package com.example.dj_15.myapplication;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Carlotta on 18/05/2017.
 */

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, Observer{

    private ArrayList<Book> books;
    private ListView listView;
    private AdapterSearchList adapter;
    private AQuery aQuery;
    private JSONArray array;
    private Position position;
    protected Bundle savedState;
    private HashMap<Book, String> toSave;
    private Orientation orientation;
    private String rotation;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aQuery = new AQuery(this.getActivity());
        books = new ArrayList<>();
        array = new JSONArray();
        toSave = new HashMap<>();

        position = new Position();
        position.addObserver(this);

        orientation = new Orientation(getActivity());
        rotation = orientation.getCurrentOrientation().name();

        setHasOptionsMenu(true);

        if(savedInstanceState != null && savedInstanceState.getBoolean("rotate")) {
            savedState = savedInstanceState;
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.results);

        return view;
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
                array = json.getJSONArray("items");
                for(int i = 0; i < array.length(); i++)
                    books.add(new Book(array.getJSONObject(i).getJSONObject("volumeInfo")));
                adapter = new AdapterSearchList(getActivity(), R.layout.result_line, books, position);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        reset();
        doMySearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void update(Observable observable, Object o) {
        int pos = (Integer) o;
        Book selected = books.get(pos);
        Position temp = (Position) observable;
        if(temp.remove != false){
            if(!toSave.containsKey(selected))
                toSave.put(selected, "REMOVE");
            else if(toSave.containsKey(selected) && toSave.get(selected).equals("ADD"))
                toSave.remove(selected);
            position.restore();
        }else if(temp.add != false){
            if(!toSave.containsKey(selected) || toSave.get(selected).equals("REMOVE"))
                toSave.put(selected, "ADD");
            position.restore();
        }else {
            Bundle arg = new Bundle();

            arg.putSerializable("selected", selected);

            getFragmentManager().saveFragmentInstanceState(this);

            BookFragment bookFragment = new BookFragment();
            bookFragment.setArguments(arg);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.library_container, bookFragment).addToBackStack("BOOK");
            transaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", books);
        if(!rotation.equals(orientation.getCurrentOrientation().name()))
            outState.putBoolean("rotate", true);
        savedState = outState;
    }

    @Override
    public void onResume() {
        super.onResume();
        position.restore();
    }

    @Override
    public void onStart() {
        super.onStart();

        if(savedState != null) {
            books = (ArrayList<Book>) savedState.getSerializable("list");
            restoreData();
        }
    }

    public void restoreData(){
        adapter = new AdapterSearchList(getActivity(), R.layout.result_line, books, position);
        listView.setAdapter(adapter);
    }

    public void reset(){
        if(!books.isEmpty())
            books.removeAll(books);
        position.restore();
    }
}
