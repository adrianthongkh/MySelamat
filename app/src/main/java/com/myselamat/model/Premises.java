package com.myselamat.model;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Blob;

public class Premises {

    private Blob qr_image;
    private String premise_id;
    private LatLng latLng;
    private boolean status;

    // ---------- Constructor, Getters & Setters ---------- //

    public Premises() {
    }

    public Premises(Blob qr_image, String premise_id, LatLng latLng, boolean status) {
        this.qr_image = qr_image;
        this.premise_id = premise_id;
        this.latLng = latLng;
        this.status = status;
    }

    public Blob getQr_image() {
        return qr_image;
    }

    public void setQr_image(Blob qr_image) {
        this.qr_image = qr_image;
    }

    public String getPremise_id() {
        return premise_id;
    }

    public void setPremise_id(String premise_id) {
        this.premise_id = premise_id;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // -------------------- End -------------------- //


    // --------------- Functions starts here --------------- //
    public void add_visitor(User user) {
        // TODO: add user to database during check-in
    }

    public void updateStatus() {
        status = status ? false : true;
    }

    public void checkStatus() {
        // TODO: Check whether the coast is clear (e.g. not infected)
    }
}
// -------------------- End -------------------- //
