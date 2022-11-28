package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.List;
public class LoginRegisterActivity extends AppCompatActivity {
    int AUTHUI_REQ_CODE=10111;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            this.finish();
        }



        List<AuthUI.IdpConfig> provider = Arrays.asList(
//                new  AuthUI.IdpConfig.EmailBuilder().build()
                new AuthUI.IdpConfig.GoogleBuilder().build()
//                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setAlwaysShowSignInMethodScreen(true)
                .build();
        startActivityForResult(intent, AUTHUI_REQ_CODE);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AUTHUI_REQ_CODE){
            if(resultCode==RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                DocumentReference docRef = db.collection("users").document(user.getUid());
//                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (value.exists()) {
//                                //update
//                                Log.d("myuser","exists");
//                            } else {
//                                //Insert
//                                Log.d("myuser","not exists");
//
//                            }
//                        }
//
//                    });
//                FirebaseFirestore firestoreDatabase= FirebaseFirestore.getInstance();
//                firestoreDatabase.collection("users")
//                        .whereEqualTo(user.getUid(), user.getUid())
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                if (task.getResult().getDocuments().size() > 0)
//                                // Here is your document with id
//                            }
//                        });

//                mAuth = FirebaseAuth.getInstance();
//                AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//                mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
//                        boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
//                        if (isNewUser) {
//                            Log.d("TAG", "Is New User!");
//                        } else {
//                            Log.d("TAG", "Is Old User!");
//                        }
//                    }



//                });
//                OnCompleteListener<AuthResult> completeListener = new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
//                            if (isNewUser) {
//                                Log.d("TAG101", "Is New User!");
//                            } else {
//                                Log.d("TAG101", "Is Old User!");
//                            }
//                        }
//                    }
//
//                };
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
            }
            else{
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if(response == null)
                    Log.d("LoginActivity", "Login is cancelled by the user" );
                else
                    Log.e("LoginActivity", "Error: ", response.getError() );

            }

        }
    }
}