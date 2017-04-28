package com.example.dj_15.myapplication;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Monica on 28/04/2017.
 */

public class ProfileFragment extends Fragment {

    TextView username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        username = (TextView) view.findViewById(R.id.usernameprofile);
        username.setText(getActivity().getIntent().getExtras().getString("myUsername"));


        return view;
    }


}
