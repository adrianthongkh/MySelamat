package model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private String email;
    private String phone;

    // ---------- Constructor, Getters & Setters ---------- //
    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // -------------------- End -------------------- //


    // --------------- Functions starts here --------------- //
    public void createAccount() {
        // TODO: create account and add to database
    }

    public void resetEmail(String newEmail) {
        // TODO: update Email
    }

    public void resetPass(String oldPass, String newPass) {
        // TODO: update Password
    }

    public boolean verify() {
        // TODO: validate from server whether username exists / correct password
        return true;
    }

    public double riskPrediction(LatLng latLng) {
        // TODO: call Risk class and predict risk
        return 0.00;
    }

    public User findUser() {
        // TODO: find User based on username
        return new User();
    }

    public void updateStatus() {
        // TODO: perform status update and update status of Premise
    }

    public List<Premises> getAllPremises() {
        // TODO: Query from database and extract all premises
        return new ArrayList<Premises>();
    }

    public List<Premises> getPremisesInTimeframe() {
        // TODO: query from database and extract premises within timeframe
        return new ArrayList<Premises>();
    }

    // -------------------- End -------------------- //
}
