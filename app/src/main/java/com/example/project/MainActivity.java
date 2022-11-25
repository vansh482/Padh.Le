package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    static String ID;
    static String UID;
    static String myFriend;
    static int frag_no;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frag_no=1;

        //firebase auth
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            Intent i = new Intent(this,LoginRegisterActivity.class);
            startActivity(i);
            this.finish();
        }
        //firestore
        //if not present in backend firestore then

        ///////////////////////////////////////////////////////////
        db.collection("users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("date", "Document exists!");
                        Intent i=new Intent(MainActivity.this,Form.class);
                        startActivityForResult(i,30);




                        Log.d("TAG", "onCreate: ");
                        Map<String, Object> details = new HashMap<>();
                        details.put("Name", user.getDisplayName());
                        details.put("Email", user.getEmail());
                        details.put("Uid", user.getUid());
                        details.put("Category",i.getStringExtra("category"));
                        db.collection("users").document(user.getUid()).collection("Details").document(user.getUid())
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
                    } else {
                        Log.d("date", "Document does not exist!");
                    }
                } else {
                    Log.d("date", "Failed with: ", task.getException());
                }
            }
        });


    ///////////////////////////////////////////////





        //navigation
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setItemHorizontalTranslationEnabled(true);

        bottomNavigationView.setSelectedItemId(R.id.add_sign);

    }

    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    FragmentBegin fragmentBegin = new FragmentBegin();
    FragmentSession fragmentSession = new FragmentSession();
    FragmentFriend fragmentFriend = new FragmentFriend();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.add_sign:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.time_outline:
                switch (frag_no){
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentBegin).commit();
                        return true;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSession).commit();
                        return true;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentFriend).commit();
                        return true;
                }
                break;
            case R.id.pie_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }
    void startLogin(){
        Intent i = new Intent(this, LoginRegisterActivity.class);
        startActivity(i);
        this.finish();
    }
    public void handleLogout(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    startLogin();

                }
            }
        });
    }
}