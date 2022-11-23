package com.example.project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Linechart_Fragment extends Fragment {
    LineChart lineChart;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_linechart_, container, false);
        drawLC();
        return view;
    }

    private void drawLC() {
        lineChart = view.findViewById(R.id.reportingChart);
        List<Entry> lineEntry = new ArrayList<>();

        ArrayList <String> xLabels = new ArrayList<>();

        xLabels.add("Mon");
        xLabels.add("Tue");
        xLabels.add("Wed");
        xLabels.add("Thur");
        xLabels.add("Fri");
        xLabels.add("Sat");
        xLabels.add("Sun");

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