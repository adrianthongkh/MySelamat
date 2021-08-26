package com.myselamat.model;

import java.util.Date;

public class Isolation {

    private String uid;         // for user referencing
    private Date startDate;
    private int day_count;
    private boolean status;

    public Isolation() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDay_count() {
        return day_count;
    }

    public void setDay_count(int day_count) {
        this.day_count = day_count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
