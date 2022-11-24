package com.example.project;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentBegin extends Fragment {
    Button join_button;
    Button create_button;
    EditText entered_id;
    CardView cardView, cardView1;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        cardView = view.findViewById(R.id.cardView);
        cardView1 = view.findViewById(R.id.cardView1);

//        animateButton(join_button);
//        animateButton(create_button);
        animateView(cardView1, 1000);
        animateView(cardView, 2000);
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=entered_id.getText().toString();
                // search in firestore if present then go ahead else toast
//                CollectionReference dbref = db.collection("users");

                if(str.length()==6) {

                    db.collection("sessions").document(str).collection("sessions")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if(task.getResult().size() > 0) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                Log.d("FTAG", "Room already exists, start the chat");
                                                ob.ID=str;

                                                joinsession(str);
                                                //go to session fragment
                                                Fragment session = new FragmentSession();
                                                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                                                fm.replace(R.id.container, session).commit();
                                            }
                                        } else {
                                            Log.d("FTAG", "room doesn't exist create a new room");
                                            Toast.makeText(getActivity(), "Session does not exists", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.d("FTAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
//                    db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
////                                List<String> list = new ArrayList<>();
//                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    db.collection("users").document(document.getId()).collection("Details").document(document.getId()).get()
////                                    list.add(document.getId());
//                                }
////                                Log.d(TAG, list.toString());
//                            } else {
//                                Log.d("TAG", "Error getting documents: ", task.getException());
//                            }
//                        }
//                    });



//                    db.collection("users")
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
//                                    if (task.isSuccessful()) {
//
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Log.d("exists","hehe");
//                                            Task tt=document.toObject(Task.class);
//                                            if(tt.getSession()==str)
//                                            {
//                                                Log.d("exists","true");
//
//                                                break;
//                                            }
//                                            else
//                                            {
//                                                Log.d("exists","false");
//                                            }
//                                        }
//
//                                    }
//                                    else {
//                                        Toast.makeText(getActivity(), "Session does not exists", Toast.LENGTH_SHORT).show();
//                                        Log.d("exists", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });

//                    Log.d("exists", "noError getting documents");
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
                // to firestore done
//                Map<String, Object> data = new HashMap<>();
//                data.put("", ob.ID);
//                db.collection("users").document(user.getUid()).collection("Details").document(user.getUid())
//                        .update("session", ob.ID);



//                db.collection("users").document(user.getUid()).collection("Details").document(user.getUid()).update({"session", ob.ID});
//                db.collection("sessions").document(ob.ID).set(data, SetOptions.merge());

              joinsession(ob.ID);


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

    private void animateButton(Button b){
        b.setAlpha(0f);
        b.setTranslationY(50);
        b.animate().alpha(1f).translationYBy(-50).setDuration(1000);
    }

    private void animateView(View v, int duration){
        v.setAlpha(0f);
        v.setTranslationY(50);
        v.animate().alpha(1f).translationYBy(-50).setDuration(duration);
    }

    private void joinsession(String sessioncode) {
        Map<String, Object> details = new HashMap<>();
        details.put("Name", user.getDisplayName());
        details.put("Email", user.getEmail());
        details.put("Uid", user.getUid());
        db.collection("sessions").document(sessioncode).collection("sessions").document()
                .set(details)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firestore", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Firestore", "Error writing document", e);
                    }
                });
    }
}