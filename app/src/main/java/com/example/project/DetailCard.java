package com.example.project;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetailCard {
    String Name;
    String Email;
    String Uid;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public DetailCard(){}
    DetailCard(String name, String email,String uid) {
        this.Name = name;
        this.Email = email;
        this.Uid=uid;
    }
}
