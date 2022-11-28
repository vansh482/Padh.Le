package com.example.project;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity {

    EditText age;
    TextView age_field;
    RadioGroup gender, activities, freetime, goout, health;
    Button Continue;
    String Gender, Age, Activities, FreeTime, GoOut, Health;
//    String url = "https://studytime-padhle.herokuapp.com/predict";
    String url = "https://sahilsorte16.pythonanywhere.com/predict";
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        age = (EditText) findViewById(R.id.age);
        age_field = (TextView) findViewById(R.id.age_field);
        gender = (RadioGroup) findViewById(R.id.q1);
        activities = (RadioGroup) findViewById(R.id.q2);
        freetime = (RadioGroup) findViewById(R.id.q3);
        goout = (RadioGroup) findViewById(R.id.q4);
        health = (RadioGroup) findViewById(R.id.q5);
        Continue = (Button) findViewById(R.id.cont);

    }

    public void onClick(View v) {
        if (TextUtils.isEmpty(age.getText())) {
            age_field.setVisibility(View.VISIBLE);
        } else {
            age_field.setVisibility(View.INVISIBLE);
            Age = age.getText().toString();
            Gender = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getContentDescription().toString();
            Activities = ((RadioButton) findViewById(activities.getCheckedRadioButtonId())).getContentDescription().toString();
            FreeTime = ((RadioButton) findViewById(freetime.getCheckedRadioButtonId())).getContentDescription().toString();
            GoOut = ((RadioButton) findViewById(goout.getCheckedRadioButtonId())).getContentDescription().toString();
            Health = ((RadioButton) findViewById(health.getCheckedRadioButtonId())).getContentDescription().toString();
            Predict();
//            Toast.makeText(this, Gender + " " + Age + " " + Activities + " " + FreeTime + " " + GoOut + " " + Health, Toast.LENGTH_LONG).show();

        }


    }

    public void Predict() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("category");
                            String data;
                            switch (result) {
                                case "1":
                                    data = "Less than 4 hours.";
                                    break;
                                case "2":
                                    data = "Between 4 to 7 hours";
                                    break;
                                case "3":
                                    data = "Between 7 to 12 hours";
                                    break;
                                case "4":
                                    data = "More than 12 hours";
                                    break;
                                default:
                                    data = "";
                                    break;
                            }
                            Toast.makeText(Form.this, data, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent();
                            i.putExtra("category",result);
                            setResult(RESULT_OK, i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Form.this, error.getMessage().toString(), Toast.LENGTH_LONG);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sex", String.valueOf(Gender));
                params.put("age", String.valueOf(Age));
                params.put("activities", String.valueOf(Activities));
                params.put("freetime", String.valueOf(FreeTime));
                params.put("goout", String.valueOf(GoOut));
                params.put("health", String.valueOf(Health));

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(Form.this);
        queue.add(stringRequest);
    }
}