package com.example.project;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Linechart_Fragment extends Fragment {
    LocalDate date = LocalDate.now();
    LineChart lineChart;
    View view;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linechart_, container, false);
        drawLC();
        Log.d("Today", String.valueOf(date.getDayOfMonth()+1));
        return view;
    }

    private void drawLC() {
        lineChart = view.findViewById(R.id.reportingChart);
        List<Entry> lineEntry = new ArrayList<>();

        ArrayList <String> xLabels = new ArrayList<>();

//        xLabels.add("Mon");
//        xLabels.add("Tue");
//        xLabels.add("Wed");
//        xLabels.add("Thur");
//        xLabels.add("Fri");
//        xLabels.add("Sat");
        xLabels.add(String.valueOf(date.getDayOfMonth()-6));
        xLabels.add(String.valueOf(date.getDayOfMonth()-5));
        xLabels.add(String.valueOf(date.getDayOfMonth()-4));
        xLabels.add(String.valueOf(date.getDayOfMonth()-3));
        xLabels.add(String.valueOf(date.getDayOfMonth()-2));
        xLabels.add(String.valueOf(date.getDayOfMonth()-1));
        xLabels.add(String.valueOf(date.getDayOfMonth()));

        lineChart.getLegend().setEnabled(false);

        lineChart.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabels.get((int) value);
            }
        });

        //lineChart.getAxisLeft().setAxisMinimum(-2);


        lineEntry.add(new Entry(0, 0f));
        lineEntry.add(new Entry(1, 8.5f));
        lineEntry.add(new Entry(2, 0));
        lineEntry.add(new Entry(3, 7));
        lineEntry.add(new Entry(4, 0));
        lineEntry.add(new Entry(5, 3));
        lineEntry.add(new Entry(6, 0));
        db.collection("users").document(user.getUid()).collection("Completed").document(date.toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("date", "Document exists!");
                        db.collection("users").document(user.getUid()).collection("Completed")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Log.d("Hello", document.getId() + " => " + document.getData().toString());
                                            }
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
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
        LineDataSet lds = new LineDataSet(lineEntry, "");
        Description desc = new Description();
        desc.setText("");
        lineChart.setDescription(desc);

        lds.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lds.setLineWidth(4);
        lds.setDrawValues(false);

        lds.setColor(Color.argb(255,200,128,255));
        LineData ld = new LineData(lds);

        lineChart.setExtraOffsets(35f, 35f, 35f, 35f);
        lineChart.setData(ld);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.getAxisLeft().setDrawAxisLine(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.animateXY(1000, 1000, Easing.EaseOutBack);
        YAxis yAxis = lineChart.getAxisLeft();
        lineChart.setPinchZoom(true);
        lineChart.invalidate();
    }
}