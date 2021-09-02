package com.myselamat.model;

import com.google.firebase.firestore.GeoPoint;

public class Premises {

    private String docID;
    private String name;
    private GeoPoint position;
    private boolean status;

    // ---------- Constructor, Getters & Setters ----------

    public Premises() {
    }

    public Premises(String name, GeoPoint position, boolean status) {
        this.name = name;
        this.position = position;
        this.status = status;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getPosition() {
        return position;
    }

    public void setPosition(GeoPoint position) {
        this.position = position;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // -------------------- End -------------------- //

}
