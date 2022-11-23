package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity3 extends AppCompatActivity {
    private Chronometer chronometer;
    ImageButton btStart, btStop;
    private boolean isResume;
    Handler handler,handler2;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    long sec=0, hrs=0, min=0, prev=0;
    int pos=0;
    String uId;
    Button button;
    private ProgressBar progressBar;
    int i = 0;
    String sTime;
    long k,j=0;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        progressBar = findViewById(R.id.progress_bar);
        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);
        handler = new Handler();
        handler2 = new Handler();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            prev = extra.getLong("time");
            pos = extra.getInt("pos");
            uId=extra.getString("uId");
            Log.d("ma3", "onCreate: " + prev + pos);
        }

        if(prev>0){
            sec =  (prev / 1000);
            min = min+(sec / 60);
            hrs = hrs+(min / 60);
            sec = sec % 60;
//            tUpdate+=sec;

            sTime=String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
//            sec=min=hrs=0;
            chronometer.setText(sTime);
        }

        button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j++;
                min=min+5;
                hrs = hrs+min / 60;
                sec = sec % 60;
                min=min%60;
                sTime=String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                chronometer.setText(sTime);
            }
        });

        Log.d("ma4", "onCreate: " + k + sTime + pos);

//-------------------------------------------------------------------------------------------------
        btStart.setOnClickListener(new View.OnClickListener() {
            boolean fuck = true;
            @Override
            public void onClick(View v) {
                //--------------------------------------------------------------------
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // set the limitations for the numeric
                        // text under the progress bar
                        if(fuck){
                            if (i <= 100) {
                                progressBar.setProgress(i);
                                i++;
                                handler.postDelayed(this, 200);
                                Log.d("ma4", "onCreate: " + k + sTime + pos);
                            } else {
                                i=0;
                                progressBar.setProgress(i);
                                i++;
                                handler.postDelayed(this, 200);
                            }
                        }}
                }, 200);
                //-----------------------------------------------------------------------
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis()-prev;
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    fuck = true;
                    btStop.setVisibility(View.GONE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                } else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    fuck = false;
                    isResume = false;
                    btStop.setVisibility(View.VISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                }
            }
        });
//---------------------------------------------------------------------------------------------------------


        Intent i=new Intent();

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isResume){
                    k= tUpdate+j*300000;
//                    time=String.valueOf(k );
                    i.putExtra("position", pos);
                    i.putExtra("uId", uId);
                    i.putExtra("time", k);
                    i.putExtra("sTime",sTime);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    tMilliSec=0L;
                    tStart=0L;
                    tBuff=0L;
                    tUpdate=0L;
                    sec=0;
                    min=0;
                    hrs=0;
                    chronometer.setText("00:00:00");
                    k=0;
                    j=0;
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }
    //-----------------------------------------------------------------------------------------------------
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec =  (tUpdate / 1000);
            if(flag) {
                sec += prev/1000;
                flag=false;
            }
            min = min+(sec / 60);
            hrs = hrs+(min / 60);
            sec = sec % 60;
            sTime=String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
            chronometer.setText(sTime);
            handler.postDelayed(this, 60);
        }
    };
}