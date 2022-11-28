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
    private boolean isResume,isRunning,flg=false;
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
        Log.d("ma3","oncreate "+ String.valueOf(min));

        // progressBar = findViewById(R.id.progress_bar);
        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);
        handler = new Handler();
        handler2 = new Handler();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            prev = extra.getLong("time");
            Log.d("ma3","prev"+String.valueOf(prev));
            pos = extra.getInt("pos");
            uId=extra.getString("uId");
            // Log.d("ma3", "onCreate: " + prev + pos);
        }

        if(prev>0){
            flg=true;
            sec =  (prev / 1000);
            hrs = hrs+(sec / 3600);
            min = (min+(sec / 60))%60;


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
                Log.d("ma3","on+5 "+ String.valueOf(min));

                j++;
                min=min+5;
                hrs = hrs+min / 60;
                sec = sec % 60;
                min=min%60;
                sTime=String.format("%02d", hrs) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                chronometer.setText(sTime);
            }
        });

//-------------------------------------------------------------------------------------------------
        btStart.setOnClickListener(new View.OnClickListener() {
            boolean temp = true;
            @Override
            public void onClick(View v) {
                Log.d("ma3","btstart "+ String.valueOf(min));

                //-----------------------------------------------------------------------
                if (!isResume) {

                    isResume = true;
                    isRunning=true;
                    handler.postDelayed(runnable, 0);
                    btStop.setVisibility(View.GONE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24));
                } else {

                    isRunning=false;
                    isResume = false;
                    handler.removeCallbacks(runnable);
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
                Log.d("ma3","btstop "+ String.valueOf(min));

                if(!isResume){


                    k= (tUpdate*1000)+(j*300000);
                    //                    time=String.valueOf(k );
                    i.putExtra("position", pos);
                    i.putExtra("uId", uId);
                    i.putExtra("time", k);
                    i.putExtra("sTime",sTime);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));

                    tUpdate=0L;
                    sec=0;
                    min=0;
                    hrs=0;
                    chronometer.setText("00:00:00");
                    k=0;
                    j=0;
                    handler.removeCallbacks(runnable);

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
            Log.d("ma3","run "+ String.valueOf(min));

            long temp ,temp2;
            if(isRunning==true) tUpdate++;

            hrs=hrs;
            temp2=((tUpdate+min*60)/3600);
            min=min;
            temp=(tUpdate/60);

            if(flg){
                tUpdate+=sec;

                flg=false;
            }

            sec = tUpdate % 60;


            sTime = String.format("%02d", (hrs+temp2)%60) + ":" + String.format("%02d", (min+temp)%60) + ":" + String.format("%02d", sec);
            chronometer.setText(sTime);
            handler.postDelayed(this, 1000);
        }
    };
}