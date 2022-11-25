package com.example.project;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Task {
    String name;
    int id;
    long time;
    String sTime;
    long Ptime;
    boolean completed;
    String uId;
    String session;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public boolean isCompleted() {
        return completed;
    }
    public Task(){}
    Task(String name, int id, int time, String sTime) {
        this.name = name;
        this.id = id;
        this.time = time;
        this.sTime = sTime;
    }
   Task(String name, int id, int time,String sTime,boolean completed,String uId){
        this.name=name;
        this.id=id;
        this.time=time;
        this.sTime=sTime;
        this.completed=completed;
        this.uId=uId;

    }
    Task(String name, int id, int time,String sTime,boolean completed,String uId,String date){
        this.name=name;
        this.id=id;
        this.time=time;
        this.sTime=sTime;
        this.completed=completed;
        this.uId=uId;
        this.date=date;

    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    String sPTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getsTime() {
        return sTime;
    }

    public void setsTime(String sTime) {
        this.sTime = sTime;
    }

    public long getPtime() {
        return Ptime;
    }

    public void setPtime(long ptime) {
        Ptime = ptime;
    }

    public String getsPTime() {
        return sPTime;
    }

    public void setsPTime(String sPTime) {
        this.sPTime = sPTime;
    }

    Task(String name, int id){
        this.name=name;
        this.id=id;
        time=0;
        sTime="00:00:00";
    }



}
