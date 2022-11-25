package com.example.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class FragmentSession extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter2 recyclerAdapter2;

    RecyclerAdapter2.RecyclerViewClickListener listener;
    List<DetailCard> myList;

    Button b2;
    Button back2;
    TextView display_ID;
    MainActivity ob;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_session, container, false);

        ob = (MainActivity)getActivity();
        myList = new ArrayList<>();

        setOnClickListener();

        recyclerView = view.findViewById(R.id.listv);
        recyclerAdapter2 = new com.example.project.RecyclerAdapter2(myList, listener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter2);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        db.collection("sessions").document(ob.ID).collection("sessions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DetailCard person = document.toObject(DetailCard.class);
                                myList.add(person);
//                                if(person.getName()!=null)
//                                {
                                Log.d("myList", person.getName().toString());
//                                }
                            }
//                            myList.add(new DetailCard("Nipun", "something@xyz.com", "myID"));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.setAdapter(recyclerAdapter2);

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        ob.frag_no = 2;

        b2 = view.findViewById(R.id.b2);
        back2 = view.findViewById(R.id.back2);
        display_ID = view.findViewById(R.id.display_ID);
        display_ID.setText("Session ID: " + ob.ID);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ob.UID="uid from selected list";
                ob.UID = "BeCZHrAnc4W85L8ybXu8kXIos123";

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

    private void setOnClickListener() {
        listener = new RecyclerAdapter2.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                ob.UID = myList.get(position).Uid;
                ob.myFriend = myList.get(position).Name;
                Fragment friend = new FragmentFriend();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, friend).commit();
            }
        };
    }
}