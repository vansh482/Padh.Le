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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragmentSession extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter2 recyclerAdapter2;

    RecyclerAdapter2.RecyclerViewClickListener listener;
    List<DetailCard> myList;

    Button reload;
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

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        reloadList();


        ob.frag_no = 2;

        reload = view.findViewById(R.id.reload);
        back2 = view.findViewById(R.id.back2);
        display_ID = view.findViewById(R.id.display_ID);
        display_ID.setText("Session ID: " + ob.ID);

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment beginPage = new FragmentBegin();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, beginPage).commit();
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadList();
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

    public void reloadList(){
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
                            recyclerView.setAlpha(0);
                            recyclerView.setTranslationX(100);
                            recyclerView.animate().alpha(1).translationXBy(-100).setDuration(600);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}