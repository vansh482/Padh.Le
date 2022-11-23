package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    EditText text;
    Button save;
    int id;
    private RadioButton b1;
    private RadioButton b2;
    private RadioButton b3;
    private RadioButton b4;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        id=-1;
        text = findViewById(R.id.editText);
        b1=findViewById(R.id.radioButton);
        b2=findViewById(R.id.radioButton2);
        b3=findViewById(R.id.radioButton3);
        b4=findViewById(R.id.radioButton4);
        save = findViewById(R.id.saveTask);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=1;
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id =2;
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=3;
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=4;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=text.getText().toString();
                if(name.equals(""))
                    Toast.makeText(MainActivity2.this, "Please Enter a Task Name", Toast.LENGTH_SHORT).show();
                else if(id==-1)
                    Toast.makeText(MainActivity2.this, "Please select How Big the Task is", Toast.LENGTH_SHORT).show();
                else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("a", name);
                    resultIntent.putExtra("b", id);


                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}