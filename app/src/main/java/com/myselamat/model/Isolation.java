package com.myselamat.model;

import java.util.Date;

public class Isolation {

    private User user;
    private Date startDate;
    private Date endDate;
    private boolean status;

    // --------------- Constructor, Setter and Getter --------------- //
    public Isolation() {}

    public Isolation(User user, Date startDate, Date endDate) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    // --------------- End --------------- //



    // --------------- Functions starts here --------------- //
    public boolean getMedicalHelp (String[] s) {
        // TODO: determine from module parameters
        return false;
    }

    public void updateStatus() {
        status = status ? false : true;
    }

    public void addIsolation(Isolation iso) {
        // TODO: add isolation information to database
    }
}
