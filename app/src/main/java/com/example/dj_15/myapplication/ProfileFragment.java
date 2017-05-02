package com.example.dj_15.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Monica on 28/04/2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private TextView username;
    private TextView name;
    private Button logout;
    private SharedPreferences savedData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        savedData = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        username = (TextView) view.findViewById(R.id.usernameprofile);
        username.setText(getActivity().getIntent().getExtras().getString("myUsername"));
        logout = (Button) view.findViewById(R.id.logout);

        name = (TextView) view.findViewById(R.id.nameprofile);

        logout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.logout:
                SharedPreferences.Editor editor = savedData.edit();
                editor.remove("user");
                editor.commit();

                Intent intentApriAS = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intentApriAS);
                this.getActivity().finish();
                break;
        }
    }
}
