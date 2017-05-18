package com.example.dj_15.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlotta on 18/05/2017.
 */

public class SearchFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.results);

        return view;
    }

    public void showResult(ArrayList<Book> books){
        adapter = new ArrayAdapter(getActivity(), R.layout.listview_line, books);
        listView.setAdapter(adapter);
    }
}
