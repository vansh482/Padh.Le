package com.example.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

public class FragmentFriend extends Fragment {

    Button back3;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<Task> myList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_friend, container, false);
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        myList = new ArrayList<>();

        MainActivity ob = new MainActivity();
        ob.frag_no=3;

        //create list task of uid of friends


        db.collection("users").document(ob.UID).collection("Tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            myList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Task tt=document.toObject(Task.class);
                                Log.d("mylist2",tt.getName());
                                myList.add(tt);

                            }

//                            show list from here


                        } else {
                            Log.d("mylist2", "Error getting documents: ", task.getException());
                        }
                    }
                });
        back3 = view.findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment session = new FragmentSession();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.container, session).commit();
            }
        });
        return view;
    }
}