package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class FragmentSession extends Fragment {

    Button b2;
    Button back2;
    TextView display_ID;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_session, container, false);
        View view = inflater.inflate(R.layout.fragment_session, container, false);

        MainActivity ob = new MainActivity();
        ob.frag_no=2;

        b2 = view.findViewById(R.id.b2);
        back2 = view.findViewById(R.id.back2);
        display_ID = view.findViewById(R.id.display_ID);

////        Retrieve the value
//        String value = getArguments().getString("ID");
//        display_ID.setText("Session ID: "+value);

        display_ID.setText("Session ID: "+ob.ID);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment friend = new FragmentFriend();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, friend).commit();
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment beginPage = new FragmentBegin();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, beginPage).commit();
            }
        });


        return view;
    }
}