package com.myselamat.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Isolation {

    private String uid;         // for user referencing
    @DocumentId
    private String docId;       // for document referencing
    private @ServerTimestamp Date startDate;
    private int day_count;
    private boolean severityStatus;

    public Isolation() {}

    public Isolation(String uid) {

        this.uid = uid;
        this.day_count = 0;
        this.severityStatus = false;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDocId() { return docId; }

    public void setDocId(String docId) { this.docId = docId; }

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

    public boolean isSeverityStatus() {
        return severityStatus;
    }

    public void setSeverityStatus(boolean severityStatus) {
        this.severityStatus = severityStatus;
    }
}
