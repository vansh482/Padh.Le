package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentBegin extends Fragment {

    Button join_button;
    Button create_button;
    EditText entered_id;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_begin, container, false);

        MainActivity ob = new MainActivity();
        ob.frag_no=1;

        join_button = view.findViewById(R.id.join_button);
        create_button = view.findViewById(R.id.create_button);
        entered_id = view.findViewById(R.id.entered_id);


        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=entered_id.getText().toString();
                if(str.length()==6) {
                    ob.ID=str;
                    Fragment session = new FragmentSession();
                    FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                    fm.replace(R.id.container, session).commit();
                }
                else
                    Toast.makeText(getActivity(), "Enter an ID of length 6", Toast.LENGTH_SHORT).show();
//                Bundle args = new Bundle();
//                args.putString("ID", ID);
//                session.setArguments(args);
//                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, session).commit();
            }
        });

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random = (int)((Math.random())*1000000);
                ob.ID = String.valueOf(random);
                Fragment session = new FragmentSession();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, session).commit();
//                Fragment session = new FragmentSession();
//                Bundle args = new Bundle();
//                args.putString("ID", ID);
//                session.setArguments(args);
//                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, session).commit();
            }
        });
        return view;
    }
}