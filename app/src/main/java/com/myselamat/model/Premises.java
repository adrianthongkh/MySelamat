package com.myselamat.model;

import com.google.firebase.firestore.GeoPoint;

public class Premises {

    private String docID;
    private String qr_imagePath;
    private String name;
    private GeoPoint position;
    private boolean status;

    // ---------- Constructor, Getters & Setters ---------- //

    public Premises() {
    }

    public Premises(String qr_imagePath, String name, GeoPoint position, boolean status) {
        this.qr_imagePath = qr_imagePath;
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

    public String getQr_imagePath() {
        return qr_imagePath;
    }

    public void setQr_imagePath(String qr_imagePath) {
        this.qr_imagePath = qr_imagePath;
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
