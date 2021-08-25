package com.myselamat.model;

public class TravelHistory {


    public String Location, CheckIn, CheckOut, idd;

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getCheckIn() {
        return CheckIn;
    }

    public void setCheckIn(String checkIn) {
        this.CheckIn = checkIn;
    }

    public String getCheckOut() {
        return CheckOut;
    }

    public void setCheckOut(String checkOut) {
        this.CheckOut = checkOut;
    }


    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public TravelHistory() {

    }

    public TravelHistory(String Location, String CheckIn, String CheckOut, String idd) {
        this.Location = Location;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
        this.idd = idd;
    }
}
