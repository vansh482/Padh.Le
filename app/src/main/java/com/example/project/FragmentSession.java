package com.example.project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentSession extends Fragment {

    Button b2;
    Button back2;
    TextView display_ID;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<DetailCard> myList;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity ob = new MainActivity();

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_session, container, false);
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        // create list of friends with same session
        myList = new ArrayList<>();
        db.collection("sessions").document(ob.ID).collection("sessions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DetailCard person=document.toObject(DetailCard.class);
                                myList.add(person);
//                                if(person.getName()!=null)
//                                {
                                    Log.d("mylist",person.getName().toString());
//                                }

                            }
                            //show list here
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

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
//                ob.UID="uid from selected list";
                ob.UID="BeCZHrAnc4W85L8ybXu8kXIos123";

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