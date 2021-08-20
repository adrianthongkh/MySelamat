package com.myselamat.model;

import java.sql.Time;

public class TravelDetails {

    private User user;
    private Premises premise;
    private Time checkIn;
    private Time checkOut;

    public TravelDetails(User user, Premises premise, Time checkIn, Time checkOut) {
        this.user = user;
        this.premise = premise;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public TravelDetails() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Premises getPremise() {
        return premise;
    }

    public void setPremise(Premises premise) {
        this.premise = premise;
    }

    public Time getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Time checkIn) {
        this.checkIn = checkIn;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Time checkOut) {
        this.checkOut = checkOut;
    }
}
